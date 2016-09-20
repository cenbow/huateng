package com.huateng.p3.account.service;


import com.huateng.p3.component.Response;

/**
 * Desc: 外围的服务，处理账户和银行之间的交互
 * Author: dimzfw@gmail.com
 * Date: 12/3/13 11:21 AM
 */
public interface BankAccountService {


    /**
     * 通过银行充值到翼支付账户，cardNo只是用来记录
     *
     * @param accountNo 翼支付账户号
     * @param cardNo    银行卡卡号
     * @param amount    充值金额
     * @return 翼支付账户余额
     */
    Response<Long> charge(String accountNo, String cardNo, Long amount);

    /**
     * 翼支付账户提现到银行卡，cardNo只是用来记录
     *
     * @param accountNo 翼支付账户号
     * @param cardNo    银行卡卡号
     * @param amount    提现金额
     * @return 翼支付账户余额
     */
    Response<Long> withdraw(String accountNo, String cardNo, Long amount);

}
