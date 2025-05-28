package br.edu.ifrs.riogrande.tads.tds.util.controller;

import br.edu.ifrs.riogrande.tads.tds.util.entity.CepResponse;
import br.edu.ifrs.riogrande.tads.tds.util.service.CepService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CepController.class)
class CepControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CepService cepService;

    @Test
    void testGetCepDatav1() throws Exception {
        CepResponse response = criarCepResponseFalso();

        when(cepService.buscarDadosCep("96202350")).thenReturn(response);

        mockMvc.perform(get("/v1/cep/96202350"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.cep").value("96202350"))
                .andExpect(jsonPath("$.logradouro").value("Rua General Osório"));
    }

    @Test
    void testGetCepDataTexto() throws Exception {
        CepResponse response = criarCepResponseFalso();

        when(cepService.buscarDadosCep("96202350")).thenReturn(response);

        mockMvc.perform(get("/cep/96202350"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("CEP: 96202350")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Cidade: Rio Grande")));
    }

    private CepResponse criarCepResponseFalso() {
        CepResponse response = new CepResponse();
        response.setCep("96202350");
        response.setLogradouro("Rua Mestre Jerônimo");
        response.setComplemento("");
        response.setBairro("Centro");
        response.setLocalidade("Rio Grande");
        response.setUf("RS");
        return response;
    }
}
