FROM eclipse-temurin:17.0.1_12-jre-alpine
WORKDIR /app
ADD /target/survey-0.0.1-SNAPSHOT.jar /app/survey.jar
CMD java -jar /app/survey.jar
