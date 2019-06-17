package com.company;

import java.util.Scanner;
import java.util.regex.Pattern;

public class FractionCalculator {

    private Scanner input;

    public FractionCalculator() {
        this.input = new Scanner(System.in);
    }

    public boolean validFraction(String fraction) {
        boolean validFractionPattern = matchesRegex("^-*\\d+/-*\\d+$", fraction);
        return validFractionPattern && !fraction.split("/")[1].equals("0");
    }

    public Fraction getFraction() {
        do {
            System.out.print("Please enter a fraction (a/b) or integer(a) where a and b are integers and b is not zero: ");
            String response = this.input.next();
            if(response.toLowerCase().equals("q")) {
                this.quit();
            }
            if(response.contains("/")) {
                if(validFraction(response)) {
                    String[] quotients = response.split("/");
                    int numerator = Integer.parseInt(quotients[0]);
                    int denominator = Integer.parseInt(quotients[1]);
                    return new Fraction(numerator,denominator);
                } else {
                    System.out.println("Invalid fraction");
                }
            } else {
                if(matchesRegex("^-*\\d+$", response)) {
                    return new Fraction(Integer.parseInt(response));
                } else {
                    System.out.println("Invalid fraction");
                }
            }
        } while(true);
    }

    public String getOperation() {
        do {
            System.out.print("Please enter an operation (+, -, *, /, = or Q to quit):");
            String response = this.input.next();
            switch (response.toLowerCase()) {
                case "+": return "+";
                case "-": return "-";
                case "*": return "*";
                case "/": return "/";
                case "=": return "=";
                case "q": this.quit();
                default:
                    System.out.println("Invalid input");
            }
        } while(true);
    }

    public void start() {
        do {
            System.out.println("This program is a fraction calculator");
            System.out.println("It will add, subtract, multiply and divide fractions until you enter Q to quit");
            Fraction f1 = this.getFraction();
            String operation = this.getOperation();
            Fraction f2 = this.getFraction();
            Fraction result = null;
            boolean isEqual = false;
            switch(operation) {
                // I really hate switches
                case "+": result = f1.add(f2); break;
                case "-": result = f1.subtract(f2); break;
                case "*": result = f1.multiply(f2); break;
                case "/": result = f1.divide(f2); break;
                case "=": isEqual = f1.equals(f2); break;
            }
            var output = String.format("%s %s %s ", f1, operation, f2);
            if(result != null) {
                output += String.format("= %s", result);
            } else {
                output += "is " + isEqual;
            }
            System.out.println(output);
        } while(true);
    }

    private static boolean matchesRegex(String regex, String test) {
        return Pattern.compile(regex).matcher(test).matches();
    }

    public void close() {
        this.input.close();
    }

    public void quit() {
        this.close();
        System.exit(0);
    }
}
