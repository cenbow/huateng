package cn.com.huateng.payment.dao;

import cn.com.huateng.payment.model.InfoSmsCheckCode;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: AP
 * Date: 13-8-27
 */
@Repository
public class InfoMobileCheckCodeMapper extends SqlSessionDaoSupport {
    public Integer insertMobileCheckCodeBase(InfoSmsCheckCode infoMobileCheckCode) {
        return (Integer)getSqlSession().insert
                ("InfoMobileCheckCode.insertMobileCheckCode", infoMobileCheckCode);
    }

    public Integer updateInfoMobileCheckCode(InfoSmsCheckCode infoMobileCheckCode) {
        return (Integer)getSqlSession().update
                ("InfoMobileCheckCode.updateInfoMobileCheckCode", infoMobileCheckCode);
    }

    public Integer updateMobileOld(InfoSmsCheckCode infoMobileCheckCode) {
        return (Integer)getSqlSession().update
                ("InfoMobileCheckCode.updateMobileOld", infoMobileCheckCode);
    }

    public InfoSmsCheckCode isMobileExisted(InfoSmsCheckCode infoMobileCheckCode) {
        return (InfoSmsCheckCode)getSqlSession().selectOne
                ("InfoMobileCheckCode.isMobileCodeExisted", infoMobileCheckCode);
    }

    public Integer isMobileCheckCodeRight(InfoSmsCheckCode infoMobileCheckCode) {
        return (Integer)getSqlSession().selectOne
                ("InfoMobileCheckCode.isMobileCheckCodeRight", infoMobileCheckCode);
    }
}

