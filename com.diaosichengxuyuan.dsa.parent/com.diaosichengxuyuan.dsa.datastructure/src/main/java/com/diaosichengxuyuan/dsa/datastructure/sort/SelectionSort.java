package com.diaosichengxuyuan.dsa.datastructure.sort;

import java.util.Arrays;

/**
 * 选择排序
 *
 * @author liuhaipeng
 * @date 2019/2/13
 */
public class SelectionSort {

    public static void main(String[] args) {
        int[] data = {50, 30, 66, 21, 9, 99, 1, 18, 48, 67, 55, 23, 18, 40, 100, 55, 66, 48, 50, 78};
        sort(data);
        System.out.println(Arrays.toString(data));
    }

    private static void sort(int[] data) {
        for(int i = 0; i <= data.length - 2; i++) {
            int minIndex = i;
            for(int j = i; j <= data.length - 1; j++) {
                if(data[j] < data[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = data[i];
            data[i] = data[minIndex];
            data[minIndex] = temp;
        }
    }

}
