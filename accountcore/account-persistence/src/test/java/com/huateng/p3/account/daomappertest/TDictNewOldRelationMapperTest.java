package com.huateng.p3.account.daomappertest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TDictNewOldRelationMapper;
import com.huateng.p3.account.persistence.models.TDictNewOldRelation;

/**
 * Created with IntelliJ IDEA.
 * User: wangshushuang
 * Date: 13-12-5
 * Time: 下午7:09
 * To change this template use File | Settings | File Templates.
 */
public class TDictNewOldRelationMapperTest extends BaseSpringTest {

    private static Logger log =  LoggerFactory.getLogger(TDictNewOldRelationMapperTest.class);

    @Autowired
    private TDictNewOldRelationMapper tDictNewOldRelationMapper;

    @Before
    public void init(){
        log.debug("init start....");
        System.out.println("init start....");
    }

    @After
    public void destory(){

        log.debug("destory....");
        System.out.println("destory....");

    }

    /**
     * 测试插入方法
     */
    @Test
    public void testInsert(){

        String newTableName = "t_log_online_payment_2012118";
        String startDate = "20121101";
        String endDate = "20121130";
        String oldTableName = "t_log_online_payment_his";

        int oriDeleteCount =  tDictNewOldRelationMapper.deleteByPrimaryKey(newTableName);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TDictNewOldRelation tDictNewOldRelation = getTDictNewOldRelation(newTableName, startDate, endDate, oldTableName);
        int insertCount = tDictNewOldRelationMapper.insert(tDictNewOldRelation);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

    }

    public TDictNewOldRelation getTDictNewOldRelation(String newTableName, String startDate, String endDate, String oldTableName) {

        TDictNewOldRelation tDictNewOldRelation = new TDictNewOldRelation();
        tDictNewOldRelation.setNewTableName(newTableName);
        tDictNewOldRelation.setStartDate(startDate);
        tDictNewOldRelation.setEndDate(endDate);
        tDictNewOldRelation.setOldTableName(oldTableName);
        return tDictNewOldRelation;
    }

}
