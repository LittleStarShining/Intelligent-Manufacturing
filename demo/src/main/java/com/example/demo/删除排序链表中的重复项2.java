package com.example.demo;

import com.example.demo.tool.ListNode;

/**
 给定一个已排序的链表的头 head ， 删除原始链表中所有重复数字的节点，只留下不同的数字 。返回 已排序的链表 。
 */
public class 删除排序链表中的重复项2 {
    public static void main(String[] args){

    }

    public static ListNode deleteDuplicates(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode pre = dummy;
        ListNode cur = head;
        while(cur != null){
            while(cur.next != null && cur.getVal() == cur.next.getVal()){
                cur = cur.next;
            }
            if(pre.next == cur){
                pre = pre.next;
            }else{
                pre.next = cur.next;
            }
            cur = cur.next;
        }
        return dummy.next;
    }
}
