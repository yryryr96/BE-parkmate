FROM eclipse-temurin:17-jdk-alpine

COPY ./build/libs/*SNAPSHOT.jar project.jar

ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "project.jar"]