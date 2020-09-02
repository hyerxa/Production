import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ProduceController {

    @FXML
    Button recordProduction;

    @FXML
    private void setRecordProduction(ActionEvent event) {
        System.out.println("Record Production");
    }
}
