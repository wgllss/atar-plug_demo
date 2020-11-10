package com.common.framework.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularUtil {
    public static boolean validatePhoneNumber(String phoneNumber) {
        String regEx = "^[1][0-9]{10}$";
        //String regEx = "/^(0|86)?[1][0-9]{10}$/";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    public static boolean validateVerifyCode(String verifyCode) {
        String regEx = "^[0-9]{6}$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(verifyCode);
        return m.matches();
    }

    public static boolean validatePassword(String password) {
        return password != null && !password.isEmpty();
    }


    /**
     * 判断邮箱
     *
     * @param mailString
     * @return
     */
    public static boolean isEmail(String mailString) {
        if (!mailString.contains("@")) return false;
        String check = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
        return regularString(check, mailString);
    }

    public static boolean isChineseOnly(String str) {
        String check = "^[u4e00-u9fa5],{0,}$";
        return regularString(check, str);
    }

    public static boolean isEnglishOnly(String string) {
        String check = "^[A-Za-z]+$";
        return regularString(check, string);
    }

    public static boolean regularString(String check, String matchString) {
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(matchString);
        return matcher.matches();
    }
}
