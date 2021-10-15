FROM gradle:7.1-jdk11

WORKDIR /url-shortener-backend

COPY . ./

RUN gradle build -x test

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "build/libs/url-shortener.jar", "--spring.profiles.active=production"]
