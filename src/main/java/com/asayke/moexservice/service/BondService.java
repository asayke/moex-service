package com.asayke.moexservice.service;

import com.asayke.moexservice.dto.BondDto;
import com.asayke.moexservice.dto.StocksDto;
import com.asayke.moexservice.dto.TickersDto;
import com.asayke.moexservice.exception.LimitRequestsException;
import com.asayke.moexservice.model.Currency;
import com.asayke.moexservice.model.Stock;
import com.asayke.moexservice.moexclient.CorporateBondsClient;
import com.asayke.moexservice.moexclient.GovBondsClient;
import com.asayke.moexservice.parser.Parser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BondService {
    private final CorporateBondsClient corporateBondsClient;
    private final GovBondsClient govBondsClient;
    private final Parser parser;

    public StocksDto getBondsFromMoex(TickersDto tickersDto) {
        List<BondDto> allBonds = new ArrayList<>();
        allBonds.addAll(getCorporateBonds());
        allBonds.addAll(getGovBonds());

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

    public List<BondDto> getCorporateBonds() {
        log.info("Getting corporate bonds from Moex");

        String xmlFromMoex = corporateBondsClient.getBondsFromMoex();
        List<BondDto> bondDtos = parser.parse(xmlFromMoex);

        if (bondDtos.isEmpty()) {
            log.error("Moex isn't answering for getting corporate bonds.");
            throw new LimitRequestsException("Moex isn't answering for getting corporate bonds.");
        }

        return bondDtos;
    }

    public List<BondDto> getGovBonds() {
        log.info("Getting government bonds from Moex");

        String xmlFromMoex = govBondsClient.getBondsFromMoex();
        List<BondDto> bondDtos = parser.parse(xmlFromMoex);

        if (bondDtos.isEmpty()) {
            log.error("Moex isn't answering for getting government bonds.");
            throw new LimitRequestsException("Moex isn't answering for getting government bonds.");
        }

        return bondDtos;
    }
}