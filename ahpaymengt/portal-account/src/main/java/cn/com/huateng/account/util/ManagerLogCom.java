package cn.com.huateng.account.util;

import cn.com.huateng.common.CodeGenerator;
import cn.com.huateng.common.TxnChannel;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.CustomerResultObject;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.service.HubCustomerQueryService;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: lizhongwei
 * Date: 14-9-24
 * Time: 下午3:12
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ManagerLogCom {
    private static  final Logger log = LoggerFactory.getLogger(ManagerLogCom.class);
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormat
            .forPattern("yyyyMMddHHmmss");
    @Autowired
    private HubCustomerQueryService hubCustomerQueryService;

    @Value("#{app.centerOrgCode}")
    private  String orgCode;


    public  ManagerLog getManagerLog(AccountInfo accountInfo){
        String inputTime = DATE_TIME_FORMAT.print(new Date().getTime());
        ManagerLog managerLog=new ManagerLog();
        managerLog.setAcceptDate(inputTime.substring(0, 8));
        managerLog.setAcceptTime(inputTime.substring(8));
        managerLog.setInputTime(DATE_TIME_FORMAT.parseDateTime(inputTime).toDate());
        managerLog.setCheckTime(DATE_TIME_FORMAT.parseDateTime(inputTime).toDate());
        managerLog.setAcceptTxnSeqNo(CodeGenerator.transSerialNo());
        managerLog.setTxnChannel(TxnChannel.CHANNEL_02.getValue());
        managerLog.setAcceptOrgCode(orgCode);
        Response<CustomerResultObject> customerResultObjectResponse;
        try{
            customerResultObjectResponse =  hubCustomerQueryService.queryCustomerInfo(accountInfo);
            if(customerResultObjectResponse.isSuccess()){
                managerLog.setBeforeStatus(customerResultObjectResponse.getResult().getStatus());
                return   managerLog;
            }else{
                log.error("fail to get Customer info cause by {}",customerResultObjectResponse.getErrorCode()+":"+customerResultObjectResponse.getErrorMsg());
                return null;
            }
        }catch (Exception e){
            log.error("fail to get Customer info cause by {}",e);
            return null;
        }
    }

    public  ManagerLog getManagerLog(){
        String inputTime = DATE_TIME_FORMAT.print(new Date().getTime());
        ManagerLog managerLog=new ManagerLog();
        managerLog.setAcceptDate(inputTime.substring(0, 8));
        managerLog.setAcceptTime(inputTime.substring(8));
        managerLog.setInputTime(DATE_TIME_FORMAT.parseDateTime(inputTime).toDate());
        managerLog.setCheckTime(DATE_TIME_FORMAT.parseDateTime(inputTime).toDate());
        managerLog.setAcceptTxnSeqNo(CodeGenerator.transSerialNo());
        managerLog.setTxnChannel(TxnChannel.CHANNEL_02.getValue());
        managerLog.setAcceptOrgCode(orgCode);
        return managerLog;
    }


}
