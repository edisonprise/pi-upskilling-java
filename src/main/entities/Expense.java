package main.entities;

public class Expense {
    private Integer id;
    private Double amount;
    private Integer categoryId;
    private String date;

    public Expense() {
    }

    public Expense(Integer id, Double amount, Integer categoryId, String date) {
        this.id = id;
        this.amount = amount;
        this.categoryId = categoryId;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", amount=" + amount +
                ", category=" + categoryId +
                ", date='" + date + '\'' +
                '}';
    }
}
