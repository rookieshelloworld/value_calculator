package com.personal;

import java.util.*;

public class InterestCalculator {

    public static Double calculateCompoundInterest(Double value, Double roi, Double years, boolean print) {
        for (int year = 1; year <= years; year++) {
            value = value + ((roi / 100) * value);
            if (print) {
                System.out.println("Asset value for the year " + year + " : " + value);
            }
        }
        return value;
    }

    public static Double calculateRecurringDeposit(Double monthlyDeposit, Double roi, Double years) {
        Double totalMaturity = 0.0;
        for (int month = 1; month <= (years * 12); month++) {

            Double monthsRemaining = (years * 12) - (month - 1);

            Double onePlusRbyN = (1 + ((roi / 100) / 4));

            Double monthsInYear = monthsRemaining / 12;

            Double nMultiplyT = 4 * monthsInYear;

            Double maturityAmountForMonth = monthlyDeposit * Math.pow(onePlusRbyN, nMultiplyT);

            //System.out.println("month : " + month + "- maturity amount : " + maturityAmountForMonth);
            totalMaturity = totalMaturity + maturityAmountForMonth;
        }

        return totalMaturity;
    }

    public static Map calculateTaxPaidDetails(Double borrowedMoney, Double initialAssetMoney, Double roi, Double years, Double appreciationPercentage) {
        //https://emicalculator.net/
        //https://mozo.com.au/interest-rates/guides/calculate-interest-on-loan
        //https://www.calcxml.com/do/hom09?skn=#results

        Double currentAssetValue = initialAssetMoney;
        Double J = (roi / 100) / 12;
        Double N = 12 * years;

        Double onePlusJPowMinusN = Math.pow((1 + J), -1 * N);

        Double JByOneMinusAbove = J / (1 - onePlusJPowMinusN);

        Double monthlyInstallment = JByOneMinusAbove * borrowedMoney;

        Double totalInterestPaid = 0.0;
        Double remainingPrincipal = borrowedMoney;

        Map<String, List<Double>> map = new HashMap<>();

        List<Double> interestPaidList = new ArrayList<>();
        Double totalTaxSavings = 0.0;
        Double currentYearInterest = 0.0;

        System.out.println("\nMonthly EMI: " + monthlyInstallment + "\n");

        for (int month = 1; month <= (years * 12); month++) {
            Double interestPaid = ((roi / 100) / 12) * remainingPrincipal;
            remainingPrincipal = remainingPrincipal - (monthlyInstallment - interestPaid);

            currentYearInterest = currentYearInterest + interestPaid;
            totalInterestPaid = totalInterestPaid + interestPaid;

            if ((month % 12 == 0 && month > 0) || month == ((years * 12) - 1)) {
                if (month == ((years * 12) - 1))
                    System.out.println("\nAfter " + (month + 1) / 12 + " years\n***************************");
                else System.out.println("\nAfter " + month / 12 + " years\n***************************");

                currentAssetValue = currentAssetValue + ((appreciationPercentage / 100) * currentAssetValue);

                System.out.println("House Value: " + currentAssetValue);
                System.out.println("Net House Value : " + (currentAssetValue - totalInterestPaid - remainingPrincipal - (initialAssetMoney - borrowedMoney)));
                System.out.println("Interest paid: " + totalInterestPaid);
                System.out.println("Principal remaining  : " + remainingPrincipal);
                System.out.println("Principal paid  : " + (borrowedMoney - remainingPrincipal));
                System.out.println("Tax saved for current year (approx) : " + currentYearInterest * 0.33);
                System.out.println("Current year interest: " + currentYearInterest);


                totalTaxSavings = totalTaxSavings + (currentYearInterest * 0.33);

                System.out.println("Total Tax Saved (approx) : " + totalTaxSavings);

                interestPaidList.add(currentYearInterest);
                currentYearInterest = 0.0;
            }
        }
        map.put("total_interest", Arrays.asList(totalInterestPaid));
        map.put("total_tax_savings", Arrays.asList(totalTaxSavings));
        map.put("monthly_installment", Arrays.asList(monthlyInstallment));
        map.put("interest_paid_yearly", interestPaidList);
        System.out.println("************************************");

        return map;
    }

    public static List<Double> calculateTotalPropertyTax(Double currentAssetValue, Double propertyTaxPercentage,
                                                         Double appreciationPercentage, Double years) {
        List<Double> propertyTaxList = new ArrayList<>();
        Double totalPropertyTax = currentAssetValue * (propertyTaxPercentage / 100);
        for (int year = 1; year <= years; year++) {
            currentAssetValue = calculateCompoundInterest(currentAssetValue, appreciationPercentage, 1.0, false);
            Double propertyTaxPaidForYear = (propertyTaxPercentage / 100) * currentAssetValue;
            //  System.out.println("Asset value for year : " + year + ": " + currentAssetValue);
            //  System.out.println("Property tax paid for year : " + year + ": " + propertyTaxPaidForYear);

            propertyTaxList.add(propertyTaxPaidForYear);
            totalPropertyTax = totalPropertyTax + propertyTaxPaidForYear;

        }
        propertyTaxList.add(totalPropertyTax);
        return propertyTaxList;
    }
}
