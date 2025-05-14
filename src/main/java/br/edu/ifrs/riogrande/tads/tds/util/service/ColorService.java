package br.edu.ifrs.riogrande.tads.tds.util.service;

import org.springframework.stereotype.Service;

import br.edu.ifrs.riogrande.tads.tds.util.dto.HSL;
import br.edu.ifrs.riogrande.tads.tds.util.dto.RGB;

@Service
public class ColorService {


    public HSL rgbParaHsl(RGB cor){

        float rf = cor.r() / 255f;
        float gf = cor.g() / 255f;
        float bf = cor.b() / 255f;

        float max = Math.max(rf, Math.max(gf, bf));
        float min = Math.min(rf, Math.min(gf, bf));

        float h, s, l;
        l = (max + min) / 2;

        if (max == min) {
            h = s = 0;
        } else {
            float d = max - min;
            s = l > 0.5 ? d / (2 - max - min) : d / (max + min);

            if (max == rf) {
                h = (gf - bf) / d + (gf < bf ? 6 : 0);
            } else if (max == gf) {
                h = (bf - rf) / d + 2;
            } else {
                h = (rf - gf) / d + 4;
            }
            h /= 6;
        }

        int H = Math.round(h * 360);
        int S = Math.round(s * 100);
        int L = Math.round(l * 100);

        return new HSL(H, S, L);

    }
}
