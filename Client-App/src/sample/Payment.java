package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class Payment {

    @FXML private RadioButton onDeliveryButton;

    public RadioButton getAdvancePaymentButton() {
        return advancePaymentButton;
    }

    @FXML private RadioButton advancePaymentButton;
    @FXML private RadioButton upsButton;
    @FXML private RadioButton dhlButton;
    @FXML private RadioButton postButton;
    @FXML private RadioButton glsButton;
    @FXML private Button returnButtonPayment;
    static ToggleGroup paymentToggle = new ToggleGroup();
    static ToggleGroup deliveryToggle = new ToggleGroup();
    RadioButton selectedRadioButton;
    RadioButton selectedRadioButton2;
    public void initialize(){
        onDeliveryButton.setToggleGroup(paymentToggle);
        advancePaymentButton.setToggleGroup(paymentToggle);
        dhlButton.setToggleGroup(deliveryToggle);
        glsButton.setToggleGroup(deliveryToggle);
        postButton.setToggleGroup(deliveryToggle);
        upsButton.setToggleGroup(deliveryToggle);
    }
    public void proceedToOrderingConfirmation() throws IOException {
        Stage newstage = new Stage();
        Parent ordercompleted = FXMLLoader.load(getClass().getResource("OrderingConfirmation.fxml"));
        newstage.setScene(new Scene(ordercompleted));
        //newstage.initOwner(returnButtonPayment.getScene().getWindow());
        newstage.show();
        selectedRadioButton = (RadioButton) paymentToggle.getSelectedToggle();
        selectedRadioButton2 = (RadioButton) deliveryToggle.getSelectedToggle();
        Stage stage = (Stage) returnButtonPayment.getScene().getWindow();
        stage.close();
    }
    public void closeWindowPayment(){
        Stage stage = (Stage) returnButtonPayment.getScene().getWindow();
        stage.close();
    }

    public static String getSelectedPaymentChoice(){
        RadioButton selectedRadioButton = (RadioButton) paymentToggle.getSelectedToggle();
        return selectedRadioButton.getText();
    }
    public String getSelectedDeliveryChoice(){
        RadioButton selectedRadioButton = (RadioButton) deliveryToggle.getSelectedToggle();
        return selectedRadioButton.getText();
    }
}
