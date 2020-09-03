package com.temperature.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

import com.temperature.api.add.TemperatureRequest;
import com.temperature.api.add.TemperatureResponse;
import com.temperature.api.list.TemperatureListResponse;
import com.temperature.model.Temperature;
import com.temperature.repository.TemperatureRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(MockitoJUnitRunner.class)
public class TemperatureServiceImplTest {

    @Mock
    private TemperatureRepository temperatureRepository;

    @InjectMocks
    private TemperatureServiceImpl temperatureService;

    private TemperatureRequest temperatureRequest;
    private Temperature temperature;
    private List<Temperature> temperatureList;
    private String date = "2020-09-02";
    private String conversion = "C";

    @Before
    public void init() {
        temperatureRequest = new TemperatureRequest();
        temperatureRequest.setCreationDate("2020-09-02 09:37:15");
        temperatureRequest.setTemperatureValue(23.9);

        temperature = new Temperature();
        temperature.setId(Long.getLong("1"));
        temperature.setTemperatureValue(23.9);
        temperature.setCreationDate(LocalDateTime.now());
        temperatureList = new ArrayList<>();
        temperatureList.add(temperature);
    }

    @Test
    public void createTemperatureWithValuesCorrect() {
        // Data preparation
        Mockito.when(temperatureRepository.save(any(Temperature.class))).thenReturn(temperature);

        // Method call
        Mono<TemperatureResponse> temperatureResponseMono = temperatureService.save(temperatureRequest);

        // Verification
        Assert.assertNotNull(temperatureResponseMono);
        Mockito.verify(temperatureRepository, Mockito.times(1)).save(any(Temperature.class));
    }

    @Test
    public void getTemperatureByDateCelcius() {
        // Data preparation
        Mockito.when(temperatureRepository.findByDate(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(temperatureList);

        // Method call
        Mono<TemperatureListResponse> temperatureResponseMono = temperatureService.getTemperatureByDate(date, conversion);

        // Verification
        Assert.assertNotNull(temperatureResponseMono);
        Mockito.verify(temperatureRepository, Mockito.times(1)).findByDate(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    public void getTemperatureByDateFahrenheit() {
        conversion = "F";
        // Data preparation
        Mockito.when(temperatureRepository.findByDate(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(temperatureList);

        // Method call
        Mono<TemperatureListResponse> temperatureResponseMono = temperatureService.getTemperatureByDate(date, conversion);

        // Verification
        Assert.assertNotNull(temperatureResponseMono);
        Mockito.verify(temperatureRepository, Mockito.times(1)).findByDate(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    public void getTemperatureByDateGroupHourCelcius() {
        // Data preparation
        Mockito.when(temperatureRepository.findByDate(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(temperatureList);

        // Method call
        Flux<TemperatureListResponse> temperatureResponseFlux = temperatureService.getTemperatureByHour(date, conversion);

        // Verification
        Assert.assertNotNull(temperatureResponseFlux);
        Mockito.verify(temperatureRepository, Mockito.times(1)).findByDate(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    public void getTemperatureByDateGroupHourFahrenheit() {
        conversion = "F";
        // Data preparation
        Mockito.when(temperatureRepository.findByDate(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(temperatureList);

        // Method call
        Flux<TemperatureListResponse> temperatureResponseFlux = temperatureService.getTemperatureByHour(date, conversion);

        // Verification
        Assert.assertNotNull(temperatureResponseFlux);
        Mockito.verify(temperatureRepository, Mockito.times(1)).findByDate(any(LocalDateTime.class), any(LocalDateTime.class));
    }

}
