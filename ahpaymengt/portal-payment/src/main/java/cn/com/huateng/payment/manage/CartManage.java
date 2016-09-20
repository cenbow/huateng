package cn.com.huateng.payment.manage;

import cn.com.huateng.payment.dao.CartMapper;
import cn.com.huateng.payment.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: 董培基
 * Date: 14-11-18
 * Time: 下午2:17
 */
@Component
public class CartManage {
    @Autowired
    private CartMapper cartMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public void insertCart(Cart cart){
        cartMapper.insertCart(cart);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCart(Cart cart){
        cartMapper.updateCart(cart);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCart(Cart cart){
        cartMapper.deleteCart(cart);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void patchDeleteCart(List<Long> ids){
        cartMapper.patchDeleteCart(ids);
    }

}
