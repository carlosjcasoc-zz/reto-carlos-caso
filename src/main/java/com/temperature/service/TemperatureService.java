package com.temperature.service;

import com.temperature.api.add.TemperatureRequest;
import com.temperature.api.add.TemperatureResponse;
import com.temperature.api.list.TemperatureListResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TemperatureService {
    Mono<TemperatureResponse> save(TemperatureRequest temperature);
    Mono<TemperatureListResponse> getTemperatureByDate(String date, String conversion);
    Flux<TemperatureListResponse> getTemperatureByHour(String date, String conversion);
}
