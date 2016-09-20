package com.huateng.p3.account.daomappertest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.base.CodeGeneratorService;
import com.huateng.p3.account.common.enummodel.SeqCoreTranType;
import com.huateng.p3.account.common.enummodel.SeqPlatBussKind;
import com.huateng.p3.account.common.enummodel.SeqPlatType;


public class SequenceTest  extends BaseSpringTest {
    private Logger   log= LoggerFactory.getLogger(SequenceTest.class);
	
	@Autowired
	private CodeGeneratorService codegeneratorservice;

	@Before  
    public void init(){  
    }  
      
    @After
    public void destory(){  
    }
    
    @Test  
    public void test() {    
    	try{
    		String chargeSeqNo = codegeneratorservice.generateSeq(
    				SeqPlatType.SEQ_TRANA_ACCOUNT_CODE.getPlatcode(),
    				SeqPlatBussKind.SEQ_TRANA_TYPE.getSeqkingcode(),
    				SeqCoreTranType.TRANS_SEQ_TYPE_NORMAL.getTrancoreseqtypecode(),
    				"S_MARKETTING_SEQ");
    	//	String seq=tsequenceproducemapper.produceSequence("S_CARD_GOODS_BASIC");
            log.info("seq==============================="+chargeSeqNo);
    		System.out.println("seq &&&&&&&&&&&&& length"+chargeSeqNo.length());
    		
    	}catch (Exception e) {
    		e.printStackTrace();
		}
    }
	
}
