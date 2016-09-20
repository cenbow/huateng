package cn.com.huateng.web.controller.api;

import cn.com.huateng.account.model.Register;
import cn.com.huateng.account.model.TInfoCustomer;
import cn.com.huateng.account.service.QueryQuestionsService;
import cn.com.huateng.account.service.RegisterUserService;
import cn.com.huateng.account.service.UserService;
import cn.com.huateng.common.BussErrorCode;
import com.aixforce.exception.JsonResponseException;
import com.google.common.collect.ImmutableMap;
import com.huateng.p3.account.persistence.models.TDictCode;
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
import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-16
 * Time: ����11:31
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping("/api")

public class Registers {
    private final static Logger log = LoggerFactory.getLogger(Registers.class);
    @Autowired
    private QueryQuestionsService queryQuestionsService;
    @Autowired
    private RegisterUserService registerUserService;

    @Autowired
    private UserService userService;

    @Value("#{app.lockPeriod}")
    private int lockPeriod;

    @Value("#{app.maxErrNum}")
    private Long maxErrNum;

    @Value("#{app.mainSite}")
    private String mainSite;
    //注册-- 输入账户时检查是否已经注册
    @RequestMapping(value = "/checkRegister", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> checkRegister (@RequestParam("unifyId") String unifyId) throws Exception{
        Map<String, String> result;
                 Response<TInfoCustomer> response= userService.findUserByUnifyId(unifyId);
        if(response.isSuccess()){
            log.info("success to register");
                   result= ImmutableMap.of("status", "success");
            return  result;
        }else {
            log.error("fail to checkRegister cause by {}", response.getErrorCode() + ":" + response.getErrorMsg());
            result= ImmutableMap.of("status", "fail");
            return  result;
        }
    }


    //注册
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> register (
            @RequestParam("unifyId") String unifyId,
            @RequestParam("loginPassword") String loginPassword,
            @RequestParam("userName") String userName,
            @RequestParam("gender") String gender,
            @RequestParam("identity") String identity,
            @RequestParam("identityNo") String identityNo,
            @RequestParam("question") String question,
            @RequestParam("answer") String answer,
            @RequestParam("vcode") String vCode,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, String> registerResult;
        if (!CaptchaGenerator.matches(request, vCode)) {
            registerResult = ImmutableMap.of("status", "fail", "msg", "验证码输入错误,请重新输入.");
            return registerResult;
        }
        Register register = new Register(unifyId, identity, identityNo,
                userName, gender, question,
                answer, loginPassword, null,
                null);
        Response<String> result = registerUserService.register(register);
        if (result.isSuccess()) {
            log.info("success to register");
            registerResult= ImmutableMap.of("status", "success", "msg", "注册成功");
            return registerResult;
        } else {
            log.error("fail to register cause by {}", result.getErrorCode() + ":" + result.getErrorMsg());
            registerResult= ImmutableMap.of("status", "fail", "msg", BussErrorCode.explain(result.getErrorCode()));
            return registerResult;
        }
    }


    @RequestMapping(value = "/getQuestion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<TDictCode> getQuestion() throws Exception {
        Response<List<TDictCode>> question = queryQuestionsService.querySecurityQuestions();
        if (question.isSuccess()) {
            log.info("success to get question");
            return question.getResult();
        } else {
            log.error("fail to get question cause by {}", question.getErrorCode() + ":" + question.getErrorMsg());
            throw new JsonResponseException(500, question.getErrorMsg());
        }
    }
}
