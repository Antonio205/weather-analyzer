package ru.uminsky.weatheranalyzer.service;

import java.time.LocalDate;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.uminsky.weatheranalyzer.model.Weather;
import ru.uminsky.weatheranalyzer.parsers.WeatherParser;
import ru.uminsky.weatheranalyzer.repository.WeatherRepository;

import java.io.IOException;

@Service
public class WeatherServiceImpl implements WeatherService {

	private static final Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

	@Value("${rapid.api.key}")
	private String rapidApiKey;

	@Autowired
	private WeatherRepository weatherRepository;

	@Autowired
	private WeatherParser parser;

	@Override
	public Weather getWeatherDataCity(String city) throws IOException {
		logger.info("Requesting weather data for city: {}", city);
		String jsonData = connectAPICity(city);

		return parser.jsonParseCityWeather(jsonData);
	}

	@Override
	public Weather saveWeather(Weather weather) {
		logger.info("Saving weather data: {}", weather);
		return weatherRepository.save(weather);
	}

	// method for first endpoint
	@Override
	public Weather findTopWeather() {
		logger.info("Fetching top weather record");
		return weatherRepository.findTopByOrderByIdDesc();
	}

	// method for second endpoint
	@Override
	public Double findAverageTemperatureBetweenDates(LocalDate startDate, LocalDate endDate, String city) {
		logger.info("Fetching average temperature between dates: {} and {}", startDate, endDate);
		return weatherRepository.findAverageTemperatureBetweenDates(startDate, endDate, city);
	}

	// Connect API with city as an argument
	private String connectAPICity(String city) throws IOException {
		OkHttpClient client = new OkHttpClient();
		Request request;

		request = new Request.Builder()
				.url("https://weatherapi-com.p.rapidapi.com/current.json?q=" + city)
				.get()
				.addHeader("X-RapidAPI-Key", rapidApiKey)
				.addHeader("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
				.build();

		logger.info("Sending API request for weather data in city: {}", city);
		String response = getResponse(client, request);

		return response;
	}

	// Return response
	private String getResponse(OkHttpClient client, Request request) throws IOException {
		Response response = client.newCall(request).execute();
		String getResponseBody = response.body().string();
		logger.info("Received weather data response: {}", getResponseBody);
		return getResponseBody;
	}

}
