package com.expensetracker.service;

import com.expensetracker.database.DatabaseHelper;
import com.expensetracker.model.Expense;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;

public class ExpenseService {

    public void addExpense(Expense expense) {
        String sql = "INSERT INTO expenses (title, amount, category, date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, expense.getTitle());
            pstmt.setDouble(2, expense.getAmount());
            pstmt.setString(3, expense.getCategory());
            pstmt.setString(4, expense.getDate().toString());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateExpense(Expense expense) {
        String sql = "UPDATE expenses SET title=?, amount=?, category=?, date=? WHERE id=?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, expense.getTitle());
            pstmt.setDouble(2, expense.getAmount());
            pstmt.setString(3, expense.getCategory());
            pstmt.setString(4, expense.getDate().toString());
            pstmt.setInt(5, expense.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteExpense(int id) {
        String sql = "DELETE FROM expenses WHERE id=?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Expense> getAllExpenses() {
        List<Expense> list = new ArrayList<>();
        String sql = "SELECT * FROM expenses ORDER BY id ASC";

        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Expense expense = new Expense(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getDouble("amount"),
                        rs.getString("category"),
                        LocalDate.parse(rs.getString("date"))
                );
                list.add(expense);
            }
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setDisplayNumber(i + 1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public double getTotalAmount() {
        String sql = "SELECT SUM(amount) FROM expenses";
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.next() ? rs.getDouble(1) : 0.0;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public List<CategorySummary> getMonthlySummary(int year, int month) {
        List<CategorySummary> list = new ArrayList<>();
        String sql = "SELECT category, SUM(amount) as total " +
                     "FROM expenses " +
                     "WHERE strftime('%Y', date) = ? AND strftime('%m', date) = ? " +
                     "GROUP BY category";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, String.valueOf(year));
            ps.setString(2, String.format("%02d", month));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new CategorySummary(rs.getString("category"), rs.getDouble("total")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public static class CategorySummary {
        private final String category;
        private final double total;

        public CategorySummary(String category, double total) {
            this.category = category;
            this.total = total;
        }

        public String getCategory() { return category; }
        public double getTotal() { return total; }
    }

    public void exportToCSV(String filePath) {
        List<Expense> expenses = getAllExpenses();
        try (PrintWriter pw = new PrintWriter(filePath)) {
            pw.println("ID,Title,Amount,Category,Date");
            for (Expense e : expenses) {
                pw.println(e.getId() + "," + e.getTitle() + "," + e.getAmount() + "," +
                           e.getCategory() + "," + e.getDate());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
