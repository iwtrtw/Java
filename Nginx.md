## Nginx

+ 安装https://www.linuxidc.com/Linux/2016-09/134907.htm



防火墙

+ 查看开放端口号

> firewall-cmd --list-all

+ 设置开放端口

> firewall-cmd --add-port=80/tcp --permanent

+ 查看防火墙状态

>  firewall-cmd --state 

+ 重启防火墙

> firewall-cmd --reload

https://blog.csdn.net/weixin_43228740/article/details/85418895





##### 反向代理

```
server {
        listen       9001;
        listen       192.168.145.128;
        server_name  somename  alias  another.alias;

        location ~ /edu/ {
            proxy_pass http://127.0.0.1:8081;
        }

        location ~ /vod/ {
            proxy_pass http://127.0.0.1:8082;
        }
}
```



##### 负载均衡

```
upstream myserver{
        server 192.168.145.128:8081;
        server 192.168.145.128:8082;
}
server {
        listen       9002;
        listen       192.168.145.128;

        location / {
            proxy_pass http://myserver;
      }
}
```



