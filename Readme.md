# 用户中心

## 项目介绍

基于SpringBoot+React的一站式用户管理系统，实现了用户注册、登录、查询管理等功能。



## 项目功能

1. 登录
   - 账号密码登录
2. 注册
   - 账号 + 密码 + 编号注册
3. 系统分为普通用户和管理员用户
   1. 普通用户登录
      - 欢迎
      - 个人信息设置
   2. 管理员登录
      - 欢迎
      - 个人信息设置
      - 所有用户信息管理页面



# 技术选型

## 前端

1. HTML + CSS + JavaScript 三件套
2. React 开发框架
3. Ant Design 组件库 
4. Ant Design Pro 现成的管理系统前端
5. Umi 插件



## 后端

1. Java
2. spring（依赖注入框架，帮助你管理 Java 对象，集成一些其他的内容）
3. SpringMVC（web 框架，提供接口访问、restful接口等能力）
4. MyBatis（Java 操作数据库的框架，持久层框架，对 JDBC的封装）
5. MyBatis-Plus（对 MyBatis的增强，不用写 SQL语句也能实现增删改查）
6. Spring Boot（**快速启动** / 快速集成项目。不用自己管理 spring 配置，不用自己整合各种框架）
7. Junit 单元测试库
8. MySQL数据库



# 项目启动

## 前端

（需要下载npm或yarn，文档末尾《知识补充》有相关介绍和下载安装教程）

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



## 后端

1. 创建数据库表
2. 修改配置文件中的数据库配置
3. 启动项目。



# 项目图片

## 登陆页面

![image-20231221230748071](https://gitee.com/CCCshengjiang/blog-img/raw/master/image/202312212307184.png)



## 注册页面

![image-20240101154732504](https://gitee.com/CCCshengjiang/blog-img/raw/master/image/202401011547670.png)



## 欢迎页面

![image-20240101154828333](https://gitee.com/CCCshengjiang/blog-img/raw/master/image/202401011548435.png)



## 个人设置

![image-20240101154940425](https://gitee.com/CCCshengjiang/blog-img/raw/master/image/202401011549553.png)



## 用户管理

![image-20231221231111564](https://gitee.com/CCCshengjiang/blog-img/raw/master/image/202312212311658.png)



## 多种查询

![image-20231221231030907](https://gitee.com/CCCshengjiang/blog-img/raw/master/image/202312212310997.png)