package br.edu.ifrs.riogrande.tads.tds.util.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print; // Importante para depuração!

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ifrs.riogrande.tads.tds.util.dto.SenhaConfig;
import br.edu.ifrs.riogrande.tads.tds.util.service.GeradorSenhaService;

@WebMvcTest(GeradorSenhaController.class)
class GeradorSenhaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GeradorSenhaService geradorSenhaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return generated password for /api/gerar-senha")
    void testGerarSenhaEndpoint() throws Exception {
        String expectedPassword = "generatedPassword123";
        when(geradorSenhaService.gerarSenha(any(SenhaConfig.class)))
                .thenReturn(expectedPassword);

        SenhaConfig senhaConfig = new SenhaConfig();

        mockMvc.perform(post("/api/gerar-senha")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(senhaConfig)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedPassword));
    }

    @Test
    @DisplayName("Should return ApiResponse with generated password for /api/v1/gerar-senha")
    void testGerarSenhaV1Endpoint() throws Exception {
        String expectedPassword = "generatedPasswordV1";
        when(geradorSenhaService.gerarSenha(any(SenhaConfig.class)))
                .thenReturn(expectedPassword);

        SenhaConfig senhaConfig = new SenhaConfig();

        mockMvc.perform(post("/api/v1/gerar-senha")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(senhaConfig)))
                .andDo(print()) 
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.result").value(expectedPassword));
    }
}