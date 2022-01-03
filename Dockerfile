FROM openjdk:17-alpine
LABEL maintainer="anshulsaraf3@gmail.com"
ARG JAR_FILE=target/Luffy.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar" ,"--database.host=mongodb://host.docker.internal", "--spring.rabbitmq.host=host.docker.internal"]
EXPOSE 8080