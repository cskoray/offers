# Offers Project

This project is a Spring Boot application that provides a RESTful API for managing offers. It allows
users to create, read, update, and delete offers. Users can also mark offers as favorites and remove
them from favorites.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for
development and testing purposes.

### Prerequisites

- Java 17 or higher
- Maven 3.6.3 or higher
- Environment variables for the database connection
  - DATASOURCE_USERNAME=postgres
  - DATASOURCE_PASSWORD=postgres
  - UNSPLASH_ACCESS_KEY=xDmX0hLExU7ZO-7ma0Lw37-fFDLCWrC-TrxcZ4rInQE
  - spring_profiles_active=dev

### Installing

1. Clone the repository
   git clone https://github.com/cskoray/offers.git
2. Navigate into the project directory
   cd offers
3. Build the project using Maven
   mvn clean install
4. Run the application
   mvn spring-boot:run

## Running the tests

To run the automated tests for this system, use the following command:
mvn test

## Deployment

This application can be deployed on any platform that supports Java 17 or higher.

## Built With

- Spring Boot - The web framework used
- Maven - Dependency Management
- Java - Used to write the application

## Author

- cskoray - Initial work - cskoray