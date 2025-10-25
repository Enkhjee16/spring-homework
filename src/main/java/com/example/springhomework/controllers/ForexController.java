package com.example.springhomework.controllers;

import com.example.springhomework.services.ForexService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ForexController {

    private final ForexService forex;

    public ForexController(ForexService forex) {
        this.forex = forex;
    }

    @GetMapping("/forex")
    public String showForm(Model model) {
        model.addAttribute("amount", 100);
        model.addAttribute("from", "USD");
        model.addAttribute("to", "EUR");
        return "forex";
    }

    @PostMapping("/forex")
    public String convert(
            @RequestParam double amount,
            @RequestParam String from,
            @RequestParam String to,
            Model model) {

        double result = forex.convert(from, to, amount);
        model.addAttribute("amount", amount);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("result", result);
        return "forex";
    }
}
