package br.edu.ifrs.riogrande.tads.tds.util.service;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class BusinessDayCalculatorServiceTest {
    
    private BusinessDayCalculatorService calculatorService;
    
    @BeforeEach
    void setUp() {
        calculatorService = new BusinessDayCalculatorService();
    }
    
    @Test
    @DisplayName("Deve calcular a data após adicionar dias úteis a partir de uma segunda-feira")
    void deveCalcularDataAposAdicionarDiasUteisAPartirDeSegundaFeira() {
        
        LocalDate startDate = LocalDate.of(2023, 1, 2); 
        int businessDays = 3;
        
        
        LocalDate resultDate = calculatorService.calculateDateAfterBusinessDays(startDate, businessDays);
        
        
        LocalDate expectedDate = LocalDate.of(2023, 1, 5); 
        assertEquals(expectedDate, resultDate, "Deve adicionar 3 dias úteis a partir de segunda-feira");
    }
    
    @Test
    @DisplayName("Deve calcular a data após adicionar dias úteis a partir de uma sexta-feira (passando pelo fim de semana)")
    void deveCalcularDataAposAdicionarDiasUteisAPartirDeSextaFeira() {
        
        LocalDate startDate = LocalDate.of(2023, 1, 6); 
        int businessDays = 3;
        
        
        LocalDate resultDate = calculatorService.calculateDateAfterBusinessDays(startDate, businessDays);
        
        
        LocalDate expectedDate = LocalDate.of(2023, 1, 11); 
        assertEquals(expectedDate, resultDate, "Deve adicionar 3 dias úteis a partir de sexta-feira, pulando o fim de semana");
    }
    
    @Test
    @DisplayName("Deve calcular a data após adicionar dias úteis a partir de um sábado")
    void deveCalcularDataAposAdicionarDiasUteisAPartirDeSabado() {
        
        LocalDate startDate = LocalDate.of(2023, 1, 7); 
        int businessDays = 2;
        
        
        LocalDate resultDate = calculatorService.calculateDateAfterBusinessDays(startDate, businessDays);
        
        
        LocalDate expectedDate = LocalDate.of(2023, 1, 11); 
        assertEquals(expectedDate, resultDate, "Deve adicionar 2 dias úteis a partir de sábado, começando a contar de segunda-feira");
    }
    
    @Test
    @DisplayName("Deve calcular a data após adicionar dias úteis a partir de um domingo")
    void deveCalcularDataAposAdicionarDiasUteisAPartirDeDomingo() {
        
        LocalDate startDate = LocalDate.of(2023, 1, 8); 
        int businessDays = 2;
        
        
        LocalDate resultDate = calculatorService.calculateDateAfterBusinessDays(startDate, businessDays);
        
        
        LocalDate expectedDate = LocalDate.of(2023, 1, 11); 
        assertEquals(expectedDate, resultDate, "Deve adicionar 2 dias úteis a partir de domingo, começando a contar de segunda-feira");
    }
    
    @Test
    @DisplayName("Deve manter a mesma data quando adicionar zero dias úteis")
    void deveManterMesmaDataQuandoAdicionarZeroDiasUteis() {
        
        LocalDate startDate = LocalDate.of(2023, 1, 4); 
        int businessDays = 0;
        
        
        LocalDate resultDate = calculatorService.calculateDateAfterBusinessDays(startDate, businessDays);
        
        
        assertEquals(startDate, resultDate, "A data resultante deve ser igual à data inicial ao adicionar zero dias úteis");
    }
    
    @Test
    @DisplayName("Deve calcular corretamente para um período longo (20 dias úteis)")
    void deveCalcularCorretamenteParaPeriodoLongo() {
        
        LocalDate startDate = LocalDate.of(2023, 1, 2); 
        int businessDays = 20;
        
        
        LocalDate resultDate = calculatorService.calculateDateAfterBusinessDays(startDate, businessDays);
        
        
        LocalDate expectedDate = LocalDate.of(2023, 1, 30); 
        assertEquals(expectedDate, resultDate, "Deve adicionar 20 dias úteis corretamente pulando fins de semana");
    }
    
    @ParameterizedTest
    @CsvSource({
        "2023-01-02, 5, 2023-01-09", 
        "2023-01-03, 5, 2023-01-10", 
        "2023-01-06, 5, 2023-01-13", 
        "2023-01-07, 5, 2023-01-16"  
    })
    @DisplayName("Testes parametrizados para diferentes datas iniciais")
    void testesParametrizadosParaDiferentesDatasIniciais(String startDateStr, int businessDays, String expectedDateStr) {
        
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate expectedDate = LocalDate.parse(expectedDateStr);
        
        
        LocalDate resultDate = calculatorService.calculateDateAfterBusinessDays(startDate, businessDays);
        
        
        assertEquals(expectedDate, resultDate, 
                String.format("Adicionando %d dias úteis a %s deve resultar em %s", businessDays, startDateStr, expectedDateStr));
    }
}
