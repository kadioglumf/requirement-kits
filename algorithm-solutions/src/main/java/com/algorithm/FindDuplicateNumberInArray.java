package com.algorithm;

import java.util.Arrays;

public class FindDuplicateNumberInArray {

    public static void main(String[] args) {
        int[] nums = {1,5,8,3,7, 9, 3};
        int result = findDuplicateNumberInArray(nums);
        System.out.println(result);
    }

    private static int findDuplicateNumberInArray(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                return nums[i];
            }
        }
        return 0;
    }
}
