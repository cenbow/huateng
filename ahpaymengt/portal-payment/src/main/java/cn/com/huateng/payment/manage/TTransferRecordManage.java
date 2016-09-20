package cn.com.huateng.payment.manage;

import cn.com.huateng.payment.dao.TTransferRecordMapper;
import cn.com.huateng.payment.model.TTransferRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Component
public class TTransferRecordManage {
    @Autowired
    private TTransferRecordMapper tTransferRecordMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public void insertTransferRecord(TTransferRecord tTransferRecord){
        tTransferRecordMapper.insertTransferRecord(tTransferRecord);
    }


}
