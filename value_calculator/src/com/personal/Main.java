package com.personal;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        System.err.println("House Purchase Value : ");
        Scanner scanner = new Scanner(System.in);

        Double homePurchaseAmount = Double.valueOf(scanner.next());

        System.err.println("House Down Payment : ");
        Double downPayment = Double.valueOf(scanner.next());

        System.err.println("Property tax per year : ");
        Double propertyTaxPerYear = Double.valueOf(scanner.next());

        System.err.println("Bills Paid per month : ");
        Double billsPerMonth = Double.valueOf(scanner.next());

        System.out.println("Home rate appreciation percentage");
        Double valueAppreciationPercent = Double.valueOf(scanner.next());

        System.out.println("home loan taken for years : " );
        Double homeLoanYears = Double.valueOf(scanner.next());

        System.out.println("Bank Interest Rate : " );
        Double rateOfBankInterest = Double.valueOf(scanner.next());

        System.err.println("Home Purchase Amount : " + homePurchaseAmount);
        System.err.println("House Down Payment : " + downPayment);
        System.err.println("Property tax per year :" + propertyTaxPerYear);
        System.err.println("Home rate appreciation percentage : " + valueAppreciationPercent);
        System.err.println("Bills Paid per month : " + billsPerMonth);
        System.err.println("home loan taken for years : " + homeLoanYears);
        System.err.println("Bank Interest Rate : " + rateOfBankInterest);

//        System.err.println("Value after 20 years : "+ calculateCompoundInterest(homePurchaseAmount, rateOfBankInterest, homeLoanYears));


    }


}
