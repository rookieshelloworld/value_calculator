package com.personal;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class InterestCalculatorTest {

    @Test
    public void shouldCalculateCompoundInterest() {
        Double maturityAmount = InterestCalculator.calculateCompoundInterest(1231.0, 16.0, 30.0, false);
        System.out.println("\nFinal Compound interest value after 10 years: " +
                maturityAmount);

        assertTrue(105681.19847468719 == maturityAmount);
    }

    @Test
    public void shouldCalculateRecurringDepositInterest() {
        //http://www.onemint.com/2012/04/03/how-to-calculate-interest-on-recurring-deposits/

        Double maturityAmount = InterestCalculator.calculateRecurringDeposit(47000.0, 8.25, 2.0);
        System.out.println("\nFinal RD value after 10 years: " + maturityAmount);

        assertTrue(1229513.6185081233 == maturityAmount);
    }

    @Test
    public void shouldCalculateLoanInterest() {
        //https://mozo.com.au/interest-rates/guides/calculate-interest-on-loan
        //https://www.wikihow.com/Calculate-Loan-Payments
        //https://smartasset.com/taxes/hennepin-county-minnesota-property-tax-calculator#rq4YJnYP9w
        //https://www.calculators.org/home/tax-savings.php
        Map<String, List<Double>> taxMap = InterestCalculator.calculateTaxPaidDetails
                (268000.0, 3.2, 30.0);

        assertTrue(1159.0111987659257 == taxMap.get("monthly_installment").get(0));
        assertTrue(149244.03155572218 == taxMap.get("total_interest").get(0));
        assertTrue(49250.53041338827 == taxMap.get("total_tax_savings").get(0));


    }

    @Test
    public void shouldCalculatePropertyTax() {
        //https://smartasset.com/taxes/hennepin-county-minnesota-property-tax-calculator#rq4YJnYP9w

        Double totalPropertyTax = InterestCalculator.
                calculateTotalPropertyTax(300000.0, 1.345, 5.0, 20.0).get(20);
        System.out.println("Property Tax Paid total : " + totalPropertyTax);

        assertTrue(144127.18104541244 == totalPropertyTax);
    }
}
