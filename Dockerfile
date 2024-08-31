# Stage 1: Build the application
FROM openjdk:17 AS build

WORKDIR /app

# Copy the Maven wrapper and the pom.xml file
COPY ./mvnw ./mvnw
COPY ./.mvn ./.mvn
COPY ./pom.xml ./pom.xml

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy the source code
COPY ./src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17

WORKDIR /app

# Copy the JAR file from the previous build stage
COPY --from=build /app/target/MedChannel-0.0.1-SNAPSHOT.jar /app/MedChannel-0.0.1-SNAPSHOT.jar

# Expose the port the application runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "MedChannel-0.0.1-SNAPSHOT.jar"]
