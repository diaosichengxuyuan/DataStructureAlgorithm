package com.diaosichengxuyuan.dsa.datastructure.tree;

import com.diaosichengxuyuan.dsa.datastructure.tree.impl.BPlusSortTree;
import org.junit.Test;

/**
 * @author liuhaipeng
 * @date 2018/11/16
 */
public class TestMultiwaySortTree {

    @Test
    public void test() {
        MultiwaySortTree<Integer> tree = new BPlusSortTree<>(4);
        tree.insert(Integer.valueOf(8));
        tree.insert(Integer.valueOf(20));
        tree.insert(Integer.valueOf(15));
        tree.insert(Integer.valueOf(7));
        tree.insert(Integer.valueOf(3));
        tree.insert(Integer.valueOf(40));
        tree.insert(Integer.valueOf(50));
        tree.insert(Integer.valueOf(6));
        tree.insert(Integer.valueOf(14));
        tree.insert(Integer.valueOf(2));
        tree.insert(Integer.valueOf(1));
        tree.insert(Integer.valueOf(9));
        tree.insert(Integer.valueOf(13));
        tree.insert(Integer.valueOf(10));
        tree.insert(Integer.valueOf(19));
        tree.insert(Integer.valueOf(18));
        tree.insert(Integer.valueOf(17));

        System.out.println("构造树完成，树大小：" + tree.size());

        System.out.println();
        System.out.println("构造树完成，按层打印");
        System.out.println(tree.levelPrint());

        System.out.println();
        System.out.println("构造树完成，按顺序打印：" + tree.sortPrint());

        tree.delete(Integer.valueOf(20));
        System.out.println();
        System.out.println("删除20，按层打印");
        System.out.println(tree.levelPrint());

        System.out.println();
        System.out.println("删除20，按顺序打印：" + tree.sortPrint());

        tree.delete(Integer.valueOf(18));
        System.out.println();
        System.out.println("删除18，按层打印");
        System.out.println(tree.levelPrint());

        System.out.println();
        System.out.println("删除18，按顺序打印：" + tree.sortPrint());

        System.out.println();
        System.out.println("当前树大小：" + tree.size());

        System.out.println();
        tree.clear();
        System.out.println("清空树，当前大小：" + tree.size());
    }
}
