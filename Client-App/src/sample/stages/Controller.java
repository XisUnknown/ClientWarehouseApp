package sample.stages;

import SQLConnectionPackage.ConnectionDB;
import SQLConnectionPackage.FillItemsClass;
import SQLConnectionPackage.SelectFromTableClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Controller {

  private int pos;

  private static BigDecimal sum = BigDecimal.ZERO;
  private BigDecimal buffsum;

  @FXML private TableView allproducts;
  @FXML private TableView coffeeproducts;
  @FXML private TableView teaproducts;
  @FXML private TableView softdrinksproducts;
  @FXML private TableView shoppingcart;
  @FXML private TableView energydrinksproducts;
  @FXML private TableView orderListTable;
  @FXML private TableView productorderTable;
  @FXML private TextField clientUsername;
  @FXML private PasswordField clientPassword;
  @FXML private TextField productID;
  @FXML private TextField productnameText;
  @FXML private TextField singlepriceText;
  @FXML private TextField bulkpriceText;
  @FXML private TextField amountText;
  @FXML private TextField searchText;
  @FXML private Label loginlabel;
  @FXML private Label totalLabel;
  @FXML private Label productDescLabel;
  @FXML private ChoiceBox selectProductCategory;
  @FXML private Button proceedButton;

  static String userName="";
  public void getUserNameFromText(){
    userName = clientUsername.getText();
  }
  public static String getUserName() {
    return userName;
  }
  private ObservableList productorderList = FXCollections.observableArrayList();
  private ObservableList orderList = FXCollections.observableArrayList();
  private static ObservableList shoppingcartList = FXCollections.observableArrayList();
  private static ObservableList allproductsList = FXCollections.observableArrayList();
  private ObservableList coffeeproductsList = FXCollections.observableArrayList();
  private ObservableList teaproductsList = FXCollections.observableArrayList();
  private ObservableList softdrinksproductsList = FXCollections.observableArrayList();
  private ObservableList energydrinksproductsList = FXCollections.observableArrayList();
  public void getCartItems(){
    shoppingcartList = shoppingcart.getItems();
  }
  static ObservableList getShoppingcartList() {
    return shoppingcartList;
  }

  private TableColumn<String, String> shoppingcartCol =
      new TableColumn<String, String>("Shoppingcart");

  private String serverURL = "jdbc:mysql://localhost:3306/";
  private static String db = "feelgoodltd";
  static String getDb() {
    return db;
  }
  static BigDecimal getSum() {
    return sum;
  }
  TableView getShoppingcart() {
    return shoppingcart;
  }
  private String timeZone =
      "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin";
  private String buff = serverURL + db + timeZone;
  private String username = "root";
  private String password = "";
  private String createChoiceBoxStmt =
      "SELECT productID,productname FROM product WHERE instock <> 0";

  public static String getCreateAllProductsTableStmt() {
    return createAllProductsTableStmt;
  }

  private static String createAllProductsTableStmt =
      "SELECT productID,productname,singleprice,bulkprice,instock "
          + "FROM product WHERE instock <> 0";
  private String createCoffeeTableStmt =
      "SELECT productID,productname,singleprice,bulkprice,instock "
          + "FROM product WHERE category LIKE ('Coffee') AND instock <> 0";
  private String createTeaTableStmt =
      "SELECT productID,productname,singleprice,bulkprice,instock "
          + "FROM product WHERE category LIKE ('Tea') AND instock <> 0";
  private String createSoftDrinksTableStmt =
      "SELECT productID,productname,singleprice,bulkprice,instock "
          + "FROM product WHERE category LIKE ('Soft Drinks') AND instock <> 0";
  private String createEnergyDrinksTableStmt =
      "SELECT productID,productname,singleprice,bulkprice,instock "
          + "FROM product WHERE category LIKE ('Soft Drinks') AND instock <> 0";
  private String getProductDescStmt = "SELECT description FROM product WHERE productID = ";
  private String getPriceStmt = "SELECT singleprice,bulkprice FROM product WHERE productID = ";
  private String getAccessDataStmt =
      "SELECT singleprice,bulkprice FROM product WHERE productID = '";

  public void initialize() throws SQLException {
    ConnectionDB.getConnection(buff, username, password);
    createCoffeeTable();
    createTeaTable();
    createSoftDrinksTable();
    createEnergyDrinksTable();
    createAllProductsTable();
    ResultSet resultSet = ConnectionDB.getResultSetfromInput(db, createChoiceBoxStmt);
    FillItemsClass.fillChoiceBox(db, createChoiceBoxStmt, resultSet, selectProductCategory);
    FillItemsClass.initializeSingleColumnTable(shoppingcartCol, shoppingcart);
    clientUsername.setText(sample.stages.OrderingConfirmation.getUserName());
    clientPassword.setText(sample.stages.OrderingConfirmation.getPassword());
    userlogin();
    if(sample.stages.OrderingConfirmation.getUserName()==""){
      loginlabel.setText("");
    }

  }
  void createAllProductsTable() throws SQLException {
    ResultSet resultSet = ConnectionDB.getResultSetfromInput(db, createAllProductsTableStmt);
    FillItemsClass.populateMultiColumnTable(allproducts, allproductsList, resultSet);
  }

  private void createCoffeeTable() throws SQLException {
    ResultSet resultSet = ConnectionDB.getResultSetfromInput(db, createCoffeeTableStmt);
    FillItemsClass.populateMultiColumnTable(coffeeproducts, coffeeproductsList, resultSet);
  }

  private void createTeaTable() throws SQLException {
    ResultSet resultSet = ConnectionDB.getResultSetfromInput(db, createTeaTableStmt);
    FillItemsClass.populateMultiColumnTable(teaproducts, teaproductsList, resultSet);
  }

  private void createSoftDrinksTable() throws SQLException {
    ResultSet resultSet = ConnectionDB.getResultSetfromInput(db, createSoftDrinksTableStmt);
    FillItemsClass.populateMultiColumnTable(softdrinksproducts, softdrinksproductsList, resultSet);
  }

  private void createEnergyDrinksTable() throws SQLException {
    ResultSet resultSet = ConnectionDB.getResultSetfromInput(db, createEnergyDrinksTableStmt);
    FillItemsClass.populateMultiColumnTable(
        energydrinksproducts, energydrinksproductsList, resultSet);
  }

  private void selectFromProductTable(TableView tableView) throws SQLException {
    amountText.setText("");
    String buff[] = SelectFromTableClass.parseFromMultiColumnTable(tableView);
    productnameText.setText(buff[1]);
    singlepriceText.setText(buff[2]);
    bulkpriceText.setText(buff[3]);
    productID.setText(buff[0]);
    ResultSet rs = ConnectionDB.getResultSetfromInput(db, getProductDescStmt + buff[0]);
    FillItemsClass.setLabel(productDescLabel, rs, "description");
  }

  public void selectAllProductsTable() throws SQLException {
    selectFromProductTable(allproducts);
  }

  public void selectItemcoffee() throws SQLException {
    selectFromProductTable(coffeeproducts);
  }

  public void selectItemtea() throws SQLException {
    selectFromProductTable(teaproducts);
  }

  public void selectItemsoftdrinks() throws SQLException {
    selectFromProductTable(softdrinksproducts);
  }

  public void selectItemenergydrinks() throws SQLException {
    selectFromProductTable(energydrinksproducts);
  }

  public void selectitemshoppingcart() throws SQLException {
    pos = shoppingcart.getSelectionModel().getSelectedIndex();
    String buff[] = SelectFromTableClass.parseFromShoppingCartTable(shoppingcart);
    System.out.println(buff[0]);
    productID.setText(buff[1]);
    productnameText.setText(buff[2]);
    amountText.setText(buff[3]);
    ResultSet rs =
        ConnectionDB.getResultSetfromInput(
            db, "SELECT singleprice,bulkprice FROM product WHERE productID = " + buff[1]);
    while (rs.next()) {
      singlepriceText.setText(rs.getString("singleprice"));
      bulkpriceText.setText(rs.getString("bulkprice"));
    }
    BigDecimal bigDecimalSinglePrice = new BigDecimal(singlepriceText.getText());
    BigDecimal bigDecimalBulkPrice = new BigDecimal(bulkpriceText.getText());
    if (Integer.parseInt(amountText.getText()) < 10) {
      buffsum =
          bigDecimalSinglePrice.multiply(new BigDecimal(Integer.parseInt(amountText.getText())));

    } else {
      buffsum =
          bigDecimalBulkPrice.multiply(new BigDecimal(Integer.parseInt(amountText.getText())));
    }
  }

  public void addtoshoppingcart() throws SQLException {
    ResultSet rs =
        ConnectionDB.getResultSetfromInput(
            db, "SELECT instock FROM product WHERE productID = " + productID.getText());
    int instock = 0;
    while (rs.next()) {
      instock = rs.getInt("instock");
    }
    if (instock - Integer.parseInt(amountText.getText()) < 0) {
      productDescLabel.setText("Choosen amount to big!!");
    } else {
      BigDecimal bigDecimalTotal = BigDecimal.ZERO;
      BigDecimal bigDecimalSinglePrice = new BigDecimal(singlepriceText.getText());
      BigDecimal bigDecimalBulkPrice = new BigDecimal(bulkpriceText.getText());
      if (Integer.parseInt(amountText.getText()) < 10) {
        bigDecimalTotal =
            bigDecimalSinglePrice.multiply(new BigDecimal(Integer.parseInt(amountText.getText())));

      } else {
        bigDecimalTotal =
            bigDecimalBulkPrice.multiply(new BigDecimal(Integer.parseInt(amountText.getText())));
      }
      String total = bigDecimalTotal.toString();
      System.out.println(total);
      shoppingcartList.add(
          "No. "
              + productID.getText()
              + " ,productname: "
              + productnameText.getText()
              + " ,amount: "
              + amountText.getText()
              + " ,subtotal: "
              + total
              + "€");
      shoppingcart.setItems(shoppingcartList);
      shoppingcart.refresh();
      sum = sum.add(bigDecimalTotal);
      System.out.println(sum);
      totalLabel.setText("Total: " + sum.toString() + "€");
    }
  }

  public void deletefromshoppingcart() {
    shoppingcart.getItems().remove(shoppingcart.getSelectionModel().getSelectedItem());
    sum = sum.subtract(buffsum);
    totalLabel.setText("Total: " + sum.toString() + "€");
  }

  public void updateamount() throws SQLException {

    sum = sum.subtract(buffsum);
    shoppingcart.getItems().remove(pos);
    BigDecimal bigDecimalTotal = BigDecimal.ZERO;
    BigDecimal bigDecimalSinglePrice = new BigDecimal(singlepriceText.getText());
    BigDecimal bigDecimalBulkPrice = new BigDecimal(bulkpriceText.getText());
    if (Integer.parseInt(amountText.getText()) < 10) {
      bigDecimalTotal =
          bigDecimalSinglePrice.multiply(new BigDecimal(Integer.parseInt(amountText.getText())));

    } else {
      bigDecimalTotal =
          bigDecimalBulkPrice.multiply(new BigDecimal(Integer.parseInt(amountText.getText())));
    }
    String total = bigDecimalTotal.toString();
    shoppingcartList.add(
        "No. "
            + productID.getText()
            + " ,productname: "
            + productnameText.getText()
            + " "
            + " ,amount: "
            + amountText.getText()
            + " ,subtotal: "
            + total
            + "€");
    shoppingcart.setItems(shoppingcartList);
    shoppingcart.refresh();
    sum = sum.add(bigDecimalTotal);
    totalLabel.setText("Total: " + sum.toString() + "€");
  }

  public void userlogin() throws SQLException {
    ResultSet rs2 =
        ConnectionDB.getResultSetfromInput(
            db,
            "SELECT clientID, username, password FROM client "
                + "WHERE username LIKE('"
                + clientUsername.getText()
                + "') AND "
                + "password LIKE ('"
                + clientPassword.getText()
                + "')");
    String usernameCon = "";
    String passwordCon = "";
    String clientIDCon = "";
    while (rs2.next()) {
      clientIDCon = rs2.getString("clientID");
      usernameCon = rs2.getString("username");
      System.out.println("username");
      passwordCon = rs2.getString("password");
    }
    if (clientUsername.getText().equals(usernameCon)
        && passwordCon.equals(clientPassword.getText())) {
      sum = BigDecimal.ZERO;
      buffsum = BigDecimal.ZERO;
      totalLabel.setText("Total: " + sum + "€");
      ResultSet rs =
          ConnectionDB.getResultSetfromInput(
              db,
              "SELECT orderID,status,total,date "
                  + "FROM ordertab "
                  + "INNER JOIN client ON client.clientID = ordertab.clientID "
                  + "WHERE username LIKE ('"
                  + clientUsername.getText()
                  + "')");
      FillItemsClass.populateMultiColumnTable(orderListTable, orderList, rs);
      loginlabel.setText("Client ID: " + clientIDCon + " logged in!");
    }
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
  @FXML
  public void sendorder() throws SQLException {
    totalLabel.setText("Total: ");
    int orderID_FK = 0;
    Date date = new Date();
    String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
    ResultSet rs =
        ConnectionDB.getResultSetfromInput(db, "SELECT MAX(orderID) FROM ordertab WHERE 1");
    while (rs.next()) {
      orderID_FK = Integer.parseInt(rs.getString("MAX(orderID)")) + 1;
    }
    ResultSet rs2 =
        ConnectionDB.getResultSetfromInput(
            db,
            "SELECT clientID FROM client "
                + "WHERE username LIKE ('"
                + clientUsername.getText()
                + "')");
    String buffIDText = "";
    while (rs2.next()) {
      buffIDText = rs2.getString("clientID");
    }
    ConnectionDB.getResultSetfromInput(
        db,
        "INSERT INTO `ordertab`(`status`, `total`, `date`, `clientID`, `shippingteamID_FK`) "
            + "VALUES ('Orders received',"
            + sum
            + ",'"
            + dateString
            + "',"
            + buffIDText
            + ","
            + "1)");
    ConnectionDB.getResultSetfromInput(
        db, "INSERT INTO `orderlist`(`orderID_FK`) VALUES ('" + orderID_FK + "')");
    int buffID = 0;
    ObservableList buffList = shoppingcart.getItems();
    for (int i = 0; i < shoppingcart.getItems().size(); i++) {
      String buff = buffList.get(i).toString();

      String[] buff2 = buff.split("No. | ,productname: | ,amount: | ,subtotal: |€");

      System.out.println(buff2[1]);
      buffID = Integer.parseInt(buff2[1]);
      int buffAmount = Integer.parseInt(buff2[3]);
      rs =
          ConnectionDB.getResultSetfromInput(
              db, "SELECT instock FROM product WHERE productID = '" + buffID + "'");
      int buffUpdateAmount = 0;
      while (rs.next()) {
        buffUpdateAmount = rs.getInt("instock") - buffAmount;
        System.out.println(buffUpdateAmount);
      }
      if (buffUpdateAmount < 50) {
        ConnectionDB.getResultSetfromInput(
            db,
            "UPDATE `product` SET `instock`='"
                + buffUpdateAmount
                + "',`availabilty`='"
                + 0
                + "' WHERE productID = '"
                + buffID
                + "'");
        System.out.println(buffID);
      } else {
        ConnectionDB.getResultSetfromInput(
            db,
            "UPDATE `product` SET `instock`='"
                + buffUpdateAmount
                + "',`availabilty`='"
                + 1
                + "' WHERE productID = '"
                + buffID
                + "'");
      }
      BigDecimal buffTotalBigDecimal = new BigDecimal(buff2[4]);
      System.out.println(buff2[4]);
      String buffTotal = buffTotalBigDecimal.toString();
      ConnectionDB.getResultSetfromInput(
          db,
          "INSERT INTO `productorderlist`(`orderlistID_FK`, `productID_FK`, `amount`, `subtotal`) "
              + "VALUES ("
              + orderID_FK
              + ","
              + buffID
              + ","
              + buffAmount
              + ","
              + buffTotal
              + ")");
    }
    createAllProductsTable();
    createCoffeeTable();
    createSoftDrinksTable();
    createEnergyDrinksTable();
    createTeaTable();
      // stmt.execute("INSERT INTO `orderlist`(`orderID_FK`) VALUES ("+orderID_FK+")");
    orderList.clear();
    orderListTable.setItems(orderList);
    orderListTable.getColumns().clear();
    orderListTable.getItems().clear();
    // ResultSet resultSet = stmt.executeQuery("");
    rs =
        ConnectionDB.getResultSetfromInput(
            db,
            "SELECT orderID,status,total,date FROM ordertab WHERE clientID = '" + buffIDText + "'");
    FillItemsClass.populateMultiColumnTable(orderListTable, orderList, rs);
    shoppingcartList.clear();
    shoppingcart.setItems(shoppingcartList);
    shoppingcart.refresh();
    sum = BigDecimal.ZERO;
    buffsum = BigDecimal.ZERO;
  }

  public void searchProducts() throws SQLException {
    ResultSet rs;
    allproducts.getItems().clear();
    allproducts.getColumns().clear();
    switch (selectProductCategory.getValue().toString()) {
      case "productID":
        rs =
            ConnectionDB.getResultSetfromInput(
                db,
                "SELECT productID,productname,singleprice,bulkprice,instock "
                    + "FROM product WHERE instock <> 0 AND productID = '"
                    + searchText.getText()
                    + "'");
        FillItemsClass.populateMultiColumnTable(allproducts, allproductsList, rs);
        break;
      case "productname":
        rs =
            ConnectionDB.getResultSetfromInput(
                db,
                "SELECT productID,productname,singleprice,bulkprice,instock "
                    + "FROM product WHERE instock <> 0 AND productname LIKE ('%"
                    + searchText.getText()
                    + "%')");
        FillItemsClass.populateMultiColumnTable(allproducts, allproductsList, rs);
        break;
      case "All":
        createAllProductsTable();
      default:
        break;
    }
  }

  public void proceedToPayment() throws IOException {
    Stage stage = new Stage();
    Parent payment = FXMLLoader.load(getClass().getResource("/sample/stages/payment.fxml"));
    stage.setScene(new Scene(payment));
    stage.show();
    getCartItems();
    getUserNameFromText();
    stage = (Stage) proceedButton.getScene().getWindow();
    stage.hide();
  }
}
