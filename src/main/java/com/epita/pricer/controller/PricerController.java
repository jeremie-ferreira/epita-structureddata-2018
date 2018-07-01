package com.epita.pricer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PricerController {

	@GetMapping("/")
    public String greeting() {
        return "index";
    }
}
