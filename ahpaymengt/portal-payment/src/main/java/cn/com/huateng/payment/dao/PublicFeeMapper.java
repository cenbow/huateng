package cn.com.huateng.payment.dao;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import cn.com.huateng.payment.model.PublicFee;


@Repository
public class PublicFeeMapper extends SqlSessionDaoSupport {

    public int savePublicFee(PublicFee publicFee){
       return getSqlSession().insert("PublicFee.insertSelective",publicFee);
    }

    public PublicFee findPublicFeeVo(String orderNo){
        return getSqlSession().selectOne("PublicFee.selectByPrimaryKey",orderNo);
    }

    public int updatePublicFeeVo(PublicFee publicFee){
        return getSqlSession().update("PublicFee.updateByPrimaryKeySelective",publicFee);
    }

}
