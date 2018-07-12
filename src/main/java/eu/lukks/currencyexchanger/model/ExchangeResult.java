package eu.lukks.currencyexchanger.model;

import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

public class ExchangeResult {

    private BigDecimal value;
    private String errorMessage;
    private HttpStatus httpStatus;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }


}
