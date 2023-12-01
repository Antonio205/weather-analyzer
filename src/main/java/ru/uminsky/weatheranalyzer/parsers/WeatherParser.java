package ru.uminsky.weatheranalyzer.parsers;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.uminsky.weatheranalyzer.model.Weather;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class WeatherParser {

    private static final Logger logger = LoggerFactory.getLogger(WeatherParser.class);

    private JSONObject jsonObject;

    public Weather jsonParseCityWeather(String jsonData) {
        this.jsonObject = new JSONObject(jsonData);

        return getParsedData();
    }

    // Parse weather data from JSON
    private Weather getParsedData() {
        Weather weather = new Weather();

        try {
            String name = jsonObject.getJSONObject("location").getString("name");
            String country = jsonObject.getJSONObject("location").getString("country");

            String localTimeString = jsonObject.getJSONObject("location").getString("localtime");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");
            LocalDateTime localDateTime = LocalDateTime.parse(localTimeString, formatter);
            LocalDate localDate = localDateTime.toLocalDate();

            double humidity = jsonObject.getJSONObject("current").getInt("humidity");
            double pressure = jsonObject.getJSONObject("current").getDouble("pressure_mb");
            double windMph = jsonObject.getJSONObject("current").getDouble("wind_mph");
            double temp_c = jsonObject.getJSONObject("current").getDouble("temp_c");
            String weatherConditions = jsonObject.getJSONObject("current").getJSONObject("condition").getString("text");

            weather.setCity(name);
            weather.setCountry(country);
            weather.setHumidity(humidity);
            weather.setPressure(pressure);
            weather.setWindSpeed(windMph);
            weather.setTemperature(temp_c);
            weather.setTimestamp(localDate);
            weather.setWeatherConditions(weatherConditions);

        } catch (Exception e) {
            logger.error("Error setting weather parameters", e);
        }

        logger.info("The parameters from JSON have been successfully set: {}", weather);

        return weather;
    }

}
