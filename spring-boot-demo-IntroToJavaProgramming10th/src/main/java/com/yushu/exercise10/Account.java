package com.yushu.exercise10;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.Date;

class Account {
    private int id;
    private double balance;
    private static double annualInterestRate;
    private Date dateCreated;

    public Account() {
        this.dateCreated = new Date();
    }

    public Account(int newId, double newBalance) {
        this.id = newId;
        this.balance = newBalance;
        this.dateCreated = new Date();
    }

    public int getId() {
        return this.id;
    }

    public double getBalance() {
        return this.balance;
    }

    public static double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public void setId(int newId) {
        this.id = newId;
    }

    public void setBalance(double newBalance) {
        this.balance = newBalance;
    }

    public static void setAnnualInterestRate(double newAnnualInterestRate) {
        annualInterestRate = newAnnualInterestRate;
    }

    public double getMonthlyInterest() {
        return this.balance * (annualInterestRate / 1200.0);
    }

    public Date getDateCreated() {
        return this.dateCreated;
    }

    public void withdraw(double amount) {
        this.balance -= amount;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }
}
