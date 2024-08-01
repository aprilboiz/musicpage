# Music Page

- This is a simple music page that suggests songs to listen to based on user's mood.
- This project is the result of learning Spring Boot, and it will be kept updated as I learn more about it.

## Technologies used
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Flask](https://flask.palletsprojects.com/en/3.0.x/)
- Authentication and authorization using JWT
- PostgreSQL database

## How to run the project
- Since the project is using both [Spring Boot](https://spring.io/projects/spring-boot) and [Flask](https://flask.palletsprojects.com/en/3.0.x/) for the backend, you need to run both of them.
- To simplify the process, I have created a Docker Compose file that will run both of them.
- **Note**: You will need to have [Docker](https://www.docker.com/) installed on your machine.

You will have 2 ways to run the project:
1. **For those who have Java installed on their machine, you can run the following command:**
    ```bash
    ./gradlew bootRun
    ```
    You will need to have [Java 21](https://www.oracle.com/java/technologies/downloads/#java21) installed on your machine.
    I have configured the project to run on this way by default.

2. **For those who don't have Java installed on their machine, you need to change a little bit the configuration.**
   - Open the `extractService.properties` file located in `src/main/resources` and change the `extractService.url` to `http://flask-service:5000/api/extract?url=`.
   - Open the `application.properties` file located in `src/main/resources` and change the `spring.datasource.url` to `jdbc:postgresql://db:5432/music_page`.
   - Open the `compose.yml` file located in the root directory and uncomment the `spring-service` section.
   - Open the `build.gradle` file located in the root directory and comment the `runtimeOnly 'org.springframework.boot:spring-boot-docker-compose'` line.
   - After that, you can run the following command:
        ```bash
        docker-compose up -d
        ```
        You will need to wait a little bit for the services to start if it is the first time you run it.

After that, you can take a quick look at the API documentation by visiting `http://localhost:8080/swagger-ui/index.html`.
