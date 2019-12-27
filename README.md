**Spring Boot Maven 项目**

    1、安装JDK
    2、环境变量增加JAVA_HOME
        setx JAVA_HOME "E:\jdk"
    3、安装maven
    4、环境变量追加maven
        setx Path "%Path%E:\maven\bin;"
    5、修改maven设置
        5.1、E:\maven\conf\settings.xml
        5.2、本地仓库
            <localRepository>E:\repo</localRepository>
        5.3、国内镜像
            <mirror>
              <id>alimaven</id>
              <name>aliyun maven</name>
              <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
              <mirrorOf>central</mirrorOf>        
            </mirror>            
    6、启动idea
        6.1、修改默认maven配置
            File | Other Settings | Settings for New Projects | Build, Execution, Deployment | Build Tools | Maven
        6.2、创建maven项目
            File | New | Project | Maven
    7、开始编码
        7.1、打开https://start.spring.io/，查看模版
        7.2、参照模版修改pom.xml文件
        7.3、增加log4j2依赖
        7.4、复制配置类
        7.5、增加log4j2配置
        7.6、复制启动类
        7.7、复制测试类
            测试类增加
            @ExtendWith(SpringExtension.class)
    8、idea运行项目
    9、maven打jar包运行项目
        9.1、进入项目目录
        9.2、执行打包命令
            mvn package (注：需要编译环境，依赖步骤2)
            跳过测试用例则增加参数 -Dmaven.test.skip=true
        9.3、启动项目
            进入target
            java -jar project.jar
    10、maven打docker镜像运行项目
        10.1、启动docker
        10.2、打开docker tcp
        10.3、引入docker-maven-plugin，并配置
        10.4、执行打包命令
            mvn package docker:build
        10.5、启动镜像
            docker run image
    11、上传项目到git
        11.1、项目目录内，执行命令
            git init
            git remote add origin https://github.com/zhigen/project.git
            
**redis**

    1、启动redis
        1.1、docker pull redis
        1.2、docker run -d -p 6379:6379 -v /F/data/docker/redis:/data redis --appendonly yes
    2、编写代码
        2.1、pom.xml文件引入依赖
        2.2、application.properties添加配置项
        2.3、编写配置类
        2.4、编写测试用例
    3、测试        
    
**redis管理session**
    
    1、pom.xml引入依赖
    2、application.properties配置cookie过期时间，默认值-1为“浏览会话结束时”过期
    3、编写测试类及测试方法
        3.1、localhost:8080/info开启会话后，重启服务，会话不丢失，证明会话已持久到redis
        3.2、不设置cookie.max-age，开启会话后，重启浏览器，会话丢失，证明cookie.max-age默认值为-1
        3.3、设置cookie.max-age=2100，开启会话后，重启浏览器，会话不丢失，查看cookie可看到过期时间为2100秒后
        3.4、localhost:8080/out，将cookie.max-age设置为0，cookie失效，会话丢失