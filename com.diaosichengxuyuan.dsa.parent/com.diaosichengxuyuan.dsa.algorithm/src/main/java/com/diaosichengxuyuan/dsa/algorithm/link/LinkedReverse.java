package com.diaosichengxuyuan.dsa.algorithm.link;

import java.util.Stack;

/**
 * 链表中间反转 例如：5->2->0->9->10->15->1->55->20 m=3  n=7 结果：5->2->1->15->10->9->0->55->20
 *
 * @author liuhaipeng
 * @date 2020/9/6
 */
public class LinkedReverse {

    public static void main(String[] args) {
        ListNode listNode5 = new ListNode(5);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode0 = new ListNode(0);
        ListNode listNode9 = new ListNode(9);
        ListNode listNode10 = new ListNode(10);
        ListNode listNode15 = new ListNode(15);
        ListNode listNode1 = new ListNode(1);
        ListNode listNode55 = new ListNode(55);
        ListNode listNode20 = new ListNode(20);

        listNode5.next = listNode2;
        listNode2.next = listNode0;
        listNode0.next = listNode9;
        listNode9.next = listNode10;
        listNode10.next = listNode15;
        listNode15.next = listNode1;
        listNode1.next = listNode55;
        listNode55.next = listNode20;

        System.out.println(print(listNode5));
        ListNode listNode = reverseBetweenByPoint(listNode5, 3, 7);
        System.out.println(print(listNode));
    }

    public static ListNode reverseBetweenByStack(ListNode head, int m, int n) {
        if(m == 1) {
            ListNode firstNode = new ListNode(-1);
            firstNode.next = head;
            head = firstNode;
        }

        final Stack<ListNode> stack = new Stack<>();
        ListNode originalHead = head;

        for(int i = 0; i < m - 2 && m > 1; i++) {
            head = head.next;
        }

        ListNode tempCurrentNode = head;
        for(int i = m; i <= n; i++) {
            head = head.next;
            stack.push(head);
        }

        ListNode endNode = head.next;
        while(!stack.isEmpty()) {
            tempCurrentNode.next = stack.pop();
            tempCurrentNode = tempCurrentNode.next;
        }

        tempCurrentNode.next = endNode;

        if(m == 1) {
            return originalHead.next;
        }

        return originalHead;
    }

    public static ListNode reverseBetweenByPoint(ListNode head, int m, int n) {
        if(m == 1) {
            ListNode firstNode = new ListNode(-1);
            firstNode.next = head;
            head = firstNode;
        }

        ListNode originalHead = head;
        for(int i = 0; i < m - 2 && m > 1; i++) {
            head = head.next;
        }

        ListNode tempCurrentNode = head;
        ListNode tempNext = head.next;
        ListNode next = head.next;

        for(int i = m; i <= n; i++) {
            ListNode nextHead = next.next;
            ListNode tempNode = tempCurrentNode.next;
            tempCurrentNode.next = next;
            next.next = tempNode;
            next = nextHead;
        }

        tempNext.next = next;

        if(m == 1) {
            return originalHead.next;
        }

        return originalHead;
    }

    private static String print(ListNode listNode) {
        String str = "";
        while(listNode != null) {
            str += listNode.val;
            listNode = listNode.next;
            str += " -> ";
        }

        return str;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

}


