



To run the backend locally, either:

1) Using gradle:
   1) Get the db up by following instructions in the [README](../url-shortener-db/readme.md) in url-shortener-db
   
   2) 1) cd into url-shortener-backend
      2) `./gradle bootRun` OR `./gradle build and Run the main() function`
   
    For reference: https://www.jetbrains.com/help/idea/your-first-spring-application.html#add-home-page


2) Run using docker:
      1) cd into `url-shortener-backend `
      2) Create a docker network: `docker network create shared-net`
         1) Run the db in the same network by adding `--network = shared-net` in Step 2 of [db README](../url-shortener-db/readme.md)
      3) Add this to application.properties:
         1) `spring.data.mongodb.host=mongo  //or your db_container_name`
         
         2) `spring.data.mongodb.port=27017`
         
         3) and delete this line `spring.data.mongodb.uri=mongodb://localhost:27017/urls`
      4) `docker build -t url-shortener-backend .`
      5) `docker run --network=shared-net url-shortener-backend`
   

Note: The application will run on port 8080 by default. 