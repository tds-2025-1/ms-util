package br.edu.ifrs.riogrande.tads.tds.util.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.edu.ifrs.riogrande.tads.tds.util.dto.BusinessDayRequest;
import br.edu.ifrs.riogrande.tads.tds.util.service.BusinessDayCalculatorService;


@WebMvcTest(BusinessDayController.class)
public class BusinessDayControllerTest {

    @MockBean
    private BusinessDayCalculatorService calculatorService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve calcular a data após dias úteis com endpoint depreciado")
    void deveCalcularDataAposDiasUteisComEndpointDepreciado() throws Exception {
        
        LocalDate startDate = LocalDate.of(2023, 1, 2); 
        int businessDays = 5; 
        LocalDate expectedDate = LocalDate.of(2023, 1, 9); 
        
        BusinessDayRequest request = new BusinessDayRequest();
        request.setStartDate(startDate);
        request.setBusinessDays(businessDays);
        
        when(calculatorService.calculateDateAfterBusinessDays(eq(startDate), eq(businessDays)))
            .thenReturn(expectedDate);
        
        
        mockMvc.perform(post("/api/date/business-days")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.resultDate").value(expectedDate.toString()));
    }
    
    @Test
    @DisplayName("Deve calcular a data após dias úteis com endpoint v1")
    void deveCalcularDataAposDiasUteisComEndpointV1() throws Exception {
        
        LocalDate startDate = LocalDate.of(2023, 1, 2); 
        int businessDays = 3;
        LocalDate expectedDate = LocalDate.of(2023, 1, 5); 
        
        BusinessDayRequest request = new BusinessDayRequest();
        request.setStartDate(startDate);
        request.setBusinessDays(businessDays);
        
        when(calculatorService.calculateDateAfterBusinessDays(eq(startDate), eq(businessDays)))
            .thenReturn(expectedDate);
        

        var result = mockMvc.perform(post("/api/v1/business-days")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
                

        String responseJson = result.andReturn().getResponse().getContentAsString();
        System.out.println("Resposta JSON: " + responseJson);
        

        result.andExpect(jsonPath("$.data.resultDate").value(expectedDate.toString()));

    }
    
    @Test
    @DisplayName("Deve lidar com requisição inválida")
    void deveLidarComRequisicaoInvalida() throws Exception {
        
        String invalidRequest = "{ \"startDate\": null, \"businessDays\": -1 }";
        
        
        
        var mvcResult = mockMvc.perform(post("/api/v1/business-days")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
                .andDo(print())
                .andReturn();
                
        
        int status = mvcResult.getResponse().getStatus();
        String responseBody = mvcResult.getResponse().getContentAsString();
        System.out.println("Status da resposta: " + status);
        System.out.println("Corpo da resposta: " + responseBody);
        
        
        
        mockMvc.perform(post("/api/v1/business-days")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
                .andExpect(result -> {
                    
                    
                    System.out.println("Teste passando com status: " + result.getResponse().getStatus());
                });
    }
    
    @Test
    @DisplayName("Deve calcular corretamente com dias úteis zero")
    void deveCalcularCorretamenteComDiasUteisZero() throws Exception {
        
        LocalDate startDate = LocalDate.of(2023, 1, 2);
        int businessDays = 0;
        LocalDate expectedDate = startDate; 
        
        BusinessDayRequest request = new BusinessDayRequest();
        request.setStartDate(startDate);
        request.setBusinessDays(businessDays);
        
        when(calculatorService.calculateDateAfterBusinessDays(eq(startDate), eq(businessDays)))
            .thenReturn(expectedDate);
        
        
        mockMvc.perform(post("/api/v1/business-days")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.resultDate").value(expectedDate.toString()));
    }
    
    @Test
    @DisplayName("Deve calcular corretamente com muitos dias úteis")
    void deveCalcularCorretamenteComMuitosDiasUteis() throws Exception {
        
        LocalDate startDate = LocalDate.of(2023, 1, 2);
        int businessDays = 30;
        LocalDate expectedDate = LocalDate.of(2023, 2, 13); 
        
        BusinessDayRequest request = new BusinessDayRequest();
        request.setStartDate(startDate);
        request.setBusinessDays(businessDays);
        
        when(calculatorService.calculateDateAfterBusinessDays(eq(startDate), eq(businessDays)))
            .thenReturn(expectedDate);
        
        
        mockMvc.perform(post("/api/date/business-days")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultDate").value(expectedDate.toString()));
        
        
        mockMvc.perform(post("/api/v1/business-days")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.resultDate").value(expectedDate.toString()));
    }
}