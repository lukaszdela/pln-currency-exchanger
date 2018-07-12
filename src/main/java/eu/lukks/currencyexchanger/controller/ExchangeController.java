package eu.lukks.currencyexchanger.controller;

import eu.lukks.currencyexchanger.currency.CurrenciesListSelect;
import eu.lukks.currencyexchanger.model.ExchangeResult;
import eu.lukks.currencyexchanger.service.ICurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
public class ExchangeController {

    private ICurrencyExchangeService exchangeService;

    @Autowired
    public ExchangeController(ICurrencyExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("/exchange/{currency}/{value}/{date}")
    public ResponseEntity<ExchangeResult> getExchangedValue(@PathVariable(name = "value") BigDecimal value,
                                                            @PathVariable(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate exchangeDate,
                                                            @PathVariable(name = "currency") String currency) {

        ExchangeResult exchangeResult = exchangeService.exchange(value, exchangeDate, currency);
        return new ResponseEntity<>(exchangeResult, exchangeResult.getHttpStatus());
    }

    @GetMapping("exchange/currencies")
    public ResponseEntity<CurrenciesListSelect> getCurencies() {
        CurrenciesListSelect currencyList = exchangeService.getCategories();
        return new ResponseEntity<>(currencyList, HttpStatus.OK);
    }
}
