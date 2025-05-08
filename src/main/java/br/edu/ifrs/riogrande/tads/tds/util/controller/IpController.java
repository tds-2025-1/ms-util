package br.edu.ifrs.riogrande.tads.tds.util.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import br.edu.ifrs.riogrande.tads.tds.util.service.IpService;
import java.util.List;
import java.util.Arrays;

@RestController
public class IpController {

    private final IpService ipService;

    public IpController(IpService ipService) {
        this.ipService = ipService;
    }

    @Deprecated(since = "2025-05-10", forRemoval = false)
    @GetMapping(value = "/{baseIp}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String generateIps(@PathVariable String baseIp) {
        try {
            return ipService.generateUniqueIps(baseIp);
        } catch (IllegalArgumentException e) {
            return "Erro: " + e.getMessage();
        }
    }

    @GetMapping(value = "/api/v1/ips/{baseIp}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> generateIpsV1(@PathVariable String baseIp) {
        try {
            String ips = ipService.generateUniqueIps(baseIp);
            return Arrays.asList(ips.split("\n"));
        } catch (IllegalArgumentException e) {
            return List.of("Erro: " + e.getMessage());
        }
    }
}