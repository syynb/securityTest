package com.syy.securitytest.utils;
import com.webank.keygen.exception.KeyGenException;
import com.webank.keygen.model.PkeyInfo;
import com.webank.keygen.model.PubKeyInfo;
import com.webank.keygen.service.PkeyByRandomService;
import com.webank.keygen.service.PkeyShardingService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SecretKeyUtil {
    /**
     * 生成密钥对
     * @return  返回私钥信息，包含公钥
     */
    public static PkeyInfo generateKeyPair() throws KeyGenException {
        PkeyByRandomService service = new PkeyByRandomService();
        return service.generatePrivateKey();
    }

    /**
     * 生成公钥
     * @return  返回公钥信息
     */
    public static PubKeyInfo getPubKeyInfo(PkeyInfo info){
        return info.getPublicKey();
    }


    /**
     * 分散存储私钥
     * @param info  私钥信息
     * @return  返回文件存储路径
     */
    public static List<String> storePKey(PkeyInfo info) throws IOException {
        PkeyShardingService shardingService = new PkeyShardingService();
        /**
         * 可配置：n:分几段    k:拿到最少k段可还原
         */
        List<String> shards = shardingService.shardingPKey(info.getPrivateKey(), 5, 3);
        List<String> files = new ArrayList<>();
        for(String piece : shards){
            /**
             * 文件名一般化
             */
            String name = FileUtil.randomName();
            FileUtil.write(name,piece);
            files.add(name);
        }
        return files;
    }

    /**
     * 还原私钥
     * @param paths  文件名列表
     * @return  私钥
     */
    public static byte[] rebuildKey(List<String> paths) throws IOException {
        PkeyShardingService shardingService = new PkeyShardingService();
        List<String> pieces = new ArrayList<>();
        for(String path:paths){
            String b = FileUtil.read(path);
            pieces.add(b);
        }
        return shardingService.recoverPKey(pieces);
    }
}
