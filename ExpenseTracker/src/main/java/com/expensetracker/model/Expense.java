package com.expensetracker.model;

import java.time.LocalDate;

public class Expense {
   private int id;
   private int displayNumber;
   private String title;
   private double amount;
   private String category;
   private LocalDate date;

   public Expense() {
   }

   public Expense(int id, String title, double amount, String category, LocalDate date) {
      this.id = id;
      this.title = title;
      this.amount = amount;
      this.category = category;
      this.date = date;
   }

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getTitle() {
      return this.title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public double getAmount() {
      return this.amount;
   }

   public void setAmount(double amount) {
      this.amount = amount;
   }

   public String getCategory() {
      return this.category;
   }

   public void setCategory(String category) {
      this.category = category;
   }

   public LocalDate getDate() {
      return this.date;
   }

   public void setDate(LocalDate date) {
      this.date = date;
   }

   public String toString() {
      return this.title + "-Rs" + this.amount + "(" + this.category + ")";
   }

   public int getDisplayNumber() {
      return this.displayNumber;
   }

   public void setDisplayNumber(int displayNumber) {
      this.displayNumber = displayNumber;
   }
}
    