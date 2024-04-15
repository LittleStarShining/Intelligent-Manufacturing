package com.example.demo;

/**
 * 思路1：使用System.arraycopy合并数组，再进行排序，但排序算法的时间复杂度为O(nlogn)还可以优化
 * 思路2：双指针，从后往前遍历，将两个数组中较大的数放到nums1的末尾
 */
public class 合并两个有序数组 {
    public static void main(String[] args){

    }
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m - 1, p2 = n - 1;
        int p = m + n - 1;
        while ((p1 >= 0) && (p2 >= 0)){
            nums1[p--] = (nums1[p1] < nums2[p2]) ? nums2[p2--] : nums1[p1--];
        }
        System.arraycopy(nums2, 0, nums1, 0, p2 + 1);
    }
}
