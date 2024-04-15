package com.example.demo;

import com.example.demo.tool.TreeNode;

import javax.management.Query;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 深度优先：递归，如果左右子树都为空，返回1，
 * 如果左子树为空，返回右子树的最小深度+1，如果右子树为空，返回左子树的最小深度+1，
 * 如果左右子树都不为空，返回左右子树的最小深度的最小值+1
 */

/**
 * 广度优先：层序遍历，找到第一个叶子节点就返回
 * 难点1：判断左子节点怎么找到右子节点？
 * 使用队列，每次取出一个节点，判断左右子节点是否为空，如果不为空，加入队列，空的话出队列
 * 难点2：怎么判断是否为叶子节点？
 * 判断左右子节点是否为空，如果都为空，说明是叶子节点
 */

/**
 * 时间复杂度都是O（n）
 * 空间复杂度：深度优先是O（logn），广度优先是O（n）
 */
public class 二叉树的最小深度 {
    public static void main(String[] args){

    }
    // 深度优先
    public static int minDepth(TreeNode root){
        if(root == null) return 0;
        int m1 = minDepth(root.left);
        int m2 = minDepth(root.right);
        //1.如果左孩子和右孩子有为空的情况，直接返回m1+m2+1
        //2.如果都不为空，返回较小深度+1
        return root.left == null || root.right == null ? m1 + m2 + 1 : Math.min(m1,m2) + 1;
    }

    // 广度优先
    public static int minDepth1(TreeNode root){
        if (root == null) return 0;
        root.setDeepth(1);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
           TreeNode node = queue.poll();
            if (node.left == null && node.right == null) {
                return node.getDeepth();
            }
            if (node.left != null) {
                node.left.setDeepth(node.getDeepth() + 1);
                queue.offer(node.left);
            }
            if (node.right != null) {
                node.right.setDeepth(node.getDeepth() + 1);
                queue.offer(node.right);
            }
        }
        return 0;
    }
}
