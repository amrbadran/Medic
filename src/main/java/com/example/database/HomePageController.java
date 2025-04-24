package com.example.database;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {




    }

    public void SignOut(ActionEvent event) throws IOException{
        SwitchToTab(event,"Login.fxml");
    }
    public void About(){
        Alert msg = new Alert(Alert.AlertType.INFORMATION,"This Application Was Done By Amr Badran(12113636) , MohamedSalha (1210000)");
        msg.showAndWait();
    }
    public void switchToMedicineTab(MouseEvent event) throws IOException {
        SwitchToTab(event,"Medicine.fxml");
    }

    public void switchToDealersTab(MouseEvent event) throws IOException {
        SwitchToTab(event,"Dealers.fxml");

    }
    public void switchToDealingsTab(MouseEvent event) throws IOException {
        SwitchToTab(event,"Dealings.fxml");
    }
    public void switchToEmployeeTab(MouseEvent event) throws IOException {
        SwitchToTab(event,"Employee.fxml");
    }

    public void switchToExpensesTab(MouseEvent event) throws IOException {
        SwitchToTab(event, "Expenses.fxml");
    }
    public void SwitchToTab(ActionEvent event, String Tab) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Tab));
        Parent scene2Parent = loader.load();
        Scene scene2 = new Scene(scene2Parent);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), scene2Parent);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
        // Get the Stage from the ActionEvent

        stage.setScene(scene2);
    }
    public void SwitchToTab(MouseEvent event,String Tab) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Tab));
        Parent scene2Parent = loader.load();
        Scene scene2 = new Scene(scene2Parent);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), scene2Parent);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
        // Get the Stage from the ActionEvent

        stage.setScene(scene2);
    }
}
