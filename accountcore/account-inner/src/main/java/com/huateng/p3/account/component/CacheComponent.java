package com.huateng.p3.account.component;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.huateng.p3.account.common.bizparammodel.RiskAllRuleQry;
import com.huateng.p3.account.common.bizparammodel.RiskTxnTypeRuleQry;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.persistence.RiskCustomerTxntypeRuleMapper;
import com.huateng.p3.account.persistence.TDictAreaCityMapper;
import com.huateng.p3.account.persistence.TDictOuterTxnCodeMapper;
import com.huateng.p3.account.persistence.TDictTxnCodeMapper;
import com.huateng.p3.account.persistence.TInfoOrgMapper;
import com.huateng.p3.account.persistence.TRiskAreaOtaMapper;
import com.huateng.p3.account.persistence.TRiskCustomerCommonRuleMapper;
import com.huateng.p3.account.persistence.models.RiskCustomerTxntypeRule;
import com.huateng.p3.account.persistence.models.TDictAreaCity;
import com.huateng.p3.account.persistence.models.TDictOuterTxnCode;
import com.huateng.p3.account.persistence.models.TDictTxnCode;
import com.huateng.p3.account.persistence.models.TInfoOrg;
import com.huateng.p3.account.persistence.models.TRiskAreaOta;
import com.huateng.p3.account.persistence.models.TRiskCustomerCommonRule;

/**
 * User: JamesTang
 * Date: 13-12-11
 * Time: 下午2:01
 */

@Component
public class CacheComponent {
	/**
	 * 外部交易类型
	 */
    @Autowired
    private TDictOuterTxnCodeMapper tDictOuterTxnCodeMapper;
    /**
     * 内部交易类型
     */
    @Autowired
    private TDictTxnCodeMapper tDictTxnCodeMapper;
    
    /**
     * 城市代码
     */
    @Autowired
    private TDictAreaCityMapper tDictAreaCityMapper;
    
    /**
     * 特殊交易类型风控
     */
    @Autowired
    private RiskCustomerTxntypeRuleMapper riskCustomerTxntypeRuleMapper;
    
    /**
     * 交易总风控
     */
    @Autowired
    private TRiskCustomerCommonRuleMapper tRiskCustomerCommonRuleMapper;
    
    @Autowired
    private TInfoOrgMapper orgMapper;
    
    /**
     * OTA风控
     */
    @Autowired
    private TRiskAreaOtaMapper tRiskAreaOtaMapper;
    
    private LoadingCache<String, Optional<TRiskAreaOta>> riskAreaOtaCache;

    private LoadingCache<String, Optional<TDictOuterTxnCode>> dictTxnTypeCache;

    private LoadingCache<String, Optional<TDictTxnCode>> dictTxnCodeCache;

    private LoadingCache<String, Optional<TDictAreaCity>> dictAreaCityCache;
    
    private LoadingCache<String, Optional<TDictAreaCity>> dictAreaCityByProductNoCache;

    private LoadingCache<RiskTxnTypeRuleQry, Optional<RiskCustomerTxntypeRule>> riskTxnTypeRuleCache;
    
    private LoadingCache<RiskAllRuleQry, Optional<TRiskCustomerCommonRule>> riskAllRuleCache;
    
    private LoadingCache<String, Optional<TInfoOrg>> orgCache;


    @PostConstruct
    public void init() {
    	riskAreaOtaCache = CacheBuilder.newBuilder()
                .expireAfterWrite(12, TimeUnit.HOURS)  //默认12个小时过期
                .build(new CacheLoader<String, Optional<TRiskAreaOta>>() {
                    @Override
                    public Optional<TRiskAreaOta> load(String key) throws Exception {
                        return Optional.fromNullable(tRiskAreaOtaMapper.findAreaOtaByAreacode(key));
                    }
                });
        dictTxnTypeCache = CacheBuilder.newBuilder()
                .expireAfterWrite(12, TimeUnit.HOURS)  //默认12个小时过期
                .build(new CacheLoader<String, Optional<TDictOuterTxnCode>>() {
                    @Override
                    public Optional<TDictOuterTxnCode> load(String key) throws Exception {
                        return Optional.fromNullable(tDictOuterTxnCodeMapper.selectByPrimaryKey(key));
                    }
                });

        dictTxnCodeCache = CacheBuilder.newBuilder()
                .expireAfterWrite(12, TimeUnit.HOURS)  //默认一个小时过期
                .build(new CacheLoader<String, Optional<TDictTxnCode>>() {
                    @Override
                    public Optional<TDictTxnCode> load(String key) throws Exception {
                        return Optional.fromNullable(tDictTxnCodeMapper.selectByPrimaryKey(key));
                    }
                });

        dictAreaCityCache = CacheBuilder.newBuilder()
                .expireAfterWrite(24, TimeUnit.HOURS)  //默认一个小时过期
                .build(new CacheLoader<String, Optional<TDictAreaCity>>() {
                    @Override
                    public Optional<TDictAreaCity> load(String key) throws Exception {
                        return Optional.fromNullable(tDictAreaCityMapper.selectByPrimaryKey(key));
                    }
                });
        
        dictAreaCityByProductNoCache = CacheBuilder.newBuilder()
                .expireAfterWrite(24, TimeUnit.HOURS)  //默认一个小时过期
                .build(new CacheLoader<String, Optional<TDictAreaCity>>() {
                    @Override
                    public Optional<TDictAreaCity> load(String key) throws Exception {
                        return Optional.fromNullable(tDictAreaCityMapper.queryAreaCityCodeByProductNo(key));
                    }
                });

        riskTxnTypeRuleCache = CacheBuilder.newBuilder().expireAfterWrite(12, TimeUnit.HOURS)
                .build(new CacheLoader<RiskTxnTypeRuleQry, Optional<RiskCustomerTxntypeRule>>() {
                    @Override
                    public Optional<RiskCustomerTxntypeRule> load(RiskTxnTypeRuleQry keyobj) throws Exception {
                        return Optional.fromNullable(riskCustomerTxntypeRuleMapper.selectTxnTypeRiskRule(keyobj.getTxnChannel(),
                                keyobj.getTxnType(), keyobj.getAccountType(), keyobj.getUserGrade()));
                    }
                });
        
        riskAllRuleCache = CacheBuilder.newBuilder().expireAfterWrite(12, TimeUnit.HOURS)
                .build(new CacheLoader<RiskAllRuleQry, Optional<TRiskCustomerCommonRule>>() {
                    @Override
                    public Optional<TRiskCustomerCommonRule> load(RiskAllRuleQry keyobj) throws Exception {
                        return Optional.fromNullable(tRiskCustomerCommonRuleMapper.findAccountGeneralRisk(keyobj.getTxnChannel(),
                               keyobj.getAccountType(), keyobj.getUserGrade(),DateTime.now().toDate()));
                    }
                });
        
        orgCache = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.HOURS)
                .build(new CacheLoader<String, Optional<TInfoOrg>>() {
                    @Override
                    public Optional<TInfoOrg> load(String keyobj) throws Exception {
                        return Optional.fromNullable(orgMapper.selectByOrgCode(keyobj));
                    }
                });
    }

    /**
     * @param areacode
     * @return
     */
    public TRiskAreaOta getAreaOtaByAreacode(String areacode) {
    	if(Strings.isNullOrEmpty(areacode)){
    		 throw new BizException(BussErrorCode.ERROR_CODE_200024.getErrorcode(),
    	                BussErrorCode.ERROR_CODE_200024.getErrordesc());
    	}
    	Optional<TRiskAreaOta> result = riskAreaOtaCache.getUnchecked(areacode);
        if (result.isPresent()) { // 如果不为null
             return result.get();
        }          
        return null;
    }
    
    /**
     * @param outTxnType
     * @return
     */
    public TDictOuterTxnCode getInnerTxnType(String outTxnType) {
    	if(Strings.isNullOrEmpty(outTxnType)){
    		 throw new BizException(BussErrorCode.ERROR_CODE_900001.getErrorcode(),
    	                BussErrorCode.ERROR_CODE_900001.getErrordesc());
    	}
    	Optional<TDictOuterTxnCode> result = dictTxnTypeCache.getUnchecked(outTxnType);
        if (result.isPresent()) { // 如果不为null
             return result.get();
        }          
        throw new BizException(BussErrorCode.ERROR_CODE_900001.getErrorcode(),
                BussErrorCode.ERROR_CODE_900001.getErrordesc());

    }

    /**
     * @param txnType
     * @return
     */
    public TDictTxnCode getTxnTypeObj(String txnType) {
    	if(Strings.isNullOrEmpty(txnType)){
   		 	throw new BizException(BussErrorCode.ERROR_CODE_900001.getErrorcode(),
   	                BussErrorCode.ERROR_CODE_900001.getErrordesc());
    	}
    	Optional<TDictTxnCode> result = dictTxnCodeCache.getUnchecked(txnType);
        if (result.isPresent()) { // 如果不为null
             return result.get();
        }          
        throw new BizException(BussErrorCode.ERROR_CODE_900001.getErrorcode(),
                BussErrorCode.ERROR_CODE_900001.getErrordesc());
    	
    	
    }

    /**
     * @param areaCity
     * @return
     */
    public TDictAreaCity getAreaCityObj(String areaCity) {
    	if(Strings.isNullOrEmpty(areaCity)){
   		 	throw new BizException(BussErrorCode.ERROR_CODE_200024.getErrorcode(),
   	                BussErrorCode.ERROR_CODE_200024.getErrordesc());
    	}
    	Optional<TDictAreaCity> result = dictAreaCityCache.getUnchecked(areaCity);
        if (result.isPresent()) { // 如果不为null
             return result.get();
        }                 
        // 非法电信区号
    	throw new BizException(BussErrorCode.ERROR_CODE_200024.getErrorcode(),BussErrorCode.ERROR_CODE_200024.getErrordesc());        
    }
    
    /**
     * @param areaCity
     * @return
     */
    public TDictAreaCity queryAreaCityCodeByProductNo(String mobileNo) {
    	if(Strings.isNullOrEmpty(mobileNo)){
   		 	throw new BizException(BussErrorCode.ERROR_CODE_200025.getErrorcode(),
   	                BussErrorCode.ERROR_CODE_200025.getErrordesc());
    	}
    	Optional<TDictAreaCity> result = dictAreaCityByProductNoCache.getUnchecked(mobileNo);
        if (result.isPresent()) { // 如果不为null
             return result.get();
        }                 
        return null;      
    }
    

    /**
     * @param riskTxnTypeRuleQry
     * @return
     */
    public RiskCustomerTxntypeRule getRiskCustomerTxntypeRule(RiskTxnTypeRuleQry riskTxnTypeRuleQry) {
        Optional<RiskCustomerTxntypeRule> result = riskTxnTypeRuleCache.getUnchecked(riskTxnTypeRuleQry);
        if (result.isPresent()) { // 如果不为null
            return result.get();
        }
        return null;

    }
    
    /**
     * @param riskAllRuleCache
     * @return
     */
    public TRiskCustomerCommonRule getRiskCustomerAllRule(RiskAllRuleQry riskAllRuleQry) {
        Optional<TRiskCustomerCommonRule> result = riskAllRuleCache.getUnchecked(riskAllRuleQry);
        if (result.isPresent()) { // 如果不为null
            return result.get();
        }
        return null;

    }
    
    /**
     * @param riskAllRuleCache
     * @return
     */
    public TInfoOrg getOrgObj(String orgCode) {
    	if(Strings.isNullOrEmpty(orgCode)){
   		 	throw new BizException(BussErrorCode.ERROR_CODE_220006.getErrorcode(),
   	                BussErrorCode.ERROR_CODE_220006.getErrordesc());
    	}
        Optional<TInfoOrg> result = orgCache.getUnchecked(orgCode);
        if (result.isPresent()) { // 如果不为null
            return result.get();
        }
        //机构不存在
        throw new BizException(BussErrorCode.ERROR_CODE_220006.getErrorcode(),
                BussErrorCode.ERROR_CODE_220006.getErrordesc());

    }


}
