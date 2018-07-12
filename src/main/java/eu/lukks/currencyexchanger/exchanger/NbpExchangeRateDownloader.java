package eu.lukks.currencyexchanger.exchanger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class NbpExchangeRateDownloader {

    private static final String NBP_API_URL = "http://api.nbp.pl/api/exchangerates/rates/A/{code}/{date}/";
    private static final String NO_DATA_ANSWER = "Not Found - Brak danych";
    private static final String UNRECOGNIZED_ANSWER = "B³êdny zakres dat / Invalid date range";
    private static final String NO_COMMUNICATION_ANSWER = "Not Found";

    private final RestTemplate restTemplate;

    @Autowired
    public NbpExchangeRateDownloader(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public NbpExchangeRateResult download(LocalDate forDate, String currency) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_XML);

        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        Map<String, String> params = prepareUrlParams(forDate, currency);

        try {
            ResponseEntity<NbpExchangeRateSeries> currencyRate = restTemplate.exchange(NBP_API_URL, HttpMethod.GET,
                    httpEntity, NbpExchangeRateSeries.class, params);
            return NbpExchangeRateResult.builder()
                    .withExchangeRate(currencyRate.getBody().getRates().stream().findFirst().get().getMid())
                    .success();

        } catch (HttpClientErrorException e) {

            if (noDataStatus(e)) {
                return NbpExchangeRateResult.builder()
                        .withErrorMessage("Kurs na dany dzień nie został opublikowany")
                        .failure();
            }
            if (unrecognizedAnswerStatus(e)) {
                return NbpExchangeRateResult.builder()
                        .withErrorMessage("Nie można wprowadzać daty z przyszłości")
                        .failure();
            }
            if (noCommunicationStatus(e)) {
                return NbpExchangeRateResult.builder()
                        .withErrorMessage("Błędne zapytanie")
                        .failure();
            }
            return null;
        }
    }

    private Map<String, String> prepareUrlParams(LocalDate forDate, String currency) {
        Map<String, String> params = new HashMap<>();
        params.put("code", currency);
        params.put("date", forDate.toString());
        return params;
    }

    private boolean noDataStatus(HttpClientErrorException e) {
        return e.getStatusCode() == HttpStatus.NOT_FOUND && e.getStatusText().equals(NO_DATA_ANSWER);
    }

    private boolean unrecognizedAnswerStatus(HttpClientErrorException e) {
        return e.getStatusCode() == HttpStatus.BAD_REQUEST && e.getStatusText().equals(UNRECOGNIZED_ANSWER);
    }

    private boolean noCommunicationStatus(HttpClientErrorException e) {
        return e.getStatusCode() == HttpStatus.NOT_FOUND && e.getStatusText().equals(NO_COMMUNICATION_ANSWER);
    }
}
