FROM eclipse-temurin:17.0.1_12-jdk-alpine AS builder
WORKDIR /app
COPY . ./
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17.0.1_12-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/survey-0.0.1-SNAPSHOT.jar survey.jar
CMD java -jar survey.jar
