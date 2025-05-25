package br.edu.ifrs.riogrande.tads.tds.util.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

public class TimeElapsedCalculatorServiceTest {

    private TimeElapsedCalculatorService service;

    @BeforeEach
    void setUp() {
        service = new TimeElapsedCalculatorService();
    }

    @Test
    @DisplayName("Deve calcular tempo decorrido entre duas horas simples")
    void testCalculateElapsedTimeSimple() {
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(10, 30);

        String elapsedTime = service.calculateTimeElapsed(startTime, endTime);

        assertEquals("02:30:00", elapsedTime);
    }

    @Test
    @DisplayName("Deve calcular tempo decorrido quando minutos precisam ser emprestados")
    void testCalculateElapsedTimeWithMinuteBorrowing() {
        LocalTime startTime = LocalTime.of(9, 45);
        LocalTime endTime = LocalTime.of(11, 15);

        String elapsedTime = service.calculateTimeElapsed(startTime, endTime);

        assertEquals("01:30:00", elapsedTime);
    }

    @Test
    @DisplayName("Deve calcular tempo decorrido para dia inteiro")
    void testCalculateElapsedTimeFullDay() {
        LocalTime startTime = LocalTime.of(0, 0);
        LocalTime endTime = LocalTime.of(23, 59);

        String elapsedTime = service.calculateTimeElapsed(startTime, endTime);

        assertEquals("23:59:00", elapsedTime);
    }

    @Test
    @DisplayName("Deve retornar zero quando horários são iguais")
    void testCalculateElapsedTimeSameTime() {
        LocalTime time = LocalTime.of(14, 30);

        String elapsedTime = service.calculateTimeElapsed(time, time);

        assertEquals("00:00:00", elapsedTime);
    }

    @Test
    @DisplayName("Deve calcular tempo decorrido com segundos")
    void testCalculateElapsedTimeWithSeconds() {
        LocalTime startTime = LocalTime.of(10, 15, 30);
        LocalTime endTime = LocalTime.of(12, 45, 45);

        String elapsedTime = service.calculateTimeElapsed(startTime, endTime);

        assertEquals("02:30:15", elapsedTime);
    }

    @Test
    @DisplayName("Deve calcular tempo decorrido quando hora final é antes da inicial (passa pela meia-noite)")
    void testCalculateElapsedTimeAcrossMidnight() {
        LocalTime startTime = LocalTime.of(23, 0);
        LocalTime endTime = LocalTime.of(1, 30);

        String elapsedTime = service.calculateTimeElapsed(startTime, endTime);

        assertEquals("02:30:00", elapsedTime);
    }

    @Test
    @DisplayName("Deve calcular tempo decorrido com minutos apenas")
    void testCalculateElapsedTimeMinutesOnly() {
        LocalTime startTime = LocalTime.of(10, 15);
        LocalTime endTime = LocalTime.of(10, 45);

        String elapsedTime = service.calculateTimeElapsed(startTime, endTime);

        assertEquals("00:30:00", elapsedTime);
    }

    @Test
    @DisplayName("Deve calcular tempo decorrido de várias horas")
    void testCalculateElapsedTimeManyHours() {
        LocalTime startTime = LocalTime.of(6, 30);
        LocalTime endTime = LocalTime.of(18, 45);

        String elapsedTime = service.calculateTimeElapsed(startTime, endTime);

        assertEquals("12:15:00", elapsedTime);
    }

    @Test
    @DisplayName("Deve calcular tempo decorrido próximo à meia-noite")
    void testCalculateElapsedTimeNearMidnight() {
        LocalTime startTime = LocalTime.of(23, 59, 59);
        LocalTime endTime = LocalTime.of(0, 0, 1);

        String elapsedTime = service.calculateTimeElapsed(startTime, endTime);

        assertEquals("00:00:02", elapsedTime);
    }

    @Test
    @DisplayName("Deve calcular tempo decorrido de 24 horas")
    void testCalculateElapsedTime24Hours() {
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(10, 0); // Mesmo horário = 0 horas, não 24

        String elapsedTime = service.calculateTimeElapsed(startTime, endTime);

        assertEquals("00:00:00", elapsedTime);
    }

    @Test
    @DisplayName("Deve calcular tempo decorrido com nanossegundos")
    void testCalculateElapsedTimeWithNanos() {
        LocalTime startTime = LocalTime.of(10, 0, 0, 500_000_000); // 10:00:00.5
        LocalTime endTime = LocalTime.of(10, 0, 1, 750_000_000);   // 10:00:01.75

        String elapsedTime = service.calculateTimeElapsed(startTime, endTime);

        assertEquals("00:00:01", elapsedTime); // Ignora nanossegundos na formatação
    }

    @Test
    @DisplayName("Deve lançar exceção para horário nulo")
    void testNullTimes() {
        assertThrows(NullPointerException.class, () -> {
            service.calculateTimeElapsed(null, LocalTime.of(10, 0));
        });

        assertThrows(NullPointerException.class, () -> {
            service.calculateTimeElapsed(LocalTime.of(10, 0), null);
        });
    }
}