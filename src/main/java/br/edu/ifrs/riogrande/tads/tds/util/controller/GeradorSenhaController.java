package br.edu.ifrs.riogrande.tads.tds.util.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifrs.riogrande.tads.tds.util.dto.SenhaConfig;
import br.edu.ifrs.riogrande.tads.tds.util.service.GeradorSenhaService;

@RestController
@RequestMapping("/api/gerar-senha")
public class GeradorSenhaController {

    private final GeradorSenhaService geradorSenhaService;

    public GeradorSenhaController(GeradorSenhaService geradorSenhaService) {
        this.geradorSenhaService = geradorSenhaService;
    }

    @PostMapping
    public String nomeValido(@RequestBody SenhaConfig dto) {
        return geradorSenhaService.gerarSenha(dto);
    }
}