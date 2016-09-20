package cn.com.huateng.web.controller.api;

/**
 * User: 董培基
 * Date: 14-4-18
 * Time: 下午1:31
 */

import cn.com.huateng.CommonUser;

import cn.com.huateng.account.model.TInfoAccount;
import cn.com.huateng.account.service.UserService;


import com.aixforce.exception.JsonResponseException;
import com.aixforce.user.base.UserUtil;
import com.huateng.p3.component.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;

@Controller
@RequestMapping("/api/account")
public class Account {

    private static final Logger logger = LoggerFactory.getLogger(Account.class);

    @Autowired
    private UserService userService;

    /**
     * 获取账户余额
     */
    @RequestMapping(value = "/balance", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getBalance(){
        CommonUser user = UserUtil.getCurrentUser();
        Response<TInfoAccount> result = userService.loadAccounts(user);
        if (result.isSuccess()){
            TInfoAccount tInfoAccount = result.getResult();
            //格式化余额
            Double balance = tInfoAccount.getBalance()/100.0;
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(balance);
        }else {
            logger.error("failed to get account balance, errorCode={}, unifyId={}",
                    result.getErrorCode(),user.getUnifyId());
            throw new JsonResponseException(500,result.getErrorMsg());
        }
    }
}
