## SSM

+ SSM框架将系统分为View层、Controller层、Service层、DAO层；Spring MVC负责请求转发和视图管理，Spring实现业务对象管理，Mybatis作为数据对象的持久化引擎
  + 持久层（Mybatis）——DAO层（mapper）：DAO层的设计首先是设计DAO的接口，编写SQL语句mapper，然后在Spring的配置文件中定义此接口的实现类，随后即可在模块中调用此接口来进行数据业务的处理（DAO数据源配置以及有关数据库连接的参数都在Spring的配置文件中进行配置）
  + 业务层（Spring）——Service层：首先设计接口，在设计其实现类，然后在Spring配置文件中配置实现关联，业务的实现具体要调用已定义的DAO层接口
  + 表现层（SpringMVC）——Controller层：调用Service层接口控制业务流程
  + 视图层——View层：视图展示

#### SSM搭建

+ 项目结构：
+ 环境：IDEA、Maven、mysql

https://blog.csdn.net/liyuanyue2017/article/details/96507642

+ pom.xml

  + properties

    ```
    <properties>
    ...
    <!--引入相关依赖：spring、mysql、mabaitas版本号--><spring.version>5.1.3.RELEASE</spring.version>
    <slf4j.version>1.6.6</slf4j.version>
    <log4j.version>1.2.12</log4j.version>
    <mysql.version>8.0.17</mysql.version>
    <mybatis.version>3.4.5</mybatis.version>
    </properties>
    ```

  + dependencies

    + spring

      ```
      <dependency>
          <groupId>org.aspectj</groupId>
          <artifactId>aspectjweaver</artifactId>
          <version>1.6.8</version>
      </dependency>
      
      <dependency>
          <groupId>org.springframework</groupId>
          <artifactId>spring-aop</artifactId>
          <version>${spring.version}</version>
      </dependency>
      
      <dependency>
          <groupId>org.springframework</groupId>
          <artifactId>spring-context</artifactId>
          <version>${spring.version}</version>
      </dependency>
      
      <dependency>
          <groupId>org.springframework</groupId>
          <artifactId>spring-web</artifactId>
          <version>${spring.version}</version>
      </dependency>
      
      <dependency>
          <groupId>org.springframework</groupId>
          <artifactId>spring-webmvc</artifactId>
          <version>${spring.version}</version>
      </dependency>
      
      <dependency>
          <groupId>org.springframework</groupId>
          <artifactId>spring-test</artifactId>
          <version>${spring.version}</version>
      </dependency>
      
      <dependency>
          <groupId>org.springframework</groupId>
          <artifactId>spring-tx</artifactId>
          <version>${spring.version}</version>
      </dependency>
      
      <dependency>
          <groupId>org.springframework</groupId>
          <artifactId>spring-jdbc</artifactId>
          <version>${spring.version}</version>
      </dependency>
      ```

    + mysql

      ```
      <!--mysql-->
      <dependency>
          <groupId>mysql</groupId>
          <artifactId>mysql-connector-java</artifactId>
          <version>${mysql.version}</version>
      </dependency>
      
      <dependency>
          <groupId>c3p0</groupId>
          <artifactId>c3p0</artifactId>
          <version>0.9.1.2</version>
          <type>jar</type>
          <scope>compile</scope>
      </dependency>
      ```

    + mybatis

      ```
      <!--mybatis-->
      <dependency>
          <groupId>org.mybatis</groupId>
          <artifactId>mybatis</artifactId>
          <version>${mybatis.version}</version>
      </dependency>
      
      <dependency>
          <groupId>org.mybatis</groupId>
          <artifactId>mybatis-spring</artifactId>
          <version>1.3.0</version>
      </dependency>
      ```

    + servlet

      ```
      <!--servlet-->
      <dependency>
          <groupId>javax.servlet</groupId>
          <artifactId>servlet-api</artifactId>
          <version>2.5</version>
          <scope>provided</scope>
      </dependency>
      ```

    + log

      ```
      <!--log-->
      <dependency>
          <groupId>log4j</groupId>
          <artifactId>log4j</artifactId>
          <version>${log4j.version}</version>
      </dependency>
      
      <dependency>
          <groupId>org.slf4j</groupId>
          <artifactId>slf4j-api</artifactId>
          <version>${slf4j.version}</version>
      </dependency>
      
      <dependency>
          <groupId>org.slf4j</groupId>
          <artifactId>slf4j-log4j12</artifactId>
          <version>${slf4j.version}</version>
      </dependency>
      ```

    + 单元测试

      ```
      <dependency>
      	<groupId>org.junit.jupiter</groupId>
      	<artifactId>junit-jupiter-api</artifactId>
      	<version>RELEASE</version>
      </dependency>
      ```

#### Spring

+ 基础配置文件——spring.xml

  ```
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:context="http://www.springframework.org/schema/context"
         xmlns:aop="http://www.springframework.org/schema/aop"
         xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/context
          http://www.springframework.org/schema/context/spring-context.xsd
          http://www.springframework.org/schema/aop
          http://www.springframework.org/schema/aop/spring-aop.xsd
          http://www.springframework.org/schema/tx
          http://www.springframework.org/schema/tx/spring-tx.xsd">
  
      <!--开启注解模式，使用扫描-->
      <context:component-scan base-package="com.ssm">
          <!--排除不需要扫描的包Controller-->
          <context:exclude-filter type="annotation"                       expression="org.springframework.stereotype.Controller"></context:exclude-filter>
      </context:component-scan>
  
  </beans>
  ```
  
  + 事务管理
  
    ```
    <!--配置事务管理-->
    <!--事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    
    <!--事务通知-->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <!--扫描包内数据操作方法的权限配置配置-->
            <tx:method name="get*" read-only="true"/>
            <tx:method name="find*" read-only="true"/>
            <tx:method name="search*" read-only="true"/>
            <tx:method name="*" propagation="REQUIRED"/>
        </tx:attributes>
    </tx:advice>
    
    <!--配置Aop-->
    <!-- 基于 Annotation 的声明式,使切面开启自动代理 -->
    <aop:aspectj-autoproxy />
    
    
    <!--基于XML的声明式,将通知与切点关联起来-->
    <aop:config>
        <aop:pointcut id="txpc" expression="execution(* com.ssm.service.impl.*.*(..))"/>
        <aop:advisor advice-ref="txAdvice" pointcut-ref="txpc"/>
    </aop:config>
    ```
  
    
  

#### SpringMVC

+ web.xml

  + 配置前端控制器（拦截所有请求，包括静态资源），加载springmvc.xml

  ```
  <!--配置前端控制器-->
  <servlet>
      <servlet-name>DispatcherServlet</servlet-name>
      <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
      
      <!--配置初始化参数，加载springmvc.xml配置文件-->
      <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:springmvc.xml</param-value>
      </init-param>
      
      <!--启动服务器时创建对象-->
      <load-on-startup>1</load-on-startup>  
  </servlet>
  
  <servlet-mapping>
      <servlet-name>DispatcherServlet</servlet-name>
      <url-pattern>/</url-pattern>
  </servlet-mapping>
  ```

  + 配置过滤器，解决中文乱码问题（post请求）

    ```
    <!--配置过滤器，解决中文乱码问题(放在最前面)-->
    <filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        
        <init-param>
          <param-name>encoding</param-name>
          <param-value>UTF-8</param-value>
        </init-param>
    </filter>
    
    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    ```

  + **整合springmvc与spring：配置监听器，加载spring.xml**

    ```
    <!--加载spring配置文件,实例化ApplicationContext容器-->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:spring.xml</param-value>
    </context-param>
    
    <!--配置spring相关监听器-->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    ```
    
  + 配置静态资源处理器

    ```
    <!--配置静态资源交给servlet处理-->
    <servlet-mapping>
    	<servlet-name>default</servlet-name>
    	<url-pattern>/assets/*</url-pattern>
    	<url-pattern>/js/*</url-pattern>
    	<url-pattern>*.js</url-pattern>
    	<url-pattern>*.jpg</url-pattern>
    	<url-pattern>*.gif</url-pattern>
    	<url-pattern>*.png</url-pattern>
    	<url-pattern>*.css</url-pattern>
    </servlet-mapping>
    ```

+ springmvc.xml

  ```
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:mvc="http://www.springframework.org/schema/mvc"
         xmlns:context="http://www.springframework.org/schema/context"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="
          http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/mvc
          http://www.springframework.org/schema/mvc/spring-mvc.xsd
          http://www.springframework.org/schema/context
          http://www.springframework.org/schema/context/spring-context.xsd">
  
      <!--扫描Controller相关注解-->
      <context:component-scan base-package="com.ssm">
          <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"></context:include-filter>
      </context:component-scan>
  
      <!--配置化视图解析器-->
      <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
          <!--配置前缀-->
          <property name="prefix" value="/WEB-INF/pages/"></property>
          <!--配置后缀-->
          <property name="suffix" value=".html"></property>
      </bean>
  
      <!--开启sprignmvc注解的支持-->
      <mvc:annotation-driven></mvc:annotation-driven>
  
      <!--静态资源处理转移-->
      <mvc:default-servlet-handler />
  
  </beans>
  ```


#### MyBatis

+ Spring接管SqlSession工厂——spring.xml

  ```
  <!--配置数据源-->
  <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
      <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
      <property name="url" value="jdbc:mysql://localhost:3306/(databse_name)?useSSL=true&amp;serverTimezone=UTC&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
      <property name="username" value="****"/>
      <property name="password" value="****"/>
  </bean>
  
  <!--配置SqlSessionFactory-->
  <bean id="sessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
      <property name="dataSource" ref="dataSource"/>
      <!--配置别名,方便在编写mapper.xml时直接使用类名-->
      <property name="typeAliasesPackage" value="com.com.pro"/>
      <!--配置mapper.xml位置-->
      <property name="mapperLocations" value="classpath:mapper/*.xml" />
  </bean>
  ```
  
  
  
+ 配置自动化扫描所有Mapper接口和文件——spring.xml

  ```
  <!--配置映射器接口-->
  <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
      <property name="sqlSessionFactoryBeanName" value="sessionFactory"/>
      <!--扫描dao接口-->
      <property name="basePackage" value="com.ssm.dao"/>
  </bean>
  ```

  

