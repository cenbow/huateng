package com.huateng.p3.account.daomappertest;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TRiskBlackMerchantMapper;
import com.huateng.p3.account.persistence.models.TRiskBlackMerchant;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
public class TRiskBlackMerchantTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TRiskBlackMerchantTest.class);

    @Autowired
    private TRiskBlackMerchantMapper tRiskBlackMerchantMapper;

    /*
    int deleteByPrimaryKey(Short recordNo);

    int insert(TRiskBlackMerchant record);

    int insertSelective(TRiskBlackMerchant record);

    TRiskBlackMerchant selectByPrimaryKey(Short recordNo);

    int updateByPrimaryKeySelective(TRiskBlackMerchant record);

    int updateByPrimaryKey(TRiskBlackMerchant record);
     */

    /**
     *测试完成
     */
    @Test
    public void testinsert() {
        try {
            TRiskBlackMerchant merchant = new TRiskBlackMerchant();
            Date date = new Date();
            String s = "a";
            long recordNo = 987654332L;
            merchant.setRecordNo(recordNo);
            merchant.setMerchantCode("aaa");
            merchant.setCheckTime(date);
            merchant.setCheckUid(s);
            merchant.setArchiveTime(date);
            merchant.setArchivedFlag(s);
            merchant.setBlackTime(date);
            merchant.setCreateTime(date);
            merchant.setCreateUid(s);
            merchant.setEffectiveDate(date);
            merchant.setLastModifyTime(date);
            merchant.setLastModifyUid(s);
            merchant.setlRecordNo(recordNo);
            merchant.setRemark(s);
            merchant.setResvFld1(s);
            merchant.setResvFld2(s);
            merchant.setUseFlag(s);
            merchant.setValidDate(date);

            int i =  tRiskBlackMerchantMapper.insert(merchant);
            System.out.print("----------------------------插入TRiskBlackMerchant返回码:--:"+i);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     *测试完成
     */
    @Test
    public void testinsertSelective() {
        try {
            TRiskBlackMerchant merchant = new TRiskBlackMerchant();
            Date date = new Date();
            String s = "a";
            long recordNo = 987654333L;
            merchant.setRecordNo(recordNo);
            merchant.setMerchantCode("aaa");
            merchant.setCheckTime(date);
            merchant.setCheckUid(s);
            merchant.setArchiveTime(date);
            merchant.setArchivedFlag(s);
            merchant.setBlackTime(date);
            merchant.setCreateTime(date);
            merchant.setCreateUid(s);
            merchant.setEffectiveDate(date);
            merchant.setLastModifyTime(date);
            merchant.setLastModifyUid(s);
            merchant.setlRecordNo(recordNo);
            merchant.setRemark(s);
            merchant.setResvFld1(s);
            merchant.setResvFld2(s);
            merchant.setUseFlag(s);
            merchant.setValidDate(date);
            int i =  tRiskBlackMerchantMapper.insertSelective(merchant);
            System.out.print("----------------------------插入TRiskBlackMerchant返回码:--:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 已经测试完成
     */
    @Test
    public void testselectByPrimaryKey() {
        try {
            long recordNo = 987654333L;
            TRiskBlackMerchant merchant = tRiskBlackMerchantMapper.selectByPrimaryKey(recordNo);
            System.out.print("----------testselectByPrimaryKey返回:---------------"+merchant.getRecordNo()+merchant.getRemark());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     *测试完成
     */
    @Test
    public void testupdateByPrimaryKeySelective() {
        try {
            TRiskBlackMerchant merchant = new TRiskBlackMerchant();
            Date date = new Date();
            String s = "b";
            long recordNo = 987654333L;
            merchant.setRecordNo(recordNo);
            merchant.setMerchantCode("aaa");
            merchant.setCheckTime(date);
            merchant.setCheckUid(s);
            merchant.setArchiveTime(date);
            merchant.setArchivedFlag(s);
            merchant.setBlackTime(date);
            merchant.setCreateTime(date);
            merchant.setCreateUid(s);
            merchant.setEffectiveDate(date);
            merchant.setLastModifyTime(date);
            merchant.setLastModifyUid(s);
            merchant.setlRecordNo(recordNo);
            merchant.setRemark(s);
            merchant.setResvFld1(s);
            merchant.setResvFld2(s);
            merchant.setUseFlag(s);
            merchant.setValidDate(date);
            int i = tRiskBlackMerchantMapper.updateByPrimaryKeySelective(merchant);
            System.out.print("----------------------------updateByPrimaryKeySelective返回码:--:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     *测试完成
     */
    @Test
    public void testupdateByPrimaryKey() {
        try {
            TRiskBlackMerchant merchant = new TRiskBlackMerchant();
            Date date = new Date();
            String s = "c";
            long recordNo = 987654333L;
            merchant.setRecordNo(recordNo);
            merchant.setMerchantCode("aaa");
            merchant.setCheckTime(date);
            merchant.setCheckUid(s);
            merchant.setArchiveTime(date);
            merchant.setArchivedFlag(s);
            merchant.setBlackTime(date);
            merchant.setCreateTime(date);
            merchant.setCreateUid(s);
            merchant.setEffectiveDate(date);
            merchant.setLastModifyTime(date);
            merchant.setLastModifyUid(s);
            merchant.setlRecordNo(recordNo);
            merchant.setRemark(s);
            merchant.setResvFld1(s);
            merchant.setResvFld2(s);
            merchant.setUseFlag(s);
            merchant.setValidDate(date);
            int i=tRiskBlackMerchantMapper.updateByPrimaryKey(merchant);
            System.out.print("----------------------------testupdateByPrimaryKey返回码:--:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     *测试完成
     */
    @Test
    public void testdeleteByPrimaryKey() {
        try {
            long recordNo = 987654332;
            int i = tRiskBlackMerchantMapper.deleteByPrimaryKey(recordNo);
            System.out.print("----------------------------testdeleteByPrimaryKey返回码:--:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
