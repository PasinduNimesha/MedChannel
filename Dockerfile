FROM openjdk:17
WORKDIR /app
COPY ./target/MedChannel-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java", "-jar", "MedChannel-0.0.1-SNAPSHOT.jar"]
