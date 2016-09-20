package com.huateng.p3.account.daomappertest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.p3.account.base.BaseSpringTest;
import com.huateng.p3.account.persistence.TInfoAccountCardMapper;
import com.huateng.p3.account.persistence.TInfoCustomerMapper;
import com.huateng.p3.account.persistence.models.TInfoAccountCard;
import com.huateng.p3.account.persistence.models.TInfoCustomer;

/**
 * Created with IntelliJ IDEA.
 * User: wangshushuang
 * Date: 13-12-10
 * Time: 上午11:15
 * To change this template use File | Settings | File Templates.
 */
public class TInfoCustomerMapperTest extends BaseSpringTest {

    private static Logger log =  LoggerFactory.getLogger(TInfoCustomerMapperTest.class);

    @Autowired
    private TInfoCustomerMapper tInfoCustomerMapper;

    @Autowired
    private TInfoAccountCardMapper tInfoAccountCardMapper;

    @Before
    public void init(){
        log.debug("init start....");
        System.out.println("init start....");
    }

    @After
    public void destory(){

        log.debug("destory....");
        System.out.println("destory....");

    }

    /**
     * 测试插入方法
     */
    @Test
    public void testInsert(){

        String customerNo = "8630591000000360"; String passportId = "1";
        String isRealname = "4";    String name = "李雷";
        String isRequestCertificate = "1";String registerType = "1";
        String mobilePhone = "19800055500";
        String emailAddress = "13315953167@qq.com";    String loginId = "a";
        String nickName = "haha"; String pinkey = "B14F59ECED0D57FC3010E7682CA65167";
        String ivrPinkey = "11"; String queryPinkey = "bb"; String gender = "1"; String idType = "X";String idNo = "222879832749847293722";
        String homePhone = "12"; String officePhone = "bc"; String otherPhone = "cd"; String apanage = "001350000000000"; String areaCode = "457000";
        String cityCode = "452100";String contactAddress = "Fuzhou IDC"; String contactZipcode = "dd";String unitAddress = "ab";String unitZipcode = "cd";
        Long pwdErrNum = new Long("1"); Date lockTimeLimit = new Date(); String unitCode = "q"; String question = "b"; String answer = "c"; String activeAddress = "w";
       String activeCode = "b"; String activeStatus = "2"; Date registerTime = new Date(); String isClosed = "1";
                String acceptOrgCode = "113350000000001";String acceptUid = "FJCRM";String manager = "aa";Date lastSuccessLoginTime = new Date();
                String lastSuccessLoginIp = "192.168.92.165"; Date lastFailLoginTime = new Date() ; String lastFailLoginIp = "127.0.0.1";
                Date lastUpdateTime = new Date(); String status = "3"; Date closeTime = new Date(); Date activeTimeLimit = new Date();
                Date activeTime = new Date(); String grade = "A"; String type = "1"; String nationality = "a" ;String profession = "b";
                String certExpiryDate = "2013"; String blackLabel = "dd";
                byte[] portrait = "ab".getBytes();


            int oriDeleteCount =  tInfoCustomerMapper.deleteByPrimaryKey(customerNo);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TInfoCustomer tInfoCustomer = getTInfoCustomer(customerNo, passportId, isRealname, name, isRequestCertificate, registerType, mobilePhone, emailAddress, loginId, nickName, pinkey, ivrPinkey, queryPinkey, gender, idType, idNo, homePhone, officePhone, otherPhone, apanage, areaCode, cityCode, contactAddress, contactZipcode, unitAddress, unitZipcode, pwdErrNum, lockTimeLimit, unitCode,
                question, answer, activeAddress, activeCode, activeStatus, registerTime, isClosed, acceptOrgCode, acceptUid, manager, lastSuccessLoginTime, lastSuccessLoginIp, lastFailLoginTime, lastFailLoginIp, lastUpdateTime, status, closeTime, activeTimeLimit, activeTime, grade, type, nationality, profession, certExpiryDate, blackLabel, portrait) ;
            int insertCount = tInfoCustomerMapper.insert(tInfoCustomer);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

    }

    @Test
    public void testFindCustomerForUpdate(){

        //TODO 没有此方法的实现在TInfoCustomerMapper.xml中

    }

    @Test
    public void testFindCustomerByCustomerNo(){

        String customerNo = "8630591000000360"; String passportId = "1";
        String isRealname = "4";    String name = "李雷";
        String isRequestCertificate = "1";String registerType = "1";
        String productNo = "13315953167"; String mobilePhone = "19800055500";
        String emailAddress = "13315953167@qq.com";    String loginId = "a";
        String nickName = "haha"; String pinkey = "B14F59ECED0D57FC3010E7682CA65167";
        String ivrPinkey = "11"; String queryPinkey = "bb"; String gender = "1"; String idType = "X";String idNo = "222879832749847293722";
        String homePhone = "12"; String officePhone = "bc"; String otherPhone = "cd"; String apanage = "001350000000000"; String areaCode = "457000";
        String cityCode = "452100";String contactAddress = "Fuzhou IDC"; String contactZipcode = "dd";String unitAddress = "ab";String unitZipcode = "cd";
        Long pwdErrNum = new Long("1"); Date lockTimeLimit = new Date(); String unitCode = "q"; String question = "b"; String answer = "c"; String activeAddress = "w";
        String activeCode = "b"; String activeStatus = "2"; Date registerTime = new Date(); String isClosed = "1";
        String acceptOrgCode = "113350000000001";String acceptUid = "FJCRM";String manager = "aa";Date lastSuccessLoginTime = new Date();
        String lastSuccessLoginIp = "192.168.92.165"; Date lastFailLoginTime = new Date() ; String lastFailLoginIp = "127.0.0.1";
        Date lastUpdateTime = new Date(); String status = "3"; Date closeTime = new Date(); Date activeTimeLimit = new Date();
        Date activeTime = new Date(); String grade = "A"; String type = "1"; String nationality = "a" ;String profession = "b";
        String certExpiryDate = "2013"; String blackLabel = "dd";
        byte[] portrait = "ab".getBytes();


        int oriDeleteCount =  tInfoCustomerMapper.deleteByPrimaryKey(customerNo);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TInfoCustomer tInfoCustomer = getTInfoCustomer(customerNo,passportId, isRealname,name, isRequestCertificate, registerType, mobilePhone,  emailAddress,loginId, nickName, pinkey, ivrPinkey, queryPinkey, gender,idType, idNo, homePhone, officePhone, otherPhone, apanage,  areaCode, cityCode, contactAddress, contactZipcode, unitAddress,  unitZipcode, pwdErrNum, lockTimeLimit, unitCode,
                question,answer, activeAddress,activeCode,  activeStatus,registerTime, isClosed, acceptOrgCode, acceptUid, manager,  lastSuccessLoginTime, lastSuccessLoginIp, lastFailLoginTime, lastFailLoginIp, lastUpdateTime, status, closeTime, activeTimeLimit, activeTime, grade, type, nationality, profession, certExpiryDate,blackLabel,portrait) ;
        int insertCount = tInfoCustomerMapper.insert(tInfoCustomer);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

        tInfoCustomer = tInfoCustomerMapper.findCustomerByCustomerNo(customerNo);
        assertThat(customerNo,is(tInfoCustomer.getCustomerNo()));


    }

    @Test
    public void testFindCustomerByCustomerNoIgnoreStatus(){

        String customerNo = "8630591000000360"; String passportId = "1";
        String isRealname = "4";    String name = "李雷";
        String isRequestCertificate = "1";String registerType = "1";
        String mobilePhone = "19800055500";
        String emailAddress = "13315953167@qq.com";    String loginId = "a";
        String nickName = "haha"; String pinkey = "B14F59ECED0D57FC3010E7682CA65167";
        String ivrPinkey = "11"; String queryPinkey = "bb"; String gender = "1"; String idType = "X";String idNo = "222879832749847293722";
        String homePhone = "12"; String officePhone = "bc"; String otherPhone = "cd"; String apanage = "001350000000000"; String areaCode = "457000";
        String cityCode = "452100";String contactAddress = "Fuzhou IDC"; String contactZipcode = "dd";String unitAddress = "ab";String unitZipcode = "cd";
        Long pwdErrNum = new Long("1"); Date lockTimeLimit = new Date(); String unitCode = "q"; String question = "b"; String answer = "c"; String activeAddress = "w";
        String activeCode = "b"; String activeStatus = "2"; Date registerTime = new Date(); String isClosed = "1";
        String acceptOrgCode = "113350000000001";String acceptUid = "FJCRM";String manager = "aa";Date lastSuccessLoginTime = new Date();
        String lastSuccessLoginIp = "192.168.92.165"; Date lastFailLoginTime = new Date() ; String lastFailLoginIp = "127.0.0.1";
        Date lastUpdateTime = new Date(); String status = "3"; Date closeTime = new Date(); Date activeTimeLimit = new Date();
        Date activeTime = new Date(); String grade = "A"; String type = "1"; String nationality = "a" ;String profession = "b";
        String certExpiryDate = "2013"; String blackLabel = "dd";
        byte[] portrait = "ab".getBytes();


        int oriDeleteCount =  tInfoCustomerMapper.deleteByPrimaryKey(customerNo);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TInfoCustomer tInfoCustomer = getTInfoCustomer(customerNo,passportId, isRealname,name, isRequestCertificate, registerType, mobilePhone,  emailAddress,loginId, nickName, pinkey, ivrPinkey, queryPinkey, gender,idType, idNo, homePhone, officePhone, otherPhone, apanage,  areaCode, cityCode, contactAddress, contactZipcode, unitAddress,  unitZipcode, pwdErrNum, lockTimeLimit, unitCode,
                question,answer, activeAddress,activeCode,  activeStatus,registerTime, isClosed, acceptOrgCode, acceptUid, manager,  lastSuccessLoginTime, lastSuccessLoginIp, lastFailLoginTime, lastFailLoginIp, lastUpdateTime, status, closeTime, activeTimeLimit, activeTime, grade, type, nationality, profession, certExpiryDate,blackLabel,portrait) ;
        int insertCount = tInfoCustomerMapper.insert(tInfoCustomer);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

        tInfoCustomer = tInfoCustomerMapper.findCustomerByCustomerNoIgnoreStatus(customerNo);
        assertThat(customerNo,is(tInfoCustomer.getCustomerNo()));

    }

    @Test
    public void testFindCustomerByProductNo(){

        String customerNo = "8630591000000360"; String passportId = "1";
        String isRealname = "4";    String name = "李雷";
        String isRequestCertificate = "1";String registerType = "1";
        String mobilePhone = "19800055500";
        String emailAddress = "13315953167@qq.com";    String loginId = "a";
        String nickName = "haha"; String pinkey = "B14F59ECED0D57FC3010E7682CA65167";
        String ivrPinkey = "11"; String queryPinkey = "bb"; String gender = "1"; String idType = "X";String idNo = "222879832749847293722";
        String homePhone = "12"; String officePhone = "bc"; String otherPhone = "cd"; String apanage = "001350000000000"; String areaCode = "457000";
        String cityCode = "452100";String contactAddress = "Fuzhou IDC"; String contactZipcode = "dd";String unitAddress = "ab";String unitZipcode = "cd";
        Long pwdErrNum = new Long("1"); Date lockTimeLimit = new Date(); String unitCode = "q"; String question = "b"; String answer = "c"; String activeAddress = "w";
        String activeCode = "b"; String activeStatus = "2"; Date registerTime = new Date(); String isClosed = "1";
        String acceptOrgCode = "113350000000001";String acceptUid = "FJCRM";String manager = "aa";Date lastSuccessLoginTime = new Date();
        String lastSuccessLoginIp = "192.168.92.165"; Date lastFailLoginTime = new Date() ; String lastFailLoginIp = "127.0.0.1";
        Date lastUpdateTime = new Date(); String status = "3"; Date closeTime = new Date(); Date activeTimeLimit = new Date();
        Date activeTime = new Date(); String grade = "A"; String type = "1"; String nationality = "a" ;String profession = "b";
        String certExpiryDate = "2013"; String blackLabel = "dd";
        byte[] portrait = "ab".getBytes();


        int oriDeleteCount =  tInfoCustomerMapper.deleteByPrimaryKey(customerNo);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TInfoCustomer tInfoCustomer = getTInfoCustomer(customerNo,passportId, isRealname,name, isRequestCertificate, registerType, mobilePhone,  emailAddress,loginId, nickName, pinkey, ivrPinkey, queryPinkey, gender,idType, idNo, homePhone, officePhone, otherPhone, apanage,  areaCode, cityCode, contactAddress, contactZipcode, unitAddress,  unitZipcode, pwdErrNum, lockTimeLimit, unitCode,
                question,answer, activeAddress,activeCode,  activeStatus,registerTime, isClosed, acceptOrgCode, acceptUid, manager,  lastSuccessLoginTime, lastSuccessLoginIp, lastFailLoginTime, lastFailLoginIp, lastUpdateTime, status, closeTime, activeTimeLimit, activeTime, grade, type, nationality, profession, certExpiryDate,blackLabel,portrait) ;
        int insertCount = tInfoCustomerMapper.insert(tInfoCustomer);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

        tInfoCustomer = tInfoCustomerMapper.findCustomerByUnifyId(passportId);
        assertThat(customerNo,is(tInfoCustomer.getCustomerNo()));
        assertThat(passportId,is(tInfoCustomer.getUnifyId()));

    }

    @Test
    public void testFindCustomerByProductNoForUpdate(){

        String customerNo = "8630591000000360"; String passportId = "1";
        String isRealname = "4";    String name = "李雷";
        String isRequestCertificate = "1";String registerType = "1";
        String mobilePhone = "19800055500";
        String emailAddress = "13315953167@qq.com";    String loginId = "a";
        String nickName = "haha"; String pinkey = "B14F59ECED0D57FC3010E7682CA65167";
        String ivrPinkey = "11"; String queryPinkey = "bb"; String gender = "1"; String idType = "X";String idNo = "222879832749847293722";
        String homePhone = "12"; String officePhone = "bc"; String otherPhone = "cd"; String apanage = "001350000000000"; String areaCode = "457000";
        String cityCode = "452100";String contactAddress = "Fuzhou IDC"; String contactZipcode = "dd";String unitAddress = "ab";String unitZipcode = "cd";
        Long pwdErrNum = new Long("1"); Date lockTimeLimit = new Date(); String unitCode = "q"; String question = "b"; String answer = "c"; String activeAddress = "w";
        String activeCode = "b"; String activeStatus = "2"; Date registerTime = new Date(); String isClosed = "1";
        String acceptOrgCode = "113350000000001";String acceptUid = "FJCRM";String manager = "aa";Date lastSuccessLoginTime = new Date();
        String lastSuccessLoginIp = "192.168.92.165"; Date lastFailLoginTime = new Date() ; String lastFailLoginIp = "127.0.0.1";
        Date lastUpdateTime = new Date(); String status = "3"; Date closeTime = new Date(); Date activeTimeLimit = new Date();
        Date activeTime = new Date(); String grade = "A"; String type = "1"; String nationality = "a" ;String profession = "b";
        String certExpiryDate = "2013"; String blackLabel = "dd";
        byte[] portrait = "ab".getBytes();


        int oriDeleteCount =  tInfoCustomerMapper.deleteByPrimaryKey(customerNo);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TInfoCustomer tInfoCustomer = getTInfoCustomer(customerNo,passportId, isRealname,name, isRequestCertificate, registerType, mobilePhone,  emailAddress,loginId, nickName, pinkey, ivrPinkey, queryPinkey, gender,idType, idNo, homePhone, officePhone, otherPhone, apanage,  areaCode, cityCode, contactAddress, contactZipcode, unitAddress,  unitZipcode, pwdErrNum, lockTimeLimit, unitCode,
                question,answer, activeAddress,activeCode,  activeStatus,registerTime, isClosed, acceptOrgCode, acceptUid, manager,  lastSuccessLoginTime, lastSuccessLoginIp, lastFailLoginTime, lastFailLoginIp, lastUpdateTime, status, closeTime, activeTimeLimit, activeTime, grade, type, nationality, profession, certExpiryDate,blackLabel,portrait) ;
        int insertCount = tInfoCustomerMapper.insert(tInfoCustomer);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

        tInfoCustomer = tInfoCustomerMapper.findCustomerByUnifyIdForUpdate(passportId);
        assertThat(customerNo,is(tInfoCustomer.getCustomerNo()));
        assertThat(passportId,is(tInfoCustomer.getUnifyId()));



    }

    @Test
    public void testUpdateByPrimaryKeyWithBLOBs(){

//        8630591000000360
        String customerNo = "8630591000000361"; String passportId = "1";
        String isRealname = "4";    String name = "李雷";
        String isRequestCertificate = "1";String registerType = "1";
        String mobilePhone = "19800055500";
        String emailAddress = "13315953167@qq.com";    String loginId = "a";
        String nickName = "haha"; String pinkey = "B14F59ECED0D57FC3010E7682CA956";
        String ivrPinkey = "11"; String queryPinkey = "bb"; String gender = "1"; String idType = "X";String idNo = "222879832749847293722";
        String homePhone = "12"; String officePhone = "bc"; String otherPhone = "cd"; String apanage = "001350000000000"; String areaCode = "457000";
        String cityCode = "452100";String contactAddress = "Fuzhou IDC"; String contactZipcode = "dd";String unitAddress = "ab";String unitZipcode = "cd";
        Long pwdErrNum = new Long("1"); Date lockTimeLimit = new Date(); String unitCode = "q"; String question = "b"; String answer = "c"; String activeAddress = "w";
        String activeCode = "b"; String activeStatus = "2"; Date registerTime = new Date(); String isClosed = "1";
        String acceptOrgCode = "113350000000001";String acceptUid = "FJCRM";String manager = "aa";Date lastSuccessLoginTime = new Date();
        String lastSuccessLoginIp = "192.168.92.165"; Date lastFailLoginTime = new Date() ; String lastFailLoginIp = "127.0.0.1";
        Date lastUpdateTime = new Date(); String status = "3"; Date closeTime = new Date(); Date activeTimeLimit = new Date();
        Date activeTime = new Date(); String grade = "A"; String type = "1"; String nationality = "a" ;String profession = "b";
        String certExpiryDate = "2013"; String blackLabel = "dd";
        byte[] portrait = "ab".getBytes();
        int oriDeleteCount1 =  tInfoCustomerMapper.deleteByPrimaryKey(customerNo);
        log.debug("oriDeleteCount1=[" + oriDeleteCount1 + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TInfoCustomer tInfoCustomer = getTInfoCustomer(customerNo,passportId, isRealname,name, isRequestCertificate, registerType, mobilePhone,  emailAddress,loginId, nickName, pinkey, ivrPinkey, queryPinkey, gender,idType, idNo, homePhone, officePhone, otherPhone, apanage,  areaCode, cityCode, contactAddress, contactZipcode, unitAddress,  unitZipcode, pwdErrNum, lockTimeLimit, unitCode,
                question,answer, activeAddress,activeCode,  activeStatus,registerTime, isClosed, acceptOrgCode, acceptUid, manager,  lastSuccessLoginTime, lastSuccessLoginIp, lastFailLoginTime, lastFailLoginIp, lastUpdateTime, status, closeTime, activeTimeLimit, activeTime, grade, type, nationality, profession, certExpiryDate,blackLabel,portrait) ;
        int insertCount = tInfoCustomerMapper.insert(tInfoCustomer);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));
        passportId = "11";
        isRealname = "5";    name = "李雷1";
        isRequestCertificate = "6";registerType = "9";
        mobilePhone = "198000555001";
        emailAddress = "13315953167@qq.com1"; loginId = "a1";
        nickName = "haha1"; pinkey = "B14F59ECED0D57FC3010E7682CA65168";
        ivrPinkey = "111"; queryPinkey = "bb1";gender = "9"; idType = "Y";idNo = "222879832749847293723";
        homePhone = "121"; officePhone = "bc1"; otherPhone = "cd1"; apanage = "001350000000009"; areaCode = "457009";
        cityCode = "452109";contactAddress = "Fuzhou IDF"; contactZipcode = "dD";unitAddress = "aB";unitZipcode = "cF";
        pwdErrNum = new Long("11"); lockTimeLimit = new Date(); unitCode = "q1"; question = "b1"; answer = "c1"; activeAddress = "w1";
        activeCode = "b1"; activeStatus = "3";registerTime = new Date(); isClosed = "3";
        acceptOrgCode = "1133500000007";acceptUid = "FJCRQ";manager = "a3"; lastSuccessLoginTime = new Date();
        lastSuccessLoginIp = "192.168.92.166"; lastFailLoginTime = new Date() ;  lastFailLoginIp = "127.0.0.2";
        lastUpdateTime = new Date();  status = "7"; closeTime = new Date();  activeTimeLimit = new Date();
        activeTime = new Date(); grade = "B";  type = "3"; nationality = "b" ; profession = "w";
        certExpiryDate = "2014"; blackLabel = "f";
        portrait = "abc".getBytes();
        tInfoCustomer = getTInfoCustomer(customerNo, passportId, isRealname, name, isRequestCertificate, registerType, mobilePhone, emailAddress, loginId, nickName, pinkey, ivrPinkey, queryPinkey, gender, idType, idNo, homePhone, officePhone, otherPhone, apanage, areaCode, cityCode, contactAddress, contactZipcode, unitAddress, unitZipcode, pwdErrNum, lockTimeLimit, unitCode,
                question, answer, activeAddress, activeCode, activeStatus, registerTime, isClosed, acceptOrgCode, acceptUid, manager, lastSuccessLoginTime, lastSuccessLoginIp, lastFailLoginTime, lastFailLoginIp, lastUpdateTime, status, closeTime, activeTimeLimit, activeTime, grade, type, nationality, profession, certExpiryDate, blackLabel, portrait) ;

        int updateCount = tInfoCustomerMapper.updateByPrimaryKeyWithBLOBs(tInfoCustomer);
        assertThat(updateCount,is(1));

    }

    @Test
    public void testFindCustomerByCardNo(){

        Long recordNo = new Long(9488);
        String customerNo = "8630591000000361";
//        String offlineAccountNo = "8632898000000347";
        String offlineAccountNo = "86328980770003" +  new Random().nextInt(100000) ;
        String fundAccountNo = "8631898000000345";
        String cardNo = "8000000700003012" + new Random().nextInt(100000) ;
        String cardType = "6";
        String bindingFlag = "1";
        String bindingMehod = "1";
        Date bindingTime = new Date();
        Date unbindingTime = new Date();
        String bindingAcceptOrgCode = "103460100000001";
        String bingdingAcceptUid = "0000000000000338";
        Date bindingAcceptTime = new Date();
        String unbindingAcceptOrgCode = "123";
        String unbingdingAcceptUid = "234";
        Date unbindingAcceptTime = new Date();
        String areaCode = "460000";
        String cityCode = "460100";

        int oriDeleteCount =  tInfoAccountCardMapper.deleteByPrimaryKey(recordNo);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TInfoAccountCard tInfoAccountCard = getTInfoAccountCard(recordNo,customerNo,offlineAccountNo,fundAccountNo,
                cardNo, cardType,bindingFlag,bindingMehod,bindingTime,
                unbindingTime, bindingAcceptOrgCode,bingdingAcceptUid, bindingAcceptTime, unbindingAcceptOrgCode,
                unbingdingAcceptUid,  unbindingAcceptTime, areaCode,cityCode);
        int insertCount = tInfoAccountCardMapper.insertSelective(tInfoAccountCard);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

//        String customerNo = "8630591000000361";
        String passportId = "1";
        String isRealname = "4";    String name = "李雷";
        String isRequestCertificate = "1";String registerType = "1";
        String productNo = "133159531673334447777" + new Random().nextInt(100000) ; String mobilePhone = "19800055500";
        String emailAddress = "13315953167@qq.com";    String loginId = "a";
        String nickName = "haha"; String pinkey = "B14F59ECED0D57FC3010E7682CA956";
        String ivrPinkey = "11"; String queryPinkey = "bb"; String gender = "1"; String idType = "X";String idNo = "222879832749847293722";
        String homePhone = "12"; String officePhone = "bc"; String otherPhone = "cd"; String apanage = "001350000000000";
//        String areaCode = "457000";
//        String cityCode = "452100";
        String contactAddress = "Fuzhou IDC"; String contactZipcode = "dd";String unitAddress = "ab";String unitZipcode = "cd";
        Long pwdErrNum = new Long("1"); Date lockTimeLimit = new Date(); String unitCode = "q"; String question = "b"; String answer = "c"; String activeAddress = "w";
        String activeCode = "b"; String activeStatus = "2"; Date registerTime = new Date(); String isClosed = "1";
        String acceptOrgCode = "113350000000001";String acceptUid = "FJCRM";String manager = "aa";Date lastSuccessLoginTime = new Date();
        String lastSuccessLoginIp = "192.168.92.165"; Date lastFailLoginTime = new Date() ; String lastFailLoginIp = "127.0.0.1";
        Date lastUpdateTime = new Date(); String status = "3"; Date closeTime = new Date(); Date activeTimeLimit = new Date();
        Date activeTime = new Date(); String grade = "A"; String type = "1"; String nationality = "a" ;String profession = "b";
        String certExpiryDate = "2013"; String blackLabel = "dd";
        byte[] portrait = "ab".getBytes();
        int oriDeleteCount1 =  tInfoCustomerMapper.deleteByPrimaryKey(customerNo);
        log.debug("oriDeleteCount1=[" + oriDeleteCount1 + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TInfoCustomer tInfoCustomer = getTInfoCustomer(customerNo,passportId, isRealname,name, isRequestCertificate, registerType, mobilePhone,  emailAddress,loginId, nickName, pinkey, ivrPinkey, queryPinkey, gender,idType, idNo, homePhone, officePhone, otherPhone, apanage,  areaCode, cityCode, contactAddress, contactZipcode, unitAddress,  unitZipcode, pwdErrNum, lockTimeLimit, unitCode,
                question,answer, activeAddress,activeCode,  activeStatus,registerTime, isClosed, acceptOrgCode, acceptUid, manager,  lastSuccessLoginTime, lastSuccessLoginIp, lastFailLoginTime, lastFailLoginIp, lastUpdateTime, status, closeTime, activeTimeLimit, activeTime, grade, type, nationality, profession, certExpiryDate,blackLabel,portrait) ;
        int insertCount1 = tInfoCustomerMapper.insert(tInfoCustomer);
        log.debug("insertCount1=[" + insertCount1 + "]");
        assertThat(insertCount1,is(1));

        tInfoCustomer =  tInfoCustomerMapper.findCustomerByCardNo(cardNo);
        assertThat(customerNo,is(tInfoCustomer.getCustomerNo()));
        assertThat(acceptOrgCode,is(tInfoCustomer.getRegisteOrgCode()));

    }

    @Test
    public void testCheckClosedCustomerByProductNo(){

        //'X_'||#{productNo,jdbcType=VARCHAR}||'_'||to_char(sysdate,'yyyyMMdd')
        String customerNo = "8630591000000360"; String passportId = "1";
        String isRealname = "4";    String name = "李雷";
        String isRequestCertificate = "1";String registerType = "1";
        String productNo = "X_" + passportId + "_" + getDateFormat();
        log.debug("productNo=[" + productNo + "]");
        String mobilePhone = "19800055500";
        String emailAddress = "13315953167@qq.com";    String loginId = "a";
        String nickName = "haha"; String pinkey = "B14F59ECED0D57FC3010E7682CA65167";
        String ivrPinkey = "11"; String queryPinkey = "bb"; String gender = "1"; String idType = "X";String idNo = "222879832749847293722";
        String homePhone = "12"; String officePhone = "bc"; String otherPhone = "cd"; String apanage = "001350000000000"; String areaCode = "457000";
        String cityCode = "452100";String contactAddress = "Fuzhou IDC"; String contactZipcode = "dd";String unitAddress = "ab";String unitZipcode = "cd";
        Long pwdErrNum = new Long("1"); Date lockTimeLimit = new Date(); String unitCode = "q"; String question = "b"; String answer = "c"; String activeAddress = "w";
        String activeCode = "b"; String activeStatus = "2"; Date registerTime = new Date(); String isClosed = "1";
        String acceptOrgCode = "113350000000001";String acceptUid = "FJCRM";String manager = "aa";Date lastSuccessLoginTime = new Date();
        String lastSuccessLoginIp = "192.168.92.165"; Date lastFailLoginTime = new Date() ; String lastFailLoginIp = "127.0.0.1";
        Date lastUpdateTime = new Date(); String status = "3"; Date closeTime = new Date(); Date activeTimeLimit = new Date();
        Date activeTime = new Date(); String grade = "A"; String type = "1"; String nationality = "a" ;String profession = "b";
        String certExpiryDate = "2013"; String blackLabel = "dd";
        byte[] portrait = "ab".getBytes();


        int oriDeleteCount =  tInfoCustomerMapper.deleteByPrimaryKey(customerNo);
        log.debug("oriDeleteCount=[" + oriDeleteCount + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TInfoCustomer tInfoCustomer = getTInfoCustomer(customerNo,passportId, isRealname,name, isRequestCertificate, registerType, mobilePhone,  emailAddress,loginId, nickName, pinkey, ivrPinkey, queryPinkey, gender,idType, idNo, homePhone, officePhone, otherPhone, apanage,  areaCode, cityCode, contactAddress, contactZipcode, unitAddress,  unitZipcode, pwdErrNum, lockTimeLimit, unitCode,
                question,answer, activeAddress,activeCode,  activeStatus,registerTime, isClosed, acceptOrgCode, acceptUid, manager,  lastSuccessLoginTime, lastSuccessLoginIp, lastFailLoginTime, lastFailLoginIp, lastUpdateTime, status, closeTime, activeTimeLimit, activeTime, grade, type, nationality, profession, certExpiryDate,blackLabel,portrait) ;
        int insertCount = tInfoCustomerMapper.insert(tInfoCustomer);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));

        tInfoCustomer = tInfoCustomerMapper.checkClosedCustomerByUnifyId(passportId);
        assertThat(customerNo,is(tInfoCustomer.getCustomerNo()));
        assertThat(passportId,is(tInfoCustomer.getUnifyId()));


    }

    @Test
    public void testCloseCustomer(){

        String customerNo = "8630591000000361"; String passportId = "1";
        String isRealname = "4";    String name = "李雷";
        String isRequestCertificate = "1";String registerType = "1";
        String mobilePhone = "19800055500";
        String emailAddress = "13315953167@qq.com";    String loginId = "a";
        String nickName = "haha"; String pinkey = "B14F59ECED0D57FC3010E7682CA956";
        String ivrPinkey = "11"; String queryPinkey = "bb"; String gender = "1"; String idType = "X";String idNo = "222879832749847293722";
        String homePhone = "12"; String officePhone = "bc"; String otherPhone = "cd"; String apanage = "001350000000000"; String areaCode = "457000";
        String cityCode = "452100";String contactAddress = "Fuzhou IDC"; String contactZipcode = "dd";String unitAddress = "ab";String unitZipcode = "cd";
        Long pwdErrNum = new Long("1"); Date lockTimeLimit = new Date(); String unitCode = "q"; String question = "b"; String answer = "c"; String activeAddress = "w";
        String activeCode = "b"; String activeStatus = "2"; Date registerTime = new Date(); String isClosed = "1";
        String acceptOrgCode = "113350000000001";String acceptUid = "FJCRM";String manager = "aa";Date lastSuccessLoginTime = new Date();
        String lastSuccessLoginIp = "192.168.92.165"; Date lastFailLoginTime = new Date() ; String lastFailLoginIp = "127.0.0.1";
        Date lastUpdateTime = new Date(); String status = "3"; Date closeTime = new Date(); Date activeTimeLimit = new Date();
        Date activeTime = new Date(); String grade = "A"; String type = "1"; String nationality = "a" ;String profession = "b";
        String certExpiryDate = "2013"; String blackLabel = "dd";
        byte[] portrait = "ab".getBytes();
        int oriDeleteCount1 =  tInfoCustomerMapper.deleteByPrimaryKey(customerNo);
        log.debug("oriDeleteCount1=[" + oriDeleteCount1 + "]");
        log.debug("如果为0,则以前无值,如果为1,则以前有值");
        //进行插入操作
        TInfoCustomer tInfoCustomer = getTInfoCustomer(customerNo,passportId, isRealname,name, isRequestCertificate, registerType, mobilePhone,  emailAddress,loginId, nickName, pinkey, ivrPinkey, queryPinkey, gender,idType, idNo, homePhone, officePhone, otherPhone, apanage,  areaCode, cityCode, contactAddress, contactZipcode, unitAddress,  unitZipcode, pwdErrNum, lockTimeLimit, unitCode,
                question,answer, activeAddress,activeCode,  activeStatus,registerTime, isClosed, acceptOrgCode, acceptUid, manager,  lastSuccessLoginTime, lastSuccessLoginIp, lastFailLoginTime, lastFailLoginIp, lastUpdateTime, status, closeTime, activeTimeLimit, activeTime, grade, type, nationality, profession, certExpiryDate,blackLabel,portrait) ;
        int insertCount = tInfoCustomerMapper.insert(tInfoCustomer);
        log.debug("insertCount=[" + insertCount + "]");
        assertThat(insertCount,is(1));
        isClosed = "3";
        status = "7";
        passportId = "933159531673334447777" + new Random().nextInt(100000) ; mobilePhone = "198000555001";
        lastUpdateTime = new Date();
        closeTime = new Date();
        mobilePhone = "198000555001";
        loginId = "a1";
        emailAddress = "13315953167@qq.com1";

        tInfoCustomer = getTInfoCustomer(customerNo, passportId, isRealname, name, isRequestCertificate, registerType, mobilePhone, emailAddress, loginId, nickName, pinkey, ivrPinkey, queryPinkey, gender, idType, idNo, homePhone, officePhone, otherPhone, apanage, areaCode, cityCode, contactAddress, contactZipcode, unitAddress, unitZipcode, pwdErrNum, lockTimeLimit, unitCode,
                question, answer, activeAddress, activeCode, activeStatus, registerTime, isClosed, acceptOrgCode, acceptUid, manager, lastSuccessLoginTime, lastSuccessLoginIp, lastFailLoginTime, lastFailLoginIp, lastUpdateTime, status, closeTime, activeTimeLimit, activeTime, grade, type, nationality, profession, certExpiryDate, blackLabel, portrait) ;

        int updateCount = tInfoCustomerMapper.closeCustomer(tInfoCustomer);
        assertThat(updateCount,is(1));

    }

    public TInfoAccountCard getTInfoAccountCard(Long recordNo, String customerNo, String offlineAccountNo, String fundAccountNo,
                                                String cardNo, String cardType, String bindingFlag, String bindingMehod, Date bindingTime,
                                                Date unbindingTime, String bindingAcceptOrgCode, String bingdingAcceptUid, Date bindingAcceptTime, String unbindingAcceptOrgCode,
                                                String unbingdingAcceptUid, Date unbindingAcceptTime, String areaCode, String cityCode) {

        TInfoAccountCard tInfoAccountCard = new TInfoAccountCard();
        tInfoAccountCard.setRecordNo(recordNo);
        tInfoAccountCard.setCustomerNo(customerNo);
        tInfoAccountCard.setOfflineAccountNo(offlineAccountNo);
        tInfoAccountCard.setFundAccountNo(fundAccountNo);
        tInfoAccountCard.setCardNo(cardNo);
        tInfoAccountCard.setCardType(cardType);
        tInfoAccountCard.setBindingFlag(bindingFlag);
        tInfoAccountCard.setBindingMehod(bindingMehod);
        tInfoAccountCard.setBindingTime(bindingTime);
        tInfoAccountCard.setUnbindingTime(unbindingTime);
        tInfoAccountCard.setBindingAcceptOrgCode(bindingAcceptOrgCode);
        tInfoAccountCard.setBingdingAcceptUid(bingdingAcceptUid);
        tInfoAccountCard.setBindingAcceptTime(bindingAcceptTime);
        tInfoAccountCard.setUnbindingAcceptOrgCode(unbindingAcceptOrgCode);
        tInfoAccountCard.setUnbingdingAcceptUid(unbingdingAcceptUid);
        tInfoAccountCard.setUnbindingAcceptTime(unbindingAcceptTime);
        tInfoAccountCard.setAreaCode(areaCode);
        tInfoAccountCard.setCityCode(cityCode);

        return tInfoAccountCard;
    }

    public String getDateFormat(){

        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
//        System.out.println(f.format(new Date()));
        return f.format(new Date());


    }

    public TInfoCustomer getTInfoCustomer(String customerNo, String passportId, String isRealname, String name, String isRequestCertificate, String registerType, String mobilePhone, String emailAddress, String loginId, String nickName, String pinkey, String ivrPinkey, String queryPinkey, String gender, String idType, String idNo, String homePhone, String officePhone, String otherPhone, String apanage, String areaCode, String cityCode, String contactAddress, String contactZipcode, String unitAddress, String unitZipcode, Long pwdErrNum, Date lockTimeLimit, String unitCode, String question, String answer, String activeAddress, String activeCode, String activeStatus, Date registerTime, String isClosed, String acceptOrgCode, String acceptUid, String manager, Date lastSuccessLoginTime, String lastSuccessLoginIp, Date lastFailLoginTime, String lastFailLoginIp, Date lastUpdateTime, String status, Date closeTime, Date activeTimeLimit, Date activeTime, String grade, String type, String nationality, String profession, String certExpiryDate, String blackLabel, byte[] portrait) {
        TInfoCustomer tInfoCustomer = new TInfoCustomer();
        tInfoCustomer.setCustomerNo(customerNo);
        tInfoCustomer.setUnifyId(passportId);
        tInfoCustomer.setIsRealname(isRealname);
        tInfoCustomer.setName(name);
        tInfoCustomer.setIsRequestCertificate(isRequestCertificate);
        tInfoCustomer.setRegisterType(registerType);
        tInfoCustomer.setMobileNo(mobilePhone);
        tInfoCustomer.setEmailAddress(emailAddress);
        tInfoCustomer.setUserName(loginId);
        tInfoCustomer.setNickName(nickName);
        tInfoCustomer.setWebLoginPassword(pinkey);
        tInfoCustomer.setIvrPassword(ivrPinkey);
        tInfoCustomer.setAccountQueryPassword(queryPinkey);
        tInfoCustomer.setGender(gender);
        tInfoCustomer.setIdentifyType(idType);
        tInfoCustomer.setIdentifyNo(idNo);
        tInfoCustomer.setHomeTelephone(homePhone);
        tInfoCustomer.setOfficeTelephone(officePhone);
        tInfoCustomer.setOtherTelephone(otherPhone);
        tInfoCustomer.setApanage(apanage);
        tInfoCustomer.setAreaCode(areaCode);
        tInfoCustomer.setCityCode(cityCode);
        tInfoCustomer.setContactAddress(contactAddress);
        tInfoCustomer.setContactZipcode(contactZipcode);
        tInfoCustomer.setOrganizationAddress(unitAddress);
        tInfoCustomer.setOrganizationZipcode(unitZipcode);
        tInfoCustomer.setPwdErrCount(pwdErrNum);
        tInfoCustomer.setLockTimeLimit(lockTimeLimit);
        tInfoCustomer.setOrganizationCode(unitCode);
        tInfoCustomer.setQuestion(question);
        tInfoCustomer.setAnswer(answer);
        tInfoCustomer.setActiveAddress(activeAddress);
        tInfoCustomer.setActiveCode(activeCode);
        tInfoCustomer.setActiveStatus(activeStatus);
        tInfoCustomer.setRegisteDate(registerTime);
        tInfoCustomer.setIsClosingAccount(isClosed);
        tInfoCustomer.setRegisteOrgCode(acceptOrgCode);
        tInfoCustomer.setRegisteUid(acceptUid);
        tInfoCustomer.setManager(manager);
        tInfoCustomer.setLastSuccessLoginTime(lastSuccessLoginTime);
        tInfoCustomer.setLastSuccessLoginIp(lastSuccessLoginIp);
        tInfoCustomer.setLastFailLoginTime(lastFailLoginTime);
        tInfoCustomer.setLastFailLoginIp(lastFailLoginIp);
        tInfoCustomer.setLastUpdateTime(lastUpdateTime);
        tInfoCustomer.setCustomerStatus(status);
        tInfoCustomer.setCloseTime(closeTime);
        tInfoCustomer.setActiveTimeLimit(activeTimeLimit);
        tInfoCustomer.setActiveTime(activeTime);
        tInfoCustomer.setCustomerGrade(grade);
        tInfoCustomer.setCustomerType(type);
        tInfoCustomer.setNationality(nationality);
        tInfoCustomer.setProfession(profession);
        tInfoCustomer.setIdentifyExpiredDate(certExpiryDate);
        tInfoCustomer.setBlacklistFlag(blackLabel);
        tInfoCustomer.setPortrait(portrait);

        return tInfoCustomer;
    }



}
