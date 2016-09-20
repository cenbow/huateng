package cn.com.huateng.payment.service;

import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.component.Response;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created with IntelliJ IDEA.
 * User: lzw
 * Date: 14-10-29
 * Time: 下午1:03
 * To change this template use File | Settings | File Templates.
 */
public interface AccountTransferService {

    /**
     * 帐户间互转
     * 从 accountKey 转到 targetAccountKey 中
     * @param unifyId   统一账号
     * @param amount    余额
     * @param transAmount   转账金额
     * @param transPhone      收款人手机号
     * @param field  备注
     * @param pwd   支付密码
     * @return
     */
    Response<TxnResultObject> transfer(@RequestParam("unifyId")String unifyId,@RequestParam("amount")String amount,@RequestParam("transAmount")String transAmount,@RequestParam("transPhone")String transPhone,@RequestParam("field")String field,@RequestParam("pwd")String pwd );

    /**
     * 帐户间互转检查
     * 从 accountKey 转到 targetAccountKey 中
     * @param unifyId   统一账号
     * @param amount    余额
     * @param transAmount   转账金额
     * @param transPhone      收款人手机号
     * @param field  备注
     * @return 余额
     */
    Response<TxnResultObject> transferCheck(@RequestParam("unifyId")String unifyId,@RequestParam("amount") String amount,@RequestParam("transAmount")String transAmount,@RequestParam("transPhone")String transPhone,@RequestParam("field")String field);
}
