package com.huateng.p3.account.component;

import org.springframework.stereotype.Component;

import com.huateng.p3.account.common.bizparammodel.SecurityResultObject;
import com.huateng.p3.account.persistence.models.TDictCode;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TInfoCustomer;

/**
 * User: huwenjie
 * Date: 14-7-16
 * Time: 上午11:13
 */
@Component
public class SecurityResultGenComponent {

   
    public SecurityResultObject genSecurityResultObject(TInfoAccount account,TInfoCustomer customer,TDictCode tDictCode) {
    	SecurityResultObject securityResultObject = new SecurityResultObject();
    	securityResultObject.setCustomerNo(customer.getCustomerNo());
    	securityResultObject.setAccountNo(account.getAccountNo());
    	securityResultObject.setSecrurityAnwser(customer.getAnswer());
    	securityResultObject.setSecrurityQuestion(customer.getQuestion());
    	securityResultObject.setSecrurityQuestionDesc(tDictCode ==null?"":tDictCode.getCodeDesc());
        return securityResultObject;
    }
   
}
