package com.huateng.p3.account.daomappertest;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.base.TSmsTempSendService;
import com.huateng.p3.account.persistence.models.TSmsTempSend;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
public class TSmsTempSendTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TSmsTempSendTest.class);

    @Autowired
    private TSmsTempSendService tSmsTempSendService;

    /*
    int deleteByPrimaryKey(Short recordNo);

    int insert(TSmsTempSend record);

    int insertSelective(TSmsTempSend record);

    TSmsTempSend selectByPrimaryKey(Short recordNo);

    int updateByPrimaryKeySelective(TSmsTempSend record);

    int updateByPrimaryKey(TSmsTempSend record);
     */

    /**
     *测试完成
     */
    @Test
    public void testinsert() {
        try {
            TSmsTempSend tSmsTempSend = new TSmsTempSend();
            tSmsTempSend.setBatchNo(123L);
            tSmsTempSend.setContent("测试content");
            tSmsTempSend.setSender("chengmoutao");
            tSmsTempSend.setRecordNo(87654321L);
            int i =  tSmsTempSendService.insert(tSmsTempSend);
            System.out.print("----------------------------插入TSmsTempSend返回码:--:"+i);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     *测试完成
     */
    @Test
    public void testinsertSelective() {
        try {
            TSmsTempSend tSmsTempSend = new TSmsTempSend();
            tSmsTempSend.setBatchNo(123L);
            tSmsTempSend.setRecordNo(87654322L);
            tSmsTempSend.setContent("testinsertSelective");
            tSmsTempSend.setSender("chengmoutao");
            int i =  tSmsTempSendService.insertSelective(tSmsTempSend);
            System.out.print("----------------------------插入TSmsTempSend返回码:--:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 已经测试完成
     */
    @Test
    public void testselectByPrimaryKey() {
        try {
            long recordNo = 3410077L;
            TSmsTempSend tSmsTempSend = tSmsTempSendService.selectByPrimaryKey(recordNo);
            System.out.print(tSmsTempSend.getContent()+tSmsTempSend.getSender());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     *测试完成
     */
    @Test
    public void testupdateByPrimaryKeySelective() {
        try {
            TSmsTempSend tSmsTempSend = new TSmsTempSend();
            tSmsTempSend.setRecordNo(87654322L);
            tSmsTempSend.setBatchNo(3333L);
            tSmsTempSend.setContent("updateByPrimaryKeySelective");
            tSmsTempSend.setSender("chene");
            int i = tSmsTempSendService.updateByPrimaryKeySelective(tSmsTempSend);
            System.out.print("----------------------------updateByPrimaryKeySelective返回码:--:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     *测试完成
     */
    @Test
    public void testupdateByPrimaryKey() {
        try {
            TSmsTempSend tSmsTempSend = new TSmsTempSend();
            tSmsTempSend.setRecordNo(87654322L);
            tSmsTempSend.setBatchNo(11111L);
            tSmsTempSend.setContent("updateByPrimaryKey");
            tSmsTempSend.setSender("chengmouta1o");
            int i=tSmsTempSendService.updateByPrimaryKey(tSmsTempSend);
            System.out.print("----------------------------testupdateByPrimaryKey返回码:--:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     *测试完成
     */
    @Test
    public void testdeleteByPrimaryKey() {
        try {
            long recordNo = 87654321;
            int i = tSmsTempSendService.deleteByPrimaryKey(recordNo);
            System.out.print("----------------------------testdeleteByPrimaryKey返回码:--:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
