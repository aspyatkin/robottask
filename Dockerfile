FROM adoptopenjdk:16.0.1_9-jre-hotspot-focal
RUN addgroup --system spring && adduser --system --group spring
USER spring:spring
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]