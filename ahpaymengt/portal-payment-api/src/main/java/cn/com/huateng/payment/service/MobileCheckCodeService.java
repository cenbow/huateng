package cn.com.huateng.payment.service;

import cn.com.huateng.payment.model.InfoSmsCheckCode;
import com.huateng.p3.component.Response;

/**
 * 手机验证码服务
 * Created with IntelliJ IDEA.
 * User: AP
 * Date: 13-8-26
 */
public interface MobileCheckCodeService {
    /**
     * 插入短信验证码表
     * @param infoMobileCheckCode 短信验证码表对象
     * @return 成功失败
     */
    Response<String> insertMobileCheckCode(InfoSmsCheckCode infoMobileCheckCode);

    /**
     * 更新短信验证码单条记录
     * @param infoMobileCheckCode 短信验证码表对象
     * @return 成功失败
     */
    Response<String> updateInfoMobileCheckCode(InfoSmsCheckCode infoMobileCheckCode);

    /**
     * 更新短信验证码表
     * @param infoMobileCheckCode 短信验证码表对象
     * @return 成功失败
     */
    Response<String> updateMobileOld(InfoSmsCheckCode infoMobileCheckCode);

    /**
     * 查询最新验证码
     * @param infoMobileCheckCode 短信验证码表对象
     * @return 验证码对象
     */
    Response<InfoSmsCheckCode> isMobileExisted(InfoSmsCheckCode infoMobileCheckCode);

    /**
     * 验证短信验证码是否正确
     * @param infoMobileCheckCode 短信验证码表对象
     * @return 成功失败
     */
    Response<String> isMobileCheckCodeRight(InfoSmsCheckCode infoMobileCheckCode);


}

