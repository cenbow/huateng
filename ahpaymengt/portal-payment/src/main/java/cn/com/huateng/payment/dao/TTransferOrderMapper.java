package cn.com.huateng.payment.dao;

import cn.com.huateng.payment.model.TTransferOrder;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;


@Repository
public class TTransferOrderMapper extends SqlSessionDaoSupport {

    public int insertTransferOrder(TTransferOrder tTransferOrder){
        return getSqlSession().insert("TTransferOrder.insertTransferOrder",tTransferOrder);
    }


}
