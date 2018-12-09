# 学习spring ioc源码分析  
## 重要的类（或者接口）：  
* ***BeanFactory*** ：定义一系列的获取bean或者判断是否单例之类的方法  
* ***BeanDefinition*** ：Bean对象在Spring实现中是以BeanDefinition来描述的，描述Bean的对象  
* ***BeanDefinitionReader*** ： 资源解析器解析资源，任务是从定义资源（Resource）中加载bean  
* ***Resource*** ： Bean定义资源（绑定配置文件），而后来被BeanDefinitionReader加载出bean  
## BeanDefinitionReader：  
**加载过程** ：将Resource绑定的配置文件转换成Document，再按照Spring的Bean语义要求将Bean定义资源解析并转换为容器内部数据结构
