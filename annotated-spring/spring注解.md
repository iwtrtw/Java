### 注册bean

​		往spring容器注册bean时，bean所属的类若声明有参构造必须要把无参构造一起声明，否则报错。（若父类没有无参构造，子类中一定要有一个去访问了父类的构造方法，否则父类数据就没有初始化）

1. 包扫描+组件注解（@Controller、@Service、@Component、@Repository）**适合自己编写的类，不使用第三方的类**

2. @Bean **适合自己编写的类、第三方的类**

   > @Bean并不是只能在@Configuration类使用，在其他类下也可以(@Service、@Controller等被加入spring容器中的类)

3. @Import **快速导入第三方的类**

+ 直接导入类

```java
@Configuration
@Import(Color.class)
public class SecondConfig{

}
```

+ 导入ImportSelector

```java
@Configuration
@Import({MyImportSelector.class})
public class SecondConfig{

}
```

```java
//自定义逻辑返回需要导入的组件
public class MyImportSelector implements ImportSelector {

	//返回值，就是到导入到容器中的组件全类名
	//AnnotationMetadata:当前标注@Import注解的类的所有注解信息
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		//方法不要返回null值，但可以返回空数组
		return new String[]{"com.entity.Blue","com.entity.Yellow"};
	}
}

```

+ 导入ImportBeanDefinitionRegistrar

```java
@Configuration
@Import(MyImportRegister.class)
public class SecondConfig{

}
```

```java
public class MyImportRegister implements ImportBeanDefinitionRegistrar {
/**
	 * AnnotationMetadata：当前类的注解信息
	 * BeanDefinitionRegistry:BeanDefinition注册类；把所有需要添加到容器中的bean；调用BeanDefinitionRegistry.registerBeanDefinition手工注册进来
	 */
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		
		boolean definition = registry.containsBeanDefinition("com.entity.Red");
		boolean definition2 = registry.containsBeanDefinition("com.entity.Blue");
		if(definition && definition2){
			//指定Bean定义信息；（Bean的类型，Bean。。。）
			RootBeanDefinition beanDefinition = new RootBeanDefinition(RainBow.class);
			//注册一个Bean，指定bean名
			registry.registerBeanDefinition("rainBow", beanDefinition);
		}
	}
}
```

4. FactoryBean：getObject方法指导getBean("userFactoryBean")才执行

```java
@Component
public class UserFactoryBean implements FactoryBean<User> {
    public User getObject() throws Exception {
        return new User();
    }

    public Class<?> getObjectType() {
        return User.class;
    }

    public boolean isSingleton() {
        return true;
    }
}
```

```java
//com.iwtrtw.custom.UserFactoryBean@3d299e3
System.out.println(context.getBean("&userFactoryBean"));
//com.iwtrtw.entity.User@3d299e3
System.out.println(context.getBean("userFactoryBean"));
```

### bean生命周期

bean创建(构造方法)→初始化(init)→销毁(destory)

 * 对象创建（构造方法）
   + 单实例：在容器启动的时候创建对象
   + 多实例：在每次获取的时候创建对象
 * 初始化
   + 对象创建完成，并赋值好，调用初始化方法inti
 * 销毁
   + 单实例：容器关闭的时候
   + 多实例：容器不会管理这个bean；容器不会调用销毁方法

1. 指定初始化和销毁方法：通过@Bean指定init-method和destroy-method；

```java
@Bean(initMethod="init",destroyMethod="detory")
	public Car car(){
		return new Car();
}
```

2. Bean实现InitializingBean（定义初始化逻辑）、DisposableBean（定义销毁逻辑）

```java
@Component
public class Cat implements InitializingBean,DisposableBean {
	
	public Cat(){
		System.out.println("cat constructor...");
	}

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("cat...destroy...");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("cat...afterPropertiesSet...");
	}
}
```

3. 使用JSR250
   + @PostConstruct在bean创建完成并且属性赋值完成；来执行初始化方法（实际上通过BenaPostProcessor的applyBeanPostProcessorsBeforeInitialization方法实现的，并不是真正的invokeInitMethods方法）
   + @PreDestroy在容器销毁bean之前通知我们进行清理工作

```java
@Component
public class Dog implements ApplicationContextAware {
	
	public Dog(){
		System.out.println("dog constructor...");
	}
	
	//对象创建并赋值之后调用
	@PostConstruct
	public void init(){
		System.out.println("Dog....@PostConstruct...");
	}
	
	//容器移除对象之前
	@PreDestroy
	public void detory(){
		System.out.println("Dog....@PreDestroy...");
	}

}
```

#### BeanPostProcessor

> populateBean(beanName, mbd, instanceWrapper);给bean进行属性赋值
>
> initializeBean
>
> {
>
> applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
>
> invokeInitMethods(beanName, wrappedBean, mbd)//执行inti()
>
> applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
> }


+ @ComponentScan（过滤规则针对扫描包下@Service、@Component、@Configuration）


```java
@ComponentScan(value = "com.iwtrtw",excludeFilters = {
        //excludeFilters:排除注解@Contrller、@Service
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes ={ Controller.class, Service.class})
})
```

```java
@ComponentScan(value = "com.iwtrtw",includeFilters = {
    	//includeFilters：只包含@Serviec(需要设置useDefaultFilters为false才生效)
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Service.class})
},useDefaultFilters = false)
```

+ 自定义规则：

   @ComponentScan.Filter(type = FilterType.CUSTOM,classes = MyTypeFilter.class)

  ```java
  public class MyTypeFilter implements TypeFilter {
      /**
       * metadataReader：读取到的当前正在扫描的类的信息
       * metadataReaderFactory:可以获取到其他任何类信息的
       */
      public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
              throws IOException {
          // TODO Auto-generated method stub
          //获取当前类注解的信息
          AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
          //获取当前正在扫描的类的类信息
          ClassMetadata classMetadata = metadataReader.getClassMetadata();
          //获取当前类资源（类的路径）
          Resource resource = metadataReader.getResource();
  
          String className = classMetadata.getClassName();
          System.out.println("--->"+className);
          if(className.contains("er")){
              return true;
          }
          return false;
      }
  }
  ```

singleton对象实例在容器启动时就已经创建了，prototype对象实例只有getBean时才正式创建(但其所属类的BeanDefinition已经存在容器中了)

```java
/*
单例情况下容器启动时就会调用该方法
多实例情况下等到getBean时才会调用该方法
*/
@Scope("prototype")
@Bean
public Person person(){
    return new Person();
}
```

+ @Conditional 根据条件注册bean（条件成立时才加载此类）

```java
 @Conditional(MyCondition.class)
 @Bean
 public Person personTwo(){
        System.out.println("Create Person Two");
        return new Person();
 }
```

```java
public class MyCondition implements Condition {
    /**
     * ConditionContext：判断条件能使用的上下文（环境）
     * AnnotatedTypeMetadata：注释信息
     */

    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // TODO是否linux系统
        //1、能获取到ioc使用的beanfactory
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        //2、获取类加载器
        ClassLoader classLoader = context.getClassLoader();
        //3、获取当前环境信息
        Environment environment = context.getEnvironment();
        //4、获取到bean定义的注册类
        BeanDefinitionRegistry registry = context.getRegistry();

        String property = environment.getProperty("os.name");

        //可以判断容器中的bean注册情况，也可以给容器中注册bean
        boolean definition = registry.containsBeanDefinition("person");
        if(property.contains("Window")){
            return true;
        }
        return false;
    }
}
```

### 自动装配

+ @Autowrited

  ​	默认按照类型，若找到相同类型会根据属性的名字匹配（匹配到一个则成功，匹配到多个则报错);自动装配的对象要在容器中存在，否则会报错，可以使用@Autowrited（required=false）解决

  + @Qualifier("bookDao")指定装配的对象的id

  + @Primary+@Bean自动装配时首选这个bean，其优先级低于Qualifier


+ @Profile指定组件在哪个环境的情况下才能被注册到容器中，不指定，任何环境下都能注册这个组件

  @Profile("dev")只有在dev环境下才生效（可以标注在方法和类上） 

  + 加了Profile标识的bean，只有这个环境被激活的时候才能注册到容器中。默认是default环境
  + 写在配置类上，只有是指定的环境的时候，整个配置类里面的所有配置才能开始生效
  + 没有标注Profile标识的bean在，任何环境下都是加载的；

```java
//1、创建一个applicationContext
AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();	
//2、设置需要激活的环境
applicationContext.getEnvironment().setActiveProfiles("dev");
//3、注册主配置类
applicationContext.register(MainConfigOfProfile.class);
//4、启动刷新容器
applicationContext.refresh();	
```

