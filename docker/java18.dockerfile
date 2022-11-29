# Base image
FROM maven:3.8.6-amazoncorretto-18

# Copy source code
COPY ./ ./

# Compile Java code
RUN mvn clean compile