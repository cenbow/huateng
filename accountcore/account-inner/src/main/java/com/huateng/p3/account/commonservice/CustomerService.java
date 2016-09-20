package com.huateng.p3.account.commonservice;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.enummodel.Carrieroperator;
import com.huateng.p3.account.common.enummodel.CustomerRealname;
import com.huateng.p3.account.common.enummodel.KeyType;
import com.huateng.p3.account.common.enummodel.RiskBlackType;
import com.huateng.p3.account.common.enummodel.ShortcutCardStatus;
import com.huateng.p3.account.common.enummodel.ShortcutChannel;
import com.huateng.p3.account.common.enummodel.TrueOrFalse;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.common.util.BeanMapper;
import com.huateng.p3.account.component.CacheComponent;
import com.huateng.p3.account.persistence.TInfoAccountenterpriseMapper;
import com.huateng.p3.account.persistence.TInfoCardBindingMapper;
import com.huateng.p3.account.persistence.TInfoCustomerMapper;
import com.huateng.p3.account.persistence.TInfoHanbindMapper;
import com.huateng.p3.account.persistence.TInfoMobileHMapper;
import com.huateng.p3.account.persistence.TInfoTxnAuthMapper;
import com.huateng.p3.account.persistence.TRealnameApplyMapper;
import com.huateng.p3.account.persistence.models.TDictAreaCity;
import com.huateng.p3.account.persistence.models.TInfoAccountenterprise;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.account.persistence.models.TInfoMobileH;
import com.huateng.p3.account.persistence.models.TInfoTxnAuth;
import com.huateng.p3.account.persistence.models.TRealnameApply;

/**
 * Created with IntelliJ IDEA.
 * User: JamesTang
 * Date: 13-12-6
 * Time: 上午8:23
 * To change this template use File | Settings | File Templates.
 */
@Component
public class CustomerService {
    @Autowired
    private TInfoCustomerMapper tInfoCustomerMapper;
    
    @Autowired
    private TInfoAccountenterpriseMapper tInfoAccountenterpriseMapper;
    
    @Autowired
    private TRealnameApplyMapper tRealnameApplyMapper;
    

    @Autowired
    private TInfoCardBindingMapper tInfoCardBindingMapper;


    @Autowired
    private TxnCheckService txnCheckService;
    
    @Autowired
    private RiskService riskService;
    
    @Autowired
    private TInfoHanbindMapper tInfoHanbindMapper;

    @Autowired
    private CacheComponent cacheComponent;
    
    @Autowired
    private TInfoMobileHMapper tInfoMobileHMapper;
    
    @Autowired
    private TInfoTxnAuthMapper tInfoTxnAuthMapper;

    public TInfoCustomer findValidCustomerByCustomerNo(String customerNo) {
        TInfoCustomer customer = tInfoCustomerMapper.findBlackCustomer(customerNo);
        txnCheckService.checkCustomerPaymentTxn(customer);
        return customer;
    }
    public TInfoCustomer findValidCustomerByCustomerNoForUpdate(String customerNo) {
        TInfoCustomer customer = tInfoCustomerMapper.findBlackCustomerForUpdate(customerNo);
        txnCheckService.checkCustomerPaymentTxn(customer);
        return customer;
    }
    
    
    public TInfoAccountenterprise findEnterpriseCustomerByCustomerNo(String customerNo) {
    	TInfoAccountenterprise customer = tInfoAccountenterpriseMapper.findCustomerByCustomerNo(customerNo);
        txnCheckService.checkCustomerPaymentTxn(customer);
        return customer;
    }
    public TInfoAccountenterprise findEnterpriseCustomerByCustomerNoForUpdate(String customerNo) {
    	TInfoAccountenterprise customer = tInfoAccountenterpriseMapper.findCustomerByCustomerNoForUpdate(customerNo);
        txnCheckService.checkCustomerPaymentTxn(customer);
        return customer;
    }
    
    /**
     * 转化为个账的客户号，方便后续处理
     * @param customerNo
     * @return
     */
    public TInfoCustomer findEnterpriseCustomerToCustomerNo(String customerNo) {
    	TInfoAccountenterprise customer = tInfoAccountenterpriseMapper.findCustomerByCustomerNo(customerNo);
        txnCheckService.checkCustomerPaymentTxn(customer);
        return EnterpriseCustomerToAccountCustomer(customer);
    }
    
    /**
     * 高级实名用户信息更新
     * @param tInfoCustomer
     */
    public void setRealnameCustomer(TInfoCustomer tInfoCustomer ) {
    	
    	if (CustomerRealname.CUSTOMER_REALNAME_HIGH.getCustomerRealnameCode().equals(tInfoCustomer.getIsRealname())) {

            TRealnameApply tRealnameApply = tRealnameApplyMapper.queryAuthenticationSuccessInfo(tInfoCustomer.getCustomerNo());
            if (null != tRealnameApply) {
                tInfoCustomer.setName(tRealnameApply.getName());
                tInfoCustomer.setIdentifyType(tRealnameApply.getIdType());
                tInfoCustomer.setIdentifyNo(tRealnameApply.getIdNo());
                //新增性别字段
                if(!Strings.isNullOrEmpty(tRealnameApply.getGender())){
                	tInfoCustomer.setGender(tRealnameApply.getGender());
                }
            }

        }
    		
	}
    
    /**
     * 客户身份证是否黑名单，会提交掉事务，这里不能将tInfoCustomer的idno直接设置为高级实名的身份证
     * @param tInfoCustomer
     */
    public void checkCustomerBlack(TInfoCustomer tInfoCustomer ) {
    	String idno = tInfoCustomer.getIdentifyNo();
    	if (CustomerRealname.CUSTOMER_REALNAME_HIGH.getCustomerRealnameCode().equals(tInfoCustomer.getIsRealname())) {

            TRealnameApply tRealnameApply = tRealnameApplyMapper.queryAuthenticationSuccessInfo(tInfoCustomer.getCustomerNo());
            if (null != tRealnameApply) {
            	idno = tRealnameApply.getIdNo();
               
            }
        }
    	//检查黑名单身份证
        riskService.queryRiskBlack(idno, RiskBlackType.BLACK_TYPE_IDNO.getTypeCode());
    		
	}
    
    /**
     * 预存款账户bean转个人账户bean，方便后续公用方法的调用
     * @param customer
     * @return
     */
    public TInfoCustomer  EnterpriseCustomerToAccountCustomer(TInfoAccountenterprise enterpriseCustomer){
    	TInfoCustomer accountCustomer = new TInfoCustomer();
        BeanMapper.copy(enterpriseCustomer, accountCustomer);
        accountCustomer.setCustomerNo(enterpriseCustomer.getEnterpriseCustomerno());
        return accountCustomer;
    	
    }
    

    public TInfoCustomer findValidCustomerByUnifyId(String unifyId) {
        TInfoCustomer customer = tInfoCustomerMapper.findCustomerByUnifyId(unifyId);
        txnCheckService.checkCustomerPaymentTxn(customer);
        return customer;
    }

    public TInfoCustomer findValidCustomerByUnifyIdForUpdate(String unifyId) {
        TInfoCustomer customer = tInfoCustomerMapper.findCustomerByUnifyIdForUpdate(unifyId);
        txnCheckService.checkCustomerPaymentTxn(customer);
        return customer;
    }

    public TInfoCustomer findValidActivationCustomerByUnifyId(String unifyId) {
        TInfoCustomer customer = tInfoCustomerMapper.findCustomerByUnifyId(unifyId);
        txnCheckService.checkCustomerActivationTxn(customer);
        return customer;
    }
    
    public int updateWithSynchronize(TInfoCustomer newCustomer ,String customerNo){
    	newCustomer.setLastUpdateTime(DateTime.now().toDate());
    	newCustomer.setCustomerNo(customerNo);
    	return tInfoCustomerMapper.updateWithSynchronize(newCustomer);
    	
    }
    
    /**
     * 修改个人信息
     * @param newCustomer
     * @return
     */
    public int updateByPrimaryKeySelective(TInfoCustomer newCustomer){
    	return tInfoCustomerMapper.updateByPrimaryKeySelective(newCustomer);
    	
    }
    /**
     * 查询是否绑卡(老翰银)
     * @param customerNo
     * @return
     */
    public TrueOrFalse findCardHanding(String customerNo){
    	TrueOrFalse isrealstr = TrueOrFalse.FALSE;
    	int cardHanding = tInfoHanbindMapper.getBankHan(customerNo).size();
    	if(cardHanding >= 1){
    		isrealstr = TrueOrFalse.TRUE;
    	}   		
    	return  isrealstr;
    }
    
    /**
     * 查询是否绑卡(新快捷)
     * @param customerNo
     * @return
     */
    public TrueOrFalse findShortcutCard(String unifyId){
    	TrueOrFalse isrealstr = TrueOrFalse.FALSE;  	
    	int shortcutCard = tInfoCardBindingMapper.findCardBinding(null, unifyId,
    			null, ShortcutChannel.SHORTCUT.getValue(), ShortcutCardStatus.NOTUSED.getValue(), 
    			null, null, null).size();
    	if(shortcutCard >= 1){
    		isrealstr = TrueOrFalse.TRUE;
    	}   		
    	return  isrealstr;
    }

    public TInfoCustomer findBlackCustomerByCustomerNo(String customerNo) {
        TInfoCustomer customer = tInfoCustomerMapper.findBlackCustomer(customerNo);
        txnCheckService.checkCustomerActivationTxn(customer);
        return customer;
    }
    
    public TInfoCustomer getCustomerForUpdate(String accountKey, KeyType keyType) {
    	TInfoCustomer customer = null;// 交易主账户
        switch (keyType) {
        	case ACCOUNT:
	            customer = tInfoCustomerMapper.findCustomerByAccountNoForUpdate(accountKey);
	            break;
            case UNIFY:
            	customer = tInfoCustomerMapper.findCustomerByUnifyIdForUpdate(accountKey);
                break;
            case CARD:
            	customer = tInfoCustomerMapper.findCustomerByCardNoForUpdate(accountKey);
                break;
            case CUSTOMER:
            	customer = tInfoCustomerMapper.findCustomerByCustomerNoForUpdate(accountKey);
                break;
        }
        if (null == customer) {
            throw new BizException(BussErrorCode.ERROR_CODE_200010.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200010.getErrordesc());
        }
        return customer;
    }
    
    public TInfoCustomer getCustomer(String accountKey, KeyType keyType) {
    	TInfoCustomer customer = null;// 交易主账户
        switch (keyType) {
        	case ACCOUNT:
	            customer = tInfoCustomerMapper.findCustomerByAccountNo(accountKey);
	            break;
            case UNIFY:
            	customer = tInfoCustomerMapper.findCustomerByUnifyId(accountKey);
                break;
            case CARD:
            	customer = tInfoCustomerMapper.findCustomerByCardNo(accountKey);
                break;
            case CUSTOMER:
            	customer = tInfoCustomerMapper.findCustomerByCustomerNo(accountKey);
                break;
        }
        if (null == customer) {
            throw new BizException(BussErrorCode.ERROR_CODE_200010.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200010.getErrordesc());
        }
        return customer;
    }
    

}
