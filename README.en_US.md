English | [简体中文](./README.md)

# Gitlab Service
`Gitlab Service` is responsible for interacting with gitlab by introducing an external java client, this client directly calls the api provided by gitlab,Then gitlab-service handles gitlab logical requests from other services

## Feature
`Gitlab Service` contains features as follows:
- Group Management

  This function is used to manage Gitlab's group, including creating projects, deleting projects, querying projects, etc.
  
- WebHook Management

  This function is used to manage Gitlab's WebHook, including creating WebHook, querying WebHook, etc.
  
- Issue Management

  This function is used to manage Gitlab's issue, including create issue , update issue, close issue and other operations.

- Label Management

  This function is used to manage Gitlab's labels, including querying label, deleting label, subscribing to labels, and so on.
  
- Merge Request Management

  This function is used to manage Gitlab's Merge Request, including create, delete, query Merge Request and other operations.
  
- Project Management

  This feature is used to manage Gitlab's projects, including creating , updating, deleting projects, and adding and removing Project members.
  
- User Management
  This function is used to manage the User of Gitlab, including creating, updating, verifying the existence of User mailbox and other operations.

## Requirements
- JDK-8
- [Maven](http://www.maven-sf.com/)
- [MySQL](https://www.mysql.com)
- [Gitlab](https://gitlab.com)
- [Kafka](https://kafka.apache.org)

## Installation and Getting Started
1. init database

    ```sql
    CREATE USER 'choerodon'@'%' IDENTIFIED BY "choerodon";
    CREATE DATABASE gitlab_service DEFAULT CHARACTER SET utf8;
    GRANT ALL PRIVILEGES ON gitlab_service.* TO choerodon@'%';
    FLUSH PRIVILEGES;
    ```
2. run command as follow or run `GitlabServiceApplication` in IntelliJ IDEA

    ```bash
    mvn clean spring-boot:run
    ```
    
## Service dependencies
- `eureka-server`L Register & configure center
- `oauth-server` authentication center
- `MySQL`: gitlab_service

## Reporting Issues
If you find any shortcomings or bugs, please describe them in the [issue](https://github.com/choerodon/choerodon/issues/new?template=issue_template.md).

## Link
[Update Log](CHANGELOG.en-US.md)

## How to Contribute
Pull requests are welcome! [Follow](https://github.com/choerodon/choerodon/blob/master/CONTRIBUTING.md) to know for more information on how to contribute.
