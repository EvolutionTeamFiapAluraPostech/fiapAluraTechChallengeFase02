FROM openjdk:17-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=build/libs/DigitalParking-*-SNAPSHOT.jar
COPY ${JAR_FILE} digitalParking.jar
ENTRYPOINT ["java", "-jar", "/digitalParking.jar"]
