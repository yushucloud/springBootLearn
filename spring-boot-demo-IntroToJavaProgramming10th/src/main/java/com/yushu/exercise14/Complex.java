package com.yushu.exercise14;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

class Complex implements Cloneable {
    private double a = 0.0;
    private double b = 0.0;

    public Complex() {
    }

    Complex(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public Complex(double a) {
        this.a = a;
    }

    public double getA() {
        return this.a;
    }

    public double getB() {
        return this.b;
    }

    public Complex add(Complex secondComplex) {
        double newA = this.a + secondComplex.getA();
        double newB = this.b + secondComplex.getB();
        return new Complex(newA, newB);
    }

    public Complex subtract(Complex secondComplex) {
        double newA = this.a - secondComplex.getA();
        double newB = this.b - secondComplex.getB();
        return new Complex(newA, newB);
    }

    public Complex multiply(Complex secondComplex) {
        double newA = this.a * secondComplex.getA() - this.b * secondComplex.getB();
        double newB = this.b * secondComplex.getA() + this.a * secondComplex.getB();
        return new Complex(newA, newB);
    }

    public Complex divide(Complex secondComplex) {
        double newA = (this.a * secondComplex.getA() + this.b * secondComplex.getB()) / (Math.pow(secondComplex.getA(), 2.0) + Math.pow(secondComplex.getB(), 2.0));
        double newB = (this.b * secondComplex.getA() - this.a * secondComplex.getB()) / (Math.pow(secondComplex.getA(), 2.0) + Math.pow(secondComplex.getB(), 2.0));
        return new Complex(newA, newB);
    }

    public double abs() {
        return Math.sqrt(this.a * this.a + this.b * this.b);
    }

    public String toString() {
        return this.b != 0.0 ? this.a + " + " + this.b + "i" : String.valueOf(this.a);
    }

    public double getRealPart() {
        return this.a;
    }

    public double getImaginaryPart() {
        return this.b;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException var2) {
            return null;
        }
    }
}
