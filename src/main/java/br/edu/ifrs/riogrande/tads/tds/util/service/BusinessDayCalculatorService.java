package br.edu.ifrs.riogrande.tads.tds.util.service;

import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
public class BusinessDayCalculatorService {
    
    /**
     * Calcula a data após adicionar uma quantidade específica de dias úteis.
     * Considera apenas dias de semana (segunda a sexta) como dias úteis.
     * Se a data inicial cair em um fim de semana, o cálculo começará a partir da próxima segunda-feira.
     * 
     * @param startDate Data inicial
     * @param businessDays Número de dias úteis a adicionar
     * @return Data resultante após adicionar os dias úteis
     */
    public LocalDate calculateDateAfterBusinessDays(LocalDate startDate, int businessDays) {
        // Se for 0 dias úteis, retorna a data inicial (mesmo se for fim de semana)
        if (businessDays == 0) {
            return startDate;
        }
        
        // Ajusta a data inicial se cair em um fim de semana (avança para segunda-feira)
        LocalDate result = startDate;
        if (isWeekend(result)) {
            result = adjustToNextMonday(result);
        }
        
        // Adiciona os dias úteis, pulando os fins de semana
        int addedDays = 0;
        while (addedDays < businessDays) {
            result = result.plusDays(1);
            if (!isWeekend(result)) {
                addedDays++;
            }
        }
        
        return result;
    }
    
    /**
     * Verifica se uma data cai em um fim de semana (sábado ou domingo).
     */
    private boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
    
    /**
     * Ajusta uma data para a próxima segunda-feira.
     * Se a data for sábado, adiciona 2 dias.
     * Se a data for domingo, adiciona 1 dia.
     */
    private LocalDate adjustToNextMonday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY) {
            return date.plusDays(2); // Sábado + 2 dias = Segunda-feira
        } else if (dayOfWeek == DayOfWeek.SUNDAY) {
            return date.plusDays(1); // Domingo + 1 dia = Segunda-feira
        }
        return date; // Já é um dia de semana
    }
}