package com.huateng.p3.account.component;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.RiskQueryObject;
import com.huateng.p3.account.persistence.models.TInfoAccount;
@Component
public class RiskQueryComponent {

	public RiskQueryObject genRiskQueryResultObject(TInfoAccount account, PaymentInfo paymentInfo ,List<Long>  amountList ,List<Long>  timesList) {
		RiskQueryObject riskQueryObject = new RiskQueryObject();
    	riskQueryObject.setAccountNo(account.getAccountNo());
    	riskQueryObject.setGrade(account.getGrade());
    	riskQueryObject.setType(account.getGrade());   	
    	riskQueryObject.setBussinessType(paymentInfo.getBussinessType());
    	riskQueryObject.setChannel(paymentInfo.getChannel());
    	riskQueryObject.setInnerTxnType(paymentInfo.getInnerTxnType());
    	riskQueryObject.setTradableAmount(Collections.min(amountList)>0 ? Collections.min(amountList):0);
    	riskQueryObject.setTradeableTimes(Collections.min(timesList)>0 ? Collections.min(timesList):0);
    	return riskQueryObject;
    }
	
	
	
}
