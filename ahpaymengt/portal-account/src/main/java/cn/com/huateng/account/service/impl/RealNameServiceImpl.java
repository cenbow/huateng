package cn.com.huateng.account.service.impl;

import cn.com.huateng.CommonUser;
import cn.com.huateng.account.model.TInfoCustomer;
import cn.com.huateng.account.service.RealNameService;
import cn.com.huateng.account.service.UserService;
import cn.com.huateng.account.util.ManagerLogCom;
import cn.com.huateng.common.BussErrorCode;
import cn.com.huateng.util.ValidationFieldUtil;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.enummodel.KeyType;
import com.huateng.p3.account.persistence.models.TRealnameApply;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.service.HubAccountManagerService;
import com.huateng.p3.hub.accountcore.service.HubAccountQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RealNameServiceImpl implements RealNameService {

    public Logger log = LoggerFactory.getLogger(RealNameServiceImpl.class);

    @Autowired
    private HubAccountManagerService hubAccountManagerService;

    @Autowired
    private HubAccountQueryService hubAccountQueryService;

    @Autowired
    private ManagerLogCom managerLogCom;

    @Autowired
    private UserService userService;

    /**
     * 证件实名查询
     *
     * @param commonUser 公共用户对象  必填
     * @return 如果操作成功, 则返回实名申请记录, 否则返回失败原因
     */
    @Override
    public Response<List<TRealnameApply>> queryRealnameAuthStatus(CommonUser commonUser) {
        // 必填判断
        Response<List<TRealnameApply>> response = new Response<List<TRealnameApply>>();
        if (commonUser == null || Strings.isNullOrEmpty(commonUser.getUnifyId())) {
            response.setErrorCode(BussErrorCode.ERROR_CODE_800001
                    .getErrorcode());
            return response;
        }
        try {
            //调用实名请求记录查询
                AccountInfo accountInfo=new AccountInfo();

                ManagerLog managerLog=managerLogCom.getManagerLog();
            Response<TInfoCustomer> tInfoCustomerResponse = userService.loadCustomer(commonUser);
            if (tInfoCustomerResponse.isSuccess()) {
                accountInfo.setAccountKey(tInfoCustomerResponse.getResult().getCustomerNo());
                accountInfo.setKeyType(KeyType.CUSTOMER);
            } else{
                log.error("fail to loadCustomer commonUser:{} cause by {}", new Object[]{commonUser, response.getErrorCode() + ":" + response.getErrorMsg()});
                return response;
            }

            response= hubAccountQueryService.queryRealnameAuthenticationStatusDetails(accountInfo,managerLog) ;
            if(response.isSuccess()){
                log.info("queryRealnameAuthenticationStatusDetails success accountInfo:{}", accountInfo);


            if (response.getResult()==null || response.getResult().size()==0) {//用户第一次申请实名制
                if (tInfoCustomerResponse.isSuccess()) {
                    TInfoCustomer tInfoCustomer = tInfoCustomerResponse.getResult();
                    if ("4".equals(tInfoCustomer.getIsRealname())) {
                        response.setErrorCode(BussErrorCode.ERROR_CODE_700203
                                .getErrorcode());
                        return response;
                    }
                } else {
                    return new Response<List<TRealnameApply>>(tInfoCustomerResponse.getErrorCode());
                }
                return response;
            }else { //用户非第一次申请实名制
                int erroNum = 0;
                List<TRealnameApply> realList=response.getResult();
                List<TRealnameApply> list = Lists.newArrayList();
                for(TRealnameApply real:realList){
                        String idNo= real.getIdNo();
                        String idNoDesc = idNo.substring(0, 2) + "*********" + idNo.substring(16);
                        real.setIdNo(idNoDesc);
                                list.add(real);
                if ("3".equals(real.getCheckFlag())) {//审核未通过
                    erroNum = erroNum + 1;
                }
                    }
                       response.setResult(list);
                //查看最后一次申请记录是否为"审核中"
                String flag = list.get(list.size() - 1).getCheckFlag();
                if ("1".equals(flag)) {
                    response.setErrorCode(BussErrorCode.ERROR_CODE_700202
                            .getErrorcode());
                    return response;
                } else if ("2".equals(flag)) {
                    response.setErrorCode(BussErrorCode.ERROR_CODE_700203
                            .getErrorcode());
                    return response;
                }
                if (tInfoCustomerResponse.isSuccess()) {
                    TInfoCustomer tInfoCustomer = tInfoCustomerResponse.getResult();
                    if ("4".equals(tInfoCustomer.getIsRealname())) {
                        response.setErrorCode(BussErrorCode.ERROR_CODE_700203
                                .getErrorcode());
                        return response;
                    }
                } else {
                    return new Response<List<TRealnameApply>>(tInfoCustomerResponse.getErrorCode());
                }
                //如果失败次数大于2次，不能再申请
                if (erroNum > 1) {
                    response.setErrorCode(BussErrorCode.ERROR_CODE_700201
                            .getErrorcode());
                    return response;
                }

            }
            }else{
                log.error("fail to queryRealnameAuthenticationStatusDetails accountInfo:{} cause by {}", new Object[]{accountInfo, response.getErrorCode() + ":" + response.getErrorMsg()});
                return response;
            }

            return response;
        } catch (Exception e) {
            log.error("queryRealnameAuthStatus fail productNo:{} ,  errorMessage:{}",commonUser.getUnifyId(),e.getMessage());
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            return response;
        }
    }


    /**
     * 实名认证
     *
     * @param commonUser 公共用户对象  必填
     * @param name       姓名 必填
     * @param idType     证件类型 必填
     * @param idNo       证件号码 必填
     * @param photo      照片(二进制流) 必填
     * @param certExpiryDate 证件有效期
     * @return 如果操作成功, 则返回SUCCESS, 否则返回失败原因
     */
    @Override
    public Response<String> identifyRealnameApply(CommonUser commonUser,
                                                  String name, String idType, String idNo, String photo,
                                                  String fileName, String loginIp,String nationality, String profession,
                                                  String certExpiryDate,String address) {

        // 必填判断
        if (Strings.isNullOrEmpty(commonUser.getUnifyId())
                || Strings.isNullOrEmpty(name) || Strings.isNullOrEmpty(idType) || Strings.isNullOrEmpty(idNo)
                || Strings.isNullOrEmpty(photo)) {
            return new Response<String>(false, BussErrorCode.ERROR_CODE_800001.getErrorcode());
        }


        Response<String> response = new Response<String>();
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey(commonUser.getUnifyId());
        accountInfo.setKeyType(KeyType.UNIFY);
        ManagerLog managerLog = managerLogCom.getManagerLog(accountInfo);

        managerLog.setName(name);
        managerLog.setPhoto(photo);
        managerLog.setIdno(idNo);
        managerLog.setIdtype(idType);
        managerLog.setNationality(nationality);
        managerLog.setProfession(profession);
        managerLog.setAddress(address);
        try {
            response = hubAccountManagerService.authenticateCustomerIdentityApplyForSecurity(accountInfo, managerLog);
            if (response.isSuccess()) {
                log.info("identifyRealnameApply success accountInfo:{} managerLog:{}", accountInfo, managerLog);

                return new Response<String>(true, "000000");
            } else {
                log.error("fail to identifyRealnameApply accountInfo:{} managerLog:{} cause by {}", new Object[]{accountInfo, managerLog, response.getErrorCode() + ":" + response.getErrorMsg()});
                return new Response<String>(false, response.getErrorCode() + ":" + response.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("fail to identifyRealnameApply cause by {}", e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            return response;
        }
    }
}
