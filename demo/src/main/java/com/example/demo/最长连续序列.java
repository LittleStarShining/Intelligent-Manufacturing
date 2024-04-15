package com.example.demo;

import java.util.HashSet;
import java.util.Set;

public class 最长连续序列 {
    public static void mian(String[] args){

    }
    public static int longestConsecutive(int[] nums) {
        Set<Integer> num_set = new HashSet<Integer>();
        for(int num : nums){
            num_set.add(num);
        }
        int longestStreak = 0;
        for(int num : num_set){
            // 如果这个数-1不在set中，就说明这个数是起点
            if(!num_set.contains(num-1)){
                int currentNum = num;
                int currentStreak = 1;
                // 不断看++的数在不在set中，如果在就继续，不在就停止
                while(num_set.contains(currentNum+1)){
                    currentNum += 1;
                    currentStreak += 1;
                }
                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }
        return longestStreak;
    }
}
