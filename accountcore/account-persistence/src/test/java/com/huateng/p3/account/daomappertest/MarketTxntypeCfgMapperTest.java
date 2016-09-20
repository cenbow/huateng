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
import com.huateng.p3.account.persistence.MarketTxntypeCfgMapper;
import com.huateng.p3.account.persistence.models.MarketTxntypeCfg;

/**
 * Created with IntelliJ IDEA.
 * User: wangshushuang
 * Date: 13-12-4
 * Time: 下午3:22
 * To change this template use File | Settings | File Templates.
 */
public class MarketTxntypeCfgMapperTest extends BaseSpringTest {

    private static Logger log =  LoggerFactory.getLogger(MarketTxntypeCfgMapperTest.class);

    @Autowired
    private MarketTxntypeCfgMapper marketTxntypeCfgMapper;

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
     * 测试按照主键进行数据删除
     */
    @Test
    public void testDeleteByPrimaryKey(){

        String markettxntypeid = "33047987777777778";
        String marketcfgid = "50012050600020131126173245001001960";
        String outtxntype = "A11005";
        String txntype = "101110";
        String validflag = "1";

        int oriDeleteCount =  marketTxntypeCfgMapper.deleteByPrimaryKey(markettxntypeid);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");

        MarketTxntypeCfg marketTxntypeCfg = new MarketTxntypeCfg();
        marketTxntypeCfg.setMarkettxntypeid(markettxntypeid);
        marketTxntypeCfg.setMarketcfgid(marketcfgid);
        marketTxntypeCfg.setOuttxntype(outtxntype);
        marketTxntypeCfg.setTxntype(txntype);
        marketTxntypeCfg.setValidflag(validflag);
        int insertCount = marketTxntypeCfgMapper.insert(marketTxntypeCfg);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));
        int deleteCount = marketTxntypeCfgMapper.deleteByPrimaryKey(markettxntypeid);
        log.debug("deleteCount=[" + deleteCount + "]");
        assertThat(deleteCount,is(1));
        deleteCount =  marketTxntypeCfgMapper.deleteByPrimaryKey(markettxntypeid);
        log.debug("第二次操作:deleteCount=[" + deleteCount + "]");
        assertThat(deleteCount,is(0));
    }

    /**
     * 测试插入一条数据
     */
    @Test
    public void testInsert(){

        MarketTxntypeCfg marketTxntypeCfg = new MarketTxntypeCfg();
        String markettxntypeid = "33047987777777780";
        String marketcfgid = "50012050600020131126173245001001960";
        String outtxntype = "A11005";
        String txntype = "101110";
        String validflag = "1";

        int oriDeleteCount =  marketTxntypeCfgMapper.deleteByPrimaryKey(markettxntypeid);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");

        marketTxntypeCfg.setMarkettxntypeid(markettxntypeid);
        marketTxntypeCfg.setMarketcfgid(marketcfgid);
        marketTxntypeCfg.setOuttxntype(outtxntype);
        marketTxntypeCfg.setTxntype(txntype);
        marketTxntypeCfg.setValidflag(validflag);
        int insertCount = marketTxntypeCfgMapper.insert(marketTxntypeCfg);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

    }

    /**
     * 进行选择数值插入
     */
    @Test
    public void testInsertSelective(){

        String markettxntypeid = "33047987777777791";
        String marketcfgid = "50012050600020131126173245001001961";
        String outtxntype = "A11006";
        String txntype = "101111";
        String validflag = "2";

        int oriDeleteCount =  marketTxntypeCfgMapper.deleteByPrimaryKey(markettxntypeid);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");

        MarketTxntypeCfg marketTxntypeCfg = getMarketTxntypeCfg(markettxntypeid,marketcfgid,outtxntype,txntype,validflag);
        int insertSelectiveCount = marketTxntypeCfgMapper.insertSelective(marketTxntypeCfg);
//        int insertCount = marketTxntypeCfgMapper.insert(marketTxntypeCfg);
        log.debug("insertSelectiveCount=[" + insertSelectiveCount + "]");
        assertThat(insertSelectiveCount,is(1));
        markettxntypeid = "33047987777777792";
        marketTxntypeCfgMapper.deleteByPrimaryKey(markettxntypeid);
        marketTxntypeCfg = getMarketTxntypeCfg(markettxntypeid,null,outtxntype,txntype,validflag);
        int insertSelectiveCount1 = marketTxntypeCfgMapper.insertSelective(marketTxntypeCfg);
        log.debug("insertSelectiveCount1=[" + insertSelectiveCount1 + "]");
        assertThat(insertSelectiveCount1,is(1));

        markettxntypeid = "33047987777777793";
        marketTxntypeCfgMapper.deleteByPrimaryKey(markettxntypeid);
        marketTxntypeCfg = getMarketTxntypeCfg(markettxntypeid,marketcfgid,null,txntype,validflag);
        int insertSelectiveCount2 = marketTxntypeCfgMapper.insertSelective(marketTxntypeCfg);
        log.debug("insertSelectiveCount2=[" + insertSelectiveCount2 + "]");
        assertThat(insertSelectiveCount2,is(1));

        markettxntypeid = "33047987777777793";
        marketTxntypeCfgMapper.deleteByPrimaryKey(markettxntypeid);
        marketTxntypeCfg = getMarketTxntypeCfg(markettxntypeid,marketcfgid,outtxntype,null,validflag);
        int insertSelectiveCount3 = marketTxntypeCfgMapper.insertSelective(marketTxntypeCfg);
        log.debug("insertSelectiveCount3=[" + insertSelectiveCount3 + "]");
        assertThat(insertSelectiveCount3,is(1));

        markettxntypeid = "33047987777777794";
        marketTxntypeCfgMapper.deleteByPrimaryKey(markettxntypeid);
        marketTxntypeCfg = getMarketTxntypeCfg(markettxntypeid,marketcfgid,outtxntype,txntype,null);
        int insertSelectiveCount4 = marketTxntypeCfgMapper.insertSelective(marketTxntypeCfg);
        log.debug("insertSelectiveCount4=[" + insertSelectiveCount4 + "]");
        assertThat(insertSelectiveCount4,is(1));

        markettxntypeid = "33047987777777795";
        marketTxntypeCfgMapper.deleteByPrimaryKey(markettxntypeid);
        marketTxntypeCfg = getMarketTxntypeCfg(markettxntypeid,null,null,null,null);
        int insertSelectiveCount5 = marketTxntypeCfgMapper.insertSelective(marketTxntypeCfg);
        log.debug("insertSelectiveCount5=[" + insertSelectiveCount5 + "]");
        assertThat(insertSelectiveCount5,is(1));

    }

    private MarketTxntypeCfg getMarketTxntypeCfg(String markettxntypeid,String marketcfgid,String outtxntype,String txntype,String validflag){
        MarketTxntypeCfg marketTxntypeCfg = new MarketTxntypeCfg();
        marketTxntypeCfg.setMarkettxntypeid(markettxntypeid);
        marketTxntypeCfg.setMarketcfgid(marketcfgid);
        marketTxntypeCfg.setOuttxntype(outtxntype);
        marketTxntypeCfg.setTxntype(txntype);
        marketTxntypeCfg.setValidflag(validflag);
        return marketTxntypeCfg;
    }

    /**
     * 按照主键查询
     */
    @Test
    public void testSelectByPrimaryKey(){
        String markettxntypeid = "33047987777777791";
        String marketcfgid = "50012050600020131126173245001001968";
        String outtxntype = "A11008";
        String txntype = "101118";
        String validflag = "8";

        int oriDeleteCount =  marketTxntypeCfgMapper.deleteByPrimaryKey(markettxntypeid);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");

        MarketTxntypeCfg marketTxntypeCfg = marketTxntypeCfgMapper.selectByPrimaryKey(markettxntypeid);
        assertNull(marketTxntypeCfg);
        log.debug("数据没有查到*******");

        marketTxntypeCfg = getMarketTxntypeCfg(markettxntypeid,marketcfgid,outtxntype,txntype,validflag);
        int insertSelectiveCount = marketTxntypeCfgMapper.insertSelective(marketTxntypeCfg);
//        int insertCount = marketTxntypeCfgMapper.insert(marketTxntypeCfg);
        log.debug("insertSelectiveCount=[" + insertSelectiveCount + "]");
        assertThat(insertSelectiveCount,is(1));

        marketTxntypeCfg = marketTxntypeCfgMapper.selectByPrimaryKey(markettxntypeid);
        assertThat(marketTxntypeCfg.getMarkettxntypeid(),is(markettxntypeid));
        assertThat(marketTxntypeCfg.getMarketcfgid(),is(marketcfgid));
        assertThat(marketTxntypeCfg.getOuttxntype(),is(outtxntype));
        assertThat(marketTxntypeCfg.getTxntype(),is(txntype));
        assertThat(marketTxntypeCfg.getValidflag(),is(validflag));
        log.debug("Markettxntypeid=[" + marketTxntypeCfg.getMarkettxntypeid() + "]");
        log.debug("Marketcfgid=[" + marketTxntypeCfg.getMarketcfgid() + "]");
        log.debug("Outtxntype=[" + marketTxntypeCfg.getOuttxntype() + "]");
        log.debug("Txntype=[" + marketTxntypeCfg.getTxntype()+ "]");
        log.debug("Validflag=[" + marketTxntypeCfg.getValidflag() + "]");

    }

    /**
     * 按照主键进行更新操作
     */
    @Test
    public void testUpdateByPrimaryKey(){

        String markettxntypeid = "33047987777777791";
        String marketcfgid = "50012050600020131126173245001001968";
        String outtxntype = "A11007";
        String txntype = "101117";
        String validflag = "7";

        int oriDeleteCount =  marketTxntypeCfgMapper.deleteByPrimaryKey(markettxntypeid);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");

        MarketTxntypeCfg marketTxntypeCfg = getMarketTxntypeCfg(markettxntypeid,marketcfgid,outtxntype,txntype,validflag);
        int updateCount = marketTxntypeCfgMapper.updateByPrimaryKey(marketTxntypeCfg);
        log.debug("updateCount=[" + updateCount + "]");
        assertThat(updateCount,is(0));

        int insertCount = marketTxntypeCfgMapper.insert(marketTxntypeCfg);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

        marketcfgid = "50012050600020131126173245001001969";
        outtxntype = "A11009";
        txntype = "101119";
        validflag = "9";
        marketTxntypeCfg = getMarketTxntypeCfg(markettxntypeid,marketcfgid,outtxntype,txntype,validflag);
//        marketTxntypeCfg.setOuttxntype(null);
       updateCount = marketTxntypeCfgMapper.updateByPrimaryKey(marketTxntypeCfg);
        log.debug("updateCount=[" + updateCount + "]");
        assertThat(updateCount,is(1));
    }

    /**
     * 部分更新测试用例
     */
    @Test
    public void testUpdateByPrimaryKeySelective(){

        String markettxntypeid = "33047987777777791";
        String marketcfgid = "50012050600020131126173245001001967";
        String outtxntype = "A11007";
        String txntype = "101117";
        String validflag = "7";

        int oriDeleteCount =  marketTxntypeCfgMapper.deleteByPrimaryKey(markettxntypeid);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");

        MarketTxntypeCfg marketTxntypeCfg = getMarketTxntypeCfg(markettxntypeid,marketcfgid,outtxntype,txntype,validflag);
        int updateCount = marketTxntypeCfgMapper.updateByPrimaryKeySelective(marketTxntypeCfg);
        log.debug("updateCount=[" + updateCount + "]");
        assertThat(updateCount,is(0));

        int insertCount = marketTxntypeCfgMapper.insert(marketTxntypeCfg);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

        marketcfgid = "50012050600020131126173245001001969";
        outtxntype = "A11009";
        txntype = "101119";
        validflag = "9";
        marketTxntypeCfg = getMarketTxntypeCfg(markettxntypeid,marketcfgid,outtxntype,txntype,validflag);
//        marketTxntypeCfg.setOuttxntype(null);
        updateCount = marketTxntypeCfgMapper.updateByPrimaryKeySelective(marketTxntypeCfg);
        log.debug("updateCount=[" + updateCount + "]");
        assertThat(updateCount,is(1));

        updateCount = marketTxntypeCfgMapper.updateByPrimaryKeySelective(marketTxntypeCfg);
        log.debug("updateCount7=[" + updateCount + "]");
        assertThat(updateCount,is(1));


        markettxntypeid = "33047987777777792";
        int oriDeleteCount2 =  marketTxntypeCfgMapper.deleteByPrimaryKey(markettxntypeid);
        marketTxntypeCfg.setMarkettxntypeid(markettxntypeid);
        log.debug("oriDeleteCount2=[" + oriDeleteCount2 + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        insertCount = marketTxntypeCfgMapper.insert(marketTxntypeCfg);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));
        marketTxntypeCfg = getMarketTxntypeCfg(markettxntypeid,null,"A11002","101112","2");
//        marketTxntypeCfg.setOuttxntype(null);
        int updateCount2 = marketTxntypeCfgMapper.updateByPrimaryKeySelective(marketTxntypeCfg);
        log.debug("updateCount2=[" + updateCount2 + "]");
        assertThat(updateCount2,is(1));

        markettxntypeid = "33047987777777793";
        int oriDeleteCount3 =  marketTxntypeCfgMapper.deleteByPrimaryKey(markettxntypeid);
        marketTxntypeCfg.setMarkettxntypeid(markettxntypeid);
        log.debug("oriDeleteCount3[" + oriDeleteCount3 + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        insertCount = marketTxntypeCfgMapper.insert(marketTxntypeCfg);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));
        marketTxntypeCfg = getMarketTxntypeCfg(markettxntypeid,"50012050600020131126173245001001963",null,"101113","3");
        int updateCount3 = marketTxntypeCfgMapper.updateByPrimaryKeySelective(marketTxntypeCfg);
        log.debug("updateCount3=[" + updateCount3 + "]");
        assertThat(updateCount3,is(1));


        markettxntypeid = "33047987777777794";
        marketTxntypeCfg.setMarkettxntypeid(markettxntypeid);
        int oriDeleteCount4 =  marketTxntypeCfgMapper.deleteByPrimaryKey(markettxntypeid);
        log.debug("oriDeleteCount4=[" + oriDeleteCount4 + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        insertCount = marketTxntypeCfgMapper.insert(marketTxntypeCfg);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));
        marketTxntypeCfg = getMarketTxntypeCfg(markettxntypeid,"50012050600020131126173245001001964","A11004",null,"4");
        int updateCount4 = marketTxntypeCfgMapper.updateByPrimaryKeySelective(marketTxntypeCfg);
        log.debug("updateCount4=[" + updateCount4 + "]");
        assertThat(updateCount4,is(1));


        markettxntypeid = "33047987777777795";
        marketTxntypeCfg.setMarkettxntypeid(markettxntypeid);
        int oriDeleteCount5 =  marketTxntypeCfgMapper.deleteByPrimaryKey(markettxntypeid);
        log.debug("oriDeleteCount5=[" + oriDeleteCount5 + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        insertCount = marketTxntypeCfgMapper.insert(marketTxntypeCfg);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));
        marketTxntypeCfg = getMarketTxntypeCfg(markettxntypeid,"50012050600020131126173245001001965","A11005","101115",null);
        int updateCount5 = marketTxntypeCfgMapper.updateByPrimaryKeySelective(marketTxntypeCfg);
        log.debug("updateCount5=[" + updateCount5 + "]");
        assertThat(updateCount5,is(1));

    }

}
