package com.example.demo.tool;

import lombok.Data;

@Data
public class TreeNode {
    public int val;
    public int deepth;
    public TreeNode left;
    public TreeNode right;
    public TreeNode(int x) { val = x; }
}

