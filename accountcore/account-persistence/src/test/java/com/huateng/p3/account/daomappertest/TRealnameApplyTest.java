package com.huateng.p3.account.daomappertest;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TRealnameApplyMapper;
import com.huateng.p3.account.persistence.models.TRealnameApply;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
public class TRealnameApplyTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TRealnameApplyTest.class);

    @Autowired
    private TRealnameApplyMapper tRealnameApplyMapper;

    /*
    int deleteByPrimaryKey(Short recordNo);

    int insert(TRealnameApply record);

    int insertSelective(TRealnameApply record);

    TRealnameApply selectByPrimaryKey(Short recordNo);

    int updateByPrimaryKeySelective(TRealnameApply record);

    int updateByPrimaryKey(TRealnameApply record);
     */

    /**
     *测试完成
     */
    @Test
    public void testinsert() {
        try {
            TRealnameApply realname = new TRealnameApply();
            Date date = new Date();
            String s = "a";
            long recordNo = 987654334L;
            realname.setIdPic(s);
            realname.setIdNo(s);
            realname.setGrade(s);
            realname.setIdType(s);
            realname.setInputDate(date);
            realname.setInputUid(s);
            realname.setName(s);
            realname.setNationality(s);
            realname.setProductNo(s);
            realname.setLogIp(s);
            realname.setProfession(s);
            realname.setStatus(s);
            realname.setSerialNo(11L);
            realname.setTransType(s);
            realname.setAcceptChannel(s);
            realname.setCustomerNo(s);
            realname.setCertExpiryDate(s);
            realname.setAddress(s);
            realname.setCheckTime(date);
            realname.setCheckUid(s);
            realname.setArchiveTime(date);
            realname.setArchivedFlag(s);
            realname.setCreateTime(date);
            realname.setLastModifyTime(date);
            realname.setLastModifyUid(s);
            realname.setlRecordNo(recordNo);
            realname.setRemark(s);

            int i =  tRealnameApplyMapper.insert(realname);
            System.out.print("----------------------------插入TRealnameApply返回码:--:"+i);
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
            TRealnameApply realname = new TRealnameApply();
            Date date = new Date();
            String s = "a";
            long recordNo = 987654335L;
            realname.setIdPic(s);
            realname.setIdNo(s);
            realname.setGrade(s);
            realname.setIdType(s);
            realname.setInputDate(date);
            realname.setInputUid(s);
            realname.setName(s);
            realname.setNationality(s);
            realname.setProductNo(s);
            realname.setLogIp(s);
            realname.setProfession(s);
            realname.setStatus(s);
            realname.setSerialNo(11L);
            realname.setTransType(s);
            realname.setAcceptChannel(s);
            realname.setCustomerNo(s);
            realname.setCertExpiryDate(s);
            realname.setAddress(s);
            realname.setCheckTime(date);
            realname.setCheckUid(s);
            realname.setArchiveTime(date);
            realname.setArchivedFlag(s);
            realname.setCreateTime(date);
            realname.setLastModifyTime(date);
            realname.setLastModifyUid(s);
            realname.setlRecordNo(recordNo);
            realname.setRemark(s);
            int i =  tRealnameApplyMapper.insertSelective(realname);
            System.out.print("----------------------------插入TRealnameApply返回码:--:"+i);
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
            long recordNo = 987654334L;
            TRealnameApply realname = tRealnameApplyMapper.selectByPrimaryKey(recordNo);
            System.out.print("----------testselectByPrimaryKey返回:---------------" + realname.getIdNo() + realname.getRemark());
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
            TRealnameApply realname = new TRealnameApply();
            Date date = new Date();
            String s = "a";
            long recordNo = 987654335L;
            realname.setIdPic(s);
            realname.setIdNo(s);
            realname.setGrade(s);
            realname.setIdType(s);
            realname.setInputDate(date);
            realname.setInputUid(s);
            realname.setName(s);
            realname.setNationality(s);
            realname.setProductNo(s);
            realname.setLogIp(s);
            realname.setProfession(s);
            realname.setStatus(s);
            realname.setSerialNo(11L);
            realname.setTransType(s);
            realname.setAcceptChannel(s);
            realname.setCustomerNo(s);
            realname.setCertExpiryDate(s);
            realname.setAddress(s);
            realname.setCheckTime(date);
            realname.setCheckUid(s);
            realname.setArchiveTime(date);
            realname.setArchivedFlag(s);
            realname.setCreateTime(date);
            realname.setLastModifyTime(date);
            realname.setLastModifyUid(s);
            realname.setlRecordNo(recordNo);
            realname.setRemark(s);
            int i = tRealnameApplyMapper.updateByPrimaryKeySelective(realname);
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
            TRealnameApply realname = new TRealnameApply();
            Date date = new Date();
            String s = "a";
            long recordNo = 987654335L;
            realname.setIdPic(s);
            realname.setIdNo(s);
            realname.setGrade(s);
            realname.setIdType(s);
            realname.setInputDate(date);
            realname.setInputUid(s);
            realname.setName(s);
            realname.setNationality(s);
            realname.setProductNo(s);
            realname.setLogIp(s);
            realname.setProfession(s);
            realname.setStatus(s);
            realname.setSerialNo(11L);
            realname.setTransType(s);
            realname.setAcceptChannel(s);
            realname.setCustomerNo(s);
            realname.setCertExpiryDate(s);
            realname.setAddress(s);
            realname.setCheckTime(date);
            realname.setCheckUid(s);
            realname.setArchiveTime(date);
            realname.setArchivedFlag(s);
            realname.setCreateTime(date);
            realname.setLastModifyTime(date);
            realname.setLastModifyUid(s);
            realname.setlRecordNo(recordNo);
            realname.setRemark(s);
            int i=tRealnameApplyMapper.updateByPrimaryKey(realname);
            System.out.print("----------------------------testupdateByPrimaryKey返回码:--:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testqueryAuthenticationSuccessInfo() {
        try {
            String customerNo = "987654335";
            TRealnameApply realname = tRealnameApplyMapper.queryAuthenticationSuccessInfo(customerNo);
            System.out.print("----------------------------testqueryAuthenticationSuccessInfo返回码:--:"+realname);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 查询实名申请中的用户
     */
    @Test
    public void testqueryAuthenticationApplyInfo() {
        try {
            String customerNo = "987654335";
            List<TRealnameApply> realnameList = tRealnameApplyMapper.queryAuthenticationApplyInfo(customerNo);
            for(TRealnameApply realname:realnameList)
            {
            	System.out.print("----------------------------testqueryAuthenticationApplyInfo返回码:--:"+realname.getIdNo());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testgetRealNameUsedIdNoCount() {
        try {
            String idNo = "a";
            int i = tRealnameApplyMapper.getRealNameUsedIdNoCount(idNo);
            System.out.print("----------------------------testgetRealNameUsedIdNoCount返回码:--:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testupdateAuthenticationInfo() {
        try {
            String customerNo = "a";
            int i = tRealnameApplyMapper.updateAuthenticationInfo(customerNo);
            System.out.print("----------------------------testupdateAuthenticationInfo返回码:--:"+i);
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
            long recordNo = 987654335;
            int i = tRealnameApplyMapper.deleteByPrimaryKey(recordNo);
            System.out.print("----------------------------testdeleteByPrimaryKey返回码:--:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
