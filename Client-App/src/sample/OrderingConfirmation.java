package sample;

import SQLConnectionPackage.ConnectionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderingConfirmation {

  private static String userName = "";
  private static String password = "";



  @FXML private Button returnButtonCompleteOrder;
  @FXML private Label confirmOrderLabel;
  @FXML private Button sendOrderButton;

  public static String getUserName() {
      return userName;
  }
  public void closeWindowCompleteOrder() {
    Stage stage = (Stage) returnButtonCompleteOrder.getScene().getWindow();
    stage.close();
  }

  public void initialize() throws SQLException {
    fillSummary();
  }

  private void fillSummary() throws SQLException {
    String orderSummary =
        "Order Summary: \n Total: "
            + Controller.getSum()
            + "€ "
            + "\n Payment Method: "
            + Payment.getSelectedPaymentChoice()
            + "\nOrdered Products List: \n ";
    ObservableList shoppingcartList = FXCollections.observableArrayList();
    shoppingcartList.addAll(Controller.getShoppingcartList());
    System.out.println(shoppingcartList.size());
    // String buff[] = new String[shoppingcart.getItems().size()];
    String buffOrderedProduct = "\n";
    // int i = 1;
    for (int i = 0; i < shoppingcartList.size(); i++) {
      String buff[] =
          shoppingcartList
              .get(i)
              .toString()
              .split("No. | ,productname: | ,amount: | ,subtotal: |€");
      System.out.println(orderSummary);
      orderSummary =
          orderSummary
              + "ProductID: "
              + buff[1]
              + " Productname: "
              + buff[2]
              + " Amount: "
              + buff[3]
              + " Subtotal: "
              + buff[4]
              + "€ \n";
      System.out.println(orderSummary);
    }
    System.out.println("----------------------");
    System.out.println(Controller.getUserName());
    System.out.println("----------------------");
    ResultSet resultSet =
        ConnectionDB.getResultSetfromInput(
            Controller.getDb(),
            "SELECT * "
                + "FROM client "
                + "WHERE username LIKE ('"
                + Controller.getUserName()
                + "')");
    while (resultSet.next()) {
      orderSummary =
          orderSummary
              + "\n Shipping Adress: \n"
              + resultSet.getString("clientname")
              + "\n"
              + resultSet.getString("clientaddress")
              + "\n"
              + resultSet.getString("clientphone")
              + "\n"
              + resultSet.getString("clientemail");
      System.out.println(orderSummary);
      password = resultSet.getString("password");
      userName = resultSet.getString("username");
    }
    confirmOrderLabel.setText(orderSummary);
  }

  public boolean sendOrder() throws SQLException, IOException {
    int orderID_FK = 0;
    Date date = new Date();
    String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
    ResultSet rs =
        ConnectionDB.getResultSetfromInput(
            Controller.getDb(), "SELECT MAX(orderID) FROM ordertab WHERE 1");
    while (rs.next()) {
      orderID_FK = Integer.parseInt(rs.getString("MAX(orderID)")) + 1;
    }
    ResultSet rs2 =
        ConnectionDB.getResultSetfromInput(
            Controller.getDb(),
            "SELECT clientID FROM client " + "WHERE username LIKE ('" + Controller.userName + "')");
    String buffIDText = "";
    while (rs2.next()) {
      buffIDText = rs2.getString("clientID");
    }
    ConnectionDB.getResultSetfromInput(
        Controller.getDb(),
        "INSERT INTO `ordertab`(`status`, `total`, `date`, `clientID`, `shippingteamID_FK`) "
            + "VALUES ('Orders received',"
            + Controller.getSum()
            + ",'"
            + dateString
            + "',"
            + buffIDText
            + ","
            + "1)");
    ConnectionDB.getResultSetfromInput(
        Controller.getDb(), "INSERT INTO `orderlist`(`orderID_FK`) VALUES ('" + orderID_FK + "')");
    int buffID = 0;
    ObservableList shoppingcartList = FXCollections.observableArrayList();
    shoppingcartList.addAll(Controller.getShoppingcartList());
    for (int i = 0; i < shoppingcartList.size(); i++) {
      String[] buff2 =
          shoppingcartList
              .get(i)
              .toString()
              .split("No. | ,productname: | ,amount: | ,subtotal: |€");

      System.out.println(buff2[1]);
      buffID = Integer.parseInt(buff2[1]);
      int buffAmount = Integer.parseInt(buff2[3]);
      rs =
          ConnectionDB.getResultSetfromInput(
              Controller.getDb(), "SELECT instock FROM product WHERE productID = '" + buffID + "'");
      int buffUpdateAmount = 0;
      while (rs.next()) {
        buffUpdateAmount = rs.getInt("instock") - buffAmount;
        System.out.println(buffUpdateAmount);
      }
      if (buffUpdateAmount < 50) {
        ConnectionDB.getResultSetfromInput(
            Controller.getDb(),
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
            Controller.getDb(),
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
          Controller.getDb(),
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
      // ResultSet resultSet = ConnectionDB.getResultSetfromInput(Controller.getDb(),
      // Controller.getCreateAllProductsTableStmt());
      // FillItemsClass.populateMultiColumnTable(Controller.getAllproducts(), allproductsList,
      // resultSet);
      Stage stage = (Stage) sendOrderButton.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
      Stage primaryStage = new Stage();
      primaryStage.setTitle("Feel Good Ltd. Client Order App");
      primaryStage.setScene(new Scene(root, 1200, 700));
      primaryStage.show();
      stage.close();
    }
    return true;
  }

  public static String getPassword() {
    return password;
  }
}
