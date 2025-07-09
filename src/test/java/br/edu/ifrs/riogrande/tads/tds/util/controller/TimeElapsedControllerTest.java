package br.edu.ifrs.riogrande.tads.tds.util.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ifrs.riogrande.tads.tds.util.dto.TimeElapsedRequest;
import br.edu.ifrs.riogrande.tads.tds.util.service.TimeElapsedCalculatorService;

import java.time.LocalTime;

@SpringBootTest
@AutoConfigureMockMvc
public class TimeElapsedControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TimeElapsedCalculatorService timeElapsedCalculatorService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Deve retornar tempo decorrido entre duas horas - V1")
    void testCalculateElapsedTimeV1() throws Exception {
        // Given
        LocalTime startTime = LocalTime.of(8, 30);
        LocalTime endTime = LocalTime.of(10, 45);
        String expectedElapsedTime = "02:15:00";
        
        TimeElapsedRequest request = new TimeElapsedRequest();
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        
        when(timeElapsedCalculatorService.calculateTimeElapsed(startTime, endTime))
            .thenReturn(expectedElapsedTime);

        mvc.perform(post("/api/time/elapsed")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.elapsedTime").value(expectedElapsedTime));
    }

    @Test
    @DisplayName("Deve retornar tempo decorrido entre duas horas - V2")
    void testCalculateElapsedTimeV2() throws Exception {
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 30);
        String expectedElapsedTime = "08:30:00";
        
        TimeElapsedRequest request = new TimeElapsedRequest();
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        
        when(timeElapsedCalculatorService.calculateTimeElapsed(startTime, endTime))
            .thenReturn(expectedElapsedTime);

        mvc.perform(post("/api/time/v2/elapsed")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("X-TADS-Version", "2"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.data.elapsedTime").value(expectedElapsedTime))
            .andExpect(jsonPath("$.data.version").value(2))
            .andExpect(jsonPath("$.data.timestamp").exists())
            .andExpect(jsonPath("$.data.vendor").value("TADS"));
    }

    @Test
    @DisplayName("Deve retornar null quando horário inicial é nulo - V1")
    void testNullStartTimeV1() throws Exception {
        TimeElapsedRequest request = new TimeElapsedRequest();
        request.setStartTime(null);
        request.setEndTime(LocalTime.of(10, 0));
        
        mvc.perform(post("/api/time/elapsed")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.elapsedTime").doesNotExist());
    }

    @Test
    @DisplayName("Deve retornar null quando horário final é nulo - V1")
    void testNullEndTimeV1() throws Exception {
        TimeElapsedRequest request = new TimeElapsedRequest();
        request.setStartTime(LocalTime.of(8, 0));
        request.setEndTime(null);
        
        mvc.perform(post("/api/time/elapsed")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.elapsedTime").doesNotExist());
    }

    @Test
    @DisplayName("Deve retornar null quando horário inicial é nulo - V2")
    void testNullStartTimeV2() throws Exception {
        TimeElapsedRequest request = new TimeElapsedRequest();
        request.setStartTime(null);
        request.setEndTime(LocalTime.of(10, 0));
        
        mvc.perform(post("/api/time/v2/elapsed")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("X-TADS-Version", "2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.elapsedTime").doesNotExist());
    }

    @Test
    @DisplayName("Deve retornar null quando horário final é nulo - V2")
    void testNullEndTimeV2() throws Exception {
        TimeElapsedRequest request = new TimeElapsedRequest();
        request.setStartTime(LocalTime.of(8, 0));
        request.setEndTime(null);
        
        mvc.perform(post("/api/time/v2/elapsed")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("X-TADS-Version", "2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.elapsedTime").doesNotExist());
    }

    @Test
    @DisplayName("Deve retornar erro quando formato de hora é inválido")
    void testInvalidTimeFormat() throws Exception {
        String invalidJson = "{\"startTime\":\"25:00\",\"endTime\":\"10:00\"}";

        mvc.perform(post("/api/time/elapsed")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Deve calcular tempo decorrido para períodos que cruzam meia-noite")
    void testTimeElapsedAcrossMidnight() throws Exception {
        LocalTime startTime = LocalTime.of(23, 0);
        LocalTime endTime = LocalTime.of(1, 30);
        String expectedElapsedTime = "02:30:00";
        
        TimeElapsedRequest request = new TimeElapsedRequest();
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        
        when(timeElapsedCalculatorService.calculateTimeElapsed(startTime, endTime))
            .thenReturn(expectedElapsedTime);

        mvc.perform(post("/api/time/elapsed")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.elapsedTime").value(expectedElapsedTime));
    }

    @Test
    @DisplayName("Deve retornar zero quando horários são iguais")
    void testSameStartAndEndTime() throws Exception {
        LocalTime time = LocalTime.of(10, 0);
        String expectedElapsedTime = "00:00:00";
        
        TimeElapsedRequest request = new TimeElapsedRequest();
        request.setStartTime(time);
        request.setEndTime(time);
        
        when(timeElapsedCalculatorService.calculateTimeElapsed(time, time))
            .thenReturn(expectedElapsedTime);

        mvc.perform(post("/api/time/elapsed")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.elapsedTime").value(expectedElapsedTime));
    }
}