package com.huateng.p3.account.daomappertest;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TInfoProductPropertyHisMapper;
import com.huateng.p3.account.persistence.models.TInfoProductPropertyHis;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | setTings | File Templates.
 */
public class TInfoProductPropertyHisTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TInfoProductPropertyHisTest.class);

    @Autowired
    private TInfoProductPropertyHisMapper tInfoProductPropertyHisMapper;

    /**
     * 测试完成
     */
    @Test
    public void testinsert() {
        TInfoProductPropertyHis tInfoProductPropertyHis = new TInfoProductPropertyHis();
        String s = "a";
        tInfoProductPropertyHis.setId(s);
        tInfoProductPropertyHis.setProductNo(s);
        tInfoProductPropertyHis.setProductName(s);
        tInfoProductPropertyHis.setProductCode(s);
        tInfoProductPropertyHis.setProductStatus(s);
        tInfoProductPropertyHis.setTxnStatus(s);
        tInfoProductPropertyHis.setCreateTime(s);
        tInfoProductPropertyHis.setAchievedTime(s);
        tInfoProductPropertyHis.setArchivedFlag(s);
        tInfoProductPropertyHis.setLastUpdateTime(s);
        tInfoProductPropertyHis.setAccountNo(s);
        tInfoProductPropertyHis.setCustomerNo(s);
        tInfoProductPropertyHis.setAreaCode(s);
        tInfoProductPropertyHis.setCityCode(s);
        tInfoProductPropertyHis.setEnlishName(s);
        tInfoProductPropertyHis.setEmail(s);
        tInfoProductPropertyHis.setBankingcard(s);
        int i = tInfoProductPropertyHisMapper.insert(tInfoProductPropertyHis);
        System.out.print("----------------------------插入tInfoProductPropertyHis返回码:--:" + i);
    }
}
