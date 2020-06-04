BeanDefinition

BeanFactoryPostProcessor（可修改BeanDefinition）

构造方法实例化

BeforeBeanPostProcessor（可进行AOP处理完成动态代理）

Init()

AfterBeanPostProcessor





--refresh

  ----prepareBeanFactory

​     -----addBeanPostProcessor(new ApplicationContextAwareProcessor)

>  ApplicationContextAwareProcessor为一系列Aware接口的实现类的某个属性设值为applicationContext

