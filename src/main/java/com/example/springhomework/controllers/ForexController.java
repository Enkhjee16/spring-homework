package com.example.springhomework.controllers;

import com.example.springhomework.services.ForexService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForexController {

    private final ForexService forex;

    public ForexController(ForexService forex) {
        this.forex = forex;
    }

    // Show page without doing a conversion
    @GetMapping("/forex")
    public String forexPage(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(required = false) Double amount,
            Model model) {

        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("amount", amount);

        if (from != null && to != null && amount != null) {
            try {
                double result = forex.convert(from, to, amount);
                model.addAttribute("result", result);
                model.addAttribute("error", null);
            } catch (Exception ex) {
                model.addAttribute("result", null);
                model.addAttribute("error", "Conversion failed: " + ex.getMessage());
            }
        }
        return "forex"; // templates/forex.html
    }
}
