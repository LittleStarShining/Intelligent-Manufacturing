package com.example.demo;

/**
 * 动规五步：
 * 1. 确认dp数组含义：dp[i][j]表示从左上角到(i, j)的最小路径和
 * 2. 确定递推公式：dp[i][j] = min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j]
 * 3. 初始化：dp[0][0] = grid[0][0], 第一行和第一列初始化
 * 4. 确定遍历顺序：从上到下，从左到右
 * 5. 举例推导dp数组
 */
public class 最小路径和 {
    public static void main(String[] args){

    }
    public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];
        // 初始化
        dp[0][0] = grid[0][0];
        for(int i = 1; i < m; i++){
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        for(int j = 1; j < n; j++){
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }
        // 递推
        for(int i = 1; i < m; i++){
            for(int j = 1; j < n; j++){
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }
}
