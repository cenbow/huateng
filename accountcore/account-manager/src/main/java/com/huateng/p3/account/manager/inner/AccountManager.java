package com.huateng.p3.account.manager.inner;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import com.huateng.p3.account.ServiceHelper.SequenceGenerator;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.OpenCustomerResultObject;
import com.huateng.p3.account.common.enummodel.AccountName;
import com.huateng.p3.account.common.enummodel.AccountStatus;
import com.huateng.p3.account.common.enummodel.AccountType;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.enummodel.CardBingdingMethod;
import com.huateng.p3.account.common.enummodel.CustomerGender;
import com.huateng.p3.account.common.enummodel.CustomerGrade;
import com.huateng.p3.account.common.enummodel.CustomerIdType;
import com.huateng.p3.account.common.enummodel.CustomerRealname;
import com.huateng.p3.account.common.enummodel.CustomerStatus;
import com.huateng.p3.account.common.enummodel.CustomerType;
import com.huateng.p3.account.common.enummodel.OrgDefaultCode;
import com.huateng.p3.account.common.enummodel.OrgType;
import com.huateng.p3.account.common.enummodel.RegisterType;
import com.huateng.p3.account.common.enummodel.RiskBlackType;
import com.huateng.p3.account.common.enummodel.TrueFalseLabel;
import com.huateng.p3.account.common.enummodel.TrueOrFalse;
import com.huateng.p3.account.common.enummodel.TxnInnerType;
import com.huateng.p3.account.common.enummodel.TxnType;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.common.exception.SubmitBizException;
import com.huateng.p3.account.common.util.PidUtil;
import com.huateng.p3.account.common.util.StringUtil;
import com.huateng.p3.account.commonservice.AccountService;
import com.huateng.p3.account.commonservice.CardService;
import com.huateng.p3.account.commonservice.CustomerService;
import com.huateng.p3.account.commonservice.OrgService;
import com.huateng.p3.account.commonservice.RiskService;
import com.huateng.p3.account.commonservice.TxnCheckService;
import com.huateng.p3.account.component.CacheComponent;
import com.huateng.p3.account.component.CustomerResultGenComponent;
import com.huateng.p3.account.component.PinkeyComponent;
import com.huateng.p3.account.component.SecurityComponent;
import com.huateng.p3.account.component.SmsComponent;
import com.huateng.p3.account.persistence.TInfoAccountCardMapper;
import com.huateng.p3.account.persistence.TInfoAccountMapper;
import com.huateng.p3.account.persistence.TInfoCustomerMapper;
import com.huateng.p3.account.persistence.TRealnameApplyMapper;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TInfoAccountCard;
import com.huateng.p3.account.persistence.models.TInfoCard;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.account.persistence.models.TInfoOrg;
import com.huateng.p3.account.persistence.models.TLogAccountManagement;
import com.huateng.p3.account.persistence.models.TRealnameApply;
import com.huateng.p3.account.persistence.models.TbPosInfo;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-12-10
 */
@Component
@Slf4j
public class AccountManager {
    @Value("${realnameCount}")
    private int realnameCount;

    private final static Pattern telePattern = Pattern.compile("1(33|53|80|81|89|70|77)[0-9]{8}");

    @Autowired
    private SmsComponent smsComponent;

    @Autowired
    private TInfoAccountCardMapper tInfoAccountCardMapper;

    @Autowired
    private TInfoCustomerMapper tInfoCustomerMapper;

    @Autowired
    private TInfoAccountMapper tInfoAccountMapper;

    @Autowired
    private CardService cardService;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired(required = false)
    private PinkeyComponent pinkeyComponent;

    @Autowired
    private SecurityComponent securityComponent;

    @Autowired
    private TxnCheckService txnCheckService;

    @Autowired
    private SecurityComponent securityService;

    @Autowired
    private OrgService orgService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TRealnameApplyMapper tRealnameApplyMapper;

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private CustomerResultGenComponent customerResultGenComponent;

    @Autowired
    private RiskService riskService;


    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    
    public OpenCustomerResultObject doOpenCustomer(TInfoCustomer customer, ManagerLog managerLog) throws BizException {
        //创建用户
        String initPinKey = createCustomer(customer, managerLog);
        //创建资金子账户
        TInfoAccount fundAccount = createFundAccount(customer, managerLog);
        //创建积分账户
        createIntegralAccount(customer, managerLog);
        //创建脱机子账户
//        if (!Strings.isNullOrEmpty(cardNo)) { 
//            createOffLineAccount(customer, fundAccount, cardNo, managerLog);
//        }
        return customerResultGenComponent.genOpenCustomerResultObject(fundAccount, customer, initPinKey);
    }


    //创建用户 返回初始登录密码
    private String createCustomer(TInfoCustomer customer, ManagerLog managerLog) {
        //检查黑名单身份证
        riskService.queryRiskBlack(customer.getIdentifyNo(), RiskBlackType.BLACK_TYPE_IDNO.getTypeCode());
        String unifyId = customer.getUnifyId();
        //判断客户是否存在,如果已经存在,则直接返回
        TInfoCustomer existed = tInfoCustomerMapper.findCustomerByUnifyId(unifyId);
        if (existed != null) {
            throw new BizException(BussErrorCode.ERROR_CODE_200001.getErrorcode(), BussErrorCode.ERROR_CODE_200001.getErrordesc());
        }
        //TODO  修改 findCustomerByProductNo 为   findCustomerByUnifyId  然后 修改 mapper 中 为通过unifyId 查询
        //当日注销又开户不允许
        TInfoCustomer closedExisted = tInfoCustomerMapper.checkClosedCustomerByUnifyId(unifyId);
        if (closedExisted != null) {
            throw new BizException(BussErrorCode.ERROR_CODE_500051.getErrorcode(), BussErrorCode.ERROR_CODE_500051.getErrordesc());
        }
        //检验手机号
        //之民不需要此检查
        //txnCheckService.checkTeleMobilePhone(customer);
        //对area code及city code进行处理
        String registerType = customer.getRegisterType();
        String areaCode = customer.getAreaCode();
        String cityCode = customer.getCityCode();
        //根据手机号前7位，获取对应的地区城市码
        //之民不需要此判断
//        if (RegisterType.CUSTOMER_REGISTER_TYPE_MOBILE.getValue().equals(registerType) ||  RegisterType.CUSTOMER_REGISTER_TYPE_OTHERPHONE.getValue().equals(registerType)) { //产品号是手机号
//            TDictAreaCity dictAreaCity = cacheComponent.queryAreaCityCodeByProductNo(productNo.substring(0, 7));
//      
//            
//            if (dictAreaCity != null && !Strings.isNullOrEmpty(dictAreaCity.getParenetCode()) && !Strings.isNullOrEmpty(dictAreaCity.getCurrentCode())) {
//                areaCode = dictAreaCity.getParenetCode();
//                cityCode = dictAreaCity.getCurrentCode();
//            }
//        }
//        //areaCode及cityCode不能为空
//        if (Strings.isNullOrEmpty(areaCode) || Strings.isNullOrEmpty(cityCode)) {
//            throw new BizException(BussErrorCode.ERROR_CODE_200017.getErrorcode(),BussErrorCode.ERROR_CODE_200017.getErrordesc());
//        }
//        // 账户属地不能为空
//        if (Strings.isNullOrEmpty(customer.getApanage())) {
//            throw new BizException(BussErrorCode.ERROR_CODE_200026.getErrorcode(),BussErrorCode.ERROR_CODE_200026.getErrordesc());
//        }

        customer.setAreaCode(areaCode);
        customer.setCityCode(cityCode);
        customer.setRegisteOrgCode(managerLog.getAcceptOrgCode() != null ? managerLog.getAcceptOrgCode() : customer.getRegisteOrgCode());
        String grade = determineGrade(customer, unifyId, registerType);

        customer.setCustomerGrade(grade);
        String customerNo = sequenceGenerator.generateCustomerNo(areaCode, cityCode);
        customer.setCustomerNo(customerNo);
        Date now = new Date();
        customer.setLastUpdateTime(now);
        customer.setRegisteDate(now);
        customer.setCustomerStatus(CustomerStatus.CUSTOMER_STATUS_NORMAL.getCustomerStatusCode());
        customer.setActiveStatus(CustomerStatus.CUSTOMER_ACTIVED.getCustomerStatusCode());
        customer.setCustomerType(CustomerType.CUSTOMER_TYPE_PERSON.getCode());
        if (Strings.isNullOrEmpty(customer.getGender())) {
            customer.setGender(CustomerGender.CUSTOMER_GENDER_UNPOINT.getCustomerGenderCode());//未指定性别
        }

        String initPinKey = customer.getWebLoginPassword();
        String pinKey = Hashing.md5().hashString(initPinKey, Charsets.UTF_8).toString();
        customer.setWebLoginPassword(pinKey);
        customer.setIvrPassword(pinKey);
        customer.setAccountQueryPassword(pinKey);
        customer.setIsClosingAccount(TrueOrFalse.FALSE.getLablCode());
        customer.setInitPinKey(initPinKey);
        tInfoCustomerMapper.insert(customer);
        return initPinKey;
    }

    //创建现金子账户
    private TInfoAccount createFundAccount(TInfoCustomer customer, ManagerLog managerLog) {
        String txnType = TxnInnerType.TXN_TYPE_101010.getTxnInnerType();
        managerLog.setInnerTxnType(txnType);
        //查询有无资金子账户
        TInfoAccount existed = tInfoAccountMapper.findFundAccountByCustomerNo(customer.getCustomerNo());
        if (existed != null) {
            throw new BizException(BussErrorCode.ERROR_CODE_200002.getErrorcode(), BussErrorCode.ERROR_CODE_200002.getErrordesc());
        }
        //受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(managerLog.getAcceptOrgCode() != null ? managerLog.getAcceptOrgCode() : customer.getRegisteOrgCode(),
                txnType, OrgType.ORG_TYPE_ORG.getOrgtype());

        TInfoAccount account = new TInfoAccount();
        account.setType(AccountType.FUND.getValue());
        String accountNo = sequenceGenerator.generateAccountNo(customer.getCustomerNo(), AccountType.FUND);
        String plainInitPassword = StringUtil.generateRandomString(6);
        //加密服务器暂时没有，此处密码使用MD5加密
        String encryptPassword = Hashing.md5().hashString(plainInitPassword, Charsets.UTF_8).toString();;
//        String encryptPassword = pinkeyComponent.encryptPin(plainInitPassword, accountNo);
        account.setAccountNo(accountNo);
        account.setCustomerNo(customer.getCustomerNo());
        account.setAccountName(AccountName.DEFAULT_ACCOUNT_NAME_FUND.getAccountName());
        account.setApanage(OrgDefaultCode.GROUP_ORG_CODE.getOrgCode());// 资金账户，账户所属机构默认为集团号百
        account.setStatus(AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus());
        account.setPayPasswd(encryptPassword);
        account.setInitPasswd(encryptPassword);
        account.setInitPasswdModified(TrueOrFalse.FALSE.getLablCode());
        account.setAreaCode(customer.getAreaCode());
        account.setCityCode(customer.getCityCode());
        account.setOrganizationCode(customer.getOrganizationCode());
        account.setGrade(customer.getCustomerGrade());
        account.setIsAllowedClose(TrueOrFalse.TRUE.getLablCode());
        account.setIsCloseRtnCash(TrueOrFalse.TRUE.getLablCode());
        account.setIsRealname(customer.getIsRealname());
        account.setLastTxnTime(null);
        DateTime now = DateTime.now();
        Timestamp ts = new Timestamp(now.toDate().getTime());
        account.setLastUpdateTime(ts);//timestamp1
        account.setOpenChannel(managerLog.getTxnChannel());
        account.setOpenDate(now.toDate());
        //加密服务暂时不可用
        account.setEncryptedMsg(securityComponent.generateEncryptedMsg(account));// 账户关键数据密文报文
        account.setExpiredDate(now.plusYears(1000).toDate());// 有效期
        tInfoAccountMapper.insert(account);
        managerLog.setBeforeStatus(account.getStatus());
        TLogAccountManagement tLogAccountManagement = accountService.accountManInDb(managerLog, account, acceptOrg);
        //短信通知结算
        smsComponent.acountManagerNotice(customer, account, tLogAccountManagement, acceptOrg, null, plainInitPassword, customer.getInitPinKey(), managerLog.getBussinessType());
        return account;

    }

    //判断账户级别
    private String determineGrade(TInfoCustomer customer, String unifyId, String registerType) {
        String isRealName = customer.getIsRealname();
        // 根据客户实名状态设置客户账户等级
        if (Strings.isNullOrEmpty(isRealName)) {
            isRealName = CustomerRealname.CUSTOMER_REALNAME_FALSE.getCustomerRealnameCode();  //默认是非实名
            if (RegisterType.CUSTOMER_REGISTER_TYPE_SUTONG_CARD.getValue().equals(registerType)) { //注册类型为速通卡账户同步开户，
                isRealName = CustomerRealname.CUSTOMER_REALNAME_TRUE.getCustomerRealnameCode();  // 为初级实名
            }
            //身份证用户判断是否为初级实名
            if (CustomerIdType.CUSTOMER_ID_TYPE_IDCARD.getCode().equals(customer.getIdentifyType())) {
                if (PidUtil.isValidate18idCard(customer.getIdentifyNo())) {
                    isRealName = CustomerRealname.CUSTOMER_REALNAME_TRUE.getCustomerRealnameCode();
                }
                if (PidUtil.isValidate15idCard(customer.getIdentifyNo())) {
                    isRealName = CustomerRealname.CUSTOMER_REALNAME_TRUE.getCustomerRealnameCode();
                }

            }
            customer.setIsRealname(isRealName);
        }
        String grade;
        CustomerRealname customerRealname = CustomerRealname.explain(isRealName);
        switch (customerRealname) {
            case CUSTOMER_REALNAME_TRUE:
                grade = CustomerGrade.CUSTOMER_ACCOUNT_GRADE_PRIMARY.getCustomerGradeCode();
                break;//初级实名
            case CUSTOMER_REALNAME_FALSE:
                grade = CustomerGrade.CUSTOMER_ACCOUNT_GRADE_NOREALNAME.getCustomerGradeCode();
                break;//非实名
            case CUSTOMER_REALNAME_HIGH:
                grade = CustomerGrade.CUSTOMER_ACCOUNT_GRADE_SENIOR.getCustomerGradeCode();
                break;//高级实名
            default:
                grade = CustomerGrade.CUSTOMER_ACCOUNT_GRADE_NOREALNAME.getCustomerGradeCode();
        }
        return grade;
    }

    //创建积分账户
    private TInfoAccount createIntegralAccount(TInfoCustomer customer, ManagerLog managerLog) {
        String txnType = TxnInnerType.TXN_TYPE_104001.getTxnInnerType();
        //检查是否有积分账户
        TInfoAccount existed = tInfoAccountMapper.findIntegralAccountByCustomerNo(customer.getCustomerNo());
        if (existed != null) {
            throw new BizException(BussErrorCode.ERROR_CODE_200012.getErrorcode(), BussErrorCode.ERROR_CODE_200002.getErrordesc());
        }
        //受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(managerLog.getAcceptOrgCode() != null ? managerLog.getAcceptOrgCode() : customer.getRegisteOrgCode(),
                txnType, OrgType.ORG_TYPE_ORG.getOrgtype());

        TInfoAccount account = new TInfoAccount();
        account.setType(AccountType.INTEGRAL.getValue());
        String accountNo = sequenceGenerator.generateAccountNo(customer.getCustomerNo(), AccountType.INTEGRAL);
        String plainInitPassword = StringUtil.generateRandomString(6);
        //积分账户 暂不需要密码 加密服务器暂时没有，此处密码使用md5加密
       // String encryptPassword = Hashing.md5().hashString(plainInitPassword, Charsets.UTF_8).toString();;
//        String encryptPassword = pinkeyComponent.encryptPin(plainInitPassword, accountNo);
        account.setAccountNo(accountNo);
        account.setCustomerNo(customer.getCustomerNo());
        account.setAccountName(AccountName.DEFAULT_ACCOUNT_NAME_INTEGRAL.getAccountName());
        account.setApanage(OrgDefaultCode.GROUP_ORG_CODE.getOrgCode());// 资金账户，账户所属机构默认为北京速通
        account.setStatus(AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus());
       /* account.setPayPasswd(encryptPassword);
        account.setInitPasswd(encryptPassword);*/
        account.setInitPasswdModified(TrueOrFalse.FALSE.getLablCode());
        account.setAreaCode(customer.getAreaCode());
        account.setCityCode(customer.getCityCode());
        account.setOrganizationCode(customer.getOrganizationCode());
        account.setGrade(customer.getCustomerGrade());
        account.setIsAllowedClose(TrueOrFalse.TRUE.getLablCode());
        account.setIsCloseRtnCash(TrueOrFalse.TRUE.getLablCode());
        account.setIsRealname(customer.getIsRealname());
        account.setLastTxnTime(null);
        DateTime now = DateTime.now();
        Timestamp ts = new Timestamp(now.toDate().getTime());
        account.setLastUpdateTime(ts);//timestamp1
        account.setOpenChannel(managerLog.getTxnChannel());
        account.setOpenDate(now.toDate());
        //加密服务暂时不可用
        //account.setEncryptedMsg(securityComponent.generateEncryptedMsg(account));// 账户关键数据密文报文
        account.setExpiredDate(now.plusYears(1000).toDate());// 有效期
        tInfoAccountMapper.insert(account);
        managerLog.setBeforeStatus(account.getStatus());
        TLogAccountManagement tLogAccountManagement = accountService.accountManInDb(managerLog, account, acceptOrg);
        //短信通知结算
        smsComponent.acountManagerNotice(customer, account, tLogAccountManagement, acceptOrg, null, plainInitPassword, customer.getInitPinKey(), managerLog.getBussinessType());
        return account;

    }

    //创建脱机子账户
    private TInfoAccount createOffLineAccount(TInfoCustomer customer, TInfoAccount fundAccount, String cardNo, ManagerLog managerLog) {

        String txnType = TxnInnerType.TXN_TYPE_102000.getTxnInnerType();
        //受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(managerLog.getAcceptOrgCode() != null ? managerLog.getAcceptOrgCode() : customer.getRegisteOrgCode(),
                txnType, OrgType.ORG_TYPE_ORG.getOrgtype());
        //检查卡号是否存在
        TInfoCard card = cardService.findCardByCardNo(cardNo);
        //检查卡号是否绑定
        TInfoAccountCard existed = tInfoAccountCardMapper.findAccountCardByCardNo(cardNo);
        int closedExisted = tInfoAccountCardMapper.checkClosedCustomerByCardNo(cardNo);
        if (existed != null || closedExisted != 0) {
            throw new BizException(BussErrorCode.ERROR_CODE_200003.getErrorcode(), BussErrorCode.ERROR_CODE_200003.getErrordesc());
        }
        //查询有无脱机子账户
        List<TInfoAccount> offlineAccounts = tInfoAccountMapper.findAccountsAsListByCustomerNo(customer.getCustomerNo(), AccountType.INTEGRAL.getValue());
        if (offlineAccounts.size() > 0) {
            throw new BizException(BussErrorCode.ERROR_CODE_200006.getErrorcode(), BussErrorCode.ERROR_CODE_200006.getErrordesc());

        }
        managerLog.setInnerTxnType(txnType);
        //检查卡状态
        txnCheckService.checkCardManagerTxn(card, txnType);

        String accountNo = sequenceGenerator.generateAccountNo(customer.getCustomerNo(), AccountType.INTEGRAL);
        // 生成账户信息
        TInfoAccount account = new TInfoAccount();
        account.setAccountNo(accountNo);
        account.setApanage(OrgDefaultCode.GROUP_ORG_CODE.getOrgCode());// 账户所属机构默认为集团号百

        DateTime now = DateTime.now();
        account.setLastUpdateTime(now.toDate());
        account.setAccountName(AccountName.DEFAULT_ACCOUNT_NAME_INTEGRAL.getAccountName());
        account.setStatus(AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus());
        account.setAreaCode(customer.getAreaCode());
        account.setCityCode(customer.getCityCode());
        account.setOrganizationCode(customer.getOrganizationCode());
        account.setCustomerNo(customer.getCustomerNo());
        account.setGrade(customer.getCustomerGrade());
        account.setIsAllowedClose(TrueOrFalse.TRUE.getLablCode());
        account.setIsCloseRtnCash(TrueOrFalse.TRUE.getLablCode());
        account.setIsRealname(customer.getIsRealname());
        account.setLastTxnTime(null);
        account.setLastUpdateTime(now.toDate());
        account.setOpenChannel(managerLog.getTxnChannel());
        account.setOpenDate(now.toDate());
        account.setType(AccountType.INTEGRAL.getValue());
        //加密服务器暂时不可用
//        account.setEncryptedMsg(securityComponent.generateEncryptedMsg(account));// 账户关键数据密文报文
        account.setExpiredDate(now.plusYears(1000).toDate());// 有效期
        tInfoAccountMapper.insert(account);
        //脱机子账户，绑定卡和脱机账户，同时将卡和该客户下面的唯一资金账户绑定
        bindAccountCard(customer, card, fundAccount, account, acceptOrg, managerLog);
        managerLog.setBeforeStatus(account.getStatus());
        accountService.accountManInDb(managerLog, account, acceptOrg);
        return account;

    }


    //脱机子账户，绑定卡和脱机账户，同时将卡和该客户下面的唯一资金账户绑定
    private void bindAccountCard(TInfoCustomer customer, TInfoCard card, TInfoAccount fundAccount, TInfoAccount offlineAccount, TInfoOrg acceptOrg, ManagerLog managerLog) {

        if (fundAccount == null || offlineAccount == null) {
            throw new BizException(BussErrorCode.ERROR_CODE_200013.getErrorcode(), BussErrorCode.ERROR_CODE_200013.getErrordesc());
        }
        //插入账户卡绑定关系表
        TInfoAccountCard accountCard = new TInfoAccountCard();
        accountCard.setCustomerNo(customer.getCustomerNo());
        accountCard.setOfflineAccountNo(offlineAccount.getAccountNo());
        accountCard.setFundAccountNo(fundAccount.getAccountNo());
        accountCard.setBindingAcceptOrgCode(managerLog.getAcceptOrgCode());
        accountCard.setBindingAcceptTime(managerLog.getInputTime());
        accountCard.setBindingFlag(TrueFalseLabel.TRUE.getLablCode());
        accountCard.setBindingMehod(CardBingdingMethod.CARD_BINGDING_METHOD_MASTER.getCardBingdingMethodCode());
        accountCard.setBindingTime(offlineAccount.getLastTxnTime());
        accountCard.setBingdingAcceptUid(managerLog.getInputUid());
        accountCard.setCardNo(card.getInnerCardNo());
        accountCard.setCardType(card.getCardMediaType());
        accountCard.setAreaCode(acceptOrg.getAreaCode());
        accountCard.setCityCode(acceptOrg.getCityCode());
        tInfoAccountCardMapper.insert(accountCard);
        //插入OTA密码转换表
        //不需要
        //securityService.otaTxnPwdInsert(customer, fundAccount, card);

    }


    private void innerModfiyTxnPasswd(AccountInfo accountInfo, ManagerLog managerLog, boolean... ispwdcheck) {

        // 检查新，旧 密码是否一致,重置没有原密码,肯定通过这个判断
        if (accountInfo.getNewTxnPassword().equals(accountInfo.getTxnPassword())) {
            throw new BizException(BussErrorCode.ERROR_CODE_200042.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200042.getErrordesc());
        }
        managerLog.setInnerTxnType(TxnInnerType.TXN_TYPE_101090.getTxnInnerType());
        // 检查交易渠道和交易类型合法性
        txnCheckService.checkTxnType(managerLog.getInnerTxnType(), TxnType.TXN_MGM.getTxnCode());

        // 得到账户
        TInfoAccount account = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());
        txnCheckService.checkAccountManagerTxn(account, managerLog);

        TInfoCustomer customer = customerService.findValidCustomerByCustomerNo(account.getCustomerNo());

        //受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(managerLog.getAcceptOrgCode(),
                managerLog.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype());

        //校验密码
        //TbPosInfo tbPosInfo = securityService.getTbPosInfo(managerLog.getMerchantCode(), managerLog.getTerminalNo(), managerLog.getAcceptOrgCode());
        if (ispwdcheck.length > 0 && ispwdcheck[0]) {
            securityService.txnPwdCheck(customer, account, accountInfo);
        }
        //更新密码
        securityService.txnPwdUpdate(customer, account, accountInfo);

        accountService.accountManInDb(managerLog, account, acceptOrg);

    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = SubmitBizException.class)
    public void doModfiyTxnPasswd(AccountInfo accountInfo, ManagerLog managerLog) throws BizException, SubmitBizException {
        innerModfiyTxnPasswd(accountInfo, managerLog, true);
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void doModfiyTxnPasswdWithoutOldPwd(AccountInfo accountInfo, ManagerLog managerLog) throws BizException {
        innerModfiyTxnPasswd(accountInfo, managerLog);
    }


    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = SubmitBizException.class)
    public void doCheckTxnPasswd(AccountInfo accountInfo, ManagerLog managerLog) throws BizException, SubmitBizException {
        managerLog.setInnerTxnType(TxnInnerType.TXN_TYPE_101210.getTxnInnerType());
        // 检查交易渠道和交易类型合法性
        txnCheckService.checkTxnType(managerLog.getInnerTxnType(), TxnType.TXN_MGM.getTxnCode());

        // 得到账户
        TInfoAccount account = accountService.getAccount(accountInfo.getAccountKey(), accountInfo.getKeyType());
        txnCheckService.checkAccountManagerTxn(account, managerLog);

        TInfoCustomer customer = customerService.findValidCustomerByCustomerNo(account.getCustomerNo());

        //受理机构检查
        orgService.getValidOrg(managerLog.getAcceptOrgCode(),
                managerLog.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype());

        //校验密码
       // TbPosInfo tbPosInfo = securityService.getTbPosInfo(managerLog.getMerchantCode(), managerLog.getTerminalNo(), managerLog.getAcceptOrgCode());
   
        securityService.txnPwdCheck(customer, account, accountInfo);
    }


    @Transactional(readOnly = true, isolation = Isolation.DEFAULT)
    public void doAuthenticateCustomerIdentityApplyForSecurity(AccountInfo accountInfo, ManagerLog managerLog) throws BizException {

        TInfoAccount account = accountService.getAccount(accountInfo.getAccountKey(), accountInfo.getKeyType());
        TInfoCustomer customer = customerService.findValidCustomerByCustomerNo(account.getCustomerNo());
        txnCheckService.checkTxnType(TxnInnerType.CUSTOMER_REALNAME_REQUEST_TXN_TYPE.getTxnInnerType(), TxnType.TXN_MGM.getTxnCode());
        orgService.getValidOrg(managerLog.getAcceptOrgCode(), TxnInnerType.CUSTOMER_REALNAME_REQUEST_TXN_TYPE.getTxnInnerType(), null);
        //校验实名信息
        checkCustomerIdentityApply(customer, managerLog);
        accountService.tRealnameApplyInDb(customer, managerLog);
        //更新客户实名等级
    	   TInfoCustomer newCustomer=new TInfoCustomer();
    	   newCustomer.setIsRealname(CustomerRealname.CUSTOMER_REALNAME_HIGH.getCustomerRealnameCode());
    	   newCustomer.setName(managerLog.getName());
    	   newCustomer.setIdentifyNo(managerLog.getIdno());
    	   newCustomer.setIdentifyType(managerLog.getIdtype());
    	   newCustomer.setNationality(managerLog.getNationality());
    	   newCustomer.setProfession(managerLog.getProfession());
    	   newCustomer.setContactAddress(managerLog.getAddress());
        customerService.updateWithSynchronize(newCustomer, customer.getCustomerNo());
        newCustomer.setAreaCode(customer.getAreaCode());
        newCustomer.setCityCode(customer.getCityCode());
        newCustomer.setCustomerGrade(customer.getCustomerGrade());
        accountService.updateAccountRealnameInfo(newCustomer, customer.getCustomerNo());
    }

    public void lockAccount(TInfoAccount tInfoAccount, ManagerLog managerLog, TInfoOrg acceptOrg, TInfoCustomer customer) throws BizException {

        AccountType accountType = AccountType.explain(tInfoAccount.getType());
        switch (accountType) {
            case FUND:
                managerLog.setInnerTxnType(TxnInnerType.TXN_TYPE_101070.getTxnInnerType());
                break;
            case INTEGRAL:
                managerLog.setInnerTxnType(TxnInnerType.TXN_TYPE_102080.getTxnInnerType());
                // 如果脱机帐户未开通，直接过滤
                if (AccountStatus.ACCOUNT_STATUS_UNACTIVE.getStatus().equals(tInfoAccount.getStatus())) {
                    return;
                }
                break;
//    	case BOND:
//    		// 代金券账户
//    		managerLog.setInnerTxnType(TxnInnerType.TXN_TYPE_103070.getTxnInnerType());
//			
//    		break;
            default:
                throw new BizException(BussErrorCode.ERROR_CODE_200052.getErrorcode(), BussErrorCode.ERROR_CODE_200052.getErrordesc());

        }

        txnCheckService.checkAccountManagerTxn(tInfoAccount, managerLog);// 检查交易合法性
        managerLog.setBeforeStatus(tInfoAccount.getStatus());
        accountService.updateAccountStatus(tInfoAccount, AccountStatus.ACCOUNT_STATUS_LOCKED.getStatus(), DateTime.now().toDate());
        TLogAccountManagement tLogAccountManagement = accountService.accountManInDb(managerLog, tInfoAccount, acceptOrg);
        //短信通知结算
        smsComponent.acountManagerNotice(customer, tInfoAccount, tLogAccountManagement, acceptOrg, null, null, null, managerLog.getBussinessType());
    }

    public void unLockAccount(TInfoAccount tInfoAccount, ManagerLog managerLog, TInfoOrg acceptOrg, TInfoCustomer customer) throws BizException {

        AccountType accountType = AccountType.explain(tInfoAccount.getType());
        switch (accountType) {
            case FUND:
                managerLog.setInnerTxnType(TxnInnerType.TXN_TYPE_101080.getTxnInnerType());
                break;
            case INTEGRAL:
                managerLog.setInnerTxnType(TxnInnerType.TXN_TYPE_102090.getTxnInnerType());
                // 如果脱机帐户未开通，直接过滤
                if (AccountStatus.ACCOUNT_STATUS_UNACTIVE.getStatus().equals(tInfoAccount.getStatus())) {
                    return;
                }
                break;
//    	case BOND:
//    		// 代金券账户
//    		managerLog.setInnerTxnType(TxnInnerType.TXN_TYPE_103080.getTxnInnerType());
//    		break;
            default:
                throw new BizException(BussErrorCode.ERROR_CODE_200052.getErrorcode(), BussErrorCode.ERROR_CODE_200052.getErrordesc());

        }
        txnCheckService.checkAccountManagerTxn(tInfoAccount, managerLog);// 检查交易合法性
        managerLog.setBeforeStatus(tInfoAccount.getStatus());
        accountService.updateAccountStatus(tInfoAccount, AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus(), DateTime.now().toDate());
        TLogAccountManagement tLogAccountManagement = accountService.accountManInDb(managerLog, tInfoAccount, acceptOrg);
        //短信通知结算
        smsComponent.acountManagerNotice(customer, tInfoAccount, tLogAccountManagement, acceptOrg, null, null, null, managerLog.getBussinessType());


    }

    public void activeAccount(TInfoAccount tInfoAccount, TInfoCustomer tInfoCustomer, ManagerLog managerLog, TInfoAccount fundAccount, String initPinKey) {
        if (null == tInfoAccount) {
            // 指定账户不存在
            throw new BizException(null, BussErrorCode.ERROR_CODE_200013.getErrorcode());
        }
        String platInitPasswd = null;
        AccountType accountType = AccountType.explain(tInfoAccount.getType());
        switch (accountType) {
            case FUND:
                // 资金账户
                managerLog.setInnerTxnType(TxnInnerType.TXN_TYPE_101001.getTxnInnerType());
                platInitPasswd = StringUtil.generateRandomString(6);
                String encryptPasswd = Hashing.md5().hashString(platInitPasswd, Charsets.UTF_8).toString();
                tInfoAccount.setPayPasswd(encryptPasswd);
                tInfoAccount.setInitPasswd(platInitPasswd);
                break;
            case INTEGRAL:
                // 钱包账户
                managerLog.setInnerTxnType(TxnInnerType.TXN_TYPE_102011.getTxnInnerType());
                TInfoAccountCard tInfoAccountCard = tInfoAccountCardMapper.findAccountCardByAccountNo(tInfoAccount.getAccountNo());
                //检查卡号是否存在
                TInfoCard card = cardService.findCardByCardNo(tInfoAccountCard.getCardNo());
                securityComponent.otaTxnPwdInsert(tInfoCustomer, fundAccount, card);
                break;
//            case BOND:
//                // 代金券账户
//                managerLog.setInnerTxnType(TxnInnerType.TXN_TYPE_103001.getTxnInnerType());
//                tInfoAccount.setPayPasswd(null);
//                tInfoAccount.setInitPasswd(null);
//                break;
            default:
                throw new BizException(BussErrorCode.ERROR_CODE_200052.getErrorcode(), BussErrorCode.ERROR_CODE_200052.getErrordesc());
        }

        // 检查交易渠道和交易类型合法性
        txnCheckService.checkTxnType(managerLog.getInnerTxnType(), TxnType.TXN_MGM.getTxnCode());
        // 检查交易合法性
        txnCheckService.checkAccountManagerTxn(tInfoAccount, managerLog);
        //受理机构检查
        TInfoOrg acceptOrg = orgService.getValidOrg(managerLog.getAcceptOrgCode(),
                managerLog.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype());
        managerLog.setBeforeStatus(tInfoAccount.getStatus());
        accountService.activaAccountStatus(tInfoAccount);
        TLogAccountManagement tLogAccountManagement = accountService.accountManInDb(managerLog, tInfoAccount, acceptOrg);
        //短信通知结算
        smsComponent.acountManagerNotice(tInfoCustomer, tInfoAccount, tLogAccountManagement, acceptOrg, null, platInitPasswd, initPinKey, managerLog.getBussinessType());
    }

    private void checkCustomerIdentityApply(TInfoCustomer customer, ManagerLog managerLog) {
        if (tRealnameApplyMapper.getRealNameUsedIdNoCount(managerLog.getIdno()) >= realnameCount) {
            throw new BizException(BussErrorCode.ERROR_CODE_200139.getErrorcode(), BussErrorCode.ERROR_CODE_200139.getErrordesc());
        }
        List<TRealnameApply> listApply = tRealnameApplyMapper.queryAuthenticationApplyInfo(customer.getCustomerNo());
        if (!listApply.isEmpty()) {
            throw new BizException(BussErrorCode.ERROR_CODE_200140.getErrorcode(), BussErrorCode.ERROR_CODE_200140.getErrordesc());
        }

        TRealnameApply successRealname = tRealnameApplyMapper.queryAuthenticationSuccessInfo(customer.getCustomerNo());
        if (null != successRealname) {
            throw new BizException(BussErrorCode.ERROR_CODE_500103.getErrorcode(), BussErrorCode.ERROR_CODE_500103.getErrordesc());
        }
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void dofreezeAccount(AccountInfo accountInfo, ManagerLog managerLog) throws BizException {

        // 得到账户信息
        TInfoAccount account = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());
        // 得到客户信息
        TInfoCustomer customer = customerService.findValidCustomerByCustomerNo(account.getCustomerNo());

        managerLog.setInnerTxnType(TxnInnerType.TXN_TYPE_101050.getTxnInnerType());
        // 检查交易合法性
        txnCheckService.checkAccountManagerTxn(account, managerLog);

        // 检查商户交易合法性
        TInfoOrg acceptOrg = orgService.getValidOrg(managerLog.getAcceptOrgCode(),
                managerLog.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype());
        managerLog.setBeforeStatus(account.getStatus());

        accountService.updateAccountStatus(account, AccountStatus.ACCOUNT_STATUS_FROZEN.getStatus(),
                DateTime.now().toDate());

        TLogAccountManagement tLogAccountManagement = accountService.accountManInDb(managerLog, account, acceptOrg);
        //短信通知结算
        smsComponent.acountManagerNotice(customer, account, tLogAccountManagement, acceptOrg, null, null, null, managerLog.getBussinessType());
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void unfreezeAccount(AccountInfo accountInfo, ManagerLog managerLog) throws BizException {

        // 得到账户信息
        TInfoAccount account = accountService.getAccountForUpdate(accountInfo.getAccountKey(), accountInfo.getKeyType());
        // 得到客户信息
        TInfoCustomer customer = customerService.findValidCustomerByCustomerNo(account.getCustomerNo());

        managerLog.setInnerTxnType(TxnInnerType.TXN_TYPE_101060.getTxnInnerType());
        // 检查交易合法性
        txnCheckService.checkAccountManagerTxn(account, managerLog);

        // 检查商户交易合法性
        TInfoOrg acceptOrg = orgService.getValidOrg(managerLog.getAcceptOrgCode(),
                managerLog.getInnerTxnType(), OrgType.ORG_TYPE_ORG.getOrgtype());
        managerLog.setBeforeStatus(account.getStatus());

        accountService.updateAccountStatus(account, AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus(),
                DateTime.now().toDate());

        TLogAccountManagement tLogAccountManagement = accountService.accountManInDb(managerLog, account, acceptOrg);
        //短信通知结算
        smsComponent.acountManagerNotice(customer, account, tLogAccountManagement, acceptOrg, null, null, null, managerLog.getBussinessType());


    }
    
}
