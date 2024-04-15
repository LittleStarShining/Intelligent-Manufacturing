package com.example.demo;

public class 统计素数个数 {
    public static void main(String[] args){
        System.out.println(countPrimes(100));
    }

    // 埃筛法
    static int countPrimes(int n){
        boolean[] isPrime = new boolean[n]; //false为素数
        int count = 0;
        for(int i = 2;i<n;i++){
            if(!isPrime[i]){
                count++;
                for(int j =i*i;j<n;j+=i){
                    isPrime[j] = true;
                }
            }
        }
        return count;
    }
}
