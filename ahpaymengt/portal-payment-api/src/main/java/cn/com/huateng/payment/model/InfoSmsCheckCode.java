package cn.com.huateng.payment.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信验证码
 * Created with IntelliJ IDEA.
 * User: AP
 * Date: 13-8-26
 */
public class InfoSmsCheckCode implements Serializable {
    @Getter
    @Setter
    private String recordNo;
    @Getter
    @Setter
    private String codeType;
    @Getter
    @Setter
    /* @property: 手机号 */
    private String mobileNo;
    @Getter
    @Setter
    /* @property: 验证码 */
    private String checkCode;
    @Getter
    @Setter
    /* @property: 下发时间 */
    private Date sendTime;
    @Getter
    @Setter
    private String status;
    @Getter
    @Setter
    private String resvFld1;

}
