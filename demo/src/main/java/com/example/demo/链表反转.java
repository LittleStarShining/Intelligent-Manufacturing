package com.example.demo;

import com.example.demo.tool.ListNode;

public class 链表反转 {
    public static void main(String[] args){


    }
    // 迭代
    static ListNode ReverseList(ListNode head){
        ListNode prev = null,next;
        ListNode curr = head;
        while(curr != null){
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
    // 递归
    static ListNode ReverseList2(ListNode head){
        if (head == null || head.next == null){
            return head;
        }
        ListNode newHead = ReverseList2(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }
}
