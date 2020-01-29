package SQLConnectionPackage;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FillItemsClass {
    private static void clearTable(TableView tableView, ObservableList items){
        items.clear();
        tableView.getItems().clear();
        tableView.getColumns().clear();
    }
    public static void initializeSingleColumnTable(TableColumn<String,String> colTable,TableView tableView){
        colTable.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getColumns().add(colTable);
    }
    public static void populateSingleColumnTable(String columnLabel, TableColumn<String, String> colTable,TableView tableView, ObservableList items, ResultSet resultSet) throws SQLException {
        clearTable(tableView,items);
        initializeSingleColumnTable(colTable,tableView);
        while (resultSet.next()) {
            items.add(resultSet.getString(columnLabel));
        }
        tableView.setItems(items);
    }
    public static void populateMultiColumnTable(TableView tableView, ObservableList items, ResultSet resultSet) throws SQLException {
        clearTable(tableView,items);
        for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
            final int j = i;
            TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i + 1));
            col.setCellValueFactory(
                    new Callback<
                            TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                        public ObservableValue<String> call(
                                TableColumn.CellDataFeatures<ObservableList, String> param) {
                            return new SimpleStringProperty(param.getValue().get(j).toString());
                        }
                    });
            tableView.getColumns().addAll(col);
        }
        while (resultSet.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                row.add(resultSet.getString(i));
            }
            items.add(row);
        }
        tableView.setItems(items);
    }
    public static void fillChoiceBox(ResultSet resultSet, ChoiceBox choiceBox) throws SQLException {
        //ResultSet resultSet = ConnectionDB.getResultSetfromInput(db,buff);
        for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
            choiceBox.getItems().add(resultSet.getMetaData().getColumnName(i + 1));
        }
        choiceBox.getItems().add("All");
    }
    public static void fillTextField(TextField textField,ResultSet resultSet,String buff) throws SQLException {
        while (resultSet.next()){
            textField.setText(resultSet.getString(buff));
        }
    }
    public static void setLabel(Label label,ResultSet resultSet,String buff) throws SQLException {
        while (resultSet.next()){
            label.setText(resultSet.getString(buff));
        }
    }
}
