package ru.uminsky.weatheranalyzer.repository;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.uminsky.weatheranalyzer.model.Weather;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

    Weather findTopByOrderByIdDesc();

    @Query("SELECT AVG(w.temperature) FROM Weather w WHERE w.timestamp BETWEEN ?1 AND ?2 AND w.city = ?3")
    Double findAverageTemperatureBetweenDates(LocalDate startDate, LocalDate endDate, String city);

}
