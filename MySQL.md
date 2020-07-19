#### MySQL

##### 常用命令

> mysql -h localhost  -P 3306 -u root -p

查看版本：select version();

查看当前所在的数据库：select database();

查看当前用户：select user();

查看数据库：show databases;

使用数据库sql_test：use sql_test;

查看表：show tables;

查看数据库sql_test的表：show tables from  sql_test;

建表：create table student(id int,name varchar(20));

查看表结构：desc student;

插入数据：insert into student  (id,name) values (1,'Tom');

更新数据：update student set name='Jerry' where id=1;

删除数据：delete from student where id=1;



##### 语法规范

+ 不区分大小写，建议关键字大写，表名、列名小写
+ 每条命令最好用分号结尾

##### SQL执行顺序

```
SELECT DISTINCT <select_list>
FROM <left_table>
<join_type> JOIN <right_table>
ON <join_condition>
WHERE <where_condition>
GROUP BY <group_by_list>
HAVING <having_condition>
ORDER BY <order_by_condition>
LIMIT <limit_number>
```



**1. 数据查询语言DQL**
数据查询语言DQL基本结构是由SELECT子句，FROM子句，WHERE
子句组成的查询块：

> SELECT <字段名表>
> FROM <表或视图名>
> WHERE <查询条件>

**2 .数据操纵语言DML**
数据操纵语言DML主要有三种形式：

> 1) 插入：INSERT
> 2) 更新：UPDATE
> 3) 删除：DELETE

**3. 数据定义语言DDL**
数据定义语言DDL用来创建数据库中的各种对象-----表、视图、
索引、同义词、聚簇等如：
CREATE TABLE/VIEW/INDEX/SYN/CLUSTER
| | | | |
表 视图 索引 同义词 簇

DDL操作是隐性提交的！不能rollback 

**4. 数据控制语言DCL**
数据控制语言DCL用来授予或回收访问数据库的某种特权，并控制
数据库操纵事务发生的时间及效果，对数据库实行监视等。如：

> 1) GRANT：授权。

> 2) ROLLBACK [WORK] TO [SAVEPOINT]：回退到某一点。
> 回滚---ROLLBACK
> 回滚命令使数据库状态回到上次最后提交的状态。其格式为：
> SQL>ROLLBACK;



> 3) COMMIT [WORK]：提交。


  在数据库的插入、删除和修改操作时，只有当事务在提交到数据
库时才算完成。在事务提交前，只有操作数据库的这个人才能有权看
到所做的事情，别人只有在最后提交完成后才可以看到。
提交数据有三种类型：显式提交、隐式提交及自动提交。下面分
别说明这三种类型。


(1) 显式提交
用COMMIT命令直接完成的提交为显式提交。其格式为：

> SQL>COMMIT；



(2) 隐式提交
用SQL命令间接完成的提交为隐式提交。这些命令是：
ALTER，AUDIT，COMMENT，CONNECT，CREATE，DISCONNECT，DROP，
EXIT，GRANT，NOAUDIT，QUIT，REVOKE，RENAME。

(3) 自动提交
若把AUTOCOMMIT设置为ON，则在插入、修改、删除语句执行后，
系统将自动进行提交，这就是自动提交。其格式为：

> SQL>SET AUTOCOMMIT ON；



##### SELECT

+ 起别名（AS）：SELECT first_name AS 姓 FROM studnet;

+ 去重（DISTINCT）：SELECT DISTINCT class_name FROM student;

+ 字符串拼接（CONCAT）：SELECT CONCAT(last_name,first_name) AS `name` FROM student;

  > 若某字段为null，则拼接后的值为null

IFNULL(age,0)：如果字段age为空则显示为0，否则显示原数据

> SELECT IFNULL(age,0) AS 年龄 FROM student;

##### WHERE

```
条件查询：根据条件过滤原始表的数据，查询到想要的数据
语法：
	select 
		要查询的字段|表达式|常量值|函数
	from 
		表
	where 
		条件 ;
```

1. 条件表达式
   条件运算符：<   >    >=   <=   =   !=   <>

   > SELECT * FROM student WHERE salary>10000

2. 逻辑表达式

   逻辑运算符：

   + and（&&）:两个条件如果同时成立，结果为true，否则为false
   + or(||)：两个条件只要有一个成立，结果为true，否则为false
   + not(!)：如果条件成立，则not后为false，否则为true

   > SELECT * FROM student WHERE salary>10000 AND salary<20000

3. 模糊查询（LIKE、IN、BETWEEN AND、IS NULL、IS NOT NULL）

   + LIKE  % 任意多个字符   _ 单个字符 （通配符不能匹配null）

     > SELECT * FROM student WHERE last_name LIKE 'a%'

     > SELECT * FROM student WHERE last_name LIKE '__a_e%'（last_name第三个字符为a，第5个字符为e）

   （当需查询字段包含_可用转义字符\，或者指定ESCAPE）查询第二个字符

   > SELECT * FROM student WHERE last_name LIKE '___ \\___%'

   > SELECT * FROM student WHERE last_name LIKE '_$%' ESCAPE '$'

   + BETWEEN AND（等价于>= AND <= 包含临界值）

   > SELECT * FROM student WHERE salary BETWEEN 100 AND 200

   大小顺序不要颠倒，因为等价于 salary >= 100 AND salary <=200；若BETWEEN 200 AND 100，则查询区间变为salary >= 200 AND salary <=100（结果为空）

   + IN（等价于 = OR = OR = 列表中的数据类型需一致，不支持通配符）

     > SELECT * FROM student WHERE las_name IN('Tom','Jack','Jane')

   + IS NULL（在mysql中无法使用=null、<>null来判断是否为空）

     > SELECT * FROM student WHERE las_name IS NULL

   + IS NOT NULL

     > SELECT * FROM student WHERE las_name IS NOT NULL

##### ORDER BY

```
排序查询(order by一般放在最后，但在limit前)
语法：
select
	要查询的东西
from
	表
where 
	条件
order by 排序的字段|表达式|函数|别名 【asc|desc】
```

+ 多个字段排序（先按salary升序排，若salary相同则按stu_id降序排）

  > SELECT * FROM sutdent ORDER BY salary ASC, stu_id DESC;

+ 函数排序（别名）

  > SELECT * ,salary* 12 *(1+IFNULL(com_pct,0)) AS 年薪 FROM student ORDER BY 年薪

  > SELECT * ,salary* 12 *(1+IFNULL(com_pct,0))  AS 年薪 FROM student ORDER BY salary* * 12 *(1+IFNULL(com_pct,0))

+ 字符长度（LENGTH）(查询邮箱中包含e的员工信息，并先按邮箱字节数降序)

  > SELECT * FROM student WHERE email LIKE '%e%' ORDER BY LENGTH(email) DESC

##### 常见函数

函数调用：SELECT 函数名(实参列表) [FROM table]

**utf-8：一个中文汉字3字节，一个英文字符一个字节**

**MySQL索引是从1开始的**

+ 字符函数

  + LENGTH 获取参数值的字节个数

    > SELECT LENGTH('john')

  + CONCAT 拼接字符串

    > SELECT CONCAT( last_name , '_' , first_name )  AS 姓名 FROM student ;

  + UPPER、LOWER 改变字符大小写
    
    > SELECT CONCAT( UPPER(last_name) , LOWER(first_name) )  AS 姓名 FROM student；  
    
  + SUBSTR(str,start,length) 截取字符 
  
    > SELECT SUNSTR("ABCDEFG",3);     //CDEFG
    >
    > SELECT SUNSTR("ABCDEFG",2,3);     //BCD
  
  + INSTR(str,substr) 返回子串substr在字符串str中的第一次出现起始索引（若无则返回0）
  
    > SELECT INSTR('ABCDEFG','CDE');     //3
  
  + TRIM 去除前后空格（可指定去除字符）
  
    > SELECT TRIM('       ABC     ');     //ABC
    >
    > SELECT TRIM('a' FROM 'aaaaaABCaaaaa' );     //ABC
  
  + LPAD 用指定字符实现左填充指定长度
  
    > SELECT LOAD('abc',10,'X');     //XXXXXXXabc
    >
    > SELECT LOAD('abc',2,'X');     //ab
  
  + RPAD 用指定字符实现右填充指定长度
  
  + REPLACE 替换
  
    > SELECT REPLACE('后端选择后端',‘后端’,'前端');     //前端选择前端
  
+ 数学函数

  + ROUND 四舍五入

    > SELECT ROUND(-1.57)		//-2
    >
    > SELECT ROUND(1.57)		//2
    >
    > SELECT ROUND(1.5678,2)		//1.57

  + CEIL 向上取整（返回大于等于该参数的最小整数）

    > SELECT CEIL(1.01)   //2
    >
    > SELECT CEIL(1.00)   //1
    >
    > SELECT CEIL(-1.02)   //-1

  + FLOOR 向下取整（返回小于等于该参数的最大整数）

    > SELECT FLOOR(9.99)   //9
    >
    > SELECT FLOOR(-9.99)   //-10

  + TRUNCATE 截断

    > SELECT TRUNCATE(1.349,2)	//1.34
    >
    > SELECT TRUNCATE(1.343453,3)	//1.343

  + MOD 取余 （mod(a,b)  <==> a-a/b*b）

    > SELECT MOD(10,3)		//1
    >
    > SELECT 10%3		//1
    >
    > SELECT MOD(-10,3)		//-1  看被除数符号
    >
    > SELECT MOD(10,-3)		//1

+ 日期函数

  + NOW  返回当前系统日期+时间

  + CURDATE  返回当前系统日期（不包括时间）

  + CURTIME  返回当前系统时间（不包括日期）

  + 获取指定部分（年、月、日）

    > SELECT YEAR('1998-1-1')  		//1998
    >
    > SELECT MONTH('1998-6-1')  		//6
    >
    > SELECT MONTHNAME('1998-6-1')  		//June

  + STR_TO_DATE  将日期格式的字符转换成指定格式的日期

    > SELECT STR_TO_DATE('9-13-199' , '%m-%d-%Y')		//1999-09-13

  + DATE_FORMAT  将日期转换为字符

    > SELECT DATE_FORMAT('2018/6/6' , '%Y年%m月%d日')		//2018年06月06日