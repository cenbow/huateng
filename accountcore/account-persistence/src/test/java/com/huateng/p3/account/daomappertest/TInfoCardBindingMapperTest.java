package com.huateng.p3.account.daomappertest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TInfoCardBindingMapper;
import com.huateng.p3.account.persistence.models.TInfoCardBinding;

/**
 * Created with IntelliJ IDEA.
 * User: wangshushuang
 * Date: 13-12-10
 * Time: 下午5:25
 * To change this template use File | Settings | File Templates.
 */
public class TInfoCardBindingMapperTest extends BaseSpringTest {

    private static Logger log =  LoggerFactory.getLogger(TDictAreaCityMapperTest.class);

    @Autowired
    private TInfoCardBindingMapper tInfoCardBindingMapper;

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

        String cardbindid = "920130503100702991";
        String pactno = "201305021005011";
        String bindingtype = "01";
        String cartype = "02";
        String accountid = "8631472002139063";
        String merchantno = "3100000036";
        String productno = "8630472002139063";
        String inttxntm = "20130502102210";
        String username = "张雯君";
        String ids = "430225198702282521";
        String cardno = "12345678900987654321";
        String cvv2 = "1234";
        String cardvalidity = "0214";
        String bingdingname = "中信银行---0123";
        String cardstatus = "00";
        String tacitly = "Y";
        String channel = "02";
        String opencusstatus = "q";
        String productNo = "q";
        String bankCode = "cbbc";
        String supplyOrgCode = "1";
        Long dayAmt = new Long(12);
        Long monthAmt = new Long(13);
        Date lastUpdateTime = new Date();

         int oriDeleteCount =  tInfoCardBindingMapper.deleteByPrimaryKey(cardbindid);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TInfoCardBinding tInfoCardBinding = getTInfoCardBinding(cardbindid,pactno,bindingtype,cartype, accountid, merchantno, productno,inttxntm, username, ids,cardno,
                cvv2, cardvalidity,  bingdingname, cardstatus, tacitly,channel,opencusstatus,productNo, bankCode,
                supplyOrgCode, dayAmt,monthAmt,lastUpdateTime) ;
        int insertCount = tInfoCardBindingMapper.insert(tInfoCardBinding);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

    }

    @Test
    public void testUpdateCardBinding(){

        String cardbindid = "920130503100702991";
        String pactno = "201305021005011";
        String bindingtype = "01";
        String cartype = "02";
        String accountid = "8631472002139063";
        String merchantno = "3100000036";
        String productno = "8630472002139063";
        String inttxntm = "20130502102210";
        String username = "张雯君";
        String ids = "430225198702282521";
        String cardno = "12345678900987654321";
        String cvv2 = "1234";
        String cardvalidity = "0214";
        String bingdingname = "中信银行---0123";
        String cardstatus = "00";
        String tacitly = "Y";
        String channel = "02";
        String opencusstatus = "q";
        String productNo = "q";
        String bankCode = "cbbc";
        String supplyOrgCode = "1";
        Long dayAmt = new Long(12);
        Long monthAmt = new Long(13);
        Date lastUpdateTime = new Date();

        int oriDeleteCount =  tInfoCardBindingMapper.deleteByPrimaryKey(cardbindid);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TInfoCardBinding tInfoCardBinding = getTInfoCardBinding(cardbindid,pactno,bindingtype,cartype, accountid, merchantno, productno,inttxntm, username, ids,cardno,
                cvv2, cardvalidity,  bingdingname, cardstatus, tacitly,channel,opencusstatus,productNo, bankCode,
                supplyOrgCode, dayAmt,monthAmt,lastUpdateTime) ;
        int insertCount = tInfoCardBindingMapper.insert(tInfoCardBinding);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

        String newProductNo = "8630472002139065";
        int updateCount = tInfoCardBindingMapper.updateCardBinding(newProductNo,productNo);
        assertThat(updateCount,is(1));

    }

    public TInfoCardBinding getTInfoCardBinding(String cardbindid, String pactno, String bindingtype, String cartype, String accountid, String merchantno, String productno, String inttxntm, String username, String ids, String cardno,
                                                String cvv2, String cardvalidity, String bingdingname, String cardstatus, String tacitly, String channel, String opencusstatus, String productNo, String bankCode,
                                                String supplyOrgCode, Long dayAmt, Long monthAmt,Date lastUpdateTime) {
        TInfoCardBinding tInfoCardBinding = new TInfoCardBinding();
        tInfoCardBinding.setCardbindid(cardbindid);
        tInfoCardBinding.setPactno(pactno);
        tInfoCardBinding.setBindingtype(bindingtype);
        tInfoCardBinding.setCartype(cartype);
        tInfoCardBinding.setAccountid(accountid);
        tInfoCardBinding.setMerchantno(merchantno);
        tInfoCardBinding.setProductno(productno);
        tInfoCardBinding.setInttxntm(inttxntm);
        tInfoCardBinding.setUsername(username);
        tInfoCardBinding.setIds(ids);
        tInfoCardBinding.setCardno(cardno);
        tInfoCardBinding.setCvv2(cvv2);
        tInfoCardBinding.setCardvalidity(cardvalidity);
        tInfoCardBinding.setBingdingname(bingdingname);
        tInfoCardBinding.setCardstatus(cardstatus);
        tInfoCardBinding.setTacitly(tacitly);
        tInfoCardBinding.setChannel(channel);
        tInfoCardBinding.setOpencusstatus(opencusstatus);
        tInfoCardBinding.setProductNo(productNo);
        tInfoCardBinding.setBankCode(bankCode);
        tInfoCardBinding.setSupplyOrgCode(supplyOrgCode);
        tInfoCardBinding.setDayAmt(dayAmt);
        tInfoCardBinding.setMonthAmt(monthAmt);
        tInfoCardBinding.setLastUpdateTime(lastUpdateTime);

        return tInfoCardBinding;
    }
}
