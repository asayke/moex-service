package com.asayke.moexservice.dto;

import com.asayke.moexservice.model.Stock;
import lombok.Value;

import java.util.List;

@Value
public class StocksDto {
    List<Stock> stocks;
}