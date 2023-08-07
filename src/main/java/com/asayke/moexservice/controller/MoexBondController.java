package com.asayke.moexservice.controller;

import com.asayke.moexservice.dto.StocksDto;
import com.asayke.moexservice.dto.TickersDto;
import com.asayke.moexservice.service.BondService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bonds")
public class MoexBondController {
    private final BondService bondService;

    @PostMapping("/getBondsFromTickers")
    public StocksDto getBondsFromMoex(@RequestBody TickersDto tickers) {
        return bondService.getBondsFromMoex(tickers);
    }
}