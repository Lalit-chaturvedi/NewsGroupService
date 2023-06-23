FROM openjdk:17
EXPOSE 8080
COPY target/news-group-service.jar news-group-service.jar
ENTRYPOINT ["java","-jar","/news-group-service.jar"]