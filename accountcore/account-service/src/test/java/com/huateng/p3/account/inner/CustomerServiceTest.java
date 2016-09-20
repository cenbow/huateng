package com.huateng.p3.account.inner;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseAccountServiceSpringTest;

import com.huateng.p3.account.commonservice.CustomerService;
import com.huateng.p3.account.persistence.models.TInfoCustomer;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-23
 * Time: 下午6:09
 * To change this template use File | Settings | File Templates.
 */
public class CustomerServiceTest extends BaseAccountServiceSpringTest{

    @Autowired
    private CustomerService service;

    @Test
    public void findValidCustomerByCustomerNo(){
        String customerNo = "8630591000000350";
        TInfoCustomer tInfoCustomer = service.findValidCustomerByCustomerNo(customerNo);
        System.out.println("----findValidCustomerByCustomerNo-------"+tInfoCustomer.getIdentifyNo());
    }

    @Test
    public void findValidCustomerByProductNo() {
        String productNo = "18017846845";
//        TInfoCustomer customer = service.findValidCustomerByProductNo(productNo);
//        System.out.println("----customer-------"+customer.getIdentifyNo());
    }

    @Test
    public void findValidCustomerByProductNoForUpdate() {
        String productNo = "18017846845";
//        TInfoCustomer customer = service.findValidCustomerByProductNoForUpdate(productNo);
//        System.out.println("----customer-------"+customer.getIdentifyNo());
    }
}
