package cn.com.huateng.account.service;

import com.aixforce.annotations.ParamInfo;

/**
 * User: 董培基
 * Date: 13-7-30
 * Time: 下午7:07
 */
public interface SeqGeneratorService {

    /**
     * 序列 查询
     * @param seqName 序列 名称
     * @return    序列号
     */
    Long generateOrderNo(@ParamInfo("seqName") String seqName);

    /**
     * 转账订单流水
     * @param seqName  序列名称
     * @return
     */
    String TransferOrderSeq(String seqName);
}
