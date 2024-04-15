package com.example.demo;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 假象一个绝对光滑的，高度很高的盒子，长度为，宽度为1。在其中，有许多的光滑铁块，铁块的每一个角一定位于整数坐标上。
 * 由于宽度为1，我们可以用二维字符图来表示每一个铁块的位置。如下图是一个长度为4，其中有7个铁块的二维字符图（'x'代表铁块，'o'代表没有铁块）：
 * oooo
 * xooo
 * xoxo
 * xxxx
 * 每一列的铁块数分别为3,1,2,1
 * 由于重力的缘故，所有的铁块要么下面是盒子底面，要么下面是另一个铁块。现在，在盒子的右边增加一个强磁铁。所有右边没有其他铁块或边界的铁块会向右移动，直到撞上一个铁块和边界停下。
 * 在上一张二维图上，加入磁铁后的字符图会变为：
 * oooo
 * ooox
 * ooxx
 * xxxx
 * 每一列的铁块数分别为。可以证明，这样操作后所有铁块要么下面是盒子底面，要么下面是另一个铁块。
 * 现在给你初始每一列有多少个铁块，请你计算，加入磁铁后每一列有多少铁块。
 */
public class 微众银行模拟题_磁铁 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // 存储盒子高度
        int[] blocks = new int[n]; // 存储每一列铁块数
        for(int i = 0; i < n; i++){
            blocks[i] = scanner.nextInt();
        }
        scanner.close();
        // 如何处理输入，对于一行只有一个的数据，直接用一个变量存储 n = scanner.nextInt();
        // 对于一行有多个数据的，用数组存储 int[] arr = new int[n]; for(int i = 0; i < n; i++){ arr[i] = scanner.nextInt(); }
        int[] res = calculateBlocksAfterMagnet(blocks);
        for(int i=0;i<n;i++){
            System.out.print(res[i] + " ");
        }
    }
    // 根据物理知识，如果磁铁在右边，那右边的肯定比左边多，做一个排序即可
    public static int[] calculateBlocksAfterMagnet(int[] blocks) {
        /**
         * 冒泡排序-超时
         * int n = blocks.length;
         *         for (int i = 0;i<n-1;i++){
         *             for(int j = 0;j<n-1-i;j++){
         *                 if(blocks[j]>blocks[j+1]){
         *                     int temp = blocks[j];
         *                     blocks[j] = blocks[j+1];
         *                     blocks[j+1] = temp;
         *                 }
         *             }
         *         }
         */
        Arrays.sort(blocks);
        return blocks;
    }
}
