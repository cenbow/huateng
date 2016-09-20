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
import com.huateng.p3.account.persistence.TAccountBanktransAmtCntMapper;
import com.huateng.p3.account.persistence.models.TAccountBanktransAmtCnt;

/**
 * 对帐户银行交易金额统计mapper,进行测试
 * User: wangshushuang
 * Date: 13-12-5
 * Time: 上午10:04
 * To change this template use File | Settings | File Templates.
 */
public class TAccountBanktransAmtCntMapperTest extends BaseSpringTest {

    private static Logger log =  LoggerFactory.getLogger(TAccountBanktransAmtCntMapperTest.class);

    @Autowired
    private TAccountBanktransAmtCntMapper tAccountBanktransAmtCntMapper;

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
     *测试删除信息按照主键
     */
    @Test
    public void testDeleteByPrimaryKey(){

        String accountNo="8631021000002404";
        String customerNo="123778";
        String rule="1";
        Long banktransMaxNum = new Long("991");
        Long banktransMaxAmtSum = new Long("999999999992");
        Long monthBanktransMaxAmt = new Long("999999999993");
        Long monthBanktransMaxCnt= new Long("994");
        Long yearBanktransMaxAmt = new Long("999999999995");
        Long yearBanktransMaxCnt = new Long("996");
        String resvFld1 = "dele";
        String resvFld2 = "file";

        int oriDeleteCount =  tAccountBanktransAmtCntMapper.deleteByPrimaryKey(accountNo);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
       //进行插入操作
        TAccountBanktransAmtCnt tAccountBanktransAmtCnt = getTAccountBanktransAmtCnt(accountNo, customerNo,rule,banktransMaxNum, banktransMaxAmtSum,
                monthBanktransMaxAmt, monthBanktransMaxCnt, yearBanktransMaxAmt, yearBanktransMaxCnt, resvFld1, resvFld2) ;
        int insertCount = tAccountBanktransAmtCntMapper.insert(tAccountBanktransAmtCnt);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));
        int deleteCount =  tAccountBanktransAmtCntMapper.deleteByPrimaryKey(accountNo);
        log.debug("deleteCount=[" + deleteCount + "]");
        assertThat(deleteCount,is(1));

        }

    /**
     * 测试插入方法
     */
    @Test
    public void testInsert(){

        String accountNo="8631021000002404";
        String customerNo="123778";
        String rule="1";
        Long banktransMaxNum = new Long("991");
        Long banktransMaxAmtSum = new Long("999999999992");
        Long monthBanktransMaxAmt = new Long("999999999993");
        Long monthBanktransMaxCnt= new Long("994");
        Long yearBanktransMaxAmt = new Long("999999999995");
        Long yearBanktransMaxCnt = new Long("996");
        String resvFld1 = "dele";
        String resvFld2 = "file";

        int oriDeleteCount =  tAccountBanktransAmtCntMapper.deleteByPrimaryKey(accountNo);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TAccountBanktransAmtCnt tAccountBanktransAmtCnt = getTAccountBanktransAmtCnt(accountNo, customerNo,rule,banktransMaxNum, banktransMaxAmtSum,
                monthBanktransMaxAmt, monthBanktransMaxCnt, yearBanktransMaxAmt, yearBanktransMaxCnt, resvFld1, resvFld2) ;
        int insertCount = tAccountBanktransAmtCntMapper.insert(tAccountBanktransAmtCnt);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

    }

    @Test
    public void testInsertSelective(){

        String accountNo="8631021000002405";
        String customerNo="123778";
        String rule="1";
        Long banktransMaxNum = new Long("991");
        Long banktransMaxAmtSum = new Long("999999999992");
        Long monthBanktransMaxAmt = new Long("999999999993");
        Long monthBanktransMaxCnt= new Long("994");
        Long yearBanktransMaxAmt = new Long("999999999995");
        Long yearBanktransMaxCnt = new Long("996");
        String resvFld1 = "dele";
        String resvFld2 = "file";

        int oriDeleteCount =  tAccountBanktransAmtCntMapper.deleteByPrimaryKey(accountNo);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TAccountBanktransAmtCnt tAccountBanktransAmtCnt = getTAccountBanktransAmtCnt(accountNo, customerNo,rule,banktransMaxNum, banktransMaxAmtSum,
                monthBanktransMaxAmt, monthBanktransMaxCnt, yearBanktransMaxAmt, yearBanktransMaxCnt, resvFld1, resvFld2) ;
        int insertSelectCount = tAccountBanktransAmtCntMapper.insertSelective(tAccountBanktransAmtCnt);
        log.debug("insertSelectCount=[" + insertSelectCount + "]");
        assertThat(insertSelectCount,is(1));

        accountNo = "8631021000002406";
        tAccountBanktransAmtCntMapper.deleteByPrimaryKey(accountNo);
        tAccountBanktransAmtCnt = getTAccountBanktransAmtCnt(accountNo, null,rule,banktransMaxNum, banktransMaxAmtSum,
                monthBanktransMaxAmt, monthBanktransMaxCnt, yearBanktransMaxAmt, yearBanktransMaxCnt, resvFld1, resvFld2) ;

        int insertSelectCount2 = tAccountBanktransAmtCntMapper.insertSelective(tAccountBanktransAmtCnt);
        log.debug("insertSelectCount2=[" + insertSelectCount2 + "]");
        assertThat(insertSelectCount2,is(1));

        accountNo = "8631021000002407";
        tAccountBanktransAmtCntMapper.deleteByPrimaryKey(accountNo);
        tAccountBanktransAmtCnt = getTAccountBanktransAmtCnt(accountNo, customerNo,null,banktransMaxNum, banktransMaxAmtSum,
                monthBanktransMaxAmt, monthBanktransMaxCnt, yearBanktransMaxAmt, yearBanktransMaxCnt, resvFld1, resvFld2) ;

        int insertSelectCount3 = tAccountBanktransAmtCntMapper.insertSelective(tAccountBanktransAmtCnt);
        log.debug("insertSelectCount3=[" + insertSelectCount3 + "]");
        assertThat(insertSelectCount3,is(1));

        accountNo = "8631021000002408";
        tAccountBanktransAmtCntMapper.deleteByPrimaryKey(accountNo);
        tAccountBanktransAmtCnt = getTAccountBanktransAmtCnt(accountNo, customerNo,rule,null, banktransMaxAmtSum,
                monthBanktransMaxAmt, monthBanktransMaxCnt, yearBanktransMaxAmt, yearBanktransMaxCnt, resvFld1, resvFld2) ;
        int insertSelectCount4 = tAccountBanktransAmtCntMapper.insertSelective(tAccountBanktransAmtCnt);
        log.debug("insertSelectCount4=[" + insertSelectCount4 + "]");
        assertThat(insertSelectCount4,is(1));

        accountNo = "8631021000002409";
        tAccountBanktransAmtCntMapper.deleteByPrimaryKey(accountNo);
        tAccountBanktransAmtCnt = getTAccountBanktransAmtCnt(accountNo, customerNo,rule,banktransMaxNum, null,
                null, null, null, null, null, null) ;
        int insertSelectCount5 = tAccountBanktransAmtCntMapper.insertSelective(tAccountBanktransAmtCnt);
        log.debug("insertSelectCount5=[" + insertSelectCount5 + "]");
        assertThat(insertSelectCount5,is(1));


    }

    @Test
    public void testSelectByPrimaryKey(){

        String accountNo="8631021000002405";
        String customerNo="123778";
        String rule="1";
        Long banktransMaxNum = new Long("991");
        Long banktransMaxAmtSum = new Long("999999999992");
        Long monthBanktransMaxAmt = new Long("999999999993");
        Long monthBanktransMaxCnt= new Long("994");
        Long yearBanktransMaxAmt = new Long("999999999995");
        Long yearBanktransMaxCnt = new Long("996");
        String resvFld1 = "dele";
        String resvFld2 = "file";

        int oriDeleteCount =  tAccountBanktransAmtCntMapper.deleteByPrimaryKey(accountNo);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");

        TAccountBanktransAmtCnt tmpTAccountBanktransAmtCnt = tAccountBanktransAmtCntMapper.selectByPrimaryKey(accountNo);
        assertNull(tmpTAccountBanktransAmtCnt);
        log.debug("没有找到数据********");

        //进行插入操作
        TAccountBanktransAmtCnt tAccountBanktransAmtCnt = getTAccountBanktransAmtCnt(accountNo, customerNo,rule,banktransMaxNum, banktransMaxAmtSum,
                monthBanktransMaxAmt, monthBanktransMaxCnt, yearBanktransMaxAmt, yearBanktransMaxCnt, resvFld1, resvFld2) ;
        int insertCount = tAccountBanktransAmtCntMapper.insert(tAccountBanktransAmtCnt);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

        tAccountBanktransAmtCnt = tAccountBanktransAmtCntMapper.selectByPrimaryKey(accountNo);
        log.debug("accountNo=[" + tAccountBanktransAmtCnt.getAccountNo() + "]" );
        log.debug("customerNo=[" + tAccountBanktransAmtCnt.getCustomerNo() + "]");
        log.debug("rule=[" + tAccountBanktransAmtCnt.getRule() + "]");

        log.debug("banktransMaxNum=[" + tAccountBanktransAmtCnt.getBanktransMaxNum().toString() + "]");
        log.debug("banktransMaxAmtSum=[" + tAccountBanktransAmtCnt.getBanktransMaxAmtSum().toString()+ "]");
        log.debug("monthBanktransMaxAmt=[" + tAccountBanktransAmtCnt.getMonthBanktransMaxAmt() + "]");
        log.debug("monthBanktransMaxCnt=[" + tAccountBanktransAmtCnt.getMonthBanktransMaxCnt() + "]");
        log.debug("yearBanktransMaxAmt=[" + tAccountBanktransAmtCnt.getYearBanktransMaxAmt()+ "]");
        log.debug("yearBanktransMaxCnt=[" + tAccountBanktransAmtCnt.getYearBanktransMaxCnt() + "]");
        log.debug("resvFld1=[" + tAccountBanktransAmtCnt.getResvFld1()+ "]");
        log.debug("resvFld2=[" + tAccountBanktransAmtCnt.getResvFld2() + "]");

        assertThat(tAccountBanktransAmtCnt.getAccountNo(),is(accountNo));
        assertThat(tAccountBanktransAmtCnt.getCustomerNo(),is(customerNo));
        assertThat(tAccountBanktransAmtCnt.getRule(),is(rule));

        assertThat(tAccountBanktransAmtCnt.getBanktransMaxNum(),is(banktransMaxNum));
        assertThat(tAccountBanktransAmtCnt.getBanktransMaxAmtSum(),is(banktransMaxAmtSum));
        assertThat(tAccountBanktransAmtCnt.getMonthBanktransMaxAmt(),is(monthBanktransMaxAmt));
        assertThat(tAccountBanktransAmtCnt.getMonthBanktransMaxCnt(),is(monthBanktransMaxCnt));
        assertThat(tAccountBanktransAmtCnt.getYearBanktransMaxAmt(),is(yearBanktransMaxAmt));
        assertThat(tAccountBanktransAmtCnt.getYearBanktransMaxCnt(),is(yearBanktransMaxCnt));
        assertThat(tAccountBanktransAmtCnt.getResvFld1(),is(resvFld1));
        assertThat(tAccountBanktransAmtCnt.getResvFld2(),is(resvFld2));
    }

    @Test
    public void testUpdateByPrimaryKey(){

        String accountNo="8631021000002405";
        String customerNo="123778";
        String rule="1";
        Long banktransMaxNum = new Long("991");
        Long banktransMaxAmtSum = new Long("999999999992");
        Long monthBanktransMaxAmt = new Long("999999999993");
        Long monthBanktransMaxCnt= new Long("994");
        Long yearBanktransMaxAmt = new Long("999999999995");
        Long yearBanktransMaxCnt = new Long("996");
        String resvFld1 = "dele";
        String resvFld2 = "file";

        int oriDeleteCount =  tAccountBanktransAmtCntMapper.deleteByPrimaryKey(accountNo);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
//
        TAccountBanktransAmtCnt tAccountBanktransAmtCnt = getTAccountBanktransAmtCnt(accountNo, customerNo,rule,banktransMaxNum, banktransMaxAmtSum,
                monthBanktransMaxAmt, monthBanktransMaxCnt, yearBanktransMaxAmt, yearBanktransMaxCnt, resvFld1, resvFld2) ;
        int tmpUpdateCount = tAccountBanktransAmtCntMapper.updateByPrimaryKey(tAccountBanktransAmtCnt);
        log.debug("tmpUpdateCount=[" + tmpUpdateCount + "]");
        assertThat(tmpUpdateCount,is(0));

        //进行插入操作
        int insertCount = tAccountBanktransAmtCntMapper.insert(tAccountBanktransAmtCnt);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

        customerNo="323778";
        rule="3";
        banktransMaxNum = new Long("391");
        banktransMaxAmtSum = new Long("399999999992");
        monthBanktransMaxAmt = new Long("399999999993");
        monthBanktransMaxCnt= new Long("394");
        yearBanktransMaxAmt = new Long("399999999995");
        yearBanktransMaxCnt = new Long("396");
        resvFld1 = "3ele";
        resvFld2 = "3ile";

        tAccountBanktransAmtCnt = getTAccountBanktransAmtCnt(accountNo, customerNo,rule,banktransMaxNum, banktransMaxAmtSum,
                monthBanktransMaxAmt, monthBanktransMaxCnt, yearBanktransMaxAmt, yearBanktransMaxCnt, resvFld1, resvFld2) ;
       tmpUpdateCount = tAccountBanktransAmtCntMapper.updateByPrimaryKey(tAccountBanktransAmtCnt);
        log.debug("tmpUpdateCount=[" + tmpUpdateCount + "]");
        assertThat(tmpUpdateCount,is(1));


    }

    @Test
    public void testUpdateByPrimaryKeySelective(){

        String accountNo="8631021000002405";
        String customerNo="123778";
        String rule="1";
        Long banktransMaxNum = new Long("991");
        Long banktransMaxAmtSum = new Long("999999999992");
        Long monthBanktransMaxAmt = new Long("999999999993");
        Long monthBanktransMaxCnt= new Long("994");
        Long yearBanktransMaxAmt = new Long("999999999995");
        Long yearBanktransMaxCnt = new Long("996");
        String resvFld1 = "dele";
        String resvFld2 = "file";

        int oriDeleteCount =  tAccountBanktransAmtCntMapper.deleteByPrimaryKey(accountNo);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");

        TAccountBanktransAmtCnt tAccountBanktransAmtCnt = getTAccountBanktransAmtCnt(accountNo, customerNo,rule,banktransMaxNum, banktransMaxAmtSum,
                monthBanktransMaxAmt, monthBanktransMaxCnt, yearBanktransMaxAmt, yearBanktransMaxCnt, resvFld1, resvFld2) ;
        int tmpUpdateCount = tAccountBanktransAmtCntMapper.updateByPrimaryKeySelective(tAccountBanktransAmtCnt);
        log.debug("tmpUpdateCount=[" + tmpUpdateCount + "]");
        assertThat(tmpUpdateCount,is(0));

//        进行插入操作
        int insertCount = tAccountBanktransAmtCntMapper.insert(tAccountBanktransAmtCnt);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

        customerNo="453778";
        rule="5";
        banktransMaxNum = new Long("451");
        banktransMaxAmtSum = new Long("459999999992");
        monthBanktransMaxAmt = new Long("459999999993");
        monthBanktransMaxCnt= new Long("454");
        yearBanktransMaxAmt = new Long("459999999995");
        yearBanktransMaxCnt = new Long("456");
        resvFld1 = "95le";
        resvFld2 = "95le";

        tAccountBanktransAmtCnt = getTAccountBanktransAmtCnt(accountNo, customerNo,rule,banktransMaxNum, banktransMaxAmtSum,
                monthBanktransMaxAmt, monthBanktransMaxCnt, yearBanktransMaxAmt, yearBanktransMaxCnt, resvFld1, resvFld2) ;
        tmpUpdateCount = tAccountBanktransAmtCntMapper.updateByPrimaryKeySelective(tAccountBanktransAmtCnt);
        log.debug("tmpUpdateCount=[" + tmpUpdateCount + "]");
        assertThat(tmpUpdateCount,is(1));

        customerNo = "843778";
        rule="4";
        banktransMaxNum = null;
        banktransMaxAmtSum = new Long("549999999992");
        monthBanktransMaxAmt = new Long("649999999993");
        monthBanktransMaxCnt= new Long("644");
        yearBanktransMaxAmt = new Long("649999999995");
        yearBanktransMaxCnt = new Long("646");
        resvFld1 = "44le";
        resvFld2 = "44le";

        accountNo = "8631021000002406";
        int oriDeleteCount2 = tAccountBanktransAmtCntMapper.deleteByPrimaryKey(accountNo);
        tAccountBanktransAmtCnt.setAccountNo(accountNo);
        log.debug("oriDeleteCount2=[" + oriDeleteCount2 + "]");
        insertCount = tAccountBanktransAmtCntMapper.insert(tAccountBanktransAmtCnt);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));
        tAccountBanktransAmtCnt = getTAccountBanktransAmtCnt(accountNo, customerNo,rule,banktransMaxNum, banktransMaxAmtSum,
                monthBanktransMaxAmt, monthBanktransMaxCnt, yearBanktransMaxAmt, yearBanktransMaxCnt, resvFld1, resvFld2) ;
        int tmpUpdateCount2 = tAccountBanktransAmtCntMapper.updateByPrimaryKeySelective(tAccountBanktransAmtCnt);
        log.debug("tmpUpdateCount2=[" + tmpUpdateCount2 + "]");
        assertThat(tmpUpdateCount2,is(1));
    }

    public TAccountBanktransAmtCnt getTAccountBanktransAmtCnt(String accountNo, String customerNo, String rule, Long banktransMaxNum, Long banktransMaxAmtSum, Long monthBanktransMaxAmt, Long monthBanktransMaxCnt, Long yearBanktransMaxAmt, Long yearBanktransMaxCnt, String resvFld1, String resvFld2) {
        TAccountBanktransAmtCnt tAccountBanktransAmtCnt = new TAccountBanktransAmtCnt();

        tAccountBanktransAmtCnt.setAccountNo(accountNo);
        tAccountBanktransAmtCnt.setCustomerNo(customerNo);
        tAccountBanktransAmtCnt.setRule(rule);
        tAccountBanktransAmtCnt.setBanktransMaxNum(banktransMaxNum);
        tAccountBanktransAmtCnt.setBanktransMaxAmtSum(banktransMaxAmtSum);
        tAccountBanktransAmtCnt.setMonthBanktransMaxAmt(monthBanktransMaxAmt);
        tAccountBanktransAmtCnt.setMonthBanktransMaxCnt(monthBanktransMaxCnt);
        tAccountBanktransAmtCnt.setYearBanktransMaxAmt(yearBanktransMaxAmt);
        tAccountBanktransAmtCnt.setYearBanktransMaxCnt(yearBanktransMaxCnt);
        tAccountBanktransAmtCnt.setResvFld1(resvFld1);
        tAccountBanktransAmtCnt.setResvFld2(resvFld2);
        return tAccountBanktransAmtCnt;
    }


}
