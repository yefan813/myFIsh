package com.frame.service.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(AESUtils.class);

    public AESUtils() {
    }

    public static byte[] encrypt(byte[] src, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec securekey = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(1, securekey);
        return cipher.doFinal(src);
    }

    public static byte[] decrypt(byte[] src, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec securekey = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(2, securekey);
        return cipher.doFinal(src);
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";

        for(int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 255);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }

        return hs.toUpperCase();
    }

    public static byte[] hex2byte(byte[] b) {
        if (b.length % 2 != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        } else {
            byte[] b2 = new byte[b.length / 2];

            for(int n = 0; n < b.length; n += 2) {
                String item = new String(b, n, 2);
                b2[n / 2] = (byte)Integer.parseInt(item, 16);
            }

            return b2;
        }
    }

    public static final String decrypt(String data, String key) {
        try {
            return new String(decrypt((byte[])hex2byte(data.getBytes()), key));
        } catch (Exception var3) {
            LOGGER.error("解密失败", var3);
            throw new RuntimeException("解密失败", var3);
        }
    }

    public static final String encrypt(String data, String key) {
        try {
            return byte2hex(encrypt((byte[])data.getBytes(), key));
        } catch (Exception var3) {
            LOGGER.error("加密失败", var3);
            throw new RuntimeException("加密失败", var3);
        }
    }

    public static void main(String[] args) {
        String str = encrypt("app_id=1&format=json&method=abc&timestamp=1&version=2.0&sign=d888cb4eed17344d363466b2ba9ecf25&name=bbbbb", "1111111122222222");
        System.out.println("str:" + str);
        System.out.println(System.currentTimeMillis());
    }
}