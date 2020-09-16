package com.diaosichengxuyuan.dsa.algorithm.sort;

import java.util.Arrays;

/**
 * 堆排序
 *
 * @author liuhaipeng
 * @date 2019/2/13
 */
public class HeapSort {
    public static void main(String[] args) {
        int[] data = {50, 30, 66, 21, 9, 99, 1, 18, 48, 67, 55, 23, 18, 40, 100, 55, 66, 48, 50, 78};
        build(data);
        sort(data);
        System.out.println(Arrays.toString(data));
    }

    private static void build(int[] data) {
        int length = data.length;
        //第一个非叶子节点
        int firstNotLeaf = (int)Math.floor(length / 2) - 1;

        //从第一个非叶子节点向上调整
        for(int i = firstNotLeaf; i >= 0; i--) {
            adjust(data, i, length);
        }
    }

    /**
     * 调整第i个下标的元素
     */
    private static void adjust(int[] data, int i, int length) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        int max = i;
        if(left <= length - 1 && data[left] > data[max]) {
            max = left;
        }
        if(right <= length - 1 && data[right] > data[max]) {
            max = right;
        }

        if(max != i) {
            exchange(data, i, max);
            adjust(data, max, length);
        }
    }

    private static void exchange(int[] data, int i, int j) {
        int temp = data[j];
        data[j] = data[i];
        data[i] = temp;
    }

    private static void sort(int[] data) {
        for(int i = data.length - 1; i > 0; i--) {
            exchange(data, i, 0);
            adjust(data, 0, i);
        }
    }

}
