FROM openjdk:17

EXPOSE 8080 8080

ARG JAR_FILE=./build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}

ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "/app.jar"]