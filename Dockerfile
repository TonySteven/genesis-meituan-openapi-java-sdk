FROM adoptopenjdk:11-jre-openj9

ENV TZ=Asia/Shanghai

COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
