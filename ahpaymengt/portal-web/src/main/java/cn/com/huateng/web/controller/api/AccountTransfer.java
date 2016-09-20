package cn.com.huateng.web.controller.api;

import cn.com.huateng.CommonUser;
import cn.com.huateng.common.BussErrorCode;
import cn.com.huateng.payment.service.AccountTransferService;
import com.aixforce.user.base.UserUtil;
import com.google.common.collect.ImmutableMap;
import com.huateng.p3.account.common.bizparammodel.TxnResultObject;
import com.huateng.p3.component.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-29
 * Time: 下午4:50
 * To change this template use File | Settings | File Templates.
 */
 @Controller
@RequestMapping("/api/accountTransfer")
public class AccountTransfer {
    public final static Logger log = LoggerFactory.getLogger(AccountTransfer.class);

    @Autowired(required = false)
    AccountTransferService accountTransferService;


    /**
     *转账检查
     * @param amount    账户余额
     * @param transferAmount  转账金额
     * @param transPhone     收款人手机号
     * @param field  转账说明
     * @return
     */
    @RequestMapping(value = "/transferCheck",method = RequestMethod.POST )
    @ResponseBody
    public Map<String,String> transferCheck(@RequestParam("amount")String amount,@RequestParam("transferAmount")String transferAmount,@RequestParam("transPhone")String transPhone,@RequestParam("field") String field){
        CommonUser user = UserUtil.getCurrentUser();
        Map<String,String> setResult;
        Response<TxnResultObject> response;
        try {

            response= accountTransferService.transferCheck(user.getUnifyId(),amount,transferAmount,transPhone,field);
            if(response.isSuccess()){
                setResult=ImmutableMap.of("status","success","msg","");
                return setResult;
            }else{
                log.error("transferCheck failed, errorCode={}, productNo={}",response.getErrorCode
                        (),user.getUnifyId());
                if("200130".equals(response.getErrorCode())){
                    setResult=ImmutableMap.of("status","fail","msg", "收款人账户不存在");
                }else{
                setResult=ImmutableMap.of("status","fail","msg", response.getErrorMsg());
                }
                return setResult;

            }
        }catch (Exception e){
            log.error("transferCheck failed, errorCode={}, unifyId={}",e.getMessage(),user.getUnifyId());
            setResult= ImmutableMap.of("status", "fail", "msg", "连接服务异常,请稍后再试");
            return setResult;
        }

    }


    /**
     *转账
     * @param amount    账户余额
     * @param transferAmount  转账金额
     * @param transPhone     收款人手机号
     * @param field  转账说明
     * @param pwd 支付密码
     * @return
     */
    @RequestMapping(value = "/transfer",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,String> transfer(@RequestParam("amount")String amount,@RequestParam("transferAmount")String transferAmount,@RequestParam("transPhone")String transPhone,@RequestParam("field") String field,@RequestParam("pwd")String pwd){
        CommonUser user = UserUtil.getCurrentUser();
        Map<String,String> setResult;
        Response<TxnResultObject> response;
        try {

            response= accountTransferService.transfer(user.getUnifyId(),amount,transferAmount,transPhone,field,pwd);
            if(response.isSuccess()){
                setResult=ImmutableMap.of("status","success","msg","");
                return setResult;
            }else{
                log.error("transfer failed, errorCode={}, productNo={}",response.getErrorCode
                        (),user.getUnifyId());
                setResult=ImmutableMap.of("status","fail","msg",response.getErrorMsg());
                return setResult;

            }
        }catch (Exception e){
            log.error("transfer failed, errorCode={}, unifyId={}",e.getMessage(),user.getUnifyId());
            setResult= ImmutableMap.of("status", "fail", "msg", "连接服务异常,请稍后再试");
            return setResult;
        }

    }

    @RequestMapping(value = "/number/check", method = RequestMethod.POST)
    public String checkTransferAccountByProduct(@RequestParam("amount") String amount,
                                                @RequestParam("transferAmount") String transferAmount,@RequestParam("transPhone") String transPhone,
                                                @RequestParam("field") String field,
                                                Map<String, Object> context) {

        String url="/layout/sutongpay/payment/transConfirmation";


            context.put("amount",amount);
            context.put("transferAmount",transferAmount);
            context.put("transPhone",transPhone);
        context.put("field",field);
            return url;




    }

    }
