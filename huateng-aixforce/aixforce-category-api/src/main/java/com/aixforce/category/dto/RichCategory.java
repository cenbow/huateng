/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.category.dto;

import com.aixforce.category.model.Category;
import lombok.Getter;
import lombok.Setter;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-09-07
 */
public class RichCategory extends Category {
    private static final long serialVersionUID = -3403328718945980767L;
    @Getter
    @Setter
    boolean hasChild;

    @Getter
    @Setter
    private int level;
}
