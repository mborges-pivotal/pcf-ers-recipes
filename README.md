# PCF Elastic Runtime Service (ERS) Recipes
Simple recipes for building cloud native application using spring boot. 

## Getting Started

Fork the repository and make sure you have the pre-requisites listed below.

**Prerequisites**
- [Spring Tool Suite](https://spring.io/tools) - Eclipe based IDE with Spring Boot support
- [Project Lombok](https://projectlombok.org/) - Helps build entity classes without boilerplate code

**Building**
```
$ git clone [YOUR_REPO]
$ cd [YOUR_REPO]
$ ./mvnw clean install
``` 

### To run the application locally
The application is set to use an embedded H2 database in non-PaaS environments, and to take advantage of Pivotal CF's auto-configuration for services. To use a MySQL Dev service in PCF, simply create and bind a service to the app and restart the app. No additional configuration is necessary when running locally or in Pivotal CF.

In Pivotal CF, it is assumed that a Pivotal MySQL service will be used.

```
$ ./mvnw spring-boot:run
```

Then go to the http://localhost:8080 in your browser

### Running on Cloud Foundry
Take a look at the manifest file for the recommended setting. Adjust them as per your environment.

## Demo Scripts summary
The application tries to be self-descriptive. You'll see when you access the application.


