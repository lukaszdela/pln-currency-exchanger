package eu.lukks.currencyexchanger.exchanger;

import java.math.BigDecimal;
import java.util.Objects;

public class NbpExchangeRateResult {

    private NbpExchangeRateExecutionStatus executionStatus;
    private String errorMessage;
    private BigDecimal exchangeRate;

    NbpExchangeRateResult(NbpExchangeRateExecutionStatus executionStatus, String errorMessage,
                          BigDecimal exchangeRate) {
        this.executionStatus = executionStatus;
        this.errorMessage = errorMessage;
        this.exchangeRate = exchangeRate;
    }

    public NbpExchangeRateExecutionStatus getExecutionStatus() {
        return executionStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public static class NbpCurrencyRateResultBuilder {

        private String errorMessage;
        private BigDecimal exchangeRate;

        public NbpCurrencyRateResultBuilder withErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public NbpCurrencyRateResultBuilder withExchangeRate(BigDecimal exchangeRate) {
            this.exchangeRate = exchangeRate;
            return this;
        }

        public NbpExchangeRateResult failure() {
            if (Objects.isNull(errorMessage)) {
                throw new IllegalArgumentException("Error message must be not null value");
            }
            return new NbpExchangeRateResult(NbpExchangeRateExecutionStatus.NEGATIVE, errorMessage, exchangeRate);
        }

        public NbpExchangeRateResult success() {
            if (Objects.isNull(exchangeRate)) {
                throw new IllegalArgumentException("Exchange rate must be not null value");
            }
            return new NbpExchangeRateResult(NbpExchangeRateExecutionStatus.POSITIVE, errorMessage, exchangeRate);
        }

    }

    public static NbpCurrencyRateResultBuilder builder() {
        return new NbpCurrencyRateResultBuilder();
    }
}
