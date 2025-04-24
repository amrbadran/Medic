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

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;

public class EmployeeController implements Initializable {

    @FXML
    private TableView<Employee> EmployeeTable;

    @FXML
    private TableColumn<Employee, Integer> SSN;

    @FXML
    private TableColumn<Employee, String> FName;

    @FXML
    private TableColumn<Employee, String> MName;

    @FXML
    private TableColumn<Employee, String> LName;

    @FXML
    private TableColumn<Employee, String> BirthDate;

    @FXML
    private TableColumn<Employee, Double> SalaryPerHour;

    @FXML
    private TableColumn<Employee, String> Sex;
    @FXML
    private TableColumn<Employee, Double> BonusPersentage;

    @FXML
    private TextField SSNTextField;

    @FXML
    private TextField FNameTextField;


    //  private TextField MNameTextField;


    // private TextField LNameTextField;

    @FXML
    private DatePicker BirthDateTextField;

    @FXML
    private TextField SalaryPerHourTextField;

    @FXML
    private ComboBox<String> SexTextField;

    @FXML
    private Button EmployeeButton;

    @FXML
    private Button MangerDestubuiterButton;
    @FXML
    private AnchorPane EmployeePane;


    Integer SelectedIndexEmployee;

    @FXML
    private TextField EmployeeSSNSearch;
    @FXML
    private TextField EmployeeNameSearch;
    @FXML
    private TextField EmployeeBirthDateSearch;
    @FXML
    private TextField EmployeeSalarySearch;

    @FXML
    private TextField EmployeeSexSearch;

    @FXML
    private TextField AgetxtField;
    @FXML
    private DialogPane dialog1;
    @FXML
    private ToggleButton NonMedic;

    @FXML
    private ToggleButton AllEmployee;

    @FXML
    private ToggleButton Medic;

    @FXML
    private TextField BonusTextField;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Tooltip tooltipSalary;
    @FXML
    private CheckBox checkBox_medDis;

    public int flag_selected = 0;

    private boolean flag = true;
    ObservableList<String> listMale = FXCollections.observableArrayList();

    ObservableList<Employee> listFemale = FXCollections.observableArrayList();


    ObservableList<Employee> EmployeeList = FXCollections.observableArrayList();
    //ObservableList<MedicineDistributor> MDistrbuiterList=FXCollections.observableArrayList();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (AllEmployee != null) {
              AllEmployee.setId("l2");
        }
        isDefault();
        flag_selected = 1;
        SexTextField.getItems().addAll("M", "F");
        SSN.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("SSN"));
        FName.setCellValueFactory(new PropertyValueFactory<Employee, String>("FName"));
        MName.setCellValueFactory(new PropertyValueFactory<Employee, String>("MName"));
        LName.setCellValueFactory(new PropertyValueFactory<Employee, String>("LName"));
        BirthDate.setCellValueFactory(new PropertyValueFactory<Employee, String>("BirthDate"));
        SalaryPerHour.setCellValueFactory(new PropertyValueFactory<Employee, Double>("SalaryPerHour"));
        Sex.setCellValueFactory(new PropertyValueFactory<Employee, String>("Sex"));

        BonusPersentage.setCellValueFactory(new PropertyValueFactory<Employee, Double>("bonus"));

        UpdateEmployeeTable();

    }

    public void changeStatus() {
        AllEmployee.setSelected(false);
        Medic.setSelected(true);
        NonMedic.setSelected(false);
        AllEmployee.setId("l1");
        Medic.setId("l2");
        NonMedic.setId("l1");
        flag_selected = 0;
        UpdateMedicineDes();
    }

    public void changeStatus1() {
        Medic.setSelected(false);
        AllEmployee.setSelected(true);
        NonMedic.setSelected(false);
        AllEmployee.setId("l2");
        Medic.setId("l1");
        NonMedic.setId("l1");
        flag_selected = 1;
        UpdateEmployeeTable();
    }

    public void changeStatus2() {
        Medic.setSelected(false);
        AllEmployee.setSelected(false);
        AllEmployee.setId("l1");
        Medic.setId("l1");
        NonMedic.setId("l2");
        NonMedic.setSelected(true);
        flag_selected = 2;
        UpdateEmployeeTable(); // employee but not dis
    }


    public void UpdateEmployeeTable() {
        try {
            if (!EmployeeList.isEmpty()) {
                EmployeeList.clear();
                if (!listMale.isEmpty()) listMale.clear();
                if (!listFemale.isEmpty()) listFemale.clear();
            }
            if (flag == false) {
                SSN.setPrefWidth(SSN.getPrefWidth() + 10);
                SalaryPerHour.setPrefWidth(SalaryPerHour.getPrefWidth() + 16);
                BirthDate.setPrefWidth(BirthDate.getPrefWidth() + 16);
                BonusPersentage.setVisible(false);
                BonusTextField.setVisible(false);
                anchorPane.setPrefHeight(355);
            }
            flag = true;

            Config config = new Config();
            Statement statement = config.makeStatemnt();
            ResultSet resultSet;
            String query3 ="SELECT medicinewarehouse.medicinedistrbuter.bonuspercentage, medicinewarehouse.employee.SSN\n" +
                    "FROM medicinewarehouse.employee\n" +
                    "JOIN medicinewarehouse.medicinedistrbuter ON medicinewarehouse.employee.SSN = medicinewarehouse.medicinedistrbuter.mdestrbuiterid\n" +
                    "ORDER BY medicinewarehouse.employee.SSN ;";
            resultSet = statement.executeQuery(query3);
            HashMap<Integer,Double> hashMap=new HashMap<>();
            while (resultSet.next()){
                hashMap.put(resultSet.getInt("SSN"),resultSet.getDouble("bonuspercentage"));
            }
            Set<Integer> keys = hashMap.keySet();

            for(int i=0 ;EmployeeList.size() >i;i++){
                if(keys.contains(EmployeeList.get(i).getSSN()) ){
                    EmployeeList.get(i).setBonus(hashMap.get((EmployeeList.get(i).getSSN())));
                }
            }

            String Query = "Select * from medicinewarehouse.employee ORDER BY SSN;";
            String Query2 = "SELECT *\n" +
                    "FROM medicinewarehouse.employee\n" +
                    "WHERE medicinewarehouse.employee.SSN NOT IN (\n" +
                    "    SELECT medicinewarehouse.medicinedistrbuter.mdestrbuiterid\n" +
                    "    FROM medicinewarehouse.medicinedistrbuter\n" +
                    ") ORDER BY SSN";

            if (!NonMedic.isSelected())
                resultSet = statement.executeQuery(Query);
            else
                resultSet = statement.executeQuery(Query2);
            while (resultSet.next()) {
                int SSN = resultSet.getInt("SSN");
                String FName = resultSet.getString("FName");
                String MName = resultSet.getString("MName");
                String LName = resultSet.getString("LName");
                String BirthDate = resultSet.getString("BirthDate");
                double SalaryPerHour = resultSet.getDouble("SalaryPerHour");
                String Sex = resultSet.getString("Sex");

                Employee obj = new Employee(SSN, FName, MName, LName, BirthDate, SalaryPerHour, Sex, 0);
                if(keys.contains(SSN)){obj.flag_medicineDes =true;}
                EmployeeList.add(obj);
            }

            config.closeConnection();
            EmployeeTable.setItems(EmployeeList);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
    public void UpdateMedicineDes() {
        if (flag == true) {
            SSN.setPrefWidth(SSN.getPrefWidth() - 10);
            SalaryPerHour.setPrefWidth(SalaryPerHour.getPrefWidth() - 16);
            BirthDate.setPrefWidth(BirthDate.getPrefWidth() - 16);
            anchorPane.setPrefHeight(444);
            BonusTextField.setVisible(true);
            BonusPersentage.setVisible(true);
        }
        flag = false;

        try {
            if (!EmployeeList.isEmpty()) {
                EmployeeList.clear();
            }
            Config config = new Config();
            Statement statement = config.makeStatemnt();
            String Query = "SELECT medicinewarehouse.employee.*, medicinewarehouse.medicinedistrbuter.bonuspercentage\n" +
                    "FROM medicinewarehouse.employee\n" +
                    "INNER JOIN medicinewarehouse.medicinedistrbuter\n" +
                    "ON medicinewarehouse.employee.SSN = medicinewarehouse.medicinedistrbuter.mdestrbuiterid ORDER BY SSN;";
            ResultSet resultSet = statement.executeQuery(Query);

            while (resultSet.next()) {
                int SSN = resultSet.getInt("SSN");
                String FName = resultSet.getString("FName");
                String MName = resultSet.getString("MName");
                String LName = resultSet.getString("LName");
                String BirthDate = resultSet.getString("BirthDate");
                double SalaryPerHour = resultSet.getDouble("SalaryPerHour");
                String Sex = resultSet.getString("Sex");
                double bonus = resultSet.getDouble("bonuspercentage");
                Employee obj = new Employee(SSN, FName, MName, LName, BirthDate, SalaryPerHour, Sex, bonus);
                obj.flag_medicineDes = true;
                EmployeeList.add(obj);
            }
            config.closeConnection();
            EmployeeTable.setItems(EmployeeList);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void getEmployeeItem(MouseEvent event) throws IOException {
        SelectedIndexEmployee = EmployeeTable.getSelectionModel().getSelectedIndex();
        if (SelectedIndexEmployee < 0) return;

        Employee tmp = EmployeeTable.getItems().get(SelectedIndexEmployee);
        SSNTextField.setText(String.valueOf(tmp.getSSN()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(tmp.getBirthDate(), formatter);
        BirthDateTextField.setValue(birthDate);
        SalaryPerHourTextField.setText(String.valueOf(tmp.getSalaryPerHour()));
        FNameTextField.setText(tmp.getFName() + " " + tmp.getMName() + " " + tmp.getLName());
        SexTextField.setValue(tmp.getSex());
        BonusTextField.setText(String.valueOf(tmp.getBonus()));
        if (tmp.flag_medicineDes == true) {
            checkBox_medDis.setSelected(true);
        } else checkBox_medDis.setSelected(false);

        int Age = calculateAge(birthDate);
        AgetxtField.setText("Age is : "+Age);


    }
    // derived Attribute (Salary)
    public void calculateSalary() {
        // Salary Per Month
        String value;
        value = SalaryPerHourTextField.getText().isEmpty() ? "0" : SalaryPerHourTextField.getText();
        BigDecimal v = new BigDecimal(value);
        double tmp = v.doubleValue() * 30 * 8;
        // work-time = 8 hours
        value = String.valueOf(tmp);
        tooltipSalary.setText("Salary-Per-Month : "+value);
    }

    // derived Attribute (Age)
    public int calculateAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthDate, currentDate);
        return period.getYears();
    }

    public void SearchEmployee() {
        String SSN = EmployeeSSNSearch.getText();
        String EmployeeName = EmployeeNameSearch.getText();
        //String[] name = splitFullName(EmployeeName);
        String EmployeeBD = EmployeeBirthDateSearch.getText();
        String EmployeeSalary = EmployeeSalarySearch.getText();
        String EmployeeSex = EmployeeSexSearch.getText();
        String query = "SELECT * FROM " + Config.DB + ".Employee WHERE 1=1";
        if(flag_selected == 0){
            query = "SELECT * FROM "+Config.DB+".Employee as e,"+Config.DB+".medicinedistrbuter as m WHERE e.SSN = m.mdestrbuiterid";
        }
        else if(flag_selected == 2){
            query = "SELECT *\n" +
                    "FROM "+Config.DB+".employee\n" +
                    "WHERE "+Config.DB+".employee.SSN NOT IN (\n" +
                    "    SELECT "+Config.DB+".medicinedistrbuter.mdestrbuiterid\n" +
                    "    FROM "+Config.DB+".medicinedistrbuter\n" +
                    ")";
        }
        int count = 0;
        if (!SSN.isEmpty()) {
            query += " AND SSN = " + SSN;
            count++;
            EmployeeSSNSearch.setText("");
        }
        //use string spliter for the name
        if (!EmployeeName.isEmpty()) {
            query += " AND (FName like '%" + EmployeeName + "%' OR MName like '%" +EmployeeName + "%' OR LName like '%" + EmployeeName + "%')";
            count++;
            EmployeeNameSearch.setText("");
        }
        if (!EmployeeBD.isEmpty()) {
            query += " AND BirthDate like %" + EmployeeBD + "%";
            count++;
            EmployeeBirthDateSearch.setText("");
        }
        if (!EmployeeSalary.isEmpty()) {
            query += " AND SalaryPerHour like '%" + EmployeeSalary + "%'";
            count++;
            EmployeeSalarySearch.setText("");
        }
        if (!EmployeeSex.isEmpty()) {
            query += " AND Sex like '%" + EmployeeSex + "%'";
            count++;
            EmployeeSexSearch.setText("");
        }
        if (count == 0) return;
        query += " ORDER BY SSN;"; // sort by Employee SSN

        try {
            EmployeeList.clear();
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            Statement stm2 = con.makeStatemnt();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                Employee obj = new Employee(rs.getInt("SSN")
                        , rs.getString("FName"),
                        rs.getString("MName"),
                        rs.getString("LName"),
                        rs.getString("BirthDate"),
                        rs.getDouble("SalaryPerHour"),
                        rs.getString("Sex"),
                        (flag_selected==0 ? rs.getDouble("bonuspercentage") : 0)
                );
                    EmployeeList.add(obj);
            }
            con.closeConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        closeSearchEmployee();
    }

    public void closeSearchEmployee() {
        EmployeePane.setDisable(false);
        //     EmployeeButton.setDisable(false);
        //      MangerDestubuiterButton.setDisable(false);
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

    public void showSearchEmployee() {
        EmployeePane.setDisable(true);
        // EmployeeButton.setDisable(true);
        //   MangerDestubuiterButton.setDisable(true);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(250), dialog1);
        scaleTransition.setFromX(0); // Initial X scale factor
        scaleTransition.setFromY(0); // Initial Y scale factor
        scaleTransition.setToX(1);   // Final X scale factor
        scaleTransition.setToY(1);   // Final Y scale factor
        scaleTransition.play();
        dialog1.setVisible(true);
    }

    public void AddEmployee() {

        String SSN = SSNTextField.getText();
        String EmployeeName = FNameTextField.getText();
        String[] name = splitFullName(EmployeeName);
        LocalDate EmployeeBD = BirthDateTextField.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String EmployeeBirth = "";
        if (EmployeeBD != null) {
            EmployeeBirth = EmployeeBD.format(formatter);
        }
        String EmployeeSalary = SalaryPerHourTextField.getText();
        String EmployeeSex = SexTextField.getValue();
        String BonusPersantage = BonusTextField.getText();
        BigDecimal bonus ;
        try {


            boolean a = SSN.isEmpty() || name[0].isEmpty() || name[1].isEmpty() || name[2].isEmpty() || EmployeeBirth.isEmpty() || EmployeeSalary.isEmpty() || EmployeeSex.isEmpty();
            if (a == true) {
                Alert msg = new Alert(Alert.AlertType.ERROR, "There is an empty field !!");
                msg.showAndWait();
                return;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            Alert msg = new Alert(Alert.AlertType.ERROR, "You must Write the employee First name , Middle name , Last name seperated with spaces");
            msg.showAndWait();
            return;
        }
        try {
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            con.con.setAutoCommit(false);

            String query = "INSERT INTO " + Config.DB + ".Employee(SSN,FName,MName,LName,BirthDate,SalaryPerHour,Sex)";
            query += " VALUES(" + Integer.parseInt(SSN) + ",'" + name[0] + "','" + name[1] + "','" + name[2] + "','" + EmployeeBirth + "','" + Double.parseDouble(EmployeeSalary) + "','" + EmployeeSex + "') ;";
            stm.executeUpdate(query);
            con.con.commit();
            if(flag_selected == 0){
                String value = (BonusPersantage.isEmpty() ? "0" : BonusPersantage);
                bonus = new BigDecimal(value);
                String query2 = "INSERT INTO "+Config.DB+".MedicineDistrbuter(MDestrbuiterID,BonusPercentage) ";
                query2 += "VALUES("+SSN+","+bonus.doubleValue()+");";
                stm.executeUpdate(query2);
                con.con.commit();
            }


            con.closeConnection();


            emptyEmployeeTxtFields();
            if(flag_selected == 0) UpdateMedicineDes();
            else UpdateEmployeeTable();
            Alert msg = new Alert(Alert.AlertType.INFORMATION, "The Employee Has Been Added ");
            msg.showAndWait();
        } catch (Exception e) {
            Alert msg = new Alert(Alert.AlertType.ERROR, e.getMessage());
            msg.showAndWait();
        }

    }

    public void emptyEmployeeTxtFields() {
        SSNTextField.setText("");
        FNameTextField.setText("");
        BirthDateTextField.setValue(null);
        SalaryPerHourTextField.setText("");
        SexTextField.setValue("");
        BonusTextField.setText("");
    }

    public static String[] splitFullName(String fullName) {
        // Split the full name into an array of words
        return fullName.split("\\s+");
    }

    public void UpdateEmployee() {

        String SSN = SSNTextField.getText();
        String EmployeeName = FNameTextField.getText();
        String[] name = splitFullName(EmployeeName);
        LocalDate EmployeeBD = BirthDateTextField.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String EmployeeBirth = "";
        String BonusPersantage = BonusTextField.getText();
        BigDecimal bonus;
        boolean isSelectedMedDis = checkBox_medDis.isSelected();
        if (EmployeeBD != null) {
            EmployeeBirth = EmployeeBD.format(formatter);
        }
        String EmployeeSalary = SalaryPerHourTextField.getText();
        String EmployeeSex = SexTextField.getValue();
        boolean a = SSN.isEmpty() || name[0].isEmpty() || name[1].isEmpty() || name[2].isEmpty() || EmployeeBirth.isEmpty() || EmployeeSalary.isEmpty() || EmployeeSex.isEmpty();
        if (a == true) {
            Alert msg = new Alert(Alert.AlertType.ERROR, "There is an empty field !!");
            msg.showAndWait();
            return;
        }
        try {
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            con.con.setAutoCommit(false);
            boolean MedicineDesFlag = false;
            for(Employee e : EmployeeList){
                if(e.getSSN() == Integer.parseInt(SSN)){MedicineDesFlag = e.flag_medicineDes;}
            }
            if(isSelectedMedDis == true){
                if(MedicineDesFlag == false){
                    String value = (BonusPersantage.isEmpty() ? "0" : BonusPersantage);
                    bonus = new BigDecimal(value);
                    String query2 = "INSERT INTO "+Config.DB+".MedicineDistrbuter(MDestrbuiterID,BonusPercentage) ";
                    query2 += "VALUES("+SSN+","+bonus.doubleValue()+");";
                    stm.executeUpdate(query2);
                }
            }
            else{
                if(MedicineDesFlag == true){
                    String query2 = "DELETE FROM "+Config.DB+".MedicineDistrbuter WHERE MDestrbuiterID="+SSN+";";
                    stm.executeUpdate(query2);

                }
            }

            String query = "UPDATE " + con.DB + ".Employee SET ";
            query += "FName='" + name[0] + "', MName='" + name[1] + "', LName='" + name[2] + "', BirthDate='" + EmployeeBirth + "', SalaryPerHour=" + Double.parseDouble(EmployeeSalary) + ", Sex='" + EmployeeSex + "'";
            query += " where SSN=" + Integer.parseInt(SSN) + ";";

            stm.executeUpdate(query);
            con.con.commit();
            con.closeConnection();

            if(flag_selected == 0) UpdateMedicineDes();
            else UpdateEmployeeTable();

            emptyEmployeeTxtFields();

            Alert msg = new Alert(Alert.AlertType.INFORMATION, "The Employee Has Been Updated");
            msg.showAndWait();

        } catch (Exception e) {
            Alert msg = new Alert(Alert.AlertType.ERROR, e.getMessage());
            msg.showAndWait();
        }
    }

    public void DeleteEmployee() {


        String SSN = SSNTextField.getText();
        if (SSN.isEmpty()) {
            Alert msg = new Alert(Alert.AlertType.ERROR, "The SSN Field is Empty !!");
            msg.showAndWait();
            return;
        }
        try {
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            con.con.setAutoCommit(false);

            String query = "Delete From " + con.DB + ".employee WHERE SSN=" + Integer.parseInt(SSN) + ";";

            stm.executeUpdate(query);
            con.con.commit();
            con.closeConnection();

            UpdateEmployeeTable();
            emptyEmployeeTxtFields();
        } catch (Exception e) {
            Alert msg = new Alert(Alert.AlertType.ERROR, e.getMessage());
            msg.showAndWait();
        }

    }

    public void isDefault() {
        NonMedic.setSelected(false);
        AllEmployee.setSelected(true);
        Medic.setSelected(false);
    }


   /* public void UpdateNonMedicineDes() {
        if (flag == false) {
            SSN.setPrefWidth(SSN.getPrefWidth() + 10);
            SalaryPerHour.setPrefWidth(SalaryPerHour.getPrefWidth() + 16);
            BirthDate.setPrefWidth(BirthDate.getPrefWidth() + 16);
            BonusPersentage.setVisible(false);
            BonusTextField.setVisible(false);
            anchorPane.setPrefHeight(355);
        }
        flag = true;

        try {
            if (!EmployeeList.isEmpty()) {
                EmployeeList.clear();
            }
            Config config = new Config();
            Statement statement = config.makeStatemnt();
            String Query = "SELECT SSN,Fname,MName,LName,BirthDate,SalaryPerHour,Sex FROM medicinewarehouse.employee Except ";
             Query += "SELECT SSN,Fname,MName,LName,BirthDate,SalaryPerHour,Sex From medicinewarehouse.employee \n" +
                    "INNER JOIN medicinewarehouse.medicinedistrbuter\n" +
                    "ON medicinewarehouse.employee.SSN = medicinewarehouse.medicinedistrbuter.mdestrbuiterid;";
             System.out.println(Query);
            ResultSet resultSet = statement.executeQuery(Query);

            while (resultSet.next()) {
                int SSN = resultSet.getInt("SSN");
                String FName = resultSet.getString("FName");
                String MName = resultSet.getString("MName");
                String LName = resultSet.getString("LName");
                String BirthDate = resultSet.getString("BirthDate");
                double SalaryPerHour = resultSet.getDouble("SalaryPerHour");
                String Sex = resultSet.getString("Sex");
                Employee obj = new Employee(SSN, FName, MName, LName, BirthDate, SalaryPerHour, Sex,0.0);
                EmployeeList.add(obj);
            }
            config.closeConnection();
            EmployeeTable.setItems(EmployeeList);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }*/

    public void switchToHomeTab(MouseEvent event) throws IOException {
        SwitchToTab(event, "HomePage.fxml");
    }

    public void switchToDealersTab(MouseEvent event) throws IOException {
        SwitchToTab(event, "Dealers.fxml");

    }

    public void switchToDealingsTab(MouseEvent event) throws IOException {
        SwitchToTab(event, "Dealings.fxml");
    }

    public void switchToMedicineTab(MouseEvent event) throws IOException {
        SwitchToTab(event, "Medicine.fxml");
    }

    public void switchToExpensesTab(MouseEvent event) throws IOException {
        SwitchToTab(event, "Expenses.fxml");
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

