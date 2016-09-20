package com.huateng.p3.account.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huateng.p3.account.persistence.TSmsTempSendMapper;
import com.huateng.p3.account.persistence.models.TSmsTempSend;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:32
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TSmsTempSendService {
    @Autowired
    public TSmsTempSendMapper tSmsTempSendMapper;

    public int deleteByPrimaryKey(long recordNo){
        return tSmsTempSendMapper.deleteByPrimaryKey(recordNo);
    }

    public int insert(TSmsTempSend record){
        return tSmsTempSendMapper.insert(record);
    }

    public int insertSelective(TSmsTempSend record){
        return tSmsTempSendMapper.insertSelective(record);
    }

    public TSmsTempSend selectByPrimaryKey(long recordNo){
        return tSmsTempSendMapper.selectByPrimaryKey(recordNo);
    }

    public int updateByPrimaryKeySelective(TSmsTempSend record){
        return tSmsTempSendMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(TSmsTempSend record){
        return tSmsTempSendMapper.updateByPrimaryKey(record);
    }
       /*
    int deleteByPrimaryKey(long recordNo);

    int insert(TSmsTempSend record);

    int insertSelective(TSmsTempSend record);

    TSmsTempSend selectByPrimaryKey(long recordNo);

    int updateByPrimaryKeySelective(TSmsTempSend record);

    int updateByPrimaryKey(TSmsTempSend record);
     */

}
