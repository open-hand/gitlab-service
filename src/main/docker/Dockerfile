FROM registry.cn-hangzhou.aliyuncs.com/choerodon-tools/javabase:0.6.0
COPY app.jar /gitlab-service.jar
ENTRYPOINT exec java $JAVA_OPTS  -jar /devops-service.jar
