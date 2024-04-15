package com.example.demo;

/**
 * 动规五步：
 * 1. 确定dp数组含义：dp[i][j]表示到达i,j的路径数
 * 2. 确定递推公式：dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
 * 3. 初始化：dp[0][0] = 1,第一行和第一列都是1，其他的是0
 * 4. 确定遍历顺序：从上到下，从左到右
 * 5. 举例推导dp数组
 */
public class 不同路径 {
    public static void main(String[] args) {

    }

    public static int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for(int i = 0; i < m; i++){
            dp[i][0] = 1;
        }
        for(int i = 0; i < n; i++){
            dp[0][i] = 1;
        }
        for(int i = 1; i < m; i++){
            for(int j = 1; j < n; j++){
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }
}
