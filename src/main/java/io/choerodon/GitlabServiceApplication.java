package io.choerodon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.choerodon.resource.annoation.EnableChoerodonResourceServer;

@EnableEurekaClient
@EnableScheduling
@SpringBootApplication
@EnableFeignClients("io.choerodon")
@EnableChoerodonResourceServer
public class GitlabServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitlabServiceApplication.class, args);
    }
}
