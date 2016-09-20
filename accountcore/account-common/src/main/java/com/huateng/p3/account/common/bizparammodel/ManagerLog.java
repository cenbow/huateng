package com.huateng.p3.account.common.bizparammodel;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.google.common.base.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: JamesTang
 * Date: 13-12-4
 * Time: 下午4:16
 * To change this template use File | Settings | File Templates.
 */

public class ManagerLog implements Serializable {

	private static final long serialVersionUID = 6970549730272080198L;
	@Getter
    @Setter
    private String feeFlag = "1"; //默认不收费
    @Getter
    @Setter
    private long feeAmt = 0;//费用为零
    
    /**
     * 商户号，只在需要验证支付密码时才有效
     */
    @Getter
    @Setter
    private String merchantCode;
    
    /**
     * 终端号，只在需要验证支付密码时才有效
     */
    @Getter
    @Setter
    private String terminalNo;
    
    /**
     * 受理机构管理类必填
     */
    @Getter
    @Setter
    private String acceptOrgCode;
    /**
     * 受理流水管理类 账户操作类必填
     */
    @Getter
    @Setter
    private String acceptTxnSeqNo;
    /**
     * 受理渠道 必填
     */
    @Getter
    @Setter
    private String txnChannel;
    /*
     * 创建人
     */
    @Getter
    @Setter
    private String inputUid;
    /**
     * 创建时间 外部不填写
     */
    @Getter
    @Setter
    private Date inputTime;
    /**
     * 审核人
     */
    @Getter
    @Setter
    private String checkUid;
    /**
     * 审核时间 外部不填写
     */
    @Getter
    @Setter
    private Date checkTime;
    
    /**
     * 外部交易类型，区分短信模板使用
     */
    @Getter
    @Setter
    private String bussinessType;
    
    /**
     * 内部交易类型   （外部无需填写 ，填了也白填）
     */
    @Getter
    @Setter
    private String innerTxnType;
    
    /**
     * 交易摘要
     */
    @Getter
    @Setter
    private String txnDscpt;
    
    /**
     * 客户照片，实名认证用
     * */
    @Getter
    @Setter
    private String photo;
    
    /**
     * 国籍，实名认证用
     * */
    @Getter
    @Setter
    private String nationality;
    
    /**
	 * 性别，实名认证用
	 */
    @Getter
    @Setter
    private String gender;
    
    /**
     * 职业，实名认证用
     * */
    @Getter
    @Setter
    private String profession;
    
    /**
     * 证件有效期 ，实名认证用
     * */
    @Getter
    @Setter
    private String certExpiryDate;
    
    /**
     * 客户地址，实名认证用
     * */
    @Getter
    @Setter
    private String address;
    
    /**
     * 证件类型，实名认证，锁定账户检测证件用
     * */
    @Getter
    @Setter
    private String idtype;
    /**
     * 证件号码，实名认证，锁定账户检测证件，查询黑名单用
     * */
    @Getter
    @Setter
    private String idno;
    /**
     * 客户名称，锁定账户检测证件用
     * */
    @Getter
    @Setter
    private String name;
    /**
     * 银行卡号  查询黑名单用
     */
    @Getter
    @Setter
    private String bankCardNo;
    /**
     * 账户锁定解锁操作前状态 (不需要外部传入)
     * */
    @Getter
    @Setter
    private String beforeStatus;
    /**
     * 受理日期  外部填写
     */
    @Getter
    @Setter
    private String acceptDate;

    /**
     * 受理时间 外部填写
     */
    @Getter
    @Setter
    private String acceptTime;

    
    /**
     * 新登录密码   MD5加密
     */
    @Getter
    @Setter
    private String newLoginPassword;
    
    /**
     * 原登录密码   MD5加密
     */
    @Getter
    @Setter
    private String oldLoginPassword;
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("feeFlag", feeFlag)
                .add("feeAmt", feeAmt)
                .add("merchantCode", merchantCode)
				.add("terminalNo", terminalNo)
				.add("acceptOrgCode", acceptOrgCode)
				.add("acceptTxnSeqNo", acceptTxnSeqNo)
				.add("txnChannel", txnChannel)
				.add("inputUid", inputUid)
				.add("inputTime", inputTime)
				.add("checkUid", checkUid)
				.add("checkTime", checkTime)
				.add("bussinessType", bussinessType)
				.add("innerTxnType", innerTxnType)
				.add("txnDscpt", txnDscpt)
				.add("nationality", nationality)
				.add("gender", gender)
				.add("profession", profession)
				.add("certExpiryDate", certExpiryDate)
				.add("address", address)
				.add("idtype", idtype)
				.add("name", name)
				.add("photo", photo)
				.add("beforeStatus", beforeStatus)
				.add("acceptDate", acceptDate)
				.add("acceptTime", acceptTime)
				.add("newLoginPassword", newLoginPassword)
				.add("oldLoginPassword", oldLoginPassword)
                .omitNullValues()
                .toString();
    }
}
