package cn.com.huateng.payment.service.impl;

import cn.com.huateng.account.service.SeqGeneratorService;
import cn.com.huateng.common.BussErrorCode;
import cn.com.huateng.payment.manage.InfoMobileCheckCodeManage;
import cn.com.huateng.payment.model.InfoSmsCheckCode;
import cn.com.huateng.payment.service.MobileCheckCodeService;
import com.huateng.p3.component.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: AP
 * Date: 13-8-26
 */
@Service
public class MobileCheckCodeServiceImpl implements MobileCheckCodeService {
    private static final Logger log = LoggerFactory.getLogger(MobileCheckCodeServiceImpl.class);

    @Autowired
    private InfoMobileCheckCodeManage mobileCheckCodeManage;

    @Autowired
    private SeqGeneratorService seqGeneratorService;

    /**
     * 插入短信验证码表                                                    。
     *
     * @param infoSmsCheckCode 短信验证码表对象
     * @return 成功失败
     */
    @Override
    public Response<String> insertMobileCheckCode(InfoSmsCheckCode infoSmsCheckCode) {
        Response<String> response=new Response<String>();
        try {
            String recordNo = seqGeneratorService.generateOrderNo("PORTAL.S_T_INFO_MOBILE_CHECKCODE").toString();
            infoSmsCheckCode.setRecordNo(recordNo);
            mobileCheckCodeManage.insertMobileCheckCodeBase(infoSmsCheckCode);
            response.setResult(BussErrorCode.ERROR_CODE_000000.getErrorcode());
        } catch (Exception e) {
            log.error("插入短信验证码失败:", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_500001.getErrorcode());
            response.setErrorMsg(BussErrorCode.ERROR_CODE_500001.getErrordesc());
            return response;
        }
        return response;
    }

    /**
     * 更新短信验证码单条记录
     *
     * @param infoMobileCheckCode 短信验证码表对象
     * @return 成功失败
     */
    @Override
    public Response<String> updateInfoMobileCheckCode(InfoSmsCheckCode infoMobileCheckCode) {
        Response<String> response=new Response<String>();
        try {
            mobileCheckCodeManage.updateInfoMobileCheckCode(infoMobileCheckCode);
            response.setResult(BussErrorCode.ERROR_CODE_000000.getErrorcode());
        } catch (Exception e) {
            log.error("更新短信验证码表失败:", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_500002.getErrorcode());
            response.setErrorMsg(BussErrorCode.ERROR_CODE_500002.getErrordesc());
            return response;
        }
        return response;
    }

    /**
     * 更新短信验证码表
     *
     * @param infoSmsCheckCode 短信验证码表对象
     * @return 成功失败
     */
    @Override
    public Response<String> updateMobileOld(InfoSmsCheckCode infoSmsCheckCode) {
        Response<String> response=new Response<String>();
        try {
            mobileCheckCodeManage.updateMobileOld(infoSmsCheckCode);
            response.setResult(BussErrorCode.ERROR_CODE_000000.getErrorcode());

        } catch (Exception e) {
            log.error("更新短信验证码表失败:", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_500002.getErrorcode());
            response.setErrorMsg(BussErrorCode.ERROR_CODE_500002.getErrordesc());
            return response;
        }

        return response;
    }

    /**
     * 查询最新验证码
     *
     * @param infoMobileCheckCode 短信验证码表对象
     * @return 验证码对象
     */
    @Override
    public Response<InfoSmsCheckCode> isMobileExisted(InfoSmsCheckCode infoMobileCheckCode) {
        Response<InfoSmsCheckCode> response = new Response<InfoSmsCheckCode>();
        InfoSmsCheckCode infoSmsCheckCode   =  mobileCheckCodeManage.isMobileExisted(infoMobileCheckCode)   ;
        response.setResult(infoSmsCheckCode);
        return response;
    }

    /**
     * 验证短信验证码是否正确
     *
     * @param infoMobileCheckCode 短信验证码表对象
     * @return 成功失败
     */
    @Override
    public Response<String> isMobileCheckCodeRight(InfoSmsCheckCode infoMobileCheckCode) {
        Response<String> response=new Response<String>();
        if (mobileCheckCodeManage.isMobileCheckCodeRight(infoMobileCheckCode).intValue() == 1) {
             response.setResult(BussErrorCode.ERROR_CODE_000000.getErrorcode());
            return response;
        } else {
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            return  response;
        }

    }

}
