package br.edu.ifrs.riogrande.tads.tds.util.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import br.edu.ifrs.riogrande.tads.tds.util.service.ColorService;

public class ColorControllerTest {

    ColorController colorController = new ColorController(new ColorService());

        @Test
        public void testRgbToHsl_ValidInput() {
            String result = colorController.convertRgbToHsl("#FF0000");
            assertEquals("HSL(0, 100%, 50%)", result); // Vermelho 
        }
        
        @Test
        public void testRgbToHsl_AnotherColor() {
            String result = colorController.convertRgbToHsl("#00FF00");
            assertEquals("HSL(120, 100%, 50%)", result); // Verde 
        }
        

        @Test
        public void testRgbToHsl_InvalidInput() {
            String result = colorController.convertRgbToHsl("ZZZZZZ");
            assertEquals("Invalid format. Use #RRGGBB.", result);// Formato Válido
        }
        
        @Test
        public void testRgbToHsl_ShortInput() {
            String result = colorController.convertRgbToHsl("FFF");
            assertEquals("Invalid format. Use #RRGGBB.", result); // Conversão Válida
        }
    }
