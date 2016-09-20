package cn.com.huateng.payment.service;

import com.aixforce.annotations.ParamInfo;
import com.huateng.p3.account.common.enummodel.Paging;
import com.huateng.p3.account.persistence.models.TLogAccountPayment;
import com.huateng.p3.component.Response;
import cn.com.huateng.CommonUser;
import cn.com.huateng.payment.model.TPortOrderBase;
import cn.com.huateng.payment.model.TPortOrderDetail;

public interface PaymentService {

    /**
     * 交易管理-交易查询
     *
     * @param commonUser 公共用户对象
     * @param startDate   开始时间
     * @param endDate     结束时间
     * @param startIndex  开始条数
     * @param pageSize    页面条数
     * @return 如果查询成功, 则返回该订单列表对象, 否则返回失败原因
     *
     */
     Response<Paging<TPortOrderBase>> orderQuery(@ParamInfo("commonUser")  CommonUser commonUser,@ParamInfo("startDate") String startDate ,
    		 @ParamInfo("endDate") String endDate,@ParamInfo("startIndex") Integer startIndex,@ParamInfo("pageSize") Integer pageSize,
    		 @ParamInfo("feeType") String feeType,@ParamInfo("type") String type);

     /**
      * 交易管理-收支明细
      *
      * @param bestpayUser 公共用户对象  必选
      * @param txnType     交易类型  可选  默认null  传12为充值
      * @param startDate   开始时间  可选  默认null
      * @param endDate     结束时间  可选  默认null
      * @param startIndex  开始页数  可选  默认1
      * @param pageSize    页面条数  可选  默认10
      * @return 如果查询成功, 则返回该订单列表对象, 否则返回失败原因
      *
      */

    /**
     * 交易管理-已购卡号
     *
     * @param bestpayUser 公共用户对象
     * @param startDate   开始时间
     * @param endDate     结束时间
     * @param startIndex  开始页数
     * @param pageSize    页面条数
     * @return 如果查询成功, 则返回该订单列表对象, 否则返回失败原因
     *
     */
//    Response<Paging<TShopOrderBasic>> buyCardNumber(@ParamInfo("bestpayUser")  BestpayUser bestpayUser,@ParamInfo("startDate") String startDate ,
//      		 @ParamInfo("endDate") String endDate,@ParamInfo("startIndex") Integer startIndex,@ParamInfo("pageSize") Integer pageSize);

    /**
     * 交易管理-提现记录
     *
     * @param bestpayUser 公共用户对象
     * @param startDate   开始时间
     * @param endDate     结束时间
     * @param startIndex  开始页数
     * @param pageSize    页面条数
     * @return 如果查询成功, 则返回该订单列表对象, 否则返回失败原因
     *
     */
//    Response<Paging<TLogCashApply>> cashApply(@ParamInfo("bestpayUser")  BestpayUser bestpayUser,@ParamInfo("startDate") String startDate ,
//      		 @ParamInfo("endDate") String endDate,@ParamInfo("startIndex") Integer startIndex,@ParamInfo("pageSize") Integer pageSize);

    
    
    /**
     * 交易管理-已购卡号(明细)
     *
     * @param bestpayUser 公共用户对象
     * @param orderNo  订单号  必填
     * @param phone  必填
     * @return 如果查询成功, 则返回该订单明细列表对象, 否则返回失败原因
     *
     */
//    Response<List<TShopOrderDetailCard>> buyCardNumberDetail(@ParamInfo("bestpayUser")  BestpayUser bestpayUser,@ParamInfo("orderNo") String orderNo,@ParamInfo("phone") String phone);
    
    
    /**
     * 交易管理-交易查询(明细)
     *
     * @param commonUser 公共用户对象
     * @param orderNo  订单号  必填
     * @param feeType  业务类型 必填
     * @return 如果查询成功, 则返回该订单明细对象, 否则返回失败原因
     *
     */
     Response<TPortOrderDetail> orderQueryDetail(@ParamInfo("commonUser")  CommonUser commonUser,@ParamInfo("orderNo") String orderNo,@ParamInfo("feeType") String feeType);;


    /**
     * 加载手机钱包信息

     * @param startDate   开始时间  可选  默认null

     * @param startIndex  开始页数  可选  默认1
     * @param pageSize    页面条数  可选  默认10
     * @return 如果查询成功, 则返回该订单列表对象, 否则返回失败原因
     */


    public Response<Paging<TLogAccountPayment>> rechargeLogQuery(@ParamInfo("commonUser") CommonUser commonUser,@ParamInfo("startDate")  String startDate,@ParamInfo("endDate")  String endDate,@ParamInfo("startIndex")  Integer startIndex,@ParamInfo("pageSize") Integer pageSize);

    /**
     * 交易管理-收支明细
     *
     * @param commonUser 公共用户对象  必选
     * @param txnType     交易类型  可选  默认null 传12为充值
     * @param startDate   开始时间  可选  默认null
     * @param endDate     结束时间  可选  默认null
     * @param startIndex  开始页数  可选  默认1
     * @param pageSize    页面条数  可选  默认10
     * @return 如果查询成功, 则返回该订单列表对象, 否则返回失败原因
     */
    public Response<Paging<TLogAccountPayment>> paymentDetail(@ParamInfo("commonUser")CommonUser commonUser, @ParamInfo("txnType")String txnType, @ParamInfo("startDate")String startDate, @ParamInfo("endDate")String endDate, @ParamInfo("startIndex")Integer startIndex, @ParamInfo("pageSize")Integer pageSize);


    }
