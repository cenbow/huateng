package cn.com.huateng.payment.dao;

import cn.com.huateng.payment.model.Cart;
import cn.com.huateng.payment.model.VirtualGoods;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: 董培基
 * Date: 14-11-24
 * Time: 上午11:35
 */
@Repository
public class VirtualGoodsMapper extends SqlSessionDaoSupport {

    public int insertVirtualGoods(VirtualGoods virtualGoods){
        return getSqlSession().insert("VirtualGoods.insertVirtualGoods",virtualGoods);
    }

    public List<VirtualGoods> findVirtualGoodsList(VirtualGoods virtualGoods) {
        return getSqlSession().selectList("VirtualGoods.selectVirtualGoodsList", virtualGoods);
    }

    public void updateVirtualGoods(VirtualGoods virtualGoods){
        getSqlSession().update("VirtualGoods.updateVirtualGoods",virtualGoods);
    }

    public VirtualGoods findVirtualGoods(VirtualGoods virtualGoods){
         return getSqlSession().selectOne("VirtualGoods.selectVirtualGoods",virtualGoods);
    }


}
