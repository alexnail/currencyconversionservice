package com.alexnail.currencyconversionservice.controller;

import com.alexnail.currencyconversionservice.dto.ExchangeRateDto;
import com.alexnail.currencyconversionservice.model.ExchangeRate;
import com.alexnail.currencyconversionservice.service.ExchangeRateService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exchangerates")
@AllArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService service;

    private final ModelMapper modelMapper;

    @GetMapping("/{from}/{to}")
    public ExchangeRateDto getRateForPair(@PathVariable("from") String from, @PathVariable("to") String to) {
        return convertToDto(service.getRate(from, to));
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExchangeRateDto create(@RequestBody ExchangeRateDto rateDto) {
        return convertToDto(service.setRate(rateDto.getFrom(), rateDto.getTo(), convertToEntity(rateDto)));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody ExchangeRateDto rateDto) {
        service.setRate(rateDto.getFrom(), rateDto.getTo(), convertToEntity(rateDto));
    }

    @DeleteMapping("/{from}/{to}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String from, @PathVariable String to) {
        service.deleteRate(from, to);
    } 

    private ExchangeRateDto convertToDto(ExchangeRate entity) {
        return modelMapper.map(entity, ExchangeRateDto.class);
    }

    private ExchangeRate convertToEntity(ExchangeRateDto dto) {
        return modelMapper.map(dto, ExchangeRate.class);
    }
}
