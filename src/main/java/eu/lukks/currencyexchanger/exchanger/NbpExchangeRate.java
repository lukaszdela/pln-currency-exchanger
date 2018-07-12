package eu.lukks.currencyexchanger.exchanger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class NbpExchangeRate {

    @XmlElement(name = "No")
    private String no;

    @XmlElement(name = "EffectiveDate")
    @XmlSchemaType(name = "date")
    private XMLGregorianCalendar effectiveDate;

    @XmlElement(name = "Mid", required = true)
    private BigDecimal mid;

    public String getNo() {
        return no;
    }

    public XMLGregorianCalendar getEffectiveDate() {
        return effectiveDate;
    }

    public BigDecimal getMid() {
        return mid;
    }
}
