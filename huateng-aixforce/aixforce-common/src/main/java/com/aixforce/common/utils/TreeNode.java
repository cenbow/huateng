/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 8/19/12 9:43 PM
 */
public class TreeNode {
    public Node current;
    public List<TreeNode> children;

    public TreeNode(Node current) {
        this.current = current;
    }

    public void addChild(TreeNode child) {
        if (this.children == null) {
            this.children = new ArrayList<TreeNode>();
        }
        this.children.add(child);
    }
}
