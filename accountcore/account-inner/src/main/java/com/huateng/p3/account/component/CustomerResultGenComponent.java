package com.huateng.p3.account.component;

import org.springframework.stereotype.Component;

import com.huateng.p3.account.common.bizparammodel.CustomerResultObject;
import com.huateng.p3.account.common.bizparammodel.OpenCustomerResultObject;
import com.huateng.p3.account.common.enummodel.TrueOrFalse;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TInfoCustomer;

/**
 * User: huwenjie
 * Date: 14-1-21
 * Time: 上午11:13
 */
@Component
public class CustomerResultGenComponent {

    public CustomerResultObject genCustomerResultObject(TInfoAccount account,TInfoCustomer customer,TrueOrFalse cardHanding,TrueOrFalse shortcutCard) {
    	CustomerResultObject customerResultObject = new CustomerResultObject();
    	customerResultObject.setBalance(account.getBalance());
    	customerResultObject.setAvailableBalance(account.getAvailableBalance());
    	customerResultObject.setCustomerNo(customer.getCustomerNo());
    	customerResultObject.setIdNo(customer.getIdentifyNo());
    	customerResultObject.setIdType(customer.getIdentifyType());
    	customerResultObject.setIsCardHanding(cardHanding);
    	customerResultObject.setIsShortcutCard(shortcutCard);
    	customerResultObject.setIsRealname(customer.getIsRealname());
    	customerResultObject.setGrade(customer.getCustomerGrade());
    	customerResultObject.setName(customer.getName());
    	customerResultObject.setUnifyId(customer.getUnifyId());
    	customerResultObject.setStatus(account.getStatus()); 
    	customerResultObject.setAccountNo(account.getAccountNo());
    	customerResultObject.setQuestion(customer.getQuestion());
    	customerResultObject.setAnswer(customer.getAnswer());
    	customerResultObject.setInitPasswdModified(account.getInitPasswdModified());
    	customerResultObject.setRegisterType(customer.getRegisterType());
    	customerResultObject.setRegisterTime(customer.getRegisteDate());
    	customerResultObject.setType(customer.getCustomerType());
    	customerResultObject.setAreaCode(customer.getAreaCode());
    	customerResultObject.setCityCode(customer.getCityCode());
    	customerResultObject.setMobilePhone(customer.getMobileNo());
    	customerResultObject.setHomeTelephone(customer.getHomeTelephone());
    	customerResultObject.setOfficeTelephone(customer.getOfficeTelephone());
    	customerResultObject.setOtherTelephone(customer.getOtherTelephone());
    	customerResultObject.setPwdErrCount(customer.getPwdErrCount());
    	customerResultObject.setContactAddress(customer.getContactAddress());
    	customerResultObject.setLastSuccessLoginTime(customer.getLastSuccessLoginTime());
    	customerResultObject.setFrozenAmount(account.getFrozenAmount());
    	customerResultObject.setGender(customer.getGender());
        return customerResultObject;
    }
    
    
    public OpenCustomerResultObject genOpenCustomerResultObject(TInfoAccount account,TInfoCustomer customer,String initPinKey) {
    	OpenCustomerResultObject openCustomerResultObject = new OpenCustomerResultObject();
    	openCustomerResultObject.setAccountNo(account.getAccountNo());
    	openCustomerResultObject.setCustomerNo(customer.getCustomerNo());
    	openCustomerResultObject.setLoginPwd(initPinKey);
    	openCustomerResultObject.setTxnPwd(account.getInitPasswd());
        return openCustomerResultObject;
    }
   
}
