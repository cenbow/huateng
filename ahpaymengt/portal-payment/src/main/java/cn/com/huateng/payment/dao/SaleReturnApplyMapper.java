package cn.com.huateng.payment.dao;

import cn.com.huateng.payment.model.SaleReturnApply;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: 董培基
 * Date: 14-12-5
 * Time: 下午1:26
 */
@Repository
public class SaleReturnApplyMapper extends SqlSessionDaoSupport {

    public int insertSaleReturnApply(SaleReturnApply saleReturnApply){
        return getSqlSession().insert("SaleReturnApply.insert",saleReturnApply);
    }

    public List<SaleReturnApply> selectSaleReturnApplyByOrderNo(SaleReturnApply saleReturnApply){
        return getSqlSession().selectList("SaleReturnApply.selectSaleReturnApplyByOrderNo", saleReturnApply);
    }
}
