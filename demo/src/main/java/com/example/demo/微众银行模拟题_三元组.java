package com.example.demo;

import java.util.Scanner;

public class 微众银行模拟题_三元组 {
    public void readInput() {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] nums = new int[n];

        for(int i = 0; i < n; i++){
            nums[i] = in.nextInt();
        }
        int res = calculateTriplets(nums);
        System.out.println(res);
    }

    // 暴力法无法通过时间限制，需要优化
    // 使用动态规划。我们可以创建两个数组，leftMin和rightMax，分别表示每个位置左边的最小值和右边的最大值。
    // 然后，我们只需要遍历一次数组，如果对于某个位置i，nums[i]大于leftMin[i]并且小于rightMax[i]，那么nums[i]就是一个满足条件的三元组的中间元素。
    // 结果：输出错误？？？
    public int calculateTriplets(int[] nums) {
        int n = nums.length;
        int[] leftMin = new int[n];
        int[] rightMax = new int[n];

        leftMin[0] = nums[0];
        for (int i = 1; i < n; i++) {
            leftMin[i] = Math.min(leftMin[i - 1], nums[i]);
        }

        rightMax[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], nums[i]);
        }

        int res = 0;
        for (int i = 1; i < n - 1; i++) {
            if (nums[i] > leftMin[i] && nums[i] < rightMax[i]) {
                res++;
            }
        }

        return res;
    }
}
