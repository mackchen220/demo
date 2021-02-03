package org.jeecg.modules.commons.util;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import lombok.extern.log4j.Log4j2;

import java.net.URLEncoder;
import java.nio.charset.Charset;

@Log4j2
public class Security {

    private static final byte[] keystr = {13, 24, 46, 110, 38, 102, 12, 36};
    private static final byte[] ivstr = {15, 37, 14, 115, 31, 82, 102, 20};

    private final static String AES_KEY = "1234567890adbcde";// AES密匙
    private final static String AES_IV = "1234567890hjlkew";// AES偏移量

    public static String encrypt(String content, String charset) {
        //构建
        DES des = SecureUtil.des(keystr);
        des.setIv(ivstr);
        return des.encryptHex(content, charset);
    }

    public static String decrypt(String content, String charset) {
        //构建
        DES des = SecureUtil.des(keystr);
        des.setIv(ivstr);
        return des.decryptStr(content, Charset.forName(charset));
    }

    public static String aesEncrypt(String content) {
        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, AES_KEY.getBytes(), AES_IV.getBytes());
        return aes.encryptBase64(content, "UTF-8");
    }

    public static String aesEncryptUrl(String content) {
        try {
            AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, AES_KEY.getBytes(), AES_IV.getBytes());
            //URL加密
            return URLEncoder.encode(aes.encryptBase64(content, "UTF-8"), "UTF-8");
        } catch (Exception e) {
            log.error("aesEncrypt error:" + e.getMessage(), e);
            return null;
        }
    }



}
