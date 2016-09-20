package cn.com.huateng.payment.manage;

import cn.com.huateng.payment.dao.TPortOrderBaseMapper;
import cn.com.huateng.payment.dao.VirtualGoodsMapper;
import cn.com.huateng.payment.model.TPortOrderBase;
import cn.com.huateng.payment.model.VirtualGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: 董培基
 * Date: 14-11-24
 * Time: 上午11:49
 */
@Component
public class VirtualGoodsManage {

    @Autowired
    private VirtualGoodsMapper virtualGoodsMapper;

    @Autowired
    private TPortOrderBaseMapper orderBaseMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public void insertVirtualGoods(VirtualGoods virtualGoods,TPortOrderBase tPortOrderBase){
        virtualGoodsMapper.insertVirtualGoods(virtualGoods);
        orderBaseMapper.insert(tPortOrderBase);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateVirtualGoods(VirtualGoods virtualGoods){
        virtualGoodsMapper.updateVirtualGoods(virtualGoods);
    }

}
