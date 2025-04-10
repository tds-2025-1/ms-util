package br.edu.ifrs.riogrande.tads.tds.util.service;

import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class IpService {

    public String generateSimpleIps(String baseIp) {

        String[] parts = baseIp.split("\\.");
        if (parts.length != 4) {
            throw new IllegalArgumentException("IP inválido. Use formato como 192.168.1.1");
        }

        String network = parts[0] + "." + parts[1] + "." + parts[2] + ".";
        Random random = new Random();
        Set<Integer> usedOctets = new HashSet<>();
        StringBuilder result = new StringBuilder();

        // Gera 5 IPs aleatórios únicos
        while (usedOctets.size() < 5) {
            int octeto = random.nextInt(254) + 1;

            if (usedOctets.add(octeto)) {
                result.append(network).append(octeto).append("\n");
            }

        }

        return result.toString();
    }
}