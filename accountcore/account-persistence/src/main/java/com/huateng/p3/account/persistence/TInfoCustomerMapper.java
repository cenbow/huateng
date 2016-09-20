package com.huateng.p3.account.persistence;

import com.huateng.p3.account.persistence.models.TInfoBankcard;
import com.huateng.p3.account.persistence.models.TInfoCustomer;

public interface TInfoCustomerMapper {
    int deleteByPrimaryKey(String customerNo);

    int insert(TInfoCustomer record);

    int insertSelective(TInfoCustomer record);

    TInfoCustomer findCustomerForUpdate(String customerNo);

    TInfoCustomer findCustomerByCustomerNo(String customerNo);
    
    TInfoCustomer findCustomerByCustomerNoForUpdate(String customerNo);

    TInfoCustomer findCustomerByCustomerNoIgnoreStatus(String customerNo);

    TInfoCustomer findCustomerByUnifyId(String unifyId);

    TInfoCustomer findCustomerByUnifyIdForUpdate(String unifyId);

    TInfoCustomer findCustomerByAccountNo(String accountNo);

    TInfoCustomer selectByPrimaryKey(String customerNo);

    int updateByPrimaryKeySelective(TInfoCustomer record);

    int updateByPrimaryKeyWithBLOBs(TInfoCustomer record);

    int updateByPrimaryKey(TInfoCustomer record);
      
    int updateWithSynchronize(TInfoCustomer record);    

    TInfoCustomer findCustomerByCardNo(String cardNo);

    /**
     * 检查统一帐号是否已经在当日注销过
     *
     * @param unifyId
     * @return
     */
    TInfoCustomer checkClosedCustomerByUnifyId(String unifyId);

    /**
     * 销户
     *
     * @param tInfoCustomer
     * @return
     */
    int closeCustomer(TInfoCustomer tInfoCustomer);

    TInfoCustomer findBlackCustomer(String customerNo);

    TInfoCustomer findBlackCustomerForUpdate(String customerNo);
    
    /**
     * 根据账号查询客户信息
     * 
     * @param accountNo
     * @return
     */
    TInfoCustomer findCustomerByAccountNoForUpdate(String accountNo);

    /**
     * 根据卡号查询客户信息
     * 
     * @param cardNo
     * @return
     */
    TInfoCustomer findCustomerByCardNoForUpdate(String cardNo);
    
   

}