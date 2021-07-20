package com.alexnail.currencyconversionservice.controller;

import com.alexnail.currencyconversionservice.dto.WalletDto;
import com.alexnail.currencyconversionservice.model.Wallet;
import com.alexnail.currencyconversionservice.service.WalletService;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wallets")
@AllArgsConstructor
public class WalletController {

    private final WalletService service;

    private final ModelMapper modelMapper;

    @GetMapping
    public List<WalletDto> findAll() {
        List<Wallet> wallets = service.findAll();
        return wallets.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public WalletDto findById(@PathVariable("id") Long id) {
        return convertToDto(service.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WalletDto create(@RequestBody WalletDto wallet) {
        return convertToDto(service.create(convertToEntity(wallet)));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody WalletDto wallet) {
        Wallet found = service.getById(wallet.getId());
        if (found != null)
            service.update(convertToEntity(wallet));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }

    Wallet convertToEntity(WalletDto dto) {
        return modelMapper.map(dto, Wallet.class);
    }

    WalletDto convertToDto(Wallet entity) {
        return modelMapper.map(entity, WalletDto.class);
    }
}
