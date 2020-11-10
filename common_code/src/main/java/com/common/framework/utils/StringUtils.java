package com.common.framework.utils;

import android.graphics.Color;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @类名： StringUtils
 * @功能说明：
 * @修改内容：
 * @修改人：
 * @修改时间：
 */
public class StringUtils {

    // BUFFER大小
    public static int BUFFER_SIZE = 512;

    /**
     * 判断是否含有特殊字符
     *
     * @param str
     * @return
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 隐藏字符
     *  0-6不隐藏，7-10，12+隐藏前4位，11隐藏4-7位
     * @param str
     * @return
     */
    public static String setHideWord(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (str.length() > 0 && str.length() <= 6) {
            return str;
        } else if ((str.length() > 6 && str.length() <= 10) || str.length() > 11) {
            return "****" + str.substring(4, str.length());
        } else if (str.length() == 11) {
            return str.substring(0, 3) + "****" + str.substring(str.length() - 4, str.length());
        }
        return "";
    }


    /**
     * 字符串非空校验
     *
     * @param str 需校验字符串
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为空或null 输入参数
     *
     * @param target
     * @return
     */
    public static boolean checkNull(Object target) {
        if (target == null || "".equals(target.toString().trim()) || "null".equalsIgnoreCase(target.toString().trim())) {
            return true;
        }
        return false;
    }

    /**
     * 字符串null转化为空
     *
     * @param str 需转化字符串
     * @return
     */
    public static String nullToBlank(String str) {
        return (str == null ? "" : str);
    }

    /**
     * 去掉字符串首尾，中间的空格，trim()，不仅仅是去掉空格，此处主要是增加去掉中间的空格
     *
     * @param str
     * @return
     */
    public static String removeSpace(String str) {

        if (!TextUtils.isEmpty(str)) {
            return str.trim().replaceAll(" ", "");
        } else {
            return str;
        }
    }

    /**
     * 将输入流转化成字符串
     *
     * @param encoding 字符编码类型,如果encoding传入null，则默认使用utf-8编码。
     * @return 字符串
     * @throws IOException
     * @author lvmeng
     */
    public static String inputToString(InputStream inputStream, String encoding)
            throws IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        inputStream.close();
        bos.close();
        if (TextUtils.isEmpty(encoding)) {
            encoding = "UTF-8";
        }
        return new String(bos.toByteArray(), encoding);
    }

    /**
     * 设置需要高亮的字
     *
     * @param wholeText    原始字符串
     * @param spanableText 需要高亮的字符串
     * @return 高亮后的字符串
     */
    public static SpannableString getSpanableText(String wholeText,
                                                  String spanableText) {
        if (TextUtils.isEmpty(wholeText)) {
            wholeText = "";
        }
        SpannableString spannableString = new SpannableString(wholeText);
        if (spanableText.equals("")) {
            return spannableString;
        }
        wholeText = wholeText.toLowerCase(Locale.getDefault());
        spanableText = spanableText.toLowerCase(Locale.getDefault());
        int startPos = wholeText.indexOf(spanableText);
        if (startPos == -1) {
            int tmpLength = spanableText.length();
            String tmpResult = "";
            for (int i = 1; i <= tmpLength; i++) {
                tmpResult = spanableText.substring(0, tmpLength - i);
                int tmpPos = wholeText.indexOf(tmpResult);
                if (tmpPos == -1) {
                    tmpResult = spanableText.substring(i, tmpLength);
                    tmpPos = wholeText.indexOf(tmpResult);
                }
                if (tmpPos != -1) {
                    break;
                }
                tmpResult = "";
            }
            if (tmpResult.length() != 0) {
                return getSpanableText(wholeText, tmpResult);
            } else {
                return spannableString;
            }
        }
        int endPos = startPos + spanableText.length();
        do {
            endPos = startPos + spanableText.length();
            spannableString.setSpan(new BackgroundColorSpan(Color.YELLOW),
                    startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            startPos = wholeText.indexOf(spanableText, endPos);
        } while (startPos != -1);
        return spannableString;
    }

    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int i = 0; i < b.length; i++) {
            stmp = Integer.toHexString(b[i] & 0xFF).toUpperCase();
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString();
    }


    /**
     * 2进制转换16进制
     *
     * @param bString 2进制串
     * @return hexString 16进制串
     */
    public static String binaryString2hexString(String bString) {
        if (bString == null || bString.equals("") || bString.length() % 8 != 0) {
            return null;
        }
        StringBuffer tmp = new StringBuffer();
        int iTmp = 0;
        for (int i = 0; i < bString.length(); i += 4) {
            iTmp = 0;
            for (int j = 0; j < 4; j++) {
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            tmp.append(Integer.toHexString(iTmp));
        }
        return tmp.toString();
    }

    /**
     * 16进制转换2进制
     *
     * @param hexString 16进制串
     * @return bString 2进制串
     */
    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0) {
            return null;
        }
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000"
                    + Integer.toBinaryString(Integer.parseInt(
                    hexString.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    /**
     * byte数组转换为十六进制字符串
     *
     * @return HexString 16进制串
     */
    public static String bytesToHexString(byte[] bArray) {
        StringBuilder sb = new StringBuilder(bArray.length);
        String sTemp;
        for (byte element : bArray) {
            sTemp = Integer.toHexString(255 & element);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp);
        }
        return sb.toString();
    }

    /**
     * 十六进制字符串转换为byte数据
     *
     * @param hexString 16进制串
     * @return byte[] byte数组
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || "".equals(hexString)) {
            return null;
        }

        String upperHexString = hexString.toUpperCase();
        int length = upperHexString.length() / 2;
        char[] hexChars = upperHexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * char转换为byte
     *
     * @param ch
     * @return
     */
    private static byte charToByte(char ch) {
        return (byte) "0123456789ABCDEF".indexOf(ch);
    }


    public static Integer getStrPhysicsLength(String str) {
        int i = str.length();
        char[] chars = str.toCharArray();
        for (char temp : chars) {
            if (19968 <= temp && temp <= 40869 || isChinese(temp)) {
                i++;
            }
        }
        return i;
    }

    private static boolean isChinese(char c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
            if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) {
                return true;
            } else if (ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS) {
                return true;
            } else if (ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) {
                return true;
            } else if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) {
                return true;
            } else if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B) {
                return true;
            } else if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C) {
                return true;
            } else if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D) {
                return true;
            } else if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
                return true;
            } else if (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
                return true;
            }
        }
        return false;
    }

    public static String filterEnterAndComma(String originString) {
        if (!TextUtils.isEmpty(originString)) {
            return originString.replaceAll("\n", "").replaceAll(",", "").replace(" ", "");
        }
        return " ";
    }
}
