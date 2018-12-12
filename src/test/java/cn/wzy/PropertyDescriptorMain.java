package cn.wzy;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * 内省类的学习
 */
public class PropertyDescriptorMain {

	static class User{
		private String username="wzy";
		private String password = "wzy";

		private int age = 10;
		public String getUsername() {
			return username;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.username = password;
		}

		@Override
		public String toString() {
			return "User{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				", age=" + age +
				'}';
		}
	}
	public static void main(String[] args) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
		BeanInfo beanInfo= Introspector.getBeanInfo(User.class);
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		User user = new User();
		System.out.println(user);
		//遍历属性的方法
		for (PropertyDescriptor desc: propertyDescriptors) {
			System.out.println(desc.getName());
			if (desc.getName().equals("class")) {
				continue;
			}
			System.out.println(desc.getReadMethod());
			if (desc.getName().equals("age")) {
				System.out.println(desc.getWriteMethod().invoke(user,888));
				continue;
			}
			System.out.println(desc.getWriteMethod().invoke(user,"8888"));
		}
		System.out.println(user);

		//获取属性的方法
		PropertyDescriptor propertyDescriptor = new PropertyDescriptor("username",User.class);
		propertyDescriptor.getWriteMethod().invoke(user,"wzy");
		System.out.println(user);
	}
}
