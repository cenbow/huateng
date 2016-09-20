package com.huateng.p3.account.ServiceHelper;

import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.math.DoubleMath;
import com.huateng.p3.account.common.enummodel.FeeType;
import com.huateng.p3.account.component.CacheComponent;
import com.huateng.p3.account.persistence.models.TDictTxnCode;

/**
 * 交易手续费用计算器
 * Writer：JamemesTang
 * Date: 13-12-17
 * Time: 下午2:13
 */
@Component
public class TxnFeeCaculator {
    @Autowired
    private CacheComponent cacheComponent;

    public long calculateTxnFee(String txnType, long txnAmount) {
        TDictTxnCode tDictTxnCode = cacheComponent.getTxnTypeObj(txnType);
        if (null == tDictTxnCode) {
            return 0;
        }
        long fee = 0;
        String feeType = tDictTxnCode.getFeeType();
        long fixAmt = tDictTxnCode.getFeeFixAmt() == null ? 0 : tDictTxnCode.getFeeFixAmt();
        double feeRatio = tDictTxnCode.getFeeRatio() == null ? 0 : tDictTxnCode.getFeeRatio().doubleValue();
        long radioFeeMin = tDictTxnCode.getFeeMin() == null ? 0 : tDictTxnCode.getFeeMin();
        long radioFeeMax = tDictTxnCode.getFeeMax() == null ? 0 : tDictTxnCode.getFeeMax();

        if (FeeType.FEE_TYPE_FIX.getFeetype().equals(feeType)) {
            return fixAmt;
        }
        if (FeeType.FEE_TYPE_RATIO.getFeetype().equals(feeType)) {
            fee = DoubleMath.roundToLong(txnAmount * feeRatio, RoundingMode.HALF_UP);
            if (fee > radioFeeMax) {
                fee = radioFeeMax;
            } else if (fee < radioFeeMin) {
                fee = radioFeeMin;
            }
        }
        return fee;
    }


}
