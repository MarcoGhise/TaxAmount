package it.blog.salarytax.amount.configuration;

import lombok.Data;

@Data
public class TaxRates {

	private double rate;
	
	private double lowerBound;
	
	private double upperBound;
	
}
