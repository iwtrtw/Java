## 代理（根据创建对象不同分为静态代理和动态代理）

* 静态代理：对于每个目标对象都需编写一个代理类

* JDK动态代理：代理类只有一个，目标对象需要实现接口
  * 编织类（InvocationHandler）: 将目标对象与事务对象组合起来
*  CGLIB(Code Generation Library代码生成类库)动态代理 :目标类不需要实现接口（通过生成目标对象的子类）
  * 编织类（MethodInterceptor）: 子类调用父类方法

## AOP

* Aop分为两类，一类是对方法的参数进行拦截，一类是对方法进行拦截，SpringAOP属于方法级