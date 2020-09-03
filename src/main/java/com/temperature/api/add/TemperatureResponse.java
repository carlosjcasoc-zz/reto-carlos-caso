package com.temperature.api.add;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class TemperatureResponse {

    @ApiModelProperty(notes = "id of temperature", example = "2")
    private Long id;

    @ApiModelProperty(notes = "value of temperature", example = "123.4")
    private Double temperatureValue;

    @ApiModelProperty(notes = "creation date of temperature", example = "2020-09-02T10:37:15")
    private LocalDateTime creationDate;
}
