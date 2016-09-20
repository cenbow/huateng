package cn.com.huateng.payment.manage;

import cn.com.huateng.payment.dao.InfoMobileCheckCodeMapper;
import cn.com.huateng.payment.model.InfoSmsCheckCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: AP
 * Date: 13-8-27
 */
@Component
public class InfoMobileCheckCodeManage {
    @Autowired
    private InfoMobileCheckCodeMapper mobileCheckCodeMapper;

    @Transactional
    public void insertMobileCheckCodeBase(InfoSmsCheckCode infoMobileCheckCode){
        mobileCheckCodeMapper.insertMobileCheckCodeBase(infoMobileCheckCode);
    }

    @Transactional
    public void updateInfoMobileCheckCode(InfoSmsCheckCode infoMobileCheckCode){
        mobileCheckCodeMapper.updateInfoMobileCheckCode(infoMobileCheckCode);
    }

    @Transactional
    public void updateMobileOld(InfoSmsCheckCode infoMobileCheckCode){
        mobileCheckCodeMapper.updateMobileOld(infoMobileCheckCode);
    }

    public InfoSmsCheckCode isMobileExisted(InfoSmsCheckCode infoMobileCheckCode){
        return mobileCheckCodeMapper.isMobileExisted(infoMobileCheckCode);
    }

    public Integer isMobileCheckCodeRight(InfoSmsCheckCode infoMobileCheckCode){
        return mobileCheckCodeMapper.isMobileCheckCodeRight(infoMobileCheckCode);
    }
}

