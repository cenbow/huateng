/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.container;

/**
 * Desc: 在Context中标识的常量
 * Author: dimzfw@gmail.com
 * Date: 8/18/12 5:24 PM
 */
public interface RenderConstants {

    //两个重要的ID
    String ID = "_ID_";
    String COMPONENT_ID = "_COMP_ID_";
    String COMPONENT_PATH = "_COMP_PATH_";
    String COMPONENT_NAME = "_COMP_NAME_";

    //用户、站点、实例的位置
    String USER = "_USER_";
    String SITE = "_SITE_";
    String SITE_INSTANCE = "_SITE_INSTANCE_";
    String PAGE = "_PAGE_";

    //数据位置
    String DATA = "_DATA_";

    //服务接口默认key
    String DEFAULT = "default";

    //渲染相关的行为、样式、父子关系等内容
    String BEHAVIOR = "_BEHAVIOR_";

    String STYLE = "_STYLE_";

    String MODE = "_MODE_";//css class
    String CHILDREN = "_CHILDREN_";
    String ROW_BOX_CHILDREN = "_ROW_CHILDREN_";
    String ROW_BOX_COMP_PATH = "common/container/rowbox"; // same as the path of "row_box" component in db.

    String HTML = "_HTML_";

}