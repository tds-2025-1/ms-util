package br.edu.ifrs.riogrande.tads.tds.util.service;

import br.edu.ifrs.riogrande.tads.tds.util.dto.SenhaConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeradorSenhaServiceTest {

    private GeradorSenhaService geradorSenhaService;

    @BeforeEach
    void setUp() {
        geradorSenhaService = new GeradorSenhaService();
    }

    @Test
    @DisplayName("Should generate a password with only lowercase letters")
    void testGerarSenha_OnlyLowercase() {
        SenhaConfig config = new SenhaConfig();
        config.setTamanho(10);
        config.setMinusculas(true);
        config.setMaiusculas(false);
        config.setNumeros(false);
        config.setSimbolos(false);

        String senha = geradorSenhaService.gerarSenha(config);

        assertNotNull(senha);
        assertEquals(10, senha.length());
        assertTrue(senha.matches("[a-z]+"));
    }

    @Test
    @DisplayName("Should generate a password with only uppercase letters")
    void testGerarSenha_OnlyUppercase() {
        SenhaConfig config = new SenhaConfig();
        config.setTamanho(12);
        config.setMinusculas(false);
        config.setMaiusculas(true);
        config.setNumeros(false);
        config.setSimbolos(false);

        String senha = geradorSenhaService.gerarSenha(config);

        assertNotNull(senha);
        assertEquals(12, senha.length());
        assertTrue(senha.matches("[A-Z]+"));
    }

    @Test
    @DisplayName("Should generate a password with only numbers")
    void testGerarSenha_OnlyNumbers() {
        SenhaConfig config = new SenhaConfig();
        config.setTamanho(8);
        config.setMinusculas(false);
        config.setMaiusculas(false);
        config.setNumeros(true);
        config.setSimbolos(false);

        String senha = geradorSenhaService.gerarSenha(config);

        assertNotNull(senha);
        assertEquals(8, senha.length());
        assertTrue(senha.matches("[0-9]+"));
    }

    @Test
    @DisplayName("Should generate a password with only symbols")
    void testGerarSenha_OnlySymbols() {
        SenhaConfig config = new SenhaConfig();
        config.setTamanho(7);
        config.setMinusculas(false);
        config.setMaiusculas(false);
        config.setNumeros(false);
        config.setSimbolos(true);

        String senha = geradorSenhaService.gerarSenha(config);

        assertNotNull(senha);
        assertEquals(7, senha.length());
        assertFalse(senha.matches("[a-zA-Z0-9]+"));
        assertTrue(senha.matches("[!@#$%&*()-_=+\\[\\]{}<>?/]+"));
    }

    @Test
    @DisplayName("Should generate a password with mixed character types")
    void testGerarSenha_MixedTypes() {
        SenhaConfig config = new SenhaConfig();
        config.setTamanho(100);
        config.setMinusculas(true);
        config.setMaiusculas(true);
        config.setNumeros(true);
        config.setSimbolos(true);

        String senha = geradorSenhaService.gerarSenha(config);

        assertNotNull(senha);
        assertEquals(100, senha.length());
        assertTrue(senha.matches(".*[a-z].*"), "Should contain lowercase letters");
        assertTrue(senha.matches(".*[A-Z].*"), "Should contain uppercase letters");
        assertTrue(senha.matches(".*[0-9].*"), "Should contain numbers");
        assertTrue(senha.matches(".*[!@#$%&*()\\-_=+\\[\\]{}<>?/].*"), "Should contain symbols");
    }

    @Test
    @DisplayName("Should return error message when no character type is selected")
    void testGerarSenha_NoCharacterTypeSelected() {
        SenhaConfig config = new SenhaConfig();
        config.setTamanho(10);
        config.setMinusculas(false);
        config.setMaiusculas(false);
        config.setNumeros(false);
        config.setSimbolos(false);

        String senha = geradorSenhaService.gerarSenha(config);

        assertNotNull(senha);
        assertEquals("Erro: selecione ao menos um tipo de caractere.", senha);
    }

    @Test
    @DisplayName("Should handle a password length of 1")
    void testGerarSenha_LengthOne() {
        SenhaConfig config = new SenhaConfig();
        config.setTamanho(1);
        config.setMinusculas(true);
        config.setMaiusculas(false); 
        config.setNumeros(true);
        config.setSimbolos(false); 
        String senha = geradorSenhaService.gerarSenha(config);

        assertNotNull(senha);
        assertEquals(1, senha.length());
        assertTrue(senha.matches("[a-z0-9]"));
    }

    @Test
    @DisplayName("Should generate different passwords on multiple calls with same config (randomness)")
    void testGerarSenha_Randomness() {
        SenhaConfig config = new SenhaConfig();
        config.setTamanho(20);
        config.setMinusculas(true);
        config.setMaiusculas(true);
        config.setNumeros(true);
        config.setSimbolos(true);

        String senha1 = geradorSenhaService.gerarSenha(config);
        String senha2 = geradorSenhaService.gerarSenha(config);
        String senha3 = geradorSenhaService.gerarSenha(config);

        assertNotEquals(senha1, senha2, "Passwords should generally be different");
        assertNotEquals(senha1, senha3, "Passwords should generally be different");
        assertNotEquals(senha2, senha3, "Passwords should generally be different");
    }
}