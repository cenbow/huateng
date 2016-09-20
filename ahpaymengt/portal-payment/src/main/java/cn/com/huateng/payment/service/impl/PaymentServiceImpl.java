package cn.com.huateng.payment.service.impl;


import java.math.BigDecimal;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.com.huateng.common.OrderStatus;
import cn.com.huateng.payment.dao.*;
import cn.com.huateng.payment.model.*;
import cn.com.huateng.payment.model.TDictAreaCity;

import cn.com.huateng.payment.util.PaymentInfoLogCom;
import com.ctc.wstx.util.DataUtil;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.TxnQueryObj;
import com.huateng.p3.account.common.enummodel.KeyType;
import com.huateng.p3.account.common.enummodel.Paging;
import com.huateng.p3.account.persistence.models.TLogAccountPayment;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.service.HubAccountQueryService;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import cn.com.huateng.CommonUser;
import cn.com.huateng.common.BussErrorCode;
import cn.com.huateng.payment.service.PaymentService;
import cn.com.huateng.util.DateUtil;
import cn.com.huateng.util.ValidationFieldUtil;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final static Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private TPortOrderBaseMapper tPortOrderBaseMapper;

    @Autowired
    private WebGatePayMapper webGatePayMapper;

    @Autowired
    private VirtualGoodsMapper virtualGoodsMapper;

    @Autowired
    private TDictAreaCityMapper tDictAreaCityMapper;

    @Autowired
    private SaleReturnApplyMapper saleReturnApplyMapper;

    @Autowired
    private HubAccountQueryService hubAccountQueryService;

    @Autowired
    PaymentInfoLogCom paymentInfoLogCom;


    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd");

    /**
     * 交易管理-交易查询
     *
     * @param commonUser 公共用户对象
     * @param startDate  开始时间 可选
     * @param endDate    结束时间  可选
     * @param startIndex 开始条数  必填  默认是1
     * @param pageSize   页面条数  必填  默认是10
     * @return 如果查询成功, 则返回该订单列表对象, 否则返回失败原因
     */
    @Override
    public Response<Paging<TPortOrderBase>> orderQuery(CommonUser commonUser, String startDate, String endDate, Integer startIndex, Integer pageSize, String feeType, String type) {
        //必填判断
        if (commonUser == null || Strings.isNullOrEmpty(commonUser.getUnifyId())) {
            return new Response<Paging<TPortOrderBase>>(BussErrorCode.ERROR_CODE_800001.getErrorcode());
        }
        //如果 开始和结束日期都是空 则为本月
        if (Strings.isNullOrEmpty(startDate) && Strings.isNullOrEmpty(endDate)) {
            //如果是账户首页的查询，查最近三十天
            if ("index".equals(type)) {
                long beforeDate = DateUtil.getDate().getTime() / 1000 - 24 * 60 * 60 * 30;
                Date myDate = new Date();
                myDate.setTime(beforeDate * 1000);
                startDate = DateUtil.formatDate(myDate, "yyyy-MM-dd");
            } else {//如果不是账户首页的查询
                startDate = DateUtil.getDateYYYYMMDD().substring(0, 8) + "01";
            }
            endDate = DateUtil.getDateYYYYMMDD();
        }
        //所有查询类型
        if (Strings.isNullOrEmpty(feeType)) {

            feeType = "01,02,03,05";
        }

        try {
            TPortOrderBase tPortOrderBase = new TPortOrderBase();
            startIndex = Objects.firstNonNull(startIndex, 1);
            pageSize = Objects.firstNonNull(pageSize, 10);
            startIndex = (startIndex - 1) * pageSize;
            Integer endIndex = startIndex + pageSize;
            tPortOrderBase.setStartDate(!Strings.isNullOrEmpty(startDate) ? DATE_TIME_FORMAT.parseDateTime(startDate).toDate() : null);
            tPortOrderBase.setEndDate(!Strings.isNullOrEmpty(endDate) ? DATE_TIME_FORMAT.parseDateTime(endDate).plusDays(1).toDate() : null);
            tPortOrderBase.setUnifyId(commonUser.getUnifyId());
            tPortOrderBase.setFeeType(!Strings.isNullOrEmpty(feeType) ? feeType : null);
            tPortOrderBase.setStartIndex(startIndex);
            tPortOrderBase.setEndIndex(endIndex);

            //列表查询
            List<TPortOrderBase> tPortOrderBaseList = tPortOrderBaseMapper.findTPortOrderBase(tPortOrderBase);
            //System.out.println("tPortOrde" + tPortOrderBaseList.size());
            Long total = tPortOrderBaseMapper.findCountTPortOrderBase(tPortOrderBase);
            //System.out.println("tPortOrderBaseList" + total);
            Paging<TPortOrderBase> tPortOrderBasePagingList = new Paging<TPortOrderBase>(total, tPortOrderBaseList);
            return new Response<Paging<TPortOrderBase>>(tPortOrderBasePagingList);

        } catch (DataIntegrityViolationException e) {
            //数据库校验数据错误
            String errorCode;
            if (e.toString().contains("ORA-00001")) {
                errorCode = BussErrorCode.ERROR_CODE_900003.getErrorcode();
            } else {
                errorCode = BussErrorCode.ERROR_CODE_900007.getErrorcode();
            }
            log.error("failed to query order{}", e);
            return new Response<Paging<TPortOrderBase>>(errorCode);

        } catch (CannotAcquireLockException e) {

            log.error("failed to query order{}", e);
            return new Response<Paging<TPortOrderBase>>(BussErrorCode.ERROR_CODE_900006.getErrorcode());

        } catch (Exception e) {

            log.error("failed to query order{}", e);
            return new Response<Paging<TPortOrderBase>>(BussErrorCode.ERROR_CODE_999999.getErrorcode());

        }

    }

    @Override
    public Response<TPortOrderDetail> orderQueryDetail(CommonUser commonUser,
                                                       String orderNo, String feeType) {
        // TODO Auto-generated method stub
        //必填判断
        Response<TPortOrderDetail> response = new Response<TPortOrderDetail>();
        if (commonUser == null || commonUser.getId() == null || Strings.isNullOrEmpty(orderNo) || Strings.isNullOrEmpty(feeType)) {
            response.setErrorCode(BussErrorCode.ERROR_CODE_800001
                    .getErrorcode());
            return response;
        }

        //验证业务类型
        if (!ValidationFieldUtil.integerPattern.matcher(
                feeType).find()) {
            response.setErrorCode(BussErrorCode.ERROR_CODE_700301
                    .getErrorcode());
            return response;
        }

        TPortOrderDetail tPortOrderDetail;
        switch (feeType) {
            case "05":
                VirtualGoods virtualGoods = virtualGoodsMapper.findVirtualGoods(new VirtualGoods(orderNo));
                List<SaleReturnApply> saleReturnApplies = saleReturnApplyMapper.selectSaleReturnApplyByOrderNo(new SaleReturnApply(virtualGoods.getOrderNo()));
                virtualGoods.setSaleReturnApplies(saleReturnApplies);
                Long refundAmt = 0L;
                boolean isReturningGoods = false;
                for (SaleReturnApply saleReturnApply : saleReturnApplies) {
                    if (Objects.equal(saleReturnApply.getCheckFlag(), OrderStatus.ORDER_STATUS_11.getValue())) {//有订单在退货中
                        isReturningGoods = true;
                    }
                    refundAmt = refundAmt + saleReturnApply.getRefundAmt();
                }
                virtualGoods.setReturningGoods(isReturningGoods);
                if (refundAmt >= virtualGoods.getOrderAmt()) {
                    virtualGoods.setIsOrNotReturnGoods("1");
                }
                tPortOrderDetail = virtualGoods;
                tPortOrderDetail.setFeeType(feeType);
                break;
            case "1001":
                tPortOrderDetail = webGatePayMapper.selectByPrimaryKey(orderNo);
                tPortOrderDetail.setFeeType(feeType);
                break;
            default:
                response.setErrorCode(BussErrorCode.ERROR_CODE_700301
                        .getErrorcode());
                return response;
        }
        if (tPortOrderDetail == null) {
            response.setErrorCode(BussErrorCode.ERROR_CODE_800003.getErrorcode());
            return response;

        }
        if (tPortOrderDetail instanceof PublicFee) {
            TDictAreaCity tDictAreaCity = tDictAreaCityMapper.findAreaNameByCode(((PublicFee) tPortOrderDetail).getCityCode());
            if (tDictAreaCity != null) {
                ((PublicFee) tPortOrderDetail).setAreaName(tDictAreaCity.getParenetName());
                ((PublicFee) tPortOrderDetail).setCityName(tDictAreaCity.getCurrentName());
            }
        }

        response.setResult(tPortOrderDetail);
        return response;

    }

    /**
     * 交易管理-充值记录查询
     *
     * @param startDate  开始时间 可选
     * @param startIndex 开始条数  必填  默认是1
     * @param pageSize   页面条数  必填  默认是10
     * @return 如果查询成功, 则返回该订单列表对象, 否则返回失败原因
     */
    @Override
    public Response<Paging<TLogAccountPayment>> rechargeLogQuery(CommonUser commonUser, String startDate, String endDate, Integer startIndex, Integer pageSize) {

        Response<Paging<TLogAccountPayment>> response = new Response<Paging<TLogAccountPayment>>();
        //必填判断
        if (commonUser == null || Strings.isNullOrEmpty(commonUser.getUnifyId())) {
            return new Response<Paging<TLogAccountPayment>>(BussErrorCode.ERROR_CODE_800001.getErrorcode());
        }

        //如果 开始和结束日期都是空 则为本月
        if (Strings.isNullOrEmpty(startDate) && Strings.isNullOrEmpty(endDate)) {
            startDate = DateUtil.getDateYYYYMMDD().substring(0, 8) + "01";
            endDate = DateUtil.getDateYYYYMMDD();
        }

        startIndex = Objects.firstNonNull(startIndex, 1);
        pageSize = Objects.firstNonNull(pageSize, 10);
        //账户信息
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey(commonUser.getUnifyId());
        accountInfo.setKeyType(KeyType.UNIFY);

        TxnQueryObj txnQueryObj = new TxnQueryObj();
        txnQueryObj.setQueryType(1);    //充值记录
        txnQueryObj.setEndDate(!Strings.isNullOrEmpty(endDate) ? DATE_TIME_FORMAT.parseDateTime(endDate).plusDays(1).toDate() : null);
        txnQueryObj.setStartDate(!Strings.isNullOrEmpty(startDate) ? DATE_TIME_FORMAT.parseDateTime(startDate).toDate() : null);
        txnQueryObj.setTxnChannel("02");     //交易渠道为网上
        txnQueryObj.setPageSize(pageSize);
        txnQueryObj.setPage(startIndex);
        try {
            Response<Paging<TLogAccountPayment>> accountPaymentResponse = hubAccountQueryService.queryAccountPaymentRecord(accountInfo, txnQueryObj);

            if (accountPaymentResponse.isSuccess()) {
                log.info("queryAccountPaymentRecord success accountInfo:{} txnQueryObj:{}", accountInfo, txnQueryObj);
                return accountPaymentResponse;
            } else {
                log.error("fail to queryAccountPaymentRecord accountInfo:{} txnQueryObj:{} cause by {}", new Object[]{accountInfo, txnQueryObj, accountPaymentResponse.getErrorCode() + ":" + accountPaymentResponse.getErrorMsg()});
                return new Response<Paging<TLogAccountPayment>>(BussErrorCode.ERROR_CODE_600009.getErrorcode());
            }
        } catch (Exception e) {
            log.error("fail to queryAccountPaymentRecord cause by {}", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            return response;
        }


    }

    /**
     * 交易管理-收支明细
     *
     * @param commonUser 公共用户对象  必选
     * @param txnType    交易类型  可选  默认null 传12为充值
     * @param startDate  开始时间  可选  默认null
     * @param endDate    结束时间  可选  默认null
     * @param startIndex 开始页数  可选  默认1
     * @param pageSize   页面条数  可选  默认10
     * @return 如果查询成功, 则返回该订单列表对象, 否则返回失败原因
     */
    @Override
    public Response<Paging<TLogAccountPayment>> paymentDetail(CommonUser commonUser, String txnType, String startDate, String endDate, Integer startIndex, Integer pageSize) {
        Response<Paging<TLogAccountPayment>> response = new Response<Paging<TLogAccountPayment>>();
        if (commonUser == null || Strings.isNullOrEmpty(commonUser.getUnifyId())) {
            response.setErrorCode(BussErrorCode.ERROR_CODE_800001.getErrorcode());
            return response;
        }
        //如果 开始和结束日期都是空 则为本月
        if (Strings.isNullOrEmpty(startDate) && Strings.isNullOrEmpty(endDate)) {
            startDate = DateUtil.getDateYYYYMMDD().substring(0, 8) + "01";
            endDate = DateUtil.getDateYYYYMMDD();
        }

        long total = 0L;

        try {
            AccountInfo accountInfo = new AccountInfo();
            TxnQueryObj txnQueryObj = new TxnQueryObj();
            startIndex = Objects.firstNonNull(startIndex, 1);
            pageSize = Objects.firstNonNull(pageSize, 10);


            txnQueryObj.setPage(startIndex);
            txnQueryObj.setPageSize(pageSize);
            txnQueryObj.setQueryType(0);
            accountInfo.setAccountKey(commonUser.getUnifyId());
            accountInfo.setKeyType(KeyType.UNIFY);
            if (!Strings.isNullOrEmpty(startDate)) {
                txnQueryObj.setStartDate(!Strings.isNullOrEmpty(startDate) ? DATE_TIME_FORMAT.parseDateTime(startDate).toDate() : null);
            }
            if (!Strings.isNullOrEmpty(endDate)) {
                txnQueryObj.setEndDate(!Strings.isNullOrEmpty(endDate) ? DATE_TIME_FORMAT.parseDateTime(endDate).plusDays(1).toDate() : null);
            }

            response = hubAccountQueryService.queryPaymentDetails(accountInfo, txnQueryObj);
            if (response.isSuccess()) {
                log.info("paymentDetail success accountInfo:{} txnQueryObj:{}", accountInfo, txnQueryObj);
            } else {
                log.error("fail to paymentDetail accountInfo:{} txnQueryObj:{} cause by {}", new Object[]{accountInfo, txnQueryObj, response.getErrorCode() + ":" + response.getErrorMsg()});
            }
            return response;

        } catch (Exception e) {

            log.error("fail to paymentDetail ,", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999
                    .getErrorcode());
            return response;

        }
    }


}
