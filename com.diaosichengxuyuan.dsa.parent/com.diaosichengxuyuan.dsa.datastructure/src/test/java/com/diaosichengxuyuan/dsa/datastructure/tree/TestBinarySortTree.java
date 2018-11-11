package com.diaosichengxuyuan.dsa.datastructure.tree;

import com.diaosichengxuyuan.dsa.datastructure.tree.impl.MyBinarySortTree;
import org.junit.Test;

/**
 * @author liuhaipeng
 * @date 2018/11/10
 */
public class TestBinarySortTree {

    @Test
    public void test() {
        BinarySortTree<Integer> tree = new MyBinarySortTree<>();
        tree.insert(Integer.valueOf(20));
        tree.insert(Integer.valueOf(10));
        tree.insert(Integer.valueOf(40));
        tree.insert(Integer.valueOf(5));
        tree.insert(Integer.valueOf(15));
        tree.insert(Integer.valueOf(25));
        tree.insert(Integer.valueOf(60));
        tree.insert(Integer.valueOf(2));
        tree.insert(Integer.valueOf(22));
        tree.insert(Integer.valueOf(30));
        tree.insert(Integer.valueOf(50));
        tree.insert(Integer.valueOf(70));

        System.out.println("构造树完成");
        System.out.println(tree.treeStructurePrint());
        System.out.println("树大小" + tree.size());

        System.out.println();
        System.out.println("前序遍历二叉树 " + tree.prePrint());
        System.out.println("中序遍历二叉树 " + tree.midPrint());
        System.out.println("后序遍历二叉树 " + tree.posPrint());

        System.out.println();
        Integer i = tree.find(Integer.valueOf(25));
        System.out.println("查找25 " + i);

        System.out.println();
        tree.delete(Integer.valueOf(40));
        System.out.println("删除40");
        System.out.println(tree.treeStructurePrint());

        System.out.println();
        tree.delete(Integer.valueOf(30));
        System.out.println("删除30");
        System.out.println(tree.treeStructurePrint());

        System.out.println();
        tree.delete(Integer.valueOf(20));
        System.out.println("删除20");
        System.out.println(tree.treeStructurePrint());

        System.out.println();
        tree.clear();
        System.out.println("清空树");
        System.out.println("树大小 " + tree.size());
    }
}
