package com.alexnail.currencyconversionservice.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
@Hidden
public class HomeController {

    @GetMapping
    @ResponseBody
    public String getHome() {
        return "Currency conversion service";
    }
}