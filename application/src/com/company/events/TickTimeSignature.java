package com.company.events;

public class TickTimeSignature extends Tick {

    private final int numerator;
    private final int denominator;

    public TickTimeSignature(long number, Type type, int numerator, int denominator) {
        super(number, type);
        this.numerator = numerator;
        this.denominator = denominator;
    }
    public int getNumerator() {
        return numerator;
    }
    public int getDenominator() {
        return denominator;
    }
}