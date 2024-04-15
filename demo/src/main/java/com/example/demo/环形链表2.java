package com.example.demo;

import com.example.demo.tool.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个链表的头节点  head ，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
 * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。
 * 为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。
 * 如果 pos 是 -1，则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
 */

/**
 * 我的思路直接用set方法，如果有环，set.add()会返回false，返回此时的head，但这个方法性能不够好，还是得用双指针
 * 设quick走f步，slow走s步，得：f=2s，f= s + nb
 * s = nb, f = 2nb
 * 第一次相遇后 quick放回head，且一次走一个节点，再次相遇的节点就是环的入口
 * 为什么呢。因为一开始quick比slow快一步，所以第一次相遇时，slow走了nb步，quick走了2nb步，也就是说quick比slow多走了一个环的长度
 * 走到环口的步数应该是a+nb，走到第一次相遇的步数是nb，所以再走a步就是环口，此时把quick放回到head，当head走到环口时，slow走到了环口，此时相遇。
 */
public class 环形链表2 {
    public static void main(String[] args){

    }
    public static ListNode detectCycle(ListNode head){
        ListNode fast = head, slow = head;
        while (true) {
            if (fast == null || fast.next == null) return null;
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) break;
        }
        fast = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return fast;
    }
}
