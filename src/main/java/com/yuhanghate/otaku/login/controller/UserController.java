package com.yuhanghate.otaku.login.controller;

import com.yuhanghate.otaku.login.entity.UserEntity;
import com.yuhanghate.otaku.login.service.UserService;
import com.yuhanghate.otaku.login.utils.RxRegTool;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/user")
public class UserController {

    /**
     * 服务
     */
    @Resource
    private UserService mUserService;

    /**
     * 登陆
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(UserEntity userEntity) {


        if (!RxRegTool.isUsername(userEntity.getUsername())) {
            return resultData(40003, "帐号不正确,用户名必须是6-20位", userEntity);
        }
        UserEntity entity = mUserService.query(userEntity);
        if (entity != null) {
            return resultData(200, "登陆成功", userEntity);
        } else {
            return resultData(40002, "帐号或密码不正确", userEntity);
        }

    }

    /**
     * 注册
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Map<String, Object> register(UserEntity userEntity) {
        if (!RxRegTool.isUsername(userEntity.getUsername())) {
            return resultData(40003, "帐号不正确,用户名必须是6-20位", userEntity);
        }
        boolean isCheck = mUserService.checkUserInfo(userEntity);
        if (!isCheck) {
            mUserService.insert(userEntity);
            return resultData(200, "注册成功", userEntity);
        } else {
            return resultData(40001, "当前帐号已存在", userEntity);
        }


    }


    private Map<String, Object> resultData(int code, String msg, Object data) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("msg", msg);
        map.put("data", data);
        return map;
    }
}
