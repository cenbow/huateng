package com.huateng.p3.account.manager;

import java.util.Date;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.enummodel.KeyType;
import com.huateng.p3.account.common.enummodel.TxnChannel;
import com.huateng.p3.account.util.encrypt.PinkeyEncrypt;

import com.huateng.p3.component.Response;

/**
 * Created with IntelliJ IDEA. User: huwenjie Date: 14-01-03 Time: 下午15:33 o
 * change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:account-manager.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class AccountManagerServiceTest extends TestCase {

	private Logger logger = LoggerFactory
			.getLogger(AccountManagerServiceTest.class);

	@Autowired
	AccountManagerService accountManagerService;

	@Test
	@Rollback(false)
    public void testAll() throws Exception {
        
//    	checkTxnPasswdOk();//支付密码鉴权
	//modfiyTxnPasswdWithoutOldPwdOk();//无需原密码修改密码正确参数  重置
   //modfiyTxnPasswdOk();//修改密码正确参数  
    	authenticateCustomerIdentityApplyForSecurity();//实名认证
		
		//freezeAccount();//冻结账户
		//unFreezeAccount();//账户解冻
    	
    }
	/*
	 * 冻结账户
	 */
	public void freezeAccount() throws Exception {
			
		ManagerLog managerLog = new ManagerLog();
		managerLog.setAcceptOrgCode("001999999020000");
		managerLog.setCheckTime(new Date());
		managerLog.setInputTime(new Date());
		
		AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey("13524124279");
        accountInfo.setKeyType(KeyType.UNIFY);
        
        
        Response<String> actual = accountManagerService.freezeAccount(accountInfo, managerLog);
        logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("成功" + actual.getResult());
		
	}
	
	/*
	 * 账户解冻
	 */
	public void unFreezeAccount() throws Exception {
			
		ManagerLog managerLog = new ManagerLog();
		managerLog.setAcceptOrgCode("001999999020000");
		managerLog.setCheckTime(new Date());
		managerLog.setInputTime(new Date());
		
		AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey("13524124279");
        accountInfo.setKeyType(KeyType.UNIFY);
        
        
        Response<String> actual = accountManagerService.unfreezeAccount(accountInfo, managerLog);
        logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("成功" + actual.getResult());
		
	}
	
	/*
	 * 实名认证
	 */
	public void authenticateCustomerIdentityApplyForSecurity() throws Exception {
		String s="/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQH/2wBDAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQH/wAARCAAIAAUDAREAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD++CgD/9k=";
		String ss="/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCACBAI0DASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwDyOO2E9yqLNGN5xufhF696c86W3moszSTZ2u5Xg9u/anFrmW1eYNxGw3uF5yRxz9M1SCEqyBSWZd2QPfrWKKE3SqWTftWTAYJ3HpSSp5eNrq2049xx93/PFWLi0MI5IwoB3Ke5Geh/EVKNLu5PLKYcOA4OR3qriM5kG5v4iD1U5FOhiZrlVVU/undnDVsR2uqalKsMVmsswPJjiXJPuRXb6D8I/EV60d1c20dspBP7xhu6cdDTYJHC38e26k2RBUXoFGB0qAW+n3FsXe4kjuB1Tb1+le6Q/BK2k2G91Ms2csI48Z9utbUXwd8KxqPMtXkI7mRh/Wpin1Kdj5h+ztvAiSR89Pl/pU32VoAJJI2Zj91McZ9/avrOz8D+G7BAttpUCAdzlj+prG1/4daBfwSPa2aRzjkgMQG9uvB9D/8ArBNtI2w/s3K0tOz6J+Z86x5uYgkvyuMHIHBqtdB0ZXTcpT1+6B3z9eK9ltvAOgXUDASSw3SvzCclifbJ/wD1d/WsnxL8NXgtPMtm3qTyw6JzwD6jnrWSaWq2/I7nCUnyy0qLr/N/XR9fU8vRY5YpHh24AOVf+H6Y/wD1VQmiLyAhW6c8dK6efwtqGiyrNNEXjdcAx4I59eeOn6c1nWsbPMxzgf3a1g2nbocNdRaU9pPdfr5ehRs9Oe5gZy20bsD61tRWTPGEIzxjNWobdY48vhVWoLvUo4Ex91TwPVqs5rla4s0t0BSUuw5NOl1e3to4oTE0rKvPTiqV4LiWEvI3k4+ZYOpPuSKy2hmYB5PlDdCe9C3GdBa30aoba4BS0kQjGB1GSv606zjjvFubG2Dq4j3OZByrAjP/AAHJ6f5OXKyRlt0oP91x/FUdvc+ROzxStGWGMjk/Ss+W4rm7r9xb3F0yKhBb7pxyBjoc/hXS+BPBd5rzRsbjbZ8b8MdwGB93j6VH8PvC0Hi6+R490aQMGuQTuwvsT64/WvofStJs9Js1trOLy4l98k00rKw0U/DnhHS/DtsI7WBS2cmRlBY8+uK6HOaaKUVQXI5JY4EMksiog6sxwB+NM+2QSWxuEkDRZIDDnJBxx688DHXtTrl4khczAeWRhgRnOeMY756Y71zNo/8AYuqCC5jItpi0luFUuY2PGOOpxxxnrweTmJSszpo0VUi+6/Hv8zVe9ubPSWubyJWnHSKEHqSAB1OTk/8A6+/nOqeKXstRuTczSx+Y2dgfIHbH4V6Dck4a5uflI+5H12Z47dWPTj1wO5PlPiPS5NaspZJwElVyp4IxjBx+dJxvox8t4OaS0evz6L+v+Doy6yLgpf23/HxH8xYdHXpz/npV2LxIGlw5BhIxhu4P9K4PTZ3i0G1lkO1oZGgkVhyc5Ofpx0pqalEj7g5bbgHjH+e1ZKDWw5VVOmlLdfl/wP1PUo10a8tBZSRqw5wGCkjJPr9a5DXvhpGJRd6NsXBJeN24Ye2BXPjUnWQOykxL8y442mul0bxpKjlZAPKTAJB4H6VtGVlY55Xk23ueW6zb6hHcm3cLDt+8TnA+nvWcIlik3b3lf+9J/D/u19Jzafo3iK2/062jkzxkg/zFcrrXwu0uWPdYObfGfVv5tWi1IseITyhFLOSTTbWGScNtYkL0yelXPEPh250W82TOZB/eO3p+BqrHcPaQIqna7ZZv6UMCLUJrO4KTW4eOZtxmj2AIpzxt5OePXvVFQS2K1dW2y3hKWJUDgtGm0MfYYrpfhr4KPirXiJ0mjtIV3udmQTkfLnj1oQkj1/4S+HhovhKG5df9K1D55PZVZtor0dTgY71Vt1UXEiRqAkQVVHpxVjcAalFMezqkZdyFC8kk4AFMiuYZ0LwypKoOCUYEZ/CsvU9Sto4nSeTbA2UcgZMh7ov8ie3Tr0zRJcDR7U6fMsccEQkkDfdkbIYjPp97PbnHriHPWx0wwzcFKWl312N6+NqEW5uiNsB3gsTgH1x3Pp+lZeowHU7BzKHEhXfBFnbsOOM+5756dB3JLTWI72IsuUkU4kjbqp9Ke82T/WqspLyIU50ZW2kv6/ruVI7sWlnDNq0yrKq7UVjnGOM9TuYjqfftznnmnlZ54vsE4jnnZ97Lt2hj3GP610MuGfzdo34278c49M1A77V5XIo5H3L+sRs7xvf5Jei/4J4/q0UtvLqdtHykEiXAyTleMf8As1YrvE0asCQzA7s9Owrt/E8Uf2rUwHxvtQ+B7EV52k4WM4529s9anqzFFmG8fzpBvyerL1H5VbF3HHKHXALDJ29MHtj0/lgVlbPutu5Jp6zlCgPJ7HP3aaQmel6Hr5jUQ52EHoDgVtNrfmAhywx6cf1rya01ZtxY7SdpXO4D+lX011yFZWAKbs4YfN7UyS/470aG9tRqdtLnyT+9Rj/Dz7da8qnmaSZn3EA8DHpXZ+IdduHtJV4QSjZjPXrXEE8+9WgNQWQY/LKh/H/61fSnwr0ZNF8GW7hYzNdf6QzKOxAwK+bodhbaBvPY9K+qtCXyPC+nL/ds4h/44KlgbGnhXtTLzmQk1mapq8WnS4khuCvHzrH8ufTJIyain1u10XS4kkfzJm42IRnnLevSqNv4kg1G3mLKCqkowPzZ/Adc1N7rQ0glFpzV0ybUNXexlhheLy7FvlMsZ5Ax0GPu/wCHTkVmzObZxdW0ebGR95gz7ZDqP1x2wDxj5a97JcQyPcTI0tlP/rIS24x/4fhxnj0NM0+RtMvoiCZ7Kc7IpO6ZPT256/T2xWTeuv8AX/APShBKknHXT5Pv6SX/AAxoXcCx2yatbzhrkjdiNcq6cZB74Hcn9OANOyu4rmVowg3RKPO+YHax/h9+hz/nGJeX8UEc1vpTFElJaWYNlE4HC9s/T1/KjYaqlrCsNsyKuck8FnPrWkL3OfEcns0nv0728/0+/RaHd+XC64IFZ17boEbbWauuHGTGWyOMGmHXI5G5kVT6Fga2PPPPfELgaxqCFiW+xsPw4rzUPvlZewYmvQPGV1u1a/cck22wY/CvNY5DubPSoS1Zd9EaYbanH8HUGqTu553MeeOaR5dwyDj1FRsecg00gHhyM7eDSm5MYkJyR6e9RK2Pbt1qlcXABKrz707EkVzdS3M2+R2Y9snpUJLGpGC4BVue4pMCqEaMTEe1fRtpqUepfD7TnguGDi1jQhDjc4XBU+vf+favnONYxJ8zlR9M123gnWja3s2ktOqW9whWNyAQrnB6noDjH5cVjPsdVCLi1Uut/wCtOx1TX11q+m3EUU+293YZyBnZ6Ke3Tt7dhxb+HXhq7Gr332iad4guWVmyNxI5/LNYUKSRa7bwxkI3IYA8sQM16ZY6pDa2KWl3fS2QRQRHD+7MhIzvLHJJOeR34NYU5u56VfDxdNu1l+XmrX0fY6lNFj8so6AgjBBxgisXQtEsL7TrpzHIYjcMEjMjABcAjIBxmqr+ObKz8iytbyedvMDSSS7XYJ1Izx1/Hgn2rqLTWIr2BZI2ODW+knfsedzSoQcU/is102/zPPvGOi3KWzCzGyFVOVTAx/nmuNsdCkt7YTCZmuiv/LT5lz3H+TXuFysd0hVsHPFcXqOk/Z7gmIYB5IrRHNfqzyJNI8UI4RLi4RVOAyy4x+Oa63TNIuTHEL65knlUA5Y5/M966+KxHD53CnmFUyEGBVWIbPMfFMPlXV2AT80J6/QV5qr4JFew+MrMPF5qrnscfSvIJ4jFPIjA8NStqUnoIXOakUlj2xTEVpHVVBZiegres9AuGHm3CbUxwpzz9aAuYkx2Jnp2xWex5J21Y1G3dbhuMjtVLy3HY0xAzc8DFN3H1pcMDnNG5vUUAa8gG4+oqxaStC/Ctzye3T0pHh2ysHlEfPTPP5Vaslt/N+aMuvTeTgfzFZtJqzNaVSVOXNHc63wm82p+KLeW6uWdIopHDE5JO0gD8yKZ418T3L+LtQQyobW1mNvaqpziNCQuOe+M5967f4RQx/bL+7RJGiiiEYdiQAxOeAxJ7HNeZ/ElCvjXUi0OGedn8wZ+ZSeKzjC+jOueIhFqVNa776art/wTPl1+edt8coidT0QEE/U16j8ONcvNQASXzTFGcEj7vQV47ptgt3dBR5m1tox717t4TsrTR9Ig8oRxFlVpC55zitrJKyOKU5Tk5Sep3aHamFYk+5qhduHU7/vD1pi3IfeTKuMZQLUHnb1Ck7mIzxTRLFDBY+wPpVK4c5OGp8s+0YBwfeqcsgzknmqMzN1GJJ4yjrkVxWoeEIbyYuMrz1XFdtO2W5PNQ4AXJqGaI5XTvDFppX7wJ5kuPvPgkVJeIrRMjH5SMVsXDZz6VjXgzGcZoEzz3U7NVuj5DyOu3PX3rO2If+Wj5962L9WFxtjiYS7TzGCST781Vt7Uzxsf+WwPzIep9h79aYlLuZ/k7vuuD+FAtHblUB/4DWh5ir8u1h6joaHuOfljwPqaZQ7UgItUmUruCueKksjG03mXTlIlUkBR97/ZA7VY8TWFxFr91i3cZkLAbT36VSZXEaxb+jb5M9d3t/Ks1JNFWZ7H8MNZEtnqn7tLZNyeXHGmABhu/wDEa5jx1bx3eqvL1fp+HPNctoXiC60e+E8PCvhHQAfMvpXTJqOnaveAPG8byZyJOOe9K6QWZg2UKW88Zj+YE8ECvW9EuIbnTIAQW2jr6cVwR8PeTK32ZwnHyg5xWnpU9zpbhJFbYvJ2jj0qriPQUkeOFUSBV2qcEkZqN5HcE7l6YyO1ZCaiHQAXA2r2yKl+2RJ+8V8kjnbySaLhY07lljQ73JOM81jtOWnz/DjtVe4nkvX2n5Y+oqC5nEICDlsc4p8wlEtO6ltxqGSTPAqsjvJT2RjUrUNitO+eBzVKWLcDxWoYO5FQvHxWiRLOJ1vS5JHSRHMeBhyPSufhl33yKDGiJ8pfbgf72PWvSLiAMvIrGm0W2e6huRADtYlx7+tJom1zJmsYdUtvOtsCZcjpy3sf8axDbOrFXUhgcEGu61KDZi8QjBXaSPXrmsw20OogSPOlvOvDliBv9OtMtM6FNWvEhklkbzZIoVXexzuYBV/p+tL/AGvL/pchtVkkmUy72AxubZ8oH0FSW5Aki2owiEkpO7vkcfh0qC3Km5UYI/0Vj8w77jz+tePdno2Lkd5pZuJ0l0exlcsuWktkO3c/b5eeBTkudAcp5mh2aKJfL3Im3OFcngAf7P6elZdrEcTZdtyxw8txyev60q25aQhSMm5ESknhU25b8cU+ZoOVPob0Vxpjwl4opIYVTIJkLfL8o/i/4FRDNbXMKujjYQrZI9axdySpFFFyot5JWI4XjLcfyq9pkRYjJwF8tiBzyR0q41JXtcmUIl+W2jjblP0qNbwJmNLfPPXNat5BnFURBwciuuKdzmbRH9odxgJs9xTBFuPNWPLxSqnStSLiRxhRxUyRE8mnJHnHpVlUwKpEMpumageMGtEp1qpMMGrJMuaPJxUCqEbpxWg6ZqlONtAIxdcY2lkyIwKPIGXPr6VzVzapcBCwUsueT6V2d5o76rp7SRAs0J3eWD14rHj0q3Z5FMuCpwdz4Ge4HBrF1FHc0UG9jo1/49k+n/soqmPvy/8AXBv/AEKiivLR6A6T/V3n/bKo/wDnl/18N/6CaKKTGiLT/wDj2k/68ZP51q6b/wAtfrD/ADaiiqj8SJlsdReVUb7tFFelE4mRn7lIPvfhRRV9SWWE6CrC9RRRVIljD0NUp+tFFUSQH7tZ9390/WiigDT8O/8AHvP/AL3+FcRq3+sP/XaT+lFFclU6aWx//9k=";
		
		
		ManagerLog managerLog = new ManagerLog();
		managerLog.setPhoto(ss);
		managerLog.setTxnChannel(TxnChannel.TXN_CHANNEL_PORTAL.getTxnCode()); 
		
		managerLog.setAddress("adress"); 
		
		managerLog.setAcceptOrgCode("001999999020000");
		AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey("18018354882");
        accountInfo.setKeyType(KeyType.UNIFY);
        Response<String> actual = accountManagerService.authenticateCustomerIdentityApplyForSecurity(accountInfo, managerLog);
        logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("成功" + actual.getResult());
        
		
	}
	public void checkTxnPasswdOk() throws Exception {
		
		ManagerLog managerLog = new ManagerLog();
		managerLog.setAcceptOrgCode("004310000040000");
		managerLog.setMerchantCode("222222222222222");
		managerLog.setTerminalNo("00431000");
		AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey("18018354882");
        accountInfo.setKeyType(KeyType.UNIFY);
        accountInfo.setTxnPassword("9F8751A660837FFB");
        Response<String> actual = accountManagerService.checkTxnPasswd(accountInfo, managerLog);
        logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("成功" + actual.getResult());
        
		
	}
	
public void modfiyTxnPasswdWithoutOldPwdOk() throws Exception {
		ManagerLog managerLog = new ManagerLog();
		managerLog.setAcceptOrgCode("004310000040000");
		managerLog.setMerchantCode("222222222222222");
		managerLog.setTerminalNo("00431000");
		AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey("18960828421");
        accountInfo.setKeyType(KeyType.UNIFY);
        PinkeyEncrypt en = new PinkeyEncrypt();
		String newPinkey = "111111"; //新密码
		String cardOrAccountNo ="0000018960828421";
		String newTxnPassword = en.encrypt(newPinkey, cardOrAccountNo);
        
        accountInfo.setNewTxnPassword(newTxnPassword);
        Response<String> actual = accountManagerService.modfiyTxnPasswdWithoutOldPwd(accountInfo, managerLog);
        logger.info("失败时，错误代码" + actual.getErrorCode() + "错误原因：" + BussErrorCode.explain(actual.getErrorCode()));      
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("成功" + actual.getResult());
	}

	/*public void modfiyTxnPasswdOk() throws Exception {
		ManagerLog managerLog = new ManagerLog();
		managerLog.setAcceptOrgCode("001999999020000");
		managerLog.setMerchantCode("222222222222222");
		managerLog.setTerminalNo("00431000");
		managerLog.setCheckTime(new Date());
		managerLog.setInputTime(new Date());
		AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountKey("13524124299");
        accountInfo.setKeyType(KeyType.UNIFY);
        PinkeyEncrypt en = new PinkeyEncrypt();
		String oldPinkey = "123456"; //旧密码
		String newPinkey = "111111"; //新密码
//		String cardOrAccountNo ="8631554000000108";//"0000018960828421";
//		String oldTxnPassword = en.encrypt(oldPinkey, cardOrAccountNo);
//		String newTxnPassword = en.encrypt(newPinkey, cardOrAccountNo);    
        accountInfo.setTxnPassword(oldPinkey);
        accountInfo.setNewTxnPassword(newPinkey);
        Response<String> actual = accountManagerService.modfiyTxnPasswd(accountInfo, managerLog);
        logger.info("失败时，错误代码" + actual.getErrorMsg() + "错误原因：" + BussErrorCode.explain(actual.getErrorMsg()));      
        Assert.assertEquals(true, actual.isSuccess());
        logger.info("成功" + actual.getResult());
	}*/
	


}
