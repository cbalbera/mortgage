package com.mortgage.mortgage;

import javax.persistence.*;

@Entity
@Table
public class mortgage {

    // disabling backend ID generation as frontend handles creation of unique ID
    /*
    @Id
    @SequenceGenerator(
            name = "mortgage_sequence",
            sequenceName = "mortgage_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "mortgage_sequence"
    )
    */

    @Id
    private long id;
    private int principal;
    private int down_pmt;
    private double annual_rate;
    private int amort_years;
    private int loan_term;
    @Transient
    private double[][] amort_schedule;

    @Override
    public String toString() {
        return "mortgage{" +
                "id=" + id +
                ", principal=" + principal +
                ", down_pmt=" + down_pmt +
                ", annual_rate=" + annual_rate +
                ", amort_years=" + amort_years +
                ", loan_term=" + loan_term +
                '}';
    }

    // default constructor - used by Hibernate as per top answer in this thread: https://stackoverflow.com/questions/25452018/hibernate-annotations-no-default-constructor-for-entity?rq=1
    public mortgage() {}

    // constructor with all values populated
    public mortgage(long id, int principal, int down_pmt, double annual_rate, int amort_years, int loan_term) {
        this.id = id;
        this.principal = principal;
        this.down_pmt = down_pmt;
        this.annual_rate = annual_rate;
        this.amort_years = amort_years;
        this.loan_term = loan_term;
    }

    //TODO: create constructor with down_pmt = 0

    public long getID() {
        return id;
    }

    public int getPrincipal() {
        return principal;
    }

    public int getDown_pmt() {
        return down_pmt;
    }

    public double getAnnual_rate() {
        return annual_rate;
    }

    public int getAmort_years() {
        return amort_years;
    }

    public int getLoan_term() {
        return loan_term;
    }

    public double[][] get_amort_schedule() {
        return monthly_amort_schedule(principal,down_pmt,annual_rate,amort_years*12,loan_term*12);
    }

    public double get_principal_at_date(int month) {
        return amort_schedule[month-1][3];
    }

    public static double monthly_mortgage_pmt(int principal, int down_pmt, double annual_rate, int amort_years) {
        //TODO: build in redundancy for "garbage" values such as negatives in any input
        double monthly_interest_rate = annual_rate / 12;
        // formula: monthly pmt = principal * [rate * (1+rate)^amort_term] / [(1+rate)^amort_term - 1]
        double interest_component = Math.pow((1+monthly_interest_rate),amort_years*12);
        return (principal - down_pmt) * (monthly_interest_rate * interest_component) / (interest_component - 1);
    }

    public static double[][] yearly_amort_schedule(int principal, int down_pmt, double annual_rate, int amort_years, int loan_term) {
        // form: double of length 4 (vertical) amort_years (horizontal)
        // in each amort_year, array includes: starting balance, interest, principal, ending balance
        double[][] output = new double[amort_years][4];
        double monthly_pmt = monthly_mortgage_pmt(principal,down_pmt,annual_rate, amort_years);
        //System.out.println("annual pmt is "+monthly_pmt*12);
        double starting_principal = principal-down_pmt;
        double monthly_interest_rate = annual_rate / 12;
        for (int i = 0; i < amort_years; i++) {
            double[] annual_values = new double[4];
            //System.out.println("starting principal appended at "+starting_principal);
            annual_values[0] = starting_principal;
            double annual_interest = 0, annual_principal = 0;
            for (int j = 0; j < 12; j++) {
                double monthly_interest = starting_principal * monthly_interest_rate;
                double monthly_principal = monthly_pmt - monthly_interest;
                annual_interest += monthly_interest;
                annual_principal += monthly_principal;
                starting_principal -= monthly_principal;
            }

            // account for balloon payment, if applicable
            if (loan_term != amort_years && i == loan_term-1) {
                annual_principal += starting_principal;
                starting_principal = 0;
            }

            //System.out.println("annual interest appended at "+annual_interest);
            annual_values[1] = annual_interest;
            //System.out.println("annual principal appended at "+annual_principal);
            annual_values[2] = annual_principal;
            //System.out.println("ending principal appended at "+starting_principal);
            annual_values[3] = starting_principal;
            output[i] = annual_values;
        }
        return output;
    }

    public static double[][] monthly_amort_schedule(int principal, int down_pmt, double annual_rate, int amort_months, int loan_term) {
        // form: double of length 4 (vertical) amort_months (horizontal)
        // in each amort_month, array includes: starting balance, interest, principal, ending balance
        double[][] output = new double[amort_months][4];
        double monthly_pmt = monthly_mortgage_pmt(principal, down_pmt ,annual_rate, amort_months / 12);
        //System.out.println("monthly pmt is "+monthly_pmt);
        double starting_principal = principal-down_pmt;
        double monthly_interest_rate = annual_rate / 12;
        for (int i = 0; i < amort_months; i++) {
            double[] monthly_values = new double[4];
            //System.out.println("starting principal appended at "+starting_principal);
            monthly_values[0] = starting_principal;
            double monthly_interest = starting_principal * monthly_interest_rate;
            double monthly_principal = monthly_pmt - monthly_interest;
            starting_principal -= monthly_principal;

            // account for balloon payment, if applicable
            if (loan_term != amort_months && i == loan_term-1) {
                monthly_principal += starting_principal;
                starting_principal = 0;
            }
            //System.out.println("monthly interest appended at "+monthly_interest);
            monthly_values[1] = monthly_interest;
            //System.out.println("monthly principal appended at "+monthly_principal);
            monthly_values[2] = monthly_principal;
            //System.out.println("ending principal appended at "+starting_principal);
            monthly_values[3] = starting_principal;
            output[i] = monthly_values;
        }
        return output;
    }

    public double get_monthly_principal_payment(int month) {
        return amort_schedule[month-1][2];
    }

    public double get_monthly_interest_payment(int month) {
        return amort_schedule[month-1][1];
    }

    public double total_interest_payment(int loan_term) {
        int sum = 0;
        for (int i = 0; i < loan_term; i++) {
            sum += amort_schedule[i][2];
        }
        return sum;
    }

    public double home_equity_percentage(mortgage amort, int month, int home_value) {
        double principal_balance = amort.get_principal_at_date(month);
        return home_value / principal_balance;
    }

    public static double home_value(int purchase_price, double[] expected_annual_growth, int months) {
        double output = purchase_price;
        double term_growth = .03;
        for (int i = 0; i < months; i++) {
            int year = (int) Math.floor(i/12);
            //System.out.println("i (month) is "+i+" and year is "+year);
            if (year >= expected_annual_growth.length) {
                output *= 1 + (term_growth/12);
            } else if (year == expected_annual_growth.length) {
                term_growth = expected_annual_growth[year]/12;
                output *= 1 + term_growth/12;
            } else {
                output *= 1 + expected_annual_growth[year]/12;
            }
            //System.out.println("value is now "+output);
        }
        return Math.round(output);
    }
}
