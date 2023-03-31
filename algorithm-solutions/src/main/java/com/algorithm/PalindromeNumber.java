package com.algorithm;

public class PalindromeNumber {

    public static void main(String[] args) {
        System.out.println(palindromeNumber(121));
        System.out.println(palindromeNumber2(121));
    }

    private static boolean palindromeNumber(int num) {
        if (num <= 0 || num % 10 == 0)
            return false;

        int result = 0;
        int temp = num;
        while (temp > 0) {

            result = result * 10 + temp%10;
            temp /= 10;

        }
        return result == num;
    }

    private static boolean palindromeNumber2(int num) {
        if (num <= 0 || num % 10 == 0)
            return false;

        String number = String.valueOf(num);
        for (int i = 0 ; i < number.length()/2 ;i++) {
            if (number.charAt(i) != number.charAt(number.length() -1 - i))
            {
                return false;
            }
        }
        return true;
    }
}
