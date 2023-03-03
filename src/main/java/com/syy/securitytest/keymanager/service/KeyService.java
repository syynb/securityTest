package com.syy.securitytest.keymanager.service;

import com.syy.securitytest.dto.R;
import com.syy.securitytest.dto.UserDto;

public interface KeyService {

    /**
     * 生成密钥,存入数据库
     * @param dto
     * @return
     */
    public R generateKey(UserDto dto);

    /**
     * 更新密钥
     * @param dto
     * @return
     */
    public R updateKey(UserDto dto);

    /**
     * 删除密钥
     * @param dto
     * @return
     */
    public R deleteKey(UserDto dto);

    /**
     * 加密
     * @param message
     * @return
     */
    public R encrypt(String message);

    /**
     * 向用户发送密钥
     * @param dto
     * @return
     */
    public R sendKey(UserDto dto);

}
