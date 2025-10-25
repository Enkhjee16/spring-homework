package com.example.springhomework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;

@Controller
public class SoapController {

    private final RestClient client = RestClient.create("https://www.dataaccess.com/webservicesserver");

    @GetMapping("/soap")
    public String showForm() {
        return "soap";
    }

    @PostMapping("/soap")
    public String callSoap(@RequestParam int number, Model model) {
        // Public SOAP service: NumberConversion.wso
        String requestBody = """
            <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <NumberToWords xmlns="http://www.dataaccess.com/webservicesserver/">
                  <ubiNum>%d</ubiNum>
                </NumberToWords>
              </soap:Body>
            </soap:Envelope>
            """.formatted(number);

        String response = client.post()
                .uri("/NumberConversion.wso")
                .header("Content-Type", "text/xml; charset=utf-8")
                .body(requestBody)
                .retrieve()
                .body(String.class);

        // crude extraction of result text between <m:NumberToWordsResult> tags
        String result = response.replaceAll("(?s).*<m:NumberToWordsResult>(.*?)</m:NumberToWordsResult>.*", "$1");

        model.addAttribute("number", number);
        model.addAttribute("result", result);
        return "soap";
    }
}