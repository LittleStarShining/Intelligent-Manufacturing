package com.example.demo;

import com.example.demo.tool.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 使用的就是广度优先遍历，使用queue
 * 但是于广度优先遍历不同的是，你需要对弹出数量加以控制，不然你没办法直到哪一层是哪一层的
 * 为什么呢，因为你不加控制的话，一直会往queue里加节点，会一直执行while里面的代码
 */
public class 层序遍历 {
    public static void main(String[] args){

    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        if (root == null) {
            return ret;
        }

        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<Integer>();
            // 此时还没放子节点，只有本层的节点
            int currentLevelSize = queue.size();
            for (int i = 1; i <= currentLevelSize; ++i) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            ret.add(level);
        }

        return ret;
    }
}
