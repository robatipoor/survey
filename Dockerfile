FROM eclipse-temurin:17.0.1_12-jdk-alpine  AS builder
WORKDIR /app
COPY . ./
RUN ./mvnw dependency:resolve
RUN ./mvnw clean package

FROM eclipse-temurin:17.0.1_12-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/survey-0.0.1-SNAPSHOT.jar /app/survey.jar
CMD java -jar /app/survey.jar
