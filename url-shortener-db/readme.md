### Steps to run mongodb

1. Build the docker image
    ```
    # docker build -f Dockerfile -t mongo .
    ```
2. Spin up the docker container
    ```
    # docker run --name mongo -p 27017:27017 -v data:/data/db mongo
    ```

> Note: run both command inside the `url-shortener-db` directory.
