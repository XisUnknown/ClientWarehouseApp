package sample;

import SQLConnectionPackage.ConnectionDB;
import SQLConnectionPackage.FillItemsClass;
import SQLConnectionPackage.SelectFromTableClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller {
  @FXML private TableView clientOrderTable;
  @FXML private TableView orderProductTable;
  @FXML private TableView receivedOrdersTable;
  @FXML private TableView sentOrdersTable;
  @FXML private TableView completedOrdersTable;
  @FXML private TableView staffmemberTable;
  @FXML private TableView orderTable;
  @FXML private TableView CustomerTable;
  @FXML private TableView loginDataTable;
  @FXML private TableView orderListTable;
  @FXML private TableView productorderTable;
  @FXML private TableView productTable;
  @FXML private TextArea descriptionText;
  @FXML private TextArea descriptionText1;
  @FXML private TextField staffnameText1;
  @FXML private TextField staffphoneText1;
  @FXML private TextField staffemailText1;
  @FXML private TextField staffaddressText1;
  @FXML private TextField staffnameText;
  @FXML private TextField staffphoneText;
  @FXML private TextField staffemailText;
  @FXML private TextField staffaddressText;
  @FXML private TextField staffIDText;
  @FXML private TextField staffsvnumberText;
  @FXML private TextField staffsvnumberText1;
  @FXML private TextField singlePriceText;
  @FXML private TextField bulkpriceText;
  @FXML private TextField instockText;
  @FXML private TextField productnameText;
  @FXML private TextField productIDText;
  @FXML private TextField singlePriceText1;
  @FXML private TextField bulkpriceText1;
  @FXML private TextField instockText1;
  @FXML private TextField productnameText1;
  @FXML private TextField productIDText1;
  @FXML private TextField usernameText;
  @FXML private TextField passwordText;
  @FXML private TextField usernameText1;
  @FXML private TextField passwordText1;
  @FXML private TextField clientIDText;
  @FXML private TextField clientName;
  @FXML private TextField clientAddress;
  @FXML private TextField clientEmail;
  @FXML private TextField clientPhone;
  @FXML private TextField clientName1;
  @FXML private TextField clientAddress1;
  @FXML private TextField clientEmail1;
  @FXML private TextField clientPhone1;
  @FXML private TextField SearchFieldClient;
  @FXML private TextField SearchFieldProduct;
  @FXML private TextField SearchFieldStaff;
  @FXML private ChoiceBox choiceBoxSearchClient;
  @FXML private ChoiceBox choiceBoxSearchProduct;
  @FXML private ChoiceBox choiceBoxProduct;
  @FXML private ChoiceBox choiceBoxArea;
  @FXML private ChoiceBox choiceBoxArea1;
  @FXML private ChoiceBox choiceBoxManufacturer;
  @FXML private ChoiceBox choiceBoxAvail;
  @FXML private ChoiceBox choiceBoxLocation;
  @FXML private ChoiceBox choiceBoxCategory;
  @FXML private ChoiceBox choiceBoxManu1;
  @FXML private ChoiceBox choiceBoxAvail1;
  @FXML private ChoiceBox choiceBoxLocation1;
  @FXML private ChoiceBox choiceBoxCategory1;
  @FXML private ChoiceBox choiceBoxStaffSearch;
  @FXML private ChoiceBox choiceBoxTeam;
  @FXML private ChoiceBox choiceBoxRole;
  @FXML private ChoiceBox choiceBoxShippingarea;
  @FXML private ChoiceBox choiceBoxRole1;
  @FXML private ChoiceBox choiceBoxShippingarea1;

  private Connection con;
  DatabaseMetaData meta;
  private ObservableList CustomerList = FXCollections.observableArrayList();
  private ObservableList productorderList = FXCollections.observableArrayList();
  private ObservableList orderList = FXCollections.observableArrayList();
  private ObservableList productList = FXCollections.observableArrayList();
  private ObservableList staffmemberList = FXCollections.observableArrayList();
  private ObservableList order2List = FXCollections.observableArrayList();
  private ObservableList loginList = FXCollections.observableArrayList();
  private ObservableList receivedOrdersList = FXCollections.observableArrayList();
  private ObservableList sentOrdersList = FXCollections.observableArrayList();
  private ObservableList completedOrdersList = FXCollections.observableArrayList();
  private ObservableList clientOrderList = FXCollections.observableArrayList();
  private ObservableList orderProductList = FXCollections.observableArrayList();

  private String serverURL = "jdbc:mysql://localhost:3306/";
  private String db = "feelgoodltd";
  private String timeZone =
      "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin";
  private String buff = serverURL + db + timeZone;
  private String username = "root";
  private String password = "";

  public void initialize() throws SQLException {

    // Connection to the DB, happens only once
    ConnectionDB.getConnection(buff, username, password);
    createCostumerTable();
    createProductTable();
    createStaffmemberTable();
    createReceivedOrderTable();
    createSentOrderTable();
    createCompletedOrderTable();
    // createOrderTable();
    ResultSet rs =
        ConnectionDB.getResultSetfromInput(
            db,
            "SELECT clientID,clientname,clientaddress,clientemail,clientphone,shippingarea FROM client WHERE 1");
    choiceBoxSearchClient.getItems().add("-- Select Option --");
    FillItemsClass.fillChoiceBox(rs, choiceBoxSearchClient);
    choiceBoxSearchClient.setValue("-- Select Option --");
    rs =
        ConnectionDB.getResultSetfromInput(
            db,
            "SELECT sm.staffID,"
                + "sm.staffname,"
                + "sm.staffphone,"
                + "sm.staffemail,"
                + "sm.staffaddress,"
                + "sm.staffsvnumber,"
                + "sm.role,"
                + "st.shippingarea,"
                + "st.teamname "
                + "FROM staffmember sm "
                + "INNER JOIN shippingteam st "
                + "ON sm.shippingteamID = st.shippingteamID "
                + "WHERE 1");
    choiceBoxStaffSearch.getItems().add("-- Select Option --");
    FillItemsClass.fillChoiceBox(rs, choiceBoxStaffSearch);
    choiceBoxStaffSearch.setValue("-- Select Option --");
    rs =
        ConnectionDB.getResultSetfromInput(
            db,
            "SELECT p.productID,p.category,p.productname,p.description,p.location,m.manuname "
                + "FROM product p INNER JOIN manufacturer m ON p.manuID = m.manuID WHERE 1");
    choiceBoxSearchProduct.getItems().add("-- Select Option --");
    FillItemsClass.fillChoiceBox(rs, choiceBoxSearchProduct);
    choiceBoxSearchProduct.setValue("-- Select Option --");
    rs = ConnectionDB.getResultSetfromInput(db, "SELECT manuname FROM manufacturer WHERE 1");
    choiceBoxManufacturer.getItems().add("-- Select Option --");
    choiceBoxManu1.getItems().add("-- Select Option --");
    FillItemsClass.fillChoiceBox(rs, choiceBoxManu1);
    FillItemsClass.fillChoiceBox(rs, choiceBoxManufacturer);
    choiceBoxManufacturer.setValue("-- Select Option --");
    choiceBoxManu1.setValue("-- Select Option --");

    // choiceBoxAvail.getItems().add("-- Select Option --");
    choiceBoxAvail.getItems().add("Available");
    choiceBoxAvail.getItems().add("Not Available");
    choiceBoxAvail1.getItems().add("Available");
    choiceBoxAvail1.getItems().add("Not Available");
    // choiceBoxAvail.setValue("-- Select Option --");

    choiceBoxCategory.getItems().add("-- Select Option --");
    choiceBoxCategory.getItems().add("Tea");
    choiceBoxCategory.getItems().add("Coffee");
    choiceBoxCategory.getItems().add("Soft Drinks");
    choiceBoxCategory.getItems().add("Energy Drinks");
    choiceBoxCategory.setValue("-- Select Option --");

    choiceBoxCategory1.getItems().add("-- Select Option --");
    choiceBoxCategory1.getItems().add("Tea");
    choiceBoxCategory1.getItems().add("Coffee");
    choiceBoxCategory1.getItems().add("Soft Drinks");
    choiceBoxCategory1.getItems().add("Energy Drinks");
    choiceBoxCategory1.setValue("-- Select Option --");

    choiceBoxLocation.getItems().add("-- Select Option --");
    choiceBoxLocation.getItems().add("Zone 1");
    choiceBoxLocation.getItems().add("Zone 2");
    choiceBoxLocation.getItems().add("Zone 3");
    choiceBoxLocation.getItems().add("Zone 4");
    choiceBoxLocation.setValue("-- Select Option --");

    choiceBoxLocation1.getItems().add("-- Select Option --");
    choiceBoxLocation1.getItems().add("Zone 1");
    choiceBoxLocation1.getItems().add("Zone 2");
    choiceBoxLocation1.getItems().add("Zone 3");
    choiceBoxLocation1.getItems().add("Zone 4");
    choiceBoxLocation1.setValue("-- Select Option --");

    choiceBoxArea.getItems().add("-- Select Option --");
    choiceBoxArea.getItems().add("WEST");
    choiceBoxArea.getItems().add("SÜD");
    choiceBoxArea.getItems().add("OST");
    choiceBoxArea.getItems().add("NORD");
    choiceBoxArea.setValue("-- Select Option --");
    choiceBoxArea1.getItems().add("-- Select Option --");
    choiceBoxArea1.getItems().add("WEST");
    choiceBoxArea1.getItems().add("SÜD");
    choiceBoxArea1.getItems().add("OST");
    choiceBoxArea1.getItems().add("NORD");
    choiceBoxArea1.setValue("-- Select Option --");

    // choiceBoxTeam.getItems().add("-- Select Option --");
    // choiceBoxTeam.getItems().add("A");
    // choiceBoxTeam.getItems().add("B");
    // choiceBoxTeam.getItems().add("C");
    // choiceBoxTeam.getItems().add("D");
    // choiceBoxTeam.setValue("-- Select Option --");

    choiceBoxRole.getItems().add("-- Select Option --");
    choiceBoxRole.getItems().add("IT");
    choiceBoxRole.getItems().add("Accounting");
    choiceBoxRole.getItems().add("Sales");
    choiceBoxRole.getItems().add("Warehouse Worker");
    choiceBoxRole.setValue("-- Select Option --");

    choiceBoxShippingarea.getItems().add("-- Select Option --");
    choiceBoxShippingarea.getItems().add("WEST");
    choiceBoxShippingarea.getItems().add("SÜD");
    choiceBoxShippingarea.getItems().add("OST");
    choiceBoxShippingarea.getItems().add("NORD");
    choiceBoxShippingarea.setValue("-- Select Option --");

    choiceBoxRole1.getItems().add("-- Select Option --");
    choiceBoxRole1.getItems().add("IT");
    choiceBoxRole1.getItems().add("Accounting");
    choiceBoxRole1.getItems().add("Sales");
    choiceBoxRole1.getItems().add("Warehouse Worker");
    choiceBoxRole1.setValue("-- Select Option --");

    choiceBoxShippingarea1.getItems().add("-- Select Option --");
    choiceBoxShippingarea1.getItems().add("WEST");
    choiceBoxShippingarea1.getItems().add("SÜD");
    choiceBoxShippingarea1.getItems().add("OST");
    choiceBoxShippingarea1.getItems().add("NORD");
    choiceBoxShippingarea1.setValue("-- Select Option --");
  }

  public void createCostumerTable() throws SQLException {

    // Execute Query for the CoffeeTable data
    ResultSet rs =
        ConnectionDB.getResultSetfromInput(
            db,
            "SELECT clientID,clientname,clientaddress,clientemail,clientphone,shippingarea FROM client WHERE 1");
    FillItemsClass.populateMultiColumnTable(CustomerTable, CustomerList, rs);
  }

  public void createProductTable() throws SQLException {

    ResultSet rs =
        ConnectionDB.getResultSetfromInput(
            db,
            "SELECT p.productID,p.category,p.productname,m.manuname,"
                + "p.singleprice, p.bulkprice, p.instock, p.availabilty,p.location "
                + "FROM  product p INNER JOIN manufacturer m ON p.manuID = m.manuID "
                + "WHERE 1");
    FillItemsClass.populateMultiColumnTable(productTable, productList, rs);
  }

  public void selectCostumer() throws SQLException {
    String buff[] = SelectFromTableClass.parseFromMultiColumnTable(CustomerTable);
    String clientID = buff[0];
    String clientNameBuff = buff[1];
    String buffAddress = buff[2] + ", " + buff[3];
    choiceBoxArea.setValue(buff[6]);
    ResultSet rs =
        ConnectionDB.getResultSetfromInput(
            db, "SELECT username,password FROM client WHERE clientID = " + clientID);
    FillItemsClass.populateMultiColumnTable(loginDataTable, loginList, rs);
    FillItemsClass.fillTextField(usernameText, rs, "username");
    FillItemsClass.fillTextField(passwordText, rs, "password");
    orderList.clear();
    orderListTable.setItems(orderList);
    orderListTable.getColumns().clear();
    productorderList.clear();
    productorderTable.setItems(productorderList);
    productorderTable.getColumns().clear();
    rs =
        ConnectionDB.getResultSetfromInput(
            db,
            "SELECT orderID,status,total,date "
                + "FROM ordertab "
                + "INNER JOIN client ON client.clientID = ordertab.clientID "
                + "WHERE client.clientID ="
                + clientID);
    FillItemsClass.populateMultiColumnTable(orderListTable, orderList, rs);
    clientIDText.setText(clientID);
    clientName.setText(clientNameBuff);
    clientAddress.setText(buffAddress);
    clientEmail.setText(buff[4]);
    clientPhone.setText(buff[5]);
  }

  public void selectProduct() throws SQLException {
    String buff[] = SelectFromTableClass.parseFromMultiColumnTable(productTable);
    ResultSet rs =
        ConnectionDB.getResultSetfromInput(
            db,
            "SELECT * "
                + "FROM  product p INNER JOIN manufacturer m ON p.manuID = m.manuID "
                + "WHERE productID = '"
                + buff[0]
                + "'");
    while (rs.next()) {
      // System.out.println();
      productIDText.setText(rs.getString("productID"));
      choiceBoxManufacturer.setValue(rs.getString("manuname"));
      productnameText.setText(rs.getString("productname"));
      choiceBoxCategory.setValue(rs.getString("category"));
      singlePriceText.setText(rs.getString("singleprice"));
      bulkpriceText.setText(rs.getString("bulkprice"));
      instockText.setText(rs.getString("instock"));
      if (rs.getString("availabilty").equals(0)) {
        choiceBoxAvail.setValue("Not Available");
      } else {
        choiceBoxAvail.setValue("Available");
      }
      choiceBoxLocation.setValue(rs.getString("location"));
      descriptionText.setText(rs.getString("description"));
    }
  }

  public void searchClientDB() throws SQLException {
    ResultSet rs;
    switch (choiceBoxSearchClient.getValue().toString()) {
      case "clientID":
        rs =
            ConnectionDB.getResultSetfromInput(
                db,
                "SELECT clientID,clientname,clientaddress,clientemail,clientphone,shippingarea"
                    + " FROM client WHERE clientID = "
                    + SearchFieldClient.getText());
        FillItemsClass.populateMultiColumnTable(CustomerTable, CustomerList, rs);
        break;
      case "clientname":
        rs =
            ConnectionDB.getResultSetfromInput(
                db,
                "SELECT clientID,clientname,clientaddress,clientemail,clientphone,shippingarea"
                    + " FROM client WHERE clientname LIKE ('%"
                    + SearchFieldClient.getText()
                    + "%')");
        FillItemsClass.populateMultiColumnTable(CustomerTable, CustomerList, rs);
        break;
      case "clientaddress":
        rs =
            ConnectionDB.getResultSetfromInput(
                db,
                "SELECT clientID,clientname,clientaddress,clientemail,clientphone,shippingarea"
                    + " FROM client WHERE clientaddress LIKE ('%"
                    + SearchFieldClient.getText()
                    + "%')");
        FillItemsClass.populateMultiColumnTable(CustomerTable, CustomerList, rs);
        break;
      case "clientemail":
        rs =
            ConnectionDB.getResultSetfromInput(
                db,
                "SELECT clientID,clientname,clientaddress,clientemail,clientphone,shippingarea"
                    + " FROM client WHERE clientemail LIKE ('%"
                    + SearchFieldClient.getText()
                    + "%')");
        FillItemsClass.populateMultiColumnTable(CustomerTable, CustomerList, rs);
        break;
      case "clientphone":
        rs =
            ConnectionDB.getResultSetfromInput(
                db,
                "SELECT clientID,clientname,clientaddress,clientemail,clientphone,shippingarea"
                    + " FROM client WHERE clientphone LIKE ('%"
                    + SearchFieldClient.getText()
                    + "%')");
        FillItemsClass.populateMultiColumnTable(CustomerTable, CustomerList, rs);
        break;
      case "shippingarea":
        rs =
            ConnectionDB.getResultSetfromInput(
                db,
                "SELECT clientID,clientname,clientaddress,clientemail,clientphone,shippingarea"
                    + " FROM client WHERE shippingarea LIKE ('%"
                    + SearchFieldClient.getText()
                    + "%')");
        FillItemsClass.populateMultiColumnTable(CustomerTable, CustomerList, rs);
        break;
      default:
        break;
    }
    // ResultSet rs = ConnectionDB.getResultSetfromInput(db,"SELECT * FROM client WHERE ");
  }

  public void searchStaffmemberDB() throws SQLException {
    ResultSet rs;
    switch (choiceBoxStaffSearch.getValue().toString()) {
      case "staffID":
        rs =
            ConnectionDB.getResultSetfromInput(
                db,
                "SELECT sm.staffID,"
                    + "sm.staffname,"
                    + "sm.staffphone,"
                    + "sm.staffemail,"
                    + "sm.staffaddress,"
                    + "sm.staffsvnumber,"
                    + "sm.role,"
                    + "st.shippingarea,"
                    + "st.teamname "
                    + "FROM staffmember sm "
                    + "INNER JOIN shippingteam st "
                    + "ON sm.shippingteamID = st.shippingteamID "
                    + "WHERE staffID = "
                    + SearchFieldStaff.getText());

        FillItemsClass.populateMultiColumnTable(staffmemberTable, staffmemberList, rs);
        break;
      case "staffname":
        rs =
            ConnectionDB.getResultSetfromInput(
                db,
                "SELECT sm.staffID,"
                    + "sm.staffname,"
                    + "sm.staffphone,"
                    + "sm.staffemail,"
                    + "sm.staffaddress,"
                    + "sm.staffsvnumber,"
                    + "sm.role,"
                    + "st.shippingarea,"
                    + "st.teamname "
                    + "FROM staffmember sm "
                    + "INNER JOIN shippingteam st "
                    + "ON sm.shippingteamID = st.shippingteamID "
                    + "WHERE staffname LIKE ('%"
                    + SearchFieldStaff.getText()
                    + "%')");

        FillItemsClass.populateMultiColumnTable(staffmemberTable, staffmemberList, rs);
        break;
      case "staffphone":
        rs =
            ConnectionDB.getResultSetfromInput(
                db,
                "SELECT sm.staffID,"
                    + "sm.staffname,"
                    + "sm.staffphone,"
                    + "sm.staffemail,"
                    + "sm.staffaddress,"
                    + "sm.staffsvnumber,"
                    + "sm.role,"
                    + "st.shippingarea,"
                    + "st.teamname "
                    + "FROM staffmember sm "
                    + "INNER JOIN shippingteam st "
                    + "ON sm.shippingteamID = st.shippingteamID "
                    + "WHERE staffphone LIKE ('%"
                    + SearchFieldStaff.getText()
                    + "%')");

        FillItemsClass.populateMultiColumnTable(staffmemberTable, staffmemberList, rs);
        break;
      case "staffemail":
        rs =
            ConnectionDB.getResultSetfromInput(
                db,
                "SELECT sm.staffID,"
                    + "sm.staffname,"
                    + "sm.staffphone,"
                    + "sm.staffemail,"
                    + "sm.staffaddress,"
                    + "sm.staffsvnumber,"
                    + "sm.role,"
                    + "st.shippingarea,"
                    + "st.teamname "
                    + "FROM staffmember sm "
                    + "INNER JOIN shippingteam st "
                    + "ON sm.shippingteamID = st.shippingteamID "
                    + "WHERE staffemail LIKE ('%"
                    + SearchFieldStaff.getText()
                    + "%')");

        FillItemsClass.populateMultiColumnTable(staffmemberTable, staffmemberList, rs);
        break;
      case "staffaddress":
        rs =
            ConnectionDB.getResultSetfromInput(
                db,
                "SELECT sm.staffID,"
                    + "sm.staffname,"
                    + "sm.staffphone,"
                    + "sm.staffemail,"
                    + "sm.staffaddress,"
                    + "sm.staffsvnumber,"
                    + "sm.role,"
                    + "st.shippingarea,"
                    + "st.teamname "
                    + "FROM staffmember sm "
                    + "INNER JOIN shippingteam st "
                    + "ON sm.shippingteamID = st.shippingteamID "
                    + "WHERE staffaddress LIKE ('%"
                    + SearchFieldStaff.getText()
                    + "%')");

        FillItemsClass.populateMultiColumnTable(staffmemberTable, staffmemberList, rs);
        break;
      case "staffsvnumber":
        rs =
            ConnectionDB.getResultSetfromInput(
                db,
                "SELECT sm.staffID,"
                    + "sm.staffname,"
                    + "sm.staffphone,"
                    + "sm.staffemail,"
                    + "sm.staffaddress,"
                    + "sm.staffsvnumber,"
                    + "sm.role,"
                    + "st.shippingarea,"
                    + "st.teamname "
                    + "FROM staffmember sm "
                    + "INNER JOIN shippingteam st "
                    + "ON sm.shippingteamID = st.shippingteamID "
                    + "WHERE staffsvnumber LIKE ('%"
                    + SearchFieldStaff.getText()
                    + "%')");

        FillItemsClass.populateMultiColumnTable(staffmemberTable, staffmemberList, rs);
        break;
      case "role":
        rs =
            ConnectionDB.getResultSetfromInput(
                db,
                "SELECT sm.staffID,"
                    + "sm.staffname,"
                    + "sm.staffphone,"
                    + "sm.staffemail,"
                    + "sm.staffaddress,"
                    + "sm.staffsvnumber,"
                    + "sm.role,"
                    + "st.shippingarea,"
                    + "st.teamname "
                    + "FROM staffmember sm "
                    + "INNER JOIN shippingteam st "
                    + "ON sm.shippingteamID = st.shippingteamID "
                    + "WHERE role LIKE ('%"
                    + SearchFieldStaff.getText()
                    + "%')");

        FillItemsClass.populateMultiColumnTable(staffmemberTable, staffmemberList, rs);
        break;
      case "shippingarea":
        rs =
            ConnectionDB.getResultSetfromInput(
                db,
                "SELECT sm.staffID,"
                    + "sm.staffname,"
                    + "sm.staffphone,"
                    + "sm.staffemail,"
                    + "sm.staffaddress,"
                    + "sm.staffsvnumber,"
                    + "sm.role,"
                    + "st.shippingarea,"
                    + "st.teamname "
                    + "FROM staffmember sm "
                    + "INNER JOIN shippingteam st "
                    + "ON sm.shippingteamID = st.shippingteamID "
                    + "WHERE shippingarea LIKE ('%"
                    + SearchFieldStaff.getText()
                    + "%')");

        FillItemsClass.populateMultiColumnTable(staffmemberTable, staffmemberList, rs);
        break;
      case "teamname":
        rs =
            ConnectionDB.getResultSetfromInput(
                db,
                "SELECT sm.staffID,"
                    + "sm.staffname,"
                    + "sm.staffphone,"
                    + "sm.staffemail,"
                    + "sm.staffaddress,"
                    + "sm.staffsvnumber,"
                    + "sm.role,"
                    + "st.shippingarea,"
                    + "st.teamname "
                    + "FROM staffmember sm "
                    + "INNER JOIN shippingteam st "
                    + "ON sm.shippingteamID = st.shippingteamID "
                    + "WHERE st.teamname LIKE ('%"
                    + SearchFieldStaff.getText()
                    + "%')");

        FillItemsClass.populateMultiColumnTable(staffmemberTable, staffmemberList, rs);
        break;
      default:
        break;
    }
  }

  public void searchProductDB() throws SQLException {
    ResultSet rs;
    switch (choiceBoxSearchProduct.getValue().toString()) {
      case "productID":
        rs =
            ConnectionDB.getResultSetfromInput(
                db,
                "SELECT p.productID,p.category,p.productname,m.manuname,"
                    + "p.singleprice, p.bulkprice, p.instock, p.availabilty,p.location "
                    + "FROM  product p INNER JOIN manufacturer m ON p.manuID = m.manuID "
                    + "WHERE productID = "
                    + SearchFieldProduct.getText());
        FillItemsClass.populateMultiColumnTable(productTable, productList, rs);
        break;
      case "productname":
        rs =
            ConnectionDB.getResultSetfromInput(
                db,
                "SELECT p.productID,p.category,p.productname,m.manuname,"
                    + "p.singleprice, p.bulkprice, p.instock, p.availabilty,p.location "
                    + "FROM  product p INNER JOIN manufacturer m ON p.manuID = m.manuID "
                    + "WHERE productname LIKE ('%"
                    + SearchFieldProduct.getText()
                    + "%')");
        FillItemsClass.populateMultiColumnTable(productTable, productList, rs);
        break;
      case "category":
        rs =
            ConnectionDB.getResultSetfromInput(
                db,
                "SELECT p.productID,p.category,p.productname,m.manuname,"
                    + "p.singleprice, p.bulkprice, p.instock, p.availabilty,p.location "
                    + "FROM  product p INNER JOIN manufacturer m ON p.manuID = m.manuID "
                    + " WHERE category LIKE ('%"
                    + SearchFieldProduct.getText()
                    + "%')");
        FillItemsClass.populateMultiColumnTable(productTable, productList, rs);
        break;
      case "description":
        rs =
            ConnectionDB.getResultSetfromInput(
                db,
                "SELECT p.productID,p.category,p.productname,m.manuname,"
                    + "p.singleprice, p.bulkprice, p.instock, p.availabilty,p.location "
                    + "FROM  product p INNER JOIN manufacturer m ON p.manuID = m.manuID "
                    + "WHERE description LIKE ('%"
                    + SearchFieldProduct.getText()
                    + "%')");
        FillItemsClass.populateMultiColumnTable(productTable, productList, rs);
        break;
      case "location":
        rs =
            ConnectionDB.getResultSetfromInput(
                db,
                "SELECT p.productID,p.category,p.productname,m.manuname,"
                    + "p.singleprice, p.bulkprice, p.instock, p.availabilty,p.location "
                    + "FROM  product p INNER JOIN manufacturer m ON p.manuID = m.manuID "
                    + "WHERE p.location LIKE ('%"
                    + SearchFieldClient.getText()
                    + "%')");
        FillItemsClass.populateMultiColumnTable(productTable, productList, rs);
        break;
      case "manuname":
        rs =
            ConnectionDB.getResultSetfromInput(
                db,
                "SELECT p.productID,p.category,p.productname,p.location,m.manuname "
                    + "FROM product p INNER JOIN manufacturer m ON p.manuID = m.manuID "
                    + "WHERE manuname LIKE ('%"
                    + SearchFieldProduct.getText()
                    + "%')");
        FillItemsClass.populateMultiColumnTable(productTable, productList, rs);
        break;
      default:
        break;
    }
  }

  public void addClient() throws SQLException {
    ConnectionDB.getResultSetfromInput(
        db,
        "INSERT INTO "
            + "`client`(`clientname`,`username`,`password`, `clientaddress`, `clientemail`, `clientphone`, `shippingarea`) "
            + "VALUES ('"
            + clientName1.getText()
            + "','"
            + usernameText1.getText()
            + "','"
            + passwordText1.getText()
            + "','"
            + clientAddress1.getText()
            + "','"
            + clientEmail1.getText()
            + "','"
            + clientPhone1.getText()
            + "','"
            + choiceBoxArea1.getValue().toString()
            + "')");
    createCostumerTable();
    clientName1.setText("");
    clientAddress1.setText("");
    clientEmail1.setText("");
    clientPhone1.setText("");
    usernameText1.setText("");
    passwordText1.setText("");
  }

  public void addProduct() throws SQLException {
    ResultSet rs =
        ConnectionDB.getResultSetfromInput(
            db,
            "SELECT manuID FROM manufacturer "
                + "WHERE manuname LIKE('"
                + choiceBoxManu1.getValue().toString()
                + "')");
    String manuID = "";
    while (rs.next()) {
      manuID = rs.getString("manuID");
    }
    int l = 0;
    switch (choiceBoxAvail1.getValue().toString()) {
      case "Available":
        l = 1;
        break;
      case "Not Available":
        l = 0;
        break;
      default:
        break;
    }
    ConnectionDB.getResultSetfromInput(
        db,
        "INSERT INTO "
            + "`product`( `category`, `productname`, `description`, `instock`, `singleprice`, "
            + "`bulkprice`, `availabilty`, `location`, `manuID`) "
            + "VALUES "
            + "('"
            + choiceBoxCategory1.getValue().toString()
            + "','"
            + productnameText1.getText()
            + "','"
            + descriptionText1.getText()
            + "','"
            + instockText1.getText()
            + "','"
            + singlePriceText1.getText()
            + "','"
            + bulkpriceText1.getText()
            + "', '"
            + l
            + "', '"
            + choiceBoxLocation1.getValue().toString()
            + "', '"
            + manuID
            + "')");
    createProductTable();
    productnameText1.setText("");
    choiceBoxCategory1.setValue("--SELECT OPTION--");
    singlePriceText1.setText("");
    bulkpriceText1.setText("");
    usernameText1.setText("");
    instockText1.setText("");
    choiceBoxAvail1.setValue("--SELECT OPTION--");
    choiceBoxLocation1.setValue("--SELECT OPTION--");
    choiceBoxManu1.setValue("--SELECT OPTION--");
  }

  public void updateClient() throws SQLException {
    ConnectionDB.getResultSetfromInput(
        db,
        "UPDATE `client` SET "
            + "clientname = '"
            + clientName.getText()
            + "',`"
            + "clientaddress`='"
            + clientAddress.getText()
            + "',`"
            + "clientemail`='"
            + clientEmail.getText()
            + "',`"
            + "clientphone`='"
            + clientPhone.getText()
            + "',`"
            + "shippingarea`='"
            + choiceBoxArea.getValue().toString()
            + "'"
            + " WHERE clientID = '"
            + clientIDText.getText()
            + "'");
    createCostumerTable();
  }

  public void deleteClient() throws SQLException {
    ConnectionDB.getResultSetfromInput(
        db, "DELETE FROM `client` WHERE clientID ='" + clientIDText.getText() + "'");
    createCostumerTable();
  }

  public void selectitemorderlist() throws SQLException {
    String buff[] = SelectFromTableClass.parseFromMultiColumnTable(orderListTable);
    ResultSet rs =
        ConnectionDB.getResultSetfromInput(
            db,
            "SELECT p.productname,pol.amount,pol.subtotal FROM ordertab o "
                + "INNER JOIN orderlist ol ON ol.orderID_FK= o.orderID "
                + "INNER JOIN productorderlist pol ON ol.orderlistID = pol.orderlistID_FK "
                + "INNER JOIN product p ON p.productID=pol.productID_FK "
                + "WHERE o.orderID = "
                + buff[0]);
    FillItemsClass.populateMultiColumnTable(productorderTable, productorderList, rs);
  }

  public void updateUsernamePassword() throws SQLException {
    ConnectionDB.getResultSetfromInput(
        db,
        "UPDATE `client`"
            + " SET `username`= '"
            + usernameText.getText()
            + "',`password`= '"
            + passwordText.getText()
            + "' WHERE clientID = "
            + clientIDText.getText());
    ResultSet rs =
        ConnectionDB.getResultSetfromInput(
            db, "SELECT username,password FROM client WHERE clientID = " + clientIDText.getText());
    FillItemsClass.populateMultiColumnTable(loginDataTable, loginList, rs);
  }

  public void updateProduct() throws SQLException {
    ResultSet rs =
        ConnectionDB.getResultSetfromInput(
            db,
            "SELECT manuID "
                + "FROM manufacturer "
                + "WHERE manuname LIKE('"
                + choiceBoxManufacturer.getValue()
                + "')");
    String manuID = "";
    while (rs.next()) {
      manuID = rs.getString("manuID");
    }
    String desc = descriptionText.getText();
    int k = 0;
    switch (choiceBoxAvail.getValue().toString()) {
      case "Not Available":
        k = 0;
        break;
      case "Available":
        k = 1;
        break;
      default:
        break;
    }
    ConnectionDB.getResultSetfromInput(
        db,
        "UPDATE `product` SET "
            + "`category`= '"
            + choiceBoxCategory.getValue().toString()
            + "',"
            + "`productname`= '"
            + productnameText.getText()
            + "',"
            + "`description`= '"
            + desc
            + "',"
            + "`instock`= '"
            + instockText.getText()
            + "',"
            + "`singleprice`='"
            + singlePriceText.getText()
            + "',"
            + "`bulkprice`='"
            + bulkpriceText.getText()
            + "',"
            + "`availabilty`='"
            + k
            + "',"
            + "`location`='"
            + choiceBoxLocation.getValue().toString()
            + "',"
            + "`manuID`= '"
            + manuID
            + "'"
            + " WHERE productID = '"
            + productIDText.getText()
            + "'");
    createProductTable();
  }

  public void deleteProduct() throws SQLException {
    ConnectionDB.getResultSetfromInput(
        db, "DELETE FROM `product` WHERE productID ='" + productIDText.getText() + "'");
    createProductTable();
  }

  public void showAllProducts() throws SQLException {
    createProductTable();
  }

  public void showAllClients() throws SQLException {
    createCostumerTable();
  }

  public void itemsToRestock() throws SQLException {
    ResultSet rs =
        ConnectionDB.getResultSetfromInput(
            db,
            "SELECT p.productID,p.category,p.productname,m.manuname,"
                + "p.singleprice, p.bulkprice, p.instock, p.availabilty,p.location "
                + "FROM  product p INNER JOIN manufacturer m ON p.manuID = m.manuID "
                + "WHERE instock < 50");
    FillItemsClass.populateMultiColumnTable(productTable, productList, rs);
  }

  public void createStaffmemberTable() throws SQLException {
    ResultSet rs =
        ConnectionDB.getResultSetfromInput(
            db,
            "SELECT sm.staffID,sm.staffname,sm.staffphone,sm.staffemail,"
                + "sm.staffaddress,sm.staffsvnumber,sm.role,st.shippingarea,st.teamname FROM staffmember sm "
                + "INNER JOIN shippingteam st ON sm.shippingteamID = st.shippingteamID WHERE 1");
    FillItemsClass.populateMultiColumnTable(staffmemberTable, staffmemberList, rs);
  }

  public void createReceivedOrderTable() throws SQLException {
    // Execute Query for the CoffeeTable data
    ResultSet rs =
        ConnectionDB.getResultSetfromInput(
            db,
            "SELECT orderID,status,total,date"
                + " FROM ordertab WHERE status LIKE ('Order received')");
    FillItemsClass.populateMultiColumnTable(receivedOrdersTable, receivedOrdersList, rs);
  }

  public void createSentOrderTable() throws SQLException {
    // Execute Query for the CoffeeTable data
    ResultSet rs =
        ConnectionDB.getResultSetfromInput(
            db,
            "SELECT o.orderID,o.status,o.total,o.date"
                + " FROM ordertab o WHERE o.status LIKE ('Order sent')");
    FillItemsClass.populateMultiColumnTable(sentOrdersTable, sentOrdersList, rs);
  }

  public void createCompletedOrderTable() throws SQLException {
    // Execute Query for the CoffeeTable data
    ResultSet rs =
        ConnectionDB.getResultSetfromInput(
            db,
            "SELECT o.orderID,o.status,o.total,o.date"
                + " FROM ordertab o WHERE o.status LIKE ('Order completed')");
    FillItemsClass.populateMultiColumnTable(completedOrdersTable, sentOrdersList, rs);
  }

  public void selectReceivedOrderTable() throws SQLException {
    String buff[] = SelectFromTableClass.parseFromMultiColumnTable(receivedOrdersTable);

    ResultSet resultSet =
        ConnectionDB.getResultSetfromInput(
            db,
            "SELECT "
                + "c.clientID,c.clientname,c.clientaddress,c.clientphone,c.clientemail "
                + "FROM client c "
                + "INNER JOIN ordertab o ON c.clientID = o.clientID WHERE o.orderID = "
                + buff[0]);
    FillItemsClass.populateMultiColumnTable(clientOrderTable, clientOrderList, resultSet);
    resultSet =
        ConnectionDB.getResultSetfromInput(
            db,
            "SELECT "
                + "p.productID, p.productname, p.category, ol.amount, ol.subtotal "
                + "FROM product p "
                + "INNER JOIN productorderlist ol "
                + "ON p.productID = ol.productID_FK "
                + "INNER JOIN orderlist o "
                + "ON ol.orderlistID_FK=o.orderlistID "
                + "INNER JOIN ordertab ot "
                + "ON ot.orderID=o.orderID_FK "
                + "WHERE ot.orderID = "
                + buff[0]);
    FillItemsClass.populateMultiColumnTable(orderProductTable, orderProductList, resultSet);
  }

  public void selectSentOrderTable() throws SQLException {
    String buff[] = SelectFromTableClass.parseFromMultiColumnTable(sentOrdersTable);
    ResultSet resultSet =
        ConnectionDB.getResultSetfromInput(
            db,
            "SELECT "
                + "c.clientID,c.clientname,c.clientaddress,c.clientphone,c.clientemail "
                + "FROM client c "
                + "INNER JOIN ordertab o ON c.clientID = o.clientID WHERE o.orderID = "
                + buff[0]);
    FillItemsClass.populateMultiColumnTable(clientOrderTable, clientOrderList, resultSet);
    resultSet =
        ConnectionDB.getResultSetfromInput(
            db,
            "SELECT "
                + "p.productID, p.productname, p.category, p.amount, ol.subtotal"
                + " FROM product p "
                + "INNER JOIN productorderlist ol "
                + "ON p.productID = ol.productID_FK "
                + "INNER JOIN orderlist o "
                + "ON ol.orderlistID_FK=o.orderlistID "
                + "INNER JOIN ordertab ot "
                + "ON ot.orderID=ol.orderID_FK "
                + "WHERE o.orderID = "
                + buff[0]);
    FillItemsClass.populateMultiColumnTable(orderProductTable, orderProductList, resultSet);
  }

  public void selectCompletedOrderTable() throws SQLException {
    String buff[] = SelectFromTableClass.parseFromMultiColumnTable(completedOrdersTable);
    ResultSet resultSet =
        ConnectionDB.getResultSetfromInput(
            db,
            "SELECT "
                + "c.clientID,c.clientname,c.clientaddress,c.clientphone,c.clientemail "
                + "FROM client c "
                + "INNER JOIN ordertab o ON c.clientID = o clientID WHERE o.orderID = "
                + buff[0]);
    FillItemsClass.populateMultiColumnTable(clientOrderTable, clientOrderList, resultSet);
    resultSet =
        ConnectionDB.getResultSetfromInput(
            db,
            "SELECT "
                + "p.productID, p.productname, p.category, p.amount, ol.subtotal"
                + "FROM product p "
                + "INNER JOIN productorderlist ol "
                + "ON p.productID = ol.productID_FK "
                + "INNER JOIN orderlist o "
                + "ON ol.orderlistID_FK=o.orderlistID "
                + "INNER JOIN ordertab ot "
                + "ON ot.orderID=ol.orderID_FK "
                + "WHERE o.orderID = "
                + buff[0]);
    FillItemsClass.populateMultiColumnTable(orderProductTable, orderProductList, resultSet);
  }

  public void selectStaff() throws SQLException {
    // System.out.println("Staff selected");
    String buff[] = SelectFromTableClass.parseFromMultiColumnTable(staffmemberTable);
    ResultSet rs =
        ConnectionDB.getResultSetfromInput(
            db,
            "SELECT * FROM staffmember sm "
                + "INNER JOIN shippingteam st ON sm.shippingteamID = st.shippingteamID "
                + "WHERE staffID = '"
                + buff[0]
                + "'");
    while (rs.next()) {
      staffIDText.setText(rs.getString("staffID"));
      choiceBoxRole.setValue(rs.getString("role"));
      staffnameText.setText(rs.getString("staffname"));
      choiceBoxShippingarea.setValue(rs.getString("shippingarea"));
      staffemailText.setText(rs.getString("staffemail"));
      staffsvnumberText.setText(rs.getString("staffsvnumber"));
      staffphoneText.setText(rs.getString("staffphone"));
      staffaddressText.setText(rs.getString("staffaddress"));
      // choiceBoxTeam.setValue(rs.getString("teamname"));
    }
  }

  public void updateStaff() throws SQLException {

    int k = 0;
    switch (choiceBoxShippingarea.getValue().toString()) {
      case "NORD":
        k = 1;
        break;
      case "OST":
        k = 2;
        break;
      case "SÜD":
        k = 3;
        break;
      case "WEST":
        k = 4;
        break;
      default:
        break;
    }
    ConnectionDB.getResultSetfromInput(
        db,
        "UPDATE `staffmember` SET "
            + "`staffname`='"
            + staffnameText.getText()
            + "',"
            + "`staffphone`='"
            + staffphoneText.getText()
            + "',"
            + "`staffemail`='"
            + staffemailText.getText()
            + "',"
            + "`staffaddress`='"
            + staffaddressText.getText()
            + "',"
            + "`staffsvnumber`='"
            + staffsvnumberText.getText()
            + "',"
            + "`role`='"
            + choiceBoxRole.getValue().toString()
            + "',"
            + "`shippingteamID`= '"
            + k
            + "' "
            + " WHERE staffID ='"
            + staffIDText.getText()
            + "'");
    createStaffmemberTable();
  }

  public void deleteStaff() throws SQLException {

    ConnectionDB.getResultSetfromInput(
        db, "DELETE FROM `staffmember` WHERE staffID ='" + staffIDText.getText() + "'");
    createStaffmemberTable();
  }

  public void addStaff() throws SQLException {

    int k = 0;
    switch (choiceBoxShippingarea1.getValue().toString()) {
      case "NORD":
        k = 1;
        break;
      case "OST":
        k = 2;
        break;
      case "SÜD":
        k = 3;
        break;
      case "WEST":
        k = 4;
        break;
      default:
        break;
    }
    ConnectionDB.getResultSetfromInput(
        db,
        "INSERT INTO `staffmember`"
            + "(`staffname`, `staffphone`, `staffemail`, `staffaddress`, `staffsvnumber`, `role`, `shippingteamID`)"
            + " VALUES "
            + "('"
            + staffnameText1.getText()
            + "','"
            + staffphoneText1.getText()
            + "','"
            + staffemailText1.getText()
            + "','"
            + staffaddressText1.getText()
            + "','"
            + staffsvnumberText1.getText()
            + "','"
            + choiceBoxRole1.getValue().toString()
            + "','"
            + k
            + "')");
    createStaffmemberTable();
  }

  public void showAllStaff() throws SQLException {
    createStaffmemberTable();
  }
}
