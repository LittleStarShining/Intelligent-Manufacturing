package com.example.demo;

/***
 * 动规五举：
 * 1.确定DP数组含义：装满容量为j的背包最少要dp[j]个物品
 * 2.确定递推公式：dp[j] = min(dp[j], dp[j - coins[i]] + 1)
 * 3.DP数组初始化：dp[0] = 0,非0下表初始值为无穷大，因为用的是min
 * 4.确认遍历顺序：都可以
 * 5.举例推导DP数组
 */
public class 零钱兑换 {
    public static void main(String[] args){

    }
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        for(int i = 1; i <= amount; i++){
            dp[i] = Integer.MAX_VALUE;
            for(int j = 0; j < coins.length; j++){
                if(i - coins[j] >= 0 && dp[i - coins[j]] != Integer.MAX_VALUE){
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }
}
