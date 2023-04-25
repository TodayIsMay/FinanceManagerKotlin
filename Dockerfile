FROM amazoncorretto:11
COPY target/finance-manager-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]