package com.common.framework.utils;

import android.content.Context;
import android.util.Base64;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @类名： CryptoUtils
 * @功能说明：常用加解密工具类
 * @修改内容：
 * @修改人：
 * @修改时间：
 */
public class CryptoUtils {
    private static final Charset CHAR_SET = Charset.forName("UTF-8");
    private static final String AES_CIPHER_MODE = "AES/ECB/PKCS5Padding";

    /**
     * 转换类算法，base64
     */
    /**
     * Base64编码转换
     *
     * @param data
     * @return
     */
    public static String encodeBase64(String data) {
        if (StringUtils.isEmpty(data)) {
            ZzLog.e("编码数据为空。");
            return null;
        }

        byte[] dataBase64 = Base64.encode(data.getBytes(CHAR_SET),
                Base64.DEFAULT);

        return new String(dataBase64);
    }

    /**
     * Base64解码转换
     *
     * @param data
     * @return
     */
    public static String decodeBase64(String data) {
        if (StringUtils.isEmpty(data)) {
            ZzLog.e("解码数据为空。");
            return null;
        }

        byte[] dataBase64 = Base64.decode(data.getBytes(CHAR_SET),
                Base64.DEFAULT);


        return new String(dataBase64);
    }


    /**
     * 摘要类算法，MD5,SHA
     */

    /**
     * 生成str对应的MD5串，128位摘要
     *
     * @param str
     * @return
     */
    public static String genMD5Str(String str) {
        if (StringUtils.isEmpty(str)) {
            ZzLog.e("加密数据为空。");
            return null;
        }

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        byte[] byteArray = str.getBytes(CHAR_SET);

        byte[] md5Bytes = md5.digest(byteArray);

        return StringUtils.bytesToHexString(md5Bytes);
    }

    /**
     * 生成str对应的SHA串，默认生成160位摘要
     *
     * @param str
     * @return
     */
    public static String genSHAStr(String str) {
        if (StringUtils.isEmpty(str)) {
            ZzLog.e("加密数据为空。");
            return null;
        }

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        byte[] byteArray = str.getBytes(CHAR_SET);
        byte[] digest = md.digest(byteArray);

        return StringUtils.bytesToHexString(digest);
    }

    /**
     * 生成str对应的SHA串
     *
     * @param str
     * @param algorithm （可取SHA,SHA-256,SHA-384,SHA-512）
     * @return
     */
    public static String genSHAStr(String str, String algorithm) {
        if (StringUtils.isEmpty(str)) {
            ZzLog.e("加密数据为空。");
            return null;
        }

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        byte[] byteArray = str.getBytes(CHAR_SET);
        byte[] digest = md.digest(byteArray);


        return StringUtils.bytesToHexString(digest);
    }

    /**
     * 对称加密类算法，AES
     */
    /**
     * AES加密，加密密钥key
     *
     * @param value
     * @param key
     * @return
     */
    public static String encryAES(String value, String key) {
        byte[] data = null;
        try {
            data = value.getBytes(CHAR_SET);
        } catch (Exception e) {
            e.printStackTrace();
        }

        data = encryAES(data, key);

        return StringUtils.bytesToHexString(data);
    }

    /**
     * AES加密，加密密钥key
     *
     * @param value
     * @param key
     * @return
     */
    public static byte[] encryAES(byte[] value, String key) {
        try {
            SecretKeySpec secretKeySpec = createKey(key);
            Cipher cipher = Cipher.getInstance(AES_CIPHER_MODE);
            cipher.init(1, secretKeySpec);
            byte[] result = cipher.doFinal(value);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES解密，解密密钥key
     *
     * @param value
     * @param key
     * @return
     */
    public static String decryAES(String value, String key) {
        byte[] data = null;
        try {
            data = StringUtils.hexStringToBytes(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        data = decryAES(data, key);
        if (data == null) {
            return "";
        }
        return new String(data, CHAR_SET);
    }

    /**
     * 生成AESkey
     *
     * @param key
     * @return
     */
    private static SecretKeySpec createKey(String key) {
        byte[] data = null;
        if (key == null) {
            key = "";
        }
        StringBuffer sb = new StringBuffer(32);
        sb.append(key);
        while (sb.length() < 32) {
            sb.append("0");
        }
        if (sb.length() > 32) {
            sb.setLength(32);
        }
        data = sb.toString().getBytes(CHAR_SET);
        return new SecretKeySpec(data, "AES");
    }

    /**
     * AES解密，解密密钥key
     *
     * @param value
     * @param key
     * @return
     */
    public static byte[] decryAES(byte[] value, String key) {
        try {
            SecretKeySpec secretKeySpec = createKey(key);
            Cipher cipher = Cipher.getInstance(AES_CIPHER_MODE);
            cipher.init(2, secretKeySpec);
            byte[] result = cipher.doFinal(value);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    /**
//     * AES加密，加密密钥是手机相关信息
//     *
//     * @param value
//     * @param context
//     * @return
//     */
//    public static String encryAES(String value, Context context) {
//        return encryAES(value, getSecretKey(context));
//    }
//
//    /**
//     * AES解密，解密密钥是手机相关信息
//     *
//     * @param value
//     * @param context
//     * @return
//     */
//    public static String decryAES(String value, Context context) {
//        return decryAES(value, getSecretKey(context));
//    }
//
////    /**
////     * 生成AES加密密钥-手机相关信息
////     *
////     * @param context
////     * @return
////     */
////    private static String getSecretKey(Context context) {
////        String deviceId = DeviceUtil.getDeviceId(context);
////
////        String result = "";
////        if (deviceId != null) {
////            long deviceIdLong = Long.parseLong(deviceId);
////            result = Long.toHexString(deviceIdLong << 2);
////        }
////
////        return genMD5Str(result);
////    }
}
