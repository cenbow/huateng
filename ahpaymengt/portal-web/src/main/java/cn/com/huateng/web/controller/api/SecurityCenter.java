package cn.com.huateng.web.controller.api;

import cn.com.huateng.CommonUser;
import cn.com.huateng.account.model.TInfoCustomer;
import cn.com.huateng.account.service.AccountTranMgmService;
import cn.com.huateng.account.service.PasswordMgmService;
import cn.com.huateng.account.service.SecuritySysDataService;
import cn.com.huateng.account.service.UserService;
import cn.com.huateng.common.BussErrorCode;
import cn.com.huateng.util.NumberUtil;
import cn.com.huateng.util.ValidationFieldUtil;
import cn.com.huateng.util.ocx.GetRandom;
import cn.com.huateng.web.controller.Message;
import com.aixforce.user.base.UserUtil;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.hash.Hashing;
import com.huateng.p3.account.persistence.models.TDictCode;
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

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/security")
public class SecurityCenter {

    public final static Logger log = LoggerFactory.getLogger(SecurityCenter.class);
    @Autowired
    private PasswordMgmService passwordMgmService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountTranMgmService accountTranMgmService;

    @Autowired
    private SecuritySysDataService securitySysDataService;

    @Autowired
    private Message message;

    /**
     * ajax判断是否符合修改或重置支付密码的条件
     * @param way 修改或重置支付密码的方式  0(修改密码)  1(密保问题重置)  2(短信验证码重置) 3(身份证重置 )
     */
    @RequestMapping(value = "/isModifyOrResetPwd",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,String> isModifyOrResetPwd(@RequestParam("way")String way){
        CommonUser user = UserUtil.getCurrentUser();
        Map<String,String> setResult;
        Response<String> response;
        try {

            response= passwordMgmService.resetPayPasswdDetect(user,way);
        }catch (Exception e){
            log.error("isModifyOrResetPwd failed, errorCode={}, productNo={}",e.getMessage(),user.getUnifyId());
            setResult= ImmutableMap.of("status", "fail", "msg", "连接服务异常,请稍后再试");
            return setResult;
        }
        if(response.isSuccess()){
            String result=response.getResult();
            //if("SUCCESS".equals(result)){
                setResult=ImmutableMap.of("status","success","msg",result);
                return setResult;
            //}
        }else{
            log.error("isModifyOrResetPwd failed, errorCode={}, productNo={}",response.getErrorCode
                    (),user.getUnifyId());
            if(response.getErrorCode().equals("700021")){
                setResult=ImmutableMap.of("status","fail","msg","您没有设置密保问题,请完善信息后再重试,或选择其它方式重置");
                return setResult;
            }else{
                setResult=ImmutableMap.of("status","fail","msg", BussErrorCode.explain(response.getErrorCode()));
                return setResult;
            }

        }
    }


    /**
     * 生成32位随机码
     * @param Key key
     */
    @RequestMapping(value = "/getRandomString", method = RequestMethod.GET)
    @ResponseBody
    public String getRandomPayment32String(@RequestParam("key") String Key, HttpServletRequest request) {
        //生成32位随机码
        String mcrypt_key = GetRandom.generateString(32);
        request.getSession().setAttribute("mcrypt_key" + Key, mcrypt_key);
        return mcrypt_key;
    }


    /**
     * 支付密码--验证新密码和确认密码是否相同
     * @param newPayPwd  新密码
     * @param newPayPwd2 确认密码
     * @param pwdKey 新密码key
     * @param pwd2Key 确认密码key
     */
    @RequestMapping(value = "/confirmPassword", method = RequestMethod.POST)
    @ResponseBody
    public String confirmPassword(@RequestParam("newPayPwd") String newPayPwd,
                                  @RequestParam("newPayPwd2") String newPayPwd2,
                                  @RequestParam("pwdKey") String pwdKey,
                                  @RequestParam("pwd2Key") String pwd2Key,
                                  HttpServletRequest request) {
        String keyNewPassword = newPayPwd == null ? "" : newPayPwd;

        String confirmNewPasswordi = newPayPwd2 == null ? "" : newPayPwd2;

        if(keyNewPassword.equals(confirmNewPasswordi)){
            return "success";
        }else{
            return "error";
        }
    }

    /**
     * 通过短信验证码重置支付密码
     * @param newPwd 新支付密码（加密）
     */
    @RequestMapping(value = "/resetPayPwdBySms",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> resetPayPwdBySms(@RequestParam("newPwd") String newPwd,
                                               @RequestParam("checkCode") String checkCode,
                                               HttpServletRequest request) {
        CommonUser user = UserUtil.getCurrentUser();

        Map<String,String> setResult;

        //后台校验图片验证码，防止页面验证被绕过
        boolean isRight=CaptchaGenerator.matches(request,checkCode);
        if(!isRight){
            log.error("modifyPayPassword failed, errorCode={}, productNo={}", "图片验证码错误",
                    user.getUnifyId());
            setResult=ImmutableMap.of("status","fail","msg","图片验证码错误");
            return setResult;

        }

        // 新支付密码
        String keyNewPassword = newPwd == null ? "" : newPwd;
        //解密
//        keyNewPassword = AESWithJCE.getResult((String) request.getSession()
//                .getAttribute("mcrypt_key" + "keyNewPassword"), keyNewPassword) == null ? ""
//                : AESWithJCE.getResult((String) request.getSession()
//                .getAttribute("mcrypt_key" + "keyNewPassword"), keyNewPassword);
//        //从session中清除
//        request.getSession().removeAttribute("mcrypt_key" + "keyPassword");
        //加密支付密码

       // keyNewPassword = Hashing.md5().hashString(keyNewPassword, Charsets.UTF_8).toString();
        Response<String> result = passwordMgmService.resetPayPasswdBySms(user, keyNewPassword);
        if(result.isSuccess()){
            setResult=ImmutableMap.of("status","success","msg","");
            return setResult;
        }else{
            log.error("resetPayPwdBySms failed, errorCode={}, productNo={}", result.getErrorCode(),
                    user.getUnifyId());
            setResult=ImmutableMap.of("status","fail","msg",result.getErrorMsg());
            return setResult;
        }
    }

    /**
     * 通过密保问题重置支付密码
     * @param question 密保问题
     * @param answer 密保答案
     * @param payPwd 新支付密码（加密）
     */
    @RequestMapping(value = "/resetPayPwdByQuestion",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> resetPayPwdByQuestion(@RequestParam("question") String question,
                                                    @RequestParam("answer") String answer,
                                                    @RequestParam("payPwd") String payPwd) {
        CommonUser user = UserUtil.getCurrentUser();

        Map<String,String> setResult;


        // 新支付密码
        String keyNewPassword = payPwd == null ? "" : payPwd;
        //解密
//        keyNewPassword = AESWithJCE.getResult((String) request.getSession()
//                .getAttribute("mcrypt_key" + "keyNewPassword"), keyNewPassword) == null ? ""
//                : AESWithJCE.getResult((String) request.getSession()
//                .getAttribute("mcrypt_key" + "keyNewPassword"), keyNewPassword);
//        //从session中清除
//        request.getSession().removeAttribute("mcrypt_key" + "keyPassword");
        //加密支付密码
        //PinkeyEncrypt bBEncrypt = new PinkeyEncrypt();
        //keyNewPassword = bBEncrypt.encrypt(keyNewPassword, user.);
        Response<String> result = passwordMgmService.resetPayPasswdByQuestion(user,question,answer,keyNewPassword);
        if(result.isSuccess()){
            setResult=ImmutableMap.of("status","success","msg","");
            return setResult;
        }else{
            log.error("resetPayPwdByQuestion failed, errorCode={}, productNo={}", result.getErrorCode(),
                    user.getUnifyId());
            setResult=ImmutableMap.of("status","fail","msg",result.getErrorCode()+"|"+result.getErrorMsg());
            return setResult;
        }
    }


    @RequestMapping(value = "/resetQuestionByPayPwd",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> resetQuestionByPayPwd(@RequestParam("newQuestion") String newQuestion,
                                                    @RequestParam("newQuestionAnswer") String newQuestionAnswer,
                                                    @RequestParam("payPwd") String payPwd) {
        CommonUser user = UserUtil.getCurrentUser();
        Map<String,String> setResult;

        Response<String> result = passwordMgmService.resetPasswdQuestionByTxnPwd
                (user,payPwd,newQuestion,newQuestionAnswer);
        if(result.isSuccess()){
            setResult= ImmutableMap.of("status", "success", "msg", "");
            return setResult;
        }else{
            log.error("resetQuestionByPayPwd failed, errorCode={}, unifyId={}",result.getErrorCode
                    (),user.getUnifyId());
            setResult=ImmutableMap.of("status","fail","msg", BussErrorCode.explain(result.getErrorCode()));
            return setResult;
        }
    }

    @RequestMapping(value = "/reSetLoginPasswd", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> reSetLoginPasswd(@RequestParam("oldLoginPasswd") String oldLoginPasswd,
                                                @RequestParam("newLoginPasswd") String newLoginPasswd) throws Exception {
        CommonUser user = UserUtil.getCurrentUser();
        Map<String, String>Result;
        String oldLoginPassworld= Hashing.md5().hashString(oldLoginPasswd, org.apache.commons.codec.Charsets.UTF_8).toString();
        String newLoginPassworld= Hashing.md5().hashString(newLoginPasswd, org.apache.commons.codec.Charsets.UTF_8).toString();
        Response<String> result = passwordMgmService.modifyLoginPasswd(user,oldLoginPassworld,newLoginPassworld);
        if (result.isSuccess()) {
            log.info("success to set LoginPassword");
            Result = ImmutableMap.of("status", "success");
        } else {
            String errorCode = result.getErrorCode();
            String message = BussErrorCode.explain(errorCode);
            Result = ImmutableMap.of("status", "fail","msg",message);
            log.error("fail to set LoginPassword");

        }

        return Result;

    }


    /**
     * 实名认证申请页面，获取国籍nationality、职业profession、地址、证件有效期 、姓名、证件号
     */
    @RequestMapping(value = "/cusInfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getCusInfo(){
        CommonUser commonUser = UserUtil.getCurrentUser();


        Response<List<TDictCode>> response1=null;
        Response<List<TDictCode>> response2 = null;
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            Response<TInfoCustomer> response=userService.loadCustomer(commonUser);
            //获取国籍列表
            response1 = securitySysDataService.querySecuritySysData("nationality");


        //获取职业列表
         response2 =  securitySysDataService.querySecuritySysData("profession");
        if(response.isSuccess()){
            TInfoCustomer result=response.getResult();
            if(response1.isSuccess()){
                map.put("status","success");
                List<TDictCode> nations=response1.getResult();
                //若用户国籍为空，则传入国籍列表
                if(Strings.isNullOrEmpty(result.getNationality())){
                    map.put("nationflag","false");
                    map.put("nationsize",nations.size());
                    map.put("nationlist",nations);
                }else{
                    map.put("nationflag","true");
                    map.put("nationCode",result.getNationality());
                    for(TDictCode data:nations){
                        if(data.getCodeValue().equals(result.getNationality())){
                            map.put("nationDesc",data.getCodeDesc());
                            break;
                        }
                    }
                }
            }else{
                map.put("status","fail");
                map.put("msg",BussErrorCode.explain(response1.getErrorCode()));
                return map;
            }
            if(response2.isSuccess()){
                List<TDictCode> profes=response2.getResult();
                //若用户职业为空，则传入职业列表
                if(Strings.isNullOrEmpty(result.getProfession())){
                    map.put("profesflag","false");
                    map.put("professize",profes.size());
                    map.put("profeslist",profes);
                }else{
                    map.put("profesflag","true");
                    map.put("profCode",result.getProfession());
                    for(TDictCode data:profes){
                        if(data.getCodeValue().equals(map.get("profCode"))){
                            map.put("profDesc",data.getCodeDesc());
                            break;
                        }
                    }
                }
            }else{
                map.put("status","fail");
                map.put("msg",BussErrorCode.explain(response2.getErrorCode()));
                return map;
            }
            if("1".equals(result.getIdentifyType())){
                map.put("valid",result.getIdentifyExpiredDate());
                map.put("idNo",result.getIdentifyNo());
            }else{
                map.put("valid","");
                map.put("idNo","");
            }
            map.put("address",result.getContactAddress());
            map.put("name",result.getName());
            map.put("productNo",result.getCustomerNo());
        }else{
            map.put("status","fail");
            map.put("msg",BussErrorCode.explain(response.getErrorCode()));
            return map;
        }
        return map;
        } catch (Exception e) {
            map.put("status","fail");
            map.put("msg","连接服务异常,请稍后再试");
            return map;
        }
    }


    /**
     * 修改绑定手机
     * @param mobileNo
     * @param payPwd
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/modifyMobileNoBind", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> modifyMobileNoBind(@RequestParam("mobileNo") String mobileNo,@RequestParam("payPwd") String payPwd) throws Exception {
        CommonUser user = UserUtil.getCurrentUser();
        Map<String, String>Result;

        Response<String> result =userService.modifyMobilePhoneBinding(user,mobileNo,payPwd);
        if (result.isSuccess()) {
            log.info("success to set modifyMobileNoBind");
            Result = ImmutableMap.of("status", "success");
        } else {
            String errorCode = result.getErrorCode();
            String message = BussErrorCode.explain(errorCode);
            Result = ImmutableMap.of("status", "fail","msg",message);
            log.error("fail to set modifyMobileNoBind");

        }

        return Result;
    }


    /**
     * 手机绑定
     * @param mobileNo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/mobileNoBind", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> mobileNoBind(@RequestParam("mobileNo") String mobileNo) throws Exception {
        CommonUser user = UserUtil.getCurrentUser();
        Map<String, String>Result;

        Response<String> result =userService.mobilePhoneBinding(user,mobileNo);
        if (result.isSuccess()) {
            log.info("success to set mobileNoBind");
            Result = ImmutableMap.of("status", "success");
        } else {
            String errorCode = result.getErrorCode();
            String message = BussErrorCode.explain(errorCode);
            Result = ImmutableMap.of("status", "fail","msg",message);
            log.error("fail to set mobileNoBind");

        }

        return Result;
    }


    /**
     * 手机解绑
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/unMobileNoBind", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> unMobileNoBind(@RequestParam("payPwd") String payPwd) throws Exception {
        CommonUser user = UserUtil.getCurrentUser();
        Map<String, String>Result;

        Response<String> result =userService.unMobilePhoneBinding(user,payPwd);
        if (result.isSuccess()) {
            log.info("success to set unMobileNoBind");
            Result = ImmutableMap.of("status", "success");
        } else {
            String errorCode = result.getErrorCode();
            String message = BussErrorCode.explain(errorCode);
            Result = ImmutableMap.of("status", "fail","msg",message);
            log.error("fail to set unMobileNoBind");

        }

        return Result;
    }


    /**
     * 交易限额设置
     *
     * @param txnAmt 单笔限额
     * @param txnDayAmt 每日限额
     * @param txnMonAmt 每月限额
     */
    @RequestMapping("/modifyRiskParam")
    @ResponseBody
    public Map<String,String> modifyRiskParamPage(@RequestParam("txnAmt") String txnAmt,
                                                  @RequestParam("txnDayAmt") String txnDayAmt,
                                                  @RequestParam("txnMonAmt") String txnMonAmt){
        CommonUser user = UserUtil.getCurrentUser();
        Map<String,String> setResult;
        Response<String> result;
        txnAmt=txnAmt==null?"":txnAmt;
        txnDayAmt=txnDayAmt==null?"":txnDayAmt;
        txnMonAmt=txnMonAmt==null?"":txnMonAmt;
        if("".equals(txnAmt)||"".equals(txnDayAmt)||"".equals(txnMonAmt)){
            setResult=ImmutableMap.of("status","fail","msg","请稍后再试。");
            return setResult;
        }
        try{
            String txnA= String.valueOf(NumberUtil.newConvertFenLong(txnAmt));
            String txnD=String.valueOf(NumberUtil.newConvertFenLong(txnDayAmt));
            String txnM=String.valueOf(NumberUtil.newConvertFenLong(txnMonAmt));
            if(NumberUtil.newConvertFenLong(txnMonAmt)>=NumberUtil.newConvertFenLong(txnDayAmt)){
                if(NumberUtil.newConvertFenLong(txnDayAmt)>=NumberUtil.newConvertFenLong(txnAmt)){
                    result = accountTranMgmService.modifyRiskParamPage(user,txnA,txnD,txnM);
                }else{
                    setResult=ImmutableMap.of("status","fail","msg","您输入的日消费限额必须大于或等于单笔消费限额");
                    return setResult;
                }
            }else{
                setResult=ImmutableMap.of("status","fail","msg","您输入的月消费限额必须大于或等于日消费限额");
                return setResult;
            }

        }catch (Exception e){
            setResult=ImmutableMap.of("status","fail","msg","请稍后再试。");
            return setResult;
        }

        if(result.isSuccess()){
            setResult=ImmutableMap.of("status","success","msg","");
            return setResult;
        }else{
            log.error("modifyRiskParam failed, errorCode={}, productNo={}",result.getErrorCode(),user.getUnifyId
                    ());
            setResult=ImmutableMap.of("status","fail","msg",BussErrorCode.explain(result.getErrorCode()));
            return setResult;
        }
    }




    /**
     * 交易限额页面获得用户实名级手机用户类型（电信非电信）
     */
    @RequestMapping(value = "/isModifyRisk",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> isModifyRisk(){
        CommonUser user = UserUtil.getCurrentUser();
        String productNo=user.getUnifyId();
        boolean flag= ValidationFieldUtil.checkTelecomMobile(productNo);
        Response<TInfoCustomer> response=userService.loadCustomer(user);
        Map<String,Object> map =new HashMap<String,Object>();
        if(response.isSuccess()){
            TInfoCustomer infoCustomer=response.getResult();
            String isRealName=infoCustomer.getIsRealname();
            map.put("status","success");
            map.put("telCom",flag);
            map.put("isRealName",isRealName);
            return map;
        }else{
            map.put("stauts","fail");
            map.put("msg",BussErrorCode.explain(response.getErrorCode()));
            return map;
        }
    }

    /**
     * 安全等级
     *
     */
    @RequestMapping("/securityCenterGrade")
    @ResponseBody
    public Map<String,String> securityCenterGrade(){
        CommonUser user = UserUtil.getCurrentUser();
        Map<String,String> setResult;
        Response<TInfoCustomer> resTInfoCus;
        int scNum=0;
        try{
            //查询客户信息
            resTInfoCus=  userService.findUser(user);
            if(resTInfoCus.isSuccess()){
                 if(!resTInfoCus.getResult().getIsRealname().equals("4")){
                     scNum++;
                 }
                if(resTInfoCus.getResult().getMobileNo().equals("")||resTInfoCus.getResult().getMobileNo().length()==0){
                    scNum++;
                }
            }
            setResult=ImmutableMap.of("status","success","msg",scNum+"");
            return setResult;
        }catch (Exception e){
            setResult=ImmutableMap.of("status","fail","msg","请稍后再试。");
            return setResult;
        }

    }

}




