package com.example.demo;

import com.example.demo.tool.ListNode;

/**
 * 我的思路：用set存储遍历过的节点，如果遍历到的节点在set中，说明有环
 */
public class 环形链表 {
    public static void main(){

    }
    public static boolean hasCycle(ListNode head) {
        if(head == null || head.next == null){
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while(slow != fast){
            if(fast == null || fast.next == null){
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }
}
