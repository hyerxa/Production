import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class ProduceController {

    @FXML
    Button recordProduction;

    @FXML
    private ComboBox<String> quantity;

    public void initialize() {
        ObservableList<String> options = FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10");
        quantity.setItems(options);
        quantity.getSelectionModel().selectFirst();
        quantity.setEditable(true);
    }

    @FXML
    private void setRecordProduction(ActionEvent event) {
        System.out.println("Record Production");
    }
}
