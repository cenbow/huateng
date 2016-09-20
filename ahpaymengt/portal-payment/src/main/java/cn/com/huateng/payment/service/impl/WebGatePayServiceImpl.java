package cn.com.huateng.payment.service.impl;

import cn.com.huateng.CommonUser;
import cn.com.huateng.account.model.TInfoAccount;
import cn.com.huateng.account.service.SeqGeneratorService;
import cn.com.huateng.account.service.UserService;
import cn.com.huateng.common.*;
import cn.com.huateng.payment.WebGateFactory;
import cn.com.huateng.payment.controller.VirtualGoodsController;
import cn.com.huateng.payment.dao.PublicFeeMapper;
import cn.com.huateng.payment.dao.TPortOrderBaseMapper;
import cn.com.huateng.payment.dao.VirtualGoodsMapper;
import cn.com.huateng.payment.dao.WebGatePayMapper;
import cn.com.huateng.payment.manage.TPortOrderBaseManage;
import cn.com.huateng.payment.manage.VirtualGoodsManage;
import cn.com.huateng.payment.manage.WebGateManage;
import cn.com.huateng.payment.model.*;
import cn.com.huateng.payment.service.WebGatePayService;
import cn.com.huateng.util.DateUtil;
import cn.com.huateng.util.Md5;
import cn.com.huateng.util.ValidationFieldUtil;
import com.aixforce.user.base.BaseUser.TYPE;
import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.huateng.p3.component.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 网关支付 服务
 * User: 董培基
 * Date: 13-8-1
 * Time: 上午11:08
 */
@Service
public class WebGatePayServiceImpl implements WebGatePayService {

    private final static Logger log = LoggerFactory.getLogger(WebGatePayServiceImpl.class);


    @Autowired
    private WebGateManage webGateManage;
    @Autowired
    private TPortOrderBaseManage tPortOrderBaseManage;
    @Autowired
    private VirtualGoodsManage virtualGoodsManage;
    @Autowired
    private WebGatePayMapper webGatePayMapper;
    @Autowired
    private TPortOrderBaseMapper tPortOrderBaseMapper;
    @Autowired
    private VirtualGoodsMapper virtualGoodsMapper;
    @Autowired
    private VirtualGoodsController virtualGoodsController;
    @Autowired
    private SeqGeneratorService seqGeneratorService;
    private final Splitter splitter = Splitter.on('|').trimResults().omitEmptyStrings();
    @Autowired
    private UserService userService;
    /*网关流水Seq名称*/
    @Value("#{app.webGatePaySeq}")
    private String webGatePaySeq;
    /*业务表流水Seq名称*/
    @Value("#{app.publicFeeSeq}")
    private String publicFeeSeq;
    /*订单总表流水Seq名称*/
    @Value("#{app.orderBaseSeq}")
    private String orderBaseSeq;
    @Autowired
    private WebGateFactory webGateFactory;

    /**
     * 账户充值 网关
     *
     * @return 调用网关支付对象
     */
    public Response<SubmitWebGatePay> accountRecharge(SubmitWebGatePay submitWebGatePay) {
        log.info("网关 充值 开始-----------");
        //获取流水
        try {
            checkNotNull(submitWebGatePay, BussErrorCode.ERROR_CODE_800001.getErrorcode());
            Long webGatePaySeqLong = seqGeneratorService.generateOrderNo(webGatePaySeq);
            String webGatePayString = CodeGenerator.generateSeqNo(webGatePaySeqLong);
            Long publicFeeSeqLong = seqGeneratorService.generateOrderNo(publicFeeSeq);
            String publicFeeSeqString = CodeGenerator.generateSeqNo(publicFeeSeqLong);
            Long orderBaseSeqLong = seqGeneratorService.generateOrderNo(orderBaseSeq);
            String orderBaseSeqString = CodeGenerator.generateSeqNo(orderBaseSeqLong);
            submitWebGatePay.setTraceSeqNo(webGatePayString);
            submitWebGatePay.setOrderId(publicFeeSeqString);
            submitWebGatePay.setOrderAmt(1L);
            submitWebGatePay.setRealAmt(1L);

            Long beforeAmt;
            Response<TInfoAccount> tInfoAccountResponse = userService.loadAccounts(new CommonUser(Long.parseLong(submitWebGatePay.getCustomerId()), null, TYPE.BUYER, submitWebGatePay.getUnifyId()));
            if (tInfoAccountResponse.isSuccess()) {
                log.info("success to get tInfoAccount with unifyId:{}", submitWebGatePay.getUnifyId());
                TInfoAccount tInfoAccount = tInfoAccountResponse.getResult();
                beforeAmt = tInfoAccount.getBalance();
            } else {
                log.error("fail to get tInfoAccount with unifyId:{}", submitWebGatePay.getUnifyId());
                return new Response<SubmitWebGatePay>(BussErrorCode.ERROR_CODE_700003.getErrorcode());
            }
            //网关mac
            SubmitWebGatePay newSubmitWebGetPay = createMac(false,submitWebGatePay, WebGateFactory.ConfigKey.accountRecharge);


            TPortOrderBase tPortOrderBaseVo = new TPortOrderBase();
            tPortOrderBaseVo.setOrderSeq(orderBaseSeqString); //总表订单号
            tPortOrderBaseVo.setFeeType(TxnType.RECHARGE.getValue());//缴费类型
            tPortOrderBaseVo.setCreateTime(DateUtil.getTime());//创建时间
            tPortOrderBaseVo.setMerchantId(newSubmitWebGetPay.getMerchantId());
            tPortOrderBaseVo.setAttachAmount(0L);//附加金额
            tPortOrderBaseVo.setCurType(newSubmitWebGetPay.getCurType());
            tPortOrderBaseVo.setOrderReqTranSeq(newSubmitWebGetPay.getOrderId());//业务流水号
            tPortOrderBaseVo.setStatus(OrderStatus.ORDER_STATUS_1.getValue());//1-已申请2-支付成功3-支付失败4-销账受理成功5-销账受理失败
            tPortOrderBaseVo.setTxnType(TxnType.RECHARGE.getValue());//1001:账户充值1002:账户支付
            tPortOrderBaseVo.setEncodeType(newSubmitWebGetPay.getEncMode());
            tPortOrderBaseVo.setTxtn1(newSubmitWebGetPay.getClientIp());
            tPortOrderBaseVo.setUnifyId(newSubmitWebGetPay.getUnifyId());
            tPortOrderBaseManage.insert(tPortOrderBaseVo);

            WebGatePay webGatePay = new WebGatePay();
            webGatePay.setOrderNo(newSubmitWebGetPay.getOrderId());
            webGatePay.setOrderReqTranSeq(webGatePayString);
            webGatePay.setMerchantId(newSubmitWebGetPay.getMerchantId());
            webGatePay.setCurType(newSubmitWebGetPay.getCurType());
            webGatePay.setEncodeType(newSubmitWebGetPay.getEncMode());
            webGatePay.setTranDate(newSubmitWebGetPay.getTraceTime());
            webGatePay.setCreateTime(DateUtil.getDate());
            webGatePay.setFeeType(TxnType.RECHARGE.getValue());
            webGatePay.setLevel(PayType.SinglePay.getValue());//单条流水 单笔充值
            webGatePay.setStatus(OrderStatus.ORDER_STATUS_1.getValue());
            webGatePay.setBusinessCode(newSubmitWebGetPay.getBusiType());
            webGatePay.setTxt1(newSubmitWebGetPay.getClientIp());
            webGatePay.setOrderBaseNo(orderBaseSeqString);
            webGatePay.setUnifyId(submitWebGatePay.getUnifyId());

            log.info("==================网关充值订单表记录数据开始============>>{}" + webGatePay);
            //记录数据库作为唯一流水
            //查询网关支付订单表
            webGateManage.insertPortWebGetPay(webGatePay);
            return new Response<SubmitWebGatePay>(newSubmitWebGetPay);
        } catch (NullPointerException e) {
            log.error("the data submitWebGatePay is null ");
            return new Response<SubmitWebGatePay>(e.getMessage());
        } catch (Exception e) {
            log.error("获取网关充值 对象 失败 cause by {}", e.getMessage());
            return new Response<SubmitWebGatePay>(BussErrorCode.ERROR_CODE_999999.getErrorcode());
        }
    }


    /**
     * 网关支付
     *
     * @param submitWebGatePay 网关业务对象
     * @return 如果下单成功，则返回msg 缴费成功 and flag  0
     */
    @Override
    public Response<SubmitWebGatePay> orderPay(SubmitWebGatePay submitWebGatePay) {
        //参数校验
        try {
            String errorCode = BussErrorCode.ERROR_CODE_800001.getErrorcode();
            checkNotNull(submitWebGatePay, errorCode);
            Iterable<String> parts = splitter.split(submitWebGatePay.getOrderId());
            if (Iterables.isEmpty(parts)) {
                throw new NullPointerException(String.valueOf(errorCode));
            }
            ValidationFieldUtil.checkChar(submitWebGatePay.getFeeType(), errorCode);


            log.info("==================网关支付 前台返回信息============>>{}", submitWebGatePay);
            //获取流水
            Long orderReqTranSeqLong = seqGeneratorService.generateOrderNo(webGatePaySeq);
            String orderReqTranSeqString = CodeGenerator.generateSeqNo(orderReqTranSeqLong);
            submitWebGatePay.setTraceSeqNo(orderReqTranSeqString);

            List<WebGatePay> webGatePays = Lists.newArrayList();// 合并支付 多笔子流水订单 待插入

            Long orderAmount = 0L;
            Long productAmount = 0L;
            Long attachAmount = 0L;


            WebGateFactory.ConfigKey configKey = WebGateFactory.ConfigKey.payRecharge;
            boolean isMultiPay = false;
            List<String> orderNoList = ImmutableList.copyOf(parts);
            if (orderNoList.size() > 1) {  //多笔订单 合并支付
                isMultiPay = true;
            }
            for (String orderSeq : orderNoList) {
                VirtualGoods virtualGoods = virtualGoodsMapper.findVirtualGoods(new VirtualGoods(orderSeq));
                List<TPortOrderBase> tPortOrderBases = tPortOrderBaseMapper.findTPortOrderBaseByOrderTranReq(new TPortOrderBase(orderSeq));
                String orderBaseNo;
                if (null != tPortOrderBases && tPortOrderBases.size() == 1) {  //取出总表订单
                    orderBaseNo = tPortOrderBases.get(0).getOrderSeq();
                } else {
                    log.error("网关支付 订单号:{} 总表记录数超过1 为:{} ", orderSeq, tPortOrderBases.size());
                    return new Response<>(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                }
                WebGatePay webGatePay = new WebGatePay();
                Long childOrderReqTranSeq = seqGeneratorService.generateOrderNo(webGatePaySeq);
                String childOrderReqTranSeqString = CodeGenerator.generateSeqNo(childOrderReqTranSeq);
                webGatePay.setOrderAmount(virtualGoods.getOrderAmt());
                webGatePay.setMerchantId(submitWebGatePay.getMerchantId());
                if (isMultiPay) {
                    webGatePay.setLevel(PayType.MultiPay.getValue());
                    webGatePay.setSubOrderReqTranSeq(childOrderReqTranSeqString);
                } else {
                    webGatePay.setLevel(PayType.SinglePay.getValue());
                    webGatePay.setOrderReqTranSeq(orderReqTranSeqString);
                }
                webGatePay.setOrderNo(orderSeq);
                webGatePay.setOrderBaseNo(orderBaseNo);
                webGatePay.setFeeType(FeeType.VIRTUALGOODS.getValue());
                webGatePay.setCreateTime(DateUtil.getDate());
                webGatePay.setProductAmount(virtualGoods.getOrderAmt());
                webGatePay.setUnifyId(virtualGoods.getUnifyId());
                webGatePay.setStatus(OrderStatus.ORDER_STATUS_1.getValue());
                webGatePays.add(webGatePay);
                orderAmount = orderAmount + virtualGoods.getOrderAmt();
                productAmount = productAmount + virtualGoods.getOrderAmt();
            }
            submitWebGatePay.setOrderAmt(orderAmount);
            submitWebGatePay.setRealAmt(productAmount);
            //网关mac
            SubmitWebGatePay newSubmitWebGetPay = createMac(isMultiPay,submitWebGatePay, configKey);
            insertWebGatePay(isMultiPay, webGatePays, orderReqTranSeqString, orderAmount, productAmount, attachAmount, submitWebGatePay);
            return new Response<>(newSubmitWebGetPay);
        } catch (NullPointerException e) {
            log.error("check arguments null for save webgate order ,{}", e);
            return new Response<>(e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("check arguments error for save webgate order,{}", e);
            return new Response<>(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            String str;
            if (e.toString().contains("ORA-00001")) {
                str = BussErrorCode.ERROR_CODE_900003
                        .getErrorcode();
            } else {
                str = BussErrorCode.ERROR_CODE_900007
                        .getErrorcode();
            }
            log.error("failed to save webGate order,", e);
            return new Response<>(str);
        } catch (CannotAcquireLockException e) {
            log.error("failed to save webGate order,", e);
            return new Response<>(BussErrorCode.ERROR_CODE_900006
                    .getErrorcode());
        } catch (Exception e) {
            log.error("failed to save webGate order", e);
            return new Response<>(BussErrorCode.ERROR_CODE_999999.getErrorcode());
        }

    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void insertWebGatePay(Boolean isMultiPay, List<WebGatePay> webGatePays, String orderReqTranSeqString, Long orderAmount, Long productAmount, Long attachAmount, SubmitWebGatePay submitWebGatePay) {
        if (isMultiPay) {     //合并支付
            for (WebGatePay webGatePay1 : webGatePays) {
                //记录数据库作为唯一流水
                //查询网关支付订单表
                webGatePay1.setOrderReqTranSeq(orderReqTranSeqString);
                webGatePayMapper.doInsertPortWebGatePay(webGatePay1);
            }
            WebGatePay webGatePay = new WebGatePay();
            webGatePay.setOrderReqTranSeq(orderReqTranSeqString);
            webGatePay.setOrderAmount(orderAmount);
            webGatePay.setMerchantId(submitWebGatePay.getMerchantId());
            webGatePay.setProductAmount(productAmount);
            webGatePay.setAttachAmount(attachAmount);
            webGatePay.setMerchantId(submitWebGatePay.getMerchantId());
            webGatePay.setCurType(submitWebGatePay.getCurType());
            webGatePay.setEncodeType(submitWebGatePay.getEncMode());
            webGatePay.setTranDate(submitWebGatePay.getTraceTime());
            webGatePay.setCreateTime(DateUtil.getDate());
            webGatePay.setFeeType(submitWebGatePay.getFeeType());
            webGatePay.setLevel(PayType.MultiPay.getValue());
            webGatePay.setStatus(OrderStatus.ORDER_STATUS_1.getValue());
            webGatePay.setBusinessCode(submitWebGatePay.getTransCode());
            webGatePay.setTxt1(submitWebGatePay.getClientIp());
            webGatePay.setOrderBaseNo(submitWebGatePay.getOrderBaseNo());
            webGatePay.setUnifyId(submitWebGatePay.getUnifyId());

            log.info("==================网关支付订单表记录数据开始============>>{}" + webGatePay);
            webGatePayMapper.doInsertPortWebGatePay(webGatePay);
        } else {
            WebGatePay webGatePay1 = webGatePays.get(0);
            webGatePay1.setMerchantId(submitWebGatePay.getMerchantId());
            webGatePayMapper.doInsertPortWebGatePay(webGatePay1);
        }
    }

    /**
     * 生成 网关 mac
     *
     * @param submitWebGetPay 网关支付 业务对象
     * @return 生成 网关支付 业务对象
     */

    private SubmitWebGatePay createMac(boolean isMultiPay,SubmitWebGatePay submitWebGetPay, WebGateFactory.ConfigKey configKey) {
        WebGateFactory.Config config = webGateFactory.getConfig(configKey);
        submitWebGetPay.setPayUrl(config.getClient());
        submitWebGetPay.setPgUrl(config.getFrontUrl());
        submitWebGetPay.setBgUrl(config.getBackUrl());
        submitWebGetPay.setTransCode(config.getTraceCode());
        submitWebGetPay.setMerchantId(config.getCode());
        submitWebGetPay.setMac(config.getMacKey());
        submitWebGetPay.setTraceTime(DateUtil.formatDate(DateUtil.getDate(), "yyyyMMddHHmmss"));
        submitWebGetPay.setCurType("RMB");
        submitWebGetPay.setEncMode("1");
        submitWebGetPay.setBusiCode(config.getBusiCode());
        submitWebGetPay.setBusiType(config.getBusiType());
        submitWebGetPay.setAttach("02");//附加信息(02:网上)
        if(isMultiPay){
            //合并支付清空 合并订单号
            submitWebGetPay.setOrderId("");
        }
        String macTxt = "MERCHANTID=" + config.getCode() + "&ORDERID=" + submitWebGetPay.getOrderId()
                + "&TRACESEQNO=" + submitWebGetPay.getTraceSeqNo()
                + "&TRACETIME=" + submitWebGetPay.getTraceTime()
                + "&ORDERAMT=" + submitWebGetPay.getOrderAmt()
                + "&ACCORGCODE="
                + "&PGURL=" + submitWebGetPay.getPgUrl()
                + "&BGURL=" + submitWebGetPay.getBgUrl()
                + "&ATTACH=" + submitWebGetPay.getAttach()
                + "&BUSITYPE=" + submitWebGetPay.getBusiType()
                + "&UNIFYID=" + submitWebGetPay.getUnifyId()
                + "&CLIENTIP=" + submitWebGetPay.getClientIp()
                + "&MAC=" + submitWebGetPay.getMac();
        log.info("加密前明文:" + macTxt);
        String mac = Md5.encode(macTxt); //MD5加密;
        log.info("加密后密文:" + mac);
        submitWebGetPay.setMac(mac);
        return submitWebGetPay;

    }

    /**
     * 账户充值 1 效验mac  2 修改订单状态
     *
     * @param submitWebGetPay 网关 支付对象
     * @return 成功 失败
     */
    public Response<Boolean> webGateAccountRechargeBack(SubmitWebGatePay submitWebGetPay) {
        log.info("网关充值 回调 开始");
        //效验参数
        try {
            checkValue(submitWebGetPay);
        } catch (NullPointerException e) {
            log.error("check arguments null for web gate ,{}", e.getMessage());
            return new Response<Boolean>(e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("check arguments error for web gate,{}", e.getMessage());
            return new Response<Boolean>(e.getMessage());
        }
        WebGateFactory.Config config = webGateFactory.getConfig(WebGateFactory.ConfigKey.accountRecharge);

        //取得回调参数，加密 比较
        String macKey = config.getMacKey();

        String macTxt = "UPTRANSEQ=" + submitWebGetPay.getUpTranSeq()
                + "&MERCHANTID=" + submitWebGetPay.getMerchantId()
                + "&ORDERID=" + submitWebGetPay.getOrderId()
                + "&ORDERAMT=" + submitWebGetPay.getOrderAmt()
                + "&RETNCODE=" + submitWebGetPay.getReturnCode()
                + "&RETNINFO=" + submitWebGetPay.getReturnInfo()
                + "&STLMDATE=" + submitWebGetPay.getTraceTime()
                + "&KEY=" + macKey;
        String mac = Md5.encode(macTxt);//MD5加密
        log.info("账户充值接收网关的加密明文" + macTxt);
        log.info("网关平台流水号" + submitWebGetPay.getUpReqTranSeq()
                + "银行流水号" + submitWebGetPay.getUpBankTranSeq());
        log.info("账户充值网关后台回调接收方法======网关返回加密串:" + submitWebGetPay.getSign()
                + "==网关支付流水:" + submitWebGetPay.getUpTranSeq()
                + "===渠道号:" + submitWebGetPay.getMerchantId()
                + "订单号=" + submitWebGetPay.getOrderId()
                + "===订单流水:" + submitWebGetPay.getTraceSeqNo()
                + "充值的账户号:" + submitWebGetPay.getUnifyId()
                + "===MAC:" + mac);
        log.info("账户充值网关后台回调接收MAC验证:" + mac.equals(submitWebGetPay.getSign())
                + "支付结果：" + submitWebGetPay.getReturnCode()
                + "\t" + submitWebGetPay.getReturnInfo());
        /**************end*****************/
        if (mac.equals(submitWebGetPay.getSign()) && "0000".equals(submitWebGetPay.getReturnCode())) {
            //查询网关支付订单表根据支付类型来进行后续销账流程
            try {
                WebGatePay webGatePay = new WebGatePay(submitWebGetPay.getOrderId(), submitWebGetPay.getTraceSeqNo());
                List<WebGatePay> webGatePayList = webGatePayMapper.findPortWebGatePayInfo(webGatePay);
                if (webGatePayList != null && webGatePayList.size() == 1) {//只有一条记录单笔支付 订单
                    WebGatePay webGatePay1 = webGatePayList.get(0);
                    //查询订单总表记录
                    TPortOrderBase tPortOrderBaseVo = new TPortOrderBase();
                    tPortOrderBaseVo.setOrderReqTranSeq(webGatePay1.getOrderNo());
                    List<TPortOrderBase> orderBaseVo = tPortOrderBaseMapper.findTPortOrderBase(tPortOrderBaseVo);
                    log.info("订单总表查询,订单号:" + tPortOrderBaseVo.getOrderSeq() + "\t 业务订单号:"
                            + tPortOrderBaseVo.getOrderReqTranSeq() + "订单总表记录数:" + orderBaseVo.size());
                    if ("1".equals(webGatePay1.getStatus())) {//订单状态为未支付的情况下才把从网关接收到的状态更新到数据表中
                        WebGatePay iPortWebGatePayUpdate = new WebGatePay();
                        iPortWebGatePayUpdate.setStatus(OrderStatus.ORDER_STATUS_9.getValue());//9:充值成功
                        iPortWebGatePayUpdate.setOrderReqTranSeq(submitWebGetPay.getTraceSeqNo());
                        iPortWebGatePayUpdate.setOrderNo(submitWebGetPay.getOrderId());
                        iPortWebGatePayUpdate.setUpTranSeq(submitWebGetPay.getUpTranSeq());
                        iPortWebGatePayUpdate.setReturnCode(submitWebGetPay.getReturnCode());
                        iPortWebGatePayUpdate.setReturnInfo(submitWebGetPay.getReturnInfo());
                        iPortWebGatePayUpdate.setOrderAmount(submitWebGetPay.getOrderAmt());
                        iPortWebGatePayUpdate.setProductAmount(submitWebGetPay.getRealAmt());
                        iPortWebGatePayUpdate.setAttachAmount(submitWebGetPay.getAttachAmt());
                        iPortWebGatePayUpdate.setBankId(submitWebGetPay.getBankId());
                        webGateManage.updatePortWebGetPay(iPortWebGatePayUpdate);

                        //更改订单总表信息
                        if (orderBaseVo.size() == 1) {
                            TPortOrderBase orderBVo = orderBaseVo.get(0);
                            orderBVo.setTranDate(DateUtil.formatString(submitWebGetPay.getTraceTime()));
                            orderBVo.setStatus(OrderStatus.ORDER_STATUS_9.getValue());//9:充值成功
                            orderBVo.setMerchantId(submitWebGetPay.getMerchantId());
                            orderBVo.setRetnCode(submitWebGetPay.getReturnCode());
                            orderBVo.setRetnInfo(submitWebGetPay.getReturnInfo());
                            orderBVo.setUpTranSeq(submitWebGetPay.getUpTranSeq());
                            orderBVo.setBankId(submitWebGetPay.getBankId());
                            orderBVo.setProductAmount(submitWebGetPay.getRealAmt());
                            orderBVo.setOrderAmount(submitWebGetPay.getOrderAmt());
                            orderBVo.setAttachAmount(submitWebGetPay.getAttachAmt());
                            tPortOrderBaseManage.updateTPortOrderBase(orderBVo);
                        }
                        return new Response<Boolean>(true);
                    } else {
                        log.info("订单状态不为创建状态或将不能进行销账处理，流水对应的订单状态!!!============ORDERREQTRANSEQ:"
                                + submitWebGetPay.getTraceSeqNo() + "\t订单状态:" + webGatePay1.getStatus());
                        return new Response<Boolean>(false);
                    }
                } else {
                    log.info("查询不到该流水对应的订单信息!!!============ORDERREQTRANSEQ:" + submitWebGetPay.getTraceSeqNo());
                    return new Response<Boolean>(false);
                }
            } catch (CannotAcquireLockException e) {
                log.error("failed to update web gate order{} cause by {},", submitWebGetPay.getOrderId(), e);
                return new Response<Boolean>(BussErrorCode.ERROR_CODE_900006
                        .getErrorcode());
            } catch (Exception e) {
                log.info("fail to change order info {} cause by {}：", submitWebGetPay.getOrderId(), e);
                return new Response<Boolean>(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            }
        } else {
            //失败
            log.info("账户充值失败============网关后台回调接收MAC验证:"
                    + mac.equals(submitWebGetPay.getSign()) + "\t" + submitWebGetPay.getReturnCode() + "\t"
                    + submitWebGetPay.getReturnInfo() + "\t 订单流水号:" + submitWebGetPay.getTraceSeqNo());
            if (!Strings.isNullOrEmpty(submitWebGetPay.getOrderId()) && !Strings.isNullOrEmpty(submitWebGetPay.getTraceSeqNo())) {
                WebGatePay webGatePay = new WebGatePay(submitWebGetPay.getOrderId(), submitWebGetPay.getTraceSeqNo());
                try {
                    List<WebGatePay> webGatePayList = webGatePayMapper.findPortWebGatePayInfo(webGatePay);
                    if (webGatePayList.size() == 1) {//只有一条记录才正确大于一条记录将异常
                        WebGatePay iPortWebGetPayUpdate = webGatePayList.get(0);
                        iPortWebGetPayUpdate.setStatus(OrderStatus.ORDER_STATUS_10.getValue());//5:充值失败
                        iPortWebGetPayUpdate.setOrderReqTranSeq(submitWebGetPay.getTraceSeqNo());
                        iPortWebGetPayUpdate.setOrderNo(submitWebGetPay.getOrderId());
                        iPortWebGetPayUpdate.setUpTranSeq(submitWebGetPay.getUpTranSeq());
                        iPortWebGetPayUpdate.setReturnCode(submitWebGetPay.getReturnCode());
                        iPortWebGetPayUpdate.setReturnInfo(submitWebGetPay.getReturnInfo());
                        iPortWebGetPayUpdate.setOrderAmount(submitWebGetPay.getOrderAmt());
                        iPortWebGetPayUpdate.setProductAmount(submitWebGetPay.getRealAmt());
                        iPortWebGetPayUpdate.setAttachAmount(submitWebGetPay.getAttachAmt());
                        webGateManage.updatePortWebGetPay(iPortWebGetPayUpdate);
                        //查询订单总表记录
                        TPortOrderBase tPortOrderBaseVo = new TPortOrderBase();
                        tPortOrderBaseVo.setOrderReqTranSeq(iPortWebGetPayUpdate.getOrderNo());
                        List<TPortOrderBase> orderBaseVo = tPortOrderBaseMapper.findTPortOrderBase(tPortOrderBaseVo);
                        log.info("订单总表查询,订单号:" + tPortOrderBaseVo.getOrderSeq() + "\t 业务订单号:"
                                + tPortOrderBaseVo.getOrderReqTranSeq() + "订单总表记录数:" + orderBaseVo.size());
                        //更改订单总表信息
                        if (orderBaseVo.size() == 1) {
                            TPortOrderBase orderBVo = orderBaseVo.get(0);
                            orderBVo.setTranDate(DateUtil.formatString(submitWebGetPay.getTraceTime()));
                            orderBVo.setStatus(OrderStatus.ORDER_STATUS_10.getValue());//7:充值失败
                            orderBVo.setMerchantId(submitWebGetPay.getMerchantId());
                            orderBVo.setRetnCode(submitWebGetPay.getReturnCode());
                            orderBVo.setRetnInfo(submitWebGetPay.getReturnInfo());
                            orderBVo.setUpTranSeq(submitWebGetPay.getUpTranSeq());
                            orderBVo.setProductAmount(submitWebGetPay.getRealAmt());
                            orderBVo.setOrderAmount(submitWebGetPay.getOrderAmt());
                            orderBVo.setAttachAmount(submitWebGetPay.getAttachAmt());
                            tPortOrderBaseManage.updateTPortOrderBase(orderBVo);
                        }
                    }
                } catch (CannotAcquireLockException e) {
                    log.error("failed to update web gate order{} cause by {}", submitWebGetPay.getOrderId(), e);
                    return new Response<Boolean>(BussErrorCode.ERROR_CODE_900006
                            .getErrorcode());
                } catch (Exception e) {
                    log.error("fail to update order{} cause by {}", submitWebGetPay.getOrderId(), e);
                    return new Response<Boolean>(BussErrorCode.ERROR_CODE_999999.getErrorcode());
                }
            }
            return new Response<Boolean>(false);
        }
    }

    /**
     * 账单销帐回调订单 1 效验mac  2 修改订单状态
     *
     * @param submitWebGetPay 网关 支付对象
     * @return 成功 失败
     */
    public Response<Boolean> webGateBack(SubmitWebGatePay submitWebGetPay) {
        log.info("网关支付 回调 开始");
        //效验参数
        try {
            checkValue(submitWebGetPay);
        } catch (NullPointerException e) {
            log.error("check arguments null for web gate ,", e.getMessage());
            return new Response<>(e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("check arguments error for web gate,", e.getMessage());
            return new Response<>(e.getMessage());
        }

        WebGateFactory.Config config = webGateFactory.getConfig(WebGateFactory.ConfigKey.payRecharge);

        //取得回调参数，加密 比较
        String macKey = config.getMacKey();
        String macTxt = "UPTRANSEQ=" + submitWebGetPay.getUpTranSeq() + "&MERCHANTID=" + config.getCode()
                + "&ORDERID=" + submitWebGetPay.getOrderId() + "&ORDERAMT=" + submitWebGetPay.getOrderAmt()
                + "&RETNCODE=" + submitWebGetPay.getReturnCode() + "&RETNINFO=" + submitWebGetPay.getReturnInfo()
                + "&STLMDATE=" + submitWebGetPay.getTraceTime() + "&KEY=" + macKey;
        String mac = Md5.encode(macTxt);//MD5加密
        log.info("网关平台流水号:" + submitWebGetPay.getUpReqTranSeq() + "银行流水号:" + submitWebGetPay.getUpBankTranSeq()
                + "BANKID:" + submitWebGetPay.getBankId());
        log.info("接收网关的加密明文" + macTxt);
        log.info("网关后台回调接收方法======网关返回加密串:" + submitWebGetPay.getSign()
                + "====网关支付流水:" + submitWebGetPay.getUpTranSeq() + "===渠道号:" + submitWebGetPay.getMerchantId()
                + "订单号=" + submitWebGetPay.getOrderId() + "===订单流水:" + submitWebGetPay.getTraceSeqNo() + "===MAC:" + mac);
        log.info("网关后台回调接收MAC验证:" + mac.equals(submitWebGetPay.getSign()) + "支付结果：" + submitWebGetPay.getReturnCode()
                + "\t" + submitWebGetPay.getReturnInfo());

        //加密验证比较
        if (mac.equals(submitWebGetPay.getSign()) && "0000".equals(submitWebGetPay.getReturnCode())) {
            //修改订单信息 修改网关表 和总表
            try {
                WebGatePay webGatePay = new WebGatePay(submitWebGetPay.getOrderId(), submitWebGetPay.getTraceSeqNo());
                List<WebGatePay> webGatePayList = webGatePayMapper.findPortWebGatePayInfo(webGatePay);
                log.info("网关对象参数,订单号:" + webGatePay.getOrderNo() + "网关支付流水:" + webGatePay.getOrderReqTranSeq() + "网关销帐 订单 条数" + webGatePayList.size());
                if (webGatePayList.size() >= 1) { //只有一条记录单笔支付 订单
                    for (WebGatePay newWebGatePay : webGatePayList) {
                        //网关返回支付金额与网关交易信息表里的总的流水的金额不匹配时，认为是非法请求
                        if (Strings.isNullOrEmpty(newWebGatePay.getSubOrderReqTranSeq()) && !Objects.equal(newWebGatePay.getOrderAmount(), submitWebGetPay.getOrderAmt())) {
                            return new Response<Boolean>(BussErrorCode.ERROR_CODE_900007.getErrorcode());
                        }
                        process(newWebGatePay,submitWebGetPay);
                    }
                } else {
                    return new Response<>(BussErrorCode.ERROR_CODE_900007
                            .getErrorcode());
                }
            } catch (CannotAcquireLockException e) {
                log.error("failed to update web gate order{} cause by {},", submitWebGetPay.getOrderId(), e);
                return new Response<>(BussErrorCode.ERROR_CODE_900006
                        .getErrorcode());
            } catch (Exception e) {
                log.info("修改订单{} 出现异常{}：", submitWebGetPay.getOrderId(), e);
                return new Response<>(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            }
            return new Response<>(true);
        } else {
            //失败
            log.info("支付失败============网关后台回调接收MAC验证:" + mac.equals(submitWebGetPay.getSign()) + "\t" + submitWebGetPay.getReturnCode()
                    + "\t" + submitWebGetPay.getReturnInfo() + "\t 订单流水号:" + submitWebGetPay.getTraceSeqNo());
            if (!Strings.isNullOrEmpty(submitWebGetPay.getTraceSeqNo())) {
                WebGatePay webGatePay = new WebGatePay(submitWebGetPay.getOrderId(), submitWebGetPay.getTraceSeqNo());
                try {
                    List<WebGatePay> webGatePayList = webGatePayMapper.findPortWebGatePayInfo(webGatePay);
                    for (WebGatePay webGetPayUpdate : webGatePayList) {
                        webGetPayUpdate.setStatus(OrderStatus.ORDER_STATUS_3.getValue());//3:支付失败
                        webGetPayUpdate.setUpTranSeq(submitWebGetPay.getUpTranSeq());
                        webGetPayUpdate.setReturnInfo(submitWebGetPay.getReturnInfo());
                        webGetPayUpdate.setReturnCode(submitWebGetPay.getReturnCode());
                        webGateManage.updatePortWebGetPay(webGetPayUpdate);
                        if (Strings.isNullOrEmpty(webGetPayUpdate.getSubOrderReqTranSeq())) {
                            //查询订单总表记录
                            TPortOrderBase tPortOrderBaseVo = new TPortOrderBase(webGetPayUpdate.getOrderNo());
                            List<TPortOrderBase> orderBaseVo = tPortOrderBaseMapper.findTPortOrderBase(tPortOrderBaseVo);
                            log.info("订单总表查询,订单号:" + tPortOrderBaseVo.getOrderSeq() + "\t 业务订单号:" + tPortOrderBaseVo.getOrderReqTranSeq() + "订单总表记录数:" + orderBaseVo.size());
                            //更改订单总表信息
                            if (orderBaseVo.size() == 1) {
                                TPortOrderBase orderBVo = orderBaseVo.get(0);
                                orderBVo.setTranDate(DateUtil.getDate());
                                orderBVo.setStatus(OrderStatus.ORDER_STATUS_3.getValue());//3:支付失败
                                orderBVo.setRetnCode(submitWebGetPay.getReturnCode());
                                orderBVo.setRetnInfo(submitWebGetPay.getReturnInfo());
                                orderBVo.setUpTranSeq(submitWebGetPay.getUpTranSeq());
                                orderBVo.setMerchantId(submitWebGetPay.getMerchantId());
                                tPortOrderBaseManage.updateTPortOrderBase(orderBVo);
                            }
                            switch (tPortOrderBaseVo.getFeeType()) {
                                case "05":
                                    VirtualGoods virtualGoods = virtualGoodsMapper.findVirtualGoods(new VirtualGoods(webGetPayUpdate.getOrderNo()));
                                    virtualGoods.setStatus(OrderStatus.ORDER_STATUS_3.getValue());
                                    virtualGoodsManage.updateVirtualGoods(virtualGoods);
                                    break;
                            }
                        }
                    }
                } catch (CannotAcquireLockException e) {
                    log.error("failed to update web gate order{} cause by {}", submitWebGetPay.getOrderId(), e);
                    return new Response<Boolean>(BussErrorCode.ERROR_CODE_900006
                            .getErrorcode());
                } catch (Exception e) {
                    log.error("fail to update order{} cause by {}", submitWebGetPay.getOrderId(), e);
                    return new Response<Boolean>(BussErrorCode.ERROR_CODE_999999.getErrorcode());
                }
            }
            return new Response<Boolean>(false);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void process(WebGatePay newWebGatePay,SubmitWebGatePay submitWebGetPay){
        if (OrderStatus.ORDER_STATUS_1.getValue().equals(newWebGatePay.getStatus())) {   //订单状态为未支付的情况下才把从网关接收到的状态更新到数据表中
            newWebGatePay.setStatus(OrderStatus.ORDER_STATUS_2.getValue());//2:支付成功
            newWebGatePay.setUpTranSeq(submitWebGetPay.getUpTranSeq());
            newWebGatePay.setReturnInfo(submitWebGetPay.getReturnInfo());
            newWebGatePay.setReturnCode(submitWebGetPay.getReturnCode());
            newWebGatePay.setBankId(submitWebGetPay.getBankId());
            webGateManage.updatePortWebGetPay(newWebGatePay);
        }
        if (!Strings.isNullOrEmpty(newWebGatePay.getSubOrderReqTranSeq()) || !Objects.equal(PayType.MultiPay.getValue(), newWebGatePay.getLevel())) {
            //查询订单总表记录
            TPortOrderBase tPortOrderBaseVo = new TPortOrderBase(newWebGatePay.getOrderNo());
            List<TPortOrderBase> orderBaseVo = tPortOrderBaseMapper.findTPortOrderBaseByOrderTranReq(tPortOrderBaseVo);
            log.info("订单总表查询,订单号:" + tPortOrderBaseVo.getOrderSeq() + "\t 业务订单号:" + tPortOrderBaseVo.getOrderReqTranSeq() + "订单总表记录数:" + orderBaseVo.size());
            //更改订单总表信息
            if (orderBaseVo.size() == 1) {
                TPortOrderBase orderBVo = orderBaseVo.get(0);
                orderBVo.setTranDate(DateUtil.formatString(submitWebGetPay.getTraceTime()));
                orderBVo.setStatus(OrderStatus.ORDER_STATUS_2.getValue());//2:支付成功
                orderBVo.setRetnCode(submitWebGetPay.getReturnCode());
                orderBVo.setRetnInfo(submitWebGetPay.getReturnInfo());
                orderBVo.setUpTranSeq(submitWebGetPay.getUpTranSeq());
                orderBVo.setMerchantId(submitWebGetPay.getMerchantId());
                orderBVo.setBankId(submitWebGetPay.getBankId());
                tPortOrderBaseManage.updateTPortOrderBase(orderBVo);
            }
            log.info("缴费类型：" + newWebGatePay.getFeeType());
            //调用不同的销帐方法
            if (FeeType.VIRTUALGOODS.getValue().equals(newWebGatePay.getFeeType()) && OrderStatus.ORDER_STATUS_2.getValue().equals(newWebGatePay.getStatus())) {//是虚拟商品  并支付成功的订单才做销账请求
                /************调虚拟商品销账*start*******************/
                try {
                    virtualGoodsController.recharge(newWebGatePay.getOrderNo(), submitWebGetPay.getTraceTime(), submitWebGetPay.getUpTranSeq(), submitWebGetPay.getBankId());
                } catch (Exception e) {
                    log.error("fail to recharge virtualGoods cause by {}",e);
                }
                /***************调虚拟商品销账*end****************/
            }
        }
    }

    /**
     * 效验参数
     *
     * @param submitWebGatePay 网关业务对象
     */
    private void checkValue(SubmitWebGatePay submitWebGatePay) throws NullPointerException, IllegalArgumentException {
        log.info("SubmitWebGatePay:" + submitWebGatePay.toString());
        //参数校验
        String errorCode = BussErrorCode.ERROR_CODE_800001.getErrorcode();
        checkNotNull(submitWebGatePay, errorCode);
        ValidationFieldUtil.checkChar(submitWebGatePay.getTraceSeqNo(), errorCode);
        ValidationFieldUtil.checkChar(submitWebGatePay.getUpTranSeq(), errorCode);
        ValidationFieldUtil.checkChar(submitWebGatePay.getOrderAmt().toString(), errorCode);
        ValidationFieldUtil.checkChar(submitWebGatePay.getCurType(), errorCode);
        ValidationFieldUtil.checkChar(submitWebGatePay.getEncMode(), errorCode);
        ValidationFieldUtil.checkChar(submitWebGatePay.getSign(), errorCode);
        ValidationFieldUtil.checkChar(submitWebGatePay.getUpBankTranSeq(), errorCode);
        ValidationFieldUtil.checkChar(submitWebGatePay.getUpReqTranSeq(), errorCode);
    }
}
