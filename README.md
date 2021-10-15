# URL Shortner

[![URL Shortener Docker CD](https://github.com/urls/url-shortner/actions/workflows/ci-workflow.yml/badge.svg?branch=master)](https://github.com/urls/url-shortner/actions/workflows/ci-workflow.yml)

A modern, minimalist, and lightweight URL shortener application.

---

## Setup

### For devlopment:

1. Start the mongo container for database using docker:

    ```
    docker run --name mongo -p 27017:27017 -v $(pwd)/.local/data:/data/db mongo:latest
    ```

2. Start the `url-shortener-backend`:
    ```
    cd url-shortener-backend
    ./gradlew bootRun
    ```

> Note: You can also run the backend using any IDE.

### Alternatively, run application using docker-compose:

```
docker-compose up --build
```

---

## Issues

You can report the bugs at the [issue tracker](https://github.com/urls/url-shortener/issues)

**OR**

You can [tweet me](https://twitter.com/iamarpandey) if you can't get it to work. In fact, you should tweet me anyway.

---

## License

Built with ♥ by awesome [people](https://github.com/urls/url-shortener/graphs/contributors) under [MIT License](LICENSE)
