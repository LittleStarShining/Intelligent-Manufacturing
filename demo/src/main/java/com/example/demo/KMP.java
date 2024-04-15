package com.example.demo;

public class KMP {
    public static void main(String[] args) {
        String s = "abababca";
        String p = "ababca";
        System.out.println(kmp(s, p));
    }

    public static int kmp(String s, String p) {
        char[] sArr = s.toCharArray();
        char[] pArr = p.toCharArray();
        int[] next = new int[pArr.length];
        getNext(pArr, next);
        int i = 0, j = 0;
        while (i < sArr.length && j < pArr.length) {
            if (j == -1 || sArr[i] == pArr[j]) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j == pArr.length) {
            return i - j;
        }
        return -1;
    }

    public static void getNext(char[] pattern, int[] next) {
        next[0] = -1;
        int i = 0, j = -1;
        while (i < pattern.length - 1) {
            if (j == -1 || pattern[i] == pattern[j]) {
                i++;
                j++;
                next[i] = j;
            } else {
                j = next[j];
            }
        }
    }
}
