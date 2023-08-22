# About The Project

A backend system to handle the connection between the future tenants and owners for seasonal rentals.

# Built With

This section outlines the major frameworks, libraries, and dependencies used in the project. Below is a list of key components utilized:
- Spring Boot: A powerful framework for building Java-based applications.
- spring-boot-starter-data-jpa: Starter package for using Spring Data JPA, simplifying database operations.
- mysql-connector-j: MySQL database driver for Java applications.
- lombok: Project Lombok for reducing boilerplate code.
- spring-boot-starter-security: Starter package for Spring Security, enabling authentication and authorization.
- spring-boot-starter-web: Starter package for building web applications with Spring MVC.
- springdoc-openapi-starter-webmvc-ui: Starter package for integrating Springdoc with OpenAPI UI.


# Getting Started
Below are instructions to help you set up and run this project on your local machine. Follow these straightforward steps to get a local copy up and running:

## Prerequisites
This is an example of how to list things you need to use the software and how to install them.

### Required
- JDK 17 and above
- Maven

1. Clone the repo
    >`https://github.com/ESrebuff/ChaTop-api-rest`
   
2. Configure your the application-dev.properties
    create your application-dev.properties at the src/main/ressources
    ```
    #data base info
        DB_USERNAME=xxxx
        DB_PASSWORD=xxxx
    
    #private key
        SECRET_KEY=xxxx

    ```
3. Running the application locally
   To run the Spring Boot application on your local machine, you have a couple of options:
   - Using your IDE: Execute the main method in the **ApiRestApplication class** directly from your integrated development environment (IDE).
   - Alternatively you can use the Spring Boot Maven plugin like so:
        >`mvn spring-boot:run