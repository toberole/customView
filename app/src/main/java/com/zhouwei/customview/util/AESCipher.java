package com.zhouwei.customview.util;//
//  https://github.com/WelkinXie/AESCipher-Java
//

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 速度比较快
 */
public class AESCipher {

    /**
     * Initialization Vector
     */
    private static final String IV_STRING = "A-16-Byte-String";
    private static final String charset = "UTF-8";


    public static byte[] aesEncryptBytes(byte[] contentBytes, byte[] keyBytes) {
        try {
            return cipherOperation(contentBytes, keyBytes, Cipher.ENCRYPT_MODE);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] aesDecryptBytes(byte[] contentBytes, byte[] keyBytes) {
        try {
            return cipherOperation(contentBytes, keyBytes, Cipher.DECRYPT_MODE);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String aesEncryptString(String content, String key) {
        try {
            byte[] contentBytes = content.getBytes(charset);
            byte[] keyBytes = key.getBytes(charset);
            byte[] encryptedBytes = aesEncryptBytes(contentBytes, keyBytes);
            return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String aesDecryptString(String content, String key) {
        try {
            byte[] encryptedBytes = Base64.decode(content, Base64.DEFAULT);
            Base64.decode(content, Base64.DEFAULT);
            byte[] keyBytes = key.getBytes(charset);
            byte[] decryptedBytes = aesDecryptBytes(encryptedBytes, keyBytes);
            return new String(decryptedBytes, charset);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private static byte[] cipherOperation(byte[] contentBytes, byte[] keyBytes, int mode) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

            byte[] initParam = IV_STRING.getBytes(charset);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(mode, secretKey, ivParameterSpec);

            return cipher.doFinal(contentBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
