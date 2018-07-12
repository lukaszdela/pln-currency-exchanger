package eu.lukks.currencyexchanger.currency;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrenciesListSelect {

    private List<Currencies> rates;
}
