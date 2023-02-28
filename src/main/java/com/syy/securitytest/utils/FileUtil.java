package com.syy.securitytest.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {
    public static void write(String path,String data) throws IOException {
        Files.write(Paths.get(new File(path).getPath()),data.getBytes());
    }


    public static String read(String path) throws IOException {
        File file = new File(path);
        byte[] b = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        return new String(b);
    }

    public static String randomName(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int k = (int)(Math.random()*10);
            sb.append(k);
        }
        return sb.toString();
    }
}
