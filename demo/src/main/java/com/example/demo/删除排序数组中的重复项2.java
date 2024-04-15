package com.example.demo;

/**
 * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使得出现次数超过两次的元素只出现两次 ，返回删除后数组的新长度。
 *
 * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
 */
public class 删除排序数组中的重复项2 {
    public static void main(String[] args){
        System.out.println(removeDuplicates(new int[]{1,1,1,2,2,3}));
    }

    static int removeDuplicates(int[] nums){
        if(nums.length == 0){
            return 0;
        }
        int i = 2;
        for(int j = 2;j<nums.length;j++){
            if(nums[i-2] != nums[j]){
                nums[i] = nums[j];
                i++;
            }
        }
        return i;
    }
}
