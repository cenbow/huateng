package com.huateng.p3.hub.dataprocess.interceptor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

/**
 * User: JamesTang
 * Date: 13-12-23
 * Time: 下午6:07
 */
@Component
@Slf4j
public class CurrenCyInvestNotifyCompnent {

//    @Autowired
//    private RabbitTemplateComponent rabbitTemplateComponent;
//	@Autowired
//	private TInfoProductPropertyMapper tInfoProductPropertyMapper;
//
//    public void InvestNotify(PaymentInfo paymentInfo, Object retObj) {
//        if (!(retObj instanceof Response)) {
//            return;
//        }
//        Response<TxnResultObject> retobj = (Response<TxnResultObject>) retObj;
//        if (!retobj.isSuccess()) {
//            return;
//        }
//        TxnResultObject txnResultObject = retobj.getResult();
//        //冲正的交易如果是伪造的交易无需发送
//        if (txnResultObject.isRollbackFake()) {
//            return;
//        }
//        if(!Strings.isNullOrEmpty(txnResultObject.getTxnSeqNo()) && !Strings.isNullOrEmpty(txnResultObject.getProductNo()) && !Strings.isNullOrEmpty(txnResultObject.getFundAccountNo()) && txnResultObject.getTxnAmount()>0){//交易流水号没有是为检查
//        	TInfoProductProperty tInfoProductProperty = tInfoProductPropertyMapper.queryProductPropertyRecordByConditions(txnResultObject.getProductNo(), ProductProperty.FINANCE_ACCOUNT.getCode(), null, null);       	
//    		// 用户开通理财产品属性
//    		if(null != tInfoProductProperty){   			
//    			BankingCoreMq bankingCoreMq = genBankingCoreResultObject(txnResultObject);
//    			log.info("bankingCoreMq send :" + bankingCoreMq);
//    	        //发送理财模块
//    	        rabbitTemplateComponent.convertAndSend(RabbitTemplateComponent.TYB_FINANCING,bankingCoreMq);
//    	        //手续费交易同步给理财模块
//    	        if(!Strings.isNullOrEmpty(txnResultObject.getFeeTxnSeqNo())){   	        	
//    	        	BankingCoreMq feeBankingCoreMq = genFeeBankingCoreResultObject(txnResultObject);
//        			log.info("feeBankingCoreMq send :" + feeBankingCoreMq);
//        	        //发送理财模块
//        	        rabbitTemplateComponent.convertAndSend(RabbitTemplateComponent.TYB_FINANCING,feeBankingCoreMq);    	        	
//    	        }   	        
//    		}       	
//        }
//        //转账
//        if(!Strings.isNullOrEmpty(txnResultObject.getTargetTxnSeqNo()) && !Strings.isNullOrEmpty(txnResultObject.getTargetProductNo()) ){
//        	TInfoProductProperty tInfoProductProperty = tInfoProductPropertyMapper.queryProductPropertyRecordByConditions(txnResultObject.getTargetProductNo(), ProductProperty.FINANCE_ACCOUNT.getCode(), null, null);        	
//    		// 用户开通理财产品属性
//    		if(null != tInfoProductProperty){
//		        BankingCoreMq targetBankingCoreMq = genTargetBankingCoreResultObject(txnResultObject);	
//		        log.info("targetBankingCoreMq send :" + targetBankingCoreMq);
//		        //发送理财模块
//		        rabbitTemplateComponent.convertAndSend(RabbitTemplateComponent.TYB_FINANCING,targetBankingCoreMq);
//    		}
//        }
//    }
//    
//    private BankingCoreMq genBankingCoreResultObject(TxnResultObject txnResultObject){
//    	
//    	BankingCoreMq bankingCoreMq = new BankingCoreMq();
//    	bankingCoreMq.setBestpayAccount(txnResultObject.getProductNo());
//    	bankingCoreMq.setTransAmount(String.valueOf(txnResultObject.getTxnAmount()));
//    	bankingCoreMq.setOrderNo(txnResultObject.getTxnSeqNo());
//    	bankingCoreMq.setOutBusinessType(txnResultObject.getOutType());
//    	bankingCoreMq.setTxnType(txnResultObject.getInnerType());
//    	bankingCoreMq.setOldOrderNo(txnResultObject.getOldTxnSeqNo());
//    	bankingCoreMq.setAvailableBalance(txnResultObject.getAvailableBalance());
//    	return bankingCoreMq;
//    }
//    
//    
//    private BankingCoreMq genFeeBankingCoreResultObject(TxnResultObject txnResultObject){
//    	
//    	BankingCoreMq bankingCoreMq = new BankingCoreMq();
//    	bankingCoreMq.setBestpayAccount(txnResultObject.getProductNo());
//    	bankingCoreMq.setTransAmount(String.valueOf(txnResultObject.getFeeTxnAmount()));
//    	bankingCoreMq.setOrderNo(txnResultObject.getFeeTxnSeqNo());
//    	bankingCoreMq.setOutBusinessType(txnResultObject.getFeeOutType());
//    	bankingCoreMq.setTxnType(txnResultObject.getFeeInnerType());
//    	bankingCoreMq.setAvailableBalance(txnResultObject.getAvailableBalance());
//    	return bankingCoreMq;
//    }
//    
//    private BankingCoreMq genTargetBankingCoreResultObject(TxnResultObject txnResultObject){
//    	
//    	BankingCoreMq bankingCoreMq = new BankingCoreMq();
//    	bankingCoreMq.setBestpayAccount(txnResultObject.getTargetProductNo());
//    	bankingCoreMq.setTransAmount(String.valueOf(txnResultObject.getTxnAmount()));
//    	bankingCoreMq.setOrderNo(txnResultObject.getTargetTxnSeqNo());
//    	bankingCoreMq.setOutBusinessType(txnResultObject.getTargetOutType());
//    	bankingCoreMq.setTxnType(txnResultObject.getTargetInnerType());    	
//    	bankingCoreMq.setAvailableBalance(txnResultObject.getTargetAvailableBalance());
//    	return bankingCoreMq;
//    }
    
}
