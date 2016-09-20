package com.huateng.p3.account.persistence.models;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;
@ToString
public class TLogOnlinePaymentHis implements Serializable{

	private static final long serialVersionUID = -3570002901877391602L;

	private String transSerialNo;

    private String settlementDate;

    private Date transTime;

    private Integer transMonth;

    private String extBusinessType;

    private String interiorTransType;

    private String transMemo;

    private String acceptChannel;

    private String acceptOrgCode;

    private String acceptOrgType;

    private String acceptOrgSerialNo;

    private String acceptOrgTransDate;

    private String acceptOrgTransTime;

    private String transferOrgCode;

    private String transferOrgType;

    private String transferOrgSerialNo;

    private String transferOrgTransDate;

    private String transferOrgTransTime;

    private String payOrgCode;

    private String payOrgType;

    private String payOrgSerialNo;

    private String payOrgTransDate;

    private String payOrgTransTime;

    private String supplyOrgCode;

    private String supplyOrgType;

    private String supplyOrgSerialNo;

    private String supplyOrgTransDate;

    private String supplyOrgTransTime;

    private Long transAmount = 0l;

    private Long beforeTransAmount;

    private Long afterTransAmount;

    private String feeInFlag;

    private String profitInFlag;

    private Long feeAmount = 0l;

    private Long profitAmount = 0l;

    private Long pFeeCode;

    private Long pFeeAmount = 0l;

    private Long acceptFeeCode;

    private Long acceptFeeAmount = 0l;

    private String acceptFeeFlag;

    private Long transferFeeCode;

    private Long transferFeeAmount = 0l;

    private String transferFeeFlag;

    private Long payFeeCode;

    private Long payFeeAmount = 0l;

    private String payFeeFlag;

    private Long supplyFeeCode;

    private Long supplyFeeAmount = 0l;

    private String supplyFeeFlag;

    private String cardSerialNo;

    private Long cardCount;

    private String transTac;

    private String tacCheckLabel;

    private Long cardDptAmount;

    private String cardExpDate;

    private String batchFileId;

    private String uploadFlag;

    private String uploadPkgNo;

    private String innerCardNo;

    private String outerCardNo;

    private String cardMediaType;

    private String cardBrandType;

    private String accountType;

    private String accountNo;

    private String areaCode;

    private String cityCode;

    private String terminalNo;

    private String terminalSerialNo;

    private String terminalCommSerialNo;

    private String terminalOperNo;

    private Long batchNo;

    private String transSerialType;

    private String transAcceptMatchFlag;

    private String transTransferMatchFlag;

    private String transPayMatchFlag;

    private String transSupplyMatchFlag;

    private String paymentObjNo;

    private String paymentObjType;

    private String lAcceptOrgSerialNo;

    private String lAcceptOrgTransDate;

    private String lAcceptOrgTransTime;

    private String lTransferOrgSerialNo;

    private String lTransferOrgTransDate;

    private String lTransferOrgTransTime;

    private String lPayTransSerialNo;

    private String lPayOrgTransDate;

    private String lPayOrgTransTime;

    private String lSupplyOrgSerialNo;

    private String lSupplyOrgTransDate;

    private String lSupplyOrgTransTime;

    private String psamCardNo;

    private String preAuthSerialNo;

    private String acceptProcFlag;

    private String transferProcFlag;

    private String payProcFlag;

    private String supplyProcFlag;

    private String acceptRespCode;

    private String transferRespCode;

    private String payRespCode;

    private String supplyCode;

    private Date transFinTs;

    private Date transToTs;

    private String isClearing;

    private String clearingId;

    private Long resendNum;

    private String settlementMonth;

    private String settlementDay;

    private String revsalFlag;

    private String revsalTxnSerialNo;

    private String cancelFlag;

    private String cancelTxnSerialNo;

    private String lTransSerialNo;

    private String returnFlag;

    private Long returnCount = 0l;;

    private Long returnAmount = 0l;

    private String dubiousFlag;

    private String batchDate;

    private String batchFlag;

    private Long acceptSettleAmount;

    private Long transferSettleAmount;

    private Long paySettleAmount;

    private Long supplySettleAmount;

    private String transferInOrgCode;

    private String inAccountNo;

    private String transferOutOrgCode;

    private String outAccountNo;

    private String resvFld1;

    private String resvFld2;

    private String resvFld3;

    private String resvFld4;

    private String resvFld5;

    private String customerNo;

    private String productNo;

    private String customerAreaCode;

    private String customerCityCode;
    
    private String tableName;

	public String getTransSerialNo() {
		return transSerialNo;
	}

	public void setTransSerialNo(String transSerialNo) {
		this.transSerialNo = transSerialNo;
	}

	public String getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	public Date getTransTime() {
		return transTime;
	}

	public void setTransTime(Date transTime) {
		this.transTime = transTime;
	}

	public Integer getTransMonth() {
		return transMonth;
	}

	public void setTransMonth(Integer transMonth) {
		this.transMonth = transMonth;
	}

	public String getExtBusinessType() {
		return extBusinessType;
	}

	public void setExtBusinessType(String extBusinessType) {
		this.extBusinessType = extBusinessType;
	}

	public String getInteriorTransType() {
		return interiorTransType;
	}

	public void setInteriorTransType(String interiorTransType) {
		this.interiorTransType = interiorTransType;
	}

	public String getTransMemo() {
		return transMemo;
	}

	public void setTransMemo(String transMemo) {
		this.transMemo = transMemo;
	}

	public String getAcceptChannel() {
		return acceptChannel;
	}

	public void setAcceptChannel(String acceptChannel) {
		this.acceptChannel = acceptChannel;
	}

	public String getAcceptOrgCode() {
		return acceptOrgCode;
	}

	public void setAcceptOrgCode(String acceptOrgCode) {
		this.acceptOrgCode = acceptOrgCode;
	}

	public String getAcceptOrgType() {
		return acceptOrgType;
	}

	public void setAcceptOrgType(String acceptOrgType) {
		this.acceptOrgType = acceptOrgType;
	}

	public String getAcceptOrgSerialNo() {
		return acceptOrgSerialNo;
	}

	public void setAcceptOrgSerialNo(String acceptOrgSerialNo) {
		this.acceptOrgSerialNo = acceptOrgSerialNo;
	}

	public String getAcceptOrgTransDate() {
		return acceptOrgTransDate;
	}

	public void setAcceptOrgTransDate(String acceptOrgTransDate) {
		this.acceptOrgTransDate = acceptOrgTransDate;
	}

	public String getAcceptOrgTransTime() {
		return acceptOrgTransTime;
	}

	public void setAcceptOrgTransTime(String acceptOrgTransTime) {
		this.acceptOrgTransTime = acceptOrgTransTime;
	}

	public String getTransferOrgCode() {
		return transferOrgCode;
	}

	public void setTransferOrgCode(String transferOrgCode) {
		this.transferOrgCode = transferOrgCode;
	}

	public String getTransferOrgType() {
		return transferOrgType;
	}

	public void setTransferOrgType(String transferOrgType) {
		this.transferOrgType = transferOrgType;
	}

	public String getTransferOrgSerialNo() {
		return transferOrgSerialNo;
	}

	public void setTransferOrgSerialNo(String transferOrgSerialNo) {
		this.transferOrgSerialNo = transferOrgSerialNo;
	}

	public String getTransferOrgTransDate() {
		return transferOrgTransDate;
	}

	public void setTransferOrgTransDate(String transferOrgTransDate) {
		this.transferOrgTransDate = transferOrgTransDate;
	}

	public String getTransferOrgTransTime() {
		return transferOrgTransTime;
	}

	public void setTransferOrgTransTime(String transferOrgTransTime) {
		this.transferOrgTransTime = transferOrgTransTime;
	}

	public String getPayOrgCode() {
		return payOrgCode;
	}

	public void setPayOrgCode(String payOrgCode) {
		this.payOrgCode = payOrgCode;
	}

	public String getPayOrgType() {
		return payOrgType;
	}

	public void setPayOrgType(String payOrgType) {
		this.payOrgType = payOrgType;
	}

	public String getPayOrgSerialNo() {
		return payOrgSerialNo;
	}

	public void setPayOrgSerialNo(String payOrgSerialNo) {
		this.payOrgSerialNo = payOrgSerialNo;
	}

	public String getPayOrgTransDate() {
		return payOrgTransDate;
	}

	public void setPayOrgTransDate(String payOrgTransDate) {
		this.payOrgTransDate = payOrgTransDate;
	}

	public String getPayOrgTransTime() {
		return payOrgTransTime;
	}

	public void setPayOrgTransTime(String payOrgTransTime) {
		this.payOrgTransTime = payOrgTransTime;
	}

	public String getSupplyOrgCode() {
		return supplyOrgCode;
	}

	public void setSupplyOrgCode(String supplyOrgCode) {
		this.supplyOrgCode = supplyOrgCode;
	}

	public String getSupplyOrgType() {
		return supplyOrgType;
	}

	public void setSupplyOrgType(String supplyOrgType) {
		this.supplyOrgType = supplyOrgType;
	}

	public String getSupplyOrgSerialNo() {
		return supplyOrgSerialNo;
	}

	public void setSupplyOrgSerialNo(String supplyOrgSerialNo) {
		this.supplyOrgSerialNo = supplyOrgSerialNo;
	}

	public String getSupplyOrgTransDate() {
		return supplyOrgTransDate;
	}

	public void setSupplyOrgTransDate(String supplyOrgTransDate) {
		this.supplyOrgTransDate = supplyOrgTransDate;
	}

	public String getSupplyOrgTransTime() {
		return supplyOrgTransTime;
	}

	public void setSupplyOrgTransTime(String supplyOrgTransTime) {
		this.supplyOrgTransTime = supplyOrgTransTime;
	}

	public Long getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(Long transAmount) {
		this.transAmount = transAmount;
	}

	public Long getBeforeTransAmount() {
		return beforeTransAmount;
	}

	public void setBeforeTransAmount(Long beforeTransAmount) {
		this.beforeTransAmount = beforeTransAmount;
	}

	public Long getAfterTransAmount() {
		return afterTransAmount;
	}

	public void setAfterTransAmount(Long afterTransAmount) {
		this.afterTransAmount = afterTransAmount;
	}

	public String getFeeInFlag() {
		return feeInFlag;
	}

	public void setFeeInFlag(String feeInFlag) {
		this.feeInFlag = feeInFlag;
	}

	public String getProfitInFlag() {
		return profitInFlag;
	}

	public void setProfitInFlag(String profitInFlag) {
		this.profitInFlag = profitInFlag;
	}

	public Long getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(Long feeAmount) {
		this.feeAmount = feeAmount;
	}

	public Long getProfitAmount() {
		return profitAmount;
	}

	public void setProfitAmount(Long profitAmount) {
		this.profitAmount = profitAmount;
	}

	public Long getpFeeCode() {
		return pFeeCode;
	}

	public void setpFeeCode(Long pFeeCode) {
		this.pFeeCode = pFeeCode;
	}

	public Long getpFeeAmount() {
		return pFeeAmount;
	}

	public void setpFeeAmount(Long pFeeAmount) {
		this.pFeeAmount = pFeeAmount;
	}

	public Long getAcceptFeeCode() {
		return acceptFeeCode;
	}

	public void setAcceptFeeCode(Long acceptFeeCode) {
		this.acceptFeeCode = acceptFeeCode;
	}

	public Long getAcceptFeeAmount() {
		return acceptFeeAmount;
	}

	public void setAcceptFeeAmount(Long acceptFeeAmount) {
		this.acceptFeeAmount = acceptFeeAmount;
	}

	public String getAcceptFeeFlag() {
		return acceptFeeFlag;
	}

	public void setAcceptFeeFlag(String acceptFeeFlag) {
		this.acceptFeeFlag = acceptFeeFlag;
	}

	public Long getTransferFeeCode() {
		return transferFeeCode;
	}

	public void setTransferFeeCode(Long transferFeeCode) {
		this.transferFeeCode = transferFeeCode;
	}

	public Long getTransferFeeAmount() {
		return transferFeeAmount;
	}

	public void setTransferFeeAmount(Long transferFeeAmount) {
		this.transferFeeAmount = transferFeeAmount;
	}

	public String getTransferFeeFlag() {
		return transferFeeFlag;
	}

	public void setTransferFeeFlag(String transferFeeFlag) {
		this.transferFeeFlag = transferFeeFlag;
	}

	public Long getPayFeeCode() {
		return payFeeCode;
	}

	public void setPayFeeCode(Long payFeeCode) {
		this.payFeeCode = payFeeCode;
	}

	public Long getPayFeeAmount() {
		return payFeeAmount;
	}

	public void setPayFeeAmount(Long payFeeAmount) {
		this.payFeeAmount = payFeeAmount;
	}

	public String getPayFeeFlag() {
		return payFeeFlag;
	}

	public void setPayFeeFlag(String payFeeFlag) {
		this.payFeeFlag = payFeeFlag;
	}

	public Long getSupplyFeeCode() {
		return supplyFeeCode;
	}

	public void setSupplyFeeCode(Long supplyFeeCode) {
		this.supplyFeeCode = supplyFeeCode;
	}

	public Long getSupplyFeeAmount() {
		return supplyFeeAmount;
	}

	public void setSupplyFeeAmount(Long supplyFeeAmount) {
		this.supplyFeeAmount = supplyFeeAmount;
	}

	public String getSupplyFeeFlag() {
		return supplyFeeFlag;
	}

	public void setSupplyFeeFlag(String supplyFeeFlag) {
		this.supplyFeeFlag = supplyFeeFlag;
	}

	public String getCardSerialNo() {
		return cardSerialNo;
	}

	public void setCardSerialNo(String cardSerialNo) {
		this.cardSerialNo = cardSerialNo;
	}

	public Long getCardCount() {
		return cardCount;
	}

	public void setCardCount(Long cardCount) {
		this.cardCount = cardCount;
	}

	public String getTransTac() {
		return transTac;
	}

	public void setTransTac(String transTac) {
		this.transTac = transTac;
	}

	public String getTacCheckLabel() {
		return tacCheckLabel;
	}

	public void setTacCheckLabel(String tacCheckLabel) {
		this.tacCheckLabel = tacCheckLabel;
	}

	public Long getCardDptAmount() {
		return cardDptAmount;
	}

	public void setCardDptAmount(Long cardDptAmount) {
		this.cardDptAmount = cardDptAmount;
	}

	public String getCardExpDate() {
		return cardExpDate;
	}

	public void setCardExpDate(String cardExpDate) {
		this.cardExpDate = cardExpDate;
	}

	public String getBatchFileId() {
		return batchFileId;
	}

	public void setBatchFileId(String batchFileId) {
		this.batchFileId = batchFileId;
	}

	public String getUploadFlag() {
		return uploadFlag;
	}

	public void setUploadFlag(String uploadFlag) {
		this.uploadFlag = uploadFlag;
	}

	public String getUploadPkgNo() {
		return uploadPkgNo;
	}

	public void setUploadPkgNo(String uploadPkgNo) {
		this.uploadPkgNo = uploadPkgNo;
	}

	public String getInnerCardNo() {
		return innerCardNo;
	}

	public void setInnerCardNo(String innerCardNo) {
		this.innerCardNo = innerCardNo;
	}

	public String getOuterCardNo() {
		return outerCardNo;
	}

	public void setOuterCardNo(String outerCardNo) {
		this.outerCardNo = outerCardNo;
	}

	public String getCardMediaType() {
		return cardMediaType;
	}

	public void setCardMediaType(String cardMediaType) {
		this.cardMediaType = cardMediaType;
	}

	public String getCardBrandType() {
		return cardBrandType;
	}

	public void setCardBrandType(String cardBrandType) {
		this.cardBrandType = cardBrandType;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	public String getTerminalSerialNo() {
		return terminalSerialNo;
	}

	public void setTerminalSerialNo(String terminalSerialNo) {
		this.terminalSerialNo = terminalSerialNo;
	}

	public String getTerminalCommSerialNo() {
		return terminalCommSerialNo;
	}

	public void setTerminalCommSerialNo(String terminalCommSerialNo) {
		this.terminalCommSerialNo = terminalCommSerialNo;
	}

	public String getTerminalOperNo() {
		return terminalOperNo;
	}

	public void setTerminalOperNo(String terminalOperNo) {
		this.terminalOperNo = terminalOperNo;
	}

	public Long getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}

	public String getTransSerialType() {
		return transSerialType;
	}

	public void setTransSerialType(String transSerialType) {
		this.transSerialType = transSerialType;
	}

	public String getTransAcceptMatchFlag() {
		return transAcceptMatchFlag;
	}

	public void setTransAcceptMatchFlag(String transAcceptMatchFlag) {
		this.transAcceptMatchFlag = transAcceptMatchFlag;
	}

	public String getTransTransferMatchFlag() {
		return transTransferMatchFlag;
	}

	public void setTransTransferMatchFlag(String transTransferMatchFlag) {
		this.transTransferMatchFlag = transTransferMatchFlag;
	}

	public String getTransPayMatchFlag() {
		return transPayMatchFlag;
	}

	public void setTransPayMatchFlag(String transPayMatchFlag) {
		this.transPayMatchFlag = transPayMatchFlag;
	}

	public String getTransSupplyMatchFlag() {
		return transSupplyMatchFlag;
	}

	public void setTransSupplyMatchFlag(String transSupplyMatchFlag) {
		this.transSupplyMatchFlag = transSupplyMatchFlag;
	}

	public String getPaymentObjNo() {
		return paymentObjNo;
	}

	public void setPaymentObjNo(String paymentObjNo) {
		this.paymentObjNo = paymentObjNo;
	}

	public String getPaymentObjType() {
		return paymentObjType;
	}

	public void setPaymentObjType(String paymentObjType) {
		this.paymentObjType = paymentObjType;
	}

	public String getlAcceptOrgSerialNo() {
		return lAcceptOrgSerialNo;
	}

	public void setlAcceptOrgSerialNo(String lAcceptOrgSerialNo) {
		this.lAcceptOrgSerialNo = lAcceptOrgSerialNo;
	}

	public String getlAcceptOrgTransDate() {
		return lAcceptOrgTransDate;
	}

	public void setlAcceptOrgTransDate(String lAcceptOrgTransDate) {
		this.lAcceptOrgTransDate = lAcceptOrgTransDate;
	}

	public String getlAcceptOrgTransTime() {
		return lAcceptOrgTransTime;
	}

	public void setlAcceptOrgTransTime(String lAcceptOrgTransTime) {
		this.lAcceptOrgTransTime = lAcceptOrgTransTime;
	}

	public String getlTransferOrgSerialNo() {
		return lTransferOrgSerialNo;
	}

	public void setlTransferOrgSerialNo(String lTransferOrgSerialNo) {
		this.lTransferOrgSerialNo = lTransferOrgSerialNo;
	}

	public String getlTransferOrgTransDate() {
		return lTransferOrgTransDate;
	}

	public void setlTransferOrgTransDate(String lTransferOrgTransDate) {
		this.lTransferOrgTransDate = lTransferOrgTransDate;
	}

	public String getlTransferOrgTransTime() {
		return lTransferOrgTransTime;
	}

	public void setlTransferOrgTransTime(String lTransferOrgTransTime) {
		this.lTransferOrgTransTime = lTransferOrgTransTime;
	}

	public String getlPayTransSerialNo() {
		return lPayTransSerialNo;
	}

	public void setlPayTransSerialNo(String lPayTransSerialNo) {
		this.lPayTransSerialNo = lPayTransSerialNo;
	}

	public String getlPayOrgTransDate() {
		return lPayOrgTransDate;
	}

	public void setlPayOrgTransDate(String lPayOrgTransDate) {
		this.lPayOrgTransDate = lPayOrgTransDate;
	}

	public String getlPayOrgTransTime() {
		return lPayOrgTransTime;
	}

	public void setlPayOrgTransTime(String lPayOrgTransTime) {
		this.lPayOrgTransTime = lPayOrgTransTime;
	}

	public String getlSupplyOrgSerialNo() {
		return lSupplyOrgSerialNo;
	}

	public void setlSupplyOrgSerialNo(String lSupplyOrgSerialNo) {
		this.lSupplyOrgSerialNo = lSupplyOrgSerialNo;
	}

	public String getlSupplyOrgTransDate() {
		return lSupplyOrgTransDate;
	}

	public void setlSupplyOrgTransDate(String lSupplyOrgTransDate) {
		this.lSupplyOrgTransDate = lSupplyOrgTransDate;
	}

	public String getlSupplyOrgTransTime() {
		return lSupplyOrgTransTime;
	}

	public void setlSupplyOrgTransTime(String lSupplyOrgTransTime) {
		this.lSupplyOrgTransTime = lSupplyOrgTransTime;
	}

	public String getPsamCardNo() {
		return psamCardNo;
	}

	public void setPsamCardNo(String psamCardNo) {
		this.psamCardNo = psamCardNo;
	}

	public String getPreAuthSerialNo() {
		return preAuthSerialNo;
	}

	public void setPreAuthSerialNo(String preAuthSerialNo) {
		this.preAuthSerialNo = preAuthSerialNo;
	}

	public String getAcceptProcFlag() {
		return acceptProcFlag;
	}

	public void setAcceptProcFlag(String acceptProcFlag) {
		this.acceptProcFlag = acceptProcFlag;
	}

	public String getTransferProcFlag() {
		return transferProcFlag;
	}

	public void setTransferProcFlag(String transferProcFlag) {
		this.transferProcFlag = transferProcFlag;
	}

	public String getPayProcFlag() {
		return payProcFlag;
	}

	public void setPayProcFlag(String payProcFlag) {
		this.payProcFlag = payProcFlag;
	}

	public String getSupplyProcFlag() {
		return supplyProcFlag;
	}

	public void setSupplyProcFlag(String supplyProcFlag) {
		this.supplyProcFlag = supplyProcFlag;
	}

	public String getAcceptRespCode() {
		return acceptRespCode;
	}

	public void setAcceptRespCode(String acceptRespCode) {
		this.acceptRespCode = acceptRespCode;
	}

	public String getTransferRespCode() {
		return transferRespCode;
	}

	public void setTransferRespCode(String transferRespCode) {
		this.transferRespCode = transferRespCode;
	}

	public String getPayRespCode() {
		return payRespCode;
	}

	public void setPayRespCode(String payRespCode) {
		this.payRespCode = payRespCode;
	}

	public String getSupplyCode() {
		return supplyCode;
	}

	public void setSupplyCode(String supplyCode) {
		this.supplyCode = supplyCode;
	}

	public Date getTransFinTs() {
		return transFinTs;
	}

	public void setTransFinTs(Date transFinTs) {
		this.transFinTs = transFinTs;
	}

	public Date getTransToTs() {
		return transToTs;
	}

	public void setTransToTs(Date transToTs) {
		this.transToTs = transToTs;
	}

	public String getIsClearing() {
		return isClearing;
	}

	public void setIsClearing(String isClearing) {
		this.isClearing = isClearing;
	}

	public String getClearingId() {
		return clearingId;
	}

	public void setClearingId(String clearingId) {
		this.clearingId = clearingId;
	}

	public Long getResendNum() {
		return resendNum;
	}

	public void setResendNum(Long resendNum) {
		this.resendNum = resendNum;
	}

	public String getSettlementMonth() {
		return settlementMonth;
	}

	public void setSettlementMonth(String settlementMonth) {
		this.settlementMonth = settlementMonth;
	}

	public String getSettlementDay() {
		return settlementDay;
	}

	public void setSettlementDay(String settlementDay) {
		this.settlementDay = settlementDay;
	}

	public String getRevsalFlag() {
		return revsalFlag;
	}

	public void setRevsalFlag(String revsalFlag) {
		this.revsalFlag = revsalFlag;
	}

	public String getRevsalTxnSerialNo() {
		return revsalTxnSerialNo;
	}

	public void setRevsalTxnSerialNo(String revsalTxnSerialNo) {
		this.revsalTxnSerialNo = revsalTxnSerialNo;
	}

	public String getCancelFlag() {
		return cancelFlag;
	}

	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	public String getCancelTxnSerialNo() {
		return cancelTxnSerialNo;
	}

	public void setCancelTxnSerialNo(String cancelTxnSerialNo) {
		this.cancelTxnSerialNo = cancelTxnSerialNo;
	}

	public String getlTransSerialNo() {
		return lTransSerialNo;
	}

	public void setlTransSerialNo(String lTransSerialNo) {
		this.lTransSerialNo = lTransSerialNo;
	}

	public String getReturnFlag() {
		return returnFlag;
	}

	public void setReturnFlag(String returnFlag) {
		this.returnFlag = returnFlag;
	}

	public Long getReturnCount() {
		return returnCount;
	}

	public void setReturnCount(Long returnCount) {
		this.returnCount = returnCount;
	}

	public Long getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(Long returnAmount) {
		this.returnAmount = returnAmount;
	}

	public String getDubiousFlag() {
		return dubiousFlag;
	}

	public void setDubiousFlag(String dubiousFlag) {
		this.dubiousFlag = dubiousFlag;
	}

	public String getBatchDate() {
		return batchDate;
	}

	public void setBatchDate(String batchDate) {
		this.batchDate = batchDate;
	}

	public String getBatchFlag() {
		return batchFlag;
	}

	public void setBatchFlag(String batchFlag) {
		this.batchFlag = batchFlag;
	}

	public Long getAcceptSettleAmount() {
		return acceptSettleAmount;
	}

	public void setAcceptSettleAmount(Long acceptSettleAmount) {
		this.acceptSettleAmount = acceptSettleAmount;
	}

	public Long getTransferSettleAmount() {
		return transferSettleAmount;
	}

	public void setTransferSettleAmount(Long transferSettleAmount) {
		this.transferSettleAmount = transferSettleAmount;
	}

	public Long getPaySettleAmount() {
		return paySettleAmount;
	}

	public void setPaySettleAmount(Long paySettleAmount) {
		this.paySettleAmount = paySettleAmount;
	}

	public Long getSupplySettleAmount() {
		return supplySettleAmount;
	}

	public void setSupplySettleAmount(Long supplySettleAmount) {
		this.supplySettleAmount = supplySettleAmount;
	}

	public String getTransferInOrgCode() {
		return transferInOrgCode;
	}

	public void setTransferInOrgCode(String transferInOrgCode) {
		this.transferInOrgCode = transferInOrgCode;
	}

	public String getInAccountNo() {
		return inAccountNo;
	}

	public void setInAccountNo(String inAccountNo) {
		this.inAccountNo = inAccountNo;
	}

	public String getTransferOutOrgCode() {
		return transferOutOrgCode;
	}

	public void setTransferOutOrgCode(String transferOutOrgCode) {
		this.transferOutOrgCode = transferOutOrgCode;
	}

	public String getOutAccountNo() {
		return outAccountNo;
	}

	public void setOutAccountNo(String outAccountNo) {
		this.outAccountNo = outAccountNo;
	}

	public String getResvFld1() {
		return resvFld1;
	}

	public void setResvFld1(String resvFld1) {
		this.resvFld1 = resvFld1;
	}

	public String getResvFld2() {
		return resvFld2;
	}

	public void setResvFld2(String resvFld2) {
		this.resvFld2 = resvFld2;
	}

	public String getResvFld3() {
		return resvFld3;
	}

	public void setResvFld3(String resvFld3) {
		this.resvFld3 = resvFld3;
	}

	public String getResvFld4() {
		return resvFld4;
	}

	public void setResvFld4(String resvFld4) {
		this.resvFld4 = resvFld4;
	}

	public String getResvFld5() {
		return resvFld5;
	}

	public void setResvFld5(String resvFld5) {
		this.resvFld5 = resvFld5;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getCustomerAreaCode() {
		return customerAreaCode;
	}

	public void setCustomerAreaCode(String customerAreaCode) {
		this.customerAreaCode = customerAreaCode;
	}

	public String getCustomerCityCode() {
		return customerCityCode;
	}

	public void setCustomerCityCode(String customerCityCode) {
		this.customerCityCode = customerCityCode;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

   
    
}