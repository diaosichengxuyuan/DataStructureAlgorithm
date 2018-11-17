package com.diaosichengxuyuan.dsa.datastructure.tree.impl;

import com.diaosichengxuyuan.dsa.datastructure.tree.util.TreeUtil;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 二叉排序树 算法请参考本人写的：https://github.com/diaosichengxuyuan/DataStructureAlgorithm/issues/1
 *
 * @author liuhaipeng
 * @date 2018/11/9
 */
public class GeneralBinarySortTree<E extends Comparable<E>> extends AbstractBinarySortTree<E> {

    private Node<E> root = null;

    private int size = 0;

    public GeneralBinarySortTree() {
    }

    @Override
    public E find(E e) {
        if(e == null) {
            throw new NullPointerException();
        }

        Node<E> node = findNode(e, root);
        if(node == null) {
            return null;
        }

        return node.element;
    }

    @Override
    public void insert(E e) {
        if(e == null) {
            throw new NullPointerException();
        }

        size++;

        if(root == null) {
            root = new Node(e);
            return;
        }

        Node<E> currentNode = root;
        Node<E> parentNode = null;
        while(currentNode != null) {
            parentNode = currentNode;
            if(e.compareTo(currentNode.element) >= 0) {
                currentNode = currentNode.rightChild;
            } else {
                currentNode = currentNode.leftChild;
            }
        }

        if(e.compareTo(parentNode.element) >= 0) {
            parentNode.rightChild = new Node<>(e);
        } else {
            parentNode.leftChild = new Node<>(e);
        }

    }

    @Override
    public void delete(E e) {
        if(e == null) {
            throw new NullPointerException();
        }

        Pair<Node, Node> pair = findNodeAndParentNode(e, root);
        //没有找到相同元素
        if(pair == null) {
            return;
        }

        //node肯定不为null
        Node deleteNode = pair.getKey();
        //若parentNode==null，证明查找到的需要删除的节点是root
        Node parentNode = pair.getValue();

        //下面的这段代码不是最精简的，但是很清晰
        //左右子树都为空
        if(deleteNode.leftChild == null && deleteNode.rightChild == null) {
            //待删除的是根节点
            if(parentNode == null) {
                root = null;
            }
            //待删除节点是其父节点的左孩子
            else if(isLeftChild(parentNode, deleteNode)) {
                parentNode.leftChild = null;
            }
            //待删除节点是其父节点的右孩子
            else {
                parentNode.rightChild = null;
            }
        }
        //左右子树都不为空，使用左子树替换
        else if(deleteNode.leftChild != null && deleteNode.rightChild != null) {
            findLeftmostNode(deleteNode.rightChild).leftChild = deleteNode.leftChild.rightChild;
            deleteNode.leftChild.rightChild = deleteNode.rightChild;

            if(parentNode == null) {
                root = deleteNode.leftChild;
            } else if(isLeftChild(parentNode, deleteNode)) {
                parentNode.leftChild = deleteNode.leftChild;
            } else {
                parentNode.rightChild = deleteNode.leftChild;
            }
        }
        //左子树为空，右子树不为空
        else if(deleteNode.leftChild == null) {
            //待删除的是根节点
            if(parentNode == null) {
                root = deleteNode.rightChild;
            }
            //待删除节点是其父节点的左孩子
            else if(isLeftChild(parentNode, deleteNode)) {
                parentNode.leftChild = deleteNode.rightChild;
            }
            //待删除节点是其父节点的右孩子
            else {
                parentNode.rightChild = deleteNode.rightChild;
            }
        }
        //右子树为空，左子树不为空
        else {
            //待删除的是根节点
            if(parentNode == null) {
                root = deleteNode.leftChild;
            }
            //待删除节点是其父节点的左孩子
            else if(isLeftChild(parentNode, deleteNode)) {
                parentNode.leftChild = deleteNode.leftChild;
            }
            //待删除节点是其父节点的右孩子
            else {
                parentNode.rightChild = deleteNode.leftChild;
            }
        }

        size--;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String levelPrint() {
        StringBuilder builder = new StringBuilder();

        if(root == null) {
            return builder.toString();
        }

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        Node<E> currentNode;
        Node<E> currentLastNode = root;
        Node<E> nextLastNode = null;

        while(!queue.isEmpty()) {
            currentNode = queue.poll();
            builder.append(currentNode.element + " ");

            if(currentNode.leftChild != null) {
                queue.offer(currentNode.leftChild);
                nextLastNode = currentNode.leftChild;
            }
            if(currentNode.rightChild != null) {
                queue.offer(currentNode.rightChild);
                nextLastNode = currentNode.rightChild;
            }

            if(currentNode.element.compareTo(currentLastNode.element) == 0) {
                currentLastNode = nextLastNode;
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    @Override
    public String treeStructurePrint() {
        StringBuilder builder = new StringBuilder();

        if(root == null) {
            return builder.toString();
        }

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        //树的深度，根的深度为1，如此计算，深度为h就是有h层
        int level = getDepth();
        //用来存储一共level个数组
        List<List<Node>> allList = new ArrayList<>(level);
        for(int i = 1; i <= level; i++) {
            List<Node> hList = new ArrayList<>((int)Math.pow(2, i - 1));
            allList.add(hList);
        }
        //第几个数组
        int h = 1;
        //出队列元素个数
        int outNodeCount = 0;

        while(!queue.isEmpty()) {
            Node<E> currentNode = queue.poll();
            outNodeCount++;

            if(outNodeCount < Math.pow(2, level - 1)) {
                if(currentNode.leftChild == null) {
                    queue.offer(new Node(null));
                } else {
                    queue.offer(currentNode.leftChild);
                }
                if(currentNode.rightChild == null) {
                    queue.offer(new Node(null));
                } else {
                    queue.offer(currentNode.rightChild);
                }
            }

            //第h个数组
            List<Node> hList = allList.get(h - 1);
            hList.add(currentNode);
            if(hList.size() >= Math.pow(2, h - 1)) {
                h++;
            }
        }

        //用4个字符的大小格式化
        int fixedLength = 4;

        //开始打印
        for(int i = 1; i <= allList.size(); i++) {
            List<Node> list = allList.get(i - 1);

            //起始位置为n，打印n个空格
            int start = (int)Math.pow(2, level - i) - 1;
            for(int j = 0; j < start; j++) {
                builder.append(TreeUtil.getFixedLengthString(fixedLength, " "));
            }

            for(int j = 0; j < list.size(); j++) {
                //元素
                Node<E> node = list.get(j);
                builder.append(TreeUtil
                    .getFixedLengthString(fixedLength, node.element == null ? "null" : node.element.toString()));
                //间隔为n，打印n-1个空格
                if(j != list.size() - 1) {
                    int interval = (int)Math.pow(2, level - i + 1);
                    for(int k = 0; k < interval - 1; k++) {
                        builder.append(TreeUtil.getFixedLengthString(fixedLength, " "));
                    }
                }
            }

            //换行
            if(i != allList.size()) {
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    @Override
    public String prePrint() {
        StringBuilder builder = new StringBuilder();
        prePrint(root, builder);
        return builder.toString();
    }

    @Override
    public String midPrint() {
        StringBuilder builder = new StringBuilder();
        midPrint(root, builder);
        return builder.toString();
    }

    @Override
    public String posPrint() {
        StringBuilder builder = new StringBuilder();
        posPrint(root, builder);
        return builder.toString();
    }

    /**
     * Node节点只有孩子，没有父亲，减少了维护成本，但是如果需要使用父节点就要自己用变量保存
     */
    private static class Node<E> {

        /**
         * 元素
         */
        private E element;

        /**
         * 左孩子
         */
        private Node<E> leftChild;

        /**
         * 右孩子
         */
        private Node<E> rightChild;

        private Node(E element) {
            this.element = element;
        }
    }

    private Node<E> findNode(E e, Node<E> node) {
        //用while循环而不使用递归，因为递归容易导致栈溢出
        while(node != null) {
            if(e.compareTo(node.element) == 0) {
                break;
            }

            if(e.compareTo(node.element) > 0) {
                node = node.rightChild;
            } else {
                node = node.leftChild;
            }
        }

        return node;
    }

    private Pair<Node, Node> findNodeAndParentNode(E e, Node<E> node) {
        Node<E> parentNode = null;
        //用while循环而不使用递归，因为递归容易导致栈溢出
        while(node != null) {
            if(e.compareTo(node.element) == 0) {
                break;
            }

            parentNode = node;

            if(e.compareTo(node.element) > 0) {
                node = node.rightChild;
            } else {
                node = node.leftChild;
            }
        }

        //没有相等的元素
        if(node == null) {
            return null;
        }

        //如果node==root，parentNode=null
        return new Pair<>(node, parentNode);
    }

    private boolean isLeftChild(Node<E> parentNode, Node<E> childNode) {
        if(parentNode == null || childNode == null) {
            return false;
        }

        if(parentNode.leftChild == null) {
            return false;
        }

        if(parentNode.leftChild.element.compareTo(childNode.element) == 0) {
            return true;
        }

        return false;
    }

    private Node<E> findLeftmostNode(Node<E> node) {
        Node<E> parentNode = null;
        while(node != null) {
            parentNode = node;
            node = node.leftChild;
        }

        return parentNode;
    }

    private void prePrint(Node<E> node, StringBuilder builder) {
        if(node == null) {
            return;
        }

        builder.append(node.element).append(" ");
        prePrint(node.leftChild, builder);
        prePrint(node.rightChild, builder);
    }

    private void midPrint(Node<E> node, StringBuilder builder) {
        if(node == null) {
            return;
        }

        midPrint(node.leftChild, builder);
        builder.append(node.element).append(" ");
        midPrint(node.rightChild, builder);
    }

    /**
     * 使用了递归，比较简单
     */
    private void posPrint(Node<E> node, StringBuilder builder) {
        if(node == null) {
            return;
        }

        posPrint(node.leftChild, builder);
        posPrint(node.rightChild, builder);
        builder.append(node.element).append(" ");
    }

    /**
     * 树的深度，根的深度为1
     */
    private int getDepth() {
        return getDepth(root);
    }

    private int getDepth(Node<E> node) {
        if(node == null) {
            return 0;
        }

        int leftDepth = getDepth(node.leftChild);
        int rightDepth = getDepth(node.rightChild);

        if(leftDepth > rightDepth) {
            return 1 + leftDepth;
        } else {
            return 1 + rightDepth;
        }
    }
}
