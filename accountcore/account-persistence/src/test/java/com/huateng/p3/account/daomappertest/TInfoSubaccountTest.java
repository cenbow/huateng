package com.huateng.p3.account.daomappertest;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TInfoSubaccountMapper;
import com.huateng.p3.account.persistence.models.TInfoSubaccount;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:09
 * To change this template use File | setTings | File Templates.
 */
public class TInfoSubaccountTest extends BaseSpringTest {
    private Logger log = LoggerFactory.getLogger(TInfoSubaccountTest.class);

    @Autowired
    private TInfoSubaccountMapper tInfoSubaccountMapper;

    /**
     * 测试完成
     */
    @Test
    public void testinsert() {
        TInfoSubaccount tInfoSubaccount = new TInfoSubaccount();
        Date date = new Date();
        String s = "a";
        Long l = 11L;
        int num = 1;
        tInfoSubaccount.setCustomerNo(s);
        tInfoSubaccount.setAccountNo(s);
        tInfoSubaccount.setMainaccountNo(s);
        int i = tInfoSubaccountMapper.insert(tInfoSubaccount);
        System.out.print("----------------------------插入TInfoSubaccount返回码:--:" + i);
    }

    /**
     * List<TInfoSubaccount> getSubaccountinfoByMainaccno(String mainAccountNo);
     */
    @Test
    public void testgetSubaccountinfoByMainaccno(){
        String mainAccountNo = "a";
        List<TInfoSubaccount> l =  tInfoSubaccountMapper.getSubaccountinfoByMainaccno(mainAccountNo);
        System.out.println("---------testgetSubaccountinfoByMainaccno 返回list.size()==--------------------"+l.size());
    }
}
