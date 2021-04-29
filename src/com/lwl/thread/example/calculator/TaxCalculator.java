package com.lwl.thread.example.calculator;

/**
 * @author liwenlong
 * @date 2021-04-29 20:26
 */
public class TaxCalculator {
    private double salary;
    private double bonus;
    private CalculatorStrategy calculatorStrategy;


    public TaxCalculator(double salary, double bonus, CalculatorStrategy calculatorStrategy) {
        this.salary = salary;
        this.bonus = bonus;
        this.calculatorStrategy = calculatorStrategy;
    }

    public double calculate() {
        return calculatorStrategy.calculate(salary, bonus);
    }
}
