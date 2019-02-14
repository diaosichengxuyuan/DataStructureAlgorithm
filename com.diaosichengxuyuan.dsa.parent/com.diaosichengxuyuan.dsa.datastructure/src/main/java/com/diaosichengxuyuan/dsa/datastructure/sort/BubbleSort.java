package com.diaosichengxuyuan.dsa.datastructure.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 *
 * @author liuhaipeng
 * @date 2019/2/13
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] data = {50, 30, 66, 21, 9, 99, 1, 18, 48, 67, 55, 23, 18, 40, 100, 55, 66, 48, 50, 78};
        sort(data);
        System.out.println(Arrays.toString(data));
    }

    private static void sort(int[] data) {
        for(int i = data.length - 1; i >= 1; i--) {
            for(int j = 0; j <= i - 1; j++) {
                if(data[j] > data[j + 1]) {
                    int temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                }
            }
        }
    }

}
