# URL Shortner
[![URL Shortener Docker CD](https://github.com/urls/url-shortner/actions/workflows/cd-workflow.yml/badge.svg?branch=master)](https://github.com/urls/url-shortner/actions/workflows/cd-workflow.yml)

Prerequisites:

1) JDK 11
2) Docker

To run this project using docker-compose :
 1) `cd url-shortener-backend`
 2) `./gradlew build `
 3) `cd .. `
 4) `docker-compose up`

To get the backend and db separately refer the following:
 1) [DB README](./url-shortener-db/readme.md)
 2) [Backend README](./url-shortener-backend/README.md)


