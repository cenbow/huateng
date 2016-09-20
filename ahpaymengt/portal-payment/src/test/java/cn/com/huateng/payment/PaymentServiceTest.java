package cn.com.huateng.payment;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseSpringTest;
import cn.com.huateng.CommonUser;
import cn.com.huateng.common.BussErrorCode;
import cn.com.huateng.payment.model.TPortOrderBase;
import cn.com.huateng.payment.service.PaymentService;

import com.huateng.p3.account.common.enummodel.Paging;
import com.aixforce.user.base.BaseUser.TYPE;
import com.huateng.p3.component.Response;


public class PaymentServiceTest extends BaseSpringTest {
	private Logger log = LoggerFactory.getLogger(PaymentServiceTest.class);
	@Autowired
	private PaymentService paymentService;
	
	@Test  
	public void testAll() throws Exception{
	
	//	PaymentService1();
		PaymentService2();
	}
	
	
	 private void PaymentService1() throws Exception{


		 //	CommonUser	commonUser=new CommonUser(null,null,TYPE.ADMIN,"13524124277");
		 CommonUser	commonUser=new CommonUser(null,null,TYPE.ADMIN,"13524124279");	
		 	Response<Paging<TPortOrderBase>>res= paymentService.orderQuery(commonUser, null, null, 1, 2, "01", null);
	
		log.info("错误的unifyId返回错误代码"+res.getErrorCode()+"错误原因："+BussErrorCode.explain(res.getErrorCode()));  
			
	    }
	 
		
	 private void PaymentService2() throws Exception{


		 //	CommonUser	commonUser=new CommonUser(null,null,TYPE.ADMIN,"13524124277");
		 CommonUser	commonUser=new CommonUser(879879898890l,null,TYPE.ADMIN,"13524124279");	
		paymentService.orderQueryDetail(commonUser, "123123123", "01");
	
		//log.info("错误的unifyId返回错误代码"+res.getErrorCode()+"错误原因："+BussErrorCode.explain(res.getErrorCode()));  
			
	    }

	
}
	