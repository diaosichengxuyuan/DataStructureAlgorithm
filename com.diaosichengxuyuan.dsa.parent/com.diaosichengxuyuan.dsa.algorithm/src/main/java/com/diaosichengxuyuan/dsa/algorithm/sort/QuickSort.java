package com.diaosichengxuyuan.dsa.algorithm.sort;

import java.util.Arrays;

/**
 * 快速排序
 *
 * @author liuhaipeng
 * @date 2019/2/13
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] data = {50, 30, 66, 21, 9, 99, 1, 18, 48, 67, 55, 23, 18, 40, 100, 55, 66, 48, 50, 78};
        sort(data, 0, data.length - 1);
        System.out.println(Arrays.toString(data));
    }

    private static void sort(int[] data, int left, int right) {
        if(left >= right) {
            return;
        }

        int startLeft = left;
        int startRight = right;
        int base = data[left];
        int baseIndex = left;

        while(left < right) {
            //从右向左寻找小于基准值的数
            while(left < right && data[right] >= base) {
                right--;
            }
            if(left == right) {
                continue;
            }
            int temp = data[right];
            data[right] = data[baseIndex];
            data[baseIndex] = temp;
            baseIndex = right;

            //从左向右寻找大于基准值的数
            while(left < right && data[left] <= base) {
                left++;
            }
            if(left == right) {
                continue;
            }
            temp = data[left];
            data[left] = data[baseIndex];
            data[baseIndex] = temp;
            baseIndex = left;
        }

        sort(data, startLeft, baseIndex);
        sort(data, baseIndex + 1, startRight);
    }
}
