# TODO API with Spark
REST API for "TODO" application using Spark framework

This project is stil work in progress, for details about what is and what's not yet implemented please check on the list below.

This project is simulation of working together with frontend developer.
My role in this simulation is to provide a working API (using database) for "TODO" simple web application that alredy has developed
frontend using Angular.js.

Project initial files where provided by project idea creators (https://teamtreehouse.com).

Project was meant to follow a set of requirements. Base requirements were as follows:

1. Model stores id, name, and isCompleated (done)
2. Interface provides create, update, delete and the ability to findAll (done)
3. Sql2o DAO implementation implements all methods from the Todo DAO interface and creates the table on initialization (done)
4. GET /api/v1/todos return all todos and populates the web application (done)
5. POST /api/v1/todos creates a new Todo, returns it and sets the appropriate status code (done)
6. PUT /api/v1/todos/xxx is handled and proper Todo is updated (done)
7. DELETE /api/v1/todos/xxx deletes the appropriate Todo sends back the appropriate status code and an empty body (done)

Additional requirements:

1. Add unit tests for DAO implementation methods (done)
2. Functional tests are written for GET/POST/PUT/DELETE requests (WIP)

To check my other work please go to:

- https://github.com/grzegorzkonczak/instateam-with-spring-and-hibernate - Project team management web application using Spring with Hibernate.
- https://github.com/grzegorzkonczak/analyze-public-data-with-hibernate - Console application for managing Countries data using Hibernate and H2 file database.
- https://github.com/grzegorzkonczak/countries-of-the-world-with-spring - Spring web application that displays information about 5 countries
- https://github.com/grzegorzkonczak/personal-blog - Simple web blog application built using Spark Framework
- https://github.com/grzegorzkonczak/Soccer-League-Organizer - Console based soccer team management application
- https://github.com/grzegorzkonczak/how_many_in_jar_game - Console based implementation of "How many in jar" game
