package com.diaosichengxuyuan.dsa.datastructure.tree.impl;

import com.diaosichengxuyuan.dsa.datastructure.tree.BinarySortTree;

/**
 * @author liuhaipeng
 * @date 2018/11/9
 */
public class AbstractBinarySortTree<E extends Comparable<E>> implements BinarySortTree<E> {

    @Override
    public E find(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insert(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String levelPrint() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String treeStructurePrint() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String prePrint() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String midPrint() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String posPrint() {
        throw new UnsupportedOperationException();
    }
}
