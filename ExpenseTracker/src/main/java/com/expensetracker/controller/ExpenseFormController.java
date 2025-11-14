package com.expensetracker.controller;

import com.expensetracker.model.Expense;
import com.expensetracker.utils.AlertHelper;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ExpenseFormController {
   @FXML
   private TextField txtTitle;
   @FXML
   private TextField txtAmount;
   @FXML
   private ComboBox<String> cmbCategory;
   @FXML
   private DatePicker datePicker;
   @FXML
   private Button btnSave;
   @FXML
   private Button btnCancel;
   private Expense expense;

   @FXML
   public void initialize() {
      this.cmbCategory.getItems().addAll(new String[]{"Food", "Transport", "Shopping", "Bills", "Health", "Entertainment", "Other"});
   }

   @FXML
   private void onSave() {
      try {
         String title = this.txtTitle.getText().trim();
         double amount = Double.parseDouble(this.txtAmount.getText().trim());
         String category = (String)this.cmbCategory.getValue();
         LocalDate date = (LocalDate)this.datePicker.getValue();
         if (title.isEmpty() || category == null || date == null) {
            AlertHelper.showError("Invalid Input", "Please fill all fields properly.");
            return;
         }

         if (this.expense == null) {
            this.expense = new Expense();
         }

         this.expense.setTitle(title);
         this.expense.setAmount(amount);
         this.expense.setCategory(category);
         this.expense.setDate(date);
         this.closeForm();
      } catch (NumberFormatException var6) {
         AlertHelper.showError("Invalid Amount", "Please enter a valid number for amount.");
      }

   }

   @FXML
   private void onCancel() {
      this.expense = null;
      this.closeForm();
   }

   private void closeForm() {
      Stage stage = (Stage)this.btnCancel.getScene().getWindow();
      stage.close();
   }

   public Expense getExpense() {
      return this.expense;
   }

   public void setExpense(Expense expenseToEdit) {
      this.expense = expenseToEdit;
      this.txtTitle.setText(expenseToEdit.getTitle());
      this.txtAmount.setText(String.valueOf(expenseToEdit.getAmount()));
      this.cmbCategory.setValue(expenseToEdit.getCategory());
      this.datePicker.setValue(expenseToEdit.getDate());
   }
}