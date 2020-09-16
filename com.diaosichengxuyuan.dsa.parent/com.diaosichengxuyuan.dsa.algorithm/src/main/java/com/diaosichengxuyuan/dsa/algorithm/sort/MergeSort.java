package com.diaosichengxuyuan.dsa.algorithm.sort;

import java.util.Arrays;

/**
 * 归并排序
 *
 * @author liuhaipeng
 * @date 2019/2/13
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] data = {50, 30, 66, 21, 9, 99, 1, 18, 48, 67, 55, 23, 18, 40, 100, 55, 66, 48, 50, 78};
        sort(data, 0, data.length - 1);
        System.out.println(Arrays.toString(data));
    }

    private static void sort(int[] data, int leftIndex, int rightIndex) {
        if(rightIndex - leftIndex < 1) {
            return;
        }

        int midIndex = (int)Math.ceil((leftIndex + rightIndex) / 2);

        sort(data, leftIndex, midIndex);
        sort(data, midIndex + 1, rightIndex);

        int[] temp = new int[rightIndex - leftIndex + 1];
        int i = 0;
        int tempLeftIndex = leftIndex;
        int tempRightIndex = midIndex + 1;

        while(true) {
            if(tempLeftIndex > midIndex && tempRightIndex <= rightIndex) {
                for(int j = tempRightIndex; j <= rightIndex; j++) {
                    temp[i++] = data[j];
                }
                break;
            }

            if(tempRightIndex > rightIndex && tempLeftIndex <= midIndex) {
                for(int j = tempLeftIndex; j <= midIndex; j++) {
                    temp[i++] = data[j];
                }
                break;
            }

            if(data[tempLeftIndex] <= data[tempRightIndex]) {
                temp[i++] = data[tempLeftIndex];
                tempLeftIndex++;
            } else {
                temp[i++] = data[tempRightIndex];
                tempRightIndex++;
            }
        }

        i = 0;
        for(int j = leftIndex; j <= rightIndex; j++) {
            data[j] = temp[i++];
        }
    }

}
