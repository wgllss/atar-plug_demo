package com.common.framework.utils;


import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * @author：atar
 * @date: 2019/7/8
 * @description:
 */
public class SecurityUtils {

    /**
     * 公钥加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[] 加密数据
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws Exception {

        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        //产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

        //数据加密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");//Android上使用
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return cipher.doFinal(data);
    }

//    public static byte[] encryptByPublicKey(String keyGenerateAlgorithm, String publicKeyStr, byte[] data) throws Exception {
//        Cipher cipher = Cipher.getInstance(keyGenerateAlgorithm);
//        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(keyGenerateAlgorithm, publicKeyStr));
//        return cipher.doFinal(data);
//    }
//
//    //    public static byte[] encryptByPublicKey(String keyGenerateAlgorithm, String publicKeyStr, byte[] data) throws Exception {
////        Cipher cipher = Cipher.getInstance(keyGenerateAlgorithm);
////        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(keyGenerateAlgorithm, publicKeyStr));
////
////        return cipher.doFinal(data);
////    }
////
//    private static PublicKey getPublicKey(String keyGenerateAlgorithm, String publicKeyStr) throws Exception {
//        byte[] b = Base64.getDecoder().decode(publicKeyStr);
//        KeyFactory kf = KeyFactory.getInstance(keyGenerateAlgorithm);
//        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(b);
//        PublicKey key = kf.generatePublic(pubKeySpec);
//        return key;
//    }

}
