package br.edu.ifrs.riogrande.tads.tds.util.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.edu.ifrs.riogrande.tads.tds.util.service.UuidService;

@WebMvcTest(UuidController.class)
class UuidControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockitoBean

    private UuidService uuidService;

    @Test
    @DisplayName("Retorna status code 200, com lista de somente um UUIDs")
    void testGenerateUuidsSuccess() throws Exception {
        Mockito.when(uuidService.generate(1)).thenReturn(List.of("6cc9ae8c-accb-499c-afdd-26a44c578bef"));

        mockMvc.perform(get("/uuid"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("6cc9ae8c-accb-499c-afdd-26a44c578bef"))
                .andExpect(content().contentType("application/json"))
                .andReturn();
    }

    @Test
    @DisplayName("Retorna status code 400, com a mensagem de erro: 'Number of passwords must be at least 1'")
    void testGenerateUuidsBadRequest() throws Exception {
        when(uuidService.generate(-1)).thenThrow(new IllegalArgumentException("Number of passwords must be at least 1"));

        mockMvc.perform(get("/uuid")
                .param("no_passwords", "-1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0]").value("Number of passwords must be at least 1"))
                .andExpect(content().contentType("application/json"))
                .andReturn();
    }
}