#which "official images"
FROM openjdk:22-slim-bookworm
#workdir
ARG JAR_FILE
COPY target/musicwebsite-0.0.1-SNAPSHOT.jar app.jar

ENV SPRING_CONFIG_LOCATION=classpath:/application:yaml
ENV SQL_SERVER_IP=172.20.0.2
ENV DATABASE_URL=jdbc:sqlserver://172.20.0.2:1433/musicwebsite
ENTRYPOINT ["java","-jar","/app.jar"]


#FROM openjdk:22-slim-bookworm
#WORKDIR /app
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#RUN ./mvnw dependency:go-offline
#COPY src ./src
#CMD ["./mvnw","spring-boot:run"]