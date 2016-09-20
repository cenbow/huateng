package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TInfoOtapwdTransfer;

public interface TInfoOtapwdTransferMapper {
    int deleteByPrimaryKey(String cardNo);

    int insert(TInfoOtapwdTransfer record);

    int insertSelective(TInfoOtapwdTransfer record);

    TInfoOtapwdTransfer selectByPrimaryKey(String cardNo);

    int updateByPrimaryKeySelective(TInfoOtapwdTransfer record);

    int updateByPrimaryKey(TInfoOtapwdTransfer record);

    int  updateOtaPasswdCardNoCloseStatus(String accountNo);
}