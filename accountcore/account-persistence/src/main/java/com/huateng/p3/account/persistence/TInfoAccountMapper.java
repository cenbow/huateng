package com.huateng.p3.account.persistence;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huateng.p3.account.persistence.models.TInfoAccount;

public interface TInfoAccountMapper {
    int deleteByPrimaryKey(String accountNo);

    int insert(TInfoAccount record);

    int insertSelective(TInfoAccount record);

    TInfoAccount selectByPrimaryKey(String accountNo);

    int updateByPrimaryKeySelective(TInfoAccount record);
    
    int updateAccountRealnameInfo(TInfoAccount record);

    int updateByPrimaryKey(TInfoAccount record);

    int updateBalanceAndPwdErr(TInfoAccount record);

    int updateAccountBalance(TInfoAccount record);
    
    int updateAccountStatus(TInfoAccount record);

    TInfoAccount findFundAccountByUnifyIdForUpdate(String unifyId);

    TInfoAccount findFundAccountByAccountNoForUpdate(String accountNo);

    TInfoAccount findFundAccountByCardNoForUpdate(String cardNo);

    TInfoAccount findFundAccountByCardNo(String cardNo);

    TInfoAccount findFundAccountByCustomerNoForUpdate(String customerNo);
    
    TInfoAccount findFundAccountByOrgCodeForUpdate(String orgcode);
    
    TInfoAccount  findFundAccountByOrgCode(String orgcode);

    TInfoAccount  findFundAccountByCustomerNo(String customerNo);

    TInfoAccount findIntegralAccountByCustomerNo(String customerNo);

    TInfoAccount  findFundAccountByAccountNo(String accountNo);

    TInfoAccount findFundAccountByUnifyId(String unifyId);


    int increaseTxnPasswdErrorNum(@Param("passwdErrNum") Long passwdErrNum,@Param("passwdErrLockTime") Date passwdErrLockTime,@Param("accountNo") String accountNo);

    TInfoAccount findIncludeClosedByAccountNoForUpdate(String accountNo);

    TInfoAccount findOfflineAccountByCardNoWithClosedForUpdate(@Param("cardNo") String cardNo,
                                                           @Param("acceptTransDate") String acceptTransDate,
                                                           @Param("acceptTransTime") String acceptTransTime
                                                           );

    int resetPasswdLockInfo(@Param("lastUpdateTime") Date lastUpdateTime,@Param("accountNo") String accountNo);

    List<TInfoAccount> findAccountsAsListByCustomerNo(@Param("customerNo") String customerNo,@Param("type") String type);

    List<TInfoAccount> findAccountsAsListByCardNo(@Param("cardNo") String customerNo,@Param("type") String type);






}