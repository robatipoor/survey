FROM maven:3.8.4-eclipse-temurin-17-alpine AS builder
ENV HOME=/app
WORKDIR $HOME
ADD pom.xml $HOME
RUN /usr/local/bin/mvn-entrypoint.sh mvn verify clean --fail-never
ADD . $HOME
RUN mvn package -DskipTests

FROM eclipse-temurin:17.0.1_12-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/survey-0.0.1-SNAPSHOT.jar survey.jar
CMD java -jar survey.jar
