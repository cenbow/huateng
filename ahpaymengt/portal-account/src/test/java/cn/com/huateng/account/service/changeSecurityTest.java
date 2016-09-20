package cn.com.huateng.account.service;

import cn.com.huateng.CommonUser;
import cn.com.huateng.base.BaseSpringTest;
import cn.com.huateng.common.BussErrorCode;
import com.google.common.base.Objects;
import com.aixforce.user.base.BaseUser;

import com.huateng.p3.account.common.bizparammodel.*;
import com.huateng.p3.component.Response;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: xueWeiJie
 * Date: 14-9-19
 * Time: 下午3:19
 * To change this template use File | Settings | File Templates.
 */
public class changeSecurityTest extends BaseSpringTest {

    private Logger log = LoggerFactory.getLogger(changeSecurityTest.class);

    @Autowired
    private UserService userService;

    @Test
    public void testAll() throws Exception {
        //testChangeSecurityQuestionAndAnswerOk();
        //testChangeSecurityQuestionAndAnswerOk();
    }

    public void testChangeSecurityQuestionAndAnswerFail() throws Exception {
        Response<String> response = new Response<String>();
        CommonUser commonUser = new CommonUser();

        String securityQuestion = "";
        String securityAnswer = "";

        response =  userService.changeSecurityQuestionAndAnswer(commonUser,securityQuestion,securityAnswer) ;
        //assertThat(response.isSuccess(), is(false));
        log.info("参数为空，返回错误代码" + response.getErrorCode() + "错误原因：" + BussErrorCode.explain(response.getErrorCode()));

        commonUser.setUnifyId("123321");
        securityQuestion = "2";
        securityAnswer = "King";

        response = userService.changeSecurityQuestionAndAnswer(commonUser,securityQuestion,securityAnswer);
        log.info("该号码不存在，返回错误码" + response.getErrorCode() + "错误原因：" +BussErrorCode.explain(response.getErrorCode()) );


    }

   public void testChangeSecurityQuestionAndAnswerOk()  throws Exception {
       Response<String> response = new Response<String>();
       CommonUser commonUser = new CommonUser();
       commonUser.setUnifyId( "110110110");
       String securityQuestion = "1";
       String securityAnswer = "XWJ";

       response = userService.changeSecurityQuestionAndAnswer(commonUser,securityQuestion,securityAnswer);

       log.info("输入正确，修改密保问题成功！");
   }



}
