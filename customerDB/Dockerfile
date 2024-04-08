FROM gradle:jdk17 AS builder
COPY . /home/gradle/app
COPY settings.xml /root/.m2/settings.xml
WORKDIR /home/gradle/app
RUN gradle build

FROM openjdk:17.0.2-slim
COPY --from=builder /home/gradle/app/build/libs/CustomerDB-1.0-SNAPSHOT.jar /app/customerDB.jar
WORKDIR /app
CMD ["java", "-jar", "customerDB.jar"]