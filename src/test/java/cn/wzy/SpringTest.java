package cn.wzy;

import cn.wzy.service.UserService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

	public static void main(String[] args) {
		BeanFactory factory = new ClassPathXmlApplicationContext("ApplicationContext.xml");
		UserService service = (UserService) factory.getBean("bean1");
		UserService service1 = (UserService) factory.getBean("bean1");
		System.out.println(service == service1);
	}
}
