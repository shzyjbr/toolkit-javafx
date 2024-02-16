package com.kelton.tooljavafx.util;

/**
 * @Author zhouzekun
 * @Date 2024/2/16 15:03
 */
public class StringUtil {
    public static boolean empty(String s) {
        if (s == null) {
            return true;
        }
        s = s.trim();
        return s.length() == 0;
    }
}
