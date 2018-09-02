package cn.wzy.controller;

import cn.wzy.annotation.WzyAutowired;
import cn.wzy.annotation.WzyController;
import cn.wzy.annotation.WzyRequestMapping;
import cn.wzy.annotation.WzyRequestParam;
import cn.wzy.service.UserService;
import com.alibaba.druid.support.json.JSONUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/2 14:17
 */
@WzyController
@WzyRequestMapping("/user")
public class UserController {

	@WzyAutowired
	private UserService service;

	@WzyRequestMapping("/hello.do")
	public String hello(@WzyRequestParam("id") Integer id,
	                    @WzyRequestParam("name") String name) throws IOException {
		return service.sayHello(id,name);
	}

	@WzyRequestMapping("/hello2.do")
	public Object hello2(@WzyRequestParam("id") Integer id,
	                    @WzyRequestParam("name") String name) throws IOException {
		Map<String,Object> datas = new HashMap<>();
		datas.put("id",id);
		datas.put("name",name);
		return JSONUtils.toJSONString(datas);
	}

}
