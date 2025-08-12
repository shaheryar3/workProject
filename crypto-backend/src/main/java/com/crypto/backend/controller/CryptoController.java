package com.crypto.backend.controller;

import com.crypto.backend.model.CryptoCurrency;
import com.crypto.backend.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/crypto")
@CrossOrigin(origins = "http://localhost:3000")
public class CryptoController {

    @Autowired
    private CryptoService cryptoService;

    @GetMapping("/top")
    public Mono<List<CryptoCurrency>> getTopCryptocurrencies(
            @RequestParam(defaultValue = "10") int limit) {
        return cryptoService.getTopCryptocurrencies(limit);
    }

    @GetMapping("/{id}")
    public Mono<CryptoCurrency> getCryptocurrencyById(@PathVariable String id) {
        return cryptoService.getCryptocurrencyById(id);
    }

    @GetMapping("/health")
    public String health() {
        return "Crypto API is running!";
    }
}