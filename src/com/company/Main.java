package com.company;

public class Main {

    public static void main(String[] args) {
        try {
            var fca = new FractionCalculatorAdvanced();
            fca.start();
        } catch(IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
        }
    }
}
