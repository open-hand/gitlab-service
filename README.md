# Gitlab Service
`Gitlab Service` is responsible for interacting with gitlab by introducing an external java client, this client directly calls the api provided by gitlab,Then gitlab-service handles gitlab logical requests from other services

## Feature
`Gitlab Service` contains features as follows:
- Application template synchronization
- User synchronization
- Project synchronization
- Application synchronization
- Branch management
- Continuous integration
- Application version management
- MergeRequest management
- Webhook management


## Requirements
- Java8
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
1. run command `sh init-local-database.sh`
1. run command as follow or run `GitlabServiceApplication` in IntelliJ IDEA

    ```bash
    mvn clean spring-boot:run
    ```

## Reporting Issues
If you find any shortcomings or bugs, please describe them in the [issue](https://github.com/choerodon/choerodon/issues/new?template=issue_template.md).

## How to Contribute
Pull requests are welcome! [Follow](https://github.com/choerodon/choerodon/blob/master/CONTRIBUTING.md) to know for more information on how to contribute.
