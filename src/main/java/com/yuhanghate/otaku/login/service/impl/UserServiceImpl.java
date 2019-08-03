package com.yuhanghate.otaku.login.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuhanghate.otaku.login.entity.UserEntity;
import com.yuhanghate.otaku.login.service.UserService;
import com.yuhanghate.otaku.login.utils.FileUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    public static final String DIR_USER_FILE = System.getProperty("user.dir") + File.separator + "userinfo" + File.separator;
    public static final String USER_FILE_NAME = "userinfo";

    @Override
    public void insert(UserEntity userEntity) {


        String file = DIR_USER_FILE + USER_FILE_NAME + ".json";
        Gson gson = new Gson();
        try {
            if (!FileUtil.isFileExist(file)) {
                //不存在
                ArrayList<UserEntity> entities = new ArrayList<>();
                entities.add(userEntity);
                FileUtil.createJsonFile(gson.toJson(entities), DIR_USER_FILE, USER_FILE_NAME);
            } else {
                String content = FileUtil.getFileContent(DIR_USER_FILE + USER_FILE_NAME+".json");
                List<UserEntity> list = gson.fromJson(content, new TypeToken<List<UserEntity>>() {
                }.getType());
                list.add(userEntity);
                FileUtil.createJsonFile(gson.toJson(list), DIR_USER_FILE, USER_FILE_NAME);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(UserEntity userEntity) {
        String file = DIR_USER_FILE + USER_FILE_NAME + ".json";
        Gson gson = new Gson();
        try {
            if (FileUtil.isFileExist(file)) {
                String content = FileUtil.getFileContent(DIR_USER_FILE + USER_FILE_NAME+".json");
                List<UserEntity> list = gson.fromJson(content, new TypeToken<List<UserEntity>>() {
                }.getType());
                list.removeIf(entity -> entity.getUsername().equals(userEntity.getUsername()));

                FileUtil.createJsonFile(gson.toJson(list), DIR_USER_FILE, USER_FILE_NAME);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkUserInfo(UserEntity userEntity) {
        String file = DIR_USER_FILE + USER_FILE_NAME+".json";
        Gson gson = new Gson();
        try {
            if (FileUtil.isFileExist(file)) {
                String content = FileUtil.getFileContent(DIR_USER_FILE + USER_FILE_NAME+".json");
                List<UserEntity> list = gson.fromJson(content, new TypeToken<List<UserEntity>>() {
                }.getType());
                for (UserEntity user : list) {
                    if (user.getUsername().equals(userEntity.getUsername())) {
                        return true;
                    }
                }
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public UserEntity query(UserEntity userEntity) {
        String file = DIR_USER_FILE + USER_FILE_NAME +".json";
        Gson gson = new Gson();
        try {
            if (FileUtil.isFileExist(file)) {
                String content = FileUtil.getFileContent(DIR_USER_FILE + USER_FILE_NAME+".json");
                List<UserEntity> list = gson.fromJson(content, new TypeToken<List<UserEntity>>() {
                }.getType());
                for (UserEntity user : list) {
                    if (user.getUsername().equals(userEntity.getUsername()) && user.getPassword().equals(userEntity.getPassword())) {
                        return user;
                    }
                }
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
