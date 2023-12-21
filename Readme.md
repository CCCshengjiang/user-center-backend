# 项目上线

项目介绍：基于SpringBoot+React的一站式用户管理系统，实现了用户注册、登录、查询管理等功能。

目前还需要完善的：前端的用户头像上传等

具体的系统方向待更新...

# 项目思路

1. 做的是什么项目？

   一个用户中心，实现登录和注册页面，权限管理分为普通用户和管理员。

2. 为什么要做整个项目？

   实现一个系统的用户管理，不管以后要做什么项目，都可以基于本项目来实现。便于管理用户。

3. 项目需要几个人？

   目前就我一个人

4. 怎么权衡成本？

   整个项目需要的经济成本：

   1. 服务器：阿里云服务器
   2. 域名：注册域名www.cwblue.cn

5. 项目名称

   中文：用户中心

   英文：user-center



# 需求分析

1. 项目需要哪些功能？
   1. 用户登录：目前就是账号密码
   2. 用户注册：账号密码和编号注册
   3. 普通用户登录，只有普通页面
   4. 管理员登录，多一个用户管理页面
2. 功能优先级
   1. P0：用户注册
   2. P1：用户登录
   3. P2：登陆后的页面
   4. P3：用户管理页面



# 技术选型

前端：

- HTML+CSS+JavaScript 三件套
- React 开发框架
- 组件库 Ant Design 
- Umi 开发框架
- Umi Request 开发框架
- Ant Design Pro


后端：

- java
- spring（依赖注入框架，帮助你管理 Java 对象，集成一些其他的内容）
- springmvc（web 框架，提供接口访问、restful接口等能力）
- mybatis（Java 操作数据库的框架，持久层框架，对 jdbc 的封装）
- mybatis-plus（对 mybatis 的增强，不用写 sql 也能实现增删改查）
- springboot（**快速启动** / 快速集成项目。不用自己管理 spring 配置，不用自己整合各种框架）
- junit 单元测试库
- mysql 数据库



# 项目启动

## 前端

（需要下载node.js，npm或yarn，文档末尾《知识补充》有相关介绍和下载安装教程）

环境要求：Node.js 版本推荐16-17

1.使用开发工具（VsCode或WebStorm等）打开前端项目文件

2.终端输入执行以下命令：

安装依赖：

```
npm install
```

启动：

```
npm start
```

部署（需要将项目部署到服务器中执行）：

```
npm build
```

执行命令后会得到 dist 目录，可以放到自己的 web 服务器指定的路径；也可以使用 Docker 容器部署，将 dist、Dockerfile、docker 目录（文件）一起打包即可。

## 后端

创建数据库表，修改配置文件中的数据库配置，启动项目。



# 项目图片

![image-20231221230748071](https://gitee.com/CCCshengjiang/blog-img/raw/master/image/202312212307184.png)

![image-20231221230822379](https://gitee.com/CCCshengjiang/blog-img/raw/master/image/202312212308482.png)

![image-20231221231111564](https://gitee.com/CCCshengjiang/blog-img/raw/master/image/202312212311658.png)

![image-20231221231030907](https://gitee.com/CCCshengjiang/blog-img/raw/master/image/202312212310997.png)