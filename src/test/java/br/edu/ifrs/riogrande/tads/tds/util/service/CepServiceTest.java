package br.edu.ifrs.riogrande.tads.tds.util.service;

import br.edu.ifrs.riogrande.tads.tds.util.entity.CepResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CepServiceTest {

    private RestTemplate restTemplate;
    private CepService cepService;

    @BeforeEach
    void setup() {
        restTemplate = Mockito.mock(RestTemplate.class);
        cepService = new CepService(restTemplate);
    }

    @Test
    void deveBuscarDadosCepValido() {
        String cep = "96010200";
        CepResponse mockResponse = new CepResponse();
        mockResponse.setCep(cep);
        mockResponse.setLogradouro("Rua General Osório");

        when(restTemplate.getForEntity(
                eq("https://viacep.com.br/ws/" + cep + "/json/"),
                eq(CepResponse.class))
        ).thenReturn(ResponseEntity.ok(mockResponse));

        CepResponse resultado = cepService.buscarDadosCep(cep);

        assertNotNull(resultado);
        assertEquals(cep, resultado.getCep());
        assertEquals("Rua General Osório", resultado.getLogradouro());
    }

    @Test
    void deveLancarExcecaoParaCepInvalido() {
        String cepInvalido = "123";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cepService.buscarDadosCep(cepInvalido)
        );

        assertTrue(exception.getMessage().contains("CEP inválido"));
    }

    @Test
    void deveLancarExcecaoParaErroNaRequisicao() {
        String cep = "96010200";

        when(restTemplate.getForEntity(
                eq("https://viacep.com.br/ws/" + cep + "/json/"),
                eq(CepResponse.class))
        ).thenThrow(new RuntimeException("Falha na conexão"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> cepService.buscarDadosCep(cep)
        );

        assertTrue(exception.getMessage().contains("Erro ao buscar dados do CEP"));
    }
}

