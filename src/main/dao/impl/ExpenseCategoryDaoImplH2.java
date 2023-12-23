package main.dao.impl;

import main.dao.ExpenseCategoryDao;
import main.dao.dto.ExpenseCategoryDto;
import main.entities.ExpenseCategory;
import main.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpenseCategoryDaoImplH2 implements ExpenseCategoryDao {
    private static final String INSERT_INTO_EXPENSE_CATEGORY = "INSERT INTO expensecategory (name) VALUES (?)";
    private static final String GET_CATEGORY_BY_NAME = "SELECT * FROM expensecategory WHERE name = ?";
    private final Connection connection;


    public ExpenseCategoryDaoImplH2(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(ExpenseCategoryDto expenseCategoryDto) throws DAOException {
        try(PreparedStatement statement = connection.prepareStatement(INSERT_INTO_EXPENSE_CATEGORY)) {
            //Mapeo de dto a entidad
            ExpenseCategory expenseCategory = mapDtoToExpensecategory(expenseCategoryDto);

            statement.setString(1, expenseCategory.getName());
            int affectedRows = statement.executeUpdate();
            // Validamos si el resultado de la ejecucion del statement no devuelve filas afectadas,
            // entonces hubo un error al insertar en base de datos
            if(affectedRows == 0) {
                throw new DAOException("Error al insertar el gasto, ninguna fila fue afectada");
            }
        } catch (DAOException | SQLException e) {
            assert e instanceof SQLException;
            throw new DAOException("Error al insertar el gasto", (SQLException) e);
        }

    }

    @Override
    public ExpenseCategory getCategoryByName(String name) throws DAOException {
       try (PreparedStatement statement = connection.prepareStatement(GET_CATEGORY_BY_NAME)){
           statement.setString(1, name);
           ResultSet resultSet = statement.executeQuery();
           if(resultSet.next()){
               return new ExpenseCategory(
                       resultSet.getInt("id"),
                       resultSet.getString("name")
               );

           }
           return null;
       } catch(SQLException e) {
           throw new DAOException("Error al obtener el gasto por ID", e);
       }
    }
    private ExpenseCategory mapDtoToExpensecategory(ExpenseCategoryDto expenseCategoryDto){
        ExpenseCategory expenseCategory = new ExpenseCategory();
        expenseCategory.setName(expenseCategoryDto.getName());
        return expenseCategory;
    }
}

