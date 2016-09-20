package cn.com.huateng.account.dao;



import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class TInfoMobileHMapper extends SqlSessionDaoSupport{
    public String getMobileAreaCode(String unifyId){
        return getSqlSession().selectOne("TInfoMobileH.getMobileAreaCode",unifyId.substring(0, 7));
    }

    public String getMobileApanage(String areaCode){
        return getSqlSession().selectOne("TInfoMobileH.getMobileApanage",areaCode);
    }


    public String getMobileCityCode(String unifyId){
        return getSqlSession().selectOne("TInfoMobileH.getMobileCityCode",unifyId.substring(0, 7));
    }

}