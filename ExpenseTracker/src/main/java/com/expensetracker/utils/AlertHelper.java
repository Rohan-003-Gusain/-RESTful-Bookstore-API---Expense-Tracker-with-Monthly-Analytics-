package com.expensetracker.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class AlertHelper {
   public static void showInfo(String title, String msg) {
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle(title);
      alert.setHeaderText((String)null);
      alert.setContentText(msg);
      alert.showAndWait();
   }

   public static void showError(String title, String msg) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle(title);
      alert.setHeaderText((String)null);
      alert.setContentText(msg);
      alert.showAndWait();
   }

   public static void showWarning(String title, String msg) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle(title);
      alert.setHeaderText((String)null);
      alert.setContentText(msg);
      alert.showAndWait();
   }

   public static boolean showConfirm(String title, String msg) {
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle(title);
      alert.setHeaderText((String)null);
      alert.setContentText(msg);
      Optional<ButtonType> result = alert.showAndWait();
      return result.isPresent() && result.get() == ButtonType.OK;
   }
}