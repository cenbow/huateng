package cn.com.huateng.web.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.huateng.payment.model.InfoSmsCheckCode;
import cn.com.huateng.payment.service.MobileCheckCodeService;
import cn.com.huateng.util.DateUtil;
import cn.com.huateng.util.StringUtil;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.huateng.p3.component.Response;

@Component
public class Message {

    private final static Logger log = LoggerFactory.getLogger(Message.class);

    @Autowired
    private MobileCheckCodeService mobileCheckCodeService;


    public  String checkMessage(String mobileNo, String checkCode,boolean... isMobileNoCheck) {

            if(isMobileNoCheck.length > 0 && isMobileNoCheck[0]){

            }    else{
                //手机号验证
                if (Strings.isNullOrEmpty(mobileNo)) {
                    //1 手机号码验证不通过
                    return "手机号码验证不通过";
                }
            }
        //短信验证码验证
        InfoSmsCheckCode infoMobileCheckCode = new InfoSmsCheckCode();
        infoMobileCheckCode.setMobileNo(mobileNo);
        infoMobileCheckCode.setCheckCode(checkCode);
        Response<String> response = mobileCheckCodeService.isMobileCheckCodeRight(infoMobileCheckCode);
        if (response.isSuccess()) {
            //验证通过后更新验证码状态
            Response<InfoSmsCheckCode> resultResponse = mobileCheckCodeService.isMobileExisted(infoMobileCheckCode);
            InfoSmsCheckCode infoMobileCheckCodeOld = resultResponse.getResult();
            infoMobileCheckCodeOld.setStatus("2");
            mobileCheckCodeService.updateInfoMobileCheckCode(infoMobileCheckCodeOld);
            return "0";
        } else {
            //3 您输入的验证码不正确或已过期
            return "您输入的短信验证码不正确或已过期";
        }
    }


    /**
     * 发送短信
     *
     * @param sendType 用户类型
     * @param mobile   手机号
     * @param uuord    sessionId
     * @return Map 包含“result”:true或false,“code”:验证码
     */
    public Map<String, Object> sendMessageTest(String sendType, String mobile, String uuord) {
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(2);
        boolean result;
        String mobileActiveCode = StringUtil.random(6);//随机生成6位数字
        String content;//短信内容
        if ("Y".equals(sendType)) {
            content = "欢迎使用号码百事通业务！您本次操作的验证码是：" + mobileActiveCode.trim()
                    + "。请勿泄露验证码，他人索取行为均可能为诈骗。如有疑问，请致电客服中心垂询：4008 0 11888";
        } else {
            content = "欢迎使用翼支付业务！您本次操作的验证码是：" + mobileActiveCode.trim()
                    + "。请勿泄露验证码，他人索取行为均可能为诈骗。如有疑问，请致电客服中心垂询：4008 0 11888";
        }
        // 短信业务类型标识01：默认信息02：交易类短信03：日报类短信04：营销类短信05：监控类短信
        try {
            //更新短信表所有短信状态为 -->发送2
            InfoSmsCheckCode infoSmsCheckCodeset = new InfoSmsCheckCode();
            infoSmsCheckCodeset.setStatus("2");// 1：生成 ；2:发送；3:重新发送
            infoSmsCheckCodeset.setMobileNo(mobile);
            Response<String> response=mobileCheckCodeService.updateMobileOld(infoSmsCheckCodeset);
            if (response.isSuccess()) {
                log.info("updateMobileOld success infoSmsCheckCodeset:{}",  infoSmsCheckCodeset);

            } else {
                log.error("fail to updateMobileOld infoSmsCheckCodeset:{}  cause by {}", new Object[]{infoSmsCheckCodeset,  response.getErrorCode() + ":" + response.getErrorMsg()});
                map.put("result", false);
                return map;
            }
            //插入新生成的短信验证码，短信状态为-->生成1
            InfoSmsCheckCode tInfoSmsCheckCode = new InfoSmsCheckCode();
            tInfoSmsCheckCode.setMobileNo(mobile);
            tInfoSmsCheckCode.setCodeType(uuord);
            tInfoSmsCheckCode.setStatus("1");
            tInfoSmsCheckCode.setCheckCode(mobileActiveCode.trim());
            tInfoSmsCheckCode.setSendTime(DateUtil.getTime());
            tInfoSmsCheckCode.setResvFld1("1");
            Response<String> responseIns =mobileCheckCodeService.insertMobileCheckCode(tInfoSmsCheckCode);
            if (responseIns.isSuccess()) {
                log.info("insertMobileCheckCode success infoSmsCheckCodeset:{}",  infoSmsCheckCodeset);

            } else {
                log.error("fail to insertMobileCheckCode infoSmsCheckCodeset:{}  cause by {}", new Object[]{infoSmsCheckCodeset,  responseIns.getErrorCode() + ":" + responseIns.getErrorMsg()});
                map.put("result", false);
                return map;
            }
            //发送短信标志判断
//            if (!"false".equals(smsSendFlag)){
//                SmsService smsService = new SmsService(smsUrl);
//                result = smsService.send(content, mobile);
//            }else {
                //不发送短信，并将生成的短信验证码发送到页面（开发环境适用）
                map.put("code", mobileActiveCode.trim());
                result = true;
         //   }
            map.put("result", result);
            return map;
        } catch (Exception e) {
            log.error("send message failed, smsUrl={}, exception message={}", "", e.getMessage());
            map.put("result", false);
            return map;
        }

    }
}
