package com.temperature.api.list;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TemperatureListResponse {

    @ApiModelProperty(notes = "date of temperature", example = "2020-09-03")
    private String date;
    @ApiModelProperty(notes = "range of hour of temperature", example = "10-11")
    private String time;
    @ApiModelProperty(notes = "min temperature", example = "2.5 ºC")
    private String min;
    @ApiModelProperty(notes = "max temperature", example = "2.5 ºC")
    private String max;
    @ApiModelProperty(notes = "average temperature", example = "2.5 ºC")
    private String average;
}
