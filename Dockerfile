# ---------- Build stage ----------
FROM maven:3.9-eclipse-temurin-17 AS build

# Set working directory inside container
WORKDIR /app

# Copy pom.xml and download dependencies (better layer caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the Spring Boot JAR (skip tests for faster build)
RUN mvn clean package -DskipTests

# ---------- Run stage ----------
FROM eclipse-temurin:17-jre-alpine

# Set working directory inside container
WORKDIR /app

# Copy the built jar from the build stage
# This will pick something like course-purchase-system-0.0.1-SNAPSHOT.jar
COPY --from=build /app/target/*.jar app.jar

# Default port (for local Docker run).
# On Render, this will be overridden by their PORT env variable.
ENV PORT=8080

# Set Spring profile to `git` by default
ENV SPRING_PROFILES_ACTIVE=git

# Expose the internal port (for local usage; platforms may ignore it)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
