package it.blog.salarytax.amount.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application")
public class TaxRanges {

    private List<TaxRates> taxRates;

    public List<TaxRates> getTaxRates() {
        return taxRates;
    }

    public void setTaxRates(List<TaxRates> taxRates) {
        this.taxRates = taxRates;
    }

}
