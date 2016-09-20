/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.common.model;

import java.io.Serializable;

/**
 * all indexable entity should implement this interface
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-08-16
 */
public interface Indexable extends Serializable {
    Long getId();
}
