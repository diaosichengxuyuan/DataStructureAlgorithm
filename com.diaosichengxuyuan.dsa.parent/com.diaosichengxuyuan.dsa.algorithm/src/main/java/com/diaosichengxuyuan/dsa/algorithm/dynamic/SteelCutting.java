package com.diaosichengxuyuan.dsa.algorithm.dynamic;

/**
 * @author liuhaipeng
 * @date 2020/9/3
 */
public class SteelCutting {

    /**
     * 假定我们知道某公司出售一段长度为i英寸的钢条的价格为p[i](i=1,2,3...)，现在先给一段长度为n的钢条，问怎么切割，获得的收益最大?
     */
    public static void main(String[] args) {
        int[] p = {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
        int n = 10;
        System.out.println(topDown(p, n));
        int[] r = new int[n];
        r[0] = 0;
        System.out.println(downTop(p, n, r));
    }

    private static int topDown(int[] p, int n) {
        int max = 0;
        for(int i = 0; i < n; i++) {
            int temp = p[i] + topDown(p, n - i - 1);
            if(temp > max) {
                max = temp;
            }
        }
        return max;
    }

    private static int downTop(int[] p, int n, int[] r) {
        for(int m = 0; m < n; m++) {
            int max = 0;
            for(int i = 0; i <= m; i++) {
                int temp = p[i];
                if(m - i - 1 >= 0) {
                    temp = temp + r[m - i - 1];
                }
                if(temp > max) {
                    max = temp;
                }
            }
            r[m] = max;
        }
        return r[n - 1];
    }

}
