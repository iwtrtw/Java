自动配置

+ spring boot会自带一些JavaConfig类帮助用户配置@Configuration
+ 用户可以通过一些条件来筛选是否需要这个配置@Condition

spring boot环境搭建

+ 以war包

  ```
  public class Spb extends SpringBootServletInitializer{
  	@Override
  	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
  		return super.configure(builder).source(Spb.class);
  	}
  	
  }
  ```

  

+ 以jar包

  ```
  @SpringBootApplication
  public class Spb{
  	public static void main(String[] args){
  		SpringApplication.run(Spb.class);
  	}
  
  }
  ```



@SpringBootApplication

```
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
@ConfigurationPropertiesScan
public @interface SpringBootApplication {

}
```

+ @SpringBootConfiguration

  ```
  @Configuration(proxyBeanMethods = false)
  public @interface SpringBootConfiguration {
  
  }
  ```

+ @EnableAutoConfiguration(只是加载配置文件而已，@Configuration真正把配置文件中的类加载到spingIoc容器当中)

  ```
  @AutoConfigurationPackage
  @Import(AutoConfigurationImportSelector.class)
  public @interface EnableAutoConfiguration {
  
  }
  ```

  + @AutoConfigurationPackage（确定自动加载扫描包的路径，根据SpringBootApplication启动类所在包）

    ```
    @AutoConfigurationPackage
    @Import(AutoConfigurationImportSelector.class)
    public @interface AutoConfigurationPackage {
    
    }
    ```

    + AutoConfigurationImportSelector.class 

      > AutoConfigurationImportSelector implements DeferredImportSelector

      > DeferredImportSelector implements ImportSelector
      
      ```
      public interface ImportSelector {
      
      	String[] selectImports(AnnotationMetadata importingClassMetadata);
      
      }
      ```
      
      selectImports：返回一个数组（一些类路径），Spring会把数组中的类全部加载并注入springIOC容器中

+ @ComponentScan

  ```
  @Repeatable(ComponentScans.class)
  public @interface ComponentScan {
  
  }
  ```




spring boot内部会把加了@SpringBootConfiguration注解类定义为根配置类（优先级最高，最先加载）

resources\META-INF\spring.factories配置文件只读一次放入缓存中，后续为自动加载类服务



##### springBoot加载springMvc

1. 手动初始化spring-mvc（前提当前项目是web项目，根据servle3.0 SPI在Tomcat启动时自动加载WebApplicationInitializer的onStartup方法）

   ```
   public class MyWebApplicationInitializer implements WebApplicationInitializer {
   
       @Override
       public void onStartup(ServletContext servletCxt) {
   
           // Load Spring web application configuration
           AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
           ac.register(AppConfig.class);
           ac.refresh();
   
           // Create and register the DispatcherServlet
           DispatcherServlet servlet = new DispatcherServlet(ac);
           ServletRegistration.Dynamic registration = servletCxt.addServlet("app", servlet);
           registration.setLoadOnStartup(1);
           registration.addMapping("/app/*");
       }
   }
   ```

2. 配置spirng-mvc

   JavaConfig代替spring-mvc.xml

   + 方式一：继承WebMvcConfigurationSupport
   + 方式二：@EbableWebMvc+实现WebMvcConfigurer接口

springBoot通过spring.factories加载WebMvcAutoConfiguration

```
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class })
@ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
@AutoConfigureAfter({ DispatcherServletAutoConfiguration.class, TaskExecutionAutoConfiguration.class,
		ValidationAutoConfiguration.class })
public class WebMvcAutoConfiguration {

}
```

+ @ConditionalOnWebApplication(type = Type.SERVLET)：当前项目是否为webr项目

+ @ConditionalOnClass({ Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class })：当前项目是否已存在这些依赖类(API)

+ @ConditionalOnMissingBean(WebMvcConfigurationSupport.class)：当前项目是否不存在WebMvcConfigurationSupport，如果存在表明用户自己配置了spring-mvc相关配置，则不再自动配置WebMvcAutoConfiguration

+ @AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)：指定加载顺序

+ @AutoConfigureAfter({ DispatcherServletAutoConfiguration.class, TaskExecutionAutoConfiguration.class,
  		ValidationAutoConfiguration.class })：在某个类之后加载

  + **DispatcherServletAutoConfiguration.class初始化springMvc**

    

SpringBoot加载servlet：传统先创建出servlet，然后将servlet装载到web容器中（web.xml）；而sp是通过ServeltRegistrationBean

```
public class ServletRegistrationBean<T extends Servlet> extends DynamicRegistrationBean<ServletRegistration.Dynamic> {
	@Override
	protected ServletRegistration.Dynamic addRegistration(String description, ServletContext servletContext) {
		String name = getServletName();
		//注册servlet
		return servletContext.addServlet(name, this.servlet);
}
}
```



+ 手动添加servlet

```
@Bean
public ServletRegistrationBean servletRegs(){
	ServletRegistrationBean srb = new ServletRegistrationBean();
	srb.setServlet(new MyServlet())
	return srb;
}
```



> ServeltRegistrationBean
>
> FilterRegistrationBean
>
> ListenerRegistrationBean



