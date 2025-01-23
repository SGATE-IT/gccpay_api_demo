package com.gccpay.utils;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
/**
 * RSA签名工具类
 * @author liu
 * @date 2023/04/01 16:09
 */
public class RSAUtils {
    /**
     * 私钥签名
     * @param data
     * @param privateKey
     * @return
     */
    public static String sign(String data, String privateKey){
        try {
            byte[] keyBytes = Base64.getDecoder().decode(privateKey);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKeyObj = keyFactory.generatePrivate(keySpec);

            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKeyObj);
            signature.update(data.getBytes());
            byte[] signedBytes = signature.sign();
            return Base64.getEncoder().encodeToString(signedBytes);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("signature failed");
        }

    }
    /**
     * 验证签名
     * @param data
     * @param signature
     * @param publicKey
     * @return
     */
    public static boolean verify(String data, String signature, String publicKey) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKeyObj = keyFactory.generatePublic(keySpec);

            Signature signatureObj = Signature.getInstance("SHA256withRSA");
            signatureObj.initVerify(publicKeyObj);
            signatureObj.update(data.getBytes());
            byte[] signatureBytes = Base64.getDecoder().decode(signature);
            return signatureObj.verify(signatureBytes);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
}
