# Gitlab Service
`Gitlab Service` is responsible for establishing communication with GitLab, handling GitLab related logic and forwarding it to other services.

## Feature
- **Application Template Synchronization**
- **User Synchronization**
- **Project Synchronization**
- **Application Synchronization**
- **Branch Management**
- **Continuous Integration**
- **Application Version Management**


## Requirements
- Java8
- [Mysql](https://www.mysql.com)
- [Gitlab](https://gitlab.com)
- [KAFKA](https://kafka.apache.org)

## Installation and Getting Started
1. init database
    ```sql
    CREATE USER 'choerodon'@'%' IDENTIFIED BY "choerodon";
    CREATE DATABASE gitlab_service DEFAULT CHARACTER SET utf8;
    GRANT ALL PRIVILEGES ON gitlab_service.* TO choerodon@'%';
    FLUSH PRIVILEGES;
    ```
1. run command `sh init-local-database.sh`
1. run command as follow or run `DevopsServiceApplication` in `IntelliJ IDEA`
    ```bash
    mvn clean spring-boot:run
    ```

## Reporting Issues
If you find any shortcomings or bugs, please describe them in the Issue.

## How to Contribute
Pull requests are welcome! [Follow](https://github.com/choerodon/choerodon/blob/master/CONTRIBUTING.md) to know for more information on how to contribute.
