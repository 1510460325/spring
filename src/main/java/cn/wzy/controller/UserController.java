package cn.wzy.controller;

import cn.wzy.DI.Inject;
import cn.wzy.service.UserService;

/**
 * Create by Wzy
 * on 2018/8/3 14:31
 * 不短不长八字刚好
 */
public class UserController {

    @Inject
    private UserService userService;

    public void save() {
        userService.save();
    }

    public UserController() {
    }
}
