package com.huateng.p3.account.commonservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.huateng.p3.account.ServiceHelper.SequenceGenerator;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.enummodel.OrgType;
import com.huateng.p3.account.common.enummodel.TrueFalseLabel;
import com.huateng.p3.account.common.enummodel.TxnSeqType;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.component.CacheComponent;
import com.huateng.p3.account.persistence.TLogPreauthApplyMapper;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TInfoOrg;
import com.huateng.p3.account.persistence.models.TLogOnlinePayment;
import com.huateng.p3.account.persistence.models.TLogPreauthApply;

/**
 * User: JamesTang Date: 14-1-6 Time: 下午4:06
 */
@Service
public class PreAuthService {

	@Autowired
	private TLogPreauthApplyMapper tLogPreauthApplyMapper;

	@Autowired
	private CacheComponent cacheComponent;

	@Autowired
	private TxnCheckService txnCheckService;
	
    @Autowired
    private SequenceGenerator sequenceGenerator;

	/**
	 * 产生预授权信息入库
	 * 
	 * @param supplyOrg
	 * @param paymentInfo
	 * @param account
	 * @return
	 */
	public TLogPreauthApply generatorPreAuthApplyInDb(TInfoOrg supplyOrg, PaymentInfo paymentInfo,
			PaymentInfo oldpaymentInfo, TInfoAccount account, String txnSeqNo, TLogPreauthApply oldTLogPreauthApply) {
		if (Strings.isNullOrEmpty(paymentInfo.getTxnDscpt())) {
			String txnDscpt = supplyOrg.getOrgFname()
					+ cacheComponent.getTxnTypeObj(paymentInfo.getInnerTxnType()).getTxnName();
			paymentInfo.setTxnDscpt(txnDscpt);
		}

		TLogPreauthApply tLogPreauthApply = new TLogPreauthApply();

		tLogPreauthApply.setPreAuthNo(paymentInfo.getAcceptTxnSeqNo());
		tLogPreauthApply.setTxnSeqNo(txnSeqNo);
		tLogPreauthApply.setTxnTime(paymentInfo.getTxnDate());
		tLogPreauthApply.setBusinessType(paymentInfo.getBussinessType());
		tLogPreauthApply.setTxnType(paymentInfo.getInnerTxnType());
		tLogPreauthApply.setTxnDscpt(paymentInfo.getTxnDscpt());
		tLogPreauthApply.setTxnChannel(paymentInfo.getChannel());
		tLogPreauthApply.setAcceptOrgCode(paymentInfo.getAcceptOrgCode());
		tLogPreauthApply.setAcceptTransDate(paymentInfo.getAcceptDate());
		tLogPreauthApply.setAcceptTransSeqNo(paymentInfo.getAcceptTxnSeqNo());
		tLogPreauthApply.setAcceptTransTime(paymentInfo.getAcceptTime());
		tLogPreauthApply.setAcceptOrgType(OrgType.ORG_TYPE_ORG.getOrgtype());
		// tLogPreauthApply.setTransferOrgCode(transferOrgCode);
		// tLogPreauthApply.setTransferOrgType(CodeGenerator.extractOrgType(transferOrgCode));
		tLogPreauthApply.setAuthAmt(paymentInfo.getAmount());
		// tLogPreauthApply.setInnerCardNo(innerCardNo);
		// tLogPreauthApply.setCardMediaType(cardMediaType);
		tLogPreauthApply.setAccountType(account.getType());
		tLogPreauthApply.setAccountNo(account.getAccountNo());
		tLogPreauthApply.setAreaCode(supplyOrg.getAreaCode());
		tLogPreauthApply.setCityCode(supplyOrg.getCityCode());
		tLogPreauthApply.setTerminalNo(paymentInfo.getTerminalNo());
		tLogPreauthApply.setTerminalSeqNo(paymentInfo.getTerminalSeqNo());
		tLogPreauthApply.setTransSeqType(paymentInfo.getTxnSeqType().getSeqType());
		tLogPreauthApply.setSupplyOrgCode(supplyOrg.getOrgCode());
		tLogPreauthApply.setSupplyOrgType(OrgType.ORG_TYPE_MERCHANT.getOrgtype());
		tLogPreauthApply.setPayOrgCode(paymentInfo.getPayOrgCode());
		tLogPreauthApply.setPayOrgType(OrgType.ORG_TYPE_ORG.getOrgtype());
		if (null != oldpaymentInfo) {
			tLogPreauthApply.setlAcceptTransSeqNo(oldpaymentInfo.getAcceptTxnSeqNo());
			tLogPreauthApply.setlAcceptTransDate(oldpaymentInfo.getAcceptDate());
			tLogPreauthApply.setlAcceptTransTime(oldpaymentInfo.getAcceptTime());
		}
		if(oldTLogPreauthApply != null){
			tLogPreauthApply.setlTxnSeqNo(oldTLogPreauthApply.getTxnSeqNo());
		}
		tLogPreauthApplyMapper.insert(tLogPreauthApply);
		return tLogPreauthApply;
	}

	;

	/**
	 * 预授权确认
	 * 
	 * @param paymentInfo
	 * @param account
	 */
	public TLogPreauthApply preauthCommit(PaymentInfo paymentInfo, TInfoAccount account, TLogOnlinePayment tLogOnlinePayment) {
		TLogPreauthApply preAuthApplylog = null;
		if(null != tLogOnlinePayment){
			preAuthApplylog = tLogPreauthApplyMapper.findPreAuthApply(tLogOnlinePayment.getAcceptOrgSerialNo(),
					tLogOnlinePayment.getSupplyOrgCode(), account.getAccountNo(), TxnSeqType.TRANS_SEQ_TYPE_NORMAL.getSeqType());
		}else{
			preAuthApplylog = tLogPreauthApplyMapper.findPreAuthApply(paymentInfo.getAcceptTxnSeqNo(),
					paymentInfo.getSupplyOrgCode(), account.getAccountNo(), TxnSeqType.TRANS_SEQ_TYPE_NORMAL.getSeqType());
		}
		txnCheckService.checkPreAuthComplete(preAuthApplylog, paymentInfo.getAmount());
		/*
		 * 更新申请记录的完成标识
		 */
		if(null != preAuthApplylog){
			preAuthApplylog.setAuthEndDate(paymentInfo.getTxnDate());
			preAuthApplylog.setAuthEndAmt(paymentInfo.getAmount());
			tLogPreauthApplyMapper.updateByPrimaryKeySelective(preAuthApplylog);
		}
		return preAuthApplylog;
	}
	/** 
	 * 预授权撤销原交易
	 * @param applyPayInfo
	 * @param account
	 * @param cancelTxnSeqNo
	 * @return
	 */
	public TLogPreauthApply cancelOldPreAuthApply(PaymentInfo applyPayInfo, TInfoAccount account, String cancelTxnSeqNo) {
		/*
		 * 查找原预授权申请记录日志
		 */
		List<TLogPreauthApply> logPreauthApplies = tLogPreauthApplyMapper.findOldPreAuthApply(
				TxnSeqType.TRANS_SEQ_TYPE_NORMAL.getSeqType(), applyPayInfo.getAcceptOrgCode(),
				applyPayInfo.getAcceptTxnSeqNo(), applyPayInfo.getAcceptDate(), applyPayInfo.getAcceptTime());

		// 如果原正交易不存在，则不能撤销
		if ((null == logPreauthApplies) || (logPreauthApplies.isEmpty())) {
			throw new BizException(BussErrorCode.ERROR_CODE_200081.getErrorcode(),
					BussErrorCode.ERROR_CODE_200081.getErrordesc());
		}
		TLogPreauthApply oldPreAuthApply = oldPreAuthApplyCancelCheck(account, applyPayInfo, logPreauthApplies);

		// 更新原正交易的撤销标识
		oldPreAuthApply.setCancelFlag(TrueFalseLabel.TRUE.getLablCode());
		oldPreAuthApply.setCancelTxnSeqNo(cancelTxnSeqNo);
		tLogPreauthApplyMapper.updateByPrimaryKeySelective(oldPreAuthApply);
		return oldPreAuthApply;
	}

	/**
	 * 预授权冲正原交易
	 * 
	 * @param applyPayInfo
	 * @param account
	 * @param rollbackTxnSeqNo
	 * @return
	 */
	public TLogPreauthApply rollbackOldPreAuthApply(TInfoOrg supplyOrg, PaymentInfo applyPayInfo, TInfoAccount account,
			String rollbackTxnSeqNo, TxnResultObject txnResultObject) {
		/*
		 * 查找原预授权申请记录日志
		 */
		List<TLogPreauthApply> logPreauthApplies = tLogPreauthApplyMapper.findOldPreAuthApply(
				TxnSeqType.TRANS_SEQ_TYPE_NORMAL.getSeqType(), applyPayInfo.getAcceptOrgCode(),
				applyPayInfo.getAcceptTxnSeqNo(), applyPayInfo.getAcceptDate(), applyPayInfo.getAcceptTime());
		// 判断是否伪造冲正对象
		if ((null == logPreauthApplies) || (logPreauthApplies.isEmpty())) {
			// 如果原正交易不存在，则伪造一条正交易
			String txnSeqNo = sequenceGenerator.generatorPreAuthTxnSeq();
			TLogPreauthApply oldPreAuthApply = this.generatorPreAuthApplyInDb(supplyOrg, applyPayInfo, null, account,
					txnSeqNo, null);
			txnResultObject.setRollbackFake(true);
			logPreauthApplies = Lists.newArrayList();
			logPreauthApplies.add(oldPreAuthApply);
		}
		TLogPreauthApply oldPreAuthApply = oldPreAuthApplyRollbackCheck(account, applyPayInfo, logPreauthApplies);
		// 更新原正交易的冲正标识
		oldPreAuthApply.setRevsalFlag(TrueFalseLabel.TRUE.getLablCode());
		oldPreAuthApply.setRevsalTxnSeqNo(rollbackTxnSeqNo);
		tLogPreauthApplyMapper.updateByPrimaryKeySelective(oldPreAuthApply);

		return oldPreAuthApply;
	}
	

	/**
     * 撤销预授权检测
     */
    private TLogPreauthApply oldPreAuthApplyCancelCheck(TInfoAccount account, PaymentInfo applyPayInfo, List<TLogPreauthApply> logPreauthApplies) {
    	if (logPreauthApplies.size() > 1) {
			// 存在多个原正交易，错误
			throw new BizException(BussErrorCode.ERROR_CODE_900005.getErrorcode(),
					BussErrorCode.ERROR_CODE_900005.getErrordesc());

		}
		TLogPreauthApply oldPreAuthApply = logPreauthApplies.get(0);
		if (!oldPreAuthApply.getAuthAmt().equals(applyPayInfo.getAmount())) {
			// 撤销金额与原交易不符
			throw new BizException(BussErrorCode.ERROR_CODE_200030.getErrorcode(),
					BussErrorCode.ERROR_CODE_200030.getErrordesc());
		}
		if (!oldPreAuthApply.getAccountNo().equals(account.getAccountNo())) {
			// 撤销卡号和原卡号不一致
			throw new BizException(BussErrorCode.ERROR_CODE_200031.getErrorcode(),
					BussErrorCode.ERROR_CODE_200031.getErrordesc());
		}
		if (TrueFalseLabel.TRUE.getLablCode().equals(oldPreAuthApply.getCancelFlag())) {
			// 如果原正交易已有撤销标识，说明是重复撤销，不处理本次撤销请求
			throw new BizException(BussErrorCode.ERROR_CODE_200080.getErrorcode(),
					BussErrorCode.ERROR_CODE_200080.getErrordesc());
		}
		if (TrueFalseLabel.TRUE.getLablCode().equals(oldPreAuthApply.getRevsalFlag())) {
			// 如果原正交易已有冲正标识，不处理本次撤销请求
			throw new BizException(BussErrorCode.ERROR_CODE_200083.getErrorcode(),
					BussErrorCode.ERROR_CODE_200083.getErrordesc());
		}
        return oldPreAuthApply;

    }
    
    
    /**
     * 冲正预授权检测
     */
    private TLogPreauthApply oldPreAuthApplyRollbackCheck(TInfoAccount account, PaymentInfo applyPayInfo, List<TLogPreauthApply> logPreauthApplies) {
    	if (logPreauthApplies.size() > 1) {
			// 原正交易数错误
			throw new BizException(BussErrorCode.ERROR_CODE_900005.getErrorcode(),
					BussErrorCode.ERROR_CODE_900005.getErrordesc());
		}
		TLogPreauthApply oldPreAuthApply = logPreauthApplies.get(0);

		if (!oldPreAuthApply.getAuthAmt().equals(applyPayInfo.getAmount())) {
			// 冲正金额与原交易不符
			throw new BizException(BussErrorCode.ERROR_CODE_500032.getErrorcode(),
					BussErrorCode.ERROR_CODE_500032.getErrordesc());
		}
		if (!oldPreAuthApply.getAccountNo().equals(account.getAccountNo())) {
			// 冲正账户号和原账户号不一致
			throw new BizException(BussErrorCode.ERROR_CODE_500033.getErrorcode(),
					BussErrorCode.ERROR_CODE_500033.getErrordesc());
		}
		if (TrueFalseLabel.TRUE.getLablCode().equals(oldPreAuthApply.getRevsalFlag())) {
			// 如果原正交易已有冲正标识，说明是重复冲正，不处理本次冲正请求
			throw new BizException(BussErrorCode.ERROR_CODE_200041.getErrorcode(),
					BussErrorCode.ERROR_CODE_200041.getErrordesc());
		}
		if (TrueFalseLabel.TRUE.getLablCode().equals(oldPreAuthApply.getCancelFlag())) {
			// 如果原正交易已有撤销标识，不处理本次冲正请求
			throw new BizException(BussErrorCode.ERROR_CODE_200084.getErrorcode(),
					BussErrorCode.ERROR_CODE_200084.getErrordesc());
		}
        return oldPreAuthApply;

    }

	/**
	 * 预授权撤销冲正原交易
	 * 
	 * @param supplyOrg
	 * @param applyPayInfo
	 * @param account
	 * @param rollbackTxnSeqNo
	 * @param txnResultObject
	 * @return
	 */
	public TLogPreauthApply rollbackCancelPreAuthApply(TInfoOrg supplyOrg, PaymentInfo applyPayInfo, TInfoAccount account,
			String rollbackCancelTxnSeqNo, TxnResultObject txnResultObject) {
		/*
		 * 查找原预授权申请撤销记录日志
		 */
		List<TLogPreauthApply> logCancelPreauthApplies = tLogPreauthApplyMapper.findOldPreAuthApply(
				TxnSeqType.TRANS_SEQ_TYPE_CANCEL.getSeqType(), applyPayInfo.getAcceptOrgCode(),
				applyPayInfo.getAcceptTxnSeqNo(), applyPayInfo.getAcceptDate(), applyPayInfo.getAcceptTime());
		// 判断是否伪造冲正对象（原预授权申请撤销记录日志）
		if ((null == logCancelPreauthApplies) || (logCancelPreauthApplies.isEmpty())) {
			// 如果原正交易不存在，则伪造一条正交易
			String cancelTxnSeqNo = sequenceGenerator.generatorCancelPreAuthTxnSeq();
			TLogPreauthApply forgeCancelPreAuthApply = this.generatorPreAuthApplyInDb(supplyOrg, applyPayInfo, null, account,
					cancelTxnSeqNo, null);
			txnResultObject.setRollbackFake(true);
			logCancelPreauthApplies = Lists.newArrayList();
			logCancelPreauthApplies.add(forgeCancelPreAuthApply);
		}
		TLogPreauthApply oldCancelPreAuthApply = oldPreAuthApplyRollbackCheck(account, applyPayInfo, logCancelPreauthApplies);
		// 更新原正交易的冲正标识
		oldCancelPreAuthApply.setRevsalFlag(TrueFalseLabel.TRUE.getLablCode());
		oldCancelPreAuthApply.setRevsalTxnSeqNo(rollbackCancelTxnSeqNo);
		tLogPreauthApplyMapper.updateByPrimaryKeySelective(oldCancelPreAuthApply);
		return oldCancelPreAuthApply;
	}

	/**
	 * 预授权申请撤销冲正原始交易处理
	 * 
	 * @param supplyOrg
	 * @param applyPayInfo
	 * @param account
	 * @param rollbackTxnSeqNo
	 * @param txnResultObject
	 * @return
	 */
	public TLogPreauthApply rollbackCancelPreAuthApply(TLogPreauthApply cancelPreAuthApply, boolean isRollbackFake) {
		// 伪造交易不需要对原始交易进行撤销冲正处理
		if(!isRollbackFake){
			TLogPreauthApply originalLogPreAuthApply = tLogPreauthApplyMapper.selectByPrimaryKey(cancelPreAuthApply.getlTxnSeqNo());
			if(null == originalLogPreAuthApply){
				throw new BizException(BussErrorCode.ERROR_CODE_200081.getErrorcode(), BussErrorCode.ERROR_CODE_200081.getErrordesc());
			}
			originalLogPreAuthApply.setCancelFlag("");
			originalLogPreAuthApply.setCancelTxnSeqNo("");
			tLogPreauthApplyMapper.updateByPrimaryKeySelective(originalLogPreAuthApply);
			return originalLogPreAuthApply;
		}
		return null;
	}
	
	/**
	 * 清除预授权申请完成的金额和时间
	 * 
	 * @param account
	 * @param paymentInfo
	 * @return
	 */
	public TLogPreauthApply preAuthCompleteCancel(TInfoAccount account, PaymentInfo paymentInfo) {
		// 查询原授权申请记录
		TLogPreauthApply preAuthApplylog = tLogPreauthApplyMapper.findPreAuthApply(paymentInfo.getAcceptTxnSeqNo(),
				paymentInfo.getSupplyOrgCode(), account.getAccountNo(), TxnSeqType.TRANS_SEQ_TYPE_NORMAL.getSeqType());
		if(preAuthApplylog != null){
			preAuthApplylog.setAuthEndAmt(0L);
			preAuthApplylog.setAuthEndDate(null);
			tLogPreauthApplyMapper.updateByPrimaryKeySelective(preAuthApplylog);
		}
		return preAuthApplylog;
	}
	
}