package com.huateng.p3.account.ServiceHelper;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.huateng.p3.account.common.enummodel.AccountType;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.enummodel.OrgDefaultCode;
import com.huateng.p3.account.common.enummodel.SeqCoreTranType;
import com.huateng.p3.account.common.enummodel.SeqPlatBussKind;
import com.huateng.p3.account.common.enummodel.SeqPlatType;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.common.util.StringUtil;
import com.huateng.p3.account.component.CacheComponent;
import com.huateng.p3.account.persistence.TSequenceProduceMapper;


/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-12-10
 */
@Service
public class SequenceGenerator {

    private final static DateTimeFormatter fullTime = DateTimeFormat.forPattern("yyyyMMddHHmmss");
    @Autowired
    private TSequenceProduceMapper sequenceDao;
    @Autowired
    private CacheComponent cacheComponent;
    public String generateCustomerNo(String areaCode, String cityCode) {

        
        String teleCode =cacheComponent.getAreaCityObj(cityCode).getTeleCode();
        if (teleCode == null || teleCode.trim().length() != 3) {
            // 非法电信区号
        	throw new BizException(BussErrorCode.ERROR_CODE_200024.getErrorcode(),BussErrorCode.ERROR_CODE_200024.getErrordesc());

        }
        
        return "8630"
                + teleCode
                + Strings.padStart(sequenceDao.produceSequence("CORE.S_ACCOUNT_NO_" + extractProvCode(areaCode)), 9, '0');
    }

    public String extractProvCode(String cityCode) {
        String areaCode = OrgDefaultCode.GROUP_AREA_CODE.getOrgCode();
        if (Strings.isNullOrEmpty(cityCode)) {
            return areaCode;
        }
        if (cityCode.substring(0, 4).equals(
                areaCode.substring(0, 4))) {
            return areaCode;
        }
        return cityCode.substring(0, 2) + "0000";
    }

    public String generateAccountNo(String customerNo, AccountType type) {
        return "863" + type.getValue() + customerNo.substring(4);
    }


    private String generateSeq(String appcode, String bussCode, String bussType, String seqName) {
        StringBuffer txnSeq = new StringBuffer(35);
        txnSeq.append(appcode).append(bussCode).append(bussType).append("000");
        // txnSeq.append("A");
        String datastr = DateTime.now().toString("MMddHHmmss");
        txnSeq.append(datastr);
        txnSeq.append(StringUtils.right(Strings.padStart(
                sequenceDao.produceSequence(seqName), 9, '0'), 9));
        return txnSeq.toString();
    }
    
    /**
     * 快捷绑卡流水号
     * @param seq
     * @return
     */
    public String getBindingSeq() {
    	return "1" + DateTime.now().toString("yyyyMMddHHmmss") +  Strings.padStart(sequenceDao.produceSequence("S_CARD_BINDING"), 8, '0');  
	}
    
    /**
     * 短信账户变动表流水
     * @return
     */
    public String getAccountAlterSeq() {
    	return sequenceDao.produceSequence("S_T_LOG_ACCOUNT_ALTER");  
	}
    

    /**
     * 生成交易撤销流水
     *
     * @return
     */
    public String generatorTxnCancelSeqNo() {
        return generateSeq(SeqPlatType.SEQ_TRANA_ACCOUNT_CODE.getPlatcode(),
                SeqPlatBussKind.SEQ_TRANA_TYPE.getSeqkingcode(),
                SeqCoreTranType.TRANS_SEQ_TYPE_CANCEL.getTrancoreseqtypecode(), "s_account_online_txn_seq");
    }
    
    /**
     * 生成交易冲正流水
     *
     * @return
     */
    public String generatorTxnRollbackSeqNo() {
        return generateSeq(SeqPlatType.SEQ_TRANA_ACCOUNT_CODE.getPlatcode(),
                SeqPlatBussKind.SEQ_TRANA_TYPE.getSeqkingcode(),
                SeqCoreTranType.TRANS_SEQ_TYPE_ROLLBACK.getTrancoreseqtypecode(), "s_account_online_txn_seq");
    }

    public String generatorPreAuthTxnSeq() {
        return generateSeq(SeqPlatType.SEQ_TRANA_ACCOUNT_CODE.getPlatcode(),
                SeqPlatBussKind.SEQ_TRANA_TYPE.getSeqkingcode(),
                SeqCoreTranType.TRANS_SEQ_TYPE_NORMAL.getTrancoreseqtypecode(), "S_T_LOG_PREAUTH_APPLY");
    }

    public String generatorRollbackPreAuthTxnSeq() {
        return generateSeq(SeqPlatType.SEQ_TRANA_ACCOUNT_CODE.getPlatcode(),
                SeqPlatBussKind.SEQ_TRANA_TYPE.getSeqkingcode(),
                SeqCoreTranType.TRANS_SEQ_TYPE_ROLLBACK.getTrancoreseqtypecode(), "S_T_LOG_PREAUTH_APPLY");
    }
    
    public String generatorCancelPreAuthTxnSeq() {
        return generateSeq(SeqPlatType.SEQ_TRANA_ACCOUNT_CODE.getPlatcode(),
                SeqPlatBussKind.SEQ_TRANA_TYPE.getSeqkingcode(),
                SeqCoreTranType.TRANS_SEQ_TYPE_CANCEL.getTrancoreseqtypecode(), "S_T_LOG_PREAUTH_APPLY");
    }

    /**
     * 生成交易流水
     *
     * @return
     */
    public String generatorTxnSeqNo() {
        return generateSeq(SeqPlatType.SEQ_TRANA_ACCOUNT_CODE.getPlatcode(),
                SeqPlatBussKind.SEQ_TRANA_TYPE.getSeqkingcode(),
                SeqCoreTranType.TRANS_SEQ_TYPE_NORMAL.getTrancoreseqtypecode(), "core.s_account_online_txn_seq");
    }
    
    /**
     * 生成退货流水
     *
     * @return
     */
    public String generatorRefundTxnSeqNo() {
        return generateSeq(SeqPlatType.SEQ_TRANA_ACCOUNT_CODE.getPlatcode(),
                SeqPlatBussKind.SEQ_TRANA_TYPE.getSeqkingcode(),
                SeqCoreTranType.TRANS_SEQ_TYPE_REFUND.getTrancoreseqtypecode(), "core.s_account_online_txn_seq");
    }


    public String generateProductPropertyNo() {
        return  SeqPlatType.SEQ_MANAGER_ACCOUNT_CODE.getPlatcode()+
                SeqPlatBussKind.SEQ_MANAGER_TYPE.getSeqkingcode()+
                SeqCoreTranType.TRANS_SEQ_TYPE_NORMAL.getTrancoreseqtypecode()
                + fullTime.print(DateTime.now())
                + Strings.padStart(sequenceDao.produceSequence("s_t_info_product_property"), 9, '0');
    }

  
    /**
     * 获取原始交易类型   例如  131011资金帐户消费撤销   它的原始交易是 131010
     *
     * @param txnType
     * @return
     */
    public String convertNormalTxnType(String txnType) {
        if (txnType == null) {
            return null;
        }
        return txnType.substring(0, 5) + "0";
    }
    
    
    /**
     * 获取原始交易类型   例如  131013资金帐户消费撤销冲正   它的原始交易是 131011
     *
     * @param txnType
     * @return
     */
    public String convertCancelTxnType(String txnType) {
        if (txnType == null) {
            return null;
        }
        return txnType.substring(0, 5) + "1";
    }	
    
    /**
     * 实名认证的组合序列生成
     * */
	public  String generateAuthenticationInfoSeqVal() {
		return DateTime.now().toString("yyyyMMdd") + 
				StringUtil.fillLeft(sequenceDao.produceSequence("CORE.S_T_REALNAME_APPLY"), '0', 10);
	}
	public  String generateAccountMgmTxnSeq() {
		return generateSeq(SeqPlatType.SEQ_TRANA_ACCOUNT_CODE.getPlatcode(),
	                SeqPlatBussKind.SEQ_MANAGER_TYPE.getSeqkingcode(),
	                SeqCoreTranType.TRANS_SEQ_TYPE_NORMAL.getTrancoreseqtypecode(), "CORE.S_LOG_ACCOUNT_MANAGEMENT");
	}
	
	
	public String generatorRefundApplySeq() {
		return "1" + StringUtil.fillLeft(sequenceDao.produceSequence("core.s_t_log_refund_apply"), '0', 19);
    }

    public String generatorAccountAlter() {
        return sequenceDao.produceSequence("S_T_LOG_ACCOUNT_ALTER");
    }
	
}
