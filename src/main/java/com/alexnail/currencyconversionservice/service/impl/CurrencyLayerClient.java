package com.alexnail.currencyconversionservice.service.impl;

import com.alexnail.currencyconversionservice.model.ExchangeRate;
import com.alexnail.currencyconversionservice.service.ExchangeRateRemoteClient;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

@Service
public class CurrencyLayerClient implements ExchangeRateRemoteClient {

    @Value("${exchangerate.remote.service.url}")
    private String baseUrl;
    @Value("${exchangerate.remote.service.key}")
    private String accessKey;

    @Override
    public ExchangeRate fetchLatestRate(String fromCurrency, String toCurrency) {
        CurrencyLayerEntity entity = new RestTemplate()
                .getForObject(baseUrl + "?access_key={key}&source={src}&currencies={tgt}",
                        CurrencyLayerEntity.class,
                        Map.of("key", accessKey, "src", fromCurrency, "tgt", toCurrency));

        if (entity != null && entity.isSuccess())
            return new ExchangeRate(fromCurrency, toCurrency,
                    BigDecimal.valueOf(entity.getQuotes().get(fromCurrency + toCurrency)),
                    new Timestamp(entity.getTimestamp()));
        return null;
    }

    @Data
    @NoArgsConstructor
    static class CurrencyLayerEntity {
        private boolean success;
        private String terms;
        private String privacy;
        private Long timestamp;
        private String source;
        private Map<String, Double> quotes;
    }
}
