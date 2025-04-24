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
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DealersController implements Initializable {
    @FXML
    private TableColumn<Customer, String> AddressCustomer;

    @FXML
    private TableColumn<Supplier, String> AddressSupplier;

    @FXML
    private TextField CustomerAddresstxtField;

    @FXML
    private TextField CustomerEmailtxtField;

    @FXML
    private TableColumn<Customer, Integer> CustomerID;

    @FXML
    private TextField CustomerIdtxtField;

    @FXML
    private TableColumn<Customer, String> CustomerName;

    @FXML
    private TextField CustomerNametxtField;

    @FXML
    private TextField CustomerPhonetxtField;

    @FXML
    private Button CustomersButton;

    @FXML
    private AnchorPane CustomersPane;

    @FXML
    private TableView<Customer> CustomersTable;

    @FXML
    private TableColumn<Customer, Integer> Discount;

    @FXML
    private TextField DiscounttxtField;

    @FXML
    private TableColumn<Customer, String> EmailCustomer;

    @FXML
    private TableColumn<Supplier,String> EmailSupplier;

    @FXML
    private TableColumn<Customer, Integer> PhoneCustomer;

    @FXML
    private TableColumn<Supplier, Integer> PhoneSupplier;

    @FXML
    private TableColumn<Supplier, String> Scope;

    @FXML
    private ComboBox<String> ScopetxtField;

    @FXML
    private TextField SupplierAddresstxtField;

    @FXML
    private TextField SupplierEmailtxtField;

    @FXML
    private TableColumn<Supplier, Integer> SupplierID;

    @FXML
    private TextField SupplierIDtxtField;

    @FXML
    private TableColumn<Supplier, String> SupplierName;

    @FXML
    private TextField SupplierNametxtField;

    @FXML
    private AnchorPane SupplierPane;

    @FXML
    private TextField SupplierPhonetxtField;

    @FXML
    private Button SuppliersButton;

    @FXML
    private TableView<Supplier> SuppliersTable;

    @FXML
    private CheckBox checkLocalScope;

    @FXML
    private CheckBox checkGlobalScope;
    @FXML
    private TextField SupplierIDSearch;
    @FXML
    private TextField SupplierNameSearch;
    @FXML
    private TextField SupplierAdressSearch;
    @FXML
    private TextField SupplierPhoneSearch;

    @FXML
    private TextField CustomerIDSearch;
    @FXML
    private TextField CustomerNameSearch;
    @FXML
    private TextField CustomerAddressSearch;
    @FXML
    private TextField CustomerPhoneSearch;
    @FXML
    private TextField LowerBetween;
    @FXML
    private TextField UpperBetween;

    @FXML
    private DialogPane dialog1;
    @FXML
    private DialogPane dialog2;
    Integer SelectedIndexSupplier;
    Integer SelectedIndexCustomer;


    ObservableList<Supplier> listSupplier = FXCollections.observableArrayList();
    ObservableList<Supplier> listSupplierCopy = FXCollections.observableArrayList();
    ObservableList<Customer> listCustomer = FXCollections.observableArrayList();
    ObservableList<Supplier> listSupplierwithLocal = FXCollections.observableArrayList();
    ObservableList<Supplier> listSupplierwithGlobal = FXCollections.observableArrayList();
    ObservableList<Customer> listCustomerCopy = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (SuppliersButton != null) {

            SuppliersButton.setId("l2");
        }
        ScopetxtField.getItems().addAll("Local","Global");
        SupplierID.setCellValueFactory(new PropertyValueFactory<Supplier,Integer>("ID"));
        PhoneSupplier.setCellValueFactory(new PropertyValueFactory<Supplier,Integer>("Phone"));
        SupplierName.setCellValueFactory(new PropertyValueFactory<Supplier,String>("Name"));
        AddressSupplier.setCellValueFactory(new PropertyValueFactory<Supplier,String>("Address"));
        EmailSupplier.setCellValueFactory(new PropertyValueFactory<Supplier,String>("Email"));
        Scope.setCellValueFactory(new PropertyValueFactory<Supplier,String>("Scope"));

        CustomerID.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("ID"));
        CustomerName.setCellValueFactory(new PropertyValueFactory<Customer,String>("Name"));
        PhoneCustomer.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("Phone"));
        EmailCustomer.setCellValueFactory(new PropertyValueFactory<Customer,String>("Email"));
        AddressCustomer.setCellValueFactory(new PropertyValueFactory<Customer,String>("Address"));
        Discount.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("Discount"));
        UpdateSupplierTable();
        UpdateCustomerTable();
    }

    public void UpdateSupplierTable(){
        try{
            if(!listSupplier.isEmpty()){
                listSupplier.clear();
                if(!listSupplierCopy.isEmpty()) listSupplierCopy.clear();
                if(!listSupplierwithGlobal.isEmpty())listSupplierwithGlobal.clear();
                if(!listSupplierwithLocal.isEmpty()) listSupplierwithLocal.clear();
            }
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            String query = "Select * from "+con.DB+".Supplier;";
            ResultSet res = stm.executeQuery(query);
            while(res.next()){
                boolean flag = res.getBoolean("flag_hidden");
                if(flag == true) continue;
                int id = res.getInt("ID");
                String SupplierName = res.getString("Name");
                double Phone = res.getDouble("Phone");
                String Address = res.getString("Address");
                String Scope = res.getString("Scope");
                String Email = res.getString("Email");
                Supplier obj = new Supplier(id,SupplierName,(long)Phone,Email,Address,Scope);

                if(Scope.equals("Global")) listSupplierwithGlobal.add(obj);
                else listSupplierwithLocal.add(obj);
                listSupplier.add(obj);
                listSupplierCopy.add(obj);

            }

            con.closeConnection();
            SuppliersTable.setItems(listSupplier);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void UpdateCustomerTable(){
        try{
            if(!listCustomer.isEmpty()){
                listCustomer.clear();
                if(!listCustomerCopy.isEmpty()) listCustomerCopy.clear();
            }
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            String query = "Select * from "+con.DB+".Customer;";
            ResultSet res = stm.executeQuery(query);
            while(res.next()){
                boolean flag = res.getBoolean("flag_hidden");
                if(flag == true) continue;
                int id = res.getInt("ID");
                String CustomerName = res.getString("Name");
                double Phone = res.getDouble("Phone");
                String Address = res.getString("Address");
                String Email = res.getString("Email");
                int discount = res.getInt("Discount");
                Customer obj = new Customer(id,CustomerName,(long)Phone,Email,Address,discount);
                listCustomer.add(obj);
                listCustomerCopy.add(obj);

            }

            con.closeConnection();
            CustomersTable.setItems(listCustomer);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public void getSupplierItem(MouseEvent event) throws IOException{

        SelectedIndexSupplier = SuppliersTable.getSelectionModel().getSelectedIndex();
        if(SelectedIndexSupplier < 0) return;

        Supplier tmp = SuppliersTable.getItems().get(SelectedIndexSupplier);

        SupplierIDtxtField.setText(String.valueOf(tmp.getID()));
        SupplierNametxtField.setText(tmp.getName());
        SupplierEmailtxtField.setText(tmp.getEmail());
        SupplierAddresstxtField.setText(tmp.getAddress());
        ScopetxtField.setValue(tmp.getScope());
        SupplierPhonetxtField.setText(String.valueOf(tmp.getPhone()));

    }

    public void getCustomerItem(MouseEvent event) throws IOException{
        SelectedIndexCustomer = CustomersTable.getSelectionModel().getSelectedIndex();
        if(SelectedIndexCustomer < 0) return;

        Customer tmp = CustomersTable.getItems().get(SelectedIndexCustomer);

        CustomerIdtxtField.setText(String.valueOf(tmp.getID()));
        CustomerNametxtField.setText(tmp.getName());
        CustomerEmailtxtField.setText(tmp.getEmail());
        CustomerAddresstxtField.setText(tmp.getAddress());
        CustomerPhonetxtField.setText(String.valueOf(tmp.getPhone()));
        DiscounttxtField.setText(String.valueOf(tmp.getDiscount()));
    }

    public void SelectScopeGlobal(){
        if(checkGlobalScope.isSelected()){

            if(checkLocalScope.isSelected()) {
                listSupplier.clear();
                listSupplier.addAll(listSupplierCopy);
            }
            else {
                listSupplier.clear();
                listSupplier.addAll(listSupplierwithGlobal);
            }
        }
        else{
            if(checkLocalScope.isSelected()) {
                listSupplier.clear();
                listSupplier.addAll(listSupplierwithLocal);
            }
            else {
                listSupplier.clear();
                listSupplier.addAll(listSupplierCopy);
            }
        }

        //SuppliersTable.setItems(listSupplier);
    }
    public void SelectScopeLocal(){
        if(checkLocalScope.isSelected()){

            if(checkGlobalScope.isSelected()) {
                listSupplier.clear();
                listSupplier.addAll(listSupplierCopy);
            }
            else {
                listSupplier.clear();
                listSupplier.addAll(listSupplierwithLocal);
            }
        }
        else{
            if(checkGlobalScope.isSelected()) {
                listSupplier.clear();
                listSupplier.addAll(listSupplierwithGlobal);
            }
            else {
                listSupplier.clear();
                listSupplier.addAll(listSupplierCopy);
            }
        }
    }
    public void SearchSupplier(){
        String id = SupplierIDSearch.getText();
        String SupplierName = SupplierNameSearch.getText();
        String SupplierPhone = SupplierPhoneSearch.getText();
        String SupplierAddress = SupplierAdressSearch.getText();
        String query = "SELECT * FROM "+Config.DB+".Supplier WHERE 1=1";
        int count = 0;
        if(!id.isEmpty()) {query += " AND ID = "+id;count++;SupplierIDSearch.setText("");}
        if(!SupplierName.isEmpty()){query += " AND Name like '%"+SupplierName+"%'";count++;SupplierNameSearch.setText("");}
        if(!SupplierPhone.isEmpty()){query += " AND Phone like %"+SupplierPhone+"%";count++;SupplierPhoneSearch.setText("");}
        if(!SupplierAddress.isEmpty()){query += " AND Address like '%"+SupplierAddress+"%'";count++;SupplierAdressSearch.setText("");}
        if(count == 0)return;
        query +=" ORDER BY ID;"; // sort by Supplier id

        try{
            listSupplier.clear();
            Config con = new Config();
            Statement stm = con.makeStatemnt();

            ResultSet rs = stm.executeQuery(query);
            while(rs.next()){
                boolean flag = rs.getBoolean("flag_hidden");
                if(flag == true) continue;
                Supplier obj = new Supplier(rs.getInt("ID")
                ,rs.getString("Name"), (int)rs.getDouble("Phone"), rs.getString("Email"), rs.getString("Address"),rs.getString("Scope")
                );
                listSupplier.add(obj);
            }
            con.closeConnection();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        closeSearchSupply();
    }
    public void SearchCustomer(){
        String id = CustomerIDSearch.getText();
        String CustomerName = CustomerNameSearch.getText();
        String CustomerPhone = CustomerPhoneSearch.getText();
        String CustomerAddress = CustomerAddressSearch.getText();
        String query = "SELECT * FROM "+Config.DB+".Customer WHERE 1=1";
        int count = 0;
        if(!id.isEmpty()) {query += " AND ID = "+id;count++;CustomerIDSearch.setText("");}
        if(!CustomerName.isEmpty()){query += " AND Name like '%"+CustomerName+"%'";count++;CustomerNameSearch.setText("");}
        if(!CustomerPhone.isEmpty()){query += " AND Phone like %"+CustomerPhone+"%";count++;CustomerPhoneSearch.setText("");}
        if(!CustomerAddress.isEmpty()){query += " AND Address like '%"+CustomerAddress+"%'";count++;CustomerAddressSearch.setText("");}
        if(count == 0)return;
        query +=" ORDER BY ID;"; // sort by Supplier id

        try{
            listCustomer.clear();
            Config con = new Config();
            Statement stm = con.makeStatemnt();

            ResultSet rs = stm.executeQuery(query);
            while(rs.next()){
                boolean flag = rs.getBoolean("flag_hidden");
                if(flag == true) continue;
                Customer obj = new Customer(rs.getInt("ID")
                        ,rs.getString("Name"), (int)rs.getDouble("Phone"), rs.getString("Email"), rs.getString("Address"),
                        rs.getInt("Discount")
                );
                listCustomer.add(obj);
            }
            con.closeConnection();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        closeSearchCustomer();
    }
    public void SearchBetween(){
        try{
            int lower;
            int upper;
            if(LowerBetween.getText().isEmpty()) lower = 0;
            else  lower = Integer.parseInt(LowerBetween.getText());
            if(UpperBetween.getText().isEmpty()) {
                listCustomer.clear();
                listCustomer.addAll(listCustomerCopy);
                return;
            }
            else upper = Integer.parseInt(UpperBetween.getText());
            String query = "SELECT * FROM "+Config.DB +".Customer";
            query += " WHERE Discount Between "+lower+" And "+upper+";";
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            listCustomer.clear();
            ResultSet rs = stm.executeQuery(query);
            while(rs.next()){
                Customer obj = new Customer(rs.getInt("ID")
                        ,rs.getString("Name"), (int)rs.getDouble("Phone"), rs.getString("Email"), rs.getString("Address"),
                        rs.getInt("Discount")
                );
                listCustomer.add(obj);
            }
            con.closeConnection();

        }
        catch (NumberFormatException e){
            Alert msg = new Alert(Alert.AlertType.ERROR,"You must enter Integer Numbers");
            UpperBetween.setText("");
            LowerBetween.setText("");
            msg.showAndWait();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void AddSupplier(){

        String id = SupplierIDtxtField.getText();
        String SupplierName  = SupplierNametxtField.getText();
        String Phone = SupplierPhonetxtField.getText();
        String Email = SupplierEmailtxtField.getText();
        String Address = SupplierAddresstxtField.getText();
        String Scope = (ScopetxtField.getValue()==null) ? "Local" :  ScopetxtField.getValue();


        boolean a =  SupplierName.isEmpty() || Phone.isEmpty() || Email.isEmpty() || Scope.isEmpty();
        if(a == true){
            Alert msg = new Alert(Alert.AlertType.ERROR,"There is an empty field !!");
            msg.showAndWait();
            return;
        }


        try {
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            con.con.setAutoCommit(false);

            String query = "INSERT INTO "+Config.DB+".Supplier(ID,Name,Phone,Email,Address,Scope)";
            query+= " VALUES(nextval('"+Config.DB+".supplierSeq'),'"+SupplierName+"',"+Phone+",'"+Email+"','"+Address+"','"+Scope+"');";

            stm.executeUpdate(query);
            con.con.commit();
            con.closeConnection();


            UpdateSupplierTable();
            emptySupplierTxtFields();
            Alert msg = new Alert(Alert.AlertType.INFORMATION,"The Row Has Been Added ");
            msg.showAndWait();



        }
        catch (Exception e){
            Alert msg = new Alert(Alert.AlertType.ERROR,e.getMessage());
            msg.showAndWait();
        }

    }
    public void AddCustomer(){

        String id = CustomerIdtxtField.getText();
        String CustomerName  = CustomerNametxtField.getText();
        String Phone = CustomerPhonetxtField.getText();
        String Email = CustomerEmailtxtField.getText();
        String Address = CustomerAddresstxtField.getText();
        String Discount = DiscounttxtField.getText();


        boolean a = CustomerName.isEmpty() || Phone.isEmpty() || Email.isEmpty() || Address.isEmpty();
        if(a == true){
            Alert msg = new Alert(Alert.AlertType.ERROR,"There is an empty field !!");
            msg.showAndWait();
            return;
        }

        int discount = Discount.isEmpty() ? 0 : Integer.parseInt(Discount);

        try {
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            con.con.setAutoCommit(false);

            String query = "INSERT INTO "+Config.DB+".Customer(ID,Name,Phone,Email,Address,Discount)";
            query+= " VALUES(nextval('"+Config.DB+".customerSeq'),'"+CustomerName+"',"+Phone+",'"+Email+"','"+Address+"',"+discount+");";
            stm.executeUpdate(query);
            con.con.commit();
            con.closeConnection();
            UpdateCustomerTable();
            /*Customer obj = new Customer(Integer.parseInt(id),CustomerName,Long.parseLong(Phone),Email,Address,discount);

            listCustomer.add(obj);
            listCustomerCopy.clear();
            listCustomerCopy.addAll(listCustomer);*/
            emptyCustomerTxtFields();
            Alert msg = new Alert(Alert.AlertType.INFORMATION,"The Row Has Been Added ");
            msg.showAndWait();
            CustomersTable.setItems(listCustomer);

        }
        catch (Exception e){
            Alert msg = new Alert(Alert.AlertType.ERROR,e.getMessage());
            msg.showAndWait();
        }

    }
    public void UpdateSupplier(){

        String id = SupplierIDtxtField.getText();
        String SupplierName  = SupplierNametxtField.getText();
        String Phone = SupplierPhonetxtField.getText();
        String Email = SupplierEmailtxtField.getText();
        String Address = SupplierAddresstxtField.getText();
        String Scope = ScopetxtField.getValue().isEmpty() ? "Local" : ScopetxtField.getValue();
        boolean a = id.isEmpty() || SupplierName.isEmpty() || Phone.isEmpty() || Email.isEmpty() || Scope.isEmpty();
        if(a == true){
            Alert msg = new Alert(Alert.AlertType.ERROR,"There is an empty field !!");
            msg.showAndWait();
            return;
        }


        try {
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            con.con.setAutoCommit(false);

            String query = "UPDATE "+con.DB+".Supplier SET ";
            query+= "Name='"+SupplierName+"',Email='"+Email+"',Address='"+Address+"',Scope='"+Scope+"',Phone="+Long.parseLong(Phone);
            query+=" where ID="+Integer.parseInt(id)+";";

            stm.executeUpdate(query);
            con.con.commit();
            con.closeConnection();
            Supplier obj = new Supplier(Integer.parseInt(id),SupplierName,Long.parseLong(Phone),Email,Address,Scope);
            UpdateSupplierTable();
            Alert msg = new Alert(Alert.AlertType.INFORMATION,"The Row Has Been Updated");
            msg.showAndWait();

        }
        catch (Exception e){
            Alert msg = new Alert(Alert.AlertType.ERROR,e.getMessage());
            msg.showAndWait();
        }

    }
    public void UpdateCustomer(){

        String id = CustomerIdtxtField.getText();
        String CustomerName  = CustomerNametxtField.getText();
        String Phone = CustomerPhonetxtField.getText();
        String Email = CustomerEmailtxtField.getText();
        String Address = CustomerAddresstxtField.getText();
        String Discount = DiscounttxtField.getText();


        boolean a = id.isEmpty() || CustomerName.isEmpty() || Phone.isEmpty() || Email.isEmpty() || Address.isEmpty();
        if(a == true){
            Alert msg = new Alert(Alert.AlertType.ERROR,"There is an empty field !!");
            msg.showAndWait();
            return;
        }

        int discount = Discount.isEmpty() ? 0 : Integer.parseInt(Discount);


        try {
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            con.con.setAutoCommit(false);

            String query = "UPDATE "+con.DB+".Customer SET ";
            query+= "Name='"+CustomerName+"',Email='"+Email+"',Address='"+Address+"',Discount="+discount+",Phone="+Long.parseLong(Phone);
            query+=" where ID="+Integer.parseInt(id)+";";

            stm.executeUpdate(query);
            con.con.commit();
            con.closeConnection();
            Customer obj = new Customer(Integer.parseInt(id),CustomerName,Long.parseLong(Phone),Email,Address,discount);
            int idx = -1;
            for(int i =0;i<listCustomer.size();i++){
                if(listCustomer.get(i).getID() == Integer.parseInt(id)){
                    idx = i;
                    break;
                }
            }
            listCustomer.remove(idx);
            listCustomer.add(idx,obj);
            listCustomerCopy.clear();
            listCustomerCopy.addAll(listCustomer);
            Alert msg = new Alert(Alert.AlertType.INFORMATION,"The Row Has Been Updated");
            msg.showAndWait();

        }
        catch (Exception e){
            Alert msg = new Alert(Alert.AlertType.ERROR,e.getMessage());
            msg.showAndWait();
        }

    }

    public void DeleteSupplier(){


        String id = SupplierIDtxtField.getText();
        if(id.isEmpty()){
            Alert msg = new Alert(Alert.AlertType.ERROR,"The ID Field is Empty !!");
            msg.showAndWait();
            return;
        }
        try {
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            con.con.setAutoCommit(false);

            String query = "Update "+con.DB+".Supplier Set flag_hidden=true " + "WHERE ID="+Integer.parseInt(id)+";";

            stm.executeUpdate(query);
            con.con.commit();
            con.closeConnection();

            UpdateSupplierTable();
            emptySupplierTxtFields();

        }
        catch (Exception e){
            Alert msg = new Alert(Alert.AlertType.ERROR,e.getMessage());
            msg.showAndWait();
        }

    }
    public void DeleteCustomer(){


        String id = CustomerIdtxtField.getText();
        if(id.isEmpty()){
            Alert msg = new Alert(Alert.AlertType.ERROR,"The ID Field is Empty !!");
            msg.showAndWait();
            return;
        }
        try {
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            con.con.setAutoCommit(false);

            String query = "Update "+con.DB+".Customer Set flag_hidden=true " + "WHERE ID="+Integer.parseInt(id)+";";

            stm.executeUpdate(query);
            con.con.commit();
            con.closeConnection();

            int idx = -1;
            for(int i =0;i<listCustomer.size();i++){
                if(listCustomer.get(i).getID() == Integer.parseInt(id)){
                    idx = i;
                    break;
                }
            }
            listCustomer.remove(idx);
            listCustomerCopy.clear();
            listCustomerCopy.addAll(listCustomer);
            emptyCustomerTxtFields();

        }
        catch (Exception e){
            Alert msg = new Alert(Alert.AlertType.ERROR,e.getMessage());
            msg.showAndWait();
        }

    }
    public void showSearchSupply(){
        SupplierPane.setDisable(true);
        SuppliersButton.setDisable(true);
        CustomersButton.setDisable(true);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(250), dialog1);
        scaleTransition.setFromX(0); // Initial X scale factor
        scaleTransition.setFromY(0); // Initial Y scale factor
        scaleTransition.setToX(1);   // Final X scale factor
        scaleTransition.setToY(1);   // Final Y scale factor
        scaleTransition.play();
        dialog1.setVisible(true);
    }
    public void closeSearchSupply(){
        SupplierPane.setDisable(false);
        SuppliersButton.setDisable(false);
        CustomersButton.setDisable(false);
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

    public void showSearchCustomer(){
        CustomersPane.setDisable(true);
        SuppliersButton.setDisable(true);
        CustomersButton.setDisable(true);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(250), dialog2);
        scaleTransition.setFromX(0); // Initial X scale factor
        scaleTransition.setFromY(0); // Initial Y scale factor
        scaleTransition.setToX(1);   // Final X scale factor
        scaleTransition.setToY(1);   // Final Y scale factor
        scaleTransition.play();
        dialog2.setVisible(true);
    }
    public void closeSearchCustomer(){
        CustomersPane.setDisable(false);
        SuppliersButton.setDisable(false);
        CustomersButton.setDisable(false);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(250), dialog2);
        scaleTransition.setFromX(1); // Initial X scale factor (1 means the original size)
        scaleTransition.setFromY(1); // Initial Y scale factor
        scaleTransition.setToX(0);   // Final X scale factor (0 means invisible)
        scaleTransition.setToY(0);   // Final Y scale factor
        scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog2.setVisible(false);
            }
        });
        scaleTransition.play();

    }
    public void showCustomer(){
        SupplierPane.setVisible(false);
        CustomersPane.setVisible(true);
        CustomersButton.setId("l2");
        SuppliersButton.setId("");
        listSupplier.clear();
        listSupplier.addAll(listSupplierCopy);
        emptySupplierTxtFields();
    }
    public void emptyCustomerTxtFields(){
        CustomerIdtxtField.setText("");
        CustomerNametxtField.setText("");
        CustomerPhonetxtField.setText("");
        CustomerEmailtxtField.setText("");
        CustomerAddresstxtField.setText("");
        DiscounttxtField.setText("");
    }

    public void emptySupplierTxtFields(){
        SupplierIDtxtField.setText("");
        SupplierNametxtField.setText("");
        SupplierPhonetxtField.setText("");
        SupplierEmailtxtField.setText("");
        SupplierAddresstxtField.setText("");
        ScopetxtField.setValue("");
    }

    public void showSupplier(){
        SupplierPane.setVisible(true);
        CustomersPane.setVisible(false);
        CustomersButton.setId("");
        SuppliersButton.setId("l2");
        listCustomer.clear();
        listCustomer.addAll(listCustomerCopy);
        emptyCustomerTxtFields();

    }
    public void switchToHomeTab(MouseEvent event) throws IOException {
        SwitchToTab(event,"HomePage.fxml");
    }

    public void switchToMedicineTab(MouseEvent event) throws IOException {
        SwitchToTab(event,"Medicine.fxml");

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
