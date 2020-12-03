import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

/**
 * Controller for Tab Pane. Sets up Tab Pane with 4 tabs. Tests Multimedia Control. Injects other
 * controllers. Sets up database credentials.
 *
 * @author Haley Yerxa
 */
public class Controller {

  /**
   * Anchor pane for product line.
   */
  public AnchorPane productLine;
  /**
   * Anchor pane for produce line.
   */
  public AnchorPane produce;
  /**
   * Anchor pane for production log.
   */
  public AnchorPane productionLog;
  /**
   * Anchor pane for employee.
   */
  public AnchorPane employee;
  /**
   * Production Log Controller.
   */
  @FXML
  private ProductionLogController productionLogController;
  /**
   * Produce Controller.
   */
  @FXML
  private ProduceController produceController;
  /**
   * Production Line Controller.
   */
  @FXML
  private ProductLineController productLineController;
  /*
  * Database Setup and usage throughout was learned through the following tutorial:
  * https://www.tutorialspoint.com/h2_database/h2_database_jdbc_connection
  */
  /**
   * Database driver.
   */
  public static final String JDBC_DRIVER = "org.h2.Driver";
  /**
   * Driver URL.
   */
  public static final String DB_URL = "jdbc:h2:./res/HR";
  /**
   * Get encrypted password from file.
   */
  public static final String encryptedPassword = readFile(
      "./src/main/resources/DatabasePassword.txt");
  /**
   * Reverse the encrypted string to get the actual password.
   */
  public static final String reversedPassword = Controller.reverseString(encryptedPassword);
  /**
   * Set database user name.
   */
  public static final String USER = "";
  /**
   * Set database password from file.
   */
  public static final String PASS = reversedPassword;

  /**
   * Append new products to listview.
   *
   * @param product new record to be added.
   */
  public void addToListView(Product product) {
    produceController.addLvItem(product);
  }

  /**
   * Append new records to product log.
   *
   * @param record new record to be added.
   */
  public void setProductLog(String record) {
    productionLogController.setTaText(record);
  }

  /**
   * Test Multimedia Control using console output.
   */
  public static void testMultimedia() {
    // Test Audio Player.
    AudioPlayer newAudioProduct = new AudioPlayer("DP-X1A", "Onkyo",
        "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL", 0);
    // Test Video Player.
    Screen newScreen = new Screen("720x480", 40, 22);
    MoviePlayer newMovieProduct = new MoviePlayer("DBPOWER MK101", "OracleProduction", newScreen,
        MonitorType.LCD, 0);
    // Second Test Audio Player.
    AudioPlayer newAudioProduct2 = new AudioPlayer("DP-X1B", "Onkyo2",
        "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3", "M3U/PLS", 1);
    // Second Test Video Player.
    Screen newScreen2 = new Screen("710x485", 45, 20);
    MoviePlayer newMovieProduct2 = new MoviePlayer("DBPOWER MK102", "OracleProduction3", newScreen2,
        MonitorType.LED, 1);

    // Create arraylist with each test Product.
    ArrayList<MultimediaControl> productList = new ArrayList<>();
    productList.add(newAudioProduct);
    productList.add(newMovieProduct);
    productList.add(newAudioProduct2);
    productList.add(newMovieProduct2);

    // Test Method Implementation
    for (MultimediaControl p : productList) {
      System.out.println(p);
      p.play();
      p.stop();
      p.next();
      p.previous();
    }
  }

  /*
   * Multiple controller injection idea came from source code from:
   * https://github.com/mvpjava/javaFX-multiple-controllers-tutorial/blob/master/src/main/java/com/mvp/java/controllers/MainController.java
   */

  /**
   * Test multimedia and inject controller in produce controller.
   */
  public void initialize() {
    testMultimedia();
    produceController.injectMainController(this);
    productLineController.injectMainController(this);
  }

  /**
   * Get text from a file.
   *
   * @param filename text file path.
   * @return String data in text file.
   */
  public static String readFile(String filename) {
    try {
      File myObj = new File(filename);
      Scanner fileReader = new Scanner(myObj);
      String data = "";
      while (fileReader.hasNextLine()) {
        data = fileReader.nextLine();
      }
      fileReader.close();
      return data;
    } catch (FileNotFoundException e) {
      System.out.println("File was not found");
      e.printStackTrace();
    }
    // if file is not found, method returns an empty string
    return "";
  }

  /**
   * Reverse the order of a string recursively. Developed by consulting the following tutorial:
   * https://beginnersbook.com/2017/09/java-program-to-reverse-a-string-using-recursion/
   *
   * @param pw given string, in this program used for password.
   * @return String reversed
   */
  public static String reverseString(String pw) {
    if (pw.isEmpty()) {
      return pw;
    }
    return reverseString(pw.substring(1)) + pw.charAt(0);
  }
}
