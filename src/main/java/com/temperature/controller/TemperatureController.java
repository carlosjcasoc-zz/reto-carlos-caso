package com.temperature.controller;

import com.temperature.api.add.TemperatureRequest;
import com.temperature.api.add.TemperatureResponse;
import com.temperature.api.list.TemperatureListResponse;
import com.temperature.service.TemperatureService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("temperatures")
public class TemperatureController {

    @Autowired
    private TemperatureService temperatureService;

    @ApiOperation(value = "add new temperature")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list", response = TemperatureResponse.class),
    }
    )
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TemperatureResponse> save(@Validated @RequestBody TemperatureRequest temperatureRequest) {
        return temperatureService.save(temperatureRequest);
    }

    @ApiOperation(value = "List temperatures filter by day")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list", response = TemperatureListResponse.class),
    }
    )
    @RequestMapping(value = "/list-by-date", method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TemperatureListResponse> getTemperatureByDate(@RequestParam("date") String date, @RequestParam(name = "conversion", defaultValue = "C") String conversion) {
        return temperatureService.getTemperatureByDate(date, conversion);
    }

    @ApiOperation(value = "List temperatures filter by day and group by hour")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list", response = TemperatureListResponse.class),
    }
    )
    @RequestMapping(value = "/list-by-hour", method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<TemperatureListResponse> getTemperatureByHour(@RequestParam("date") String date, @RequestParam(name = "conversion", defaultValue = "C") String conversion) {
        return temperatureService.getTemperatureByHour(date, conversion);
    }


    /**
     * Sirvio para verfiicar mis create en la BD
    @RequestMapping(value = "/list", method= RequestMethod.GET)
    public Flux<Temperature> findAll() {
        return temperatureService.findAll();
    }
    **/
}
