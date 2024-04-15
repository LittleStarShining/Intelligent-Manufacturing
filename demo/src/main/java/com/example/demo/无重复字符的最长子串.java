package com.example.demo;

/**
 * 双指针问题
 * 思路：创建一个数组last，用于存储每个字符最后出现的位置。数组的大小为128，因为ASCII字符的范围是0-127。
 * 从头开始遍历字符串，每次更新当前字符最后出现的位置。
 * 如果当前字符最后出现的位置在start的左边，说明当前字符在之前的子串中出现过，更新start的位置。
 * 每次更新结果res，res为当前位置i减去start的位置加1。
 * 返回结果res。
 */
public class 无重复字符的最长子串 {
    public static void main(String[] args){

    }
    public static int lengthOfLongestSubstring(String s) {
        // 创建一个数组last，用于存储每个字符最后出现的位置。数组的大小为128，因为ASCII字符的范围是0-127。
        int[] last = new int[128];
        int res = 0;
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            int index = s.charAt(i);
            // 选择一个start，如果当前字符最后出现的位置在start的左边，说明当前字符在之前的子串中出现过，更新start的位置。
            start = Math.max(start, last[index]);
            //i - start + 1表示当前考虑的子串的长度,当前位置i-当前子串的起始位置start+1就是字串长度，max是为了获得最长字串
            res = Math.max(res, i - start + 1);
            last[index] = i + 1;
        }
        return res;
    }
}
