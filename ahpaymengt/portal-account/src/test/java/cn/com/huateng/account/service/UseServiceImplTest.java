package cn.com.huateng.account.service;

import java.util.List;

import cn.com.huateng.CommonUser;
import cn.com.huateng.base.BaseSpringTest;
import cn.com.huateng.common.BussErrorCode;

import com.huateng.p3.account.persistence.models.TDictCode;
import com.huateng.p3.component.Response;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: John
 * Date: 14-9-24
 * Time: 上午10:43
 * To change this template use File | Settings | File Templates.
 */
public class UseServiceImplTest extends BaseSpringTest {
    private Logger log =  LoggerFactory.getLogger(UseServiceImplTest.class);
    @Autowired
    private UserService UseServiceImplTest;
    @Autowired
    private QueryQuestionsService QueryQuestionsTest;
    @Test
    public void testAll() throws Exception {

       //testFail1();
        //textFail2();
       // textsuccess();
        //sssss();

}

   public void testFail1 ()  {
       //号码不正确
       Response<String> actual = new Response<String>();
       CommonUser commonUser = new CommonUser();
       commonUser.setUnifyId("123234");
       String mobileNo = "";
       actual = UseServiceImplTest.mobilePhoneBinding(commonUser,mobileNo)  ;
       log.info("号码不正确" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));

   }
    public void textFail2(){
        //号码为空
        Response<String> actual = new Response<String>();
        CommonUser commonUser = new CommonUser();
        commonUser.setUnifyId("");
        String mobileNo = "";
        actual = UseServiceImplTest.mobilePhoneBinding(commonUser,mobileNo)  ;
        log.info("号码不能为空" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));

    }
    public void textsuccess(){
        //号码正确
        Response<String> actual = new Response<String>();
        CommonUser commonUser = new CommonUser();
        commonUser.setUnifyId("18018354882");
        String mobileNo = "11111111112";
        actual = UseServiceImplTest.mobilePhoneBinding(commonUser,mobileNo)  ;
        log.info("失败："+actual.getErrorCode()+":错误原因:" +BussErrorCode.explain(actual.getErrorCode()));

        log.info("成功："+actual.getResult());

    }
    public void sssss(){

        try {
        	Response<List<TDictCode>> res= QueryQuestionsTest.querySecurityQuestions();
            log.info("成功："+res.toString());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }
}

