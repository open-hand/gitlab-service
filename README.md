简体中文 | [English](./README.en_US.md)

# Gitlab Service   

`Gitlab Service`通过引入外部java客户端与Gitlab进行交互。该客户端通过直接调用Gitlab提供的api，处理来自其他服务的Gitlab请求。


## 功能

- 项目组管理

  此功能用于对Gitlab的group进行管理，包括创建、删除、查询项目以及添加、移除、查询项目组成员等操作。
  
- WebHook管理

  此功能用于对Gitlab的ProjectHook进行管理，包括创建和查询ProjectHook详情等操作。
  
- 问题管理

  此功能用于对Gitlab的Issue进行管理，包括创建、更新、关闭Issue等操作。
  
- Label管理

  此功能用于对Gitlab的Label进行管理，包括查询、删除、订阅Label等操作。
  
- Merge Request管理

  此功能用于对Gitlab的Merge Request进行管理，包括创建、删除、查询Merge Request等操作。
  
- 项目管理

  此功能用于对Gitlab的Project进行管理，包括创建、更新、删除项目以及添加、移除项目成员等操作。
  
- 用户管理

  此功能用于对Gitlab的User进行管理，包括创建、更新、校验用户邮箱是否存在等操作。                          

## 环境依赖

- JDK-8
- [Maven](http://www.maven-sf.com/)
- [MySQL](https://www.mysql.com)
- [Gitlab](https://gitlab.com)
- [Kafka](https://kafka.apache.org)


## 安装与启动

1. 初始化数据库
    ```sql
       CREATE USER 'choerodon'@'%' IDENTIFIED BY "choerodon";
       CREATE DATABASE gitlab_service DEFAULT CHARACTER SET utf8;
       GRANT ALL PRIVILEGES ON gitlab_service.* TO choerodon@'%';
       FLUSH PRIVILEGES;
    ```

2. 执行下列命令或在 IntelliJ IDEA 中运行`GitlabServiceApplication`类

    ```bash
    mvn clean spring-boot:run
    ```

## 服务依赖

- `eureka-server`: 注册&配置中心
- `oauth-server` 认证中心
- `MySQL`: gitlab_service 数据库

## 问题报告

如果您发现任何缺陷或bug，请在  [问题报告](https://github.com/choerodon/choerodon/issues/new?template=issue_template.md) 中提出

## 链接

[更新日志](CHANGELOG_zh_CN.md)

## 贡献

欢迎贡献代码！ [如何贡献](https://github.com/choerodon/choerodon/blob/master/CONTRIBUTING.md)
