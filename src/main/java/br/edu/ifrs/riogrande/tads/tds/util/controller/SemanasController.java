package br.edu.ifrs.riogrande.tads.tds.util.controller;
import br.edu.ifrs.riogrande.tads.tds.util.service.DateCalculatorService;
import org.springframework.web.bind.annotation.*;
import br.edu.ifrs.riogrande.tads.tds.util.controller.dto.SemanaResponseDTO;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/semanas")
public class SemanasController {

    private final DateCalculatorService dateCalculatorService;

    public SemanasController(DateCalculatorService dateCalculatorService) {
        this.dateCalculatorService = dateCalculatorService;
    }

    @GetMapping
    public long calcularSemanas(
            @RequestParam String dataInicial,
            @RequestParam String dataFinal) {

        LocalDate inicio = LocalDate.parse(dataInicial);
        LocalDate fim = LocalDate.parse(dataFinal);

        return dateCalculatorService.calculateWeeksBetween(inicio, fim);
    }

    @GetMapping("/v1")
    public SemanaResponseDTO<Long> calcularSemanasV1(
            @RequestParam String dataInicial,
            @RequestParam String dataFinal) {

        LocalDate inicio = LocalDate.parse(dataInicial);
        LocalDate fim = LocalDate.parse(dataFinal);
        long semanas = dateCalculatorService.calculateWeeksBetween(inicio, fim);

        return new SemanaResponseDTO<Long>(semanas, "Cálculo realizado com sucesso", true);
    }
}


