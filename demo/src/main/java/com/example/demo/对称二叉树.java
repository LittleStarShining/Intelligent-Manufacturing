package com.example.demo;
import com.example.demo.tool.TreeNode;

/**
 * 根节点左子树和右子树是否可以反转后是否相等，是的话就是对称的
 * 只能使用后序遍历
 */
public class 对称二叉树 {
    public static void main(String[] args){

    }
    public boolean isSymmetric(TreeNode root) {
        return compare(root, root);
    }

    public boolean compare(TreeNode left, TreeNode right){
        if(left == null && right == null){
            return true;
        }
        if(left == null || right == null){
            return false;
        }
        if(left.val != right.val){
            return false;
        }
        return compare(left.left, right.right) && compare(left.right, right.left);
    }
}
