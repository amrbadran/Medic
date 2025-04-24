package com.example.database;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader root = new FXMLLoader(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root.load());
        /*String css = this.getClass().getResource("style/login.css").toExternalForm();
        scene.getStylesheets().add(css);*/
        Image icon = new Image(getClass().getResourceAsStream("111.png"));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setTitle("Medic");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}