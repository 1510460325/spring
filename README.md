# 学习spring ioc源码分析  
## 重要的类（或者接口）：  
* ***BeanFactory*** ：定义一系列的获取bean或者判断是否单例之类的方法  
* ***BeanDefinition*** ：Bean对象在Spring实现中是以BeanDefinition来描述的，Bean的定义类   
* ***BeanDefinitionReader*** ： 资源解析器解析资源，任务是从定义资源（Resource）中加载bean  
* ***Resource*** ： Bean定义资源（绑定配置文件），而后来被BeanDefinitionReader加载出bean  

## BeanDefinition如何定义Bean的  

***描述属性和值***  
有一个方法getPropertyValues（），返回一个MutablePropertyValues类，这个类里面是包含了List<PropertyValue>的属性：PropertyValue对象含有String的name和Object的value
  
***定位Bean的类***  
Definition中含有一个getBeanClassName（）获取bean的class

## BeanDefinitionReader（加载）：  
**功能** ： 
* 继承了DefaultResourceLoader，所以它有了加载资源的能力  
* 本身的功能就是加载Resource的  

**加载过程** ：  
1.调用资源加载器的获取资源方法resourceLoader.getResource(location)，从配置文件中加载成spring资源Resource  
2.再用BeanDefinitionReader将Resource加载成Document对象  
3.从Document中解析出BeanDefinition包装成BeanDefinitionHolder对象 （类的结构如下）  
![Image text](https://raw.githubusercontent.com/hongmaju/light7Local/master/img/productShow/20170518152848.png)  
4.将解析好的BeanDefinitionHolder对象向IOC容器中注册  
  
## BeanFactory （注册）：  
***容器存储原理***  
DefaultListableBeanFactory中含有一个Map<String, BeanDefinition> beanDefinitionMap存储名字和Bean定义的映射。  
