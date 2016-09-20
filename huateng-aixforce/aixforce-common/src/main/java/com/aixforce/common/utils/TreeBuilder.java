/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.common.utils;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 8/19/12 9:42 PM
 */
public class TreeBuilder {
    private static Logger logger = LoggerFactory.getLogger(TreeBuilder.class);

    public static TreeNode buildTree(Iterable<? extends Node> nodes) {
        TreeNode root = null;
        boolean rootFound = false;
        Map<Long, TreeNode> nodeMap = Maps.newHashMap();

        for (Node node : nodes) {
            nodeMap.put(node.getId(), new TreeNode(node));
        }
        for (Node node : nodes) {
            if (node.getParentId() != null) {
                TreeNode parent = nodeMap.get(node.getParentId());
                if (parent == null) {
                    logger.error("find broken node,it's parent is null,[id:{}].need deleted,", node.getId());
                } else {
                    parent.addChild(nodeMap.get(node.getId()));
                }
            } else {
                if(rootFound){
                    logger.error("data error, find another root (id={}) ,previous found root(id={})",node.getId(),
                            root.current.getId());
                    throw new IllegalArgumentException("find more than one root");
                }
                root = nodeMap.get(node.getId());
                rootFound = true;
            }
        }
        checkArgument(root != null, "root not found!");
        return root;
    }


    public static String travel(TreeNode treeNode, NodeVisitor visitor) {

        //当前节点和所有儿子节点的内容
        Node currNode = treeNode.current;
        if (treeNode.children != null && treeNode.children.size() > 0) {
            for (TreeNode child : treeNode.children) {
                travel(child, visitor);
            }
        }
        return visitor.visit(currNode);
    }


//    public static String travel(TreeNode treeNode, NodeVisitor visitor) {
//
//        //当前节点和所有儿子节点的内容
//        Node currNode = treeNode.current;
//        StringBuilder stringBuilder = null;
//        if (treeNode.children != null && treeNode.children.size() > 0) {
//            if (visitor.needMerger()) {
//                stringBuilder = new StringBuilder();
//                for (TreeNode child : treeNode.children) {
//                    stringBuilder.append(travel(child, visitor));
//                }
//            } else {
//                for (TreeNode child : treeNode.children) {
//                    travel(child, visitor);
//                }
//            }
//        }
//        return visitor.visit(currNode, stringBuilder);
//    }
}
