package com.huateng.p3.account.component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.enummodel.AccountStatus;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.enummodel.OrgDefaultCode;
import com.huateng.p3.account.common.enummodel.TrueOrFalse;
import com.huateng.p3.account.common.enummodel.TxnOutType;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.common.exception.SubmitBizException;
import com.huateng.p3.account.common.util.StringUtil;
import com.huateng.p3.account.persistence.TInfoAccountCardMapper;
import com.huateng.p3.account.persistence.TInfoAccountMapper;
import com.huateng.p3.account.persistence.TInfoOtapwdTransferMapper;
import com.huateng.p3.account.persistence.TbPosInfoMapper;
import com.huateng.p3.account.persistence.models.TInfoAccount;
import com.huateng.p3.account.persistence.models.TInfoCard;
import com.huateng.p3.account.persistence.models.TInfoCustomer;
import com.huateng.p3.account.persistence.models.TInfoOtapwdTransfer;
import com.huateng.p3.account.persistence.models.TbPosInfo;
import com.google.common.collect.Maps;
import com.huateng.p3.account.commonservice.AccountService;

/**
 * Created with IntelliJ IDEA.
 * User: JamesTang
 * Date: 13-12-6
 * Time: 上午10:02
 * To change this template use File | Settings | File Templates.
 */
@Component
public class SecurityComponent {

    @Autowired
    private TbPosInfoMapper tbPosInfoMapper;
    @Autowired
    private PinkeyComponent pinkeyComponent;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TInfoAccountMapper tInfoAccountMapper;
    
    @Autowired
    private TInfoAccountCardMapper tInfoAccountCardMapper;
    
    @Autowired
    private TInfoOtapwdTransferMapper tInfoOtapwdTransferMapper;
    
    @Value("${maxPasswdErrorTimes}")
    private int maxPasswdErrorTimes = 5;
    @Value("${passwdErrorLockHours}")
    private int passwdErrorLockHours = 12;


    @Autowired
    private SmsComponent smsHelper;

    private TbPosInfo findPinkey(String posCode, String merchantCode) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("posCode", posCode);
        param.put("merchantCode", merchantCode);
        TbPosInfo tbPosInfo = tbPosInfoMapper.findPinkeyFromEncrypt(param);
        return tbPosInfo;
    }

    public TbPosInfo getTbPosInfo(String merchantCode, String terminalNo, String acceptOrgCode) {
        TbPosInfo tbPosInfo = null;
        if (!OrgDefaultCode.PROVINCE_POSP_MERCHANT_CODE.getOrgCode().equals(merchantCode)) {
            tbPosInfo = findPinkey(terminalNo, merchantCode);//   posInfoDao.findPinkey(terminalNo, merchantCode);
        } else {
            tbPosInfo = findPinkey(acceptOrgCode, merchantCode);
        }
        if (null == tbPosInfo) {
            throw new BizException(BussErrorCode.ERROR_CODE_200032.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200032.getErrordesc());
        }
        return tbPosInfo;
    }

    /**
     * 交易密码校验
     *
     * @param customer
     * @param account
     * @param tbPosInfo
     * @param accountInfo
     * @return
     */
    public TInfoAccount txnPwdCheck(TInfoCustomer customer, TInfoAccount account , AccountInfo accountInfo) {

        // 校验交易密码
        try {
            return checkAccountTxnPassword(account, accountInfo);
        } catch (SubmitBizException ex) {
        	// 保存密码错误信息到数据库
            accountService.increaseTxnPasswdErrorNum(account);
            if (ex.getCode().equals(BussErrorCode.ERROR_CODE_200023.getErrorcode())) {               
                // 密码次数超限被锁定，需要下发短信给用户，短信内容：您的翼支付账户因支付密码输入错误次数过多，已被临时冻结三小时，如非本人授权操作，请致电4008011888。【翼支付】
                smsHelper.createAndSendSms(customer.getMobileNo(),TxnOutType.OUT_TXN_TYPE_F11022.getTxnOutType() );
                throw ex;
            }
            throw ex;
        }
    }
    
    /**
     * 更新交易密码
     *
     * @param customer
     * @param account
     * @param tbPosInfo
     * @param accountInfo
     * @return
     */
    public TInfoAccount txnPwdUpdate(TInfoCustomer customer, TInfoAccount account, AccountInfo accountInfo) {
      
        String afterStatus = AccountStatus.ACCOUNT_STATUS_NORMAL.getStatus();
        account.setStatus(afterStatus);
        Date now = new Date();
        account.setLastUpdateTime(now);
        String newPasswd = Hashing.md5().hashString(accountInfo.getNewTxnPassword(), Charsets.UTF_8).toString();//pinkeyComponent.convertPinBlock(posPinkey, posPinSeq, accountInfo.getNewTxnPassword(), account.getAccountNo(), StringUtil.fillLeft(Strings.isNullOrEmpty(accountInfo.getCardBin())?accountInfo.getAccountKey():accountInfo.getCardBin(), '0', 16));//密码加密
        if (newPasswd == null) {
			// 密码加密失败
			throw new BizException(BussErrorCode.ERROR_CODE_900000.getErrorcode(), BussErrorCode.ERROR_CODE_900000.getErrordesc());
		}
        account.setPayPasswd(newPasswd);
        account.setInitPasswdModified(TrueOrFalse.TRUE.getLablCode());
        tInfoAccountMapper.updateByPrimaryKeySelective(account);
        
        Map<String,String> param = Maps.newHashMap();
        param.put("posCode", OrgDefaultCode.VIRTUAL_TERMINAL_NO.getOrgCode());
        param.put("merchantCode", OrgDefaultCode.VIRTUAL_MERCHANT_CODE.getOrgCode());
        //String posPinkeyOcx = tbPosInfoMapper.findPinkeyFromEncrypt(param).getPosPinkey();
        
        // 更新OTA密码转换表
       /* List<String> cardNos = tInfoAccountCardMapper.findLinkCardNo(account.getAccountNo());
     	for (Iterator<String> i = cardNos.iterator(); i.hasNext();) {
			String cardNo = i.next();
			
			String flatPwd = pinkeyComponent.decryptTxnPasswd(encryptPasswd, account.getAccountNo());
			
			String otaPasswd = pinkeyComponent.encryptOtaPin(cardNo, flatPwd);
			
			String ocxPasswd = pinkeyComponent.convertOcx(posPinkeyOcx, flatPwd, cardNo);
			
			if( otaPasswd == null || ocxPasswd == null){
				// 密码加密失败
				throw new BizException(BussErrorCode.ERROR_CODE_900000.getErrorcode(), BussErrorCode.ERROR_CODE_900000.getErrordesc());
			}
			
			TInfoOtapwdTransfer tInfoOtapwdTransfer = new TInfoOtapwdTransfer();
			tInfoOtapwdTransfer.setCardNo(cardNo);
			tInfoOtapwdTransfer.setOtaPasswd(otaPasswd);
			tInfoOtapwdTransfer.setOcxPasswd(ocxPasswd);
			tInfoOtapwdTransferMapper.updateByPrimaryKeySelective(tInfoOtapwdTransfer);
			
     	}*/
        return account;
    }
    
    
    /**
     * 插入OTA密码转换表
     *
     * @param customer
     * @param fundAccount
     * @param card
     * @return
     */
    public TInfoAccount otaTxnPwdInsert(TInfoCustomer customer, TInfoAccount fundAccount ,TInfoCard card) {
        //获取密钥
        Map<String,String> param = Maps.newHashMap();
        param.put("posCode", OrgDefaultCode.VIRTUAL_TERMINAL_NO.getOrgCode());
        param.put("merchantCode", OrgDefaultCode.VIRTUAL_MERCHANT_CODE.getOrgCode());
        String posPinkeyOcx = tbPosInfoMapper.findPinkeyFromEncrypt(param).getPosPinkey();

        // 插入OTA密码转换表

        String flatPwd = pinkeyComponent.decryptTxnPasswd(fundAccount.getPayPasswd(), fundAccount.getAccountNo());

        String otaPasswd = pinkeyComponent.encryptOtaPin(card.getInnerCardNo(), flatPwd);

        String ocxPasswd = pinkeyComponent.convertOcx(posPinkeyOcx, flatPwd, card.getInnerCardNo());

        if( otaPasswd == null || ocxPasswd == null){
            // 密码加密失败
            throw new BizException(BussErrorCode.ERROR_CODE_900000.getErrorcode(), BussErrorCode.ERROR_CODE_900000.getErrordesc());
        }

        TInfoOtapwdTransfer tInfoOtapwdTransfer = new TInfoOtapwdTransfer();
        tInfoOtapwdTransfer.setCardNo(card.getInnerCardNo());
        tInfoOtapwdTransfer.setOtaPasswd(otaPasswd);
        tInfoOtapwdTransfer.setOcxPasswd(ocxPasswd);
        tInfoOtapwdTransferMapper.insert(tInfoOtapwdTransfer);


        return fundAccount;
    }



    public String generateEncryptedMsg(TInfoAccount account) {
        if (account.getBalance() < 0
                || account.getAvailableBalance() < 0) {
            throw new BizException(BussErrorCode.ERROR_CODE_200020.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200020.getErrordesc());
        }
        long a = account.getBalance() + account.getAvailableBalance();
        String bal = StringUtil.fillLeft("" + Math.abs(a), '0', 20);
        String accountNo = StringUtil.fillLeft(account.getAccountNo(), '0', 20);
        long c = Long.valueOf(bal);
        long d = Long.valueOf(accountNo);
        long e = c ^ d;
        // 加密机 String encryptMsg = pinkeyComponent.encryptedMsg(StringUtil.fillLeft("" + e, '0', 20));
        String encryptMsg =
                Hashing.md5().hashString(
                        StringUtil.fillLeft("" + e, '0', 20), Charsets.UTF_8).toString();
        if (encryptMsg == null) {
            throw new BizException(BussErrorCode.ERROR_CODE_900000.getErrorcode(),
                    BussErrorCode.ERROR_CODE_900000.getErrordesc());
        }
        return encryptMsg;
    }

    public static void main(String[] args) {
        TInfoAccount account = new TInfoAccount();
        account.setAccountNo("8630596000000109");
        account.setAvailableBalance(9999L);
        account.setWithdrawBalance(9999L);
        account.setBalance(9999L);
        if (account.getBalance() < 0
                || account.getAvailableBalance() < 0) {
            throw new BizException(BussErrorCode.ERROR_CODE_200020.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200020.getErrordesc());
        }
        long a = account.getBalance() + account.getAvailableBalance();
        String bal = StringUtil.fillLeft("" + Math.abs(a), '0', 20);
        String accountNo = StringUtil.fillLeft(account.getAccountNo(), '0', 20);
        long c = Long.valueOf(bal);
        long d = Long.valueOf(accountNo);
        long e = c ^ d;
        // 加密机 String encryptMsg = pinkeyComponent.encryptedMsg(StringUtil.fillLeft("" + e, '0', 20));
        String encryptMsg =
                Hashing.md5().hashString(
                        StringUtil.fillLeft("" + e, '0', 20), Charsets.UTF_8).toString();
        System.out.println(encryptMsg);
    }


    private TInfoAccount checkAccountTxnPassword(TInfoAccount account,
                                                 AccountInfo accountInfo) {
        if (account == null) {
            throw new BizException(BussErrorCode.ERROR_CODE_200013.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200013.getErrordesc());
        }
        if (account.getPasswdErrLockTime() != null
                && account.getPasswdErrLockTime().after(DateTime.now().toDate())) {
            // 密码错误次数超限被锁定，并尚未到解锁时间
            String fmtDatestr = new DateTime(account.getPasswdErrLockTime()).toString(DateTimeFormat.forPattern("yyyy年MM月dd日HH时mm分"));  //使用自定义的日期格式化当期日期
            throw new BizException(BussErrorCode.ERROR_CODE_200023.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200023.getErrordesc(),fmtDatestr);
        }
        /*pinkeyComponent.convertPinBlock(posPinkey,
        posPinSeq, accountInfo.getTxnPassword(), account.getAccountNo(),
        StringUtil.fillLeft(Strings.isNullOrEmpty(accountInfo.getCardBin())?accountInfo.getAccountKey():accountInfo.getCardBin() , '0', 16))*/
        if (!account.getPayPasswd().equals(accountInfo.getTxnPassword())) {// 密码错误
            Date lockTime = null;
            account.setPasswdErrNum(account.getPasswdErrNum() + 1);
            if (account.getPasswdErrNum() >= maxPasswdErrorTimes) {
                lockTime = new DateTime().plusHours(passwdErrorLockHours).toDate();
            }
            account.setPasswdErrLockTime(lockTime);
            
            if (lockTime == null) {
                throw new SubmitBizException(BussErrorCode.ERROR_CODE_200022.getErrorcode(),
                        BussErrorCode.ERROR_CODE_200022.getErrordesc(),String.valueOf(maxPasswdErrorTimes - account.getPasswdErrNum()));
            }
            String fmtDatestr = new DateTime(account.getPasswdErrLockTime()).toString(DateTimeFormat.forPattern("yyyy年MM月dd日HH时mm分"));  //使用自定义的日期格式化当期日期
            throw new SubmitBizException(BussErrorCode.ERROR_CODE_200023.getErrorcode(),
                    BussErrorCode.ERROR_CODE_200023.getErrordesc(),fmtDatestr);

        }
        // 密码正确，清空以前错误次数和日期
        account.setPasswdErrNum(Long.valueOf(0));
        account.setPasswdErrLockTime(null);
        return account;

    }
}
