package com.huateng.p3.account.service;


import com.huateng.p3.component.Response;

/**
 * Desc: 理财相关的服务接口，包括理财账户充值和赎回；每人会有多个理财账户；
 * Author: dimzfw@gmail.com
 * Date: 12/3/13 11:30 AM
 */
public interface FinanceAccountService {

    /**
     * 从翼支付账户充值到理财账户
     *
     * @param accountNo        翼支付账户号
     * @param financeAccountNo 理财账户号
     * @param amount           金额
     * @return 理财账户总余额
     */
    Response<Long> charge(String accountNo, String financeAccountNo, Long amount);

    /**
     * 从理财账户赎回到翼支付账户
     *
     * @param accountNo        翼支付账户号
     * @param financeAccountNo 理财账户号
     * @param amount           金额
     * @return 理财账户总余额
     */
    Response<Long> withdraw(String accountNo, String financeAccountNo, Long amount);
}
