package com.personal;

import org.junit.Test;

import java.util.List;
import java.util.Map;

public class AssetValueCalculatorTest {

    //years of investment
    Double years = 25.0;

    //Real Estate parameters
    Double currentAssetValue = 300000.0;
    Double downPayment = 32000.0;
    Double houseValueAppreciationPercentage = 5.0;
    Double monthlyBills = 300.0;
    Double propertyTaxPercentage = 1.345;
    Double bankInterestRate = 3.5;
    Double others = 0.0;

    //Non-Real estate investments
    Double rateOfInterest = 20.0;
    Double rent = 1469.0;
    Double monthlyBillForRentedHouse = 250.0;
    Double rentAmountWithBills = rent + monthlyBillForRentedHouse;
    Double annualRiseOfRent = 50.0;


    @Test
    public void compareRealEstateVsNonRealEstateWithCompoundedSavings() {
        AssetValueCalculator.calculateRealVsNonRealAssetDifference(currentAssetValue, downPayment, years,
                houseValueAppreciationPercentage, monthlyBills, propertyTaxPercentage, bankInterestRate, others, rent, annualRiseOfRent, rateOfInterest, monthlyBillForRentedHouse);
    }

    @Test
    public void calculateNetAssetValueWhilePayingInterestLoan() {
        System.out.println("\nValue left with investor after years: " +
                AssetValueCalculator.calculateNetAssetValueFixedSavings
                        (currentAssetValue, downPayment, years, houseValueAppreciationPercentage, monthlyBills, propertyTaxPercentage, bankInterestRate, others));
    }

    @Test
    public void shouldCalculateTotalRentPaid() {
        Double investments = InterestCalculator.calculateCompoundInterest(downPayment, rateOfInterest, years, false);
        Double rentPaid = AssetValueCalculator.calculateRentPaidValue(rentAmountWithBills, annualRiseOfRent, 10.0);
        Double nonRealEstateValue = investments - rentPaid;
        System.out.println("Non-real estate value : " +
                nonRealEstateValue);
    }

    @Test
    public void calculateLoanRepaidToBankWithInterest() {
        System.out.println("Amount repaid to bank with interest " + AssetValueCalculator.calculateLoanRepaidToBank
                (currentAssetValue - downPayment, currentAssetValue, bankInterestRate, years, houseValueAppreciationPercentage));
    }

    @Test
    public void calculateNetAssetWithNonRealEstateInvestments() {
        Double investments = InterestCalculator.calculateRecurringDeposit(rentAmountWithBills, rateOfInterest, years);
        Double rentPaid = AssetValueCalculator.calculateRentPaidValue(rentAmountWithBills, annualRiseOfRent, years);
        System.out.println("Net asset with non-2SWrealestate : " +
                (investments - rentPaid));

    }

    @Test
    public void compareRealEstateVsNonRealEstate() {
        Double realEstateValue = AssetValueCalculator.calculateNetAssetValueFixedSavings
                (currentAssetValue, downPayment, years, houseValueAppreciationPercentage, monthlyBills, propertyTaxPercentage, bankInterestRate, others);

        Double investments = InterestCalculator.calculateCompoundInterest(downPayment, rateOfInterest, years, false);
        Double rentPaid = AssetValueCalculator.calculateRentPaidValue(rentAmountWithBills, annualRiseOfRent, years);
        Double nonRealEstateValue = investments - rentPaid;

        Map<String, List<Double>> map = InterestCalculator.calculateTaxPaidDetails((currentAssetValue - downPayment), currentAssetValue, bankInterestRate, years, houseValueAppreciationPercentage);
        Double monthlyInstallment = map.get("monthly_installment").get(0);

        String amount = AssetValueCalculator.calculateInvestmentOverEMIAndRentDifference(monthlyInstallment, rent,
                annualRiseOfRent, years, rateOfInterest);

        realEstateValue = realEstateValue + Double.valueOf(amount.split("-")[0]);
        nonRealEstateValue = nonRealEstateValue + Double.valueOf(amount.split("-")[1]);

        System.out.println("Realestate NET asset value " + realEstateValue);
        System.out.println("\n****************************\n");
        System.out.println("Amount earned through investments after " + years + " at " + rateOfInterest + "% : " + investments);
        System.out.println("\nRent paid with bills : " + rentPaid);
        System.out.println("\nNon-Realestate NET asset value " + nonRealEstateValue);

        if (nonRealEstateValue > realEstateValue) {
            System.out.println("\n***********************************\nNon-realestate will leave you with additional wealth of " + (nonRealEstateValue - realEstateValue) +
                    " in " + years + " years. \n\n\n**** NON-REALESTATE investments recommended ****");
        } else if (nonRealEstateValue < realEstateValue) {
            System.out.println("\n***********************************\nRealestate will leave you with additional wealth of " + (realEstateValue - nonRealEstateValue) +
                    " in " + years + " years. \n\n\n**** REALESTATE investments recommended ****");
        }
        System.out.println();
    }

    @Test
    public void calculateCompoundInterest() {
        System.out.println("Compound interest : " + InterestCalculator.calculateCompoundInterest(391405.0, 16.0, 10.0, false));
    }


}

