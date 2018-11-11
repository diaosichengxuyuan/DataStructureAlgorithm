package com.diaosichengxuyuan.dsa.datastructure.tree.util;

import org.apache.commons.lang3.StringUtils;

/**
 * util工具类
 *
 * @author liuhaipeng
 * @date 2018/11/11
 */
public class TreeUtil {

    /**
     * 输出固定长度的字符串，不足的位置在字符串前后补充空格
     *
     * @param length 固定长度
     * @param str    输入字符串
     * @return 固定长度的字符串
     */
    public static String getFixedLengthString(int length, String str) {
        if(length <= 0) {
            throw new IllegalArgumentException(String.format("长度不合法，长度应该大于0，你输入的是：%s", length));
        }

        StringBuilder builder = new StringBuilder();

        if(StringUtils.isEmpty(str)) {
            for(int i = 0; i < length; i++) {
                builder.append(" ");
            }
            return builder.toString();
        }

        int strLength = str.length();
        if(strLength >= length) {
            return str;
        }

        //补充在前部的空格长度
        int preLength = (length - strLength) / 2;
        //补充在后部的空格长度
        int posLength = length - preLength - strLength;

        for(int i = 0; i < preLength; i++) {
            builder.append(" ");
        }
        builder.append(str);
        for(int i = 0; i < posLength; i++) {
            builder.append(" ");
        }

        return builder.toString();
    }
}
