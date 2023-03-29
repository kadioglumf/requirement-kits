package com.algorithm;

public class Sqrt {

    public static void main(String[] args) {
        System.out.println(sqrt(2));
    }

    public static int sqrt(int x) {
        if (x == 1)
            return 1;

        for (int i = 2 ; i <= x ; i++) {
            if (i * i > x || i * i < 0) {
                return i-1;
            }
        }
        return 0;
    }
}
