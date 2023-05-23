# Use a base image with the required dependencies
FROM openjdk:11

# Set the working directory inside the container
WORKDIR /app

# Copy the application files to the container
COPY src/ /app/src/

# Compile the Java application
RUN javac /app/src/*.java

# Set the command to run the application
CMD ["java", "StudentRegistration"]
