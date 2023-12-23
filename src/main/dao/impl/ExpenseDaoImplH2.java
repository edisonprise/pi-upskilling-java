package main.dao.impl;

import main.dao.ExpenseDao;
import main.dao.dto.ExpenseDto;
import main.entities.Expense;
import main.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDaoImplH2 implements ExpenseDao {

    private static final String INSERT_INTO_EXPENSE =
            "INSERT INTO expense (amount, category_id, date) VALUES (?, ?, ?)";
    private static final String GET_ALL_EXPENSES = "SELECT * FROM expense";

    private final Connection connection;

    public ExpenseDaoImplH2(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(ExpenseDto expenseDto) {
        try(PreparedStatement statement = connection.prepareStatement(INSERT_INTO_EXPENSE)) {
            Expense expense = mapDtoToExpense(expenseDto);
            statement.setDouble(1, expense.getAmount());
            statement.setInt(2, expense.getCategoryId());
            statement.setString(3, expense.getDate());
            int affectedRows = statement.executeUpdate();
            if(affectedRows == 0) {
                throw new DAOException("Error al insertar el gasto, ninguna fila fue afectada");
            }
       } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

    }

    private Expense mapDtoToExpense(ExpenseDto expenseDto) {
        Expense expense = new Expense();
        expense.setAmount(expenseDto.getAmount());
        expense.setCategoryId(expenseDto.getCategoryId());
        expense.setDate(expenseDto.getDate());
        return expense;
    }

    @Override
    public List<ExpenseDto> getAll() throws DAOException {
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_EXPENSES)){
            ResultSet resultSet = statement.executeQuery();
            List<ExpenseDto> expenses = new ArrayList<>();
            // Iteramos el ResultSet para agregar el gasto a la lista
            // y mientras agregamos, realizamos el mapeo de cada item
            while (resultSet.next()){
                expenses.add(mapResultSetToExpenseDto(resultSet));
            }
            return expenses;
        } catch (SQLException e) {
            throw new DAOException("Error al obtener la lista de gastos", e);
        }

    }

    private ExpenseDto mapResultSetToExpenseDto(ResultSet resultSet) throws SQLException {
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setAmount(resultSet.getInt("amount"));
        expenseDto.setDate(resultSet.getString("date"));
        expenseDto.setCategoryId(resultSet.getInt("category_id"));
        return expenseDto;
    }

    @Override
    public void update(ExpenseDto expenseDto) {

    }

    @Override
    public void delete(Integer id) {

    }
}
