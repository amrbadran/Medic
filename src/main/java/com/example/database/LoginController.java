package com.example.database;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML

    private ImageView myImg;
    @FXML
    private AnchorPane ca_box;
    @FXML
    private AnchorPane login_box;

    @FXML
    private TextField usr_login;
    @FXML
    private TextField SignUpusr;
    @FXML
    private DatePicker SignUpBdate;
    @FXML
    private TextField SignUpPassword;
    @FXML
    private TextField SignUpPassword2;
    @FXML
    private Label label_pass;
    @FXML
    private Label label_pass2;

    @FXML
    private TextField password_login;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){


        // Animation for the image in the login (hover in-place)
        TranslateTransition tt = new TranslateTransition();
        tt.setNode(myImg);
        tt.setDuration(Duration.millis(800));

        tt.setCycleCount(TranslateTransition.INDEFINITE);
        tt.setByY(15);
        tt.setAutoReverse(true);
        tt.play();
    }


    // display Create-Account box
    public void createAccountVis(){

        // Fade-In and Fade-Out animation to make it smooth transition
        FadeTransition fade1 = new FadeTransition(Duration.seconds(0.5),ca_box);
        FadeTransition fade2 = new FadeTransition(Duration.seconds(0.5),login_box);

        fade1.setCycleCount(1);
        fade1.setFromValue(0.0);
        fade1.setToValue(1.0);
        //fade1.setAutoReverse(true);
        fade2.setCycleCount(1);
        fade2.setFromValue(1.0);
        fade2.setToValue(0.0);
        //fade2.setAutoReverse(true);

        fade2.play();

        login_box.setVisible(false);
        ca_box.setVisible(true);

        fade1.play();
    }

    public void createAccountdisVis(){

        // Fade-In and Fade-Out animation to make it smooth transition
        FadeTransition fade1 = new FadeTransition(Duration.seconds(0.5),ca_box);
        FadeTransition fade2 = new FadeTransition(Duration.seconds(0.5),login_box);

        fade1.setCycleCount(1);
        fade1.setFromValue(1.0);
        fade1.setToValue(0.0);
        //fade1.setAutoReverse(true);
        fade2.setCycleCount(1);
        fade2.setFromValue(0.0);
        fade2.setToValue(1.0);
        //fade2.setAutoReverse(true);

        fade2.play();

        login_box.setVisible(true);
        ca_box.setVisible(false);

        fade1.play();
    }

    public void LoginFunc(ActionEvent event) throws IOException{

        String usr = usr_login.getText();
        String pass = password_login.getText();
        try{

            Config con = new Config();
            Statement stm = con.makeStatemnt();
            String query = "SELECT username,password FROM "+con.DB+".users;";
            ResultSet rs = stm.executeQuery(query);
            boolean flag = false;
            while(rs.next()){

                String usr2 = rs.getString("username");
                String pass2 = rs.getString("password");
                if(usr.equals(usr2) && pass.equals(pass2)){
                    flag = true;
                    break;


                }
            }
            if(flag == true){SwitchToTab(event,"HomePage.fxml");}
            else {
                Alert msg = new Alert(Alert.AlertType.ERROR, "The username or password is incorrect");
                msg.showAndWait();
                return;
            }
        }

        catch (Exception e){System.out.println(e.getMessage());}



    }
    public void SignUp(){

        String username = SignUpusr.getText();
        LocalDate tmp = SignUpBdate.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String Date = "";
        if(tmp != null)
            Date = tmp.format(formatter);
        String pass1 = SignUpPassword.getText();
        String pass2 = SignUpPassword2.getText();

        boolean flag = username.isEmpty() || Date.isEmpty() || pass1.isEmpty() || pass2.isEmpty();
        if(flag == true){
            Alert msg = new Alert(Alert.AlertType.ERROR, "There is an empty field !!");
            msg.showAndWait();
            return;
        }
        if(!checkPass()) return;

        String query = "INSERT INTO "+Config.DB+".users(UID,BDate,username,password) ";
        query += "VALUES (nextval('"+Config.DB+".usersSeq'),'"+Date+"','"+username+"','"+pass1+"')";

        try{
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            con.con.setAutoCommit(false);
            stm.executeUpdate(query);
            con.con.commit();
            con.closeConnection();
            Alert msg = new Alert(Alert.AlertType.INFORMATION,"You Created A new Account");
            msg.showAndWait();
            SignUpBdate.setValue(null);
            SignUpusr.setText("");
            SignUpPassword.setText("");
            SignUpPassword2.setText("");
            createAccountdisVis();
        }

        catch (Exception e){
            System.out.println(e.getMessage());
        }


    }
    public boolean checkPass(){
        String pass1 = SignUpPassword.getText();
        String pass2 = SignUpPassword2.getText();
        if(pass1.isEmpty() && pass2.isEmpty()){
            label_pass.setVisible(false);
            label_pass2.setVisible(false);
            return false;
        }
        else if(pass1.equals(pass2)){
            label_pass.setVisible(true);
            label_pass2.setVisible(false);
            return true;
        }
        else{
            label_pass2.setVisible(true);
            label_pass.setVisible(false);
            return false;
        }
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

}
