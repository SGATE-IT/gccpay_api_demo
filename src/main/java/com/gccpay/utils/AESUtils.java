package com.gccpay.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
/**
 * AES 加解密工具类
 * */
public class AESUtils {
    /**
     * AES加密
     * @param content 需要加密的内容
     * @param key 加密密码
     * @return
     */
    public static String encrypt(String content, String key) {
        try {

            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");

            // 使用指定的IV
            IvParameterSpec ivParameterSpec = new IvParameterSpec(new byte[16]);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encryptedData = cipher.doFinal(content.getBytes("utf-8"));

            return Base64.getEncoder().encodeToString(encryptedData);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Decryption failed");
        }
    }
    /**
     * AES解密
     * @param encryptedData 已加密内容
     * @param key 加密密码
     * @return
     */
    public static String decrypt(String encryptedData, String key) {
        try {
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(new byte[16]);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Decryption failed");
        }
    }
}
