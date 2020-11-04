import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

/**
 * Controller for Tab Pane.
 * Sets up Tab Pane with 3 tabs
 * Haley Yerxa
 */
public class Controller implements Initializable {

  public static void testMultimedia() {

    AudioPlayer newAudioProduct = new AudioPlayer("DP-X1A", "Onkyo", "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL", 0);
    Screen newScreen = new Screen("720x480", 40, 22);
    MoviePlayer newMovieProduct = new MoviePlayer("DBPOWER MK101", "OracleProduction", newScreen, MonitorType.LCD, 0);
    ArrayList<MultimediaControl> productList = new ArrayList<MultimediaControl>();
    productList.add(newAudioProduct);
    productList.add(newMovieProduct);

    for (MultimediaControl p : productList) {
      System.out.println(p);
      p.play();
      p.stop();
      p.next();
      p.previous();

    }
  }

  @FXML
  private ProductionLogController productionLogController;

  @FXML
  private ProduceController produceController;

  @FXML
  private ProductLineController productLineController;

  /**
   * Append new products to listview
   * @param product new record to be added
   */
  public void addToListView(Product product) {
    produceController.addLVItem(product);
  }

  /**
   * Append new records to product log
   * @param record new record to be added
   */
  public void setProductLog(String record) {
    productionLogController.setTAText(record);
  }

  /**
   * Test multimedia and inject controller in produce controller
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    testMultimedia();
    produceController.injectMainController(this);
    productLineController.injectMainController(this);
  }

}
