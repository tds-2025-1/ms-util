package br.edu.ifrs.riogrande.tads.tds.util.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.ifrs.riogrande.tads.tds.util.service.CpfService;

public class CpfServiceTest {

    CpfService cpfService;

    @BeforeEach // resetar o objeto para cada teste
    void init() {
        cpfService = new CpfService();
    }
    
    // hamcrest
    @Test
    void testaAVerdade() {
        assertEquals(2, 1 + 1);
    }

    @Test
    void testaCpfFormatado() {
        
        assertEquals("123.456.789-01", 
            cpfService.formatCpf(new int[]{1,2,3,4,5,6,7,8,9,0,1}));
    }
}
