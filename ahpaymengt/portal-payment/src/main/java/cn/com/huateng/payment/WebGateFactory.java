package cn.com.huateng.payment;

import cn.com.huateng.common.WebGateEnum;
import com.google.common.collect.Maps;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * User: 董培基
 * Date: 13-8-20
 * Time: 上午10:59
 */
@Component
public class WebGateFactory {
    /*网关支付请求地址*/
    @Value("#{app.webGateUrl}")
    private String payClient;

    /*网关充值请求地址*/
    @Value("#{app.webAccountRechargeUrl}")
    private String accountRechargeClient;

    /*支付SP身份标识商户号渠道号*/
    @Value("#{app.webGateOrgCode}")
    private String webGateOrgCode;

    /*充值网关商户号*/
    @Value("#{app.accountRechargeOrgCode}")
    private String accountRechargeOrgCode;

    /* 支付前台返回地址*/
    @Value("#{app.payMerChantUrl}")
    private String payMerChantUrl;

    /* 支付后台返回地址*/
    @Value("#{app.payBackMerchantUrl}")
    private String payBackMerchantUrl;

    /* 充值前台返回地址*/
    @Value("#{app.accountMerChantUrl}")
    private String accountMerChantUrl;

    /* 充值后台返回地址*/
    @Value("#{app.accountBackMerchantUrl}")
    private String accountBackMerchantUrl;

    /* 支付调用网关key*/
    @Value("#{app.webGateMacKey}")
    private String webGateMacKey;

    /* 充值网关key*/
    @Value("#{app.accountRechargeMacKey}")
    private String accountRechargeMacKey;


    private Map<ConfigKey, Config> configMap = Maps.newHashMap();

    @PostConstruct
    public void init() {
        configMap.put(ConfigKey.payRecharge, new Config(payClient, webGateOrgCode, webGateMacKey,
                payMerChantUrl, payBackMerchantUrl, WebGateEnum.WebGate_BUSICODE_02.getValue(),WebGateEnum.WebGate_BUSITYPE_02.getValue(),WebGateEnum.WebGate_TRANSCODE_0002.getValue()));
        configMap.put(ConfigKey.accountRecharge, new Config(accountRechargeClient, accountRechargeOrgCode,
                accountRechargeMacKey, accountMerChantUrl, accountBackMerchantUrl, WebGateEnum.WebGate_BUSICODE_01.getValue(),WebGateEnum.WebGate_BUSITYPE_01.getValue(), WebGateEnum.WebGate_TRANSCODE_0001.getValue()));
    }

    public Config getConfig(ConfigKey key) {
        return configMap.get(key);
    }


    public class Config {
        @Getter
        private String client;
        @Getter
        private String code;
        @Getter
        private String macKey;
        @Getter
        private String frontUrl;
        @Getter
        private String backUrl;
        @Getter
        private String busiCode;
        @Getter
        private String busiType;
        @Getter
        private String traceCode;

        public Config(String client,String code, String macKey, String frontUrl, String backUrl, String busiCode,String busiType, String traceCode) {
            this.client = client;
            this.code = code;
            this.macKey = macKey;
            this.frontUrl = frontUrl;
            this.backUrl = backUrl;
            this.busiCode = busiCode;
            this.busiType = busiType;
            this.traceCode = traceCode;
        }
    }

    public enum ConfigKey {
        accountRecharge, payRecharge
    }
}
