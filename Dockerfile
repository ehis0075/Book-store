#
# Build stage
#
FROM maven:3-openjdk-17-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
#COPY config/* /home/app/config/
#COPY keys/* /home/app/keys/
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM amazoncorretto:17

WORKDIR /app

COPY --from=build /home/app/target/book-store-service.jar .

ENTRYPOINT ["java", "-Djdk.tls.client.protocols=TLSv1.2", "-jar", "book-store-service.jar"]