package com.example.database;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ExpensesController implements Initializable {
    @FXML
    private TableView<Expense> ExpenseTable;

    @FXML
    private TableColumn<Expense, Integer> ExpenseID;

    @FXML
    private TableColumn<Expense, String> ExpenseName;

    @FXML
    private TableColumn<Expense, String> ExpenseType;

    @FXML
    private TableColumn<Expense, String> Description;

    @FXML
    private TableColumn<Expense, Integer> Amount;

    @FXML
    private TextField ExpenseIDTextField;
    @FXML
    private TextField ExpenseNameTextField;
    @FXML
    private TextField ExpenseTypeTextField;
    @FXML
    private TextArea DescriptionTextField;
    @FXML
    private TextField AmountTextField;

    @FXML
    private TextField IDSearch;

    @FXML
    private TextField NameSearch;

    @FXML
    private TextField TypeSearch;

    @FXML
    private TextField DescriptionSearch;

    @FXML
    private TextField AmountSearch;

    @FXML
    private AnchorPane ExpensePane;

    @FXML
    private DialogPane dialog1;

    @FXML
    private int TotalExpense;

    @FXML
    private TextField TotalExpenseTextFeild ;

    private int SelectedIndexExpense;


    ObservableList<Expense> ExpenseList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ExpenseID.setCellValueFactory(new PropertyValueFactory<Expense, Integer>("ExpenseID"));
        ExpenseName.setCellValueFactory(new PropertyValueFactory<Expense, String>("ExpenseName"));
        ExpenseType.setCellValueFactory(new PropertyValueFactory<Expense, String>("ExpenseType"));
        Description.setCellValueFactory(new PropertyValueFactory<Expense, String>("Description"));
        Amount.setCellValueFactory(new PropertyValueFactory<Expense, Integer>("Amount"));

        UpdateExpenseTable();
    }

    public void UpdateExpenseTable() {
        try {
            Config config = new Config();
            Statement statement = config.makeStatemnt();
            String Quary = " Select * from medicinewarehouse.expenses ;";
            ResultSet resultSet = statement.executeQuery(Quary);

            if (!ExpenseList.isEmpty()) {
                ExpenseList.clear();
                TotalExpense = 0;
            }

            while (resultSet.next()) {
                Integer ExpenseID = resultSet.getInt("ExpenseID");
                String ExpenseName = resultSet.getString("ExpenseName");
                String ExpenseType = resultSet.getString("ExpenseType");
                String Description = resultSet.getString("Description");
                Integer Amount = resultSet.getInt("Amount");
                TotalExpense += Amount;

                Expense obj = new Expense(ExpenseID, ExpenseName, ExpenseType, Description, Amount);
                ExpenseList.add(obj);
            }
            config.closeConnection();
            ExpenseTable.setItems(ExpenseList);
            TotalExpenseTextFeild.setText(String.valueOf(TotalExpense));
            TotalExpenseTextFeild.setEditable(false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void getExpenseItem(MouseEvent event) throws IOException {
        SelectedIndexExpense = ExpenseTable.getSelectionModel().getSelectedIndex();
        if (SelectedIndexExpense < 0) return;

        Expense tmp = ExpenseTable.getItems().get(SelectedIndexExpense);
        ExpenseIDTextField.setText(String.valueOf(tmp.getExpenseID()));
        ExpenseNameTextField.setText(tmp.getExpenseName());
        ExpenseTypeTextField.setText(tmp.getExpenseType());
        DescriptionTextField.setText(tmp.getDescription());
        AmountTextField.setText(String.valueOf(tmp.getAmount()));
    }

    public void SearchExpense() {
        String ID = IDSearch.getText();
        String Name = NameSearch.getText();
        String Type = TypeSearch.getText();
        String Description = DescriptionSearch.getText();
        String Amount = AmountSearch.getText();
        String query = "SELECT * FROM " + Config.DB + ".Expenses WHERE 1=1";
        int count = 0;
        if (!ID.isEmpty()) {
            query += " AND ExpenseID = " + ID;
            count++;
            IDSearch.setText("");
        }
        if (!Name.isEmpty()) {
            query += " AND ExpenseName like '%" + Name + "%'";
            count++;
            NameSearch.setText("");
        }
        if (!Type.isEmpty()) {
            query += " AND ExpenseType like %" + Type + "%";
            count++;
            TypeSearch.setText("");
        }
        if (!Description.isEmpty()) {
            query += " AND Description like '%" + Description + "%'";
            count++;
            DescriptionSearch.setText("");
        }
        if (!Amount.isEmpty()) {
            query += " AND Amount =" + Amount;
            count++;
            AmountSearch.setText("");
        }
        if (count == 0) return;
        query += " ORDER BY ExpenseID;"; // sort by Employee SSN

        try {
            ExpenseList.clear();
            Config con = new Config();
            Statement stm = con.makeStatemnt();

            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                Expense obj = new Expense(rs.getInt("ExpenseID"),
                        rs.getString("ExpenseName"),
                        rs.getString("ExpenseType"),
                        rs.getString("Description"),
                        rs.getInt("Amount")
                );
                ExpenseList.add(obj);
            }
            con.closeConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        closeSearchExpense();
    }

    public void closeSearchExpense() {
        ExpensePane.setDisable(false);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(250), dialog1);
        scaleTransition.setFromX(1); // Initial X scale factor (1 means the original size)
        scaleTransition.setFromY(1); // Initial Y scale factor
        scaleTransition.setToX(0);   // Final X scale factor (0 means invisible)
        scaleTransition.setToY(0);   // Final Y scale factor
        scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog1.setVisible(false);
            }
        });
        scaleTransition.play();

    }

    public void showSearchExpense() {
        ExpensePane.setDisable(true);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(250), dialog1);
        scaleTransition.setFromX(0); // Initial X scale factor
        scaleTransition.setFromY(0); // Initial Y scale factor
        scaleTransition.setToX(1);   // Final X scale factor
        scaleTransition.setToY(1);   // Final Y scale factor
        scaleTransition.play();
        dialog1.setVisible(true);
    }

    public void AddExpense() {

        String ExpenseId = ExpenseIDTextField.getText();
        String ExpenseName = ExpenseNameTextField.getText();
        String ExpenseType = ExpenseTypeTextField.getText();
        String Description = DescriptionTextField.getText();
        String Amount = AmountTextField.getText();

        boolean a = ExpenseName.isEmpty() || ExpenseType.isEmpty() || Description.isEmpty() || Amount.isEmpty();
        if (a == true) {
            Alert msg = new Alert(Alert.AlertType.ERROR, "There is an empty field !!");
            msg.showAndWait();
            return;
        }

        try {
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            con.con.setAutoCommit(false);

            String query = "INSERT INTO " + Config.DB + ".Expenses(ExpenseID, ExpenseName, ExpenseType, Description, Amount)";
            query += " VALUES(nextval('"+Config.DB+".expensesSeq'),'" + ExpenseName + "','" + ExpenseType + "','" + Description + "','" + Integer.parseInt(Amount) + "') ;";


            stm.executeUpdate(query);
            con.con.commit();
            con.closeConnection();

            UpdateExpenseTable();
            emptyExpenseTxtFields();
            Alert msg = new Alert(Alert.AlertType.INFORMATION, "The Row Has Been Added ");
            msg.showAndWait();
        } catch (Exception e) {
            Alert msg = new Alert(Alert.AlertType.ERROR, e.getMessage());
            msg.showAndWait();
        }

    }

    public void emptyExpenseTxtFields() {
        ExpenseIDTextField.setText("");
        ExpenseNameTextField.setText("");
        ExpenseTypeTextField.setText("");
        DescriptionTextField.setText("");
        AmountTextField.setText("");
    }

    public void UpdateExpense() {

        String ExpenseID = ExpenseIDTextField.getText();
        String ExpenseName = ExpenseNameTextField.getText();
        String ExpenseType = ExpenseTypeTextField.getText();
        String Description = DescriptionTextField.getText();
        String Amount = AmountTextField.getText();
        boolean a = ExpenseID.isEmpty() || ExpenseName.isEmpty() || ExpenseType.isEmpty() || Description.isEmpty() || Amount.isEmpty();
        if (a == true) {
            Alert msg = new Alert(Alert.AlertType.ERROR, "There is an empty field !!");
            msg.showAndWait();
            return;
        }
        try {
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            con.con.setAutoCommit(false);

            String query = "UPDATE " + con.DB + ".Expenses SET ";
            query += "ExpenseName='" + ExpenseName + "', ExpenseType='" + ExpenseType + "', Description='" + Description + "', Amount=" + Double.parseDouble(Amount);
            query += " WHERE ExpenseID=" + Integer.parseInt(ExpenseID) + ";";


            stm.executeUpdate(query);
            con.con.commit();
            con.closeConnection();
            Expense obj = new Expense(Integer.parseInt(ExpenseID), ExpenseName, ExpenseType, Description, Integer.parseInt(Amount));
            UpdateExpenseTable();
            Alert msg = new Alert(Alert.AlertType.INFORMATION, "The Row Has Been Updated");
            msg.showAndWait();

        } catch (Exception e) {
            Alert msg = new Alert(Alert.AlertType.ERROR, e.getMessage());
            msg.showAndWait();
        }
    }

    public void DeleteExpense() {


        String ExpenseID = ExpenseIDTextField.getText();
        if (ExpenseID.isEmpty()) {
            Alert msg = new Alert(Alert.AlertType.ERROR, "The SSN Field is Empty !!");
            msg.showAndWait();
            return;
        }
        try {
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            con.con.setAutoCommit(false);

            String query = "Delete From " + con.DB + ".Expenses WHERE ExpenseID=" + Integer.parseInt(ExpenseID) + ";";

            stm.executeUpdate(query);
            con.con.commit();
            con.closeConnection();

            UpdateExpenseTable();
            emptyExpenseTxtFields();
        } catch (Exception e) {
            Alert msg = new Alert(Alert.AlertType.ERROR, e.getMessage());
            msg.showAndWait();
        }

    }

    public void generateReport(){
        try {
            Connection con;
            DriverManager.registerDriver(new org.postgresql.Driver());
            String ConnectionInfo = "jdbc:postgresql://localhost:5432/postgres";
            con = DriverManager.getConnection(ConnectionInfo, Config.user, Config.pass);


            InputStream inp = new FileInputStream("Expenses.jrxml");
            JasperDesign jd = JRXmlLoader.load(inp);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr,null,con);
            OutputStream op= new FileOutputStream("Expenses.pdf");
            JasperExportManager.exportReportToPdfStream(jp,op);
            con.close();
            inp.close();
            op.close();
            Alert msg = new Alert(Alert.AlertType.INFORMATION,"All Expenses Report Has Been Generated Successfully");
            msg.showAndWait();

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void switchToHomeTab(MouseEvent event) throws IOException {
        SwitchToTab(event, "HomePage.fxml");
    }

    public void switchToDealersTab(MouseEvent event) throws IOException {
        SwitchToTab(event, "Dealers.fxml");

    }

    public void switchToDealingsTab(MouseEvent event) throws IOException {
        SwitchToTab(event, "Dealings.fxml");
    }

    public void switchToEmployeeTab(MouseEvent event) throws IOException {
        SwitchToTab(event, "Employee.fxml");
    }

    public void switchToMedicineTab(MouseEvent event) throws IOException {
        SwitchToTab(event, "Medicine.fxml");
    }

    public void SwitchToTab(MouseEvent event, String Tab) throws IOException {
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
