package com.example.ahtunis.expensemaster;

/**
 * Created by Ahtunis on 4/3/2017.
 */

class Expense {


    public int getExpenseId() {
        return ExpenseId;
    }

    public void setExpenseId(int expenseId) {
        ExpenseId = expenseId;
    }

    String getBusiness() {
        return Business;
    }

    public void setBusiness(String business) {
        Business = business;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    private int ExpenseId;

    private int UserId;

    private String Amount;

    private String Business;

    private String Date;

    private  String Total;

    private String Status;

    public String getComment() {
        return Comment;
    }
    public boolean IsValidForSave(){
        return !(Comment.isEmpty() && Business.isEmpty() && Date.isEmpty() && Total.isEmpty());
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    private String Comment;

    public Expense(String business, String date, String total, String comment) {
        Comment = comment;
        Business = business;
        Date = date;
        Total = total;
    }

    public Expense(String business, String date, String total, String status, String expenseType) {
        Business = business;
        Date = date;
        Total = total;
        Status = status;
        ExpenseType = expenseType;
    }

    public String getExpenseType() {
        return ExpenseType;
    }

    public void setExpenseType(String expenseType) {
        ExpenseType = expenseType;
    }

    private  String ExpenseType;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }




}
