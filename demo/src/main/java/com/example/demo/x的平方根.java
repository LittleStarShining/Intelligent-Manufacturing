package com.example.demo;

public class x的平方根 {
    public static void main(String[] args){
        System.out.println(mySqrt(1));
    }
    public static int mySqrt(int x) {
        int index = -1,l=0,r=x;
        while(l<=r){
            int mid = l+(r-l)/2;
            if((long)mid*mid<=x){
                index = mid;
                l = mid+1;
            }else{
                r = mid-1;
            }
        }
        return index;
    }
}
