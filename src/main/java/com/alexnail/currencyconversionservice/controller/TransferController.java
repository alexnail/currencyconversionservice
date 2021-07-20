package com.alexnail.currencyconversionservice.controller;

import com.alexnail.currencyconversionservice.dto.TransferDto;
import com.alexnail.currencyconversionservice.service.TransferService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfer")
@AllArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void transfer(@RequestBody TransferDto transferDto) {
        transferService.transfer(transferDto.getSourceWalletId(), transferDto.getTargetWalletId(),
                transferDto.getSend(), transferDto.getReceive());
    }

}
