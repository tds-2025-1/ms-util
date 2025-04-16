package main.java.br.edu.ifrs.riogrande.tads.tds.util.controller;

import br.edu.ifrs.riogrande.tads.tds.util.dto.TimeElapsedRequest;
import br.edu.ifrs.riogrande.tads.tds.util.dto.TimeElapsedResponse;
import br.edu.ifrs.riogrande.tads.tds.util.service.TimeElapsedCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/time")
public class TimeElapsedController {

    private final TimeElapsedCalculatorService calculatorService;

    @Autowired
    public TimeElapsedController(TimeElapsedCalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PostMapping("/elapsed")
    public ResponseEntity<TimeElapsedResponse> calculateTimeElapsed(@RequestBody TimeElapsedRequest request) {
        String elapsedTime = calculatorService.calculateTimeElapsed(
                request.getStartTime(), request.getEndTime());
        
        return ResponseEntity.ok(new TimeElapsedResponse(elapsedTime));
    }
}