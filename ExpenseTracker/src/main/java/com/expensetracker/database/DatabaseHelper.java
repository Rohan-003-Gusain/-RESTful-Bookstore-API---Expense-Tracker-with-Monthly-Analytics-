package com.expensetracker.database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.expensetracker.model.Expense;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:expense_tracker.db";

    static {
        createTableIfNotExists();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private static void createTableIfNotExists() {
        String sql = """
                CREATE TABLE IF NOT EXISTS expenses (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    amount REAL NOT NULL,
                    category TEXT NOT NULL,
                    date TEXT NOT NULL
                )
                """;
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addExpense(Expense expense) {
        String sql = "INSERT INTO expenses(title, amount, category, date) VALUES(?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, expense.getTitle());
            pstmt.setDouble(2, expense.getAmount());
            pstmt.setString(3, expense.getCategory());
            pstmt.setString(4, expense.getDate().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateExpense(Expense expense) {
        String sql = "UPDATE expenses SET title=?, amount=?, category=?, date=? WHERE id=?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
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

    public static void deleteExpense(int id) {
        String sql = "DELETE FROM expenses WHERE id=?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Expense> getAllExpenses() {
        List<Expense> list = new ArrayList<>();
        String sql = "SELECT * FROM expenses ORDER BY date DESC";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Expense e = new Expense(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getDouble("amount"),
                    rs.getString("category"),
                    LocalDate.parse(rs.getString("date"))
                );
                list.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
