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
    testMultimedia();



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

    FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductionLog.fxml"));
    try {
      loader.load();
    } catch (IOException iex) {
      System.out.println("unable to load production log");
    }
    ProductionLogController productionLogController = loader.getController();
    productionLogController.populateTextArea();

  }

}
