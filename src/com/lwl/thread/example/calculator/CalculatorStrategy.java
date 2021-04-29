package com.lwl.thread.example.calculator;

/**
 * @author liwenlong
 * @date 2021-04-29 20:25
 */
@FunctionalInterface
public interface CalculatorStrategy {

    double calculate(double b, double s);
}
