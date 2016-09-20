package cn.com.huateng.payment.dao;

import cn.com.huateng.payment.model.Cart;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: 董培基
 * Date: 14-11-14
 * Time: 下午4:37
 */
@Repository
public class CartMapper extends SqlSessionDaoSupport {

    public List<Cart> findCartList(Cart cart) {
        return getSqlSession().selectList("Cart.selectCartListByUnifyId", cart);
    }

    public List<Cart> findCartListB(Cart cart) {
        return getSqlSession().selectList("Cart.selectCartList", cart);
    }

    public Long findCountCartList(Cart cart) {
        return getSqlSession().selectOne("Cart.selectCountCartListByUnifyId", cart);
    }

    public int insertCart(Cart cart){
        return getSqlSession().insert("Cart.insertCart",cart);
    }

    public void updateCart(Cart cart){
        getSqlSession().update("Cart.updateCart",cart);
    }

    public void deleteCart(Cart cart){
        getSqlSession().delete("Cart.deleteCart",cart);
    }

    public void patchDeleteCart(List<Long> ids){
        getSqlSession().delete("Cart.patchDeleteCart",ids);
    }

    public List<Cart> findByIds(List<Long> ids){
        return getSqlSession().selectList("Cart.findByIds",ids);
    }
}
