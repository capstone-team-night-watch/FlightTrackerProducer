FROM openjdk:17-jdk-slim

EXPOSE 8080
ADD /target/*.jar demo.jar

ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=prod" ,"demo.jar"]
