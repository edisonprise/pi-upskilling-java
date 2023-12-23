import main.config.JdbcConfig;
import main.dao.ExpenseCategoryDao;
import main.dao.ExpenseDao;
import main.dao.dto.ExpenseCategoryDto;
import main.dao.dto.ExpenseDto;
import main.dao.impl.ExpenseCategoryDaoImplH2;
import main.dao.impl.ExpenseDaoImplH2;
import main.entities.ExpenseCategory;
import main.exceptions.DAOException;
import main.exceptions.InvalidExpenseException;
import main.interfaces.ExpenseAmountValidator;
import main.interfaces.ExpenseAmountValidatorImpl;
import main.interfaces.ExpenseCalculator;
import main.interfaces.ExpenseCalculatorImpl;
import util.Utilities;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static int counter = 0;

    public static void main(String[] args) throws InvalidExpenseException {

        Scanner scanner = new Scanner(System.in);

        try (Connection connection = JdbcConfig.getConnection()){

            ExpenseDao expenseDao = new ExpenseDaoImplH2(connection);
            ExpenseCategoryDao expenseCategoryDao = new ExpenseCategoryDaoImplH2(connection);

            Double amount;
            int index = 0;

            ExpenseAmountValidator expenseAmountValidator = new ExpenseAmountValidatorImpl();
            ExpenseCalculator expenseCalculator = new ExpenseCalculatorImpl();

            Map<String, Integer> countCategoryMap = new HashMap<>();
        /*do {
            System.out.print("Ingrese la cantidad de gastos a registrar: ");
            if (scanner.hasNextInt()) {
                cantGastosAIngresar = scanner.nextInt();
            } else {
                System.out.println("Dato erroneo");
            }
        } while (isWrongType);*/
            boolean cutLogicVar;
            System.out.print("Desea cargar un gasto? SI/NO: ");
            String resp = scanner.nextLine();
            cutLogicVar = Objects.equals(resp, "si");


            while(cutLogicVar) {
                ExpenseDto expenseDto = new ExpenseDto();
                ExpenseCategoryDto category = new ExpenseCategoryDto();
                // Input amount
                //double amount;
                System.out.print("Ingrese el monto del gasto " + (index + 1) + ": ");
                amount = scanner.nextDouble();

                if (!expenseAmountValidator.notValidAmount(amount)) {
                    System.out.println("El monto es válido");
                } else {
                    System.out.println("El monto no es válido, inténtelo de nuevo.");
                }

                // Clear the input buffer
                scanner.nextLine();

                // Input category
                System.out.print("Ingrese la categoría del gasto: ");
                String name = scanner.nextLine().toLowerCase().trim();
                category.setName(name);
                expenseCategoryDao.insert(category);

                // Input date
                System.out.print("Ingrese la fecha del gasto (dd/mm/yyyy): ");
                String date = scanner.nextLine();

                countCategoryMap.put(name, countCategoryMap.getOrDefault(name, 0) + 1);

                expenseDto.setAmount(amount);

                ExpenseCategory expenseCategory = expenseCategoryDao.getCategoryByName(name);
                expenseDto.setCategoryId(expenseCategory.getId());
                expenseDto.setDate(date);

                expenseDao.insert(expenseDto);
                counter++;
                index++;

                System.out.print("Desea cargar otro gasto? SI/NO: ");
                resp = scanner.nextLine();
                cutLogicVar = Objects.equals(resp, "si");
                    /*
                    catch (java.util.InputMismatchException e) {
                        System.out.println("Error: Ingrese un número válido.");
                        // Clear the input buffer
                        scanner.nextLine();
                    } finally {
                        continue;
                    }*/

                // while (index < cantGastosAIngresar);
            }

            List<ExpenseDto> expenses = expenseDao.getAll();
                System.out.println("Total de gastos ingresados: " + expenseCalculator.calculateTotalExpense(expenses));

                System.out.println("TOP 3 DE GASTOS INGRESADOS");
                List<Double> amounts = expenses.stream()
                        .map(e -> e.getAmount())
                        .limit(3)
                        .collect(Collectors.toList());
                amounts.forEach(System.out::println);

                System.out.println("CONTADOR POR CATEGORIA");
                countCategoryMap.forEach((category, count)-> System.out.println(category + ": " + count));

                System.out.println("DETALLE DE GASTOS INGRESADOS: ");
                Utilities.printElements(expenses);

        } catch (SQLException exception){
            exception.printStackTrace();

        } catch (DAOException e) {
            throw new RuntimeException(e);
        }


    }
    }

