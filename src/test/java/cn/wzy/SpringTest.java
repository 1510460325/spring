package cn.wzy;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

public class SpringTest {

	public static void main(String[] args) {
		BeanFactory factory = new ClassPathXmlApplicationContext("asdf");
		BeanFactory factory1 = new FileSystemXmlApplicationContext("asdf");
		BeanFactory factory2 = new XmlBeanFactory(new FileSystemResource("asdf"));//废弃
	}
}
