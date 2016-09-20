package cn.com.huateng.account.service.impl;

import java.util.Date;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.com.huateng.account.dao.TInfoMobileHMapper;
import cn.com.huateng.account.model.Register;
import cn.com.huateng.account.service.RegisterUserService;
import cn.com.huateng.account.util.ManagerLogCom;
import cn.com.huateng.common.BussErrorCode;
import cn.com.huateng.common.CodeGenerator;
import cn.com.huateng.common.TxnChannel;
import cn.com.huateng.util.ValidationFieldUtil;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.OpenCustomerResultObject;
import com.huateng.p3.account.common.enummodel.KeyType;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.service.HubCustomerManagerService;


/**
 * 用户注册
 *
 * @author huwenjie
 * @date 2013-08-12
 */
@Service
public class RegisterUserServiceImpl implements RegisterUserService {

    private static final Logger logger = LoggerFactory.getLogger(RegisterUserServiceImpl.class);

    @Autowired
    private TInfoMobileHMapper tInfoMobileHMapper;


    @Autowired
    private HubCustomerManagerService hubCustomerManagerService;

    @Autowired
    private ManagerLogCom managerLogCom;
    
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormat
            .forPattern("yyyyMMddHHmmss");
    
    @Value("#{app.centerOrgCode}")
    private  String orgCode;


    @Value("#{app.defaultAreaCode}")
    private  String defaultAreaCode;

    @Value("#{app.defaultCityCode}")
    private  String defaultCityCode;

    @Value("#{app.defaultApanage}")
    private  String defaultApanage;







    /**
     * 开户
     * unifyId   统一编号  必填
     * idType    证件类型  必填
     * idNo      证件号  必填
     * name      名字 必填
     * gender    性别  必填
     * question  密保问题 必填
     * answer    密保答案 必填
     * passWd    非电信 用户 必填
     *
     * @return 如果开户成功, 则返回SUCCESS, 否则返回失败原因
     */
    public Response<String> register(Register register) {
        // 必填判断
        if (Strings.isNullOrEmpty(register.getUnifyId())
                || Strings.isNullOrEmpty(register.getIdType()) || Strings.isNullOrEmpty(register.getIdNo())
                || Strings.isNullOrEmpty(register.getName()) || Strings.isNullOrEmpty(register.getGender())
                || Strings.isNullOrEmpty(register.getQuestion()) || Strings.isNullOrEmpty(register.getAnswer())) {
            return new Response<String>(false, BussErrorCode.ERROR_CODE_800001.getErrorcode());
        }

        // 格式判断
        if (!ValidationFieldUtil.mobilePattern.matcher(register.getUnifyId()).find()&&!ValidationFieldUtil.emailPattern.matcher(register.getUnifyId()).find()) {
            return new Response<String>(false, BussErrorCode.ERROR_CODE_800002
                    .getErrorcode());
        }
         com.huateng.p3.account.persistence.models.TInfoCustomer customer =new com.huateng.p3.account.persistence.models.TInfoCustomer();
        customer.setUnifyId(register.getUnifyId());
        customer.setIdentifyType(register.getIdType());
        customer.setIdentifyNo(register.getIdNo());
        customer.setUserName(register.getName());
        customer.setGender(register.getGender());
        customer.setQuestion(register.getQuestion());
        customer.setAnswer(register.getAnswer());
        customer.setWebLoginPassword(register.getPassWd());
        String areaCode="350000";
        String cityCode="350500";
        String registerType;
        // 格式判断是否手机号
        if (ValidationFieldUtil.checkMobile(register.getUnifyId())) {
            //验证地区代码
//            areaCode = tInfoMobileHMapper.getMobileAreaCode(register.getUnifyId());
//            if (Strings.isNullOrEmpty(areaCode)) {
//                return new Response<String>(false, BussErrorCode.ERROR_CODE_700015
//                        .getErrorcode());
//            }
//            cityCode = tInfoMobileHMapper.getMobileCityCode(register.getUnifyId());
            registerType = "0";
        } else if (ValidationFieldUtil.checkEmail(register.getUnifyId())) {
//            areaCode = defaultAreaCode;
//            cityCode = defaultCityCode;

                  areaCode=defaultAreaCode;
            cityCode=defaultCityCode;
            registerType = "2";
        } else {
            return new Response<String>(false, BussErrorCode.ERROR_CODE_100000.getErrorcode());
        }
        //验证属地
       // String apanage = tInfoMobileHMapper.getMobileApanage(areaCode);
        String apanage="001340100000000";
        if (Strings.isNullOrEmpty(apanage)) {
            apanage  = defaultApanage;
        }

        //开户
        String result;
        try {
            result = openCustomer(register.getUnifyId(), register.getIdType(), register.getIdNo(),
                    register.getAddress(), register.getName(),register.getPassWd(), register.getGender(),
                    register.getQuestion(), register.getAnswer(), areaCode, apanage, cityCode,
                    registerType, register.getNationality(), register.getProfession(), register.getCertExpiryDate(),"0");
        } catch (Exception e) {
            logger.error("fail to register customer from core cause by {}", e);
            return new Response<String>(false, BussErrorCode.ERROR_CODE_200003.getErrorcode());
        }
        if (result.equals(BussErrorCode.ERROR_CODE_000000.getErrorcode())) {
            return new Response<String>(true, "SUCCESS");
        } else {
            return new Response<String>(false, result);
        }
    }


    // 私有内部方法,调用核心dubbo接口开户
    private String openCustomer(String unifyId,
                                String idType, String idNo, String contactAddress, String name,String loginPwd, String gender,
                                String question, String answer, String areaCode, String apanage,
                                String cityCode, String registerType, String nationality,
                                String profession, String certExpiryDate,String isRequestCertificate) throws Exception {
        com.huateng.p3.account.persistence.models.TInfoCustomer tInfoCustomer = new com.huateng.p3.account.persistence.models.TInfoCustomer();
        tInfoCustomer.setUnifyId(unifyId); //统一编号
        tInfoCustomer.setIdentifyNo(idNo);
        tInfoCustomer.setIdentifyType(idType);
        tInfoCustomer.setIdentifyExpiredDate(certExpiryDate);
        tInfoCustomer.setContactAddress(contactAddress);
        tInfoCustomer.setQuestion(question);
        tInfoCustomer.setProfession(profession);
        tInfoCustomer.setAnswer(answer);
        tInfoCustomer.setRegisterType(registerType);
        tInfoCustomer.setAreaCode(areaCode);
        tInfoCustomer.setCityCode(cityCode);
        tInfoCustomer.setApanage(apanage);
        tInfoCustomer.setNationality(nationality);
        tInfoCustomer.setName(name);
        tInfoCustomer.setWebLoginPassword(loginPwd);
        tInfoCustomer.setGender(gender);
        tInfoCustomer.setIsRequestCertificate(isRequestCertificate);
        // 手机号
        if (Objects.equal(registerType, "0")) {
            tInfoCustomer.setIsRealname("3");
            tInfoCustomer.setMobileNo(unifyId);
        } else if (Objects.equal(registerType, "2")) {  //邮箱
            tInfoCustomer.setIsRealname("3");
            tInfoCustomer.setEmailAddress(unifyId);
        } else if (Objects.equal(registerType, "1")) {  //速通卡号开户 实名用户
            tInfoCustomer.setIsRealname("1");
        }
        AccountInfo accountInfo=new AccountInfo();
        accountInfo.setAccountKey(unifyId);
        accountInfo.setKeyType(KeyType.UNIFY);
        String inputTime = DATE_TIME_FORMAT.print(new Date().getTime());
        ManagerLog managerLog=new ManagerLog();
        managerLog.setAcceptDate(inputTime.substring(0, 8));
        managerLog.setAcceptTime(inputTime.substring(8));
        managerLog.setInputTime(DATE_TIME_FORMAT.parseDateTime(inputTime).toDate());
        managerLog.setCheckTime(DATE_TIME_FORMAT.parseDateTime(inputTime).toDate());
        managerLog.setAcceptTxnSeqNo(CodeGenerator.transSerialNo());
        managerLog.setTxnChannel(TxnChannel.CHANNEL_02.getValue());
        managerLog.setAcceptOrgCode(orgCode);
        

        Response<OpenCustomerResultObject> openCustomerResultObjectResponse = hubCustomerManagerService.openCustomer(tInfoCustomer, managerLog);
        if (openCustomerResultObjectResponse.isSuccess()) {
            logger.info("open account success unifyId:{} customerNo:{} ", unifyId, openCustomerResultObjectResponse.getResult().getCustomerNo());
            return "000000";
        } else {
            logger.error("fail to open account unifyId:{} cause by {}", unifyId, openCustomerResultObjectResponse.getErrorCode() + ":" + openCustomerResultObjectResponse.getErrorMsg());
            return openCustomerResultObjectResponse.getErrorCode();
        }

    }




    }
