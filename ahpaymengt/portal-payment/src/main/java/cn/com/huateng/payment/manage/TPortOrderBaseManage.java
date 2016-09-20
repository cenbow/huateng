package cn.com.huateng.payment.manage;

import cn.com.huateng.payment.dao.TPortOrderBaseMapper;
import cn.com.huateng.payment.model.TPortOrderBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TPortOrderBaseManage {
    @Autowired
    private TPortOrderBaseMapper orderBaseMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateTPortOrderBase(TPortOrderBase orderBase){
        orderBaseMapper.updateTPortOrderBase(orderBase);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void insert(TPortOrderBase orderBase){
        orderBaseMapper.insert(orderBase);
    }
    
}
