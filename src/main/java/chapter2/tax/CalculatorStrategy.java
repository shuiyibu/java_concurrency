package chapter2.tax;



@FunctionalInterface
public interface CalculatorStrategy {

    double calculate(double salary, double bonus);
}
