package cn.com.huateng.payment.manage;

import cn.com.huateng.payment.dao.WebGatePayMapper;
import cn.com.huateng.payment.model.WebGatePay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: 董培基
 * Date: 13-8-5
 * Time: 上午9:20
 */
@Component
public class WebGateManage {
    @Autowired
    private WebGatePayMapper webGetPayMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public void insertPortWebGetPay(WebGatePay webGatePay){
         webGetPayMapper.doInsertPortWebGatePay(webGatePay);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePortWebGetPay(WebGatePay webGatePay){
           webGetPayMapper.updatePortWebGatePay(webGatePay);
    }
}
