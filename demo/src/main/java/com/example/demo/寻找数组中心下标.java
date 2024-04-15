package com.example.demo;

public class 寻找数组中心下标 {
    public static void main(String[] args){

    }
    public static int pivotIndex(int[] nums) {
        int sum = 0;
        for(int num : nums){
            sum += num;
        }
        int preSum = 0;
        for(int i = 0; i < nums.length; i++){
            if(preSum * 2 + nums[i] == sum){
                return i;
            }
            preSum += nums[i];
        }
        return -1;
    }
}
