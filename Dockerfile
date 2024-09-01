FROM openjdk:17
WORKDIR /app

# Copy the Maven wrapper files and pom.xml
COPY ./mvnw ./mvnw
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
