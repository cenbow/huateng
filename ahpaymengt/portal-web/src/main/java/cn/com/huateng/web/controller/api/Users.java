package cn.com.huateng.web.controller.api;


import cn.com.huateng.CommonUser;
import cn.com.huateng.account.model.TInfoCustomer;
import cn.com.huateng.account.service.UserService;
import cn.com.huateng.common.BussErrorCode;
import cn.com.huateng.web.controller.Targets;
import cn.com.huateng.web.controller.api.userEvent.LoginEvent;
import cn.com.huateng.web.controller.api.userEvent.LogoutEvent;
import cn.com.huateng.web.controller.api.userEvent.UserEventBus;

import com.aixforce.common.utils.CommonConstants;
import com.aixforce.exception.JsonResponseException;
import com.aixforce.session.util.WebUtil;
import com.aixforce.user.base.BaseUser;
import com.aixforce.user.base.UserUtil;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.huateng.p3.component.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-08-06
 */
@Controller
@RequestMapping("/api/user")
public class Users {

    private final static Logger log = LoggerFactory.getLogger(Users.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserEventBus userEventBus;

    @Autowired
    private UserCache userCache;

    @Value("#{app.lockPeriod}")
    private int lockPeriod;

    @Value("#{app.maxErrNum}")
    private Long maxErrNum;

    @Value("#{app.mainSite}")
    private String mainSite;

    /**
     * 判断用户是否登录（防止退出后的浏览器后退行为）
     *
     * @param target 跳转地址
     * @author hannawei
     */
    @RequestMapping(value = "/islogin", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> isLogin(@RequestParam("target") String target) {
        Map<String, String> resultMap;
        CommonUser user = UserUtil.getCurrentUser();
        String url = "http://" + mainSite + "/login?target=" + target;
        if (user != null) {
            resultMap = ImmutableMap.of("status", "ok", "target", url);
        } else {
            resultMap = ImmutableMap.of("status", "fail", "target", url);
        }
        return resultMap;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    
    public Map<String,String> login(@RequestParam("unifyId") String unifyId,
                        @RequestParam("password") String password,
                        @RequestParam("vcode") String vCode,
                        @RequestParam(value = "target", defaultValue = "/account") String target,
                        HttpServletRequest request,
                        HttpServletResponse response)throws Exception{
        Map<String, String> loginResult;
        //验证图片验证码
       /* if (!CaptchaGenerator.matches(request, vCode)) {
            loginResult = ImmutableMap.of("status", "fail", "msg", "验证码输入错误，请重新输入。");
            return loginResult;
        }*/
        
        Response<TInfoCustomer> result = userService.login(unifyId, password, WebUtil.getClientIpAddr(request));
        if (result.isSuccess()) {
            TInfoCustomer user = result.getResult();
            request.getSession().setAttribute(CommonConstants.SESSION_USER_ID, user.getCustomerNo());
            //invalidate user cache to ensure a fresh user object
            userCache.evict(Long.parseLong(user.getCustomerNo()));
            LoginEvent loginEvent = new LoginEvent(Long.parseLong(user.getCustomerNo()), request, response);
            userEventBus.post(loginEvent);
            //target过滤
            if ("/account".equals(target)) {
                loginResult = ImmutableMap.of("status", "ok", "target", target);
            } else {
                Iterable<String> parts = Splitter.on('/').trimResults().omitEmptyStrings().split(target);
                String mainsite = Iterables.get(parts, 0) + "//" + Iterables.get(parts, 1);
                StringBuilder prePath = new StringBuilder();
                for (int i = 2; i < Iterables.size(parts); i++) {
                    prePath.append("/").append(Iterables.get(parts, i));
                }
                String directPath = Targets.targetProcess(prePath.toString());
                loginResult = ImmutableMap.of("status", "ok", "target", mainsite + directPath);
            }
            return loginResult;
        } else {
            String errorCode = result.getErrorCode();
            String message = result.getErrorMsg();
            
            log.error("user(unifyId={}) login failed, errorCode={})", unifyId, errorCode);
            loginResult = ImmutableMap.of("status", "fail", "msg", message);
            return loginResult;
        }
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            BaseUser baseUser = UserUtil.getCurrentUser();
            if (baseUser != null) {
                userCache.evict(baseUser.getId());
                //delete login token cookie
                LogoutEvent logoutEvent = new LogoutEvent(baseUser.getId(), request, response);
                userEventBus.post(logoutEvent);
            }
            if ("tel".equals(request.getParameter("type"))) {
                return "redirect:/login";
            } else if ("card".equals(request.getParameter("type"))) {
                return "redirect:/login?type=card";
            }
            return "redirect:/";
        } catch (Exception e) {
            log.error("failed to logout user,cause:", e);
            throw new JsonResponseException(500, "登出失败");
        }
    }

    /**
     * 验证用户手机号

     */
    @RequestMapping(value = "/checkMobile", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> matchVerifyCode(@RequestParam("mobileNo") String mobileNo,@RequestParam("unifyId") String unifyId){
        Map<String,String> setResult;
        Response<TInfoCustomer> response = new Response<TInfoCustomer>();
        response=userService.findUserByUnifyId(unifyId);

        if(response.getResult().getMobileNo()!=""){
            if(mobileNo.equals(response.getResult().getMobileNo())){
                setResult=ImmutableMap.of("status","ok","msg","");
                return setResult;
            }
            setResult=ImmutableMap.of("status","fail","msg","手机号码和注册时手机号码不一致");
            return setResult;
        } else {
        setResult=ImmutableMap.of("status","ok","msg","");
        return setResult;
        }
}
    @RequestMapping(value = "/realName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> realName() throws Exception {
            CommonUser commonUser= UserUtil.getCurrentUser();
        Map<String, String> setResult ;
        try {
            Response<TInfoCustomer> tInfoCustomerResponse = userService.loadCustomer(commonUser);
            if (tInfoCustomerResponse.isSuccess()) {
                TInfoCustomer tInfoCustomer = tInfoCustomerResponse.getResult();
                setResult=ImmutableMap.of("status","success","msg",tInfoCustomer.getIsRealname());
                return setResult;
            } else {
                log.error("failed to get account realName", tInfoCustomerResponse.getErrorCode());
                setResult= ImmutableMap.of("status", "fail", "msg", tInfoCustomerResponse.getErrorMsg());
                return setResult;
            }
        } catch (Exception e) {
            log.error("realName failed, errorCode={}, unifyId={}",e,commonUser.getUnifyId());
            setResult= ImmutableMap.of("status", "fail", "msg", "获取实名信息失败");
            return setResult;
        }
    }

    @RequestMapping(value = "/modifyCustomerInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> modifyCustomerInfo(@RequestParam("gender") String gender,@RequestParam("homeTelephone") String homeTelephone,@RequestParam("officeTelephone") String officeTelephone,@RequestParam("otherTelephone") String otherTelephone,@RequestParam("contactAddress") String contactAddress) throws Exception {
        CommonUser commonUser= UserUtil.getCurrentUser();
        Map<String, String> setResult ;
        try {
            Response<String> response = userService.modifyCustomerInfo(commonUser,gender,homeTelephone,officeTelephone,otherTelephone,contactAddress);
            if (response.isSuccess()) {

                setResult=ImmutableMap.of("status","success","msg","");
                return setResult;
            } else {
                log.error("failed to get account modifyCustomerInfo", BussErrorCode.explain(response.getErrorCode()));
                setResult= ImmutableMap.of("status", "fail", "msg", BussErrorCode.explain(response.getErrorCode()));
                return setResult;
            }
        } catch (Exception e) {
            log.error("modifyCustomerInfo failed, errorCode={}, unifyId={}",e,commonUser.getUnifyId());
            setResult= ImmutableMap.of("status", "fail", "msg", "获取用户信息失败");
            return setResult;
        }
    }



    @RequestMapping(value = "/getAddCard2", method = RequestMethod.POST)
    public String getAddCard2(@RequestParam("bankType") String bankType,@RequestParam("bankCode") String bankCode,@RequestParam("cardType") String cardType,Map<String, Object> context) throws Exception {
        String url="/layout/sutongpay/myCard/addCard2";
        if(UserUtil.getCurrentUser()==null){
            url="/layout/sutongpay/login";
            return url;
        }
        CommonUser commonUser= UserUtil.getCurrentUser();

        if(Strings.isNullOrEmpty(commonUser.getUnifyId())){
             url="/layout/sutongpay/login";
            return url;
        }
        Response<TInfoCustomer> tInfoCustomerResponse = userService.loadCustomer(commonUser);
        if (tInfoCustomerResponse.isSuccess()) {
            TInfoCustomer tInfoCustomer = tInfoCustomerResponse.getResult();
            context.put("name",tInfoCustomer.getName());
            context.put("idNo",tInfoCustomer.getIdentifyNo());
            context.put("mobileNo",tInfoCustomer.getMobileNo());

        } else {
            String errorCode = tInfoCustomerResponse.getErrorCode();
            String message = BussErrorCode.explain(errorCode);
            log.error("failed to loadCustomer", tInfoCustomerResponse.getErrorCode());
            throw new JsonResponseException(500, message);

        }
        context.put("bankType",bankType);
        context.put("bankCode",bankCode);
        context.put("cardType",cardType);
        return url;
    }





}