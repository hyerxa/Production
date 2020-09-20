import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

/**
 * Controller for Tab Pane.
 * Sets up Tab Pane with 3 tabs
 * Haley Yerxa
 */
public class Controller implements Initializable {

  /**
   * The productLine tab.
   */
  @FXML
  Tab productLine;
  /**
   * The produce Tab.
   */
  @FXML
  Tab produce;
  /**
   * The production Log Tab.
   */
  @FXML
  Tab productionLog;

  /**
   * Setup Tabs and connect them to fxml files.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      AnchorPane anch1 = FXMLLoader.load(getClass().getResource("ProductLine.fxml"));
      productLine.setContent(anch1);
    } catch (IOException iex) {
      System.out.println("unable to load production line");
    }

    try {
      AnchorPane anch2 = FXMLLoader.load(getClass().getResource("Produce.fxml"));
      produce.setContent(anch2);
    } catch (IOException iex) {
      System.out.println("unable to load produce");
    }

    try {
      AnchorPane anch3 = FXMLLoader.load(getClass().getResource("ProductionLog.fxml"));
      productionLog.setContent(anch3);
    } catch (IOException iex) {
      System.out.println("unable to load production log");
    }

  }

}
