package cn.com.huateng.account.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

public class TInfoCustomerBanks implements Serializable{
  
	private static final long serialVersionUID = 3821251164136914696L;
	@Getter
	@Setter
	private String customerNo;
	@Getter
	@Setter
    private String bankName;
    @Getter
	@Setter
    private String branchBankName;
    @Getter
	@Setter
    private String areaCode;
    @Getter
	@Setter
    private String cityCode;
    @Getter
	@Setter
    private String realName;
    @Getter
	@Setter
    private String accountNo;
    @Getter
	@Setter
    private Date updateTime;
    @Getter
	@Setter
	private String logoName;
    @Getter
   	@Setter
    private String areaName;
    @Getter
   	@Setter
    private String cityName;

  
}