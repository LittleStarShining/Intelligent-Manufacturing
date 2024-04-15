package com.example.demo;

/**
 * 动规五步：
 * 1. 确定dp数组含义：dp[i]表示i的完全平方数的最小个数
 * 2. 确定递推公式：dp[i] = min(dp[i], dp[i - j * j] + 1)
 * 3. dp数组初始化：dp[0] = 0，非0下标用无穷大
 * 4. 确定遍历顺序：都可以
 * 5. 举例推导dp数组
 */
public class 完全平方数 {
    public static void main(String[] args) {
        System.out.println(new 完全平方数().numSquares(12));
    }

    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int j = 1; j * j <= i; j++) {
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
            }
        }
        return dp[n];
    }
}
