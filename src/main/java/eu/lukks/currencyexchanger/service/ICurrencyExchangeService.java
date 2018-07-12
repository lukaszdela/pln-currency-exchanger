package eu.lukks.currencyexchanger.service;


import eu.lukks.currencyexchanger.currency.CurrenciesListSelect;
import eu.lukks.currencyexchanger.model.ExchangeResult;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ICurrencyExchangeService {
    ExchangeResult exchange(BigDecimal value, LocalDate date, String currency);

    CurrenciesListSelect getCategories();
}
