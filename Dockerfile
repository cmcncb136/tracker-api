FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY build/libs/*.jar app.jar
EXPOSE 1204
ENTRYPOINT ["java", "-jar", "/app/app.jar"]