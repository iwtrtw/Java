## 单例bean

1. 解析xml文件

2. 将xml文件中的bean封装成GenericBeanDefinition对象，存放bean的信息（所属类、id、类型）

   ```
   GenericBeanDefnition genericBeanDefnition = new GenericBeanDefnition();
   genericBeanDefnition.setBeanClass();
   genericBeanDefnition.setBeanClassName();
   ```

3. 将genericBeanDefnition放入Map{ "beanClassName"：genericBeanDefnition }中，将beanClassName放入List中（后续会通过遍历Lsit取Map中的GenericBeanDefnition）；然后根据需要确定是否调用扩张

   + Map存放在ConfigurableListableBeanFactory里
   + 对Spring进行拓展（实现BeanFactoryPostProcessor）

4. 遍历map，validate（校验）是否需要立即new出java对象，把对象放入单例池里singletonObjects （map）,此时才是sping bean

   ```
   //Cache of singleton objects: bean name to bean instance.
   private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);
   ```

   + prototype原型并不会立即new出来，而是等到getBean才创建对象

**在执行到refresh里的prepareBeanFactory时Map中就获取到了所有BeanDefnition**

```
ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

// Prepare the bean factory for use in this context.
prepareBeanFactory(beanFactory);
```

当执行到invokeBeanFactoryPostProcessors(beanFactory)完成所有扫描并将BeanDefnition放入Map中

```
// Invoke factory processors registered as beans in the context.
invokeBeanFactoryPostProcessors(beanFactory);

```

在执行完refresh里的finishBeanFactoryInitialization(beanFactory)会真正完成对象的实例化和赋值（调用对象的构造方法与set）**

```
finishBeanFactoryInitialization(beanFactory);
```



https://www.jianshu.com/p/8c24e0c804cc

在创建对象时，先调用构造方法，还没赋值()，判断是否需要循环依赖，Spring单例默认支持单例，earlySingletonExposure（）默认情况下为ture，然后判断是否需要Aop,然后在注入属性

```
<!--AbstractAutowireCapableBeanFactory-->

protected Object doCreateBean(final String beanName, final RootBeanDefinition mbd, final @Nullable Object[] args)
			throws BeanCreationException {
	//创建Bean对象
	instanceWrapper = createBeanInstance(beanName, mbd, args);


//判断是否需要循环依赖allowCircularReferences默认为ture
	boolean earlySingletonExposure = (mbd.isSingleton() && this.allowCircularReferences &&
				isSingletonCurrentlyInCreation(beanName));
	if (earlySingletonExposure) {
		if (logger.isTraceEnabled()) {
				logger.trace("Eagerly caching bean '" + beanName +
						"' to allow for resolving potential circular references");
			}
			//判断是否需要Aop（第4次调用后置处理器）
			addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, mbd, bean));
		}
		
}


// Initialize the bean instance.
Object exposedObject = bean;
try {
		//属性注入
		populateBean(beanName, mbd, instanceWrapper);
		exposedObject = initializeBean(beanName, exposedObject, mbd);
}
```

+ 关闭循环依赖 把allowCircularReferences属性设为false

  



<!--BeanWrapperImpl-->调用ReflectionUtils.class完成依赖注入





+ <context:component-scan base-package="">：org.springframework.context.annotation.internalConfigurationAnnotationProcessor





+ + 

    

    

  







https://blog.csdn.net/java_lyvee/article/details/101793774