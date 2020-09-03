package com.temperature.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temperature.api.add.TemperatureRequest;
import com.temperature.repository.TemperatureRepository;
import com.temperature.service.TemperatureService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TemperatureControllerTest.class)
public class TemperatureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TemperatureService temperatureService;

    @MockBean
    private TemperatureRepository temperatureRepository;

    @Test
    void whenCreateValidUrlAndMethodAndContentType_thenReturns200() throws Exception {

        TemperatureRequest temperatureRequest = new TemperatureRequest();
        temperatureRequest.setCreationDate("2020-09-02 09:37:15");
        temperatureRequest.setTemperatureValue(13.9);

        mockMvc.perform(post("/temperatures/add")
                .content(objectMapper.writeValueAsString(temperatureRequest))
                .contentType("application/json"))
                .andExpect(status().isOk());

    }

    @Test
    void whenCreateValidInput_thenReturns200() throws Exception {

        TemperatureRequest temperatureRequest = new TemperatureRequest();
        temperatureRequest.setCreationDate("2020-09-02 09:37:15");
        temperatureRequest.setTemperatureValue(13.9);
        mockMvc.perform(post("/temperatures/add", 42L)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(temperatureRequest)))
                .andExpect(status().isOk());

    }

    @Test
    void whenCreateIValidInput_thenReturns400() throws Exception {

        TemperatureRequest temperatureRequest = new TemperatureRequest();
        temperatureRequest.setTemperatureValue(13.9);
        mockMvc.perform(post("/temperatures/add", 42L)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(temperatureRequest)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void whenGetByDateValidInput_thenReturns200() throws Exception {

        String date = "2020-09-02";
        String conversion = "F";

        mockMvc.perform(get("/temperatures/list-by-date")
                .param("date", date)
                .param("conversion", conversion)
        )
                .andExpect(status().isOk());
    }

    @Test
    void whenGetByDateIValidInput_thenReturns400() throws Exception {

        String conversion = "F";

        mockMvc.perform(get("/temperatures/list-by-date")
                .param("conversion", conversion)
        )
                .andExpect(status().is4xxClientError());
    }

    @Test
    void whenGetByDateAndGroupByHourValidInput_thenReturns200() throws Exception {

        String date = "2020-09-02";
        String conversion = "F";

        mockMvc.perform(get("/temperatures/list-by-hour")
                .param("date", date)
                .param("conversion", conversion)
        )
                .andExpect(status().isOk());
    }

    @Test
    void whenGetByDateAndGroupByHourIValidInput_thenReturns400() throws Exception {

        String conversion = "F";

        mockMvc.perform(get("/temperatures/list-by-hour")
                .param("conversion", conversion)
        )
                .andExpect(status().is4xxClientError());
    }

}
