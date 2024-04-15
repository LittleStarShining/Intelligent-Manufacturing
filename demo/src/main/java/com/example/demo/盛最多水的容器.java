package com.example.demo;

import java.util.List;

public class 盛最多水的容器 {
    public static void main(String[] args){

    }
    public static int maxArea(int[] height) {
        int l = 0, r = height.length - 1;
        int res = 0;
        while(l < r){
            res = height[l] < height[r] ? Math.max(res, (r - l) * height[l++]) : Math.max(res, (r - l) * height[r--]);
        }
        return res;
    }

    // 我的写法
    // 一个关键点是：移动哪个木板,记住高度是由矮木板决定的
    // 若向内移动短板 ，水槽的短板 min(h[i],h[j])min(h[i], h[j])min(h[i],h[j]) 可能变大，因此下个水槽的面积 可能增大 。
    // 若向内移动长板 ，水槽的短板 min(h[i],h[j])min(h[i], h[j])min(h[i],h[j])​ 不变或变小，因此下个水槽的面积 一定变小
    public int MymaxArea(int[] height) {
        int left = 0,right = height.length -1;
        int max = 0;
        while(left<right){
            int temp = Math.min(height[left],height[right]) * (right - left);
            if(temp>max){
                max = temp;
            }
            if(height[left]>height[right]){
                right--;
            }else{
                left++;
            }
        }
        return max;
    }
}
