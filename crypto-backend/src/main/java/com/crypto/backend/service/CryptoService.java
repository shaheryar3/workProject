package com.crypto.backend.service;

import com.crypto.backend.model.CryptoCurrency;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class CryptoService {

    private final WebClient webClient;
    private static final String COINGECKO_API_URL = "https://api.coingecko.com/api/v3";

    public CryptoService() {
        this.webClient = WebClient.builder()
                .baseUrl(COINGECKO_API_URL)
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
                .build();
    }

    public Mono<List<CryptoCurrency>> getTopCryptocurrencies(int limit) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/coins/markets")
                        .queryParam("vs_currency", "usd")
                        .queryParam("order", "market_cap_desc")
                        .queryParam("per_page", limit)
                        .queryParam("page", 1)
                        .queryParam("sparkline", false)
                        .build())
                .retrieve()
                .bodyToFlux(CryptoCurrency.class)
                .timeout(Duration.ofSeconds(5))
                .collectList()
                .doOnError(error -> System.err.println("Error fetching crypto data: " + error.getMessage()))
                .onErrorReturn(getDummyData(limit));
    }

    public Mono<CryptoCurrency> getCryptocurrencyById(String id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/coins/markets")
                        .queryParam("vs_currency", "usd")
                        .queryParam("ids", id)
                        .queryParam("sparkline", false)
                        .build())
                .retrieve()
                .bodyToFlux(CryptoCurrency.class)
                .timeout(Duration.ofSeconds(10))
                .retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(1)))
                .next()
                .onErrorReturn(getDummyCryptocurrency(id));
    }

    private List<CryptoCurrency> getDummyData(int limit) {
        List<CryptoCurrency> dummyData = new ArrayList<>();
        dummyData.add(new CryptoCurrency("bitcoin", "btc", "Bitcoin", 
            "https://assets.coingecko.com/coins/images/1/large/bitcoin.png", 
            35000.0, 680000000000L, 1, 2.5));
        dummyData.add(new CryptoCurrency("ethereum", "eth", "Ethereum", 
            "https://assets.coingecko.com/coins/images/279/large/ethereum.png", 
            2100.0, 250000000000L, 2, -1.2));
        dummyData.add(new CryptoCurrency("binancecoin", "bnb", "BNB", 
            "https://assets.coingecko.com/coins/images/825/large/bnb-icon2_2x.png", 
            240.0, 36000000000L, 4, 0.8));
        dummyData.add(new CryptoCurrency("cardano", "ada", "Cardano", 
            "https://assets.coingecko.com/coins/images/975/large/cardano.png", 
            0.35, 12000000000L, 7, 3.1));
        dummyData.add(new CryptoCurrency("solana", "sol", "Solana", 
            "https://assets.coingecko.com/coins/images/4128/large/solana.png", 
            20.0, 8000000000L, 9, -2.8));
        return dummyData.subList(0, Math.min(limit, dummyData.size()));
    }

    private CryptoCurrency getDummyCryptocurrency(String id) {
        return new CryptoCurrency(id, id.substring(0, 3), id.substring(0, 1).toUpperCase() + id.substring(1), 
            "https://via.placeholder.com/40", 100.0, 1000000000L, 10, 0.0);
    }
}