package com.common.framework.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    /**
     * 验证Email
     *
     * @param email email地址，格式：zhangsan@sina.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkEmail(String email) {
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }

    /**
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     *
     * @param mobile 移动、联通、电信运营商的号码段
     *               <p>移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     *               、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）</p>
     *               <p>联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）</p>
     *               <p>电信的号段：133、153、180（未启用）、189</p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkMobile(String mobile) {
        String regex = "(\\+\\d+)?1[23456879]\\d{9}$";
        return Pattern.matches(regex, mobile);
    }

    /**
     * 验证固定电话号码
     *
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     *              <p><b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
     *              数字之后是空格分隔的国家（地区）代码。</p>
     *              <p><b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
     *              对不使用地区或城市代码的国家（地区），则省略该组件。</p>
     *              <p><b>电话号码：</b>这包含从 0 到 9 的一个或多个数字 </p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPhone(String phone) {
        String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
        return Pattern.matches(regex, phone);
    }

    /**
     * 验证整数（正整数和负整数）
     *
     * @param digit 一位或多位0-9之间的整数
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDigit(String digit) {
        String regex = "\\-?[1-9]\\d+";
        return Pattern.matches(regex, digit);
    }

    /**
     * 验证整数和浮点数（正负整数和正负浮点数）
     *
     * @param decimals 一位或多位0-9之间的浮点数，如：1.23，233.30
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDecimals(String decimals) {
        String regex = "\\-?[1-9]\\d+(\\.\\d+)?";
        return Pattern.matches(regex, decimals);
    }

    /**
     * 验证空白字符
     *
     * @param blankSpace 空白字符，包括：空格、\t、\n、\r、\f、\x0B
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkBlankSpace(String blankSpace) {
        String regex = "\\s+";
        return Pattern.matches(regex, blankSpace);
    }

    /**
     * 验证中文
     *
     * @param chinese 中文字符
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkChinese(String chinese) {
        String regex = "^[\u4E00-\u9FA5]+$";
        return Pattern.matches(regex, chinese);
    }

    /**
     * 验证日期（年月日）
     *
     * @param birthday 日期，格式：1992-09-03，或1992.09.03
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkBirthday(String birthday) {
        String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
        return Pattern.matches(regex, birthday);
    }

    /**
     * 验证URL地址
     *
     * @param url 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或 http://www.csdn.net:80
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkURL(String url) {
        String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
        return Pattern.matches(regex, url);
    }

    /**
     * 匹配中国邮政编码
     *
     * @param postcode 邮政编码
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPostcode(String postcode) {
        String regex = "[1-9]\\d{5}";
        return Pattern.matches(regex, postcode);
    }

    /**
     * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
     *
     * @param ipAddress IPv4标准地址
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIpAddress(String ipAddress) {
        String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
        return Pattern.matches(regex, ipAddress);
    }

    /**
     * 判断字符串是否包含数字和字母
     *
     * @param strContent
     * @return
     * @author :Atar
     * @createTime:2015-11-11上午11:26:41
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public static boolean isContansNumAndLetter(String strContent) {
        boolean isDigit = false;// 定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;// 定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < strContent.length(); i++) { // 循环遍历字符串
            if (Character.isDigit(strContent.charAt(i))) { // 用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            }
            if (Character.isLetter(strContent.charAt(i))) { // 用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        return isDigit && isLetter;
    }

    /**
     * 是否含有字母
     *
     * @param strContent
     * @return
     */
    public static boolean hasLetter(String strContent) {
        boolean isLetter = false;// 定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < strContent.length(); i++) { // 循环遍历字符串
            if (Character.isLetter(strContent.charAt(i))) { // 用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
                break;
            }
        }
        return isLetter;
    }

    /**
     * 是否含有中文
     *
     * @param content
     * @return
     */
    public static boolean hasChinese(String content) {
        String regEx = "[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(content);
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                return true;
            }
        }
        return false;
    }

//    /**
//     * 检测商品条码是否合法
//     *
//     * @param barCode
//     * @return
//     */
//    public static boolean checkEanCodeIsError(String barCode) {
//        if (barCode.length() != 13) {
//            return true;
//        }
//        String code = barCode.substring(0, 12);
//        String checkBit = barCode.substring(12, 13);
//        int c1 = 0, c2 = 0;
//        for (int i = 0; i < code.length(); i += 2) {
//            char c = code.charAt(i);
//            int n = c - '0';
//            c1 += n;
//        }
//        for (int i = 1; i < code.length(); i += 2) {
//            char c = code.charAt(i);
//            int n = c - '0';
//            c2 += n;
//        }
//        String check = (10 - (c1 + c2 * 3) % 10) % 10 + "";
//        if (check.equals(checkBit)) {
//            return false;
//        } else {
//            return true;
//        }
//    }

    // 判断一个字符串是否都为数字
    public static boolean isDigit(String strNum) {
        return strNum.matches("[0-9]{1,}");
    }

    // 判断一个字符串是否都为数字
    public static boolean isDigits(String strNum) {
        Pattern pattern = Pattern.compile("[0-9]{1,}");
        Matcher matcher = pattern.matcher((CharSequence) strNum);
        return matcher.matches();
    }

    //计算商品最后一位
    public static String getLastCode(String code) {
//        String code = barCode.substring(0, barCode.length() - 1);
//        String checkBit = barCode.substring(barCode.length() - 1);
        int c1 = 0, c2 = 0;
        for (int i = 0; i < code.length(); i += 2) {
            char c = code.charAt(i);
            int n = c - '0';
            c1 += n;
        }
        for (int i = 1; i < code.length(); i += 2) {
            char c = code.charAt(i);
            int n = c - '0';
            c2 += n;
        }
//        String check = (10 - (c1 + c2 * 3) % 10) % 10 + "";
        String check = getMod10CheckDigit(code) + "";

        return check;
//        if (check.equals(checkBit)) {
//            return false;
//        } else {
////            log.info("条码识别 校验位异常 {} {} 正确校验位: {}", shopId, barCode, check);
//            return true;
//        }
    }

    /**
     * 检测商品条码是否合法
     *
     * @param barCode
     * @return
     */
    public static boolean checkEanCodeIsError(String barCode, int num) {
        if (barCode.length() != num) {
            return true;
        }
        try {
            String code = barCode.substring(0, num - 1);
            String checkBit = barCode.substring(num - 1, num);
            int c1 = 0, c2 = 0;
            for (int i = 0; i < code.length(); i += 2) {
                char c = code.charAt(i);
                int n = c - '0';
                c1 += n;
            }
            for (int i = 1; i < code.length(); i += 2) {
                char c = code.charAt(i);
                int n = c - '0';
                c2 += n;
            }
            String check = (10 - (c1 + c2 * 3) % 10) % 10 + "";
            if (check.equals(checkBit)) {
                return false;
            } else {
                if (checkBit.equals(getMod10CheckDigit(code) + "")) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (Exception e) {
            return true;
        }
    }

    //18位saas 条码 寺岗验证规则
    public static char getMod10CheckDigit(String barcodeString) {
        int a = 0, b = 0, c = 0;
        int d = 0;
        char[] charArray = barcodeString.toCharArray();

        // Step 1: Sum all of the digits in the odd positions together.
        for (int i = barcodeString.length() - 1; i >= 0; i = i - 2) {
            if (charArray[i] > '9' || charArray[i] < '0')
                return (char) 0;
            a = a + charArray[i] - 48;
        }

        // Step 2: Mutliply the sum from Step 1 by 3.
        a = a * 3;

        // Step 3: Sum all of the digits in the even positions together.
        for (int i = barcodeString.length() - 2; i >= 0; i = i - 2) {
            if (charArray[i] > '9' || charArray[i] < '0')
                return (char) 0;
            b = b + charArray[i] - 48;
        }

        // Step 4: Sum together the results from Step 2 and Step 3.
        c = a + b;

        // Step 5: Subtract the sum from the next highest multiple of 10.
        d = c % 10;
        if (d != 0) {
            d = 10 - d;
        }
        return (char) (d + 48);
    }
}
