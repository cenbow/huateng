package cn.com.huateng.account.service;

import cn.com.huateng.CommonUser;


import cn.com.huateng.account.model.TInfoAccount;
import cn.com.huateng.account.model.TInfoCustomer;
import com.aixforce.annotations.ParamInfo;

import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.enummodel.Paging;
import com.huateng.p3.account.persistence.models.TInfoAccountBankCard;
import com.huateng.p3.account.persistence.models.TInfoBankcard;
import com.huateng.p3.component.Response;

import java.util.List;
import java.util.Map;


/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-07-24
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param password 用户密码  必填
     * @param loginIp  登录IP地址  必填
     * @return 如果登录成功,则返回该用户对象,否则返回失败原因
     */
    Response<TInfoCustomer> login(String unifyId, @ParamInfo("password") String password, @ParamInfo("loginIp") String loginIp);

    /**
     * 根据客户号查询用户
     *
     * @param primaryKey 主键,这里是unifyId
     * @return 如果查询成功,则返回该用户对象,否则返回失败原因
     */

    Response<TInfoCustomer> findUserByPK(String primaryKey);


    /**
     * 根据unifyId查询用户
     * @return
     */
    public Response<TInfoCustomer> findUser(@ParamInfo("commonUser") CommonUser commonUser) ;


    /**
     * 根据公共用户对象查询账户
     *
     * @param commonUser 公共用户对象
     * @return 如果查询成功,则返回该用户的账户对象,否则返回失败原因
     */
    Response<TInfoAccount> loadAccounts(@ParamInfo("commonUser") CommonUser commonUser);



    /**
     * 根据产品号查询用户
     *
     * @param unifyId 统一编号
     * @return 如果查询成功, 则返回该用户对象, 否则返回失败原因
     */

    Response<TInfoCustomer> findUserByUnifyId(@ParamInfo("unifyId") String unifyId);


    /**
     * 根据公共用户对象查询用户
     *
     * @param commonUser 公共用户对象
     * @return 如果查询成功,则返回该用户对象,否则返回失败原因
     */

    Response<TInfoCustomer> loadCustomer(@ParamInfo("commonUser") CommonUser commonUser);


    /**
     * 修改个人信息
     *
     * @param commonUser   公共用户对象
     * @return 如果修改成功,则返回该用户对象,否则返回失败原因
     */

    Response<String> modifyCustomerInfo(@ParamInfo("commonUser") CommonUser commonUser,@ParamInfo("gender")String gender,@ParamInfo("homeTelephone")String homeTelephone,@ParamInfo("officeTelephone")String officeTelephone,@ParamInfo("otherTelephone")String otherTelephone,@ParamInfo("contactAddress")String contactAddress);


    /**
     * 修改 客户信息
     *
     * @param tInfoCustomer 用户对象
     * @return 如果修改成功,则返回该用户对象,否则返回失败原因
     */

    Response<TInfoCustomer> modifyCustomer(@ParamInfo("tInfoCustomer") TInfoCustomer tInfoCustomer);

    /**
     * 找回登录密码
     * @return  如果找回成功
     */

    Response<String> findPassWord(@ParamInfo("commonUser") CommonUser commonUse,@ParamInfo("newPassword")String newPassword);


    Response<String> changeSecurityQuestionAndAnswer(@ParamInfo("commonUser") CommonUser commonUser, @ParamInfo("securityQuestion") String securityQuestion, @ParamInfo("securityAnswer") String securityAnswer);

    Response<String> activeCustomer(@ParamInfo("commonUser") CommonUser commonUser);

    Response<String> mobilePhoneBinding(@ParamInfo("commonUser") CommonUser commonUser, String mobileNo);
    Response<String> unMobilePhoneBinding(@ParamInfo("commonUser") CommonUser commonUser,String payPwd);

    Response<String> modifyMobilePhoneBinding(@ParamInfo("commonUser") CommonUser commonUser, String mobileNo,String payPwd);

    /**
     * 银行卡绑定
     * @return
     */
    Response<String> bindBankCard(@ParamInfo("commonUser") CommonUser commonUser, @ParamInfo("cardNo") String cardNo, @ParamInfo("name") String name, @ParamInfo("idNo") String idNo,@ParamInfo("mobileNo") String mobileNo,@ParamInfo("bankCode") String bankCode,@ParamInfo("cardType") String cardType);

    /**
     * 银行卡解绑
     * @return
     */
    Response<String> unBindBankCard(@ParamInfo("commonUser") CommonUser commonUser,@ParamInfo("cardNo") String  cardNo);

    /**
     * 查询客户绑定的银行卡
     * @return
     */
    Response<Paging<TInfoAccountBankCard>> querytAccountBankCardBinding(@ParamInfo("commonUser") CommonUser commonUser);
}


     
