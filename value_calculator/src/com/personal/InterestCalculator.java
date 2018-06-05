package com.personal;

public class InterestCalculator {

    public static Double calculateCompoundInterest(Double value, Double roi, Double years) {
        for (int year = 1; year <= years; year++) {
            value = value + ((roi / 100) * value);
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

    public static Double calculateLoanMonthlyInstallment(Double borrowedMoney, Double roi, Double years) {
        Double J = (roi / 100) / 12;
        Double N = 12 * years;

        Double onePlusJPowMinusN = Math.pow((1 + J), -1 * N);

        Double JByOneMinusAbove = J / (1 - onePlusJPowMinusN);

        Double monthlyInstallment = JByOneMinusAbove * borrowedMoney;

        Double totalInterestPaid = 0.0;
        Double remainingPrincipal = borrowedMoney;

        System.out.println("\nMonthly EMI: " + monthlyInstallment + "\n");
        for (int month = 0; month < (years * 12); month++) {
            Double interestPaid = ((roi / 100) / 12) * remainingPrincipal;
            remainingPrincipal = remainingPrincipal - (monthlyInstallment - interestPaid);

            totalInterestPaid = totalInterestPaid + interestPaid;

            if ((month % 12 == 0 && month > 0) || month == ((years * 12) - 1)) {
                if (month == ((years * 12) - 1))
                    System.out.println("\nAfter " + (month + 1) / 12 + " years\n***************************");
                else System.out.println("\nAfter " + month / 12 + " years\n***************************");
                System.out.println("Total interest paid: " + totalInterestPaid);
                System.out.println("Principal remaining  : " + remainingPrincipal);
                System.out.println("Principal paid  : " + (borrowedMoney - remainingPrincipal));

            }
            totalInterestPaid = totalInterestPaid + interestPaid;
        }
        System.out.println("************************************");


        return monthlyInstallment;
    }

    public static Double calculateTotalPropertyTax(Double currentAssetValue, Double propertyTaxPercentage,
                                                   Double appreciationPercentage, Double years) {
        Double totalPropertyTax = currentAssetValue * (propertyTaxPercentage / 100);
        for (int year = 1; year <= years; year++) {
            currentAssetValue = calculateCompoundInterest(currentAssetValue, appreciationPercentage, 1.0);
            Double propertyTaxPaidForYear = (propertyTaxPercentage / 100) * currentAssetValue;
            //  System.out.println("Asset value for year : " + year + ": " + currentAssetValue);
            //  System.out.println("Property tax paid for year : " + year + ": " + propertyTaxPaidForYear);
            totalPropertyTax = totalPropertyTax + propertyTaxPaidForYear;

        }
        return totalPropertyTax;
    }
}
