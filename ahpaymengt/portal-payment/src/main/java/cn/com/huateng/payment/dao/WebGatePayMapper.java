package cn.com.huateng.payment.dao;

import cn.com.huateng.payment.model.WebGatePay;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: 董培基
 * Date: 13-8-1
 * Time: 下午1:24
 */
@Repository
public class WebGatePayMapper extends SqlSessionDaoSupport {

    public List<WebGatePay> findPortWebGatePayInfo(WebGatePay webGetPay){
        return getSqlSession().selectList("WebGatePay.findPortWebGatePayInfo",webGetPay);
    }

    public int doInsertPortWebGatePay(WebGatePay webGetPay) {
        return getSqlSession().insert("WebGatePay.doInsertPortWebGatePay",webGetPay);
    }
    public int updatePortWebGatePay(WebGatePay webGetPay) {
        return getSqlSession().insert("WebGatePay.updatePortWebGatePay",webGetPay);
    }
    
    public WebGatePay selectByPrimaryKey(String orderNo){
        return getSqlSession().selectOne("WebGatePay.selectByPrimaryKey",orderNo);
    }
}
