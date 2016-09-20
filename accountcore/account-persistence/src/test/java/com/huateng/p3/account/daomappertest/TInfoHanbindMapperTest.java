package com.huateng.p3.account.daomappertest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TInfoHanbindMapper;
import com.huateng.p3.account.persistence.models.TInfoHanbind;

/**
 * Created with IntelliJ IDEA.
 * User: wangshushuang
 * Date: 13-12-10
 * Time: 下午4:34
 * To change this template use File | Settings | File Templates.
 */
public class TInfoHanbindMapperTest extends BaseSpringTest {

    private static Logger log =  LoggerFactory.getLogger(TInfoHanbindMapperTest.class);

    @Autowired
    private TInfoHanbindMapper tInfoHanbindMapper;


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

        String merCode = "111310049000000";
        String intTxnTm = "20130709091315";
        String productNo = "8630027000001799" + new Random().nextInt(100000);
        String cardBindId = "42";
        String cardType = "01";
        String cardNo = "7662";
        String bindingName = "招商银行(7662)";
        String status = "1";
        String tacitly = "1";

        log.debug("productNo=[" + productNo + "]");
        //进行插入操作
        TInfoHanbind tInfoHanbind = getTInfoHanbind(merCode, intTxnTm, productNo, cardBindId,
                cardType, cardNo, bindingName, status, tacitly);
            int insertCount = tInfoHanbindMapper.insert(tInfoHanbind);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

    }

    @Test
    public void testDebindInfoBankHan(){

        String merCode = "111310049000000";
        String intTxnTm = "20130709091315";
        String productNo = "8630027000001799" + new Random().nextInt(100000);
        String cardBindId = "42";
        String cardType = "01";
        String cardNo = "7662";
        String bindingName = "招商银行(7662)";
        String status = "2";
        String tacitly = "1";

        log.debug("productNo=[" + productNo + "]");
        //进行插入操作
        TInfoHanbind tInfoHanbind = getTInfoHanbind(merCode, intTxnTm, productNo, cardBindId,
                cardType, cardNo, bindingName, status, tacitly);
        int insertCount = tInfoHanbindMapper.insert(tInfoHanbind);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));
        int updateCount = tInfoHanbindMapper.debindInfoBankHan(productNo);
        log.debug("updateCount=[" + updateCount + "]");
        assertThat(updateCount,is(1));


    }



    public TInfoHanbind getTInfoHanbind(String merCode, String intTxnTm, String productNo, String cardBindId, String cardType, String cardNo, String bindingName, String status, String tacitly) {
        TInfoHanbind tInfoHanbind = new TInfoHanbind();
        tInfoHanbind.setMerCode(merCode);
        tInfoHanbind.setIntTxnTm(intTxnTm);
        tInfoHanbind.setProductNo(productNo);
        tInfoHanbind.setCardBindId(cardBindId);
        tInfoHanbind.setCardType(cardType);
        tInfoHanbind.setCardNo(cardNo);
        tInfoHanbind.setBindingName(bindingName);
        tInfoHanbind.setStatus(status);
        tInfoHanbind.setTacitly(tacitly);
        return tInfoHanbind;
    }

}
