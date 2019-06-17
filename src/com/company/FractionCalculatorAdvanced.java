package com.company;

import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class FractionCalculatorAdvanced {
    private Scanner input;

    private enum OpKeys {
        FIRSTOPERAND, SECONDOPERAND, OPERATOR
    }

    public FractionCalculatorAdvanced() {
        this.input = new Scanner(System.in);
    }

    public void close() {
        this.input.close();
    }

    public Fraction getFraction(String fraction) {
        if(fraction.contains("/")) {
            String[] quotients = fraction.split("/");
            return new Fraction(
                    Integer.parseInt(quotients[0]),
                    Integer.parseInt(quotients[1])
            );
        } else {
            return new Fraction(Integer.parseInt(fraction));
        }
    }

    public String performCalculation(HashMap<OpKeys, String> operation) {
        Fraction f1 = this.getFraction(operation.get(OpKeys.FIRSTOPERAND));
        Fraction f2 = this.getFraction(operation.get(OpKeys.SECONDOPERAND));
        switch(operation.get(OpKeys.OPERATOR)) {
            case "+":
                return f1.add(f2).toString();
            case "-":
                return f1.subtract(f2).toString();
            case "*":
                return f1.multiply(f2).toString();
            case "/":
                return f1.divide(f2).toString();
            case "=":
                return Boolean.toString(f1.equals(f2));
            // technically can't reach the default due to the regex, but the compiler doesn't know that
            default:
                return "Invalid operation";
        }
    }

    public HashMap getOperation() {
        do {
            System.out.print("Enter an operation or Q to quit");
            String response = this.input.nextLine().trim();
            if(response.toLowerCase().equals("q")) {
                this.quit();
            }
            if(matchesRegex("^((-*\\d+\\/-*\\d+)|\\d+)\\s[+-/*=]{1}\\s((-*\\d+\\/-*\\d+)|\\d+)$", response)) {
                var tokenizer = new StringTokenizer(response);
                var operation = new HashMap<OpKeys, String>();
                int index = 0;
                while(tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();
                    if(index % 2 == 0) {
                        OpKeys key = index == 0 ? OpKeys.FIRSTOPERAND : OpKeys.SECONDOPERAND;
                        operation.put(key, token);
                    } else {
                        operation.put(OpKeys.OPERATOR, token);
                    }
                    index += 1;
                }
                return operation;
            } else {
                System.out.println("Invalid operation, must be in the form \"[FRAC] [OPERATION] [FRAC]\"");
            }
        } while(true);
    }

    public void start() {
        System.out.println("This is a fraction calculator");
        System.out.println("It will add, subtract, multiply and divide fractions until you hit Q to quit");
        System.out.println("Valid operations are in the form \"[FRAC] [OPERATION] [FRAC]\"");
        System.out.println("The spaces around [OPERATION] are required");
        System.out.println("[FRAC] can either be a single integer or two integers separated by a \"/\"");
        System.out.println("[OPERATION] can be +, -, *, / or =");
        do {
            System.out.println(performCalculation(getOperation()));
        } while(true);
    }

    public void quit() {
        this.close();
        System.exit(0);
    }

    private static boolean matchesRegex(String regex, String test) {
        return Pattern.compile(regex).matcher(test).matches();
    }
}
