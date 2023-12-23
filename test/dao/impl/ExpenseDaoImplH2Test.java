package dao.impl;

import main.dao.ExpenseDao;
import main.dao.dto.ExpenseDto;
import main.exceptions.DAOException;
import main.dao.impl.ExpenseDaoImplH2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpenseDaoImplH2Test {
    @Mock
    private Connection connectionMock;
    @Mock
    private PreparedStatement preparedStatementMock;
    private final ResultSet resultSetMock = mock(ResultSet.class);
    private ExpenseDao expenseDao;
    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        expenseDao = new ExpenseDaoImplH2(connectionMock);
    }
    @Test
    @DisplayName("Cuando inserto un dto con valores correctos, " +
    "entonces el registro se inserta correctamente")
    void insert_ShouldInsertExpense_WhenValidExpenseDto() throws SQLException {
        //GIVEN
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setAmount(100.0);
        expenseDto.setDate("2023-22-12");
        expenseDto.setCategoryId(2);

        when(preparedStatementMock.executeUpdate()).thenReturn(1);

        //WHEN
        expenseDao.insert(expenseDto);


        //THEN
        verify(preparedStatementMock).setDouble(1, expenseDto.getAmount());
        verify(preparedStatementMock).setInt(2, expenseDto.getCategoryId());
        verify(preparedStatementMock).setString(3, expenseDto.getDate());
        verify(preparedStatementMock, times(1)).executeUpdate();
    }
    @Test
    void getAll_shouldreturnListOfExpenseDto_WhenDataBaseHasData() throws DAOException, SQLException {
       //GIVEN
        List<ExpenseDto> expectedList;
        expectedList = List.of(
                new ExpenseDto(100.0, 2, "2023-22-12"),
                new ExpenseDto(200.0, 1, "2023-22-12")
        );

        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true, true, false);
        when(resultSetMock.getInt("id")).thenReturn(2, 1);
        when(resultSetMock.getDouble("amount")).thenReturn(100.0, 200.0);
        when(resultSetMock.getInt("category_id")).thenReturn(2);
        when(resultSetMock.getString("date")).thenReturn("2023-22-12");

        //WHEN
        List<ExpenseDto> resultList = expenseDao.getAll();

        //THEN
        verify(preparedStatementMock).executeQuery();
        verify(resultSetMock, times(2)).getInt("amount");
        verify(resultSetMock, times(2)).getString("date");
        verify(resultSetMock, times(2)).getInt("category_id");
        assertEquals(expectedList.size(), resultList.size());
    }
}