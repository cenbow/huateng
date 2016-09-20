package com.huateng.p3.account.common.bizparammodel;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.huateng.p3.account.common.enummodel.TrueOrFalse;

/**
 * User: huwenjie
 * Date: 14-1-21
 * Time: 下午6:10
 */
@ToString(exclude={"question","answer","idNo"})
public class CustomerResultObject implements Serializable {

	private static final long serialVersionUID = 8767368587870212635L;

	/**
     * 客户号
     */
    @Getter
    @Setter
    private String customerNo;

    /**
     * 统一帐号
     */
    @Getter
    @Setter
    private String unifyId;
    
    /**
     * 账户号
     */
    @Getter
    @Setter
    private String accountNo;


    /**
     * 姓名
     */
    @Getter
    @Setter
    private String name;

    /**
     * 证件类型
     */
    @Getter
    @Setter
    private String idType;

    
    /**
     * 证件号
     */
    @Getter
    @Setter
    private String idNo;
    
    /**
     * 密保问题
     */
    @Getter
    @Setter
    private String question;
    
    /**
     * 密保答案
     */
    @Getter
    @Setter
    private String answer;
    
    /**
     * 实名等级
     */
    @Getter
    @Setter
    private String isRealname;
    
    /**
     * 客户等级
     */
    @Getter
    @Setter
    private String grade;

    /**
     * 账户余额
     */
    @Getter
    @Setter
    private Long balance;
    
    /**
     * 账户可用余额
     */
    @Getter
    @Setter
    private Long availableBalance;

    /**
     * 积分余额
     */
    @Getter
    @Setter
    private Long Integral;
    /**
     * 账户状态
     */
    @Getter
    @Setter
    private String status;

    /**
     * 是否快捷绑卡（老翰银的绑卡查询）
     */
    @Getter
    @Setter
    private TrueOrFalse isCardHanding;
    
    /**
     * 是否快捷绑卡（新快捷的绑卡查询）
     */
    @Getter
    @Setter
    private TrueOrFalse isShortcutCard;
    
    /**
     * 是否修改过密码  0没有修改  1修改过
     */
    @Getter
    @Setter
    private String initPasswdModified;
    
    
    /**
     * 注册类型
     */
    @Getter
    @Setter
    private String registerType;
    
    /**
     * 注册时间
     */
    @Getter
    @Setter
    private Date registerTime;
    
    /**
     * 类型
     */
    @Getter
    @Setter
    private String type;
    
    /**
     * 地区编码
     */
    @Getter
    @Setter
    private String areaCode;
    
    /**
     * 城市编码
     */
    @Getter
    @Setter
    private String cityCode;
    
    /**
     * 联系手机
     */
    @Getter
    @Setter
    private String mobilePhone;
    
    /**
     * 联系地址
     */
    @Getter
    @Setter
    private String contactAddress;
    
    /**
     * 最后一次登录时间
     */
    @Getter
    @Setter
    private Date lastSuccessLoginTime;
    
    /**
     * 登录密码错误输入次数
     */
    @Getter
    @Setter
    private Long pwdErrCount;
    
    /**
     * 家庭电话
     */
    @Getter
    @Setter
    private String homeTelephone;
    
    /**
     * 办公电话
     */
    @Getter
    @Setter
    private String officeTelephone;
    
    /**
     * 其他联系人电话
     */
    @Getter
    @Setter
    private String otherTelephone;
    
    /**
     * 冻结金额
     */
    @Getter
    @Setter
    private Long frozenAmount;
    
    /**
     * 性别
     */
    @Getter
    @Setter
    private String gender;
}
