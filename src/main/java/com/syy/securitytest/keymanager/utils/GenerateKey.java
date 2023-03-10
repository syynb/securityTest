package com.syy.securitytest.keymanager.utils;

import java.security.SecureRandom;

public class GenerateKey {

    /**
     * 随机生成密钥
     * @return  密钥
     */
    public static String generateKey(){
        byte[] key = new byte[32];
        SecureRandom random = new SecureRandom();
        random.nextBytes(key);
        return toHexString1(key);
    }

    /**
     * 数组转成十六进制字符串
     * @param b
     * @return HexString
     */
    public static String toHexString1(byte[] b){
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; ++i){
            buffer.append(toHexString1(b[i]));
        }
        return buffer.toString();
    }
    public static String toHexString1(byte b){
        String s = Integer.toHexString(b & 0xFF);
        if (s.length() == 1){
            return "0" + s;
        }else{
            return s;
        }
    }


}
