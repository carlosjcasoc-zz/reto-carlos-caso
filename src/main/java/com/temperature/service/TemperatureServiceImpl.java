package com.temperature.service;

import com.temperature.api.add.TemperatureRequest;
import com.temperature.api.add.TemperatureResponse;
import com.temperature.api.list.TemperatureListResponse;
import com.temperature.model.Temperature;
import com.temperature.repository.TemperatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class TemperatureServiceImpl  implements TemperatureService{

    @Autowired
    private TemperatureRepository temperatureRepository;

    @Override
    public Mono<TemperatureResponse> save(TemperatureRequest temperatureRequest) {
        Temperature temperature = new Temperature();
        temperature.setTemperatureValue(temperatureRequest.getTemperatureValue());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(temperatureRequest.getCreationDate(), formatter);
        temperature.setCreationDate(dateTime);
        Temperature temperatureAdd = temperatureRepository.save(temperature);
        TemperatureResponse temperatureResponse = new TemperatureResponse();
        temperatureResponse.setId(temperatureAdd.getId());
        temperatureResponse.setTemperatureValue(temperatureAdd.getTemperatureValue());
        temperatureResponse.setCreationDate(temperatureAdd.getCreationDate());
        return Mono.just(temperatureResponse);
    }

    @Override
    public Mono<TemperatureListResponse> getTemperatureByDate(String date, String conversion) {
        List<Temperature>  listTemperatures;
        LocalDate dateConvert = LocalDate.parse(date);
        LocalDateTime startDate = dateConvert.atStartOfDay();
        LocalDateTime endDate = dateConvert.plusDays(1).atStartOfDay();
        listTemperatures = temperatureRepository.findByDate(startDate, endDate);
        TemperatureListResponse temperatureListResponse = buildTemperatureListByDateResponse(listTemperatures, conversion);
        temperatureListResponse.setDate(date);
        return Mono.justOrEmpty(temperatureListResponse);
    }

    @Override
    public Flux<TemperatureListResponse> getTemperatureByHour(String date, String conversion) {
        List<TemperatureListResponse> listByHourResponse = new ArrayList<>();
        List<Temperature>  listTemperatures;
        LocalDate dateConvert = LocalDate.parse(date);
        LocalDateTime startDate = dateConvert.atStartOfDay();
        LocalDateTime endDate = dateConvert.plusDays(1).atStartOfDay();
        listTemperatures = temperatureRepository.findByDate(startDate, endDate);
        Map<Integer, List<Temperature>> groupByPriceMap =
                listTemperatures.stream().collect(Collectors.groupingBy(temperature -> temperature.getCreationDate().getHour()));

        for (Map.Entry<Integer, List<Temperature>> entry : groupByPriceMap.entrySet()) {
            Integer intervalHour = entry.getKey() +1 ;
            TemperatureListResponse temperatureListResponse = buildTemperatureListByDateResponse(entry.getValue(), conversion);
            temperatureListResponse.setTime(entry.getKey().toString().concat(" - " + intervalHour.toString()));
            listByHourResponse.add(temperatureListResponse);
        }

        return Flux.fromIterable(listByHourResponse);
    }

    public Double convertFahrenheit(Double value) {
        return ((9.0 / 5.0) * value) + 32;
    }

    public TemperatureListResponse buildTemperatureListByDateResponse(List<Temperature> listTemperatures, String conversion) {
        TemperatureListResponse temperatureListResponse = null;
        if (listTemperatures.size() > 0) {
            Temperature minObject = listTemperatures.stream().min(Comparator.comparingDouble(Temperature::getTemperatureValue)).get();
            Temperature maxObject = listTemperatures.stream().max(Comparator.comparingDouble(Temperature::getTemperatureValue)).get();

            double average = listTemperatures.stream().mapToDouble(t -> t.getTemperatureValue()).sum() / listTemperatures.size();
            temperatureListResponse = new TemperatureListResponse();

            temperatureListResponse.setMax("" + maxObject.getTemperatureValue() +" ªC");
            temperatureListResponse.setMin("" + minObject.getTemperatureValue()+" ºC");
            temperatureListResponse.setAverage("" + average +" ºC");
            if (!conversion.equals("C")){
                temperatureListResponse.setMax("" + convertFahrenheit(maxObject.getTemperatureValue()) +" ºF");
                temperatureListResponse.setMin("" + convertFahrenheit(minObject.getTemperatureValue())+" ºF");
                temperatureListResponse.setAverage("" + convertFahrenheit(average) +" ºF");
            }
        }
        return temperatureListResponse;
    }
}
