package com.diaosichengxuyuan.dsa.datastructure.tree;

/**
 * 多叉排序树
 *
 * @author liuhaipeng
 * @date 2018/11/16
 */
public interface MultiwaySortTree<E extends Comparable<E>> extends MultiwayTree<E> {

    /**
     * 按顺序输出整棵树
     */
    String sortPrint();
}
