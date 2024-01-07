#FROM openjdk:17-oracle
#
#ENV TZ=Asia/Shanghai
#
#COPY target/*.jar app.jar
#
#EXPOSE 8080
#
#ENTRYPOINT ["java","-jar","app.jar"]

# 使用官方的 OpenJDK 基础镜像
FROM openjdk:17-oracle

ENV LANG C.UTF-8
ENV TZ=Asia/Shanghai

# 在容器内设置工作目录
#WORKDIR /app

# 将 JAR 文件复制到容器的 /app 目录下
COPY target/*.jar app.jar

# 暴露应用程序将运行的端口
EXPOSE 8080

# 指定容器启动时要运行的命令
CMD ["java", "-jar", "app.jar"]

