package com.asayke.moexservice.service;

import com.asayke.moexservice.dto.BondDto;
import com.asayke.moexservice.dto.StocksDto;
import com.asayke.moexservice.dto.TickersDto;
import com.asayke.moexservice.model.Currency;
import com.asayke.moexservice.model.Stock;
import com.asayke.moexservice.repository.BondRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BondService {
    private final BondRepository bondRepository;
    private final CacheManager cacheManager;

    public StocksDto getBondsFromMoex(TickersDto tickersDto) {
        List<BondDto> allBonds = new ArrayList<>();
        allBonds.addAll(bondRepository.getCorporateBonds());
        allBonds.addAll(bondRepository.getGovBonds());

        List<BondDto> result = allBonds.stream().filter(b -> tickersDto.getTickers().contains(b.getTicker()))
                .collect(Collectors.toList());

        List<Stock> stocks = result.stream().map(b -> {
            return Stock.builder()
                    .ticker(b.getTicker())
                    .name(b.getName())
                    .figi(b.getTicker())
                    .type("Bond")
                    .currency(Currency.RUB)
                    .source("MOEX")
                    .build();
        }).collect(Collectors.toList());

        return new StocksDto(stocks);
    }
}