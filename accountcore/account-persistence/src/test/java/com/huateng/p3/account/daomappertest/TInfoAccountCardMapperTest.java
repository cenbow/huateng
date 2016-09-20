package com.huateng.p3.account.daomappertest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TInfoAccountCardMapper;
import com.huateng.p3.account.persistence.models.TInfoAccountCard;

/**
 * Created with IntelliJ IDEA.
 * User: wangshushuang
 * Date: 13-12-6
 * Time: 上午9:14
 * To change this template use File | Settings | File Templates.
 */
public class TInfoAccountCardMapperTest extends BaseSpringTest {

    private static Logger log =  LoggerFactory.getLogger(TInfoAccountCardMapperTest.class);

    @Autowired
    private TInfoAccountCardMapper tInfoAccountCardMapper;

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

//        Random rand = new Random();
//        System.out.println(rand.nextInt(100000));
        Long recordNo = new Long(9488);
        String customerNo = "8630898000000344";
//        String offlineAccountNo = "8632898000000347";
        String offlineAccountNo = "86328980770003" +  new Random().nextInt(100000) ;
        String fundAccountNo = "8631898000000345";
        String cardNo = "8000000700003012" + new Random().nextInt(100000) ;
        String cardType = "6";
        String bindingFlag = "1";
        String bindingMehod = "1";
        Date bindingTime = new Date();
        Date unbindingTime = new Date();
        String bindingAcceptOrgCode = "103460100000001";
        String bingdingAcceptUid = "0000000000000338";
        Date bindingAcceptTime = new Date();
        String unbindingAcceptOrgCode = "123";
        String unbingdingAcceptUid = "234";
        Date unbindingAcceptTime = new Date();
        String areaCode = "460000";
        String cityCode = "460100";

        int oriDeleteCount =  tInfoAccountCardMapper.deleteByPrimaryKey(recordNo);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TInfoAccountCard tInfoAccountCard = getTInfoAccountCard(recordNo,customerNo,offlineAccountNo,fundAccountNo,
                cardNo, cardType,bindingFlag,bindingMehod,bindingTime,
                unbindingTime, bindingAcceptOrgCode,bingdingAcceptUid, bindingAcceptTime, unbindingAcceptOrgCode,
                unbingdingAcceptUid,  unbindingAcceptTime, areaCode,cityCode);
        int insertCount = tInfoAccountCardMapper.insertSelective(tInfoAccountCard);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

    }

    /**
     * 查询通过卡号
     */
    @Test
    public void testFindAccountCardByCardNo(){

        //bindingFlag 已1为条件去查，是否合格
        Long recordNo = new Long(9488);
        String customerNo = "8630898000000344";
        String offlineAccountNo = "8632898000000346";
        String fundAccountNo = "8631898000000345";
        String cardNo = "8000000700003012" + new Random().nextInt(100000) ;
//        String cardNo = "8000000700003012" ;
        String cardType = "6";
        String bindingFlag = "1";
        String bindingMehod = "1";
        Date bindingTime = new Date();
        Date unbindingTime = new Date();
        String bindingAcceptOrgCode = "103460100000001";
        String bingdingAcceptUid = "0000000000000338";
        Date bindingAcceptTime = new Date();
        String unbindingAcceptOrgCode = "123";
        String unbingdingAcceptUid = "234";
        Date unbindingAcceptTime = new Date();
        String areaCode = "460000";
        String cityCode = "460100";

        TInfoAccountCard tInfoAccountCard = tInfoAccountCardMapper.findAccountCardByCardNo(cardNo);
        if(null == tInfoAccountCard){

            int oriDeleteCount =  tInfoAccountCardMapper.deleteByPrimaryKey(recordNo);
            log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
            log.debug("如果为0,则以前无值,如果为1,则以前有值");
            //进行插入操作
            tInfoAccountCard = getTInfoAccountCard(recordNo,customerNo,offlineAccountNo,fundAccountNo,
                    cardNo, cardType,bindingFlag,bindingMehod,bindingTime,
                    unbindingTime, bindingAcceptOrgCode,bingdingAcceptUid, bindingAcceptTime, unbindingAcceptOrgCode,
                    unbingdingAcceptUid,  unbindingAcceptTime, areaCode,cityCode);
            int insertCount = tInfoAccountCardMapper.insertSelective(tInfoAccountCard);
            log.debug("insertCount=[" + insertCount + "]");
            assertThat(insertCount,is(1));
            tInfoAccountCard = tInfoAccountCardMapper.findAccountCardByCardNo(cardNo);
            log.debug("recordNo=[" + tInfoAccountCard.getRecordNo() + "]");
            log.debug("customerNo=[" + tInfoAccountCard.getCustomerNo() + "]" );
            assertThat(tInfoAccountCard.getRecordNo(),is(recordNo));
            assertThat(tInfoAccountCard.getCustomerNo(),is(customerNo));
            assertThat(tInfoAccountCard.getCardNo(),is(cardNo));
        }else {

            log.debug("recordNo=[" + tInfoAccountCard.getRecordNo() + "]");
            log.debug("cardNo=[" + tInfoAccountCard.getCardNo() + "]");
            assertThat(tInfoAccountCard.getCardNo(),is(cardNo));
        }

    }

    @Test
    public void testFindAccountCardByAccountNo(){

        Long recordNo = new Long(9488);
        String customerNo = "8630898000000344";
//        String offlineAccountNo = "8632898000000347";
        String offlineAccountNo = "86328980770003" +  new Random().nextInt(100000) ;
        String fundAccountNo = "8631898000000345";
        String cardNo = "8000000700003012" + new Random().nextInt(100000) ;
        String cardType = "6";
        String bindingFlag = "1";
        String bindingMehod = "1";
        Date bindingTime = new Date();
        Date unbindingTime = new Date();
        String bindingAcceptOrgCode = "103460100000001";
        String bingdingAcceptUid = "0000000000000338";
        Date bindingAcceptTime = new Date();
        String unbindingAcceptOrgCode = "123";
        String unbingdingAcceptUid = "234";
        Date unbindingAcceptTime = new Date();
        String areaCode = "460000";
        String cityCode = "460100";

        int oriDeleteCount =  tInfoAccountCardMapper.deleteByPrimaryKey(recordNo);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TInfoAccountCard tInfoAccountCard = getTInfoAccountCard(recordNo,customerNo,offlineAccountNo,fundAccountNo,
                cardNo, cardType,bindingFlag,bindingMehod,bindingTime,
                unbindingTime, bindingAcceptOrgCode,bingdingAcceptUid, bindingAcceptTime, unbindingAcceptOrgCode,
                unbingdingAcceptUid,  unbindingAcceptTime, areaCode,cityCode);
        int insertCount = tInfoAccountCardMapper.insertSelective(tInfoAccountCard);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

        //如果offlineaccountNo数据超过一条，会报错
//        String offlineAccountNo = "8632898000000346";
        tInfoAccountCard = tInfoAccountCardMapper.findAccountCardByAccountNo(offlineAccountNo);
        assertThat(tInfoAccountCard.getOfflineAccountNo(),is(offlineAccountNo));
    }


    @Test
    public void testFindLinkCardNo(){

        String fundAccountNo = "8631898000000345";
//        String fundAccountNo = "8631898000000349888";
        List<String> cardNoList = tInfoAccountCardMapper.findLinkCardNo(fundAccountNo);
        log.debug("cardNoList.size()=[" + cardNoList.size() + "]");
        for(String cardNo : cardNoList){
            log.debug("cardNo=[" + cardNo + "]");
        }

    }

    @Test
    public void testCheckClosedCustomerByCardNo(){

        Long recordNo = new Long(9488);
        String customerNo = "8630898000000344";
        String offlineAccountNo = "8632898000000347";
        String fundAccountNo = "8631898000000345";
        String cardNo = "8000000700003012" + new Random().nextInt(100000) ;
        String tmpCardNo = "X_" + cardNo + "_" + getDateFormat();
        String cardType = "6";
        String bindingFlag = "1";
        String bindingMehod = "1";
        Date bindingTime = new Date();
        Date unbindingTime = new Date();
        String bindingAcceptOrgCode = "103460100000001";
        String bingdingAcceptUid = "0000000000000338";
        Date bindingAcceptTime = new Date();
        String unbindingAcceptOrgCode = "123";
        String unbingdingAcceptUid = "234";
        Date unbindingAcceptTime = new Date();
        String areaCode = "460000";
        String cityCode = "460100";

        int oriDeleteCount =  tInfoAccountCardMapper.deleteByPrimaryKey(recordNo);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TInfoAccountCard tInfoAccountCard = getTInfoAccountCard(recordNo,customerNo,offlineAccountNo,fundAccountNo,
                tmpCardNo, cardType,bindingFlag,bindingMehod,bindingTime,
                unbindingTime, bindingAcceptOrgCode,bingdingAcceptUid, bindingAcceptTime, unbindingAcceptOrgCode,
                unbingdingAcceptUid,  unbindingAcceptTime, areaCode,cityCode);
        int insertCount = tInfoAccountCardMapper.insertSelective(tInfoAccountCard);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

//        cardNo = "0000000000000058";
        int checkCount = tInfoAccountCardMapper.checkClosedCustomerByCardNo(cardNo);
        log.debug("checkCount=[" + checkCount + "]");
        assertThat(checkCount,is(1));

    }

    @Test
    public void testUnbindingCardByOfflineAccountNo(){

        Long recordNo = new Long(9488);
        String customerNo = "8630898000000349";
        String offlineAccountNo = "86328980770003" +  new Random().nextInt(100000) ;
        String fundAccountNo = "8631898000000345";
        String cardNo = "9000000700003012" + new Random().nextInt(100000) ;
        String cardType = "6";
        String bindingFlag = "1";
        String bindingMehod = "1";
        Date bindingTime = new Date();
        Date unbindingTime = new Date();
        String bindingAcceptOrgCode = "103460100000001";
        String bingdingAcceptUid = "0000000000000338";
        Date bindingAcceptTime = new Date();
        String unbindingAcceptOrgCode = "123";
        String unbingdingAcceptUid = "234";
        Date unbindingAcceptTime = new Date();
        String areaCode = "460000";
        String cityCode = "460100";

        int oriDeleteCount =  tInfoAccountCardMapper.deleteByPrimaryKey(recordNo);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TInfoAccountCard tInfoAccountCard = getTInfoAccountCard(recordNo,customerNo,offlineAccountNo,fundAccountNo,
                cardNo, cardType,bindingFlag,bindingMehod,bindingTime,
                unbindingTime, bindingAcceptOrgCode,bingdingAcceptUid, bindingAcceptTime, unbindingAcceptOrgCode,
                unbingdingAcceptUid,  unbindingAcceptTime, areaCode,cityCode);
        int insertCount = tInfoAccountCardMapper.insertSelective(tInfoAccountCard);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

//        CARD_NO='X_'||CARD_NO||'_'||to_char(sysdate,'yyyyMMdd'),
//                BINDING_FLAG='2',
//                UNBINDING_ACCEPT_ORG_CODE=#{unbindingAcceptOrgCode,jdbcType=VARCHAR},
//        UNBINGDING_ACCEPT_UID=#{unbingdingAcceptUid,jdbcType=VARCHAR},
//        UNBINDING_ACCEPT_TIME=#{unbindingAcceptTime,jdbcType=TIMESTAMP},
//        UNBINDING_TIME=#{unbindingTime,jdbcType=TIMESTAMP}

        tInfoAccountCard.setUnbindingAcceptOrgCode("aaa");
        tInfoAccountCard.setUnbingdingAcceptUid("bbb");
        tInfoAccountCard.setUnbindingAcceptTime(new Date());
        tInfoAccountCard.setUnbindingTime(new Date());
        int updateCount = tInfoAccountCardMapper.unbindingCardByOfflineAccountNo(tInfoAccountCard);
        log.debug("updateCount=[" + updateCount + "]");
        assertThat(updateCount,is(1));
    }

    
    public TInfoAccountCard getTInfoAccountCard(Long recordNo, String customerNo, String offlineAccountNo, String fundAccountNo,
                                                String cardNo, String cardType, String bindingFlag, String bindingMehod, Date bindingTime,
                                                Date unbindingTime, String bindingAcceptOrgCode, String bingdingAcceptUid, Date bindingAcceptTime, String unbindingAcceptOrgCode,
                                                String unbingdingAcceptUid, Date unbindingAcceptTime, String areaCode, String cityCode) {

        TInfoAccountCard tInfoAccountCard = new TInfoAccountCard();
        tInfoAccountCard.setRecordNo(recordNo);
        tInfoAccountCard.setCustomerNo(customerNo);
        tInfoAccountCard.setOfflineAccountNo(offlineAccountNo);
        tInfoAccountCard.setFundAccountNo(fundAccountNo);
        tInfoAccountCard.setCardNo(cardNo);
        tInfoAccountCard.setCardType(cardType);
        tInfoAccountCard.setBindingFlag(bindingFlag);
        tInfoAccountCard.setBindingMehod(bindingMehod);
        tInfoAccountCard.setBindingTime(bindingTime);
        tInfoAccountCard.setUnbindingTime(unbindingTime);
        tInfoAccountCard.setBindingAcceptOrgCode(bindingAcceptOrgCode);
        tInfoAccountCard.setBingdingAcceptUid(bingdingAcceptUid);
        tInfoAccountCard.setBindingAcceptTime(bindingAcceptTime);
        tInfoAccountCard.setUnbindingAcceptOrgCode(unbindingAcceptOrgCode);
        tInfoAccountCard.setUnbingdingAcceptUid(unbingdingAcceptUid);
        tInfoAccountCard.setUnbindingAcceptTime(unbindingAcceptTime);
        tInfoAccountCard.setAreaCode(areaCode);
        tInfoAccountCard.setCityCode(cityCode);

        return tInfoAccountCard;
    }

    public String getDateFormat(){

        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
//        System.out.println(f.format(new Date()));
        return f.format(new Date());


    }




}
