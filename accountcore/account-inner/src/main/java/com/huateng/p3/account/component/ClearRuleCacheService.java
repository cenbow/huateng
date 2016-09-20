package com.huateng.p3.account.component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huateng.p3.account.clearservice.CapitalFeeMatcher;
import com.huateng.p3.account.common.enummodel.AccountType;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.exception.ClearException;
import com.huateng.p3.account.persistence.TInfoOrgMapper;
import com.huateng.p3.account.persistence.TRuleCapitalFeeMapper;
import com.huateng.p3.account.persistence.TRuleFeeCodeMapper;
import com.huateng.p3.account.persistence.models.TLogOnlinePayment;
import com.huateng.p3.account.persistence.models.TRuleCapitalFee;
import com.huateng.p3.account.persistence.models.TRuleFeeCode;

/**
 * User: JamesTang
 * Date: 14-7-9
 * Time: 上午10:39
 */
@Component
public class ClearRuleCacheService {


    @Autowired
    TRuleCapitalFeeMapper tRuleCapitalFeeMapper;

    @Autowired
    TRuleFeeCodeMapper tRuleFeeCodeMapper;

    @Autowired
    private TInfoOrgMapper orgMapper;


    private Map<String, TRuleCapitalFee> levelOneRuleCapitalFeeRepository = new ConcurrentHashMap<String, TRuleCapitalFee>();
    private Map<String, TRuleCapitalFee> levelTwoRuleCapitalFeeRepository = new ConcurrentHashMap<String, TRuleCapitalFee>();

    private Map<String, List<TRuleFeeCode>> ruleFeeRepositoryLevelOne = new ConcurrentHashMap<String, List<TRuleFeeCode>>();
    private Map<String, List<TRuleFeeCode>> ruleFeeRepositoryLevelTwo = new ConcurrentHashMap<String, List<TRuleFeeCode>>();


    public BigDecimal calcRuleFeeLv1(TLogOnlinePayment tLogOnlinePayment) {
        try {
            TRuleCapitalFee tRuleCapitalFee = this.getCapitalRulelevelone(tLogOnlinePayment);
            if (null != tRuleCapitalFee.getRemark()) {
                if (tRuleCapitalFee.getRemark().equals("clearfail")) {
                    throw new ClearException(BussErrorCode.ERROR_CODE_700017.getErrorcode(),
                            BussErrorCode.ERROR_CODE_700017.getErrordesc());
                }
            }

            String feeKey = tRuleCapitalFee.getFeeCode() + "";
            List<TRuleFeeCode> tRuleFeeCodeList = getFeeRuleLevelone(feeKey);// ruleFeeRepositoryLevelOne.get(feeKey);
            return feeCaculate(tLogOnlinePayment, tRuleCapitalFee, tRuleFeeCodeList);
        } catch (Exception e) {
            e.printStackTrace();
            return new BigDecimal(999999999);
        }
    }


    private BigDecimal feeCaculate(TLogOnlinePayment tLogOnlinePayment, TRuleCapitalFee tRuleCapitalFee, List<TRuleFeeCode> tRuleFeeCodeList) {
        char calctype = tRuleFeeCodeList.get(0).getCalcType().charAt(0);
        BigDecimal feeAmt = new BigDecimal(0);
        double percentage, bottom, corpus;
        if ("C".equals(tRuleCapitalFee.getDcFlagFee()) && "181010".equals(tLogOnlinePayment.getInteriorTransType())) {
            //默认为0
            return new BigDecimal(0);
        }
        switch (calctype) {
            case '1':     /*单笔固定金额*/
                for (TRuleFeeCode tRuleFeeCode : tRuleFeeCodeList) {
                    feeAmt = BigDecimal.valueOf(tRuleFeeCode.getAmt());
                }
                feeAmt = BigDecimal.valueOf(feeAmt.doubleValue());
                break;
            case '2':    /* 单笔交易金额按比例 */
                for (TRuleFeeCode tRuleFeeCode : tRuleFeeCodeList) {
                    percentage = tRuleFeeCode.getPercentage().doubleValue();
                    if (null != tRuleFeeCode.getBottomAmt() && tRuleFeeCode.getBottomAmt() > percentage * 0.01 * tLogOnlinePayment.getTransAmount().doubleValue()) {
                        feeAmt = BigDecimal.valueOf(tRuleFeeCode.getBottomAmt());
                    } else if (null != tRuleFeeCode.getCeilingAmt() && tRuleFeeCode.getCeilingAmt() != 0 && tRuleFeeCode.getCeilingAmt() < percentage * 0.01 * tLogOnlinePayment.getTransAmount().doubleValue()) {
                        feeAmt = BigDecimal.valueOf(tRuleFeeCode.getCeilingAmt());
                    } else {
                        feeAmt = BigDecimal.valueOf(percentage * 0.01 * tLogOnlinePayment.getTransAmount().doubleValue());
                    }
                }
                break;
            case '3':    /* 单笔固定金额+按比例 */
                for (TRuleFeeCode tRuleFeeCode : tRuleFeeCodeList) {
                    percentage = tRuleFeeCode.getPercentage().doubleValue();
                    feeAmt = BigDecimal.valueOf(percentage * 0.01 * tLogOnlinePayment.getTransAmount().doubleValue() + tRuleFeeCode.getAmt());
                }
                break;
            case '5':   /* 单笔金额分段百分比（全额） */
                //由于需要每笔去结算,故暂时不支持
                corpus = tLogOnlinePayment.getTransAmount().doubleValue();
                bottom = 0; //最小值
                for (TRuleFeeCode tRuleFeeCode : tRuleFeeCodeList) {
                    if (corpus > tRuleFeeCode.getTxnAmountStep()) {
                        if (bottom <= tRuleFeeCode.getTxnAmountStep()) {
                            bottom = tRuleFeeCode.getTxnAmountStep();
                            feeAmt = BigDecimal.valueOf(tRuleFeeCode.getPercentage().doubleValue() * 0.01 * corpus);
                        }
                    }
                }
                //  log.info("calctype 5 feeAmt 分段按比例= "+feeAmt);
                break;
            default:
                return new BigDecimal(0);
        }
        feeAmt = feeAmt.setScale(0, BigDecimal.ROUND_HALF_UP);//设置四舍五入
        return feeAmt;
    }

    public BigDecimal calcRuleFeeLv2(TLogOnlinePayment tLogOnlinePayment) {
        try {
            TRuleCapitalFee tRuleCapitalFee = this.getCapitalRuleleveltwo(tLogOnlinePayment);
            if (null != tRuleCapitalFee.getRemark()) {
                if (tRuleCapitalFee.getRemark().equals("clearfail")) {
                    throw new ClearException(BussErrorCode.ERROR_CODE_700017.getErrorcode(),
                            BussErrorCode.ERROR_CODE_700017.getErrordesc());
                }
            }
            String feeKey = tRuleCapitalFee.getFeeCode() + "";
            List<TRuleFeeCode> tRuleFeeCodeList = getFeeRuleLeveltwo(feeKey);// ruleFeeRepositoryLevelOne.get(feeKey);
            return feeCaculate(tLogOnlinePayment, tRuleCapitalFee, tRuleFeeCodeList);
        } catch (Exception e) {
            return new BigDecimal(999999999);
        }
    }


    private TRuleCapitalFee getCapitalRulelevelone(TLogOnlinePayment tLogOnlinePayment) {
        String strlevelkey = getCapitalFeekeyLeveOne(tLogOnlinePayment);
        TRuleCapitalFee tRuleCapitalFee = levelOneRuleCapitalFeeRepository.get(strlevelkey);
        if (null != tRuleCapitalFee) {
            return tRuleCapitalFee;
        }
        CapitalFeeMatcher capitalFeeMatcher = getCapitalRule(tLogOnlinePayment, "1");
        levelOneRuleCapitalFeeRepository.put(capitalFeeMatcher.getCapitalFeeRuleKey(), capitalFeeMatcher.getTRuleCapitalFee());
        return capitalFeeMatcher.getTRuleCapitalFee();

    }


    private TRuleCapitalFee getCapitalRuleleveltwo(TLogOnlinePayment tLogOnlinePayment) {
        String strlevelkey = getCapitalFeekeyLevelTwo(tLogOnlinePayment);
        TRuleCapitalFee tRuleCapitalFee = levelOneRuleCapitalFeeRepository.get(strlevelkey);
        if (null != tRuleCapitalFee) {
            return tRuleCapitalFee;
        }
        CapitalFeeMatcher capitalFeeMatcher = getCapitalRule(tLogOnlinePayment, "2");
        levelTwoRuleCapitalFeeRepository.put(capitalFeeMatcher.getCapitalFeeRuleKey(), capitalFeeMatcher.getTRuleCapitalFee());
        return capitalFeeMatcher.getTRuleCapitalFee();

    }


    private CapitalFeeMatcher getCapitalRule(TLogOnlinePayment tLogOnlinePayment, String settleLevel) {
        // String settleLv = "1";
/*        if (null == tLogOnlinePayment.getSupplyCode()) {
            return null;
        }
        if (null == tLogOnlinePayment.getSupplyCode()) {
            return null;
        }*/
        CapitalFeeMatcher capitalFeeMatcher = new CapitalFeeMatcher();
        TRuleCapitalFee tmpRuleCapitalFee = new TRuleCapitalFee();
        tmpRuleCapitalFee.setRemark("clearfail");
        capitalFeeMatcher.setTRuleCapitalFee(tmpRuleCapitalFee);
        String supplyOrgCode = tLogOnlinePayment.getSupplyOrgCode();

        if (null == supplyOrgCode) {
            return capitalFeeMatcher;
        }
        String acceptOrgCode = tLogOnlinePayment.getAcceptOrgCode();

        if (null == acceptOrgCode) {
            return capitalFeeMatcher;
        }

        String txnchannel = tLogOnlinePayment.getAcceptChannel();
        if (null == txnchannel) {
            return capitalFeeMatcher;
        }

        String payOrgCode = tLogOnlinePayment.getPayOrgCode();
        if (null == payOrgCode) {
            return capitalFeeMatcher;
        }


        String txnType = tLogOnlinePayment.getInteriorTransType();
        if (null == txnType) {
            return capitalFeeMatcher;
        }

        String accountType = tLogOnlinePayment.getAccountType();
        if (null == accountType) {
            return capitalFeeMatcher;
        }


        String txndate = tLogOnlinePayment.getAcceptOrgTransDate();
        if (null == txndate) {
            return capitalFeeMatcher;
        }


        String mchntCode = orgMapper.selectMchntType(tLogOnlinePayment.getSupplyOrgCode());
        String keystr;
        if (settleLevel.equals("1")) {
            keystr = acceptOrgCode + txnchannel + payOrgCode + txnType + accountType;
            supplyOrgCode = null;
        } else {
            keystr = supplyOrgCode + txnchannel + payOrgCode + txnType + accountType;
            acceptOrgCode = null;
        }
        capitalFeeMatcher.setCapitalFeeRuleKey(keystr);

        List<TRuleCapitalFee> tRuleCapitalFeelst =
                tRuleCapitalFeeMapper.findRuleCapitalFee(settleLevel, supplyOrgCode,
                        acceptOrgCode, txnchannel, payOrgCode, txnType, accountType, mchntCode, txndate);

        if (tRuleCapitalFeelst.size() <= 0) {
            accountType =""; //AccountType.CHANNEL.getValue();
            tRuleCapitalFeelst =
                    tRuleCapitalFeeMapper.findRuleCapitalFee(settleLevel, supplyOrgCode,
                            acceptOrgCode, txnchannel, payOrgCode, txnType, accountType, mchntCode, txndate);
        }

        if (tRuleCapitalFeelst.size() <= 0) {
            txnchannel = "99";
            tRuleCapitalFeelst =
                    tRuleCapitalFeeMapper.findRuleCapitalFee(settleLevel, supplyOrgCode,
                            acceptOrgCode, txnchannel, payOrgCode, txnType, accountType, mchntCode, txndate);
        }
        if (tRuleCapitalFeelst.size() <= 0) {
            mchntCode = "9999";
            tRuleCapitalFeelst =
                    tRuleCapitalFeeMapper.findRuleCapitalFee(settleLevel, supplyOrgCode,
                            acceptOrgCode, txnchannel, payOrgCode, txnType, accountType, mchntCode, txndate);
        }

        if (tRuleCapitalFeelst.size() <= 0) {
            acceptOrgCode = "999999999999999";
            tRuleCapitalFeelst =
                    tRuleCapitalFeeMapper.findRuleCapitalFee(settleLevel, supplyOrgCode,
                            acceptOrgCode, txnchannel, payOrgCode, txnType, accountType, mchntCode, txndate);
        }

        if (tRuleCapitalFeelst.size() <= 0) {
            payOrgCode = "999999999999999";
            tRuleCapitalFeelst =
                    tRuleCapitalFeeMapper.findRuleCapitalFee(settleLevel, supplyOrgCode,
                            acceptOrgCode, txnchannel, payOrgCode, txnType, accountType, mchntCode, txndate);
        }


        TRuleCapitalFee tRuleCapitalFee = tRuleCapitalFeelst.size() > 0 ? tRuleCapitalFeelst.get(0) : tmpRuleCapitalFee;
        capitalFeeMatcher.setTRuleCapitalFee(tRuleCapitalFee);
        return capitalFeeMatcher;
    }

    ;

    private List<TRuleFeeCode> getFeeRuleLevelone(String feeCode) {
        List<TRuleFeeCode> listRuleFeeCodes = ruleFeeRepositoryLevelOne.get(feeCode);
        if (null != listRuleFeeCodes) {
            return listRuleFeeCodes;
        }
        listRuleFeeCodes = tRuleFeeCodeMapper.selectFeeByCode(feeCode, "1", DateTime.now().toString("YYYYMMDD"));
        ruleFeeRepositoryLevelOne.put(feeCode, listRuleFeeCodes);
        return listRuleFeeCodes;


    }

    private List<TRuleFeeCode> getFeeRuleLeveltwo(String feeCode) {
        List<TRuleFeeCode> listRuleFeeCodes = ruleFeeRepositoryLevelTwo.get(feeCode);
        if (null != listRuleFeeCodes) {
            return listRuleFeeCodes;
        }
        listRuleFeeCodes = tRuleFeeCodeMapper.selectFeeByCode(feeCode, "2", DateTime.now().toString("YYYYMMDD"));
        ruleFeeRepositoryLevelTwo.put(feeCode, listRuleFeeCodes);
        return listRuleFeeCodes;
    }

    private String getCapitalFeekeyLeveOne(TLogOnlinePayment tLogOnlinePayment) {
        String keystr = tLogOnlinePayment.getAcceptOrgCode()
                + tLogOnlinePayment.getAcceptChannel() + tLogOnlinePayment.getPayOrgCode()
                + tLogOnlinePayment.getInteriorTransType() + tLogOnlinePayment.getAccountType();
        return keystr;
    }

    private String getCapitalFeekeyLevelTwo(TLogOnlinePayment tLogOnlinePayment) {
        String keystr = tLogOnlinePayment.getSupplyCode()
                + tLogOnlinePayment.getAcceptChannel() + tLogOnlinePayment.getPayOrgCode()
                + tLogOnlinePayment.getInteriorTransType() + tLogOnlinePayment.getAccountType();
        return keystr;
    }
}