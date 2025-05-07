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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import br.edu.ifrs.riogrande.tads.tds.util.service.SenhaService;

@SpringBootTest
@AutoConfigureMockMvc
public class ForcaSenhaControllerTest {

    @MockitoBean
    SenhaService senhaService;

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Testa forcaSenha retorna 200, texto com a forca e a nota em texto plano")
    void testaForcaSenhaDeveRetornar200Texto() throws Exception {
        Mockito.when(senhaService.forcaSenha("aaa")).thenReturn("MUITO_FRACA : Nota 0");

        mvc.perform(get("/forca-senha/aaa"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("MUITO_FRACA : Nota 0"))
                .andExpect(content().contentType("text/plain;charset=UTF-8"));
    }
}