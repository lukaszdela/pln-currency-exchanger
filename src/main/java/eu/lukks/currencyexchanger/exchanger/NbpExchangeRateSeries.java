package eu.lukks.currencyexchanger.exchanger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class NbpExchangeRateSeries {

    @XmlElement(name = "Table")
    private String table;

    @XmlElement(name = "Currency")
    private String currency;

    @XmlElement(name = "Code")
    private String code;

    @XmlElement(name = "Rates")
    private List<NbpExchangeRate> rates;

    public String getTable() {
        return table;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCode() {
        return code;
    }

    public List<NbpExchangeRate> getRates() {
        return rates;
    }
}
