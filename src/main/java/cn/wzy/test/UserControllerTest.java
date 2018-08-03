package cn.wzy.test;

import cn.wzy.DI.BeanFactory;
import cn.wzy.controller.UserController;

/**
 * Create by Wzy
 * on 2018/8/3 14:33
 * 不短不长八字刚好
 */
public class UserControllerTest {


    public static void main(String[] args) {
        UserController userController = BeanFactory.getBean(UserController.class);
        userController.save();
    }


}
