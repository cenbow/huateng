package cn.com.huateng.account.service;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.huateng.CommonUser;
import cn.com.huateng.base.BaseSpringTest;
import cn.com.huateng.common.BussErrorCode;

import com.aixforce.user.base.BaseUser.TYPE;
import com.huateng.p3.component.Response;


public class PasswordMgmServiceTest extends BaseSpringTest {
	private Logger log = LoggerFactory.getLogger(PasswordMgmServiceTest.class);
	@Autowired
	private PasswordMgmService passwordMgmService;

	@Test
	public void testAll() throws Exception{
		//修改登录密码
		//testModifyLoginPasswdFail800001();
	//	testModifyLoginPasswdFail700001();
	//	testModifyLoginPasswdFail800002();
		 
		//testModifyLoginPasswdOk();

	}
	
	/**
     * 修改登录密码
     * @return 如果操作成功,则返回SUCCESS,否则返回失败原因
     */
	public void testModifyLoginPasswdFail800001() throws Exception{
		//空的参数信息
		CommonUser commonUser=new CommonUser();
		String oldLoginPassword="";
		String newLoginPassword="";
		Response<String> actual = passwordMgmService.modifyLoginPasswd(commonUser, oldLoginPassword, newLoginPassword);	
		assertThat(actual.isSuccess(),is(false));
		assertThat(actual.getErrorCode(),is("800001"));
		log.info("用户为空，新旧密码为空时返回，错误代码"+actual.getErrorCode()+"错误原因："+BussErrorCode.explain(actual.getErrorCode()));
	}
	public void testModifyLoginPasswdFail700001() throws Exception{
		//错误的原始密码
		CommonUser commonUser=new CommonUser(8630898000000257L, null, TYPE.SELLER, "13524124279");
		String oldLoginPassword="e10adc3949ba59abbe56e057f20f883e";
		String  newLoginPassword="112233";
		Response<String>  res = passwordMgmService.modifyLoginPasswd(commonUser, oldLoginPassword, newLoginPassword);	
		if("000000".equals(res.getResult())){
			log.info("成功");  
		}else{
		log.info("错误的unifyId返回错误代码"+res.getErrorCode()+"错误原因："+res.getErrorMsg()); 
		//log.info("正确的用户信息，错误的原始密码，错误代码"+actual.getErrorCode()+"错误原因："+BussErrorCode.explain(actual.getErrorCode()));
	}}
	public void testModifyLoginPasswdFail800002() throws Exception{
		CommonUser commonUser=new CommonUser(8630898000000257L, null,  TYPE.SELLER, null, "8631898000000258");
		String  oldLoginPassword="123456";
		String  newLoginPassword="  ";
		Response<String> 	actual = passwordMgmService.modifyLoginPasswd(commonUser, oldLoginPassword, newLoginPassword);	
		assertThat(actual.isSuccess(),is(false));
		assertThat(actual.getErrorCode(),is("800002"));
		log.info("正确的用户信息，正确的原始密码,空格的新密码，修改登录密码操作失败，错误代码"+actual.getErrorCode()+"错误原因："+BussErrorCode.explain(actual.getErrorCode()));

	}
	
	public void testModifyLoginPasswdOk() throws Exception{
		CommonUser commonUser=null;
		String oldLoginPassword="";
		String newLoginPassword="";
		commonUser=new CommonUser(8630898000000388L, null,  TYPE.SELLER,  "123123123");
		oldLoginPassword="111111";
		newLoginPassword="e10adc3949ba59abbe56e057f20f883e";
		Response<String> res = passwordMgmService.modifyLoginPasswd(commonUser, oldLoginPassword, newLoginPassword);	
		if("000000".equals(res.getResult())){
			log.info("成功");  
		}else{
		log.info("错误的unifyId返回错误代码"+res.getErrorCode()+"错误原因："+res.getErrorMsg()); 
		//		assertThat(actual.isSuccess(),is(true));
//		log.info("正确的用户信息，正确的原始密码,新密码，修改登录密码操作成功"+BussErrorCode.explain(actual.getErrorCode()));
		
		/*oldLoginPassword="123456";
		newLoginPassword="111111";
		actual = passwordMgmService.modifyLoginPasswd(bestpayUser, oldLoginPassword, newLoginPassword);	
		assertThat(actual.isSuccess(),is(true));
		log.info("正确的用户信息，正确的原始密码,新密码，修改登录密码操作成功");*/
	}}
	
	
}
