FROM openjdk:17
WORKDIR /app

# Install dos2unix
RUN apt update && apt install dos2unix

# Copy the Maven wrapper files and convert line endings
COPY ./mvnw ./mvnw
RUN dos2unix ./mvnw
COPY ./.mvn ./.mvn
COPY ./pom.xml ./pom.xml

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy the source code
COPY ./src ./src

# Package the application
RUN ./mvnw package -DskipTests

# Run the application
CMD ["java", "-jar", "target/MedChannel-0.0.1-SNAPSHOT.jar"]
