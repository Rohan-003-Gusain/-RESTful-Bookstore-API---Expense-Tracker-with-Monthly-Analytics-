package com.expensetracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
   public void start(Stage stage) {
      try {
         Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/fxml/dashboard.fxml"));
         Scene scene = new Scene(root, 900.0D, 600.0D);
         scene.getStylesheets().add(this.getClass().getResource("/css/style.css").toExternalForm());
         stage.setTitle("Expense Tracker");
         stage.setScene(scene);
         stage.show();
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   public static void main(String[] args) {
      launch(new String[0]);
   }
}
