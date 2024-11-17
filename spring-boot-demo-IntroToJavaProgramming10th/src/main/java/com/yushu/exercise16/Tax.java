package com.yushu.exercise16;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

class Tax {
    public static final int SINGLE_FILER = 1;
    public static final int MARRIED_JOINTLY = 2;
    public static final int MARRIED_SEPARATELY = 3;
    public static final int HEAD_OF_HOUSEHOLD = 4;
    private int filingStatus = 1;
    private int[][] brackets = new int[][]{{27050, 65550, 136750, 297350}, {45200, 109250, 166500, 297350}, {22600, 54625, 83250, 148675}, {36250, 93650, 151650, 297350}};
    private double[] rates = new double[]{0.15, 0.275, 0.305, 0.355, 0.391};
    private double taxableIncome = 100000.0;

    public Tax() {
    }

    public Tax(int filingStatus, int[][] brackets, double[] rates, double taxableIncome) {
        this.filingStatus = filingStatus;
        this.brackets = brackets;
        this.rates = rates;
        this.taxableIncome = taxableIncome;
    }

    public void setBrackets(int[][] brackets) {
        this.brackets = brackets;
    }

    public void setRates(double[] rates) {
        this.rates = rates;
    }

    public double getTaxableIncome() {
        return this.taxableIncome;
    }

    public void setTaxableIncome(double taxableIncome) {
        this.taxableIncome = taxableIncome;
    }

    public int getFilingStatus() {
        return this.filingStatus;
    }

    public void setFilingStatus(int filingStatus) {
        this.filingStatus = filingStatus;
    }

    public double findTax() {
        double tax = 0.0;
        if (this.taxableIncome <= (double)this.brackets[this.filingStatus][0]) {
            return this.taxableIncome * this.rates[0];
        } else {
            tax = (double)this.brackets[this.filingStatus][0] * this.rates[0];

            int i;
            for(i = 1; i < this.brackets[0].length; ++i) {
                if (!(this.taxableIncome > (double)this.brackets[this.filingStatus][i])) {
                    tax += (this.taxableIncome - (double)this.brackets[this.filingStatus][i - 1]) * this.rates[i];
                    break;
                }

                tax += (double)(this.brackets[this.filingStatus][i] - this.brackets[this.filingStatus][i - 1]) * this.rates[i];
            }

            if (i == this.brackets[0].length && this.taxableIncome > (double)this.brackets[this.filingStatus][i - 1]) {
                tax += (this.taxableIncome - (double)this.brackets[this.filingStatus][i - 1]) * this.rates[i];
            }

            return tax;
        }
    }
}
