package com.example.demo;

import com.example.demo.tool.ListNode;

/**
 * 给你单链表的头指针 head 和两个整数 left 和 right ，其中 left <= right 。
 * 请你反转从位置 left 到位置 right 的链表节点，返回 反转后的链表 。
 */
public class 链表反转2 {
    public static void main(String[] args){}


    /*
    创建一个虚拟头节点，这样我们就可以在头节点之前插入节点。
    初始化一个指针 pre，并让它移动到 left - 1 的位置。这是反转部分的前一个节点。
    初始化另一个指针 cur，并让它移动到 left 的位置。这是反转部分的第一个节点。
    使用经典的反转链表的方法，反转从 left 到 right 的部分。
    将反转部分与原链表的其他部分连接起来。
    */
    public static ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummyNode = new ListNode(-1);
        dummyNode.next = head;
        ListNode pre = dummyNode;
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }
        ListNode curr = pre.next;
        ListNode next;
        for (int i = 0;i<right-left;i++){
            next = curr.next;
            curr.next = next.next;
            next.next = pre.next;
            pre.next = next;
        }
        return dummyNode.next;
    }
}
