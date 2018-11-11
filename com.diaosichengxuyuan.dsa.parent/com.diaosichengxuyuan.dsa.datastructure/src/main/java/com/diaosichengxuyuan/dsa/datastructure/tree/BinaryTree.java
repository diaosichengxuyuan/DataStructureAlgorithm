package com.diaosichengxuyuan.dsa.datastructure.tree;

/**
 * 二叉树
 *
 * @author liuhaipeng
 * @date 2018/11/9
 */
public interface BinaryTree<E> extends Tree<E> {

    /**
     * 前序遍历打印
     */
    String prePrint();

    /**
     * 中序遍历打印
     */
    String midPrint();

    /**
     * 后序遍历打印
     */
    String posPrint();
}
