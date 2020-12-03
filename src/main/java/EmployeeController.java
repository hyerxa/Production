import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EmployeeController {

    @FXML
    private TextField name;

    @FXML
    private TextField password;

    @FXML
    private Button enterButton;

    @FXML
    private TextArea detailsField;

    public void addUser() {

        String empName = name.getText();
        String empPassword = password.getText();

        Employee employee = new Employee(empName, empPassword);

        detailsField.setText(employee.toString());
    }

}

