FROM openjdk:11
ADD url-shortener.jar url-shortener.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "url-shortener.jar"]
