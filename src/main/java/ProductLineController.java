import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ProductLineController {

    @FXML
    Button addProduct;

    @FXML
    private void setAddProduct(ActionEvent event) {
        System.out.println("Add Product");
    }
}
