package com.example.database;

import java.io.*;
import java.sql.*;
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
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MedicineController implements Initializable{

    @FXML
    private TableView<Medicine> MedicineTable;
    @FXML
    private TableColumn<Medicine, Integer> MedicineID;
    @FXML
    private TableColumn<Medicine, String> BrandName;

    @FXML
    private TableColumn<Medicine, String> CategoryName;

    @FXML
    private TableColumn<Medicine, String> ExpDate;

    @FXML
    private TableColumn<Medicine, Integer> FreeQuantity;
    @FXML
    private TableColumn<Medicine, Double> PriceAfterTax;

    @FXML
    private TableColumn<Medicine, Double> PriceBeforeTax;

    @FXML
    private TableColumn<Medicine, String> ScentficName;

    @FXML
    private TableColumn<Medicine,Double> TaxPercent;

    @FXML
    private TableColumn<Medicine, String> Unit;

    @FXML
    private TableColumn<Category, Integer> CatID;

    @FXML
    private TableView<Category> CategoryTable;
    @FXML
    private TableColumn<Category, String> CatName;
    @FXML
    private TableColumn<Category, String> Description;


    @FXML
    private AnchorPane MedicinePane;

    @FXML
    private AnchorPane CategoryPane;

    @FXML
    Button medicineButtton;

    @FXML
    Button catButton;

    @FXML
    private TextField BrandNametxtField;
    @FXML
    private ComboBox<String> CategoryNametxtField;
    @FXML
    private DatePicker ExpDatetxtField;
    @FXML
    private TextField FreeQuantitytxtField;
    @FXML
    private TextField MedicineIDtxtField;
    @FXML
    private TextField PriceBeforeTaxtxtField;
    @FXML
    private TextField ScentficNametxtField;
    @FXML
    private TextField TaxPercenttxtField;
    @FXML
    private TextField UnittxtField;
    @FXML
    private TextField PriceAfterTaxtxtField;
    @FXML
    private TextField CatIDtxtField;
    @FXML
    private TextField CatNametxtField;
    @FXML
    private TextArea DescriptiontxtField;
    @FXML
    private DialogPane dialog1;
    @FXML
    private DialogPane dialog2;
    @FXML
    private AnchorPane MainAnchor;
    @FXML
    private TextField MedIDSearch;
    @FXML
    private TextField BrandNameSearch;
    @FXML
    private TextField ScentficNameSearch;
    @FXML
    private TextField CatNameSearch;
    @FXML
    private TextField CatIDSearch;
    @FXML
    private TextField CategoryNameSearch;
    @FXML
    private TextArea DescriptionSearch;
    @FXML
    private PieChart PieChart1;

    Integer SelectedIndexMedicine;
    Integer SelectedIndexCategory;
    public static HashMap<String,Integer> mapOfCategory = new HashMap<>();



    public ObservableList<Medicine> listMedicine = FXCollections.observableArrayList();
    public ObservableList<String> CatComboBox = FXCollections.observableArrayList();
    public ObservableList<Category> listCategory = FXCollections.observableArrayList();
    public ObservableList<PieChart.Data> listPieChart = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        // handling nullexception
        if (medicineButtton != null) {
            //medicineButtton.setStyle("-fx-text-fill:#FFF;-fx-background-color:#3d26a5;");
            medicineButtton.setId("l2");
        }
        // this for table-view-category
        CatID.setCellValueFactory(new PropertyValueFactory<Category,Integer>("CategoryID"));
        CatName.setCellValueFactory(new PropertyValueFactory<Category,String>("Name"));
        Description.setCellValueFactory(new PropertyValueFactory<Category,String>("Description"));
        // this for table-view ( to know excatly where each table column from where will get its value)
        MedicineID.setCellValueFactory(new PropertyValueFactory<Medicine,Integer>("MedicineID"));
        BrandName.setCellValueFactory(new PropertyValueFactory<Medicine,String>("BrandName"));
        ScentficName.setCellValueFactory(new PropertyValueFactory<Medicine,String>("ScentficName"));
        Unit.setCellValueFactory(new PropertyValueFactory<Medicine,String>("Unit"));
        ExpDate.setCellValueFactory(new PropertyValueFactory<Medicine,String>("ExpDate"));
        FreeQuantity.setCellValueFactory(new PropertyValueFactory<Medicine,Integer>("FreeQuantity"));
        TaxPercent.setCellValueFactory(new PropertyValueFactory<Medicine,Double>("TaxPercent"));
        PriceBeforeTax.setCellValueFactory(new PropertyValueFactory<Medicine,Double>("PriceBeforeTax"));
        PriceAfterTax.setCellValueFactory(new PropertyValueFactory<Medicine,Double>("PriceAfterTax"));
        CategoryName.setCellValueFactory(new PropertyValueFactory<Medicine,String>("CategoryName"));
        UpdateMedicineTable();
        UpdateCategoryTable();
        // Update UI components
        MedicineTable.setItems(listMedicine);
        CategoryNametxtField.setItems(CatComboBox);
        CategoryTable.setItems(listCategory);



    }


    public  void UpdateMedicineTable(){
        // this function is for updating the table-view and getting the data from database into application program
        if(!listMedicine.isEmpty()) listMedicine.clear();
        try{
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            String Query = "SELECT * FROM "+con.DB+".Medicines as m,"+con.DB+".Category as c where m.catid= c.CategoryID ORDER BY MedicineID;";
            ResultSet rs = stm.executeQuery(Query);
            while(rs.next()){
                boolean flag = rs.getBoolean("flag_hidden");
                if(flag == true) continue;
                // we build the medicine object to add to observation list and show it in table-view
                // note that PriceAfterTax is derived attribute with the following equation : pricebtax*tax + pricebtax
                Medicine obj = new Medicine(
                        rs.getInt("MedicineID"),
                        rs.getString("BrandName"),
                        rs.getString("ScentficName"),
                        rs.getString("Unit"),
                        rs.getString("ExpDate"),
                        rs.getInt("FreeQuantity"),
                        rs.getDouble("TaxPercentage"),
                        rs.getDouble("PriceBTax"),
                        Math.round((rs.getDouble("TaxPercentage")* rs.getDouble("PriceBTax")+rs.getDouble("PriceBTax") ) * 100.0) / 100.0,
                        rs.getString("Name")

                );
                listMedicine.add(obj);
            }

            con.closeConnection();
        }
        catch (Exception e){
            // do-noting
        }
    }
    public  void UpdateCategoryTable(){
        // this function is for updating the table-view and getting the data from database into application program

        if(!listCategory.isEmpty()) listCategory.clear();
        try{

            Config con = new Config();
            Statement stm = con.makeStatemnt();
            String query = "Select Name,CategoryID,Description from medicinewarehouse.Category;";
            ResultSet res = stm.executeQuery(query);
            while(res.next()){

                Integer id = res.getInt("CategoryID");
                String CatName = res.getString("name");
                String Description = res.getString("Description");
                Category obj = new Category(id,CatName,Description);
                CatComboBox.add(CatName);
                mapOfCategory.put(CatName,id);
                listCategory.add(obj);
            }

            con.closeConnection();
        }
        catch (Exception e){
           System.out.println(e.getMessage());
        }


    }
    public void generateReport(){
        try {
            Connection con;
            DriverManager.registerDriver(new org.postgresql.Driver());
            String ConnectionInfo = "jdbc:postgresql://localhost:5432/postgres";
            con = DriverManager.getConnection(ConnectionInfo, Config.user, Config.pass);


            InputStream inp = new FileInputStream("Blank_A4_1.jrxml");
            JasperDesign jd = JRXmlLoader.load(inp);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr,null,con);
            OutputStream op= new FileOutputStream("Medicines.pdf");
            JasperExportManager.exportReportToPdfStream(jp,op);
            JasperViewer.viewReport(jp);
            con.close();
            inp.close();
            op.close();
            Alert msg = new Alert(Alert.AlertType.INFORMATION,"All Medicines Report Has Been Generated Successfully");
            msg.showAndWait();

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void getCategoryItem(MouseEvent event) throws IOException{
        SelectedIndexCategory = CategoryTable.getSelectionModel().getSelectedIndex();
        if(SelectedIndexCategory < 0) return;
        Category tmp = CategoryTable.getItems().get(SelectedIndexCategory);

        CatIDtxtField.setText(String.valueOf(tmp.getCategoryID()));
        CatNametxtField.setText(tmp.getName());
        DescriptiontxtField.setText(tmp.getDescription());

    }
    public void getMedicineItem(MouseEvent event) throws IOException{
        SelectedIndexMedicine = MedicineTable.getSelectionModel().getSelectedIndex();
        if(SelectedIndexMedicine < 0) return;
        Medicine tmp = MedicineTable.getItems().get(SelectedIndexMedicine);
        MedicineIDtxtField.setText(String.valueOf(tmp.getMedicineID()));
        BrandNametxtField.setText(tmp.getBrandName());
        ScentficNametxtField.setText(tmp.getScentficName());
        UnittxtField.setText(tmp.getUnit());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ExpDatetxtField.setValue(LocalDate.parse(tmp.getExpDate(),formatter));
        FreeQuantitytxtField.setText(String.valueOf(tmp.getFreeQuantity()));
        TaxPercenttxtField.setText(String.valueOf(tmp.getTaxPercent()));
        PriceBeforeTaxtxtField.setText(String.valueOf(tmp.getPriceBeforeTax()));
        CategoryNametxtField.setValue(tmp.getCategoryName());
        PriceAfterTaxtxtField.setText(String.valueOf(tmp.getPriceAfterTax()));
    }

    // this function is for calculating the Price After Tax when typing in keyboard
    public void PriceATaxDis(){
        if (!TaxPercenttxtField.getText().isEmpty() && !PriceBeforeTax.getText().isEmpty()) {
            try {
                BigDecimal a = new BigDecimal(TaxPercenttxtField.getText().replace(",", "."));
                BigDecimal b = new BigDecimal(PriceBeforeTaxtxtField.getText().replace(",", "."));
                BigDecimal result = b.add(a.multiply(b));
                PriceAfterTaxtxtField.setText(result.toString());
            }
            catch (NumberFormatException e){
                PriceAfterTaxtxtField.setText("");
            }

        }

    }

    public void SearchCategory(){
        String id = CatIDSearch.getText();
        String CatName = CategoryNameSearch.getText();
        String Description = DescriptionSearch.getText();
        String query = "SELECT * FROM "+Config.DB+".Category WHERE 1=1";
        int count = 0;
        if(!id.isEmpty()) {query += " AND CategoryID = "+id;count++;CatIDSearch.setText("");}
        if(!CatName.isEmpty()){query += " AND Name like '%"+CatName+"%'";count++;CategoryNameSearch.setText("");}
        if(!Description.isEmpty()){query += " AND Description like '%"+Description+"%'";count++;DescriptionSearch.setText("");}
        if(count == 0)return;
        query +=" ORDER BY CategoryID;"; // sort by medicine id
        // System.out.println(query);
        try{
            listCategory.clear();
            Config con = new Config();
            Statement stm = con.makeStatemnt();

            ResultSet rs = stm.executeQuery(query);
            while(rs.next()){
                Category obj = new Category(
                        rs.getInt("CategoryID"),
                        rs.getString("Name"),
                        rs.getString("Description")
                );
                listCategory.add(obj);
            }
            con.closeConnection();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        closeSearchCat();
    }
    public void SearchMedicine(){
        String id = MedIDSearch.getText();
        String BrandName = BrandNameSearch.getText();
        String ScentficName = ScentficNameSearch.getText();
        String CatName = CatNameSearch.getText();
        String query = "SELECT * FROM medicinewarehouse.Medicines as m,medicinewarehouse.Category as c where m.catid= c.CategoryID";
        int count = 0;
        if(!id.isEmpty()) {query += " AND MedicineID = "+id;count++;MedIDSearch.setText("");}
        if(!BrandName.isEmpty()){query += " AND BrandName like '%"+BrandName+"%'";count++;BrandNameSearch.setText("");}
        if(!ScentficName.isEmpty()){query += " AND ScentficName like '%"+ScentficName+"%'";count++;ScentficNameSearch.setText("");}
        if(!CatName.isEmpty()){query += " AND Name like '%"+CatName+"%'";count++;CatNameSearch.setText("");}
        if(count == 0)return;
        query +=" ORDER BY MedicineID;"; // sort by medicine id
       // System.out.println(query);
        try{
            listMedicine.clear();
            Statement stm = new Config().makeStatemnt();

            ResultSet rs = stm.executeQuery(query);
            while(rs.next()){
                boolean flag = rs.getBoolean("flag_hidden");
                if(flag == true) continue;
                Medicine obj = new Medicine(
                        rs.getInt("MedicineID"),
                        rs.getString("BrandName"),
                        rs.getString("ScentficName"),
                        rs.getString("Unit"),
                        rs.getString("ExpDate"),
                        rs.getInt("FreeQuantity"),
                        rs.getDouble("TaxPercentage"),
                        rs.getDouble("PriceBTax"),
                        rs.getDouble("TaxPercentage")* rs.getDouble("PriceBTax")+rs.getDouble("PriceBTax"),
                        rs.getString("Name")
                );
                listMedicine.add(obj);
            }
        }
        catch (Exception e){
            // do-nothing
        }

        closeSearchMedicine();
    }
    public void AddMedicine(){

        String id = MedicineIDtxtField.getText();
        String BrandName  = BrandNametxtField.getText();
        String ScentficName = ScentficNametxtField.getText();
        String Unit = UnittxtField.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ExpDate_tmp = ExpDatetxtField.getValue();
        String ExpDate = "";
        if(ExpDate_tmp != null)
            ExpDate = ExpDate_tmp.format(formatter);
        String FreeQuantity = FreeQuantitytxtField.getText();
        String TaxPercent = TaxPercenttxtField.getText();
        if(FreeQuantity.isEmpty()) FreeQuantity = "0";
        if(TaxPercent.isEmpty()) TaxPercent = "0.16";
        BigDecimal taxpercent = new BigDecimal(TaxPercent);
        String PriceBTax = PriceBeforeTaxtxtField.getText();
        BigDecimal pricebtax = new BigDecimal(PriceBTax);
        String CatName = CategoryNametxtField.getValue();
        int catid = mapOfCategory.get(CatName);
        boolean a = BrandName.isEmpty() || ScentficName.isEmpty() || Unit.isEmpty() || ExpDate.isEmpty() || PriceBTax.isEmpty() || CatName.isEmpty();
        if(a == true){
            Alert msg = new Alert(Alert.AlertType.ERROR,"There is an empty field !!");
            msg.showAndWait();
            return;
        }


        try {
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            con.con.setAutoCommit(false);

            String query = "INSERT INTO "+Config.DB+".Medicines(MedicineID,BrandName,ScentficName,Unit,ExpDate,FreeQuantity,TaxPercentage,PriceBTax,CatID)";
            query += "VALUES(nextval('"+Config.DB+".medicineSeq'), '" + BrandName + "', '" + ScentficName + "', '" + Unit + "', '" + ExpDate + "', " + FreeQuantity + ", " + TaxPercent + ", " + PriceBTax + ", " + catid + ");";
            System.out.println(query);
            stm.executeUpdate(query);
            con.con.commit();
            con.closeConnection();

            UpdateMedicineTable();
            MedicineIDtxtField.setText("");
            BrandNametxtField.setText("");
            ScentficNametxtField.setText("");
            UnittxtField.setText("");
            ExpDatetxtField.setValue(null);
            FreeQuantitytxtField.setText("");
            TaxPercenttxtField.setText("");
            PriceBeforeTaxtxtField.setText("");
            CategoryNametxtField.setValue("");
            PriceAfterTaxtxtField.setText("");

        }
        catch (Exception e){
            Alert msg = new Alert(Alert.AlertType.ERROR,e.getMessage());
            msg.showAndWait();
        }

    }
    public void AddCategory(){

        String id = CatIDtxtField.getText();
        String CatName  = CatNametxtField.getText();
        String Description = DescriptiontxtField.getText();

        boolean a = CatName.isEmpty();
        if(a == true){
            Alert msg = new Alert(Alert.AlertType.ERROR,"There is an empty field !!");
            msg.showAndWait();
            return;
        }


        try {
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            con.con.setAutoCommit(false);

            String query = "INSERT INTO "+Config.DB+".Category(CategoryID,Name,Description)";
            query+= " VALUES(nextval('"+Config.DB+".categorySeq'),'"+CatName+"','"+Description+"');";

            stm.executeUpdate(query);
            con.con.commit();
            con.closeConnection();
            UpdateCategoryTable();

            CatIDtxtField.setText("");
            CatNametxtField.setText("");
            DescriptiontxtField.setText("");
            Alert msg = new Alert(Alert.AlertType.INFORMATION,"The Row Has Been Added ");
            msg.showAndWait();

        }
        catch (Exception e){
            Alert msg = new Alert(Alert.AlertType.ERROR,e.getMessage());
            msg.showAndWait();
        }

    }
    public void UpdateMedicine(){

        String id = MedicineIDtxtField.getText();
        String BrandName  = BrandNametxtField.getText();
        String ScentficName = ScentficNametxtField.getText();
        String Unit = UnittxtField.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ExpDate_tmp = ExpDatetxtField.getValue();
        String ExpDate = "";
        if(ExpDate_tmp != null)
        ExpDate = ExpDate_tmp.format(formatter);
        String FreeQuantity = FreeQuantitytxtField.getText();
        String TaxPercent = TaxPercenttxtField.getText();
        BigDecimal taxpercent = new BigDecimal(TaxPercent);
        String PriceBTax = PriceBeforeTaxtxtField.getText();
        BigDecimal pricebtax = new BigDecimal(PriceBTax);
        String CatName = CategoryNametxtField.getValue();
        int catid = mapOfCategory.get(CatName);
        boolean a = id.isEmpty() || BrandName.isEmpty() || ScentficName.isEmpty() || Unit.isEmpty() || ExpDate.isEmpty() || PriceBTax.isEmpty() || CatName.isEmpty();
        if(a == true){
            Alert msg = new Alert(Alert.AlertType.ERROR,"There is an empty field !!");
            msg.showAndWait();
            return;
        }
        if(FreeQuantity.isEmpty()) FreeQuantity = "0";
        if(TaxPercent.isEmpty()) TaxPercent = "0.16";

        try {
            Config con = new Config();
            Statement stm = con.makeStatemnt();
           con.con.setAutoCommit(false);

            String query = "UPDATE "+con.DB+".Medicines SET ";
            query+= "BrandName='"+BrandName+"',ScentficName='"+ScentficName+"',Unit='"+Unit +"',ExpDate='"+ExpDate+"',";
            query +="FreeQuantity="+Integer.parseInt(FreeQuantity)+",TaxPercentage="+taxpercent.doubleValue()+",PriceBTax="+pricebtax.doubleValue();
            query+=",catid="+catid+" where MedicineID="+Integer.parseInt(id);

            stm.executeUpdate(query);
            con.con.commit();
            con.closeConnection();
            Medicine obj = new Medicine(Integer.parseInt(id),BrandName,ScentficName,Unit,ExpDate,
                    Integer.parseInt(FreeQuantity),
                    taxpercent.doubleValue(),
                    pricebtax.doubleValue(),
                    pricebtax.doubleValue()* taxpercent.doubleValue()+ pricebtax.doubleValue(),
                    CatName
                    );
            int idx = -1;
            for(int i =0;i<listMedicine.size();i++){
                if(listMedicine.get(i).getMedicineID() == Integer.parseInt(id)){
                    idx = i;
                    break;
                }
            }
            listMedicine.remove(idx);
            listMedicine.add(idx,obj);

        }
        catch (Exception e){
            Alert msg = new Alert(Alert.AlertType.ERROR,e.getMessage());
            msg.showAndWait();
        }

    }

    public void UpdateCategory(){

        String id = CatIDtxtField.getText();
        String CatName  = CatNametxtField.getText();
        String Description = DescriptiontxtField.getText();
        if(Description.isEmpty()) Description = "";
        boolean a = id.isEmpty() || CatName.isEmpty();
        if(a == true){
            Alert msg = new Alert(Alert.AlertType.ERROR,"There is an empty field !!");
            msg.showAndWait();
            return;
        }


        try {
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            con.con.setAutoCommit(false);

            String query = "UPDATE "+con.DB+".Category SET ";
            query+= "Name='"+CatName+"',Description='"+Description+"'";
            query+="where CategoryID="+Integer.parseInt(id);

            stm.executeUpdate(query);
            con.con.commit();
            con.closeConnection();
            Category obj = new Category(Integer.parseInt(id),CatName,Description);
            int idx = -1;
            for(int i =0;i<listCategory.size();i++){
                if(listCategory.get(i).getCategoryID() == Integer.parseInt(id)){
                    idx = i;
                    break;
                }
            }
            listCategory.remove(idx);
            listCategory.add(idx,obj);
            Alert msg = new Alert(Alert.AlertType.INFORMATION,"The Row Has Been Updated");
            msg.showAndWait();

        }
        catch (Exception e){
            Alert msg = new Alert(Alert.AlertType.ERROR,e.getMessage());
            msg.showAndWait();
        }

    }
    public void DeleteMedicine(){

        String id = MedicineIDtxtField.getText();
        if(id.isEmpty()){
            Alert msg = new Alert(Alert.AlertType.ERROR,"The ID Field is Empty !!");
            msg.showAndWait();
            return;
        }
        try {
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            con.con.setAutoCommit(false);

            String query = "Update "+con.DB+".Medicines Set flag_hidden=true WHERE MedicineID="+Integer.parseInt(id)+";";

            stm.executeUpdate(query);
            con.con.commit();
            con.closeConnection();

            int idx = -1;
            for(int i =0;i<listMedicine.size();i++){
                if(listMedicine.get(i).getMedicineID() == Integer.parseInt(id)){
                    idx = i;
                    break;
                }
            }
            listMedicine.remove(idx);
            //MedicineTable.setItems(listMedicine);
        }
        catch (Exception e){
            Alert msg = new Alert(Alert.AlertType.ERROR,e.getMessage());
            msg.showAndWait();
        }

    }
    public void DeleteCategory(){


        String id = CatIDtxtField.getText();
        if(id.isEmpty()){
            Alert msg = new Alert(Alert.AlertType.ERROR,"The ID Field is Empty !!");
            msg.showAndWait();
            return;
        }
        try {
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            con.con.setAutoCommit(false);

            String query = "Delete From "+con.DB+".Category WHERE CategoryID="+Integer.parseInt(id)+";";

            stm.executeUpdate(query);
            con.con.commit();
            con.closeConnection();

            int idx = -1;
            for(int i =0;i<listCategory.size();i++){
                if(listCategory.get(i).getCategoryID() == Integer.parseInt(id)){
                    idx = i;
                    break;
                }
            }
            listCategory.remove(idx);
            //MedicineTable.setItems(listMedicine);
        }
        catch (Exception e){
            Alert msg = new Alert(Alert.AlertType.ERROR,e.getMessage());
            msg.showAndWait();
        }

    }

    public void LoadPieChart(){
        try{
            if(!listPieChart.isEmpty())listPieChart.clear();
            Config con = new Config();
            Statement stm = con.makeStatemnt();
            String query = "select Name,count(catid) from "+con.DB+".medicines as m ,"+con.DB+".category as c where c.CategoryID = m.catid and m.flag_hidden !=true GROUP BY Name;";
            ResultSet res = stm.executeQuery(query);
            while(res.next()){
                String CatName = res.getString("name");
                int count = res.getInt("count");
                PieChart.Data obj = new PieChart.Data(CatName,count);
                listPieChart.add(obj);
            }

            con.closeConnection();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    public void showSearchMedicine(){
        MainAnchor.setDisable(true);
        catButton.setDisable(true);
        medicineButtton.setDisable(true);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(250), dialog1);
        scaleTransition.setFromX(0); // Initial X scale factor
        scaleTransition.setFromY(0); // Initial Y scale factor
        scaleTransition.setToX(1);   // Final X scale factor
        scaleTransition.setToY(1);   // Final Y scale factor
        scaleTransition.play();
        dialog1.setVisible(true);
    }
    public void closeSearchMedicine(){
        MainAnchor.setDisable(false);
        catButton.setDisable(false);
        medicineButtton.setDisable(false);
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

    public void showSearchCategory(){
        CategoryPane.setDisable(true);
        catButton.setDisable(true);
        medicineButtton.setDisable(true);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(250), dialog2);
        scaleTransition.setFromX(0); // Initial X scale factor
        scaleTransition.setFromY(0); // Initial Y scale factor
        scaleTransition.setToX(1);   // Final X scale factor
        scaleTransition.setToY(1);   // Final Y scale factor
        scaleTransition.play();
        dialog2.setVisible(true);
    }
    public void closeSearchCat(){
        CategoryPane.setDisable(false);
        catButton.setDisable(false);
        medicineButtton.setDisable(false);
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

    public void showCat(){
        MedicinePane.setVisible(false);
        CategoryPane.setVisible(true);
        catButton.setId("l2");
        medicineButtton.setId("");
        LoadPieChart();
        PieChart1.setData(listPieChart);
    }


    public void showMedicine(){
        MedicinePane.setVisible(true);
        CategoryPane.setVisible(false);
        catButton.setId("");
        medicineButtton.setId("l2");
    }

    // Switch Scenes Functions Here

    public void switchToHomeTab(MouseEvent event) throws IOException {
        SwitchToTab(event,"HomePage.fxml");
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