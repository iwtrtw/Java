### AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class)初始化流程

**AnnotationConfigApplicationContext**  === **BeanDefinitionRegistry**

```
//读取注解的Bean定义读取器，并将其设置到容器中
//将bean(加了注解的类)转换为BeanDefinition
private final AnnotatedBeanDefinitionReader reader;

//扫描指定类路径中注解Bean定义的扫描器，并将其设置到容器中
private final ClassPathBeanDefinitionScanner scanner;
```

→

```
public AnnotationConfigApplicationContext() {
		//→→step1先调用父类构造方法
		//→→step2实例化Reader（reader可将注解bean转换为BeanDefinition)
		this.reader = new AnnotatedBeanDefinitionReader(this);
		this.scanner = new ClassPathBeanDefinitionScanner(this);
}
```

+ ***Step1***：AnnotationConfigApplicationContext先调用父类GenericApplicationContext的构造方法去实例化一个工厂DefaultListableBeanFactory（只是个空实例，属性并没有赋值；后面会在实例化AnnotatedBeanDefinitionReader对工厂的一些属性赋值）

**GenericApplicationContext  implements  BeanDefinitionRegistry** 

→父类构造方法→

```
public GenericApplicationContext() {
		this.beanFactory = new DefaultListableBeanFactory();
}
```

→实例化Reader→

**AnnotatedBeanDefinitionReader**

```
//创建一个新AnnotatedBeanDefinitionReader
public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry, Environment environment) {
		this.registry = registry;
		this.conditionEvaluator = new ConditionEvaluator(registry, environment, null);
//→→step3委托AnnotationConfigUtils给DefaultListableBeanFactory工厂的属性赋值
AnnotationConfigUtils.registerAnnotationConfigProcessors(this.registry);
}
```

+ **Step2、3**：实例化AnnotatedBeanDefinitionReader，AnnotatedBeanDefinitionReader会委托AnnotationConfigUtils 对之前的工厂空实例DefaultListableBeanFactory的一些属性设值（增加工厂功能）,并且通过registerPostProcessor( )去注册一些spring内部的bean——PostProcessor（ConfigurationClassPostProcessor、）

**AnnotationConfigUtils**

```
public static Set<BeanDefinitionHolder> registerAnnotationConfigProcessors(
			BeanDefinitionRegistry registry, @Nullable Object source) {

		DefaultListableBeanFactory beanFactory = unwrapDefaultListableBeanFactory(registry);
		.....
		//给beanFactory的属性赋值，添加一些功能			beanFactory.setDependencyComparator(AnnotationAwareOrderComparator.INSTANCE);
beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
		.....
		Set<BeanDefinitionHolder> beanDefs = new LinkedHashSet<>(8);
		//注册一些spring内部的bean使用RootBeanDefinition封装，调用registerPostProcessor（）方法注册到工厂的beanDefinitionMap中，同时存入BeanDefinitionHolder中
		...
		//1 ConfigurationClassPostProcessor
			RootBeanDefinition def = new RootBeanDefinition(ConfigurationClassPostProcessor.class);
			def.setSource(source);
			//→→step4注册Spring内部的PostProcessor
			beanDefs.add(registerPostProcessor(registry, def, CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME));
		...
		//2 AutowiredAnnotationBeanPostProcessor
			RootBeanDefinition def = new RootBeanDefinition(AutowiredAnnotationBeanPostProcessor.class);
			def.setSource(source);
			beanDefs.add(registerPostProcessor(registry, def, AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME));
		...
		//3 RequiredAnnotationBeanPostProcessor
			RootBeanDefinition def = new RootBeanDefinition(RequiredAnnotationBeanPostProcessor.class);
			def.setSource(source);
			beanDefs.add(registerPostProcessor(registry, def, REQUIRED_ANNOTATION_PROCESSOR_BEAN_NAME));
		}
		...
		//4 CommonAnnotationBeanPostProcessor
			RootBeanDefinition def = new RootBeanDefinition(CommonAnnotationBeanPostProcessor.class);
			def.setSource(source);
			beanDefs.add(registerPostProcessor(registry, def, COMMON_ANNOTATION_PROCESSOR_BEAN_NAME));
		...	
		//5 PersistenceAnnotationBeanPostProcessor
			RootBeanDefinition def = new RootBeanDefinition();
	def.setBeanClass(ClassUtils.forName(PERSISTENCE_ANNOTATION_PROCESSOR_CLASS_NAME,
						AnnotationConfigUtils.class.getClassLoader()));
			def.setSource(source);
			beanDefs.add(registerPostProcessor(registry, def, PERSISTENCE_ANNOTATION_PROCESSOR_BEAN_NAME));
		...	
		//6 EventListenerMethodProcessor
			RootBeanDefinition def = new RootBeanDefinition(EventListenerMethodProcessor.class);
			def.setSource(source);
			beanDefs.add(registerPostProcessor(registry, def, EVENT_LISTENER_PROCESSOR_BEAN_NAME));
		}
		...
		//7 EventListenerMethodProcessor
			RootBeanDefinition def = new RootBeanDefinition(DefaultEventListenerFactory.class);
			def.setSource(source);
			beanDefs.add(registerPostProcessor(registry, def, EVENT_LISTENER_FACTORY_BEAN_NAME));
		...		
		...	
		return beanDefs;
```

+ **Step4**：AnnotationConfigUtils调用自身的registerPostProcessor( )方法去调用AnnotationConfigApplicationContext的registerBeanDefinition( )方法【实际调用其父类GenericApplicationContext的方法】

**AnnotationConfigUtils**

```
private static BeanDefinitionHolder registerPostProcessor(
			BeanDefinitionRegistry registry, RootBeanDefinition definition, String beanName) {

		definition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		//调用registry(即AnnotationConfigApplicationContext)的registerBeanDefinition()方法注册beandefinition(PostProcessor)
		registry.registerBeanDefinition(beanName, definition);
		return new BeanDefinitionHolder(definition, beanName);
	}
```

**GenericApplicationContext**

```
public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
			throws BeanDefinitionStoreException {
		//→→step5调用工厂DefaultListableBeanFactory的registerBeanDefinition()
		this.beanFactory.registerBeanDefinition(beanName, beanDefinition);
}
```

+ **Step5**  ：调用工厂DefaultListableBeanFactory的registerBeanDefinition()【此方法来自其实现的接口BeanDefinitionRegistry】

**DefaultListableBeanFactory implements BeanDefinitionRegistry**



