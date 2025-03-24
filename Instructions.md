# Instructions for Popcorn Palace Movie Ticket Booking System

This document provides instructions on how to set up, run, and test the Popcorn Palace Movie Ticket Booking System, a RESTful API built with Spring Boot for managing movies, showtimes, and ticket bookings.

## Prerequisites

Before proceeding, ensure you have the following installed:

- **Java 17**: Download from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/#java17) or use a tool like SDKMAN (`sdk install java 17.0.10-tem`).
- **Maven**: Included with most IDEs or install via [Maven](https://maven.apache.org/download.cgi).
- **Docker**: Install from [Docker Desktop](https://www.docker.com/products/docker-desktop/) to run the PostgreSQL database.
- **Git**: To clone the repository (optional if you’re working locally).
- **Postman** (optional): For testing API endpoints manually ([Download](https://www.postman.com/downloads/)).

## Setup Instructions

Follow these steps to set up and run the application locally:

### 1. Clone the Repository
If you’re using a Git repository:
```bash
    git clone <your-public-repo-url>
    cd popcorn-palace
```
Alternatively, use the project folder containing the provided code.
### 2. Start PostgreSQL with Docker

The project uses a PostgreSQL database, configured via docker-compose.yml.

Start the Docker container:

    docker-compose up -d

This launches a PostgreSQL instance with:
- Database: `popcorn_palace`
- User: `admin`
- Password: `password`
- Port: `5432`

Verify it’s running:

    docker ps
- Look for a container named something like popcorn-palace-db-1.

### 3. Build the Project

Compile and download dependencies using Maven:

    mvn clean install

- This ensures all required libraries (e.g., Spring Boot, PostgreSQL driver) are fetched.

### 4. Run the Application

Start the Spring Boot application:

    mvn spring-boot:run

- The app will start on http://localhost:8080.
- Logs will appear in the terminal, indicating the app is running and connected to PostgreSQL (look for Started PopcornPalaceApplication).

**Stopping the Application**

- Press `Ctrl+C` in the terminal to stop the app.
- Stop the Docker container:

    
    docker-compose down

## Using the API

The API provides endpoints for managing movies, showtimes, and bookings. Use a tool like Postman or curl to interact with it.

**Base URL**

- `http://localhost:8080`

**Key Endpoints**

#### Movies

- Get All Movies: `GET /movies/all`
  - Response: `List of movies (e.g., [{"id": 1, "title": "Inception", ...}])`
- Add a Movie: `POST /movies` 
  - Body: `{"title": "Inception", "genre": "Sci-Fi", "duration": 148, "rating": 8.8, "releaseYear": 2010}`
  - Response: `Created movie object`
- Update a Movie: `POST /movies/update/{movieTitle}`
  - Example: `POST /movies/update/Inception`
  - Body: `Updated movie details`
- Delete a Movie: `DELETE /movies/{movieTitle}`
    - Example: `DELETE /movies/Inception`

#### Showtimes

- Get Showtime by ID: `GET /showtimes/{showtimeId}`
    - Example: `GET /showtimes/1`
- Add a Showtime: `POST /showtimes`
    - Body: `{"movieId": 1, "price": 20.2, "theater": "Sample Theater", "startTime": "2025-02-14T11:47:46Z", "endTime": "2025-02-14T14:47:46Z"}`
- Update a Showtime: `POST /showtimes/update/{showtimeId}`
    - Example: `POST /showtimes/update/1`
- Delete a Showtime: `DELETE /showtimes/{showtimeId}`
    - Example: `DELETE /showtimes/1`

#### Bookings

- Book a Ticket: `POST /bookings`
  -   Body: `{"showtimeId": 1, "seatNumber": 15, "userId": "84438967-f68f-4fa0-b620-0f08217e76af"}`
    - Response: `{"bookingId": "uuid"}`

### Example with Postman

- Open Postman and create a new request.
- Set the method to POST, URL to http://localhost:8080/movies.
- Add header: `Content-Type: application/json.`
- Set body to "raw" JSON:


      {
          "title": "Inception",
          "genre": "Sci-Fi",
          "duration": 148,
          "rating": 8.8,
          "releaseYear": 2010
      }
  Click "Send" and check the response (e.g., 200 OK with the movie object).

## Running Tests

The project includes unit and integration tests to verify functionality. Tests are located in src/test/java/com/xai/popcornpalace.
Prerequisites for Testing

  Maven and Java 17 (already set up from the run instructions).

### Running Specific Tests

To run a specific test class (e.g., PopcornPalaceApplicationTests):

    mvn test -Dtest=PopcornPalaceApplicationTests
Test Coverage

- **Unit Tests**: Validate service layer logic (e.g., adding movies, checking showtime overlaps).
- **Integration Tests**: Test API endpoints with an in-memory database (H2) or PostgreSQL, covering validation, error handling, and constraints (e.g., no double-booked seats).

Notes on Tests

- If using PostgreSQL for tests, ensure the Docker container is running.
- Tests assume an empty database initially; pre-populate data if needed (e.g., via POST /movies before running booking tests).

### Troubleshooting

- **App Won’t Start**: Check PostgreSQL is running (docker ps) and the port 5432 isn’t blocked.
- **Schema Errors**: If you see errors like null value in column "theater_id", drop the showtimes table (DROP TABLE showtimes; in psql) and restart the app to recreate it with the correct schema (theater as a string).
- **Test Failures**: Ensure the app’s dependencies are built (mvn clean install) and check test logs for details.

