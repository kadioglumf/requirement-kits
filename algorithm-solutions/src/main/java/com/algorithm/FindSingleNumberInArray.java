package com.algorithm;

import java.util.Arrays;

public class FindSingleNumberInArray {

    public static void main(String[] args) {
        int[] nums = {4,1,2,1,2,5,6,5};
        System.out.println(singleNumberUsingBitwise(nums));;
    }

    public static int singleNumber(int[] nums) {
        if(nums.length==1){
            return nums[0];
        }
        Arrays.sort(nums);
        for (int i = 0 ; i< nums.length -1; i+=2) {
            if (nums[i] != nums[i +1]) {
                return nums[i];
            }
        }
        return nums[nums.length-1];
    }

    /**
     * @see <a href="https://leetcode.com/problems/single-number/solutions/3021354/java-easy-solution-beats-100-runtime-1ms-bit-manipulation/"></a>
     *
     */
    public static int singleNumberUsingBitwise(int[] nums) {
        int result = 0;

        for (int i = 1 ; i < nums.length ; i++) {
            result ^= nums[i];
        }

        return result;
    }
}
