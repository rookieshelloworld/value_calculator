package com.personal;

import org.junit.Test;

public class AssetValueCalculatorTest {

    //years of investment
    Double years = 20.0;

    //Real Estate parameters
    Double currentAssetValue =350000.0;
    Double downPayment = 60000.0;
    Double houseValueAppreciationPercentage = 4.0;
    Double monthlyBills = 300.0;
    Double propertyTaxPercentage = 1.345;
    Double bankInterestRate = 4.0;
    Double others = 10000.0;

    //Non-Real estate investments
    Double rateOfInterest = 13.0;
    Double rentAmountWithBills = 1700.0;
    Double annualRiseOfRent = 50.0;

    //RD
    Double monthlyEMI = 2000.0;

    @Test
    public void calculateNetAssetValueWhilePayingInterestLoan() {
        System.out.println("\nValue left with investor after years: " +
                AssetValueCalculator.calculateNetAssetValue
                        (currentAssetValue, downPayment, years, houseValueAppreciationPercentage, monthlyBills, propertyTaxPercentage, bankInterestRate, others));
    }

    @Test
    public void calculateExpenditure() {
        System.out.println("\nAmount paid for property tax and bills: " +
                AssetValueCalculator.calculateExpenditure
                        (currentAssetValue, years, houseValueAppreciationPercentage, monthlyBills, propertyTaxPercentage));
    }


    @Test
    public void calculateRecurringDeposit(){
        System.out.println("REcurring deposit Maturity amount : " + AssetValueCalculator.calculateRecurringDeposit(
                monthlyEMI, rateOfInterest, years));
    }

    @Test
    public void shouldCalculatedTotalRentPaid() {
        System.out.println("total rent paid : " +
                AssetValueCalculator.calculateRentPaidValue(rentAmountWithBills, annualRiseOfRent, years));

    }

    @Test
    public void calculateLoanRepaidToBankWithInterest() {
        System.out.println("Amount repaid to bank with interest " + AssetValueCalculator.calculateLoanRepaidToBank
                (currentAssetValue, bankInterestRate, years));
    }

    @Test
    public void calculateFutureAssetValue() {
        System.out.println("Investment calcuated with compound interest : " +
                AssetValueCalculator.calculateCompoundInterest(currentAssetValue, houseValueAppreciationPercentage, years));
    }

    @Test
    public void calculateNetAssetWithNonRealEstateInvestments() {
        Double investments = AssetValueCalculator.calculateRecurringDeposit(rentAmountWithBills, rateOfInterest, years);
        Double rentPaid = AssetValueCalculator.calculateRentPaidValue(rentAmountWithBills, annualRiseOfRent, years);
        System.out.println("Net asset with non-2SWrealestate : " +
                (investments - rentPaid));

    }

    @Test
    public void compareRealEstateVsNonRealEstate() {
        Double realEstateValue = AssetValueCalculator.calculateNetAssetValue
                (currentAssetValue, downPayment, years, houseValueAppreciationPercentage, monthlyBills, propertyTaxPercentage, bankInterestRate, others);

        Double investments = AssetValueCalculator.calculateCompoundInterest(downPayment, rateOfInterest, years);
        Double rentPaid = AssetValueCalculator.calculateRentPaidValue(rentAmountWithBills, annualRiseOfRent, years);
        Double nonRealEstateValue = investments - rentPaid;
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
}

