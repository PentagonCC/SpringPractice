FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/eureka-discovery-0.0.1-SNAPSHOT.jar /app/
EXPOSE 8761
CMD ["java", "-jar", "eureka-discovery-0.0.1-SNAPSHOT.jar"]