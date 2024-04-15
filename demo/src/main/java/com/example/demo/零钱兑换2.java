package com.example.demo;

/**
 * 动规五步：
 * 1. 确定DP数组含义：金额为j有dp[j]个换法
 * 2. 确定递归公式：dp[j] += dp[j-conis[i]]
 * 3. DP数组初始化：dp[0] = 1
 * 4. 确定遍历顺序：先遍历硬币，再遍历金额，防止重复。比如说如果先遍历金额，可能会出现2+3,3+2的情况.先物品在背包是组合数，先背包再物品是排列数
 * 5. 举例推导DP数组
 */

/**
 * 给你一个整数数组 coins 表示不同面额的硬币，另给一个整数 amount 表示总金额。
 *
 * 请你计算并返回可以凑成总金额的硬币组合数。如果任何硬币组合都无法凑出总金额，返回 0 。
 *
 * 假设每一种面额的硬币有无限个。 题目数据保证结果符合 32 位带符号整数。
 */
public class 零钱兑换2 {
    public static void main(String[] args){

    }
    public static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        // 初始化
        dp[0] = 1;
        for(int i = 0; i < coins.length; i++){
            for(int j = coins[i]; j <= amount; j++){
                dp[j] += dp[j - coins[i]];
            }
        }
        return dp[amount];
    }
}
