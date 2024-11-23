FROM openjdk:11-jdk-slim AS build
FROM tomcat:9.0-jdk11-openjdk-slim

WORKDIR /

RUN apt-get update && apt-get install -y maven

COPY pom.xml .

RUN mvn -B dependency:resolve

COPY src ./src

RUN mvn clean package -DskipTests

COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/

EXPOSE 8082

CMD ["catalina.sh", "run"]