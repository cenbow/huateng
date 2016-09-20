package com.huateng.p3.account.daomappertest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TDictAreaCityMapper;
import com.huateng.p3.account.persistence.models.TDictAreaCity;

/**
 * Created with IntelliJ IDEA.
 * User: wangshushuang
 * Date: 13-12-5
 * Time: 下午4:33
 * To change this template use File | Settings | File Templates.
 */
public class TDictAreaCityMapperTest extends BaseSpringTest {

    private static Logger log =  LoggerFactory.getLogger(TDictAreaCityMapperTest.class);

    @Autowired
    private TDictAreaCityMapper tDictAreaCityMapper;

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

        String currentCode = "920282";
        String teleCode = "555";
        String currentName = "宜兴市";
        String parenetCode = "320000";
        String codeFlag = "2";
        String mobileHCity = "宜兴";

        int oriDeleteCount =  tDictAreaCityMapper.deleteByPrimaryKey(currentCode);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TDictAreaCity tDictAreaCity = getTDictAreaCity(currentCode,teleCode,currentName,parenetCode,codeFlag,mobileHCity) ;
        int insertCount = tDictAreaCityMapper.insert(tDictAreaCity);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

    }

    /**
     * 删除按照主键
     */
    @Test
    public void testDeleteByPrimaryKey(){

        String currentCode = "920282";
        String teleCode = "555";
        String currentName = "宜兴市";
        String parenetCode = "320000";
        String codeFlag = "2";
        String mobileHCity = "宜兴";

        int oriDeleteCount =  tDictAreaCityMapper.deleteByPrimaryKey(currentCode);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TDictAreaCity tDictAreaCity = getTDictAreaCity(currentCode,teleCode,currentName,parenetCode,codeFlag,mobileHCity) ;
        int insertCount = tDictAreaCityMapper.insert(tDictAreaCity);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

        int deleteCount =   tDictAreaCityMapper.deleteByPrimaryKey(currentCode);
        log.debug("deleteCount=[" + deleteCount + "]");
        assertThat(deleteCount,is(1));
    }

    /**
     * 按照选择插入
     */
    @Test
    public void testInsertSelective(){

        String currentCode = "920284";
        String teleCode = "556";
        String currentName = "宜兴区";
        String parenetCode = "320001";
        String codeFlag = "3";
        String mobileHCity = "宜昌";

        int oriDeleteCount =  tDictAreaCityMapper.deleteByPrimaryKey(currentCode);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TDictAreaCity tDictAreaCity = getTDictAreaCity(currentCode,teleCode,currentName,parenetCode,codeFlag,mobileHCity) ;
        int insertCount = tDictAreaCityMapper.insertSelective(tDictAreaCity);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

        currentCode = "920283";
        int oriDeleteCount2 =  tDictAreaCityMapper.deleteByPrimaryKey(currentCode);
        log.debug("oriDeleteCount2=[" + oriDeleteCount2 + "]");
//        tDictAreaCity = getTDictAreaCity(currentCode,teleCode,currentName,parenetCode,codeFlag,mobileHCity) ;
        tDictAreaCity = getTDictAreaCity(currentCode,null,null,null,codeFlag,null) ;
        int insertCount2 = tDictAreaCityMapper.insertSelective(tDictAreaCity);
        log.debug("insertCount2=[" + insertCount2 + "]");
        assertThat(insertCount2,is(1));

    }

    /**
     * 查询按照主键
     */
    @Test
    public void testSelectByPrimaryKey(){

        String currentCode = "320282";
        String teleCode = "855";
        String currentName = "b兴市";
        String parenetCode = "520000";
        String codeFlag = "3";
        String mobileHCity = "b兴";

        int oriDeleteCount =  tDictAreaCityMapper.deleteByPrimaryKey(currentCode);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        TDictAreaCity tDictAreaCity = tDictAreaCityMapper.selectByPrimaryKey(currentCode);
        assertNull(tDictAreaCity);
        log.debug("没有找到数据********");

        //进行插入操作
        tDictAreaCity = getTDictAreaCity(currentCode,teleCode,currentName,parenetCode,codeFlag,mobileHCity) ;
        int insertCount = tDictAreaCityMapper.insert(tDictAreaCity);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

        tDictAreaCity = tDictAreaCityMapper.selectByPrimaryKey(currentCode);
        log.debug("currentCode=[" + tDictAreaCity.getCurrentCode() + "]");
        log.debug("teleCode=[" + tDictAreaCity.getTeleCode() + "]");
        log.debug("currentName=[" + tDictAreaCity.getCurrentName() + "]");
        log.debug("parenetCode=[" + tDictAreaCity.getParenetCode() + "]");
        log.debug("codeFlag=[" + tDictAreaCity.getCodeFlag() + "]");
        log.debug("mobileHCity=[" + tDictAreaCity.getMobileHCity() + "]");

        assertThat(tDictAreaCity.getCurrentCode(),is(currentCode));
        assertThat(tDictAreaCity.getTeleCode(),is(teleCode));
        assertThat(tDictAreaCity.getCurrentName(),is(currentName));
        assertThat(tDictAreaCity.getParenetCode(),is(parenetCode));
        assertThat(tDictAreaCity.getCodeFlag(),is(codeFlag));
        assertThat(tDictAreaCity.getMobileHCity(),is(mobileHCity));
    }

    /**
     * 更新数据通过主键
     */
    @Test
    public void testUpdateByPrimaryKey(){

        String currentCode = "920282";
        String teleCode = "555";
        String currentName = "宜兴市";
        String parenetCode = "320000";
        String codeFlag = "2";
        String mobileHCity = "宜兴";

        int oriDeleteCount =  tDictAreaCityMapper.deleteByPrimaryKey(currentCode);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");

        TDictAreaCity tDictAreaCity = getTDictAreaCity(currentCode,teleCode,currentName,parenetCode,codeFlag,mobileHCity) ;

        int tmpUpdateCount = tDictAreaCityMapper.updateByPrimaryKey(tDictAreaCity);
        log.debug("tmpUpdateCount=[" + tmpUpdateCount + "]");
        assertThat(tmpUpdateCount,is(0));

        //进行插入操作
        int insertCount = tDictAreaCityMapper.insert(tDictAreaCity);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

        teleCode = "855";
        currentName = "8兴市";
        parenetCode = "820000";
        codeFlag = "8";
        mobileHCity = "8兴";
        tDictAreaCity = getTDictAreaCity(currentCode,teleCode,currentName,parenetCode,codeFlag,mobileHCity) ;
        tmpUpdateCount = tDictAreaCityMapper.updateByPrimaryKey(tDictAreaCity);
        log.debug("tmpUpdateCount=[" + tmpUpdateCount + "]");
        assertThat(tmpUpdateCount,is(1));


    }

    /**
     * 选择性修改数据通过主键
     */
    @Test
    public void testUpdateByPrimaryKeySelective(){

        String currentCode = "920282";
        String teleCode = "555";
        String currentName = "宜兴市";
        String parenetCode = "320000";
        String codeFlag = "2";
        String mobileHCity = "宜兴";

        int oriDeleteCount =  tDictAreaCityMapper.deleteByPrimaryKey(currentCode);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");

        TDictAreaCity tDictAreaCity = getTDictAreaCity(currentCode,teleCode,currentName,parenetCode,codeFlag,mobileHCity) ;

        int tmpUpdateCount = tDictAreaCityMapper.updateByPrimaryKeySelective(tDictAreaCity);
        log.debug("tmpUpdateCount=[" + tmpUpdateCount + "]");
        assertThat(tmpUpdateCount,is(0));

        //进行插入操作
        int insertCount = tDictAreaCityMapper.insert(tDictAreaCity);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

        teleCode = "255";
        currentName = "2兴市";
        parenetCode = "220000";
        codeFlag = "2";
        mobileHCity = "2兴";
        tDictAreaCity = getTDictAreaCity(currentCode,teleCode,currentName,parenetCode,codeFlag,mobileHCity) ;
        tmpUpdateCount = tDictAreaCityMapper.updateByPrimaryKeySelective(tDictAreaCity);
        log.debug("tmpUpdateCount=[" + tmpUpdateCount + "]");
        assertThat(tmpUpdateCount,is(1));

        currentCode = "920283";
        int oriDeleteCount2 =  tDictAreaCityMapper.deleteByPrimaryKey(currentCode);
        log.debug("oriDeleteCount2=[" + oriDeleteCount2 + "]");
        tDictAreaCity.setCurrentCode(currentCode);
        int insertCount2 = tDictAreaCityMapper.insert(tDictAreaCity);
        log.debug("insertCount2=[" + insertCount2 + "]");
        assertThat(insertCount2,is(1));

//        teleCode = "855";
//        currentName = "8兴市";
//        parenetCode = "820000";
//        codeFlag = "8";
//        mobileHCity = "8兴";

        teleCode = null;
        currentName = "9兴市";
        parenetCode = null;
        codeFlag = "9";
        mobileHCity = null;
        tDictAreaCity = getTDictAreaCity(currentCode,teleCode,currentName,parenetCode,codeFlag,mobileHCity) ;
        int tmpUpdateCount2 = tDictAreaCityMapper.updateByPrimaryKeySelective(tDictAreaCity);
        log.debug("tmpUpdateCount2=[" + tmpUpdateCount2 + "]");
        assertThat(tmpUpdateCount2,is(1));
    }

    public TDictAreaCity getTDictAreaCity(String currentCode, String teleCode, String currentName, String parenetCode, String codeFlag, String mobileHCity) {

        TDictAreaCity tDictAreaCity = new TDictAreaCity();
        tDictAreaCity.setCurrentCode(currentCode);
        tDictAreaCity.setTeleCode(teleCode);
        tDictAreaCity.setCurrentName(currentName);
        tDictAreaCity.setParenetCode(parenetCode);
        tDictAreaCity.setCodeFlag(codeFlag);
        tDictAreaCity.setMobileHCity(mobileHCity);
        return tDictAreaCity;
    }
}
