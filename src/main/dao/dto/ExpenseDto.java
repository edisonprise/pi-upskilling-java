package main.dao.dto;

public class ExpenseDto {
    private double amount;
    private int categoryId;
    private String date;

    public ExpenseDto() {
    }

    public ExpenseDto(double amount, int categoryId, String date) {
        this.amount = amount;
        this.categoryId = categoryId;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    @Override
    public String toString(){
        return "ExpenseDto{" +
                "amount=" + amount +
                ", categoryId=" + categoryId +
                ", date=" + date + '\'' +
                '}';
    }
}
