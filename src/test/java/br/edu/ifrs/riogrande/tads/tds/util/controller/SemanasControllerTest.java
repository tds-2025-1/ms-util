package br.edu.ifrs.riogrande.tads.tds.util.controller;

import br.edu.ifrs.riogrande.tads.tds.util.service.DateCalculatorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SemanasController.class)
@DisplayName("SemanasController Tests")
@SuppressWarnings("removal")
class SemanasControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private DateCalculatorService dateCalculatorService;

        @Test
        @DisplayName("Deve retornar número de semanas quando parâmetros são válidos")
        void shouldReturnWeeksWhenParametersAreValid() throws Exception {
                when(dateCalculatorService.calculateWeeksBetween(any(LocalDate.class), any(LocalDate.class)))
                                .thenReturn(2L);

                mockMvc.perform(get("/api/semanas")
                                .param("dataInicial", "2024-01-01")
                                .param("dataFinal", "2024-01-15"))
                                .andExpect(status().isOk())
                                .andExpect(content().string("2"));
        }

        @Test
        @DisplayName("Deve retornar 0 semanas para datas iguais")
        void shouldReturnZeroForSameDates() throws Exception {
                when(dateCalculatorService.calculateWeeksBetween(any(LocalDate.class), any(LocalDate.class)))
                                .thenReturn(0L);

                mockMvc.perform(get("/api/semanas")
                                .param("dataInicial", "2024-01-01")
                                .param("dataFinal", "2024-01-01"))
                                .andExpect(status().isOk())
                                .andExpect(content().string("0"));
        }

        @Test
        @DisplayName("Deve retornar erro 400 quando parâmetros estão ausentes")
        void shouldReturnBadRequestWhenParametersAreMissing() throws Exception {
                mockMvc.perform(get("/api/semanas")
                                .param("dataInicial", "2024-01-01"))
                                .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Deve retornar DTO com sucesso na versão v1")
        void shouldReturnDTOOnV1Endpoint() throws Exception {
                when(dateCalculatorService.calculateWeeksBetween(any(LocalDate.class), any(LocalDate.class)))
                                .thenReturn(2L);

                mockMvc.perform(get("/api/semanas/v1")
                                .param("dataInicial", "2024-01-01")
                                .param("dataFinal", "2024-01-15"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.data").value(2))
                                .andExpect(jsonPath("$.message").value("Cálculo realizado com sucesso"))
                                .andExpect(jsonPath("$.success").value(true));
        }
}