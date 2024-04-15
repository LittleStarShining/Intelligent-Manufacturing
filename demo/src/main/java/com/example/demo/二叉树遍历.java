package com.example.demo;
import com.example.demo.tool.TreeNode;


// 前序遍历：根左右
// 中序遍历：左根右
// 后序遍历：左右根
// 层序遍历：从上往下，从左往右

// 遍历方法：递归遍历、迭代遍历、morris遍历
public class 二叉树遍历 {
    public static void main(String[] args){

    }

    // 前序遍历-递归
    public static void preOrder(TreeNode root){
        if(root == null){
            return;
        }
        System.out.println(root.val);
        preOrder(root.left);
        preOrder(root.right);
    }

    // 中序遍历-递归
    public static void inOrder(TreeNode root){
        if(root == null){
            return;
        }
        inOrder(root.left);
        System.out.println(root.val);
        inOrder(root.right);
    }

    // 后序遍历-递归
    public static void postOrder(TreeNode root){
        if(root == null){
            return;
        }
        postOrder(root.left);
        postOrder(root.right);
        System.out.println(root.val);
    }

}
