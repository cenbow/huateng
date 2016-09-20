package cn.com.huateng.account.service;

import base.BasePortalAccountSpringTest;
import cn.com.huateng.CommonUser;
import cn.com.huateng.account.model.TInfoAccount;
import cn.com.huateng.account.model.TInfoCustomer;
import cn.com.huateng.common.BussErrorCode;
import com.aixforce.user.base.BaseUser;
import com.huateng.p3.component.Response;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-22
 * Time: 下午3:16
 * To change this template use File | Settings | File Templates.
 */
public class UserServiceTest extends BasePortalAccountSpringTest {
    private Logger log = LoggerFactory.getLogger(UserServiceTest.class);


    @Autowired
    PasswordMgmService passwordMgmService;

    @Autowired
    UserService userService;

    @Autowired
    RealNameService realNameService;


    @Test
    public void testAll() throws Exception {
        //login();//登录
       // loadAccounts();//账户信息查询
       // resetTxnPasswd();//支付密码重置
        //modfiyTxnPasswd();//支付密码修改
       // identifyRealnameApply();//实名认证
    }

    public void login(){

        String unifyId="13524124299";
        String password="123456";
        String loginIp="127.0.0.1";
        CommonUser cu=new CommonUser();
        cu.setUnifyId(unifyId);
        Response<TInfoCustomer> response= userService.login(unifyId, password, loginIp);
        log.info("失败时，错误代码" + response.getErrorCode() + "错误原因：" + BussErrorCode.explain(response.getErrorCode()));
        Assert.assertEquals(true, response.isSuccess());
        log.info("成功" + response.getResult());
    }

    public void loadAccounts(){

        CommonUser cu=new  CommonUser();
          cu.setUnifyId("123123123");
        Response<TInfoAccount> response=userService.loadAccounts(cu);
        log.info("失败时，错误代码" + response.getErrorCode() + "错误原因：" + BussErrorCode.explain(response.getErrorCode()));
        Assert.assertEquals(true, response.isSuccess());
        log.info("成功" + response.getResult().getAccountNo());
    }


    public void resetTxnPasswd(){
        // String txnDscpt, String inputUid, String inputTime, String checkUid, String checkTime

           CommonUser c=new   CommonUser();
        c.setUnifyId("13524124278");
        String newPasswprd="111111"  ;
        Response<String> response=passwordMgmService.resetTxnPasswd(c,newPasswprd);
        log.info("失败时，错误代码" + response.getErrorCode() + "错误原因：" + BussErrorCode.explain(response.getErrorCode()));

        log.info("成功" + response.getResult());
    }

    public void modfiyTxnPasswd(){

        CommonUser c=new   CommonUser();
        c.setUnifyId("13524124299");
        String newPasswprd="111111"   ;
                String oldPassword="123456";
        Response<String> response=passwordMgmService.modfiyTxnPasswd(c,newPasswprd,oldPassword);
        log.info("失败时，错误代码" + response.getErrorCode() + "错误原因：" + response.getErrorMsg());

        log.info("成功" + response.getResult());
    }

    public void identifyRealnameApply(){
        String name="伟哥";
        String idType="1";
        String idNo="1234567890";
        String photo="/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQH/2wBDAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQH/wAARCAAIAAUDAREAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD++CgD/9k=";
                String nationality="中国";
        String profession="软件";
                String address="地球";
        String fileName="";
        String loginIp="127.0.0.1";
        String certExpiryDate="";
        CommonUser c=new   CommonUser();
        c.setUnifyId("120120120");

        Response<String> response=   realNameService.identifyRealnameApply(c,name,idType,idNo,photo,fileName,loginIp,nationality,profession,certExpiryDate,address);
        log.info("失败时，错误代码" + response.getErrorCode() + "错误原因：" + response.getErrorMsg());

        log.info("成功" + response.getResult());
    }
}
