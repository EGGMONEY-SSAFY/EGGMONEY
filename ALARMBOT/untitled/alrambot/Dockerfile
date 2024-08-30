# Use the official Liberica JDK 17 image from the Docker Hub
FROM bellsoft/liberica-openjdk-alpine:17

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY build/libs/alrambot-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the application will run on
EXPOSE 8080

# Define the command to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
