package com.algorithm;

public class PowerOfTwo {
    public static void main(String[] args) {
        int value = 20;
        System.out.println(isPowerOfTwo(value));
    }

    public static boolean isPowerOfTwo(int value) {
        return (value & (value - 1)) == 0;
    }
}
