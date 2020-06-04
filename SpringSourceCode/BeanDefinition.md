理解Spring容器、BeanFactory和ApplicationContexthttps://www.jianshu.com/p/2854d8984dfc



### BeanDefinition(interface )

```
//指定此bean定义的bean类名。
//类名可以在bean工厂的后处理过程中修改，bean factory post-processing.
void setBeanClassName(@Nullable String beanClassName);

//返回此bean的构造函数参数值. 返回的实例可以在bean工厂的后处理过程中修改
ConstructorArgumentValues getConstructorArgumentValues();

//返回要应用到bean的新实例的属性值。返回的实例可以在bean工厂的后处理过程中修改。
MutablePropertyValues getPropertyValues();
```



### AnnotatedBeanDefinitionReader

类描述：一个方便用于带注释的bean类进行编程注册的适配器，这是ClassPathBeanDefinitionScanner的另一种选择，应用相同的注释解析，但仅用于显式注册的类。

```
//用于注册beanDefinition
//当基于AnnotationConfigApplicationContext开发时，该属性的值通过构造方法设为
//AnnotationConfigApplicationContext
private final BeanDefinitionRegistry registry;

public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry) {
		this(registry, getOrCreateEnvironment(registry));
}

//创建一个新AnnotatedBeanDefinitionReader
public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry, Environment environment) {
		this.registry = registry;
		this.conditionEvaluator = new ConditionEvaluator(registry, environment, null);
//委托AnnotationConfigUtils给DefaultListableBeanFactory工厂的属性赋值
AnnotationConfigUtils.registerAnnotationConfigProcessors(this.registry);
}
```

### DefaultListableBeanFactory 

+ **DefaultListableBeanFactory  **extends  AbstractAutowireCapableBeanFactory( abstract )  

```
//可选的OrderComparator，用于依赖列表和数组(排序加载bean？)
Comparator<Object> dependencyComparator

//bean定义对象Map{beanName:beanDefinition}
Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256)

//bean定义名称列表，按注册顺序排列（存放所有的beanName）
volatile List<String> beanDefinitionNames = new ArrayList<>(256)
```

> **AbstractAutowireCapableBeanFactory(abstract)** extends  AbstractBeanFactory

+ **AbstractBeanFactory(abstract)**  extends FactoryBeanRegistrySupport（abstract）

```
/** Map from bean name to merged RootBeanDefinition
* 从bean名映射到合并的RootBeanDefinition*/
private final Map<String, RootBeanDefinition> mergedBeanDefinitions = new ConcurrentHashMap<>(256);


/** BeanPostProcessors to apply in createBean
* 在createBean中应用的beanpostprocessor*/
private final List<BeanPostProcessor> beanPostProcessors = new CopyOnWriteArrayList<>();



//已经至少创建过一次的bean的名称
private final Set<String> alreadyCreated = Collections.newSetFromMap(new ConcurrentHashMap<>(256));
```

> **FactoryBeanRegistrySupport(abstract)** extends DefaultSingletonBeanRegistry

+ **DefaultSingletonBeanRegistry**

```
//单例对象缓存:{beanName:bean实例}
private final Map<String, Object> singletonObjects = new ConcurrentHashMap< >(256);

//早期单例对象的缓存:{beanName:bean实例}
private final Map<String, Object> earlySingletonObjects = new HashMap< >(16);

//单例工厂的缓存:{beanName:对象工厂}
private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap< >(16);


//已实例化的单例，包含按注册顺序排列的 beanName
private final Set<String> registeredSingletons = new LinkedHashSet<>(256);
```





### BeanFactoryPostProcessor(interface )

> /**
>
> Modify the application context's internal bean factory after its standard initialization.
>
> 在应用程序上下文的标准初始化之后修改它的内部bean工厂。
>
> All bean definitions will have been loaded,所有的bean定义都将被加载，
>
> but no beans will have been instantiated yet.但是还没有实例化bean。
>
> This allows for overriding or adding properties even to eager-initializing beans.
>
> 这允许 覆盖或添加属性，甚至对急于初始化的bean也是如此。
>
> @param beanFactory the bean factory used by the application context
>
> @throws org.springframework.beans.BeansException in case of errors
> */

```
void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
```

+ **BeanDefinitionRegistryPostProcessor(interface)** extends BeanFactoryPostProcessor

  > 	/**
  > 	 * Modify the application context's internal bean definition registry after its standard initialization.
  > 	 * 在应用程序上下文的标准初始化之后修改其内部bean定义注册表。
  > 	 * All regular bean definitions will have been loaded,所有常规的bean定义都会被加载，
  > 	 * but no beans will have been instantiated yet.但是还没有实例化bean。
  > 	 * This allows for adding further bean definitions before the next post-processing phase kicks in.
  > 	 * 这允许在下一个后处理阶段开始之前添加更多的bean定义。
  > 	 * @param registry the bean definition registry used by the application context
  > 	 * @throws org.springframework.beans.BeansException in case of errors
  > 	 */

  ```
  void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException;
  ```

  

在refresh（）调用finishBeanFactoryInitialization(beanFactory);之后：alreadyCreated 、beanDefinitionMap、mergedBeanDefinitions 的数量一致 ；registeredSingletons与singletonObjects的数量一致（它们有什么区别）

