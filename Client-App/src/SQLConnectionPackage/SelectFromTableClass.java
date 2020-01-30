package SQLConnectionPackage;

import javafx.scene.control.TableView;

public class SelectFromTableClass {
    public static String[] parseFromMultiColumnTable(TableView tableView){
        String selectedItem = tableView.getSelectionModel().getSelectedItem().toString();
        String[] buff = selectedItem.split(", ");
        String buff3 = buff[0].substring(1);
        String[] buff2 = buff[buff.length-1].split("]");
        buff[0] = buff3;
        buff[buff.length-1] = buff2[0];
        return buff;
    }
    public static String[] parseFromShoppingCartTable(TableView tableView){
        String buff = tableView.getSelectionModel().getSelectedItem().toString();
        return buff.split("No. | ,productname: | ,amount: | ,subtotal: |€");
    }

}
