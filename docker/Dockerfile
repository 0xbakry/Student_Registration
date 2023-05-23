# Use a base image with the required dependencies
FROM openjdk:11

# Set the working directory inside the container
WORKDIR /app

# Copy the application files to the container
COPY *.java /app

# Compile the Java application
RUN javac *.java

# Set the command to run the application
CMD ["java", "StudentRegistration"]
