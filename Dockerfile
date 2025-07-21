# Use an OpenJDK base image
FROM openjdk:21-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy built JAR file (update the jar name accordingly)
COPY build/libs/restaurantfinder-0.0.1-SNAPSHOT.jar app.jar

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
