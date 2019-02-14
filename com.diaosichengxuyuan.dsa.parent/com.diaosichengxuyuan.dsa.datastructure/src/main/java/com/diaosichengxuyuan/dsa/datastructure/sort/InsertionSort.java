package com.diaosichengxuyuan.dsa.datastructure.sort;

import java.util.Arrays;

/**
 * 插入排序
 *
 * @author liuhaipeng
 * @date 2019/2/13
 */
public class InsertionSort {

    public static void main(String[] args) {
        int[] data = {50, 30, 66, 21, 9, 99, 1, 18, 48, 67, 55, 23, 18, 40, 100, 55, 66, 48, 50, 78};
        sort(data);
        System.out.println(Arrays.toString(data));
    }

    private static void sort(int[] data) {
        for(int i = 1; i <= data.length - 1; i++) {
            if(data[i-1] <= data[i]) {
                continue;
            }
            if(data[0] >= data[i]) {
                insert(data, 0, data[i], i);
                continue;
            }

            for(int j = i - 1; j >= 0; j--) {
                if(data[j - 1] <= data[i] && data[i] <= data[j]) {
                    insert(data, j, data[i], i);
                    break;
                }
            }
        }
    }

    /**
     * 在index=i之前插入value，i...end-1之间的元素往后移动一位
     */
    private static void insert(int[] data, int i, int value, int end) {
        for(int j = end - 1; j >= i; j--) {
            data[j + 1] = data[j];
        }
        data[i] = value;
    }

}
