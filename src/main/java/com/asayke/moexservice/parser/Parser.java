package com.asayke.moexservice.parser;

import com.asayke.moexservice.dto.BondDto;

import java.util.List;

public interface Parser {
    List<BondDto> parse(String ratesAsString);
}