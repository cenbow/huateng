package com.huateng.p3.hub.accountcore.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class AnnouncementVo implements Serializable {


	@Getter
    @Setter
	private String id;
    @Getter
    @Setter
	//标题
	private String title;
    @Getter
    @Setter
	//类别
	private String type;
    @Getter
    @Setter
	//优先级
	private String priority;
    @Getter
    @Setter
	//内容
	private String context;
    @Getter
    @Setter
	//开始日期
	private String startDate;
    @Getter
    @Setter
	//结束日期
	private String endDate;
    @Getter
    @Setter
	//录入人
	private String inputUid;
    @Getter
    @Setter
	//录入时间
	private String inputDate;
    @Getter
    @Setter
	//状态
	private String status;
    @Getter
    @Setter
	//点击链接
	private String url;
    @Getter
    @Setter
	//图片链接
	private String imgUrl;
	

	
}
