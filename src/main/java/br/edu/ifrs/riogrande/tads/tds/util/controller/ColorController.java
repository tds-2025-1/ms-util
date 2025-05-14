package br.edu.ifrs.riogrande.tads.tds.util.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifrs.riogrande.tads.tds.util.dto.HSL;
import br.edu.ifrs.riogrande.tads.tds.util.dto.RGB;
import br.edu.ifrs.riogrande.tads.tds.util.service.ColorService;

@RestController
@RequestMapping("/api")
public class ColorController {

    private final ColorService colorService;

    ColorController(ColorService colorService){

        this.colorService = colorService;
    }

        @GetMapping("/rgb-to-hsl")
    public String convertRgbToHsl(@RequestParam String rgb) {
        // Decode the RGB input to handle any URL-encoded characters
        try {
            rgb = URLDecoder.decode(rgb, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "Error: Invalid encoding.";
        }

        // Validate the format of the hex color code (for example, #RRGGBB)
        if (!rgb.matches("^#?[A-Fa-f0-9]{6}$")) {
        return "Invalid format. Use #RRGGBB.";
        }

        if (rgb.startsWith("#")) {
            rgb = rgb.substring(1); // remove o '#'
        }


        // Proceed with the RGB to HSL conversion...
        int r = Integer.parseInt(rgb.substring(0, 2), 16);
        int g = Integer.parseInt(rgb.substring(2, 4), 16);
        int b = Integer.parseInt(rgb.substring(4, 6), 16);

        HSL cor = colorService.RgbParaHsl(new RGB(r, g, b));

        return String.format("HSL(%d, %d%%, %d%%)", cor.h(), cor.s(), cor.l());
    }
}
