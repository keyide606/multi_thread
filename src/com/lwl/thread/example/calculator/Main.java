package com.lwl.thread.example.calculator;

/**
 * @author liwenlong
 * @date 2021-04-29 20:28
 */
public class Main {
    public static void main(String[] args) {
        // 策略模式简单实现
        // 类似java中runnable接口,TaxCalculator类型Thread类,其成员有CalculatorStrategy
        CalculatorStrategy calculatorStrategy = (s, b) -> s * 0.1 + b * 0.15;
        TaxCalculator taxCalculator = new TaxCalculator(10000, 3000, calculatorStrategy);
        System.out.println(taxCalculator.calculate());
    }
}
