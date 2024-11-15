# Build stage
FROM maven:3-openjdk-17 AS build
WORKDIR /app

# Copy the entire project
COPY . .

# Run Maven build (skip tests to speed up the build process)
RUN mvn clean package -DskipTests

# Debugging step to list the contents of the target directory
RUN ls -al /app/target

# Run stage
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the WAR file from the build stage
COPY --from=build /app/target/DrComputer-0.0.1-SNAPSHOT.war drcomputer.war

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "drcomputer.war"]
