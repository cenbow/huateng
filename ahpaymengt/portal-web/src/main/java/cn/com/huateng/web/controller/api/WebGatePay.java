package cn.com.huateng.web.controller.api;

import cn.com.huateng.CommonUser;
import cn.com.huateng.common.BussErrorCode;
import cn.com.huateng.common.Constants;
import cn.com.huateng.payment.model.SubmitWebGatePay;
import cn.com.huateng.payment.service.WebGatePayService;
import cn.com.huateng.util.Md5;
import cn.com.huateng.util.NumberUtil;
import cn.com.huateng.util.ValidationFieldUtil;
import cn.com.huateng.web.util.HttpUtil;
import com.aixforce.site.container.RenderConstants;
import com.aixforce.site.model.redis.Page;
import com.aixforce.user.base.UserUtil;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.huateng.p3.component.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * Author: 韩纳威
 * Date: 13-8-2
 * Time: 下午3:23
 */

@Controller
@RequestMapping("/api/webGatePay")
public class WebGatePay {
    private final static Logger log = LoggerFactory.getLogger(WebGatePay.class);

    @Autowired
    private WebGatePayService webGatePayService;

    /**
     * 网关支付后台通知
     *
     * @throws Exception
     */
    @RequestMapping(value = "/payConfirm", method = RequestMethod.POST)
    public String webGatePayConfirm(HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        log.info("----------------网关支付后台通知开始----------------");
        try {
            SubmitWebGatePay submitWebGatePay = new SubmitWebGatePay();
            String upTranSeq = Strings.nullToEmpty(request.getParameter("UPTRANSEQ"));//支付网关平台交易流水号
            if (!ValidationFieldUtil.checkChar(upTranSeq)) {
                log.info("非法请求！网关支付流水upTranSeq:{}非法！", upTranSeq);
                return null;
            } else {
                submitWebGatePay.setUpTranSeq(upTranSeq);
            }
            String merchantId = Strings.nullToEmpty(request.getParameter("MERCHANTID"));//渠道号
            if (!ValidationFieldUtil.checkChar(merchantId)) {
                log.info("非法请求！渠道号 merchantId:{}非法 ！", merchantId);
                return null;
            } else {
                submitWebGatePay.setMerchantId(merchantId);
            }
            String traceSeqNo = Strings.nullToEmpty(request.getParameter("TRACESEQNO"));//订单请求交易流水号
            if (!ValidationFieldUtil.checkChar(traceSeqNo)) {
                log.info("非法请求！缴费订单流水 traceSeqNo:{} 非法！", traceSeqNo);
                return null;
            } else {
                submitWebGatePay.setTraceSeqNo(traceSeqNo);
            }
            submitWebGatePay.setOrderId(Strings.nullToEmpty(request.getParameter("ORDERID")));//订单号
            String orderAmount = Strings.nullToEmpty(request.getParameter("ORDERAMT"));//订单总金额
            if (!ValidationFieldUtil.doublePattern.matcher(orderAmount).find()) {
                log.info("非法请求！订单总支付金额orderAmount:{}非法！", orderAmount);
                return null;
            } else {
                submitWebGatePay.setOrderAmt(Long.parseLong(orderAmount));
            }
            String realAmt = Strings.nullToEmpty(request.getParameter("REALAMT"));//产品金额
            if (!ValidationFieldUtil.doublePattern.matcher(realAmt).find()) {
                log.info("非法请求！实际金额realAmt:{}非法！", realAmt);
                return null;
            } else {
                submitWebGatePay.setRealAmt(Long.parseLong(realAmt));
            }
            String retnCode = Strings.nullToEmpty(request.getParameter("RETNCODE"));//处理结果码
            if (!ValidationFieldUtil.checkChar(retnCode)) {
                log.info("非法请求！返回CODE retnCode:{}非法！", retnCode);
                return null;
            } else {
                submitWebGatePay.setReturnCode(retnCode);
            }
            String retnInfo = Strings.nullToEmpty(request.getParameter("RETNINFO"));//处理结果码
            if (!ValidationFieldUtil.checkChar(retnCode)) {
                log.info("非法请求！返回信息 retnInfo:{}非法！", retnInfo);
                return null;
            } else {
                submitWebGatePay.setReturnInfo(retnInfo);
            }
            String tranDate = Strings.nullToEmpty(request.getParameter("STLMDATE"));//支付网关平台交易日期
            if (!ValidationFieldUtil.checkChar(tranDate)) {
                log.info("非法请求！支付日期tranDate:{}非法！", tranDate);
                return null;
            } else {
                submitWebGatePay.setTraceTime(tranDate);
            }
            String curType = Strings.nullToEmpty(request.getParameter("CURTYPE"));//币种
            if (!ValidationFieldUtil.checkChar(curType)) {
                log.info("非法请求！币种类型curType:{}非法！", curType);
                return null;
            } else {
                submitWebGatePay.setCurType(curType);
            }
            String encodeType = Strings.nullToEmpty(request.getParameter("ENCMODE"));//加密方式
            if (!ValidationFieldUtil.checkChar(encodeType)) {
                log.info("非法请求！加密方式类型encodeType:{}非法！", encodeType);
                return null;
            } else {
                submitWebGatePay.setEncMode(encodeType);
            }
            String sign = Strings.nullToEmpty(request.getParameter("MAC"));//数字签名
            if (!ValidationFieldUtil.checkChar(sign)) {
                log.info("非法请求！加密串sign:{}非法！", sign);
                return null;
            } else {
                submitWebGatePay.setSign(sign);
            }
            String bankId = Strings.nullToEmpty(request.getParameter("BANKID"));//银行编码
            if (!Strings.isNullOrEmpty(bankId)) {
                submitWebGatePay.setBankId(bankId);
            }
            String unifyId = Strings.nullToEmpty(request.getParameter("UNIFYID"));//产品号码
            if (!Strings.isNullOrEmpty(unifyId)) {
                submitWebGatePay.setUnifyId(unifyId);
            }
            String upReqTranSeq = Strings.nullToEmpty(request.getParameter("UPREQTRANSEQ"));//网关平台请求银行流水号
            if (!ValidationFieldUtil.checkChar(upReqTranSeq)) {
                log.info("非法请求！网关平台流水号upReqTranSeq:{}非法！", upReqTranSeq);
                return null;
            } else {
                submitWebGatePay.setUpReqTranSeq(upReqTranSeq);
            }
            String upBankTranSeq = Strings.nullToEmpty(request.getParameter("UPBANKTRANSEQ"));//银行流水号
            if (!ValidationFieldUtil.checkChar(upBankTranSeq)) {
                log.info("非法请求！银行流水号upBankTranSeq:{}非法！", upBankTranSeq);
                return null;
            } else {
                submitWebGatePay.setUpBankTranSeq(upBankTranSeq);
            }
            log.info(submitWebGatePay.toString());
            response.getWriter().write("UPTRANSEQ_" + upTranSeq);
            log.info("给支付网关的响应:=====UPTRANSEQ_" + upTranSeq);
            webGatePayService.webGateBack(submitWebGatePay);
        } catch (Exception e) {
            log.error("fail to web gate pay  cause by {}", Throwables.getStackTraceAsString(e));
        }
        log.info("--------------网关支付后台通知结束-----------------");
        return null;
    }

    /**
     * 网关充值后台通知
     *
     * @throws Exception
     */
    @RequestMapping(value = "/accountRechargeConfirm", method = RequestMethod.POST)
    public String webGateAccountRechargeConfirm(HttpServletRequest request,
                                                HttpServletResponse response) throws Exception {
        log.info("----------------网关充值后台通知开始----------------");
        try {
            SubmitWebGatePay submitWebGatePay = new SubmitWebGatePay();
            String upTranSeq = Strings.nullToEmpty(request.getParameter("UPTRANSEQ"));//翼支付网关平台交易流水号
            if (!ValidationFieldUtil.checkChar(upTranSeq)) {
                log.info("非法请求！网关支付流水upTranSeq:{}非法！", upTranSeq);
                return null;
            } else {
                submitWebGatePay.setUpTranSeq(upTranSeq);
            }
            String merchantId = Strings.nullToEmpty(request.getParameter("MERCHANTID"));//渠道号
            if (!ValidationFieldUtil.checkChar(merchantId)) {
                log.info("非法请求！渠道号 merchantId:{}非法 ！", merchantId);
                return null;
            } else {
                submitWebGatePay.setMerchantId(merchantId);
            }
            String traceSeqNo = Strings.nullToEmpty(request.getParameter("TRACESEQNO"));//订单请求交易流水号
            if (!ValidationFieldUtil.checkChar(traceSeqNo)) {
                log.info("非法请求！缴费订单流水 traceSeqNo:{} 非法！", traceSeqNo);
                return null;
            } else {
                submitWebGatePay.setTraceSeqNo(traceSeqNo);
            }
            String orderId = Strings.nullToEmpty(request.getParameter("ORDERID"));//订单号
            if (!ValidationFieldUtil.checkChar(orderId)) {
                log.info("非法请求！缴费订单号orderId:{}非法！", orderId);
                return null;
            } else {
                submitWebGatePay.setOrderId(orderId);
            }
            String orderAmount = Strings.nullToEmpty(request.getParameter("ORDERAMT"));//订单总金额
            if (!ValidationFieldUtil.doublePattern.matcher(orderAmount).find()) {
                log.info("非法请求！订单总支付金额orderAmount:{}非法！", orderAmount);
                return null;
            } else {
                submitWebGatePay.setOrderAmt(Long.parseLong(orderAmount));
            }
            String realAmt = Strings.nullToEmpty(request.getParameter("REALAMT"));//产品金额
            if (!ValidationFieldUtil.doublePattern.matcher(realAmt).find()) {
                log.info("非法请求！实际金额realAmt:{}非法！", realAmt);
                return null;
            } else {
                submitWebGatePay.setRealAmt(Long.parseLong(realAmt));
            }
            String retnCode = Strings.nullToEmpty(request.getParameter("RETNCODE"));//处理结果码
            if (!ValidationFieldUtil.checkChar(retnCode)) {
                log.info("非法请求！返回CODE retnCode:{}非法！", retnCode);
                return null;
            } else {
                submitWebGatePay.setReturnCode(retnCode);
            }
            String retnInfo = Strings.nullToEmpty(request.getParameter("RETNINFO"));//处理结果码
            if (!ValidationFieldUtil.checkChar(retnCode)) {
                log.info("非法请求！返回信息 retnInfo:{}非法！", retnInfo);
                return null;
            } else {
                submitWebGatePay.setReturnInfo(retnInfo);
            }
            String tranDate = Strings.nullToEmpty(request.getParameter("STLMDATE"));//翼支付网关平台交易日期
            if (!ValidationFieldUtil.checkChar(tranDate)) {
                log.info("非法请求！支付日期tranDate:{}非法！", tranDate);
                return null;
            } else {
                submitWebGatePay.setTraceTime(tranDate);
            }
            String curType = Strings.nullToEmpty(request.getParameter("CURTYPE"));//币种
            if (!ValidationFieldUtil.checkChar(curType)) {
                log.info("非法请求！币种类型curType:{}非法！", curType);
                return null;
            } else {
                submitWebGatePay.setCurType(curType);
            }
            String encMode = Strings.nullToEmpty(request.getParameter("ENCMODE"));//加密方式
            if (!ValidationFieldUtil.checkChar(encMode)) {
                log.info("非法请求！加密方式类型encodeType:{}非法！", encMode);
                return null;
            } else {
                submitWebGatePay.setEncMode(encMode);
            }
            String sign = Strings.nullToEmpty(request.getParameter("MAC"));//数字签名
            if (!ValidationFieldUtil.checkChar(sign)) {
                log.info("非法请求！加密串sign:{}非法！", sign);
                return null;
            } else {
                submitWebGatePay.setSign(sign);
            }
            String bankId = Strings.nullToEmpty(request.getParameter("BANKID"));//银行编码
            if (!Strings.isNullOrEmpty(bankId)) {
                submitWebGatePay.setBankId(bankId);
            }
            String unifyId = Strings.nullToEmpty(request.getParameter("UNIFYID"));//产品号码
            if (!Strings.isNullOrEmpty(unifyId)) {
                submitWebGatePay.setUnifyId(unifyId);
            }
            String upReqTranSeq = Strings.nullToEmpty(request.getParameter("UPREQTRANSEQ"));//网关平台请求银行流水号
            if (!ValidationFieldUtil.checkChar(upReqTranSeq)) {
                log.info("非法请求！网关平台流水号upReqTranSeq:{}非法！", upReqTranSeq);
                return null;
            } else {
                submitWebGatePay.setUpReqTranSeq(upReqTranSeq);
            }
            String upBankTranSeq = Strings.nullToEmpty(request.getParameter("UPBANKTRANSEQ"));//银行流水号
            if (!ValidationFieldUtil.checkChar(upBankTranSeq)) {
                log.info("非法请求！银行流水号upBankTranSeq:{}非法！", upBankTranSeq);
                return null;
            } else {
                submitWebGatePay.setUpBankTranSeq(upBankTranSeq);
            }
            log.info(submitWebGatePay.toString());
            response.getWriter().write("UPTRANSEQ_" + upTranSeq);
            log.info("给支付网关的响应:=====UPTRANSEQ_" + upTranSeq);
            webGatePayService.webGateAccountRechargeBack(submitWebGatePay);
        } catch (Exception e) {
            log.error("fail to web gate pay  cause by {}", Throwables.getStackTraceAsString(e));
        }
        log.info("--------------网关充值后台通知结束-----------------");
        //账户充值方法执行时间埋点
        return null;
    }

    /**
     * 网关支付前台回调
     */
    @RequestMapping(value = "/payCallback", method = RequestMethod.POST)
    public String payCallBack(HttpServletRequest request, Map<String, Object> context) {
        log.info("--------------网关支付前台回调开始-----------------");
        String UPTRANSEQ = request.getParameter("UPTRANSEQ") == null ? ""
                : request.getParameter("UPTRANSEQ");// 网关支付流水
        String MERCHANTID = request.getParameter("MERCHANTID") == null ? ""
                : request.getParameter("MERCHANTID");// 渠道号
        String TRACESEQNO = request.getParameter("TRACESEQNO") == null ? ""
                : request.getParameter("TRACESEQNO");// 缴费订单流水
        String ORDERID = request.getParameter("ORDERID") == null ? ""
                : request.getParameter("ORDERID");// 缴费订单号
        String ORDERAMT = request.getParameter("ORDERAMT") == null ? ""
                : request.getParameter("ORDERAMT");// 订单总支付金额（分）
        String RETNCODE = request.getParameter("RETNCODE") == null ? ""
                : request.getParameter("RETNCODE");// 支付返回CODE（0000：成功）
        String RETNINFO = request.getParameter("RETNINFO") == null ? ""
                : request.getParameter("RETNINFO");// 支付返回消息
        String UPREQTRANSEQ = request.getParameter("UPREQTRANSEQ") == null ? ""
                : request.getParameter("UPREQTRANSEQ");// 网关平台流水号
        String UPBANKTRANSEQ = request.getParameter("UPBANKTRANSEQ") == null ? ""
                : request.getParameter("UPBANKTRANSEQ");// 银行流水号
        log.info("网关平台流水号" + UPREQTRANSEQ + "银行流水号" + UPBANKTRANSEQ);
        log.info("网关前台回调接收方法======网关返回===网关流水:" + UPTRANSEQ + "===渠道号:"
                + MERCHANTID + "订单号=" + ORDERID + "===订单流水:" + TRACESEQNO
                + "===订单总金额:" + ORDERAMT);
        log.info("网关前台回调接收结果：" + RETNCODE + "\t" + RETNINFO);
        String returnUrl;
        if ("0000".equals(RETNCODE)) {
            returnUrl = "/layout/sutongpay/cashier/cashierPaySuc";
        } else {
            returnUrl = "/layout/sutongpay/cashier/cashierPayFail";
            context.put("RETNINFO", RETNINFO);
        }
        context.put("TRACESEQNO", TRACESEQNO);
        context.put("PRODUCTAMOUNT",ORDERAMT);
        context.put("ORDERAMOUNT",
                NumberUtil.convertYuanString(Long.parseLong(ORDERAMT)));
        log.info("--------------网关支付前台回调结束-----------------");
        return returnUrl;
    }



    /**
     * 网关充值前台回调
     */
    @RequestMapping(value = "/accountRechargeCallback", method = RequestMethod.POST)
    public String accountRechargeCallback(HttpServletRequest request, Map<String, Object> context) {
        log.info("--------------网关充值前台回调开始-----------------");
        String UPTRANSEQ = request.getParameter("UPTRANSEQ") == null ? ""
                : request.getParameter("UPTRANSEQ");// 网关支付流水
        String MERCHANTID = request.getParameter("MERCHANTID") == null ? ""
                : request.getParameter("MERCHANTID");// 渠道号
        String TRACESEQNO = request.getParameter("TRACESEQNO") == null ? ""
                : request.getParameter("TRACESEQNO");// 缴费订单流水
        String ORDERID = request.getParameter("ORDERID") == null ? ""
                : request.getParameter("ORDERID");// 缴费订单号
        String ORDERAMT = request.getParameter("ORDERAMT") == null ? ""
                : request.getParameter("ORDERAMT");// 订单总支付金额（分）
        String RETNCODE = request.getParameter("RETNCODE") == null ? ""
                : request.getParameter("RETNCODE");// 支付返回CODE（0000：成功）
        String RETNINFO = request.getParameter("RETNINFO") == null ? ""
                : request.getParameter("RETNINFO");// 支付返回消息
        String STLMDATE = request.getParameter("STLMDATE") == null ? ""
                : request.getParameter("STLMDATE");//支付日期
        String UNIFYID = request.getParameter("UNIFYID") == null ? ""
                : request.getParameter("UNIFYID");//充值的翼支付账户
        String UPREQTRANSEQ = request.getParameter("UPREQTRANSEQ") == null ? ""
                : request.getParameter("UPREQTRANSEQ");// 网关平台流水号
        String UPBANKTRANSEQ = request.getParameter("UPBANKTRANSEQ") == null ? ""
                : request.getParameter("UPBANKTRANSEQ");// 银行流水号
        log.info("网关平台流水号" + UPREQTRANSEQ + "银行流水号" + UPBANKTRANSEQ);
        log.info("网关充值前台回调接收方法======网关返回===网关流水:"
                + UPTRANSEQ + "===渠道号:" + MERCHANTID + "订单号=" + ORDERID + "===订单流水:"
                + TRACESEQNO + "===订单总金额:" + ORDERAMT + "===充值的支付账号:" + UNIFYID);
        log.info("网关充值前台回调接收结果：" + RETNCODE + "\t" + RETNINFO);
        String returnUrl;
        if ("0000".equals(RETNCODE)) {
            returnUrl = "/layout/sutongpay/cashier/cashierAccountSuc";
        } else {
            returnUrl = "/layout/sutongpay/cashier/cashierAccountFail";
            context.put("RETNINFO", RETNINFO);
        }
        context.put("ORDERID", ORDERID);
        context.put("STLMDATE", STLMDATE);
        context.put("UNIFYID", UNIFYID);
        context.put("PRODUCTAMOUNT",ORDERAMT);
        context.put("ORDERAMOUNT",
                NumberUtil.convertYuanString(Long.parseLong(ORDERAMT)));
        log.info("--------------网关充值前台回调结束-----------------");
        return returnUrl;
    }

    /**
     * 网关账户充值
     */
    @RequestMapping(value = "/recharge", method = RequestMethod.GET)
    public String toRecharge(HttpServletRequest request, Map<String, Object> context) {
        //账户充值笔数风控埋点
        SubmitWebGatePay submitWebGatePay = new SubmitWebGatePay();
        CommonUser user = UserUtil.getCurrentUser();
        submitWebGatePay.setUnifyId(user.getUnifyId());
        submitWebGatePay.setCustomerId(user.getId().toString());
        submitWebGatePay.setFeeType(Constants.PORTORDERBASE_1001);
        String clientIp = HttpUtil.getIpAddr(request);
        submitWebGatePay.setClientIp(clientIp);
        submitWebGatePay.setOrderAmt(1L);

        Response<SubmitWebGatePay> result = webGatePayService.accountRecharge(submitWebGatePay);
        if (result.isSuccess()) {
            submitWebGatePay = result.getResult();
            submitWebGatePay.setRealAmt(1L);
            submitWebGatePay.setAttachAmt(0L);
            submitWebGatePay.setCustomerId(user.getUnifyId());
            context.put("payInfo", submitWebGatePay);
            setContextPath("/layout/sutongpay/payment/webgate", context);
            return "/layout/sutongpay/payment/webgate";
        } else {
            String errorCode = result.getErrorCode();
            String message = BussErrorCode.explain(errorCode);
            context.put("ex", new RuntimeException(message));
            return "error";
        }
    }

    private void setContextPath(String path, Map<String, Object> context) {
        Page page = new Page();
        page.setPath(path);
        context.put(RenderConstants.PAGE,page);
    }

}
