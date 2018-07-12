package eu.lukks.currencyexchanger.service;

import eu.lukks.currencyexchanger.currency.Currencies;
import eu.lukks.currencyexchanger.currency.CurrenciesDownloader;
import eu.lukks.currencyexchanger.currency.CurrenciesList;
import eu.lukks.currencyexchanger.currency.CurrenciesListSelect;
import eu.lukks.currencyexchanger.exchanger.NbpExchangeRateDownloader;
import eu.lukks.currencyexchanger.exchanger.NbpExchangeRateResult;
import eu.lukks.currencyexchanger.model.ExchangeResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static eu.lukks.currencyexchanger.exchanger.NbpExchangeRateExecutionStatus.NEGATIVE;
import static eu.lukks.currencyexchanger.exchanger.NbpExchangeRateExecutionStatus.POSITIVE;


@Service
public class CurrencyExchangeService implements ICurrencyExchangeService {

    private NbpExchangeRateDownloader downloader;
    private CurrenciesDownloader currenciesDownloader;

    public CurrencyExchangeService(NbpExchangeRateDownloader downloader, CurrenciesDownloader currenciesDownloader) {
        this.downloader = downloader;
        this.currenciesDownloader = currenciesDownloader;
    }

    @Override
    public ExchangeResult exchange(BigDecimal value, LocalDate date, String currency) {
        NbpExchangeRateResult rateResult = downloader.download(date, currency);
        ExchangeResult exchangeResult = new ExchangeResult();

            if (rateResult.getExecutionStatus() == POSITIVE) {
                BigDecimal calculatedValue = value.divide(rateResult.getExchangeRate(), 2, BigDecimal.ROUND_DOWN);
                exchangeResult.setValue(calculatedValue);
                exchangeResult.setHttpStatus(HttpStatus.OK);
            }
            if (rateResult.getExecutionStatus() == NEGATIVE) {
                exchangeResult.setErrorMessage(rateResult.getErrorMessage());
                exchangeResult.setHttpStatus(HttpStatus.BAD_REQUEST);
            }
        return exchangeResult;
    }

    @Override
    public CurrenciesListSelect getCategories(){
        CurrenciesListSelect currenciesListSelect = new CurrenciesListSelect();
        CurrenciesList[] currenciesLists = currenciesDownloader.currenciesLists();
        List<Currencies> rates = currenciesLists[0].getRates();
        currenciesListSelect.setRates(rates);
        return currenciesListSelect;
    }
}
