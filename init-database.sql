
/*    Weather table    */

CREATE TABLE weather (
    id SERIAL PRIMARY KEY,
    temperature DOUBLE PRECISION,
    wind_speed DOUBLE PRECISION,
    pressure DOUBLE PRECISION,
    humidity DOUBLE PRECISION,
    country VARCHAR(255),
    city VARCHAR(255),
    timestamp TIMESTAMP,
    weather_conditions VARCHAR(255)
);

/*     Test data      */

INSERT INTO weather (temperature, wind_speed, pressure, humidity, country, city, timestamp, weather_conditions)
VALUES
    (25.5, 10.2, 1015.5, 60.0, 'Belarus', 'Minsk', '2023-12-01', 'Sunny'),
    (20.0, 8.5, 1010.0, 55.5, 'Belarus', 'Minsk', '2023-12-01', 'Cloudy'),
    (18.5, 12.8, 1012.2, 70.0, 'Belarus', 'Minsk', '2023-12-01', 'Rainy'),
    (22.3, 15.0, 1013.8, 65.5, 'Belarus', 'Minsk', '2023-12-02', 'Partly Cloudy'),
    (19.8, 11.7, 1011.5, 58.0, 'Belarus', 'Minsk', '2023-12-02', 'Clear'),
    (21.5, 13.2, 1014.7, 68.5, 'Belarus', 'Minsk', '2023-12-02', 'Snowy'),
    (24.0, 9.0, 1017.3, 62.5, 'Belarus', 'Minsk', '2023-12-03', 'Foggy'),
    (17.2, 14.5, 1011.8, 75.0, 'Belarus', 'Minsk', '2023-12-03', 'Thunderstorm'),
    (20.5, 10.8, 1015.0, 57.5, 'Belarus', 'Minsk', '2023-12-03', 'Windy'),
    (23.8, 12.0, 1013.5, 64.0, 'Belarus', 'Minsk', '2023-12-04', 'Misty');

