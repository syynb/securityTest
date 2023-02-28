package com.syy.securitytest;

import com.syy.securitytest.utils.SecretKeyUtil;
import com.webank.keysign.utils.Numeric;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
@Slf4j
public class SecurityTestApplication {

    public static String[] files;

    public static void main(String[] args) {
        log.info(String.valueOf(Arrays.asList(args)));
        files = args;
        SpringApplication.run(SecurityTestApplication.class, args);
    }

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() throws IOException {
        List<String> paths = new ArrayList<>();
        paths.add(files[0]);
        paths.add(files[1]);
        paths.add(files[2]);
//        List<String> paths = new ArrayList<>(Arrays.asList(args));
        /**
         * 读取文件，还原私钥并展示
         */
        String recoveredKey = Numeric.toHexString(SecretKeyUtil.rebuildKey(paths));
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(recoveredKey);
        config.setPoolSize("1");
        encryptor.setConfig(config);
        return encryptor;
    }


}
