package com.huateng.p3.account.inner.component;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseAccountServiceSpringTest;

import com.huateng.p3.account.common.bizparammodel.RiskTxnTypeRuleQry;
import com.huateng.p3.account.component.CacheComponent;
import com.huateng.p3.account.persistence.models.RiskCustomerTxntypeRule;
import com.huateng.p3.account.persistence.models.TDictOuterTxnCode;
import com.huateng.p3.account.persistence.models.TDictTxnCode;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-31
 * Time: 下午1:51
 * To change this template use File | Settings | File Templates.
 */
public class CacheComponentTest extends BaseAccountServiceSpringTest{
    @Autowired
    private CacheComponent cacheComponent;


    @Test
    public void getInnerTxnType(){
        String innerTxnType = "B3101A";
        TDictOuterTxnCode tDictOuterTxnCode = cacheComponent.getInnerTxnType(innerTxnType);
        System.out.println("--------------tDictOuterTxnCode--------------------"+tDictOuterTxnCode.getCreateDate());
    }

    @Test
    public void getTxnTypeObj(){
        String innerTxnType = "131090";
        TDictTxnCode tDictTxnCode = cacheComponent.getTxnTypeObj(innerTxnType);
        System.out.println("--------------tDictOuterTxnCode--------------------"+tDictTxnCode.getTxnName());
    }

    @Test
    public void getRiskCustomerTxntypeRule(){
      //  String para = "08:121010:1:5";// + riskTxnTypeRuleQry.getTxnType() + ":" + riskTxnTypeRuleQry.getAccountType() + ":" + riskTxnTypeRuleQry.getUserGrade()
        RiskTxnTypeRuleQry para  =new   RiskTxnTypeRuleQry();
        para.setUserGrade("5");
        para.setTxnChannel("08");
        para.setTxnType("121010");
        para.setAccountType("1");
        RiskCustomerTxntypeRule riskCustomerTxntypeRule = cacheComponent.getRiskCustomerTxntypeRule(para);//   .getTxnTypeObj(innerTxnType);
        System.out.println("--------------tDictOuterTxnCode--------------------" + riskCustomerTxntypeRule.getMonthTransAmt());
    }
}
