package com.huateng.p3.account.inner;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseAccountServiceSpringTest;

import com.huateng.p3.account.common.enummodel.TxnType;
import com.huateng.p3.account.commonservice.OrgService;
import com.huateng.p3.account.persistence.models.TInfoOrg;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-23
 * Time: 下午10:26
 * To change this template use File | Settings | File Templates.
 */
public class OrgServiceTest extends BaseAccountServiceSpringTest{

    @Autowired
    private OrgService orgService;

    @Test
    public void getValidOrg() {
        String orgCode= "111330053990004";
        String  txntype= "107010";
        String orgtype = TxnType.TXN_ENCLOSURE.getTxnCode()+"";
        TInfoOrg org = orgService.getValidOrg(orgCode,txntype,orgtype);
        System.out.println("----------getValidOrg-----------org.getOrgCode():----------"+org.getOrgCode());
    }
}
