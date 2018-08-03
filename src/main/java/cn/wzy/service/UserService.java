package cn.wzy.service;

import cn.wzy.dao.ClassDao;
import cn.wzy.dao.UserDao;
import cn.wzy.DI.Inject;

/**
 * Create by Wzy
 * on 2018/8/3 14:29
 * 不短不长八字刚好
 */
public class UserService {

    @Inject
    private UserDao userDao;

    @Inject
    private ClassDao classDao;

    public void save() {
        userDao.save();
        classDao.save();
    }

    public UserService() {
    }
}
