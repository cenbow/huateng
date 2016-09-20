package com.huateng.p3.account.daomappertest;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TParaAccountExtendFeeMapper;
import com.huateng.p3.account.persistence.models.TParaAccountExtendFee;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
public class TParaAccountExtendFeeTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TParaAccountExtendFeeTest.class);

    @Autowired
    private TParaAccountExtendFeeMapper tParaAccountExtendFeeMapper;

    /**
     * 测试完成
     */
    @Test
    public void testinsert() {
        TParaAccountExtendFee extendfee = new TParaAccountExtendFee();
        Date date = new Date();
        String s = "a";
        long recordNo = 987654332L;
        extendfee.setRecordNo(recordNo);
        extendfee.setCheckTime(date);
        extendfee.setCheckUid(s);
        extendfee.setArchiveTime(date);
        extendfee.setArchivedFlag(s);
        extendfee.setCreateTime(date);
        extendfee.setCreateUid(s);
        extendfee.setEffectiveDate(date);
        extendfee.setLastModifyTime(date);
        extendfee.setLastModifyUid(s);
        extendfee.setRemark(s);
        extendfee.setResvFld1(s);
        extendfee.setResvFld2(s);
        extendfee.setUseFlag(s);
        extendfee.setValidDate(date);
        int i = tParaAccountExtendFeeMapper.insert(extendfee);
        System.out.print("----------------------------插入TParaAccountExtendFee返回码:--:" + i);
    }


}
