FROM openjdk:17-jdk-alpine
EXPOSE 8086
WORKDIR /app
COPY target/librarysystem.jar /app/librarysystem.jar
CMD ["java", "-jar", "librarysystem.jar"]