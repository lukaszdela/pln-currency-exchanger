package eu.lukks.currencyexchanger.currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

@Controller
public class CurrenciesDownloader {

    RestTemplate restTemplate;

    @Autowired
    public CurrenciesDownloader(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CurrenciesList[] currenciesLists(){

        String url = "http://api.nbp.pl/api/exchangerates/tables/A";
        return restTemplate.getForObject(url, CurrenciesList[].class);

    }
}
