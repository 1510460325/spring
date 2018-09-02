package cn.wzy.servlet;

import cn.wzy.annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/2 13:58
 */
public class WzyDispatchServlet extends HttpServlet {

	private Properties properties = new Properties();

	/**
	 * 扫描的所有类名
	 */
	private List<String> classNames = new ArrayList<>();

	/**
	 * ioc实例化容器
	 */
	private Map<String,Object> ioc = new HashMap<>();

	/**
	 * url路径
	 */
	private Map<String,Handler> handlerMapping = new HashMap<>();

	@Override
	public void init(ServletConfig config) throws ServletException {
		//1、加载配置文件
		loadConfig(config.getInitParameter("contextConfigLocation"));
		//2、扫描文件
		scanner(properties.getProperty("package"));
		//3、初始化示例
		instance();
		//4、自动注入
		autowired();
		//5、初始化handlerMapping
		initHandlerMapping();
	}

	/**
	 * 初始化请求绑定
	 */
	private void initHandlerMapping() {
		for (Object value: ioc.values()) {
			Class<?> clazz = value.getClass();
			if (!clazz.isAnnotationPresent(WzyController.class)) {
				continue;
			}
			String baseUrl = "";
			if (clazz.isAnnotationPresent(WzyRequestMapping.class)) {
				WzyRequestMapping url = clazz.getAnnotation(WzyRequestMapping.class);
				if (!url.value().equals("")) {
					baseUrl += url.value();
				}
			}

			Method[] methods = clazz.getDeclaredMethods();
			for (Method method: methods) {
				if (!method.isAnnotationPresent(WzyRequestMapping.class)) {
					continue;
				}
				WzyRequestMapping url = method.getAnnotation(WzyRequestMapping.class);
				Handler handler = new Handler();
				handler.controller = value;
				handler.method = method;
				handler.url = baseUrl + url.value();
				handlerMapping.put(handler.url,handler);
			}
		}
	}

	/**
	 * 依赖注入
	 */
	private void autowired() {
		if (ioc.isEmpty()) {
			return;
		}
		for (Map.Entry<String,Object> entry: ioc.entrySet()) {
			Field[] fields = entry.getValue().getClass().getDeclaredFields();
			for (Field field: fields) {
				if (!field.isAnnotationPresent(WzyAutowired.class)) {
					continue;
				}

				WzyAutowired autowired = field.getAnnotation(WzyAutowired.class);
				String beanName = autowired.value();
				if (beanName.equals("")) {
					beanName = lowerFirst(field.getType().getName());
				}
				field.setAccessible(true);
				try {
					field.set(entry.getValue(),ioc.get(beanName));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					System.out.println("===Autowired " + field.getName() + " failed=====");
				}
			}
		}
	}

	/**
	 * 实例化
	 */
	private void instance() {
		if (classNames.isEmpty())
			return;
		try {
		for (String className: classNames) {
				Class<?> clazz = Class.forName(className);
				if (clazz.isAnnotationPresent(WzyController.class)) {
					String beanName = lowerFirst(clazz.getSimpleName());
					ioc.put(beanName,clazz.newInstance());
				} else if (clazz.isAnnotationPresent(WzyService.class)) {
					WzyService service = clazz.getAnnotation(WzyService.class);
					String beanName = service.value();
					if (beanName.equals("")) {
						beanName = lowerFirst(clazz.getSimpleName());
					}
					Object instance = clazz.newInstance();
					ioc.put(beanName,instance);

					Class<?>[] interfaces = clazz.getInterfaces();
					for (Class<?> c: interfaces) {
						ioc.put(lowerFirst(c.getName()),instance);
					}
				}
			}
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}

	private String lowerFirst(String className) {
		char[] ch = className.toCharArray();
		ch[0] += 32;
		return new String(ch);
	}

	/**
	 * 递归扫描包下所有的类，存到list里面待下阶段生成实例
	 * @param packageName
	 */
	private void scanner(String packageName) {
		URL url = this.getClass().getClassLoader().getResource("/" + packageName.replace(".","/"));
		File classDir = new File(url.getFile());
		for (File file: classDir.listFiles()) {
			if (file.isDirectory()) {
				scanner(packageName + "." + file.getName());
			} else {
				classNames.add(packageName + "." + file.getName().replace(".class",""));
			}
		}
	}

	/**
	 * 加载配置文件
	 * @param location
	 */
	private void loadConfig(String location) {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(location);
		try {
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * handlerMapping处理类
	 */
	private class Handler{
		protected Object controller;
		protected Method method;
		protected String url;

		public Handler() {
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doDispatcher(req,resp);
	}

	protected void doDispatcher(HttpServletRequest req, HttpServletResponse resp) {
		//获取请求url
		String url = req.getRequestURI();
		url = url.replace(req.getContextPath(),"");
		//映射到具体实例方法
		Handler handler = handlerMapping.get(url);
		Method targetMethod = handler.method;
		//绑定请求参数到具体方法
		Class<?>[] types = targetMethod.getParameterTypes();
		Object[] params = new Object[types.length];
		int index = 0;
		Annotation[][] annotations = targetMethod.getParameterAnnotations();
		for (Annotation[] an1: annotations) {
			for (Annotation an: an1) {
				if (an instanceof WzyRequestParam) {
					String paramName = ((WzyRequestParam)an).value();
					Class<?> type = types[index];
					if (type == Integer.class)
						params[index] = Integer.parseInt(req.getParameter(paramName));
					else
						params[index] = req.getParameter(paramName);
				}
				index++;
			}
		}
		try {
			Object result = targetMethod.invoke(handler.controller,params);
			resp.setContentType("application/json");
			resp.setCharacterEncoding("utf-8");
			resp.getWriter().write(result.toString());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
