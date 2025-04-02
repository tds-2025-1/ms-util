package br.edu.ifrs.riogrande.tads.tds.util.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlgumaCoisaController {


    @GetMapping(
        value = "/coisa", 
        produces = MediaType.TEXT_PLAIN_VALUE)
    public String coisa() {
        return "Coisa Alguma";
    }

}
