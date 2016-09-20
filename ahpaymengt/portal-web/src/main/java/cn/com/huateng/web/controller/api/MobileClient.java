package cn.com.huateng.web.controller.api;


import cn.com.huateng.web.controller.Message;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
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
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lzw
 * Date: 13-8-22
 * Time: 下午4:23
 */
@Controller
@RequestMapping("/api/mobileclient")
public class MobileClient {
    private final static Logger log = LoggerFactory.getLogger(MobileClient.class);

    @Autowired
    private Message message;



    /**
     * 电信用户登录、非电信用户找回密码获取短信验证码(方便测试使用，该方法将短信验证码返回)
     * @param uuord
     * @param mobile 手机号码（电信）
     * @param sendType login(登录) findpwd找回密码
     */
    @RequestMapping(value = "/testsend", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> sendNoticeTest(@RequestParam("uuord") String uuord,
                                              @RequestParam("mobile") String mobile,
                                              @RequestParam("sendType") String sendType,
                                              HttpServletRequest request
                                                    ) {
        Map<String, String> sendResult;
        try {
            if (Strings.isNullOrEmpty(uuord)) {
                uuord = request.getSession().getId();
            }
            Map<String, Object> map = message.sendMessageTest(sendType, mobile, uuord);
            Boolean result = (Boolean) map.get("result");
            if (result) {
                if (map.get("code") != null) {
                    sendResult = ImmutableMap.of("status", "ok", "msg", map.get("code").toString());
                } else {
                    sendResult = ImmutableMap.of("status", "ok", "msg", "");
                }
            } else {
                sendResult = ImmutableMap.of("status", "fail", "msg", "短信验证码发送失败。");
            }
            return sendResult;
        } catch (Exception e) {
            log.error("send message exception:", e);
            sendResult = ImmutableMap.of("status", "fail", "msg", "短信验证码发送失败。");
            return sendResult;
        }
    }
}




