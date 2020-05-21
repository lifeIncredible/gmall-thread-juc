package com.atguigu.test;

/**
 * @Author 苏晓虎
 * @Description:
 *      将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 * 示例：
 * 输入：1->2->4, 1->3->4
 * 输出：1->1->2->3->4->4
 * @create 2020-04-09 16:45
 */
public class ListNode {
     int val;
     ListNode next;

    ListNode(int x ){
        val = x;
    }
}

class Solution {
    public ListNode mergeTwoLists(ListNode L1, ListNode L2) {
        ListNode resNode = new ListNode(0);
        ListNode cur = resNode;
        while (L1 != null && L2 != null) {
            if (L1.val <= L2.val) {
                cur.next = L1;
                cur = cur.next;
                L1 = L1.next;
            } else {
                cur.next = L2;
                cur = cur.next;
                L2 = L2.next;
            }
        }
        if (L1 != null) {
            cur.next = L1;
        }
        if (L2 != null) {
            cur.next = L2;
        }
        return resNode.next;
    }
}
