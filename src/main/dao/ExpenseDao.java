package main.dao;

import main.dao.dto.ExpenseDto;
import main.exceptions.DAOException;

import java.util.List;

public interface ExpenseDao {
    void insert(ExpenseDto expenseDto);
    List<ExpenseDto> getAll() throws DAOException;
    void update(ExpenseDto expenseDto);
    void delete(Integer id);
}
