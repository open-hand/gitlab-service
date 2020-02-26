FROM registry.cn-hangzhou.aliyuncs.com/choerodon-tools/javabase:0.8.0
COPY app.jar /gitlab-service.jar
RUN chown -R www-data:www-data /usr/lib/yaml /usr/bin/kaniko /gitlab-service.jar
USER www-data
CMD java $JAVA_OPTS $SKYWALKING_OPTS -jar /gitlab-service.jar