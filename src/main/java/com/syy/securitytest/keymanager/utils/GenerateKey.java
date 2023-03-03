package com.syy.securitytest.keymanager.utils;

import java.security.SecureRandom;

public class GenerateKey {

    /**
     * 随机生成密钥
     * @return  密钥
     */
    public static String generateKey(){
        byte[] key = new byte[128];
        SecureRandom random = new SecureRandom();
        random.nextBytes(key);
        return new String(key);
    }

}
