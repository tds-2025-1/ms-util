package br.edu.ifrs.riogrande.tads.tds.util.service;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.edu.ifrs.riogrande.tads.tds.util.entity.CepResponse;

@Service
public class CepService {

    private final RestTemplate restTemplate;

    public CepService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CepResponse buscarDadosCep(String cep) {
        if (!cep.matches("\\d{8}")) {
            throw new IllegalArgumentException("CEP inválido: " + cep);            
        }
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        try {
        return restTemplate.getForEntity(url, CepResponse.class).getBody();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar dados do CEP: " + cep, e);
        }
        
        
    }
}