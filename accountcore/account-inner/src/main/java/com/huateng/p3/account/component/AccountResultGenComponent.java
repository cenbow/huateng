package com.huateng.p3.account.component;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.huateng.p3.account.common.bizparammodel.AccountResultObject;
import com.huateng.p3.account.common.enummodel.AccountStatus;
import com.huateng.p3.account.persistence.models.TInfoAccount;


/**
 * User: huwenjie
 * Date: 14-1-22
 * Time: 上午11:14
 */
@Component
public class AccountResultGenComponent {

    public AccountResultObject genAccountResultObject(TInfoAccount account) {
    	AccountResultObject accountResultObject = new AccountResultObject();	
		accountResultObject.setAccountNo(account.getAccountNo());
		accountResultObject.setAccountName(account.getAccountName());
		accountResultObject.setType(account.getType());
		accountResultObject.setStatus(account.getStatus());
		// 账户状态为关闭、挂失、销户，不能查询
		if(!AccountStatus.ACCOUNT_STATUS_UNACTIVE.getStatus().equals(account.getStatus())
				&& !AccountStatus.ACCOUNT_STATUS_LOSTED.getStatus().equals(account.getStatus())
				&& !AccountStatus.ACCOUNT_STATUS_CLOSED.getStatus().equals(account.getStatus())){
			accountResultObject.setBalance(account.getBalance());
			accountResultObject.setLastDayBal(account.getLastDayBal());
			accountResultObject.setLastMonthBal(account.getLastMonthBal());
			accountResultObject.setAvailableBalance(account.getAvailableBalance());
			accountResultObject.setUnavailableBalance(account.getUnavailableBalance());
			accountResultObject.setWithdrawBalance(account.getWithdrawBalance());
			accountResultObject.setCardNo(account.getCardNo());
			accountResultObject.setCardType(account.getCardType());   			
		}
        return accountResultObject;
    }
    
    
    public List<AccountResultObject> genAccountListResultObject(List<TInfoAccount> accounts) {
    	List<AccountResultObject> list = Lists.newArrayList();
    	AccountResultObject accountResultObject;
    	for (TInfoAccount account :accounts) {
    		accountResultObject = new AccountResultObject();    		
    		accountResultObject.setAccountNo(account.getAccountNo());
    		accountResultObject.setAccountName(account.getAccountName());
    		accountResultObject.setType(account.getType());
    		accountResultObject.setStatus(account.getStatus());
    		// 账户状态为关闭、挂失、销户，不能查询
    		if(!AccountStatus.ACCOUNT_STATUS_UNACTIVE.getStatus().equals(account.getStatus())
					&& !AccountStatus.ACCOUNT_STATUS_LOSTED.getStatus().equals(account.getStatus())
					&& !AccountStatus.ACCOUNT_STATUS_CLOSED.getStatus().equals(account.getStatus())){
    			accountResultObject.setBalance(account.getBalance());
    			accountResultObject.setLastDayBal(account.getLastDayBal());
    			accountResultObject.setLastMonthBal(account.getLastMonthBal());
    			accountResultObject.setAvailableBalance(account.getAvailableBalance());
    			accountResultObject.setUnavailableBalance(account.getUnavailableBalance());
    			accountResultObject.setWithdrawBalance(account.getWithdrawBalance());
    			accountResultObject.setCardNo(account.getCardNo());
    			accountResultObject.setCardType(account.getCardType());   			
    		}
    		list.add(accountResultObject);
    	}
    	
        return list;
    }
    
   
}
