package main.interfaces;

import main.exceptions.InvalidExpenseException;

@FunctionalInterface
public interface ExpenseAmountValidator {
    boolean notValidAmount(double amount) throws InvalidExpenseException;
}
