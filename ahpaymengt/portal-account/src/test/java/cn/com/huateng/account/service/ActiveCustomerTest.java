package cn.com.huateng.account.service;

import cn.com.huateng.CommonUser;
import cn.com.huateng.base.BaseSpringTest;
import cn.com.huateng.common.BussErrorCode;
import com.huateng.p3.component.Response;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: xueweijie
 * Date: 14-9-23
 * Time: 下午4:23
 * To change this template use File | Settings | File Templates.
 */
public class ActiveCustomerTest extends BaseSpringTest {

    private Logger log = LoggerFactory.getLogger(ActiveCustomerTest.class);

    @Autowired
    private UserService userService;

    @Test
    public void testAll() throws Exception {

        //testActiveCustomerOk();
    }

    public void testActiveCustomerFail() throws Exception {
        Response<String> response = new Response<String>();
        CommonUser commonUser = new CommonUser();

        response = userService.activeCustomer(commonUser);
        log.info("激活失败，返回错误代码" + response.getErrorCode() + "错误原因：" + BussErrorCode.explain(response.getErrorCode()));

        commonUser.setUnifyId("1234342");
        response = userService.activeCustomer(commonUser);
        log.info("激活失败，返回错误码" + response.getErrorCode() + "错误原因：" + BussErrorCode.explain(response.getErrorCode()));


        commonUser.setUnifyId("110110110");
        response = userService.activeCustomer(commonUser);
        log.info("账户之前已经激活！");

    }

    public void testActiveCustomerOk() throws Exception {
        Response<String> response = new Response<String>();
        CommonUser commonUser = new CommonUser();
        commonUser.setUnifyId("13524124279");

        response = userService.activeCustomer(commonUser);
        log.info("输入正确，账户激活成功！");

    }
}
