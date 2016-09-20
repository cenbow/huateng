package com.huateng.p3.account.commonservice;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.enummodel.OrgType;
import com.huateng.p3.account.component.CacheComponent;
import com.huateng.p3.account.persistence.TParaMerchantRefundRuleMapper;
import com.huateng.p3.account.persistence.models.TInfoOrg;
import com.huateng.p3.account.persistence.models.TLogOnlinePaymentHis;
import com.huateng.p3.account.persistence.models.TParaMerchantRefundRule;

/**
 * Created with IntelliJ IDEA.
 * User: JamesTang
 * Date: 13-12-6
 * Time: 上午8:23
 * To change this template use File | Settings | File Templates.
 */

@Component
public class OrgService {
    @Autowired
    private CacheComponent cacheComponent;
    
    @Autowired
    private TParaMerchantRefundRuleMapper tParaMerchantRefundRuleMapper;

    @Autowired
    private TxnCheckService txnCheckService;
    
    


    public TInfoOrg getValidOrg(String orgCode, String  txntype,String orgtype ,boolean... isNoCheck) {
        TInfoOrg org = cacheComponent.getOrgObj(orgCode);       
        txnCheckService.checkOrgPayment(org,txntype,orgtype,isNoCheck);
        return  org;
    }
    
    
    public TParaMerchantRefundRule getMerchantRefundRule(String orgCode,PaymentInfo paymentInfo,TLogOnlinePaymentHis tLogOnlinePaymentHis , String orgtype) {
    	TParaMerchantRefundRule tParaMerchantRefundRule = tParaMerchantRefundRuleMapper.findMerchantRefundRule(orgCode, DateTime.now().toDate());        
    	txnCheckService.checkMerchantRefundRule(tParaMerchantRefundRule,paymentInfo,tLogOnlinePaymentHis);    
    	if(OrgType.ORG_TYPE_MERCHANT.getOrgtype().equals(orgtype)){
    		paymentInfo.setSupplyFeeFlag(tParaMerchantRefundRule.getFeeFlag());   		
    	}else{    		
    		paymentInfo.setPayFeeFlag(tParaMerchantRefundRule.getFeeFlag());   
    	}
    	
    	return  tParaMerchantRefundRule;
    }


}
