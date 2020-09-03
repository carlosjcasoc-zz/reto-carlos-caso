package com.temperature.repository;

import com.temperature.model.Temperature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TemperatureRepository extends JpaRepository<Temperature, Long> {

    @Query(value = "SELECT *" +
            " FROM temperature s" +
            " WHERE s.creation_date >= :startDate" +
            " AND s.creation_date < :endDate", nativeQuery = true)
    List<Temperature> findByDate(LocalDateTime startDate, LocalDateTime endDate);
}
