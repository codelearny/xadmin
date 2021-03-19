FROM openjdk:8
MAINTAINER enjoyu
WORKDIR /usr/local/jar
ADD /target/app.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]