FROM openjdk:11
VOLUME /tmp
COPY build/libs/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app.jar"]