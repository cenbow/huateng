package cn.com.huateng.payment.dao;

import cn.com.huateng.payment.model.TTransferOrder;
import cn.com.huateng.payment.model.TTransferRecord;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;


@Repository
public class TTransferRecordMapper extends SqlSessionDaoSupport {

    public int insertTransferRecord(TTransferRecord tTransferRecord){
        return getSqlSession().insert("TTransferRecord.insertTransferRecord",tTransferRecord);
    }


}
