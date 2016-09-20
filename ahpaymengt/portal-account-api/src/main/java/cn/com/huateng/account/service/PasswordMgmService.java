package cn.com.huateng.account.service;

import cn.com.huateng.CommonUser;
import com.aixforce.annotations.ParamInfo;
import com.huateng.p3.component.Response;

/**
 * 修改登陆密码，修改支付密码，重置支付密码，重置密保问题接口
 * @date 2013-08-02
 * @author huwenjie
 * @copyright huateng
 */
public interface PasswordMgmService {
	 /**
     * 修改登录密码
     * @param CommonUser 公共用户对象  必填
     * @param oldLoginPassword 老登录密码  必填
     * @param newLoginPassword 新登录密码  必填
     * @return 如果操作成功,则返回SUCCESS,否则返回失败原因
     */
	Response<String> modifyLoginPasswd(@ParamInfo("commonUser") CommonUser CommonUser, @ParamInfo("oldLoginPassword") String oldLoginPassword, @ParamInfo("newLoginPassword") String newLoginPassword);
	
	
	
	/**
     * 通过密保问题重置支付密码
     * @param commonUser 公共用户对象  必填
     * @param pwdAnswer 密保问题答案  必填
     * @param pwdQuestion 密保问题  必填
     * @param newPayPassword 新支付密码  必填
     * @return 如果操作成功,则返回SUCCESS,否则返回失败原因
     */
	 Response<String> resetPayPasswdByQuestion(@ParamInfo("commonUser") CommonUser commonUser, @ParamInfo("pwdQuestion") String pwdQuestion, @ParamInfo("pwdAnswer") String pwdAnswer, @ParamInfo("newPayPassword") String newPayPassword);
	
	 /**
     * 通过短信验证码重置支付密码
     * @param commonUser 公共用户对象  必填
     * @param newPayPassword 新支付密码  必填
     * @return 如果操作成功,则返回SUCCESS,否则返回失败原因
     */
	 Response<String> resetPayPasswdBySms(@ParamInfo("CommonUser") CommonUser commonUser, @ParamInfo("newPayPassword") String newPayPassword);

	 /**
     * 通过身份证重置支付密码
     * @param commonUser 公共用户对象  必填
     * @param realName 真实姓名  必填
     * @param idNo 身份证号码  必填
     * @param newPayPassword 新支付密码  必填
     * @return 如果操作成功,则返回SUCCESS,否则返回失败原因
     */
	 Response<String> resetPayPasswdByIdNo(@ParamInfo("commonUser") CommonUser commonUser, @ParamInfo("realName") String realName, @ParamInfo("idNo") String idNo, @ParamInfo("newPayPassword") String newPayPassword);

	
	
	 /**
     * 通过支付密码重置密码保护问题
     * @param commonUser 公共用户对象  必填
     * @param payPassword 支付密码  必填
     * @param question 密保问题  必填
     * @param answer  密保问题答案  必填
     * @return 如果操作成功,则返回SUCCESS,否则返回失败原因
     */
	 Response<String> resetPasswdQuestionByTxnPwd(@ParamInfo("commonUser") CommonUser commonUser, @ParamInfo("payPassword") String payPassword, @ParamInfo("question") String question, @ParamInfo("answer") String answer);
	
	 
	 /**
     * 通过短信验证码重置密码保护问题
     * @param commonUser 公共用户对象  必填
     * @param question 密保问题  必填
     * @param answer  密保问题答案  必填
     * @return 如果操作成功,则返回SUCCESS,否则返回失败原因
     */
	 Response<String> resetPasswdQuestionBySms(@ParamInfo("commonUser") CommonUser commonUser, @ParamInfo("question") String question, @ParamInfo("answer") String answer);

    /**
     *     账户支付密码重置
     * @param commonUser
     * @return
     */
    Response<String> resetTxnPasswd(@ParamInfo("commonUser") CommonUser commonUser,String newPassword);

    /**
     *    账户支付密码修改
     * @param commonUser
     * @param newPassword
     * @param oldPassword
     * @return
     */
    Response<String> modfiyTxnPasswd(@ParamInfo("commonUser") CommonUser commonUser,String newPassword,String oldPassword);

    /**
     * 密码修改重置判断
     * @param commonUser 公共用户对象  必填
     * @param resetType 重置类型  必填 0(修改密码)  1(密保问题重置)  2(短信验证码重置) 3(身份证重置 )
     * @return 如果操作成功,则返回SUCCESS,否则返回失败原因
     */
    Response<String> resetPayPasswdDetect(@ParamInfo("CommonUser")  CommonUser commonUser,@ParamInfo("resetType") String resetType);

    /**
     * 验证支付密码
     * @param commonUser
     * @param payPassword
     * @return
     */
    public  Response<String>  checkTxnPasswd(CommonUser commonUser,String payPassword);
}
