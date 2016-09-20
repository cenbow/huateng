package com.huateng.p3.account.common.bizparammodel;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import com.google.common.base.Objects;
import com.huateng.p3.account.common.enummodel.KeyType;

/**
 * 账户变动相关的入参
 * <p/>
 * Created by IntelliJ IDEA.
 * User: AnsonChan
 * Date: 13-12-3
 */

public class AccountInfo implements Serializable {

    private static final long serialVersionUID = -8014568563319610244L;
    /**
     * 账户key，和keyType组合确定查询方式来找到一个account
     */
    @Getter
    @Setter
    private String accountKey;
    /**
     * accountKey的type
     */
    @Getter
    @Setter
    private KeyType keyType = KeyType.UNIFY;

    /**
     * 转账和圈存使用，与targetKeyType组合
     */
    @Getter
    @Setter
    private String targetAccountKey;
    /**
     * 转账和圈存使用
     */
    @Getter
    @Setter
    private KeyType targetKeyType = KeyType.UNIFY;

    /**
     * 密码
     */
    @Getter
    @Setter
    private String txnPassword;
    
    /**
     * 新密码(修改密码时使用)
     */
    @Getter
    @Setter
    private String newTxnPassword;
    
    /**
     * 卡bin信息，加密密码用，不传默认用accountKey
     */
    @Getter
    @Setter
    private String cardBin;
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("accountKey", accountKey)
                .add("keyType", keyType)
                .add("targetAccountKey", targetAccountKey)
				.add("targetKeyType", targetKeyType)
				.add("txnPassword", txnPassword)
				.add("newTxnPassword", newTxnPassword)
				.add("cardBin", cardBin)
                .omitNullValues()
                .toString();
    }
}
