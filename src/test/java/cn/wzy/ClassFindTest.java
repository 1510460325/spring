package cn.wzy;


import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.io.Resource;

public class ClassFindTest {

	public static void main(String[] args) {

		//维护一个Map<beanName, definition>的ioc容器仓库。
		DefaultListableBeanFactory factory;

		//包装配置文件（如xml文件）
		Resource resource;

		//把Resource中的信息加载成Document然后再加载成Definition包装成BeanDefinitionHolder
		BeanDefinitionReader reader;

		//包装一个definition
		BeanDefinitionHolder holder;

		//定义一个bean
		BeanDefinition definition1;
		//维护一个PropertyValue的列表
		MutablePropertyValues mutablePropertyValues;
		//存储属性值得关系
		PropertyValue propertyValue;


	}
}
