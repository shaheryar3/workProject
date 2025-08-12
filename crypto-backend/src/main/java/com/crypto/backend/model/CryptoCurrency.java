package com.crypto.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptoCurrency {
    private String id;
    private String symbol;
    private String name;
    private String image;
    @JsonProperty("current_price")
    private Double currentPrice;
    @JsonProperty("market_cap")
    private Long marketCap;
    @JsonProperty("market_cap_rank")
    private Integer marketCapRank;
    @JsonProperty("price_change_percentage_24h")
    private Double priceChangePercentage24h;

    // Default constructor
    public CryptoCurrency() {}

    // Constructor
    public CryptoCurrency(String id, String symbol, String name, String image, 
                         Double currentPrice, Long marketCap, Integer marketCapRank, 
                         Double priceChangePercentage24h) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
        this.image = image;
        this.currentPrice = currentPrice;
        this.marketCap = marketCap;
        this.marketCapRank = marketCapRank;
        this.priceChangePercentage24h = priceChangePercentage24h;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Long getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Long marketCap) {
        this.marketCap = marketCap;
    }

    public Integer getMarketCapRank() {
        return marketCapRank;
    }

    public void setMarketCapRank(Integer marketCapRank) {
        this.marketCapRank = marketCapRank;
    }

    public Double getPriceChangePercentage24h() {
        return priceChangePercentage24h;
    }

    public void setPriceChangePercentage24h(Double priceChangePercentage24h) {
        this.priceChangePercentage24h = priceChangePercentage24h;
    }
}