package com.diaosichengxuyuan.dsa.datastructure.tree.impl;

import com.diaosichengxuyuan.dsa.datastructure.tree.MultiwaySortTree;

/**
 * @author liuhaipeng
 * @date 2018/11/16
 */
public class AbstractMultiwaySortTree<E extends Comparable<E>> implements MultiwaySortTree<E> {

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
    public String sortPrint() {
        throw new UnsupportedOperationException();
    }
}
