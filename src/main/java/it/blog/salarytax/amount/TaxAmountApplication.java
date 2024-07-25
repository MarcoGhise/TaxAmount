package it.blog.salarytax.amount;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.blog.salarytax.amount.component.TaxCalculator;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class TaxAmountApplication implements CommandLineRunner{
	
	private final TaxCalculator taxCalculator;
	
	public static void main(String[] args) {
		SpringApplication.run(TaxAmountApplication.class, args);
	}
	
	public TaxAmountApplication(TaxCalculator taxCalculator) {
		this.taxCalculator = taxCalculator;
	}

	@Override
	public void run(String... args) throws Exception {
		
		log.info("Please enter annual salary : (send 'bye' to exit) ");
		
		Scanner input = new Scanner(System.in);
		while (true) {
			String line = input.nextLine();
			if ("bye".equalsIgnoreCase(line)) {
				break;
			}

			double taxSalary = taxCalculator.getAmountSenior(Double.valueOf(line));
			double netSalary = Double.valueOf(line) - taxSalary;

			log.info("Total Tax Amount: {}", taxSalary);
			log.info("Net salary: {}", netSalary);

		}
		input.close();
		
	}

}
