package ru.uminsky.weatheranalyzer.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.uminsky.weatheranalyzer.parsers.WeatherParser;

@Configuration
public class AppConfig {

    @Bean
    public WeatherParser weatherParser() {
        return new WeatherParser();
    }

}
