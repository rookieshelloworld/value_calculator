package com.personal;

public class AssetValueCalculator {

    public static Double calculateNetAssetValue(
            Double currentAssetValue, Double downPayment, Double years, Double appreciationPercentage,
            Double monthlyBills, Double propertyTaxPercentage, Double bankInterest, Double miscellaneous) {


        Double futureAssetValue = InterestCalculator.calculateCompoundInterest(currentAssetValue, appreciationPercentage, years);

        Double totalBillsPaid = monthlyBills * 12 * years;

        Double propertyTaxPaid = InterestCalculator.calculateTotalPropertyTax(currentAssetValue, propertyTaxPercentage,
                appreciationPercentage, years);

        Double totalEMIWithInterestPaidToBank = calculateLoanRepaidToBank(
                (currentAssetValue - downPayment), bankInterest, years);

        System.out.println("\nHouse market value after " + years + " years: " + futureAssetValue);
        System.out.println("\nBills And Property tax paid : " + propertyTaxPaid + totalBillsPaid);

        System.out.println("Total EMI and interest paid to bank: " + totalEMIWithInterestPaidToBank +"\n");

        return futureAssetValue - totalEMIWithInterestPaidToBank - totalBillsPaid - propertyTaxPaid - downPayment - miscellaneous;

    }

    public static Double calculateExpenditure(Double currentAssetValue, Double years, Double appreciationPercentage,
                                              Double monthlyBills, Double propertyTaxPercentage) {
        Double propertyTaxPaid = InterestCalculator.calculateTotalPropertyTax(currentAssetValue, propertyTaxPercentage,
                appreciationPercentage, years);

        Double totalBillsPaid = monthlyBills * 12 * years;

        return propertyTaxPaid + totalBillsPaid;

    }


    public static Double calculateRentPaidValue(Double currentRentWithBills, Double annualRise, Double years) {

        Double rentPaidTotal = currentRentWithBills * years * 12;
        Double rentPaidIncreasedValue = 0.0;

        for (int year = 1; year <= years; year++) {
            rentPaidIncreasedValue = rentPaidIncreasedValue + (annualRise * 12 * year);
        }

        return rentPaidTotal + rentPaidIncreasedValue;

    }

    public static Double calculateCompoundInterest(Double investmentAmount, Double roi, Double years) {
        return InterestCalculator.calculateCompoundInterest(investmentAmount, roi, years);
    }


    public static double calculateLoanRepaidToBank(Double investmentAmount, Double roi, Double years) {
        return InterestCalculator.calculateLoanMonthlyInstallment(investmentAmount, roi, years) * 12 * years;

    }

    public static Double calculateRecurringDeposit(Double monthly, Double roi, Double years) {
        return InterestCalculator.calculateRecurringDeposit(monthly, roi, years);

    }

}
