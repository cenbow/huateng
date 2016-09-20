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
import com.huateng.p3.account.persistence.TbPosInfoMapper;
import com.huateng.p3.account.persistence.models.TbPosInfo;
import com.huateng.p3.account.persistence.models.TbPosInfoKey;

/**
 * add junit test
 * User: wangshushuang
 * Date: 13-12-5
 * Time: 下午2:14
 * To change this template use File | Settings | File Templates.
 */
public class TbPosInfoMapperTest extends BaseSpringTest {

    private static Logger log =  LoggerFactory.getLogger(TAccountBanktransAmtCntMapperTest.class);

    @Autowired
    private TbPosInfoMapper tbPosInfoMapper;

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

        String merchantCode = "222222222222228";
        String posCode = "00199991";
        String posTmk = "1";
        String posPinkey = "2555427FF8F9C833";
        String posMackey = "1";
        String posStatus = "1";
        String posPinSeq = "111";

        int oriDeleteCount =  tbPosInfoMapper.deleteByPrimaryKey(getTbPosInfoKey(merchantCode,posCode
        ));
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
         TbPosInfo tbPosInfo = getTbPosInfo(merchantCode,posCode,posTmk,posPinkey,posMackey,posStatus,posPinSeq) ;
         int insertCount = tbPosInfoMapper.insert(tbPosInfo);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

    }

    /**
     * 删除按照主键
     */
    @Test
    public void testDeleteByPrimaryKey(){

        String merchantCode = "222222222222228";
        String posCode = "00199991";
        String posTmk = "1";
        String posPinkey = "2555427FF8F9C833";
        String posMackey = "1";
        String posStatus = "1";
        String posPinSeq = "111";

        int oriDeleteCount =  tbPosInfoMapper.deleteByPrimaryKey(getTbPosInfoKey(merchantCode,posCode
        ));
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
//        //进行插入操作
        TbPosInfo tbPosInfo = getTbPosInfo(merchantCode,posCode,posTmk,posPinkey,posMackey,posStatus,posPinSeq) ;
        int insertCount = tbPosInfoMapper.insert(tbPosInfo);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

        int deleteCount =  tbPosInfoMapper.deleteByPrimaryKey(getTbPosInfoKey(merchantCode,posCode
        ));
        log.debug("deleteCount=[" + deleteCount + "]");
        assertThat(deleteCount,is(1));
    }

    /**
     * 按照选择插入
     */
    @Test
    public void testInsertSelective(){

        String merchantCode = "222222222222228";
        String posCode = "00199991";
        String posTmk = "2";
        String posPinkey = "2555427FF8F9C834";
        String posMackey = "2";
        String posStatus = "2";
        String posPinSeq = "112";

        int oriDeleteCount =  tbPosInfoMapper.deleteByPrimaryKey(getTbPosInfoKey(merchantCode,posCode
        ));
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TbPosInfo tbPosInfo = getTbPosInfo(merchantCode,posCode,posTmk,posPinkey,posMackey,posStatus,posPinSeq) ;
        int insertCount = tbPosInfoMapper.insertSelective(tbPosInfo);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

        merchantCode = "222222222222229";
        posCode = "00199993";
        int oriDeleteCount2 =  tbPosInfoMapper.deleteByPrimaryKey(getTbPosInfoKey(merchantCode,posCode
        ));
        log.debug("oriDeleteCount2=[" + oriDeleteCount2 + "]");
//        tbPosInfo = getTbPosInfo(merchantCode,posCode,null,posPinkey,posMackey,posStatus,posPinSeq) ;
        tbPosInfo = getTbPosInfo(merchantCode,posCode,null,null,null,null,null) ;
        int insertCount2 = tbPosInfoMapper.insertSelective(tbPosInfo);
        log.debug("insertCount2=[" + insertCount2 + "]");
        assertThat(insertCount2,is(1));

    }

    /**
     * 查询按照主键
     */
    @Test
    public void testSelectByPrimaryKey(){

        String merchantCode = "222222222222229";
        String posCode = "00199992";
        String posTmk = "7";
        String posPinkey = "2555427FF8F9C837";
        String posMackey = "8";
        String posStatus = "9";
        String posPinSeq = "119";

        int oriDeleteCount =  tbPosInfoMapper.deleteByPrimaryKey(getTbPosInfoKey(merchantCode,posCode
        ));
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");

        TbPosInfo tbPosInfo = tbPosInfoMapper.selectByPrimaryKey(getTbPosInfoKey(merchantCode,posCode
        ));
        assertNull(tbPosInfo);
        log.debug("没有找到数据********");
        //进行插入操作
        tbPosInfo = getTbPosInfo(merchantCode,posCode,posTmk,posPinkey,posMackey,posStatus,posPinSeq) ;
        int insertCount = tbPosInfoMapper.insert(tbPosInfo);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

       tbPosInfo = tbPosInfoMapper.selectByPrimaryKey(getTbPosInfoKey(merchantCode,posCode
        ));

        log.debug("merchantCode=[" + tbPosInfo.getMerchantCode() + "]");
        log.debug("posCode=[" + tbPosInfo.getPosCode() + "]");
        log.debug("posTmk=[" + tbPosInfo.getPosTmk().trim() + "]");
        log.debug("posPinkey=[" + tbPosInfo.getPosPinkey().trim() + "]");
        log.debug("posMackey=[" + tbPosInfo.getPosMackey().trim()+ "]");
        log.debug("posStatus=[" + tbPosInfo.getPosStatus().trim() + "]");
        log.debug("posPinSeq=[" + tbPosInfo.getPosPinSeq().trim()+ "]");
        assertThat(tbPosInfo.getMerchantCode(),is(merchantCode));
        assertThat(tbPosInfo.getPosCode(),is(posCode));
        assertThat(tbPosInfo.getPosTmk().trim(),is(posTmk));
        assertThat(tbPosInfo.getPosPinkey().trim(),is(posPinkey));
        assertThat(tbPosInfo.getPosMackey().trim(),is(posMackey));
        assertThat(tbPosInfo.getPosStatus().trim(),is(posStatus));
        assertThat(tbPosInfo.getPosPinSeq().trim(),is(posPinSeq));
    }

    /**
     * 更新数据通过主键
     */
    @Test
    public void testUpdateByPrimaryKey(){

        String merchantCode = "222222222222228";
        String posCode = "00199991";
        String posTmk = "1";
        String posPinkey = "2555427FF8F9C833";
        String posMackey = "1";
        String posStatus = "1";
        String posPinSeq = "111";

        int oriDeleteCount =  tbPosInfoMapper.deleteByPrimaryKey(getTbPosInfoKey(merchantCode,posCode
        ));
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");

        TbPosInfo tbPosInfo = getTbPosInfo(merchantCode,posCode,posTmk,posPinkey,posMackey,posStatus,posPinSeq) ;

        int tmpUpdateCount = tbPosInfoMapper.updateByPrimaryKey(tbPosInfo);
        log.debug("tmpUpdateCount=[" + tmpUpdateCount + "]");
        assertThat(tmpUpdateCount,is(0));

        //进行插入操作
        int insertCount = tbPosInfoMapper.insert(tbPosInfo);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

        posTmk = "7";
        posPinkey = "2555427FF8F9C837";
        posMackey = "7";
        posStatus = "7";
        posPinSeq = "711";
        tbPosInfo = getTbPosInfo(merchantCode,posCode,posTmk,posPinkey,posMackey,posStatus,posPinSeq) ;
        tmpUpdateCount = tbPosInfoMapper.updateByPrimaryKey(tbPosInfo);
        log.debug("tmpUpdateCount=[" + tmpUpdateCount + "]");
        assertThat(tmpUpdateCount,is(1));


    }

    /**
     * 选择性修改数据通过主键
     */
    @Test
    public void testUpdateByPrimaryKeySelective(){

        String merchantCode = "222222222222228";
        String posCode = "00199991";
        String posTmk = "1";
        String posPinkey = "2555427FF8F9C833";
        String posMackey = "1";
        String posStatus = "1";
        String posPinSeq = "111";

        int oriDeleteCount =  tbPosInfoMapper.deleteByPrimaryKey(getTbPosInfoKey(merchantCode,posCode
        ));
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");

        TbPosInfo tbPosInfo = getTbPosInfo(merchantCode,posCode,posTmk,posPinkey,posMackey,posStatus,posPinSeq) ;

        int tmpUpdateCount = tbPosInfoMapper.updateByPrimaryKeySelective(tbPosInfo);
        log.debug("tmpUpdateCount=[" + tmpUpdateCount + "]");
        assertThat(tmpUpdateCount,is(0));

        //进行插入操作
        int insertCount = tbPosInfoMapper.insert(tbPosInfo);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

        posTmk = "9";
        posPinkey = "2555427FF8F9C839";
        posMackey = "9";
        posStatus = "9";
        posPinSeq = "911";
        tbPosInfo = getTbPosInfo(merchantCode,posCode,posTmk,posPinkey,posMackey,posStatus,posPinSeq) ;
        tmpUpdateCount = tbPosInfoMapper.updateByPrimaryKeySelective(tbPosInfo);
        log.debug("tmpUpdateCount=[" + tmpUpdateCount + "]");
        assertThat(tmpUpdateCount,is(1));

        merchantCode = "222222222222229";
        posCode = "00199992";
        int oriDeleteCount2 =  tbPosInfoMapper.deleteByPrimaryKey(getTbPosInfoKey(merchantCode,posCode
        ));
        log.debug("oriDeleteCount2=[" + oriDeleteCount2 + "]");
        tbPosInfo.setMerchantCode(merchantCode);
        tbPosInfo.setPosCode(posCode);
        int insertCount2 = tbPosInfoMapper.insert(tbPosInfo);
        log.debug("insertCount2=[" + insertCount2 + "]");
        assertThat(insertCount2,is(1));

        posTmk = null;
        posPinkey = "2555427FF8F9C835";
        posMackey = null;
        posStatus = null;
        posPinSeq = null;
        tbPosInfo = getTbPosInfo(merchantCode,posCode,posTmk,posPinkey,posMackey,posStatus,posPinSeq) ;
        int tmpUpdateCount2 = tbPosInfoMapper.updateByPrimaryKeySelective(tbPosInfo);
        log.debug("tmpUpdateCount2=[" + tmpUpdateCount2 + "]");
        assertThat(tmpUpdateCount2,is(1));
    }

    public TbPosInfo getTbPosInfo(String merchantCode, String posCode,String posTmk, String posPinkey, String posMackey, String posStatus, String posPinSeq) {
        TbPosInfo tbPosInfo = new TbPosInfo();
        tbPosInfo.setMerchantCode(merchantCode);
        tbPosInfo.setPosCode(posCode);
        tbPosInfo.setPosTmk(posTmk);
        tbPosInfo.setPosPinkey(posPinkey);
        tbPosInfo.setPosMackey(posMackey);
        tbPosInfo.setPosStatus(posStatus);
        tbPosInfo.setPosPinSeq(posPinSeq);
        return tbPosInfo;
    }

    public TbPosInfoKey getTbPosInfoKey(String merchantCode, String posCode){
        TbPosInfoKey tbPosInfoKey = new TbPosInfoKey();
        tbPosInfoKey.setMerchantCode(merchantCode);
        tbPosInfoKey.setPosCode(posCode);
        return tbPosInfoKey;
    }

}
