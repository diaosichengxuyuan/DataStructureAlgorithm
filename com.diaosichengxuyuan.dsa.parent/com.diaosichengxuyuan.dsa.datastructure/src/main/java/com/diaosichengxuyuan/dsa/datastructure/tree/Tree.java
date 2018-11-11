package com.diaosichengxuyuan.dsa.datastructure.tree;

/**
 * 树
 *
 * @author liuhaipeng
 * @date 2018/11/9
 */
public interface Tree<E> {

    E find(E e);

    void insert(E e);

    void delete(E e);

    /**
     * 按层输出整棵树
     */
    String levelPrint();

    /**
     * 按树型输出整棵树
     */
    String treeStructurePrint();

    void clear();

    int size();
}
