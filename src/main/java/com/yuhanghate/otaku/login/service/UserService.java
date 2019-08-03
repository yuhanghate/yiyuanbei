package com.yuhanghate.otaku.login.service;

import com.yuhanghate.otaku.login.entity.UserEntity;

public interface UserService {

    /**
     * 插入帐号
     * @param userEntity
     */
    void insert(UserEntity userEntity);

    /**
     * 删除帐号
     * @param userEntity
     */
    void delete(UserEntity userEntity);

    /**
     * 检测帐号
     * @return true: 存在  false: 不存在
     */
    boolean checkUserInfo(UserEntity userEntity);

    UserEntity query(UserEntity userEntity);
}
