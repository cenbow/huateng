package cn.com.huateng.payment.manage;

import cn.com.huateng.payment.dao.TTransferOrderMapper;
import cn.com.huateng.payment.model.TTransferOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Component
public class TTransferOrderManage {
    @Autowired
    private TTransferOrderMapper tTransferOrderMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public void insertTransferOrder(TTransferOrder tTransferOrder){
        tTransferOrderMapper.insertTransferOrder(tTransferOrder);
    }


}
