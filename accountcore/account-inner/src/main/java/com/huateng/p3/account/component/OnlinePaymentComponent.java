package com.huateng.p3.account.component;

import java.util.List;

import org.springframework.stereotype.Component;

import com.huateng.p3.account.common.bizparammodel.LogOnlinePaymentObject;
import com.huateng.p3.account.common.bizparammodel.TxnQueryObj;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.common.util.BeanMapper;
import com.huateng.p3.account.persistence.models.TLogOnlinePayment;
import com.huateng.p3.account.persistence.models.TLogOnlinePaymentHis;
@Component
public class OnlinePaymentComponent {

	public List<LogOnlinePaymentObject> genLogOnlinePaymentResultObject(List<TLogOnlinePayment> tLogOnlinePay,TxnQueryObj txnQueryObj,Integer total) {
		if(tLogOnlinePay ==null || tLogOnlinePay.size() ==0){
			throw new BizException(BussErrorCode.ERROR_CODE_500101.getErrorcode(),
                    BussErrorCode.ERROR_CODE_500101.getErrordesc());
		}
		List<LogOnlinePaymentObject> list =BeanMapper.mapList(tLogOnlinePay, LogOnlinePaymentObject.class);
		//设置商户名
		for(LogOnlinePaymentObject logOnlinePaymentObject: list){			
			logOnlinePaymentObject.setMerchantName(txnQueryObj.getMerchantName());
			logOnlinePaymentObject.setTotal(total);
		}	
		
        return list;
    }
	
	
	public List<LogOnlinePaymentObject> genLogOnlinePaymentHisResultObject(List<TLogOnlinePaymentHis> tLogOnlinePayHis,TxnQueryObj txnQueryObj,Integer total) {
		if(tLogOnlinePayHis ==null || tLogOnlinePayHis.size() ==0){
			throw new BizException(BussErrorCode.ERROR_CODE_500101.getErrorcode(),
                    BussErrorCode.ERROR_CODE_500101.getErrordesc());
		}
		List<LogOnlinePaymentObject> list =BeanMapper.mapList(tLogOnlinePayHis, LogOnlinePaymentObject.class);
		//设置商户名
		for(LogOnlinePaymentObject logOnlinePaymentObject: list){			
			logOnlinePaymentObject.setMerchantName(txnQueryObj.getMerchantName());
			logOnlinePaymentObject.setTotal(total);
		}			
        return list;
    }
}
