package it.blog.salarytax.amount.component;

import org.springframework.stereotype.Component;

import it.blog.salarytax.amount.configuration.TaxRanges;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TaxCalculator {

	private final TaxRanges taxRanges;

	public TaxCalculator(TaxRanges taxRanges) {
		this.taxRanges = taxRanges;
	}

	public double getAmountJunior(double annualSalary) {

		double remainder = annualSalary;
		double taxAmount = 0;
		double quote = 15000;

		int taxRateIndex = 0;

		if (annualSalary > quote)
			/*
			 * First range
			 */
			taxAmount += quote * 23 / 100;
		else
		{
			quote = remainder;
			taxAmount += annualSalary * 23 / 100;
		}

		/*
		 * new salary to apply
		 */
		remainder = remainder - quote;
		
		quote = 13000;

		if (remainder != 0) {

			if (annualSalary > 28000)
				/*
				 * Second range
				 */
				taxAmount += quote * 25 / 100;
			else {
				quote = remainder;
				taxAmount += remainder * 25 / 100;
			}
			remainder = remainder - quote;
		}
		
		quote = 22000;

		if (remainder != 0) {

			if (annualSalary > 50000)
				/*
				 * Third range
				 */
				taxAmount += quote * 35 / 100;
			else
			{
				quote = remainder;
				taxAmount += remainder * 35 / 100;
			}
			remainder = remainder - quote;
		}

		if (remainder != 0) {

			taxAmount += remainder * 35 / 100;
		}

		log.info("Applied {} tax ranges", taxRateIndex);

		return taxAmount;
	}

	public double getAmountSenior(double annualSalary) {

		double remainder = annualSalary;

		double taxAmount = 0;

		double quote = 0;

		int taxRateIndex = 0;

		while (remainder != 0) {

			if (annualSalary > taxRanges.getTaxRates().get(taxRateIndex).getUpperBound())
				/*
				 * First range
				 */
				quote = taxRanges.getTaxRates().get(taxRateIndex).getUpperBound()
						- taxRanges.getTaxRates().get(taxRateIndex).getLowerBound();
			else
				quote = remainder;
			/*
			 * Calc taxes
			 */
			taxAmount += quote * taxRanges.getTaxRates().get(taxRateIndex).getRate() / 100;
			/*
			 * new salary to apply
			 */
			remainder = remainder - quote;

			taxRateIndex++;
		}

		log.info("Applied {} tax ranges", taxRateIndex);

		return taxAmount;
	}

	public double getRecursiveAmountSenior(double annualSalary) {
		return getRecursiveAmountSenior(annualSalary, annualSalary, 0, 0);
	}
	private double getRecursiveAmountSenior(double annualSalary, double remainder, double taxAmount, int taxRateIndex) {

		double quote = 0;

		if (annualSalary > taxRanges.getTaxRates().get(taxRateIndex).getUpperBound())
			/*
			 * First range
			 */
			quote = taxRanges.getTaxRates().get(taxRateIndex).getUpperBound()
					- taxRanges.getTaxRates().get(taxRateIndex).getLowerBound();
		else
			quote = remainder;
		/*
		 * Calc taxes
		 */
		taxAmount += quote * taxRanges.getTaxRates().get(taxRateIndex).getRate() / 100;
		/*
		 * new salary to apply
		 */
		remainder = remainder - quote;

		if (remainder == 0)
			return taxAmount;

		return this.getRecursiveAmountSenior(annualSalary, remainder, taxAmount, taxRateIndex + 1);

	}

}
