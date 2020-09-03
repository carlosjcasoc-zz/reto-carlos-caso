package com.temperature.api.add;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class TemperatureRequest {

    @ApiModelProperty(notes = "value of temperature")
    @NotNull
    private Double temperatureValue;

    @ApiModelProperty(notes = "creation date of temperature", example = "2020-09-02T06:09:11.808+00:00")
    @NotNull
    private String creationDate;


}
