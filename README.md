# Weather-analyzer

This Weather Analyzer application is designed to provide insights into current weather data for a specific city. Whether you want to check the current temperature, humidity, and other weather parameters or analyze the average temperature over a certain period, this application has got you covered.

# Features

Current Weather Display: Obtain real-time weather data for a specified city, including temperature, wind speed, pressure, humidity, and current weather conditions.

Scheduled Data Update: The application automatically fetches and updates weather data for the default city at regular intervals (configurable in the application properties).

Average Temperature Calculation: Calculate and display the average temperature over a specified date range for the chosen city.

# Technologies Used

Spring Boot: A powerful and flexible Java framework for building web applications.

Thymeleaf: A modern server-side Java template engine for web and standalone environments.

Hibernate: An object-relational mapping framework for the Java programming language.

OkHttp: An open-source HTTP client for Java that simplifies communication with APIs.

# Getting started

To get a local copy of Weather Finder up and running follow these simple example steps.

1. Open your terminal

2. Go to the directory where you want your copy of this repository to be copied to

3. Type git clone https://github.com/Antonio205/weather-analyzer.git and it will copy

4. Get a free API Key at [WeatherAPI] (https://rapidapi.com/weatherapi/api/weatherapi-com)

5. Go to your application.properties file and enter the API key for config property rapid.api.key=YOUR_RAPID_API_KEY

6. Deploy the database using the script from the init-database.sql file

7. Enter spring.datasource.url, spring.datasource.username, spring.datasource.password of your database in application.properties file

8. Change weather.city=Minsk or weather.api.update.period=900000 to the city and time interval you are interested in (at the moment, the application is updated every 15 minutes, because the rapid api itself is updated every 15 minutes)

# Author

Uminsky Anton

