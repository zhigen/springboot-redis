# springboot-redis
> 本项目使用springboot框架，集成redis

# 目录
* [1 创建项目](#01)
* [2 启动redis](#02)
* [3 编写代码](#03)
* [4 redis管理session](#04)
* [5 redisson锁](#05)

## <div id="01"></div>
## 1 创建项目
> 参照或复制springboot-maven项目
            
## <div id="02"></div>
## 2 启动redis
    2.1、docker pull redis
    2.2、docker run -d -p 6379:6379 -v /F/data/docker/redis:/data redis --appendonly yes

## <div id="03"></div>
## 3 编写代码
    3.1、pom.xml文件引入依赖
    3.2、application.properties添加配置项
    3.3、编写配置类
    3.4、编写测试用例
    3.5、测试        
    
## <div id="04"></div>
## 4 redis管理session    
    4.1、pom.xml引入依赖
    4.2、application.properties配置cookie过期时间，默认值-1为“浏览会话结束时”过期
    4.3、编写测试类及测试方法
        4.3.1、localhost:8080/info开启会话后，重启服务，会话不丢失，证明会话已持久到redis
        4.3.2、不设置cookie.max-age，开启会话后，重启浏览器，会话丢失，证明cookie.max-age默认值为-1
        4.3.3、设置cookie.max-age=2100，开启会话后，重启浏览器，会话不丢失，查看cookie可看到过期时间为2100秒后
        4.3.4、localhost:8080/out，将cookie.max-age设置为0，cookie失效，会话丢失
        
## <div id="05"></div>
## 5 redisson锁
    5.1、pom.xml引入依赖
    5.2、编写bean类
    5.3、编写测试类及测试方法
        5.3.1、浏览器打开两个选项卡
        5.3.2、同时访问localhost:8080/lock，观察发现后面访问的选项卡，会在前一个选项卡执行完之后，才开始执行