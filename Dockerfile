# Use a base image with Java and a lightweight OS
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy your application JAR into the container
COPY target/*.jar app.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
