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

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;



public class DealingsController implements Initializable {

    @FXML
    private TableColumn<Orders,String> CustomerName;

    @FXML
    private TextField CustomerNameSearch;

    @FXML
    private ComboBox<String> CustomerNametxtField;

    @FXML
    ToggleGroup PaymentMethodradio;

    @FXML
    RadioButton PaymentCheck;
    @FXML
    RadioButton PaymentCash;
    @FXML
    RadioButton StatusPaid;
    @FXML
    RadioButton StatusUnpaid;


    @FXML
    private TableView<Supplies> SuppliesTable;

    @FXML
    private TableView<Supplies> USuppliesTable;

    @FXML
    private TableColumn<Orders,String> Distrbuter;

    @FXML
    private TextField DistrbuterNameSearch;

    @FXML
    private ComboBox<String> DistrbuterNametxtField;

    @FXML
    private TableColumn<Orders,Double> OrderCost;

    @FXML
    private TableColumn<Orders,String> OrderDate;

    @FXML
    private DatePicker OrderDateSearch;

    @FXML
    private DatePicker OrderDatetxtField;

    @FXML
    private TableColumn<Orders,Integer> OrderID;

    @FXML
    private TextField OrderIDSearch;

    @FXML
    private TextField OrderIDtxtField;

    @FXML
    private ComboBox<String> OrderMedIDtxtField;

    @FXML
    private TextField OrderMedicineSearch;

    @FXML
    private TableColumn<Orders,Integer> OrderQuantity;

    @FXML
    private TableColumn<Orders,String> OrderStatus;

    @FXML
    private TableView<Orders> OrderTable;

    @FXML
    private Button OrdersButton;

    @FXML
    private AnchorPane OrdersPane;

    @FXML
    private TableColumn<Orders,String> PaymentMethod;

    @FXML
    private TextField PaymentMethodtxtField;

    @FXML
    private TextField SupplierNameSearch;

    @FXML
    private TextField SupplyMedicineSearch;

    @FXML
    private ComboBox<String> SuppliertxtField;

    @FXML
    private Button SuppliesButton;

    @FXML
    private AnchorPane SuppliesPane;

    @FXML
    private TableColumn<Supplies, Double> SupplyCost;

    @FXML
    private TableColumn<Supplies, Double> SupplyCost1;

    @FXML
    private TextField SupplyCosttxtField;

    @FXML
    private TableColumn<Supplies, String> SupplyDate;

    @FXML
    private TableColumn<Supplies, String> SupplyDate1;

    @FXML
    private DatePicker SupplyDateSearch;

    @FXML
    private DatePicker SupplyDatetxtField;

    @FXML
    private TableColumn<Supplies, Integer> SupplyID;

    @FXML
    private TableColumn<Supplies, Integer> SupplyID1;

    @FXML
    private TextField SupplyIDSearch;

    @FXML
    private TextField SupplyIDtxtField;

    @FXML
    private TableColumn<Supplies, String> SupplyMedName;

    @FXML
    private TableColumn<Supplies, String> SupplyMedName1;

    @FXML
    private ComboBox<String> SupplyMedicinetxtField;

    @FXML
    private TableColumn<Supplies, Integer> SupplyQuantity;

    @FXML
    private TableColumn<Supplies, Integer> SupplyQuantity1;

    @FXML
    private TextField SupplyQuantitytxtField;

    @FXML
    private TextField OrderQuantitytxtField;

    @FXML
    private TableColumn<Supplies, String> SupplyStatus;

    @FXML
    private TableColumn<Supplies, String> SupplyStatus1;

    @FXML
    private TableColumn<Supplies, String> SupplySupplierName;

    @FXML
    private TableColumn<Supplies, String> SupplySupplierName1;
    @FXML
    private TableColumn<Orders, String> OrderMedicine;

    @FXML
    private CheckBox checkUnpaid;

    @FXML
    private CheckBox checkUrgent;

    @FXML
    private DialogPane dialog1;
    @FXML
    RadioButton StatusPaid1;
    @FXML
    RadioButton StatusUnpaid1;
    @FXML
    ToggleGroup SuppliesStatus;

    int SelectedIndexSupplies;
    int SelectedIndexOrders;

    ObservableList<Supplies> listSupplies = FXCollections.observableArrayList();
    ObservableList<Supplies> listSupplies1 = FXCollections.observableArrayList();
    ObservableList<String> SupplyMedicineCombo = FXCollections.observableArrayList();
    ObservableList<String> SupplySupplierCombo = FXCollections.observableArrayList();
    ObservableList<Orders> listOrders = FXCollections.observableArrayList();
    ObservableList<Orders> listOrdersCopy = FXCollections.observableArrayList();
    ObservableList<Orders> listOrdersUrgent = FXCollections.observableArrayList();
    ObservableList<Orders> listOrdersUnPaid = FXCollections.observableArrayList();

    ObservableList<String> DistrbuterCombo = FXCollections.observableArrayList();
    ObservableList<String> CustomerCombo = FXCollections.observableArrayList();
    ObservableList<String> OrderMedicineCombo = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (SuppliesButton != null) {
            SuppliesButton.setId("l2");
        }
        SupplyID.setCellValueFactory(new PropertyValueFactory<Supplies,Integer>("ID"));
        SupplyID1.setCellValueFactory(new PropertyValueFactory<Supplies,Integer>("ID"));
        SupplyDate.setCellValueFactory(new PropertyValueFactory<Supplies,String >("Date"));
        SupplyDate1.setCellValueFactory(new PropertyValueFactory<Supplies,String >("Date"));
        SupplyStatus.setCellValueFactory(new PropertyValueFactory<Supplies,String>("Status"));
        SupplyStatus1.setCellValueFactory(new PropertyValueFactory<Supplies,String>("Status"));
        SupplyCost.setCellValueFactory(new PropertyValueFactory<Supplies,Double>("Cost"));
        SupplyCost1.setCellValueFactory(new PropertyValueFactory<Supplies,Double>("Cost"));
        SupplyQuantity.setCellValueFactory(new PropertyValueFactory<Supplies,Integer>("quantity"));
        SupplyQuantity1.setCellValueFactory(new PropertyValueFactory<Supplies,Integer>("quantity"));
        SupplyMedName.setCellValueFactory(new PropertyValueFactory<Supplies,String>("MedName"));
        SupplyMedName1.setCellValueFactory(new PropertyValueFactory<Supplies,String>("MedName"));
        SupplySupplierName.setCellValueFactory(new PropertyValueFactory<Supplies,String>("SupplierName"));
        SupplySupplierName1.setCellValueFactory(new PropertyValueFactory<Supplies,String>("SupplierName"));
        UpdateSuppliesTable();
        SupplyMedicinetxtField.setItems(SupplyMedicineCombo);
        SuppliertxtField.setItems(SupplySupplierCombo);

        OrderID.setCellValueFactory(new PropertyValueFactory<Orders,Integer>("ID"));
        OrderDate.setCellValueFactory(new PropertyValueFactory<Orders,String>("Date"));
        OrderStatus.setCellValueFactory(new PropertyValueFactory<Orders,String>("Status"));
        OrderCost.setCellValueFactory(new PropertyValueFactory<Orders,Double>("cost"));
        PaymentMethod.setCellValueFactory(new PropertyValueFactory<Orders,String>("PaymentMethod"));
        OrderQuantity.setCellValueFactory(new PropertyValueFactory<Orders,Integer>("quantity"));
        OrderMedicine.setCellValueFactory(new PropertyValueFactory<Orders,String>("MedName"));
        CustomerName.setCellValueFactory(new PropertyValueFactory<Orders,String>("CustomerName"));
        Distrbuter.setCellValueFactory(new PropertyValueFactory<Orders,String>("DistrbuterName"));
        OrderMedIDtxtField.setItems(OrderMedicineCombo);
        DistrbuterNametxtField.setItems(DistrbuterCombo);
        CustomerNametxtField.setItems(CustomerCombo);
        UpdateOrdersTable();
    }


    public void UpdateOrdersTable(){
        try{
            if(!listOrders.isEmpty()){
                listOrders.clear();
            }
            if(!listOrdersCopy.isEmpty()) listOrdersCopy.clear();
            if(!listOrdersUrgent.isEmpty())listOrdersUrgent.clear();
            if(!listOrdersUnPaid.isEmpty())listOrdersUnPaid.clear();
            if(!CustomerCombo.isEmpty()) CustomerCombo.clear();
            if(!OrderMedicineCombo.isEmpty()) OrderMedicineCombo.clear();
            if(!DistrbuterCombo.isEmpty()) DistrbuterCombo.clear();

            Config con = new Config();
            Statement stm = con.makeStatemnt();
            String query = "SELECT * FROM "+con.DB+".Orders as o ";
            query += "JOIN "+con.DB+".Customer as c ON o.CustomerID = c.ID ";
            query += "JOIN "+con.DB+".Medicines as m ON o.MedicineID = m.MedicineID ";
            query += "JOIN "+con.DB+".Employee as e ON o.MSSN = e.SSN ORDER BY TID;";
            ResultSet res = stm.executeQuery(query);
            while(res.next()){

                int id = res.getInt("TID");
                String Date = res.getString("TDate");
                int Quantity = res.getInt("OrderQuantity");
                String Status = res.getString("Status");
                String PaymentMethod = res.getString("PaymentMethod");
                String MedName = res.getString("BrandName");
                String CustomerName = res.getString("Name");
                String DistrbuterName = res.getString("Fname") + " " + res.getString("Lname");
                double cost = Math.round((res.getDouble("TaxPercentage")* res.getDouble("PriceBTax")+res.getDouble("PriceBTax") ) * 100.0) / 100.0;
                Orders obj = new Orders(id,Date,Status,PaymentMethod,Quantity,cost,MedName,CustomerName,DistrbuterName);

                listOrders.add(obj);
                listOrdersCopy.add(obj);
                DistrbuterCombo.add(DistrbuterName + " ("+res.getInt("SSN")+")");
                CustomerCombo.add(CustomerName + " ("+res.getInt("ID")+")");
                OrderMedicineCombo.add(MedName + " ("+res.getInt("MedicineID")+")");

                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedDate = currentDate.format(formatter);
                boolean isUrgent = formattedDate.equals(Date);

                if(isUrgent) listOrdersUrgent.add(obj);

                if(Status.equals("UNPAID")) listOrdersUnPaid.add(obj);


            }

            con.closeConnection();
            OrderTable.setItems(listOrders);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void UpdateSuppliesTable(){
        try{
            if(!listSupplies.isEmpty()){
                listSupplies.clear();
            }
            if(!listSupplies1.isEmpty()){
                listSupplies1.clear();
            }
            if(!SupplyMedicineCombo.isEmpty()) SupplyMedicineCombo.clear();
            if(!SupplySupplierCombo.isEmpty()) SupplySupplierCombo.clear();
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            String query = "SELECT * FROM "+con.DB+".Supplies as s ";
            query += "JOIN "+con.DB+".Supplier as su ON s.SupplierID = su.ID ";
            query += "JOIN "+con.DB+".Medicines as m ON s.MedicineID = m.MedicineID ORDER BY TID;";
            ResultSet res = stm.executeQuery(query);
            while(res.next()){

                int id = res.getInt("TID");
                String Date = res.getString("TDate");
                double Cost = res.getDouble("SupplyCost");
                int Quantity = res.getInt("SupplyQuantity");
                String Status = res.getString("Status");
                String MedName = res.getString("BrandName");
                String SupplierName = res.getString("Name");
                Supplies obj = new Supplies(id,Date,Status,Cost,Quantity,MedName,SupplierName);

                listSupplies.add(obj);
                SupplySupplierCombo.add(SupplierName + " ("+res.getInt("ID")+")");
                SupplyMedicineCombo.add(MedName + " ("+res.getInt("MedicineID")+")");
                if(Status.equals("UNPAID")) listSupplies1.add(obj);


            }

            con.closeConnection();
            SuppliesTable.setItems(listSupplies);
            USuppliesTable.setItems(listSupplies1);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void AddSupply(){

        String id = SupplyIDtxtField.getText();
        LocalDate tmp = SupplyDatetxtField.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String SupplyDate = "";
        if(tmp != null)
            SupplyDate = tmp.format(formatter);
        String Status = "UNPAID";
        if(StatusPaid1.isSelected()) Status = "PAID";
        String Cost = SupplyCosttxtField.getText();
        String Quantity = SupplyQuantitytxtField.getText();
        String SupplierName = SuppliertxtField.getValue();
        String MedicineName = SupplyMedicinetxtField.getValue();

        if(Status.isEmpty()) Status = "UNPAID";

        boolean a = SupplierName.isEmpty() || MedicineName.isEmpty() || SupplyDate.isEmpty() || Quantity.isEmpty();
            if(a == true) {
                Alert msg = new Alert(Alert.AlertType.ERROR, "There is an empty field !!");
                msg.showAndWait();
                return;
            }
        BigDecimal supplycost= new BigDecimal(Cost);;
        try {
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            con.con.setAutoCommit(false);
            int supplierid = Integer.parseInt(SupplierName.substring(SupplierName.indexOf("(")+1, SupplierName.indexOf(")")));
            int medicineid = Integer.parseInt(MedicineName.substring(MedicineName.indexOf("(")+1,MedicineName.indexOf(")")));
            String query = "INSERT INTO "+Config.DB+".Supplies(TID,TDate,Status,SupplyCost,SupplyQuantity,MedicineID,SupplierID)";
            query+= " VALUES(nextval('"+Config.DB+".suppliesSeq'),'"+SupplyDate+"','"+Status+"',"+supplycost.doubleValue()+","+Integer.parseInt(Quantity)+","+medicineid+","+supplierid+");";
            //System.out.println(query);
            stm.executeUpdate(query);
            con.con.commit();
            con.closeConnection();


            UpdateSuppliesTable();
            emptySuppliestxtFields();
            Alert msg = new Alert(Alert.AlertType.INFORMATION,"The Row Has Been Added ");
            msg.showAndWait();



        }
        catch (StringIndexOutOfBoundsException e){
            Alert msg = new Alert(Alert.AlertType.ERROR,"You Must Specify Either SupplierName,Medicine BrandName");
            msg.showAndWait();
        }
        catch (Exception e){
            Alert msg = new Alert(Alert.AlertType.ERROR,e.getMessage());
            msg.showAndWait();
        }

    }

    public void UpdateSupply(){

        String id = SupplyIDtxtField.getText();
        LocalDate tmp = SupplyDatetxtField.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String Date = "";
        if(tmp != null)
            Date = tmp.format(formatter);
        String Status = "UNPAID";
        if(StatusPaid1.isSelected()) Status = "PAID";

        String SupplyCost = SupplyCosttxtField.getText();
        String Quantity = SupplyQuantitytxtField.getText();
        String SupplierName = SuppliertxtField.getValue();
        String MedicineName = SupplyMedicinetxtField.getValue();

        int supplierid = SupplierName.indexOf(")");
        int medicineid = MedicineName.indexOf(")");

        if(supplierid > -1) {
            supplierid = Integer.parseInt(SupplierName.substring(SupplierName.indexOf("(")+1, SupplierName.indexOf(")")));
        }
        if(medicineid > -1){
            medicineid = Integer.parseInt(MedicineName.substring(MedicineName.indexOf("(")+1,MedicineName.indexOf(")")));
        }


        boolean a = id.isEmpty() || SupplierName.isEmpty() || MedicineName.isEmpty() || Date.isEmpty() || Quantity.isEmpty() || SupplyCost.isEmpty();
        if(a == true) {
            Alert msg = new Alert(Alert.AlertType.ERROR, "There is an empty field !!");
            msg.showAndWait();
            return;
        }
        BigDecimal supplycost= new BigDecimal(SupplyCost);;
        try {
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            con.con.setAutoCommit(false);

            String query = "UPDATE "+con.DB+".Supplies SET ";
            query+= "TDate='"+Date+"',Status='"+Status+"',SupplyCost="+supplycost.doubleValue()+",SupplyQuantity="+Integer.parseInt(Quantity);
            if(medicineid > -1) query+= ",MedicineID="+medicineid;
            if(supplierid > -1) query+= ",CustomerID="+supplierid;
            query+=" where TID="+Integer.parseInt(id)+";";

            stm.executeUpdate(query);
            con.con.commit();
            con.closeConnection();

            UpdateSuppliesTable();
            emptySuppliestxtFields();
            //System.out.println(Status);
            Alert msg = new Alert(Alert.AlertType.INFORMATION,"The Row Has Been Updated ");
            msg.showAndWait();

        }
        catch (Exception e){
            Alert msg = new Alert(Alert.AlertType.ERROR,e.getMessage());
            msg.showAndWait();
        }

    }
    public void DeleteSupply(){


        String id = SupplyIDtxtField.getText();
        if(id.isEmpty()){
            Alert msg = new Alert(Alert.AlertType.ERROR,"The ID Field is Empty !!");
            msg.showAndWait();
            return;
        }
        try {
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            con.con.setAutoCommit(false);

            String query = "DELETE FROM "+con.DB+".Supplies " + "WHERE TID="+Integer.parseInt(id)+";";

            stm.executeUpdate(query);
            con.con.commit();
            con.closeConnection();

            UpdateSuppliesTable();
            emptySuppliestxtFields();

            Alert msg = new Alert(Alert.AlertType.INFORMATION,"The Row Has Been Deleted ");
            msg.showAndWait();

        }
        catch (Exception e){
            Alert msg = new Alert(Alert.AlertType.ERROR,e.getMessage());
            msg.showAndWait();
        }

    }
    public void AddOrder(){

        String id = OrderIDtxtField.getText();
        LocalDate tmp = OrderDatetxtField.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String OrderDate = "";
        if(tmp != null)
            OrderDate = tmp.format(formatter);
        String Status = "UNPAID";
        if(StatusPaid.isSelected()) Status = "PAID";

        String PaymentMethod = "Check";
        if(PaymentCash.isSelected()) PaymentMethod= "Cash";
        String Quantity = OrderQuantitytxtField.getText();
        String CustomerName = CustomerNametxtField.getValue();
        String MedicineName = OrderMedIDtxtField.getValue();
        String DistrbuterName = DistrbuterNametxtField.getValue();



        boolean a = CustomerName.isEmpty() || MedicineName.isEmpty() || OrderDate.isEmpty() || Quantity.isEmpty();
        if(a == true) {
            Alert msg = new Alert(Alert.AlertType.ERROR, "There is an empty field !!");
            msg.showAndWait();
            return;
        }

        try {
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            con.con.setAutoCommit(false);
            int customerid = Integer.parseInt(CustomerName.substring(CustomerName.indexOf("(")+1, CustomerName.indexOf(")")));
            int medicineid = Integer.parseInt(MedicineName.substring(MedicineName.indexOf("(")+1,MedicineName.indexOf(")")));
            int mssn = Integer.parseInt(DistrbuterName.substring(DistrbuterName.indexOf("(")+1,DistrbuterName.indexOf(")")));
            String query = "INSERT INTO "+Config.DB+".Orders(TID,TDate,Status,PaymentMethod,OrderQuantity,MedicineID,CustomerID,MSSN)";
            query+= " VALUES(nextval('"+Config.DB+".ordersSeq'),'"+OrderDate+"','"+Status+"','"+PaymentMethod+"',"+Integer.parseInt(Quantity)+","+medicineid+","+customerid+","+mssn+");";
            System.out.println(query);
            stm.executeUpdate(query);
            con.con.commit();
            con.closeConnection();


            UpdateOrdersTable();
            emptyOrderstxtFields();
            Alert msg = new Alert(Alert.AlertType.INFORMATION,"The Row Has Been Added ");
            msg.showAndWait();



        }
        catch (StringIndexOutOfBoundsException e){
            Alert msg = new Alert(Alert.AlertType.ERROR,"You Must Specify Either CustomerName,Medicine BrandName,DistrbuterName");
            msg.showAndWait();
        }
        catch (Exception e){
            Alert msg = new Alert(Alert.AlertType.ERROR,e.getMessage());
            msg.showAndWait();
        }

    }

    public void UpdateOrder(){

        String id = OrderIDtxtField.getText();
        LocalDate tmp = OrderDatetxtField.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String OrderDate = "";
        if(tmp != null)
            OrderDate = tmp.format(formatter);
        String Status = "UNPAID";
        if(StatusPaid.isSelected()) Status = "PAID";

        String PaymentMethod = "Check";
        if(PaymentCash.isSelected()) PaymentMethod= "Cash";
        String Quantity = OrderQuantitytxtField.getText();
        String CustomerName = CustomerNametxtField.getValue();
        String MedicineName = OrderMedIDtxtField.getValue();
        String DistrbuterName = DistrbuterNametxtField.getValue();
        int customerid = CustomerName.indexOf(")");
        int medicineid = MedicineName.indexOf(")");
        int mssn = DistrbuterName.indexOf(")");
        if(customerid > -1) {
            customerid = Integer.parseInt(CustomerName.substring(CustomerName.indexOf("(")+1, CustomerName.indexOf(")")));
        }
        if(medicineid > -1){
            medicineid = Integer.parseInt(MedicineName.substring(MedicineName.indexOf("(")+1,MedicineName.indexOf(")")));
        }
        if(mssn > -1){
            mssn = Integer.parseInt(DistrbuterName.substring(DistrbuterName.indexOf("(")+1,DistrbuterName.indexOf(")")));
        }



        boolean a = id.isEmpty() || CustomerName.isEmpty() || MedicineName.isEmpty() || OrderDate.isEmpty() || Quantity.isEmpty();
        if(a == true) {
            Alert msg = new Alert(Alert.AlertType.ERROR, "There is an empty field !!");
            msg.showAndWait();
            return;
        }

        try {
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            con.con.setAutoCommit(false);

            String query = "UPDATE "+con.DB+".Orders SET ";
            query+= "TDate='"+OrderDate+"',Status='"+Status+"',PaymentMethod='"+PaymentMethod+"',OrderQuantity="+Integer.parseInt(Quantity);
            if(medicineid > -1) query+= ",MedicineID="+medicineid;
            if(customerid > -1) query+= ",CustomerID="+customerid;
            if(mssn > -1) query+= ",MSSN="+mssn;
            query+=" where TID="+Integer.parseInt(id)+";";

            stm.executeUpdate(query);
            con.con.commit();
            con.closeConnection();

            UpdateOrdersTable();
            emptyOrderstxtFields();
            Alert msg = new Alert(Alert.AlertType.INFORMATION,"The Row Has Been Updated ");
            msg.showAndWait();

        }
        catch (Exception e){
            Alert msg = new Alert(Alert.AlertType.ERROR,e.getMessage());
            msg.showAndWait();
        }

    }

    public void DeleteOrder(){


        String id = OrderIDtxtField.getText();
        if(id.isEmpty()){
            Alert msg = new Alert(Alert.AlertType.ERROR,"The ID Field is Empty !!");
            msg.showAndWait();
            return;
        }
        try {
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            con.con.setAutoCommit(false);

            String query = "DELETE FROM "+con.DB+".Orders " + "WHERE TID="+Integer.parseInt(id)+";";

            stm.executeUpdate(query);
            con.con.commit();
            con.closeConnection();

            UpdateOrdersTable();
            emptyOrderstxtFields();

            Alert msg = new Alert(Alert.AlertType.INFORMATION,"The Row Has Been Deleted ");
            msg.showAndWait();

        }
        catch (Exception e){
            Alert msg = new Alert(Alert.AlertType.ERROR,e.getMessage());
            msg.showAndWait();
        }

    }

    public void generateReport(){
        try {
            Connection con;
            DriverManager.registerDriver(new org.postgresql.Driver());
            String ConnectionInfo = "jdbc:postgresql://localhost:5432/postgres";
            con = DriverManager.getConnection(ConnectionInfo, Config.user, Config.pass);
            Map<String, Object> parameters = new HashMap<>();
            String id = OrderIDSearch.getText();
            if(id.isEmpty()){
                Alert msg = new Alert(Alert.AlertType.ERROR,"Order ID must be filled to generate report !!");
                msg.showAndWait();
                return;
            }
            parameters.put("orderid", Integer.parseInt(id)); // Re
            InputStream inp = new FileInputStream("Simple_Blue.jrxml");
            JasperDesign jd = JRXmlLoader.load(inp);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr,parameters,con);
            OutputStream op= new FileOutputStream("Order"+id+".pdf");
            JasperExportManager.exportReportToPdfStream(jp,op);
            con.close();
            inp.close();
            op.close();
            Alert msg = new Alert(Alert.AlertType.INFORMATION,"Report Of OrderId : "+id+" Has Been Generated Successfully");
            msg.showAndWait();

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void emptySuppliestxtFields(){
        SupplyIDtxtField.setText("");
        SupplyDatetxtField.setValue(null);
        SupplyCosttxtField.setText("");
        SupplyStatus.setText("");
        SuppliertxtField.setValue("");
        SupplyMedicinetxtField.setValue("");
        SupplyQuantitytxtField.setText("");
    }
    public void emptyOrderstxtFields(){
        OrderIDtxtField.setText("");
        OrderDatetxtField.setValue(null);
        OrderQuantitytxtField.setText("");
        StatusPaid.setSelected(false);
        StatusUnpaid.setSelected(false);
        PaymentCash.setSelected(false);
        PaymentCheck.setSelected(false);
        CustomerNametxtField.setValue("");
        DistrbuterNametxtField.setValue("");
        OrderMedIDtxtField.setValue("");
    }
    public void SearchOrder(){
        String id = OrderIDSearch.getText();
        LocalDate tmp = OrderDateSearch.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String OrderDate = "";
        if(tmp != null)
            OrderDate = tmp.format(formatter);

        String CustomerName = CustomerNameSearch.getText();
        String MedicineName = OrderMedicineSearch.getText();
        String DistrbuterName = DistrbuterNameSearch.getText();
        String query = "SELECT * FROM "+Config.DB+".Orders as o ";
        query += "JOIN "+Config.DB+".Customer as c ON o.CustomerID = c.ID ";
        query += "JOIN "+Config.DB+".Medicines as m ON o.MedicineID = m.MedicineID ";
        query += "JOIN "+Config.DB+".Employee as e ON o.MSSN = e.SSN WHERE 1=1";
        int count = 0;
        if(!id.isEmpty()) {query += " AND TID = "+id;count++;OrderIDSearch.setText("");}
        if(!OrderDate.isEmpty()){query += " AND TDate = '"+OrderDate+"'";count++;OrderDateSearch.setValue(null);}
        if(!CustomerName.isEmpty()){query += " AND Name like '%"+CustomerName+"%'";count++;CustomerNameSearch.setText("");}
        if(!MedicineName.isEmpty()){query += " AND BrandName like '%"+MedicineName+"%'";count++;OrderMedicineSearch.setText("");}
        if(!DistrbuterName.isEmpty()){
            query += " AND (Fname like '%"+DistrbuterName+"%'";count++;
            query += " OR Lname like '%"+DistrbuterName+"%')";count++;
            DistrbuterNameSearch.setText("");
        }
        if(count == 0)return;
        query +=" ORDER BY TID;"; // sort by Supplier id

        try{
            listOrders.clear();
            Config con = new Config();
            Statement stm = con.makeStatemnt();

            ResultSet rs = stm.executeQuery(query);
            while(rs.next()){

                Orders obj = new Orders(rs.getInt("TID")
                        ,rs.getString("TDate"),
                        rs.getString("Status"),
                        rs.getString("PaymentMethod"),
                        rs.getInt("OrderQuantity"),
                Math.round((rs.getDouble("TaxPercentage")* rs.getDouble("PriceBTax")+rs.getDouble("PriceBTax") ) * 100.0) / 100.0,
                        rs.getString("BrandName"),
                        rs.getString("Name"),
                        rs.getString("Fname")+ " "+rs.getString("Lname")


                );
                listOrders.add(obj);
            }
            con.closeConnection();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void SearchSupply(){
        String id = SupplyIDSearch.getText();
        LocalDate tmp = SupplyDateSearch.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String SupplyDate = "";
        if(tmp != null)
            SupplyDate = tmp.format(formatter);

        String SupplierName = SupplierNameSearch.getText();
        String MedicineName = SupplyMedicineSearch.getText();
        String query = "SELECT * FROM "+Config.DB+".Supplies as s ";
        query += "JOIN "+Config.DB+".Supplier as su ON s.SupplierID = su.ID ";
        query += "JOIN "+Config.DB+".Medicines as m ON s.MedicineID = m.MedicineID WHERE 1=1";
        int count = 0;
        if(!id.isEmpty()) {query += " AND TID = "+id;count++;SupplyIDSearch.setText("");}
        if(!SupplyDate.isEmpty()){query += " AND TDate = '"+SupplyDate+"'";count++;SupplyDateSearch.setValue(null);}
        if(!SupplierName.isEmpty()){query += " AND Name like '%"+SupplierName+"%'";count++;SupplierNameSearch.setText("");}
        if(!MedicineName.isEmpty()){query += " AND BrandName like '%"+MedicineName+"%'";count++;SupplyMedicineSearch.setText("");}

        if(count == 0)return;
        query +=" ORDER BY TID;"; // sort by Supplier id

        try{
            listSupplies.clear();
            Config con = new Config();
            Statement stm = con.makeStatemnt();

            ResultSet rs = stm.executeQuery(query);
            while(rs.next()){

                Supplies obj = new Supplies(rs.getInt("TID")
                        ,rs.getString("TDate"),
                        rs.getString("Status"),
                        rs.getDouble("SupplyCost"),
                        rs.getInt("SupplyQuantity"),
                        rs.getString("BrandName"),
                        rs.getString("Name")

                );
                listSupplies.add(obj);
            }
            con.closeConnection();
            closeSearchSupply();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void selectUnpaid(){
        if(!checkUnpaid.isSelected()) {
            if(!checkUrgent.isSelected()) {listOrders.clear();listOrders.addAll(listOrdersCopy);return;}
            else {selectUrgent();return;}
        }

        int n = listOrdersCopy.size();
        listOrders.clear();

        for(int i = 0;i<n;i++){
            String Unpaid = listOrdersCopy.get(i).getStatus();
            String Date = listOrdersCopy.get(i).getDate();
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(formatter);
            boolean isUrgent = formattedDate.equals(Date);
            if(checkUrgent.isSelected() && isUrgent) listOrders.add(listOrdersCopy.get(i));
            if(checkUnpaid.isSelected() && Unpaid.equals("UNPAID")) listOrders.add(listOrdersCopy.get(i));
        }
    }
    public void selectUrgent(){
        if(!checkUrgent.isSelected()) {
            if(!checkUnpaid.isSelected()) {listOrders.clear();listOrders.addAll(listOrdersCopy);return;}
            else {selectUnpaid();return;}
        }
        int n = listOrdersCopy.size();
        listOrders.clear();

        for(int i = 0;i<n;i++){
            String Unpaid = listOrdersCopy.get(i).getStatus();
            String Date = listOrdersCopy.get(i).getDate();
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(formatter);
            boolean isUrgent = formattedDate.equals(Date);
            if(checkUrgent.isSelected() && isUrgent) listOrders.add(listOrdersCopy.get(i));
            if(checkUnpaid.isSelected() && Unpaid.equals("UNPAID")) listOrders.add(listOrdersCopy.get(i));
        }
    }
    public void getOrderItem(MouseEvent event) throws IOException {

        SelectedIndexOrders = OrderTable.getSelectionModel().getSelectedIndex();
        if (SelectedIndexOrders < 0) return;

        Orders tmp = OrderTable.getItems().get(SelectedIndexOrders);

        OrderIDtxtField.setText(String.valueOf(tmp.getID()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        OrderDatetxtField.setValue(LocalDate.parse(tmp.getDate(), formatter));
        OrderQuantitytxtField.setText(String.valueOf(tmp.getQuantity()));
        String PaymentMethod = tmp.getPaymentMethod();
        if(PaymentMethod.equals("Check")){

            PaymentCash.setSelected(false);
            PaymentCheck.setSelected(true);

        }
        else {

            PaymentCash.setSelected(true);
            PaymentCheck.setSelected(false);
        }

        String Status = tmp.getStatus();
        if(Status.equals("UNPAID")){
            StatusUnpaid.setSelected(true);
            StatusPaid.setSelected(false);
        }
        else {
            StatusUnpaid.setSelected(false);
            StatusPaid.setSelected(true);
        }
        CustomerNametxtField.setValue(tmp.getCustomerName());
        OrderMedIDtxtField.setValue(tmp.getMedName());
        DistrbuterNametxtField.setValue(tmp.getDistrbuterName());

    }
    public void getSupplyItem(MouseEvent event) throws IOException{

        SelectedIndexSupplies = SuppliesTable.getSelectionModel().getSelectedIndex();
        if(SelectedIndexSupplies < 0 )return;

        Supplies tmp = SuppliesTable.getItems().get(SelectedIndexSupplies);

        SupplyIDtxtField.setText(String.valueOf(tmp.getID()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        SupplyDatetxtField.setValue(LocalDate.parse(tmp.getDate(),formatter));
        SupplyCosttxtField.setText(String.valueOf(tmp.getCost()));
        SupplyStatus.setText(tmp.getStatus());
        SuppliertxtField.setValue(tmp.getSupplierName());
        SupplyMedicinetxtField.setValue(tmp.getMedName());
        SupplyQuantitytxtField.setText(String.valueOf(tmp.getQuantity()));
        String Status = tmp.getStatus();
        if(Status.equals("UNPAID")){
            StatusUnpaid1.setSelected(true);
            StatusPaid1.setSelected(false);
        }
        else {
            StatusUnpaid1.setSelected(false);
            StatusPaid1.setSelected(true);
        }

    }

    public void getSupplyItem2(MouseEvent event) throws IOException{
        int selecteunpaid = USuppliesTable.getSelectionModel().getSelectedIndex();
        if(selecteunpaid < 0 )return;
        Supplies tmp = USuppliesTable.getItems().get(selecteunpaid);

        SupplyIDtxtField.setText(String.valueOf(tmp.getID()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        SupplyDatetxtField.setValue(LocalDate.parse(tmp.getDate(),formatter));
        SupplyCosttxtField.setText(String.valueOf(tmp.getCost()));
        SupplyStatus.setText(tmp.getStatus());
        SuppliertxtField.setValue(tmp.getSupplierName());
        SupplyMedicinetxtField.setValue(tmp.getMedName());
        SupplyQuantitytxtField.setText(String.valueOf(tmp.getQuantity()));
        String Status = tmp.getStatus();
        if(Status.equals("UNPAID")){
            StatusUnpaid1.setSelected(true);
            StatusPaid1.setSelected(false);
        }
        else {
            StatusUnpaid1.setSelected(false);
            StatusPaid1.setSelected(true);
        }
    }
    public void closeSearchSupply() {
        SuppliesPane.setDisable(false);
        SuppliesButton.setDisable(false);
        OrdersButton.setDisable(false);
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

    public void showSearchSupply(ActionEvent event) {
        SuppliesPane.setDisable(true);
        SuppliesButton.setDisable(true);
        OrdersButton.setDisable(true);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(250), dialog1);
        scaleTransition.setFromX(0); // Initial X scale factor
        scaleTransition.setFromY(0); // Initial Y scale factor
        scaleTransition.setToX(1);   // Final X scale factor
        scaleTransition.setToY(1);   // Final Y scale factor
        scaleTransition.play();
        dialog1.setVisible(true);
    }

    public void showOrders(){
        SuppliesPane.setVisible(false);
        OrdersPane.setVisible(true);
        OrdersButton.setId("l2");
        SuppliesButton.setId("");

    }

    public void showSupplies(){
        SuppliesPane.setVisible(true);
        OrdersPane.setVisible(false);
        OrdersButton.setId("");
        SuppliesButton.setId("l2");

    }
    public void switchToHomeTab(MouseEvent event) throws IOException {
        SwitchToTab(event,"HomePage.fxml");
    }

    public void switchToDealersTab(MouseEvent event) throws IOException {
        SwitchToTab(event,"Dealers.fxml");

    }
    public void switchToMedicineTab(MouseEvent event) throws IOException {
        SwitchToTab(event,"Medicine.fxml");
    }
    public void switchToEmployeeTab(MouseEvent event) throws IOException {
        SwitchToTab(event,"Employee.fxml");
    }

    public void switchToExpensesTab(MouseEvent event) throws IOException {
        SwitchToTab(event, "Expenses.fxml");
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
