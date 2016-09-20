package com.huateng.p3.account.daomappertest;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TInfoOrgMapper;
import com.huateng.p3.account.persistence.models.TInfoOrg;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | setTings | File Templates.
 */
public class TInfoOrgTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TInfoOrgTest.class);

    @Autowired
    private TInfoOrgMapper tInfoOrgMapper;

    /**
     * 测试完成
     */
    @Test
    public void testinsert() {
        TInfoOrg tInfoOrg = new TInfoOrg();
        Long l = 11L;
        Date date = new Date();
        String s = "a";
        tInfoOrg.setRecordNo(l);
        tInfoOrg.setOrgCode(s);
        tInfoOrg.setOrgType(s);
        tInfoOrg.setSubType(s);
        tInfoOrg.setOrgFname(s);
        tInfoOrg.setOrgSname(s);
        tInfoOrg.setIndustryType(s);
        tInfoOrg.setAddress(s);
        tInfoOrg.setZipCode(s);
        tInfoOrg.setBusinessLicenseNo(s);
        tInfoOrg.setOrganizationCode(s);
        tInfoOrg.setLegalRepresentiveName(s);
        tInfoOrg.setLinkmanName(s);
        tInfoOrg.setLinkmanTelephone(s);
        tInfoOrg.setLinkmanFax(s);
        tInfoOrg.setLinkmanEmail(s);
        tInfoOrg.setServiceLevel(s);
        tInfoOrg.setRiskLevel(s);
        tInfoOrg.setAllowTrans(s);
        tInfoOrg.setOrgStatus(s);
        tInfoOrg.setAreaCode(s);
        tInfoOrg.setCityCode(s);
        tInfoOrg.setUpOrgCode(s);
        tInfoOrg.setSignOrgCode(s);
        tInfoOrg.setClearingOrgCode(s);
        tInfoOrg.setSettlementOrgCode(s);
        tInfoOrg.setSettlementMode(s);
        tInfoOrg.setIsRealClear(s);
        tInfoOrg.setClearingDate(s);
        tInfoOrg.setSettleDate(s);
        tInfoOrg.setfLinkmanName(s);
        tInfoOrg.setfLinkmanTelephone(s);
        tInfoOrg.setfLinkmanFax(s);
        tInfoOrg.setfLinkmanEmail(s);
        tInfoOrg.setCreateUid(s);
        tInfoOrg.setCreateTime(date);
        tInfoOrg.setCheckFlag(s);
        tInfoOrg.setCheckUid(s);
        tInfoOrg.setCheckTime(date);
        tInfoOrg.setLastModifyUid(s);
        tInfoOrg.setLastModifyTime(date);
        tInfoOrg.setSettlementMin1(l);
        tInfoOrg.setSettlementMin2(l);
        tInfoOrg.setSettlementMin3(l);
        tInfoOrg.setSettlementMin4(l);
        tInfoOrg.setSettlementMin5(l);
        tInfoOrg.setChargeAccFlag(s);
        tInfoOrg.setOrgKind(s);
        tInfoOrg.setArchiveTime(date);
        tInfoOrg.setArchiveFlag(s);
        tInfoOrg.setlRecordNo(l);
        tInfoOrg.setRemark(s);
        tInfoOrg.setSettleFlag(s);
        tInfoOrg.setPayType(s);
        tInfoOrg.setResvFld1(s);
        tInfoOrg.setResvFld2(s);
        tInfoOrg.setResvFld3(s);
        int i = tInfoOrgMapper.insert(tInfoOrg);
        System.out.println("----------------------------插入TInfoOrg返回码:--:" + i);
    }

    /**
     * TInfoOrg findBlackMerchant(Map orgCode);
     */
    @Test
    public void testfindBlackMerchant(){
        String s = "a";
        String merchantCode = s;
        Date currentDate = new Date();

        TInfoOrg org = tInfoOrgMapper.findBlackMerchant(merchantCode,currentDate);
        System.out.println("-----testfindBlackMerchant 返回：---------"+org==null);
    }
}
