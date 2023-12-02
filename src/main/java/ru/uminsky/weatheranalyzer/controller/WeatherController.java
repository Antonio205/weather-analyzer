package ru.uminsky.weatheranalyzer.controller;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.uminsky.weatheranalyzer.model.Weather;
import ru.uminsky.weatheranalyzer.service.WeatherService;

import java.io.IOException;

@Controller
@RequestMapping("/")
public class WeatherController implements ErrorController {

	private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

	@Autowired
	WeatherService weatherService;

	@Value("${weather.city}")
	private String defaultCity;

	@RequestMapping("/")
	public String getHomePage(Model model) {

		logger.info("Request received for home page.");

		return "index";
	}

	// first endpoint, returns the current value from the database, if missing, reports it
	@GetMapping("/weather/current")
	public String getCurrentTopWeather(Model model) {

		logger.info("Request received for current weather data.");

		Weather weather = weatherService.findTopWeather(defaultCity);

		if (weather != null) {
			model.addAttribute("weather", weather);
			logger.info("Current weather data found and sent to the view.");
			return "weather/current_weather";
		} else {
			model.addAttribute("error", "There aren't any weather records yet! Wait for a minute");
			logger.warn("No current weather data found in the database.");
			return getHomePage(model);
		}
	}

	// writes a new value from the api to the database
	// now data is entered once every 15 minutes, because in the api they are updated every 15 minutes too
	@Scheduled(fixedRateString = "${weather.api.update.period}")
	public void saveCurrentWeatherData() {
		try {
			logger.info("Scheduled task started: saving current weather data.");
			Weather weatherData = this.weatherService.getWeatherDataCity(defaultCity);
			weatherService.saveWeather(weatherData);
			logger.info("Scheduled task completed: current weather data saved.");
		} catch (IOException e) {
			logger.error("Error occurred while saving current weather data.", e);
		}
	}

	// second endpoint, displays the average temperature value for all data between two dates inclusive
	@GetMapping("/weather/average_temperature")
	public String getAverageTemperature(
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			Model model) throws IOException {

		logger.info("Request received for average temperature between dates.");

		if (startDate.isAfter(endDate)) {
			model.addAttribute("error", "Start date must be earlier than end date");
			logger.warn("Invalid date range provided: start date is later than end date.");
			return getHomePage(model);
		}

		Double averageTemperatureBetweenDates = this.weatherService.findAverageTemperatureBetweenDates(startDate, endDate, defaultCity);

		if (averageTemperatureBetweenDates != null) {
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("averageTemperature", String.format("%.2f", averageTemperatureBetweenDates));
			logger.info("Average temperature between dates calculated and sent to the view.");
			return "weather/average_temperature";
		} else {
			model.addAttribute("error", "There are no records for the time you indicated");
			logger.warn("No weather records found for the provided date range.");
			return getHomePage(model);
		}
	}

}