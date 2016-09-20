package com.huateng.p3.account.service.aopcomponent;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.huateng.p3.account.common.bizparammodel.BankingCoreMq;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.account.common.enummodel.ProductProperty;
import com.huateng.p3.account.component.RabbitTemplateComponent;
import com.huateng.p3.account.persistence.TInfoProductPropertyMapper;
import com.huateng.p3.account.persistence.models.TInfoProductProperty;
import com.huateng.p3.component.Response;

/**
 * User: JamesTang
 * Date: 13-12-23
 * Time: 下午6:07
 */
@Component
@Slf4j
public class CurrenCyInvestNotifyCompnent {

    @Autowired
    private RabbitTemplateComponent rabbitTemplateComponent;
	@Autowired
	private TInfoProductPropertyMapper tInfoProductPropertyMapper;

    public void InvestNotify(PaymentInfo paymentInfo, Object retObj) {
        if (!(retObj instanceof Response)) {
            return;
        }
        Response<TxnResultObject> retobj = (Response<TxnResultObject>) retObj;
        if (!retobj.isSuccess()) {
            return;
        }
        TxnResultObject txnResultObject = retobj.getResult();
        //冲正的交易如果是伪造的交易无需发送
        if (txnResultObject.isRollbackFake()) {
            return;
        }
    }
    

    
}
