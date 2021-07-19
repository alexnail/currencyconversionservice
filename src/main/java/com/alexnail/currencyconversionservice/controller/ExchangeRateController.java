package com.alexnail.currencyconversionservice.controller;

import com.alexnail.currencyconversionservice.dto.ExchangeRateDto;
import com.alexnail.currencyconversionservice.model.ExchangeRate;
import com.alexnail.currencyconversionservice.service.ExchangeRateService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exchangerates")
@AllArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    private final ModelMapper modelMapper;

    @GetMapping("/{from}/{to}")
    public ExchangeRateDto getRateForPair(@PathVariable("from") String from, @PathVariable("to") String to) {
        return convertToDto(exchangeRateService.getRate(from, to));
    }

    private ExchangeRateDto convertToDto(ExchangeRate entity) {
        return modelMapper.map(entity, ExchangeRateDto.class);
    }
}
