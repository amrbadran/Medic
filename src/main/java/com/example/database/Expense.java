package com.example.database;

public class Expense {

    private int ExpenseID;

    private String ExpenseName;

    private String ExpenseType;

    private String Description;

    private int Amount;

    public Expense(int ExpenseID, String ExpenseName, String ExpenseType, String Description, int Amount) {
        this.ExpenseID = ExpenseID;
        this.ExpenseName = ExpenseName;
        this.ExpenseType = ExpenseType;
        this.Description = Description;
        this.Amount = Amount;
    }

    public int getExpenseID() {
        return ExpenseID;
    }

    public String getExpenseName() {
        return ExpenseName;
    }

    public String getExpenseType() {
        return ExpenseType;
    }

    public String getDescription() {
        return Description;
    }

    public int getAmount() {
        return Amount;
    }
}
