package cn.com.huateng.account.service;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import base.BasePortalAccountSpringTest;
import cn.com.huateng.account.model.Register;
import cn.com.huateng.common.BussErrorCode;
import com.huateng.p3.component.Response;


/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-11
 * Time: 下午2:48
 * To change this template use File | Settings | File Templates.
 */
public class RegisterUserServiceTest extends BasePortalAccountSpringTest{

    private Logger log = LoggerFactory.getLogger(RegisterUserServiceTest.class);

    @Autowired
    private RegisterUserService registerUserService;

    @Test
    public void testAll() throws Exception {
      //  testRegisterTypeFail();
          //testRegister189TypeOk();
      //  testRegisterOtherTypeOk();
    }
//开户测试

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

    public void testRegisterTypeFail() throws Exception {
        Response<String> actual = new Response<String>();
        String unifyId = "";
        String idType = "";
        String name = "";
        String idNo = "";
        String gender = "";
        String question = "";
        String answer = "";
        String registerType = "";
        String areaCode = "";
        String cityCode = "";
      
        Register register = new Register(unifyId, idType, idNo, name, gender, question, answer, "111", null, null);
        actual = registerUserService.register(register);
        assertThat(actual.isSuccess(), is(false));
        log.info("参数为空，返回错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));

        //错误的手机号
        unifyId = "1293948444";
        name = "@sd";
        idType = "1";
        idNo = "123456";
        gender = "1";
        question = "1";
        answer = "1111";
        registerType = "1";
        Register register1 = new Register(unifyId, idType, idNo, name, gender, question, answer, "111", null, null);
        actual = registerUserService.register(register1);
        assertThat(actual.isSuccess(), is(false));
        log.info("不存在的手机号，返回错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));


        unifyId = "13511111111";  //正确的手机号
        name = "mc12里!@#";//格式正确不？
        idType = "1";//证件类型
        idNo = "123456";//证件号
        gender = "1";//性别
        question = "1";
        answer = "1111";
        registerType = "1";
        Register register2 = new Register(unifyId, idType, idNo, name, gender, question, answer, "111", null, null);
        actual = registerUserService.register(register2);
        assertThat(actual.isSuccess(), is(false));
        log.info("不存在的手机号，返回错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));

    }

    public void testRegister189TypeOk() throws Exception {
        Response<String> actual = new Response<String>();
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
        String unifyId = "";
        String idType = "";
        String name = "";
        String idNo = "";
        String gender = "";
        String question = "";
        String answer = "";
        String registerType = "";

        unifyId = "18159401086";  //正确的电信手机号
        name = "mcyusxli3";//格式正确？
        idType = "1";//证件类型
        idNo = "320192198801230294";//证件号
        gender = "2";//性别
        question = "1";

        answer = "1111";
        registerType = "1";
        Register register= new Register(unifyId, idType, idNo, name, gender, question, answer, "111", null, null);

        actual = registerUserService.register(register);
        assertThat(actual.isSuccess(), is(true));
        log.info("正确的手机号及参数信息，开户成功");

//        unifyId = "18918558908";  //正确的电信手机号
//        Register register1 = new Register(unifyId, idType, idNo, name, gender, question, answer, "111", null, null);
//        actual = registerUserService.register(register1);
//        assertThat(actual.isSuccess(), is(false));
//        log.info("正确的手机号及参数信息，开户成功后，再注册一次，返回错误码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));

    }

    public void testRegisterOtherTypeOk() throws Exception {
        Response<String> actual = new Response<String>();
        String unifyId = "";
        String idType = "";
        String name = "";
        String idNo = "";
        String gender = "";
        String question = "";
        String answer = "";
        String registerType = "";
        unifyId = "18159401088";
        name = "mcyusxli3";//格式正确？
        idType = "1";//证件类型
        idNo = "320192198801230294";//证件号
        gender = "2";//性别
        question = "1";
        answer = "1111";
        registerType = "8";//非电信号码
        Register register = new Register(unifyId, idType, idNo, name, gender, question, answer, "111",null, null);
        actual = registerUserService.register(register);
        assertThat(actual.isSuccess(), is(true));
        log.info("正确的非电信手机号及参数信息，开户成功");
    }

}

