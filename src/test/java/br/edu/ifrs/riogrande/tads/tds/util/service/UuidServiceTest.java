package br.edu.ifrs.riogrande.tads.tds.util.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UuidServiceTest {

    UuidService uuidService;

    @BeforeEach 
    void setup() {
        uuidService = new UuidService();// Setup method to initialize any required resources before each test
    }

    @Test
    @DisplayName("Testa um número válido de UUIDs")
    void testGenerateValidNumberOfUuids() {
        List<String> uuids = uuidService.generate(5);
        assertEquals(5, uuids.size());
    }

    @Test
    @DisplayName("Testa um número inválido de UUIDs (menos que 1)")
    void testGenerateWithZeroUuids() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            uuidService.generate(0);
        });
        assertEquals("Number of passwords must be at least 1", exception.getMessage());
    }

    @Test
    @DisplayName("Testa um número inválido de UUIDs (mais que 50)")
    void testGenerateWithTooManyUuids() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            uuidService.generate(51);
        });
        assertEquals("Number of passwords must be at most 50", exception.getMessage());
    }
}