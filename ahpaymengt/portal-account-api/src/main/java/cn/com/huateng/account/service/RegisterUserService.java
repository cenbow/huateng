package cn.com.huateng.account.service;


import cn.com.huateng.account.model.Register;
import com.aixforce.annotations.ParamInfo;
import com.huateng.p3.component.Response;

/**
 * 用户注册
 *
 * @author huwenjie
 * @date 2013-08-12
 */
public interface RegisterUserService {


    /**
     * 开户
     * productNo 手机号码  必填
     * idType    证件类型  必填
     * idNo      证件号  必填
     * name      名字 必填
     * gender    性别  必填
     * question  密保问题 必填
     * answer    密保答案 必填
     * passWd    非电信 用户 必填
     *
     * @return 如果开户成功, 则返回SUCCESS, 否则返回失败原因
     */
    Response<String> register(@ParamInfo("register") Register register);


}
