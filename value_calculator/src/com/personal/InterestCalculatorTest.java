package com.personal;

import org.junit.Test;

public class InterestCalculatorTest {

    @Test
    public void shouldCalculateCompoundInterest() {
        System.out.println("\nFinal Compound interest value after 10 years: " +
                InterestCalculator.calculateCompoundInterest(300000.0, 5.0, 30.0));
    }

    @Test
    public void shouldCalculateRecurringDepositInterest() {
        //http://www.onemint.com/2012/04/03/how-to-calculate-interest-on-recurring-deposits/

        System.out.println("\nFinal RD value after 10 years: " +
                InterestCalculator.calculateRecurringDeposit(47000.0, 8.25, 2.0));
    }

    @Test
    public void shouldCalculateLoanInterest() {
        //https://mozo.com.au/interest-rates/guides/calculate-interest-on-loan
        //https://www.wikihow.com/Calculate-Loan-Payments
        //https://smartasset.com/taxes/hennepin-county-minnesota-property-tax-calculator#rq4YJnYP9w

        System.out.println("\nMonthly Payment : " + InterestCalculator.calculateLoanMonthlyInstallment
                (300000.0, 4.0, 20.0));

    }

    @Test
    public void shouldCalculatePropertyTax(){
        //https://smartasset.com/taxes/hennepin-county-minnesota-property-tax-calculator#rq4YJnYP9w
        System.out.println("Property Tax Paid total : " + InterestCalculator.
                calculateTotalPropertyTax(300000.0, 1.345, 5.0, 20.0));
    }
}
