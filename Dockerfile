#which "official images"
FROM openjdk:22-slim-bookworm
#workdir
WORKDIR /app
#Copy from your Host(PC,laptop) to container
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
#Run inside the image
RUN ./mvnw dependency:go-offline
COPY src ./src
#run inside container
CMD ["./mvnw","spring-boot:run"]
EXPOSE 9000


