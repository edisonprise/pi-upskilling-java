package main.interfaces;

import main.exceptions.InvalidExpenseException;

public class ExpenseAmountValidatorImpl implements ExpenseAmountValidator {
    @Override
    public boolean notValidAmount(double amount) throws InvalidExpenseException {
        if (amount < 0) {
         throw new InvalidExpenseException("El monto debe ser igual o mayor a 0");
        }
        return false;
    }
}
