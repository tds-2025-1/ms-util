package br.edu.ifrs.riogrande.tads.tds.util.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.edu.ifrs.riogrande.tads.tds.util.service.PingService;

@SpringBootTest
@AutoConfigureMockMvc
public class PingControllerTest {

    @MockitoBean
    PingService pingService;
    
    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Testa Ping retorna 200, texto pong em texto plano")
    void testaPingDeveRetornar200TextComTextoPong() throws Exception {
        Mockito.when(pingService.ping()).thenReturn("pong pong");

        mvc.perform(get("/ping"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("pong pong"))
            .andExpect(content().contentType("text/plain;charset=UTF-8"));
    }

}
