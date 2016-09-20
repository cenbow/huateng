package cn.com.huateng.web.controller.api;

import cn.com.huateng.CommonUser;
import cn.com.huateng.account.model.TInfoCustomer;
import cn.com.huateng.account.service.PasswordMgmService;
import cn.com.huateng.account.service.UserService;
import cn.com.huateng.common.BussErrorCode;
import cn.com.huateng.web.controller.Message;
import com.aixforce.user.base.UserUtil;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.common.hash.Hashing;
import com.huateng.p3.component.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-15
 * Time: 下午7:08
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping("/api/passWordMgn")
public class PassWordMgn {
    private static final Logger logger = LoggerFactory.getLogger(PassWordMgn.class);

    @Autowired
    private PasswordMgmService passwordMgmService;

    @Autowired
    private UserService userService;

    @Autowired
    private Message message;

    @RequestMapping(value = "/modify", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> modfiyTxnPasswd(@RequestParam("unifyId") String unifyId,
                                                @RequestParam("oldLoginPasswd") String oldLoginPasswd,
                                               @RequestParam("newLoginPasswd") String newLoginPasswd


                                              ) {


        Map<String, String> modifyResult;
        CommonUser commonUser = new CommonUser();
        commonUser.setUnifyId(unifyId);
        Response<String> res = passwordMgmService.modfiyTxnPasswd(commonUser, newLoginPasswd, oldLoginPasswd);
        if("000000".equals(res.getResult())){
            modifyResult = ImmutableMap.of("status", "ok");
        }else{
            modifyResult = ImmutableMap.of("status", "fail","msg",res.getErrorMsg());
        }

        return modifyResult;
    }

    /**
     * ajax验证短信验证码
     * @param verifyCode  短信验证码
     */
    @RequestMapping(value = "/matchCode", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> matchVerifyCode(@RequestParam("verifyCode") String verifyCode){
        CommonUser user= UserUtil.getCurrentUser();
        Map<String,String> setResult;
        String returnStr=checkVerifyCode(user,verifyCode);
        if(!returnStr.equals("suc")){
            setResult=ImmutableMap.of("status","fail","msg",returnStr);
            return setResult;
        }
        setResult=ImmutableMap.of("status","suc","msg","");
        return setResult;
    }

    /**
     * ajax验证短信验证码 (手机绑定)
     * @param verifyCode  短信验证码
     */
    @RequestMapping(value = "/matchCodeOutCheckMobileNo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> matchVerifyCodeOutCheckMobileNo(@RequestParam("verifyCode") String verifyCode,@RequestParam("mobileNo") String mobileNo){
        CommonUser user= UserUtil.getCurrentUser();
        user.setMobileNo(mobileNo);
        Map<String,String> setResult;
        String returnStr=checkVerifyCode(user,verifyCode,true);
        if(!returnStr.equals("suc")){
            setResult=ImmutableMap.of("status","fail","msg",returnStr);
            return setResult;
        }
        setResult=ImmutableMap.of("status","suc","msg","");
        return setResult;
    }

    private String checkVerifyCode(CommonUser user,String verifyCode,boolean... isMobileNoCheck){
        Response<TInfoCustomer> infoCustomer=userService.loadCustomer(user);
        String mobile;
        if(infoCustomer.isSuccess()){
            mobile=infoCustomer.getResult().getMobileNo();
        }else{
            return BussErrorCode.explain(infoCustomer.getErrorCode());
        }
        String returnStr;
        if(isMobileNoCheck.length > 0 && isMobileNoCheck[0]){
            mobile =user.getMobileNo();
        returnStr=message.checkMessage(mobile,verifyCode,true);
        } else{
        returnStr=message.checkMessage(mobile,verifyCode);
        }
        if(!"0".equals(returnStr)){
            return returnStr;
        }
        return "suc";
    }

    /**
     * ajax验证短信验证码

     */
    @RequestMapping(value = "/matchCode_other", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> matchVerifyCode(@RequestParam("mobileNo") String mobileNo,@RequestParam("verifyCode") String verifyCode,@RequestParam("unifyId") String unifyId){
        Map<String,String> setResult;
        String returnStr=checkVerifyCode(unifyId,verifyCode,mobileNo);
        if(!returnStr.equals("suc")){
            setResult=ImmutableMap.of("status","fail","msg",returnStr);
            return setResult;
        }
        setResult=ImmutableMap.of("status","suc","msg","");
        return setResult;
    }

    private String checkVerifyCode(String unifyId,String verifyCode,String mobileNo){

        String returnStr=message.checkMessage(mobileNo,verifyCode);
        if(!"0".equals(returnStr)){
            return returnStr;
        }
        return "suc";
    }


    @RequestMapping(value = "/re_loginPwd", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> resetLoginPasswd(@RequestParam("unifyId") String unifyId,
                                                 @RequestParam("newPasswd") String newPasswd) {


        Map<String, String>Result;
        CommonUser commonUser = new CommonUser();
        commonUser.setUnifyId(unifyId);
        Response<String> res =userService.findPassWord(commonUser,newPasswd);
            if("000000".equals(res.getResult())){
            Result = ImmutableMap.of("status", "ok");
        }else{
           Result = ImmutableMap.of("status", "fail","msg",BussErrorCode.explain(res.getErrorCode()));
        }
        return Result;
    }




}
