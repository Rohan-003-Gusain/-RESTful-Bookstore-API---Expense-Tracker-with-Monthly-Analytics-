package com.expensetracker.controller;

import com.expensetracker.model.Expense;
import com.expensetracker.service.ExpenseService;
import com.expensetracker.utils.AlertHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class DashboardController {

    @FXML private TableView<Expense> tableExpenses;
    @FXML private TableColumn<Expense, Integer> colDisplayNumber;
    @FXML private TableColumn<Expense, String> colTitle;
    @FXML private TableColumn<Expense, Double> colAmount;
    @FXML private TableColumn<Expense, String> colCategory;
    @FXML private TableColumn<Expense, String> colDate;
    @FXML private Label lblTotal;
    @FXML private Button btnAddExpense;
    @FXML private Button btnEditExpense;
    @FXML private Button btnDeleteExpense;

    private final ExpenseService expenseService = new ExpenseService();
    private final ObservableList<Expense> expenseList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colDisplayNumber.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getDisplayNumber()).asObject());
        colTitle.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getTitle()));
        colAmount.setCellValueFactory(data ->
                new javafx.beans.property.SimpleDoubleProperty(data.getValue().getAmount()).asObject());
        colCategory.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getCategory()));
        colDate.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getDate().toString()));

        tableExpenses.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        loadExpenses();
    }

    private void loadExpenses() {
        expenseList.setAll(expenseService.getAllExpenses());
        tableExpenses.setItems(expenseList);
        lblTotal.setText("₹" + String.format("%.2f", expenseService.getTotalAmount()));
    }

    @FXML
    private void onAddExpense(ActionEvent event) {
        openExpenseForm(null); 
    }

    @FXML
    private void onEditExpense(ActionEvent event) {
        Expense selected = tableExpenses.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertHelper.showWarning("Select Expense", "Please select an expense to edit.");
            return;
        }
        openExpenseForm(selected);
    }

    @FXML
    private void onDeleteExpense(ActionEvent event) {
        Expense selected = tableExpenses.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertHelper.showWarning("Select Expense", "Please select an expense to delete.");
            return;
        }

        boolean confirmed = AlertHelper.showConfirm(
                "Delete Expense", "Are you sure you want to delete this expense?");
        if (confirmed) {
            expenseService.deleteExpense(selected.getId());
            loadExpenses();
            AlertHelper.showInfo("Deleted", "Expense deleted successfully!");
        }
    }

    private void openExpenseForm(Expense expenseToEdit) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/expense_form.fxml"));
            Parent root = loader.load();

            ExpenseFormController controller = loader.getController();
            if (expenseToEdit != null) {
                controller.setExpense(expenseToEdit); 
            }

            Stage stage = new Stage();
            stage.setTitle(expenseToEdit == null ? "Add Expense" : "Edit Expense");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            Expense result = controller.getExpense();
            if (result != null) {
                if (expenseToEdit == null) {
                    expenseService.addExpense(result);
                } else {
                    expenseService.updateExpense(result);
                }
                loadExpenses(); 
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void onMonthlyChart(ActionEvent event) {
        DatePicker dp = new DatePicker();
        dp.setShowWeekNumbers(false);
        dp.setValue(null);

        Dialog<LocalDate> dialog = new Dialog<>();
        dialog.setTitle("Select Month");
        dialog.getDialogPane().setContent(dp);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(button -> button == ButtonType.OK ? dp.getValue() : null);

        dialog.showAndWait().ifPresent(date -> {
            if (date == null) return;

            int month = date.getMonthValue();
            int year = date.getYear();

            List<ExpenseService.CategorySummary> summary = expenseService.getMonthlySummary(year, month);
            if (summary.isEmpty()) {
                AlertHelper.showInfo("No Data", "No expenses found for this month.");
                return;
            }

            javafx.scene.chart.PieChart pieChart = new javafx.scene.chart.PieChart();
            for (ExpenseService.CategorySummary s : summary) {
                pieChart.getData().add(new javafx.scene.chart.PieChart.Data(s.getCategory(), s.getTotal()));
            }

            pieChart.setTitle("Expenses for " + month + "/" + year);
            Stage chartStage = new Stage();
            chartStage.setTitle("Monthly Analytics");
            chartStage.setScene(new Scene(new StackPane(pieChart), 600, 400));
            chartStage.show();
        });
    }
    
    @FXML
    private void onExportCSV(ActionEvent event) {
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.setTitle("Save CSV");
        fileChooser.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        java.io.File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            expenseService.exportToCSV(file.getAbsolutePath());
            AlertHelper.showInfo("Exported", "Expenses exported to CSV successfully!");
        }
    }


}
