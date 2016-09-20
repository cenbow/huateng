package cn.com.huateng.web.controller.api;

import cn.com.huateng.CommonUser;
import cn.com.huateng.account.service.PasswordMgmService;
import cn.com.huateng.account.service.UserService;
import cn.com.huateng.common.BussErrorCode;
import com.aixforce.annotations.ParamInfo;
import com.aixforce.user.base.UserUtil;
import com.google.common.collect.ImmutableMap;
import com.huateng.p3.account.persistence.models.TInfoBankcard;
import com.huateng.p3.component.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lzw
 * Date: 14-12-3
 * Time: 下午1:58
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/api/bankCard")
public class BankCard {
    private final static Logger log = LoggerFactory.getLogger(BankCard.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordMgmService passwordMgmService;



    @RequestMapping(value = "/bindBankCard", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> bindBankCard( @ParamInfo("cardNo") String cardNo, @ParamInfo("name") String name, @ParamInfo("idNo") String idNo,@ParamInfo("mobileNo") String mobileNo,@ParamInfo("bankCode") String bankCode,@ParamInfo("cardType") String cardType) throws Exception {
        CommonUser commonUser= UserUtil.getCurrentUser();
        Map<String, String> setResult ;
        try {
            Response<String> response = userService.bindBankCard(commonUser,cardNo,name,idNo,mobileNo,bankCode,cardType);
            if (response.isSuccess()) {

                setResult= ImmutableMap.of("status", "success", "msg", "");
                return setResult;
            } else {
                log.error("failed to bindBankCard", BussErrorCode.explain(response.getErrorCode()));
                setResult= ImmutableMap.of("status", "fail", "msg", BussErrorCode.explain(response.getErrorCode()));
                return setResult;
            }
        } catch (Exception e) {
            log.error("bindBankCard failed, errorCode={}, unifyId={}",e,commonUser.getUnifyId());
            setResult= ImmutableMap.of("status", "fail", "msg", "绑定银行卡失败");
            return setResult;
        }
    }



    @RequestMapping(value = "/unBindBankCard", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> unBindBankCard(@ParamInfo("bankCardNo") String bankCardNo,@ParamInfo("payPwd") String payPwd) throws Exception {
        CommonUser commonUser= UserUtil.getCurrentUser();
        Map<String, String> setResult ;
        try {
            //验证支付密码
            Response<String> res= passwordMgmService.checkTxnPasswd(commonUser,payPwd);
            if(!res.isSuccess()){
                setResult= ImmutableMap.of("status", "fail", "msg",BussErrorCode.explain(res.getErrorCode()));
                return setResult;
            }
            //解绑
            Response<String> response = userService.unBindBankCard(commonUser,bankCardNo);
            if (response.isSuccess()) {

                setResult= ImmutableMap.of("status", "success", "msg", "");
                return setResult;
            } else {
                log.error("failed to unBindBankCard", BussErrorCode.explain(response.getErrorCode()));
                setResult= ImmutableMap.of("status", "fail", "msg", BussErrorCode.explain(response.getErrorCode()));
                return setResult;
            }
        } catch (Exception e) {
            log.error("unBindBankCard failed, errorCode={}, unifyId={}",e,commonUser.getUnifyId());
            setResult= ImmutableMap.of("status", "fail", "msg", "绑定银行卡失败");
            return setResult;
        }
    }
}
