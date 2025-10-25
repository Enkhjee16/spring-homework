package com.example.springhomework.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ForexService {
    private final RestClient rest = RestClient.create("https://api.exchangerate.host");

    public double convert(String from, String to, double amount) {
        // API: /convert?from=USD&to=EUR&amount=100  -> { "result": 92.34, ... }
        var json = rest.get()
                .uri(uri -> uri.path("/convert")
                        .queryParam("from", from)
                        .queryParam("to", to)
                        .queryParam("amount", amount)
                        .build())
                .retrieve()
                .body(ConvertResponse.class);

        if (json == null || json.result == null) throw new IllegalStateException("No result from API");
        return json.result;
    }

    // minimal DTO for the JSON response
    public record ConvertResponse(Boolean success, Double result) {}
}
