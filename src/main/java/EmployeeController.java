import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Controls employee tab, accepting employee input.
 *
 * @author Haley Yerxa
 */

public class EmployeeController {

  /**
   * TextField for name.
   */
  @FXML
  private TextField name;
  /**
   * TextField for password.
   */
  @FXML
  private TextField password;
  /**
   * Button to enter info.
   */
  @FXML
  private Button enterButton;
  /**
   * TextArea to display employee details.
   */
  @FXML
  private TextArea detailsField;

  /**
   * Creates new Employee Based on info in fields.
   * If insufficient info is entered, default values are handled in employee class.
   */
  public void addUser() {

    String empName = name.getText();
    String empPassword = password.getText();

    Employee employee = new Employee(empName, empPassword);

    detailsField.setText(employee.toString());
  }

}

