import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * Controller for Produce fxml page.
 * Sets up combobox and button reaction for produce tab.
 * Haley Yerxa
 */
public class ProduceController {

  @FXML
  Button recordProduction;

  @FXML
  private ComboBox<String> quantity;

  /**
   * Connect to database and insert and display values.
   */
  public void initialize() {
    // Add 1-10 as options for combobox
    ObservableList<String> options = FXCollections
        .observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    quantity.setItems(options);
    quantity.getSelectionModel().selectFirst(); // default is first value (1)
    quantity.setEditable(true); // Allow user to enter custom values
  }

  /**
   * Print statement when button is pressed.
   */
  @FXML
  private void setRecordProduction(ActionEvent event) {
    System.out.println("Record Production");
  }
}
