package br.edu.ifrs.riogrande.tads.tds.util.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import br.edu.ifrs.riogrande.tads.tds.util.service.IpService;

@RestController
public class IpController {

    private final IpService ipService;

    public IpController(IpService ipService) {
        this.ipService = ipService;
    }

    @GetMapping(value = "/{baseIp}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String generateIps(@PathVariable String baseIp) {

        return ipService.generateSimpleIps(baseIp);
    }
}