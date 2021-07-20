package com.alexnail.currencyconversionservice.controller;

import com.alexnail.currencyconversionservice.dto.CommissionDto;
import com.alexnail.currencyconversionservice.model.Commission;
import com.alexnail.currencyconversionservice.service.CommissionService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/commissions")
@AllArgsConstructor
public class CommissionController {

    private final CommissionService commissionService;

    private final ModelMapper modelMapper;

    @GetMapping
    public List<CommissionDto> getAllCommissions() {
        Collection<Commission> allCommissions = commissionService.getAllCommissions();
        return allCommissions.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/{from}/{to}")
    public CommissionDto getCommission(@PathVariable String from, @PathVariable String to) {
        return convertToDto(commissionService.getCommission(Pair.of(from, to)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommissionDto createCommission(@RequestBody CommissionDto commissionDto) {
        return convertToDto(commissionService.setCommission(convertToEntity(commissionDto)));
    }

    @DeleteMapping("/{from}/{to}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCommission(@PathVariable String from, @PathVariable String to) {
        commissionService.delete(commissionService.getCommission(Pair.of(from, to)));
    }

    private CommissionDto convertToDto(Commission entity) {
        return modelMapper.map(entity, CommissionDto.class);
    }

    private Commission convertToEntity(CommissionDto dto) {
        return modelMapper.map(dto, Commission.class);
    }
}
