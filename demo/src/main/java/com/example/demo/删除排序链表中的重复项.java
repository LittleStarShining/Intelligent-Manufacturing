package com.example.demo;
import com.example.demo.tool.ListNode;
public class 删除排序链表中的重复项 {
    public static void main(String[] args){
        ListNode head = new ListNode(1);
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(3);
        head.setNext(node1);
        node1.setNext(node2);
        node2.setNext(node3);
        node3.setNext(node4);
        ListNode result = deleteDuplicates(head);
        while(result != null){
            System.out.println(result.getVal());
            result = result.next;
        }
    }

    // 链表可以直接跳过重复项
    public static ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }

        ListNode cur = head;
        while (cur.next != null) {
            if (cur.getVal() == cur.next.getVal()) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }

        return head;

    }
}
