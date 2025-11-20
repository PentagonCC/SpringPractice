FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/gateway-service-0.0.1-SNAPSHOT.jar /app/
EXPOSE 9000
CMD ["java", "-jar", "gateway-service-0.0.1-SNAPSHOT.jar"]