package cn.com.huateng.payment.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import cn.com.huateng.payment.model.TPortOrderBase;

@Repository
public class TPortOrderBaseMapper extends SqlSessionDaoSupport {
    public void insert(TPortOrderBase orderBase) {
        getSqlSession().insert("TPortOrderBaseMapper.insert", orderBase);
    }
    public List<TPortOrderBase>  findTPortOrderBase(TPortOrderBase orderBase) {
       return getSqlSession().selectList("TPortOrderBaseMapper.findTPortOrderBase", orderBase);
    }

    public List<TPortOrderBase>  findTPortOrderBaseByOrderTranReq(TPortOrderBase orderBase) {
        return getSqlSession().selectList("TPortOrderBaseMapper.findTPortOrderBaseByOrderTranReq", orderBase);
    }
    
    public Long  findCountTPortOrderBase(TPortOrderBase orderBase) {
    	 return getSqlSession().selectOne("TPortOrderBaseMapper.findCountTPortOrderBase", orderBase);
     }

    public void updateTPortOrderBase(TPortOrderBase orderBase) {
        getSqlSession().update("TPortOrderBaseMapper.updateTPortOrderBaseVo",orderBase);
    }
}