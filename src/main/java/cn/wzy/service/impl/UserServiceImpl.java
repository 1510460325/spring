package cn.wzy.service.impl;

import cn.wzy.annotation.WzyService;
import cn.wzy.service.UserService;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/2 14:21
 */
@WzyService
public class UserServiceImpl implements UserService {
	@Override
	public String sayHello(Integer id, String name) {
		return "hello " + id + " : " + name ;
	}
}
