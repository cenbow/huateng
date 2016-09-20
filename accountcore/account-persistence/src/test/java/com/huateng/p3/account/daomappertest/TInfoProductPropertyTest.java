package com.huateng.p3.account.daomappertest;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TInfoProductPropertyMapper;
import com.huateng.p3.account.persistence.models.TInfoProductProperty;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | setTings | File Templates.
 */
public class TInfoProductPropertyTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TInfoProductPropertyTest.class);

    @Autowired
    private TInfoProductPropertyMapper tInfoProductPropertyMapper;

    /**
     * 测试完成
     */
    @Test
    public void testinsert() {
        TInfoProductProperty tInfoProductProperty = new TInfoProductProperty();
        Date date = new Date();
        String s = "a";
        Long l = 11L;
        int num = 1;
        tInfoProductProperty.setId(s);
        tInfoProductProperty.setProductNo(s);
        tInfoProductProperty.setProductName(s);
        tInfoProductProperty.setProductCode(s);
        tInfoProductProperty.setProductStatus(s);
        tInfoProductProperty.setTxnStatus(s);
        tInfoProductProperty.setCreateTime(s);
        tInfoProductProperty.setAchievedTime(s);
        tInfoProductProperty.setArchivedFlag(s);
        tInfoProductProperty.setLastUpdateTime(s);
        tInfoProductProperty.setAccountNo(s);
        tInfoProductProperty.setCustomerNo(s);
        tInfoProductProperty.setAreaCode(s);
        tInfoProductProperty.setCityCode(s);
        tInfoProductProperty.setEnlishName(s);
        tInfoProductProperty.setEmail(s);
        tInfoProductProperty.setBankingcard(s);
        int i = tInfoProductPropertyMapper.insert(tInfoProductProperty);
        System.out.print("----------------------------插入TInfoProductProperty返回码:--:" + i);
    }

    /**
     *
     TInfoProductProperty  queryProductPropertyRecordByConditions(Map paramap);

     List<TInfoProductProperty> queryProductPropertyRecordByConditionsByProduct(String productNo);
     */

    @Test
      public void testqueryProductPropertyRecordByConditions(){
        String s = "a";
        String productNo = s;
        String productCode = s;
        String productStatus = s;
        String txnStatus = s;
        TInfoProductProperty tInfoProductProperty = tInfoProductPropertyMapper.queryProductPropertyRecordByConditions(productNo, productCode, productStatus, txnStatus);
        System.out.println("----testqueryProductPropertyRecordByConditions 获得accountNo:---------------"+tInfoProductProperty.getAccountNo());
    }

    @Test
    public void testqueryProductPropertyRecordByConditionsByProduct(){
        String s = "a";
        String productNo = s;

        List<TInfoProductProperty> list = tInfoProductPropertyMapper.queryProductPropertyRecordByConditionsByProduct(productNo);
        System.out.println("----testqueryProductPropertyRecordByConditionsByProduct 获得list.size():---------------"+list.size());
    }
}
