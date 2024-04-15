package com.example.demo;
import com.example.demo.tool.TreeNode;

/**
 * 简单来说，递归交换左右子树
 * 难点：用什么顺序进行遍历
 * 前后序比较方便，要么就是先把子树反转了，要么就是先把上面的节点反转了
 */
public class 翻转二叉树 {
    public static void main(String[] args){

    }

    public static TreeNode invertTree(TreeNode root) {
        if(root == null){
            return null;
        }
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }
}
