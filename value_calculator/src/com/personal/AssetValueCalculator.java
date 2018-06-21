package com.personal;

import java.util.List;
import java.util.Map;

public class AssetValueCalculator {

    public static void calculateRealVsNonRealAssetDifference(
            Double currentAssetValue, Double downPayment, Double years, Double appreciationPercentage,
            Double monthlyBills, Double propertyTaxPercentage, Double bankInterest, Double miscellaneous, Double rent, Double annualRiseOfRent, Double investROI, Double monthlyBillForRentedHouse) {

        //House Expenditure
        List<Double> annualPropertyTaxPaidList = InterestCalculator.calculateTotalPropertyTax(currentAssetValue, propertyTaxPercentage,
                appreciationPercentage, years);

        Double totalPropertyTaxPaid = annualPropertyTaxPaidList.get(annualPropertyTaxPaidList.size() - 1);
        Double totalBillsPaid = monthlyBills * 12 * years;
        Map<String, List<Double>> map = InterestCalculator.calculateTaxPaidDetails((currentAssetValue - downPayment), currentAssetValue, bankInterest, years, appreciationPercentage);
        Double totalEMIWithInterestPaidToBank = getTotalEMIWithInterestPaidToBankPropertyTaxIncluded(years, map.get("monthly_installment").get(0),
                annualPropertyTaxPaidList);

        //Calculate Growth of EMI difference
        String calculateDifference = calculateRealAndNonRealSavingsCompound(currentAssetValue, downPayment, years, appreciationPercentage,
                monthlyBills, propertyTaxPercentage, investROI, miscellaneous,
                rent, annualRiseOfRent, map);

        Double billsDifferenceGrowth = calculateBillsDifferenceGrowth(monthlyBills, monthlyBillForRentedHouse, years, investROI);

        System.out.println("\n***** House Expenditure ******");
        System.out.println("\nBills  paid : " + totalBillsPaid);
        System.out.println("Property Tax Paid : " + totalPropertyTaxPaid);

        Double houseInsurance = 1200.0 * years;
        System.out.println("Total House Insurance : " + houseInsurance);
        System.out.println("Total EMI paid to bank + property tax: " + totalEMIWithInterestPaidToBank + "\n");

        System.out.println("***** House Income ******");
        Double futureHouseValue = InterestCalculator.calculateCompoundInterest(currentAssetValue, appreciationPercentage, years, false);
        System.out.println("\nHouse market value after " + years + " years: " + futureHouseValue);

        Double totalTaxSavings = map.get("total_tax_savings").get(0);
        System.out.println("Total Tax Savings : " + totalTaxSavings);

        Double realEstateNetValue = futureHouseValue + totalTaxSavings + Double.valueOf(calculateDifference.split("-")[0]) - totalEMIWithInterestPaidToBank -
                -downPayment - miscellaneous - houseInsurance;

        System.out.println("\n***** Real Estate Net Value -->>>>" + realEstateNetValue);

        System.out.println("\n***** Rent Expenditure ******");
        Double rentPaid = calculateRentPaidValue(rent, annualRiseOfRent, years);
        System.out.println("\nRent Paid over " + years + " years : " + rentPaid);
        System.out.println("Monthly Bills Paid : " + monthlyBillForRentedHouse * 12 * years);

        System.out.println("\n***** Downpayment Growth ******");
        Double downPaymentGrowth = InterestCalculator.calculateCompoundInterest(downPayment, investROI, years, false);
        System.out.println("\nDownpayment grown over " + years + " years : " + downPaymentGrowth);

        Double nonRealEstateNetValue = downPaymentGrowth + billsDifferenceGrowth - rentPaid + Double.valueOf(calculateDifference.split("-")[1]);

        System.out.println("\n***** Non-Real Estate Net Value -->>>>" + nonRealEstateNetValue);

        if (nonRealEstateNetValue > realEstateNetValue) {
            System.out.println("\n***********************************\nNon-realestate will leave you with additional wealth of " + (nonRealEstateNetValue - realEstateNetValue) +
                    " in " + years + " years. \n\n\n**** NON-REALESTATE investments recommended ****");
        } else if (nonRealEstateNetValue < realEstateNetValue) {
            System.out.println("\n***********************************\nRealestate will leave you with additional wealth of " + (realEstateNetValue - nonRealEstateNetValue) +
                    " in " + years + " years. \n\n\n**** REALESTATE investments recommended ****");
        }
    }

    private static Double calculateBillsDifferenceGrowth(Double monthlyBills, Double monthlyBillForRentedHouse, Double years, Double investROI) {
        Double billDifference = monthlyBills - monthlyBillForRentedHouse;
        Double growthDIfferenceTotal = 0.0;
        for (int year = 1; year <= years; year++) {
            growthDIfferenceTotal = growthDIfferenceTotal + InterestCalculator.calculateCompoundInterest(billDifference * 12, investROI, years - year, false);
        }
        return growthDIfferenceTotal;
    }

    public static String calculateRealAndNonRealSavingsCompound(Double currentAssetValue, Double downPayment, Double years, Double appreciationPercentage,
                                                                Double monthlyBills, Double propertyTaxPercentage, Double bankInterest, Double investROI,
                                                                Double rent, Double rentIncreaseEveryYear, Map<String, List<Double>> map) {
        Double baseMonthLyInstallment = map.get("monthly_installment").get(0);
        List<Double> propertyTaxPaidList = InterestCalculator.calculateTotalPropertyTax(currentAssetValue, propertyTaxPercentage,
                appreciationPercentage, years);

        Double savingsWithRealEstate = 0.0;
        Double rentSavings = 0.0;

        List<Double> interestPaidList = map.get("interest_paid_yearly");
        for (int year = 1; year <= years; year++) {
            Double rentPaidForYear = (rent + (rentIncreaseEveryYear * year)) * 12;

            Double propertyTaxPaidForYear = propertyTaxPaidList.get(year - 1);

            Double taxSavingsEveryYear = interestPaidList.get(year - 1) * 0.33;

            Double emiPaidForyear = propertyTaxPaidForYear + (baseMonthLyInstallment * 12);
            Double differenceOfRentAndEMI = rentPaidForYear - (emiPaidForyear - taxSavingsEveryYear);

            if (differenceOfRentAndEMI > 0) {
                savingsWithRealEstate = savingsWithRealEstate + InterestCalculator.calculateCompoundInterest(differenceOfRentAndEMI, investROI, (years - year), false
                );
            } else {
                rentSavings = rentSavings + InterestCalculator.calculateCompoundInterest((differenceOfRentAndEMI * -1), investROI, (years - year), false);
            }
        }
        System.out.println("\nDifference amount saved in real Estate : " + savingsWithRealEstate);
        System.out.println("Difference amount saved in rent/non-real : " + rentSavings);

        return savingsWithRealEstate + "-" + rentSavings;

    }

    public static Double calculateNetAssetValueFixedSavings(
            Double currentAssetValue, Double downPayment, Double years, Double appreciationPercentage,
            Double monthlyBills, Double propertyTaxPercentage, Double bankInterest, Double miscellaneous) {


        //house expenditure
        List<Double> propertyTaxPaidList = InterestCalculator.calculateTotalPropertyTax(currentAssetValue, propertyTaxPercentage,
                appreciationPercentage, years);
        Double propertyTaxPaid = propertyTaxPaidList.get(propertyTaxPaidList.size() - 1);
        Double totalBillsPaid = monthlyBills * 12 * years;
        Map<String, List<Double>> map = InterestCalculator.calculateTaxPaidDetails((currentAssetValue - downPayment), currentAssetValue, bankInterest, years, appreciationPercentage);
        Double baseMonthLyInstallment = map.get("monthly_installment").get(0);
        Double totalEMIWithInterestPaidToBank = getTotalEMIWithInterestPaidToBankPropertyTaxIncluded(years, baseMonthLyInstallment,
                propertyTaxPaidList);
        Double houseInsurance = 1200.0 * years;

        //hosue income
        Double futureAssetValue = InterestCalculator.calculateCompoundInterest(currentAssetValue, appreciationPercentage, years, true);
        Double totalTaxSavings = map.get("total_tax_savings").get(0);


        System.out.println("\nHouse market value after " + years + " years: " + futureAssetValue);
        System.out.println("Total Tax Savings : " + totalTaxSavings);
        System.out.println("\nBills And Property tax paid : " + propertyTaxPaid + totalBillsPaid);
        System.out.println("Total House Insurance : " + houseInsurance);
        System.out.println("Total EMI and interest paid to bank: " + totalEMIWithInterestPaidToBank + "\n");

        return futureAssetValue + totalTaxSavings - totalEMIWithInterestPaidToBank - totalBillsPaid -
                propertyTaxPaid - downPayment - miscellaneous - houseInsurance;

    }

    private static double getTotalEMIWithInterestPaidToBankPropertyTaxIncluded(Double years, Double baseMonthLyInstallment, List<Double> propertyTaxPaidList) {
        return (baseMonthLyInstallment * 12 * years) + propertyTaxPaidList.get(propertyTaxPaidList.size() - 1);
    }

    public static Double calculateRentPaidValue(Double currentRentWithBills, Double annualRise, Double years) {

        Double rentPaidTotal = currentRentWithBills * years * 12;
        Double rentPaidIncreasedValue = 0.0;

        for (int year = 1; year <= years; year++) {
            rentPaidIncreasedValue = rentPaidIncreasedValue + (annualRise * 12 * year);
        }

        return rentPaidTotal + rentPaidIncreasedValue;

    }

    public static double calculateLoanRepaidToBank(Double borrowedAmount, Double assetValue, Double roi, Double years, Double houseValueAppreciationPercentage) {
        Map<String, List<Double>> map = InterestCalculator.calculateTaxPaidDetails(borrowedAmount, assetValue, roi, years, houseValueAppreciationPercentage);
        return map.get("monthly_installment").get(0) * 12 * years;

    }

    public static String calculateInvestmentOverEMIAndRentDifference(Double monthlyInstallment, Double rent, Double annualRiseOfRent, Double years,
                                                                     Double investmentRateOfInterest) {
        Double savingsWhenEMIGreaterThanRent = 0.0;
        Double savingsWhenRentGreaterThanEMI = 0.0;
        for (int year = 0; year < years; year++) {
            if ((rent + (annualRiseOfRent * year) > monthlyInstallment)) {
                double diff = ((rent + (annualRiseOfRent * year) - monthlyInstallment)) * 12;
                savingsWhenRentGreaterThanEMI = savingsWhenRentGreaterThanEMI + InterestCalculator.calculateCompoundInterest(diff,
                        investmentRateOfInterest, (years - year), false);

                System.out.println("Diff " + year + " " + diff);
                System.out.println("Saving for year " + year + " " + savingsWhenRentGreaterThanEMI);
            } else {
                double diff = (monthlyInstallment - (rent + (annualRiseOfRent * year))) * 12;
                savingsWhenEMIGreaterThanRent = savingsWhenEMIGreaterThanRent + InterestCalculator.calculateCompoundInterest(diff,
                        investmentRateOfInterest, (years - year), false);
            }
        }
        System.out.println("Savings when EMI greater than Rent" + savingsWhenEMIGreaterThanRent);
        System.out.println("Savings When Rent greater than EMI" + savingsWhenRentGreaterThanEMI);
        return savingsWhenRentGreaterThanEMI + "-" + savingsWhenEMIGreaterThanRent;
    }


}
