package com.company;

import java.util.Objects;

public class Fraction {

    private int denominator;
    private int numerator;

    public Fraction() {
        this(0, 1);
    }

    public Fraction(int number) {
        this(number, 1);
    }

    public Fraction(int numerator, int denominator) throws IllegalArgumentException {
        if(denominator == 0) {
            throw new IllegalArgumentException();
        }
        this.numerator = numerator;
        this.denominator = denominator;
        if((this.numerator < 0 && this.denominator < 0) || (this.denominator < 0 && this.numerator > 0))  {
            this.numerator *= -1;
            this.denominator *= -1;
        }
    }

    public double toDouble() {
        return (double)this.numerator / (double)this.denominator;
    }

    public Fraction add(Fraction f) {
        int thisNumerator = this.numerator * f.denominator;
        int thatNumerator = f.numerator * this.denominator;
        return new Fraction(
                thisNumerator + thatNumerator,
                this.denominator * f.denominator
        ).toLowestTerms();
    }

    public Fraction subtract(Fraction f) {
        int thisNumerator = this.numerator * f.denominator;
        int thisDenominator = this.denominator * f.denominator;
        int thatNumerator = f.numerator * this.denominator;
        int thatDenominator = f.denominator * this.denominator;
        return new Fraction(
                thisNumerator - thatNumerator,
                thisDenominator
        ).toLowestTerms();
    }

    public Fraction multiply(Fraction f) {
        return new Fraction(
                this.numerator * f.numerator,
                this.denominator * f.denominator
        ).toLowestTerms();
    }

    public Fraction divide(Fraction f) throws IllegalArgumentException {
        var reciprocal = new Fraction(f.denominator, f.numerator);
        return this.multiply(reciprocal).toLowestTerms();
    }

    public static Fraction toLowestTerms(Fraction f) {
        int gcd = gcd(f.numerator, f.denominator);
        if(gcd != 0) {
            return new Fraction(f.numerator / gcd, f.denominator / gcd);
        } else {
            return f;
        }
    }

    public Fraction toLowestTerms() {
        Fraction f = toLowestTerms(this);
        this.numerator = f.numerator;
        this.denominator = f.denominator;
        return this;
    }

    public static int gcd(int numerator, int denominator) {
        while(numerator != 0 && denominator != 0) {
            int remainder = numerator % denominator;
            numerator = denominator;
            denominator = remainder;
        }
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public int getNumerator() {
        return numerator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fraction otherFraction = ((Fraction) o);
        // get lowest terms without mutating the compared fractions
        Fraction f1 = Fraction.toLowestTerms(this);
        Fraction f2 = Fraction.toLowestTerms(otherFraction);
        return f1.denominator == f2.denominator &&
                f1.numerator == f2.numerator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(denominator, numerator);
    }

    @Override
    public String toString() {
        return String.format("%d/%d", this.numerator, this.denominator);
    }
}
