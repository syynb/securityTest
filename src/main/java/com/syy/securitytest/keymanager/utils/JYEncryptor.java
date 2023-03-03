package com.syy.securitytest.keymanager.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class JYEncryptor {

    /**
     * 使用根密钥进行加密信息
     * @param rootKey   根密钥
     * @param message   明文信息
     * @return          密文信息
     */
    public static String getEncode(String rootKey,String message) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        //盐值
        standardPBEStringEncryptor.setPassword(rootKey);
        //加密明文
        return standardPBEStringEncryptor.encrypt(message);
    }

}
