package main.interfaces;

import main.dao.dto.ExpenseDto;
import main.entities.Expense;

import java.util.List;

public interface ExpenseCalculator{
    double calculateExpense(Expense expense);
    double calculateTotalExpense(List<ExpenseDto> expenses);
}
