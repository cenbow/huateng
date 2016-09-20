package com.huateng.p3.account.persistence;


import com.huateng.p3.account.persistence.models.TLogAccountTxnPayment;

import java.util.Map;

/**
 *
 */
public interface TLogAccountTxnPaymentMapper {
    int insert(TLogAccountTxnPayment record);

    int insertSelective(TLogAccountTxnPayment record);

    int updateAccountTxnInfoByAccountnoAndTxntype(TLogAccountTxnPayment tLogAccountTxnPayment);

    TLogAccountTxnPayment getAccountTxnInfoByAccountnoAndTxntype(Map paraMap);

}