package br.edu.ifrs.riogrande.tads.tds.util.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DateCalculatorService Tests")
class DateCalculatorServiceTest {

    private DateCalculatorService dateCalculatorService;

    @BeforeEach
    void setUp() {
        dateCalculatorService = new DateCalculatorService();
    }

    @Test
    @DisplayName("Deve calcular 1 semana para diferença de 7 dias")
    void shouldCalculateOneWeekForSevenDays() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 8);
        
        long result = dateCalculatorService.calculateWeeksBetween(startDate, endDate);
        
        assertEquals(1, result, "Diferença de 7 dias deve resultar em 1 semana");
    }

    @Test
    @DisplayName("Deve calcular 4 semanas para diferença de 28 dias")
    void shouldCalculateFourWeeksForTwentyEightDays() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 29);
        
        long result = dateCalculatorService.calculateWeeksBetween(startDate, endDate);
        
        assertEquals(4, result);
    }

    @Test
    @DisplayName("Deve calcular 1 semana para 10 dias (divisão inteira)")
    void shouldCalculateOneWeekForTenDays() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 11);
        
        long result = dateCalculatorService.calculateWeeksBetween(startDate, endDate);
        
        assertEquals(1, result, "10 dias dividido por 7 = 1 semana (divisão inteira)");
    }

    @Test
    @DisplayName("Deve retornar 0 semanas para a mesma data")
    void shouldReturnZeroForSameDate() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        
        long result = dateCalculatorService.calculateWeeksBetween(date, date);
        
        assertEquals(0, result);
    }

    @Test
    @DisplayName("Deve retornar 0 semanas para diferença menor que 7 dias")
    void shouldReturnZeroForLessThanSevenDays() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 6);
        
        long result = dateCalculatorService.calculateWeeksBetween(startDate, endDate);
        
        assertEquals(0, result);
    }

    @Test
    @DisplayName("Deve calcular semanas corretamente entre anos diferentes")
    void shouldCalculateWeeksBetweenDifferentYears() {
        LocalDate startDate = LocalDate.of(2023, 12, 25);
        LocalDate endDate = LocalDate.of(2024, 1, 8);
        
        long result = dateCalculatorService.calculateWeeksBetween(startDate, endDate);
        
        assertEquals(2, result); 
    }
}