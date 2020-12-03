import static java.lang.Integer.parseInt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller for Product Line fxml page.
 * Connects to database to insert new products and display
 * current products.
 *
 * @author Haley Yerxa
 */

public class ProductLineController {

  /**
   * Button to submit new product.
   */
  @FXML
  Button addProduct;
  /**
   * Text field for user input of new product name.
   */
  @FXML
  private TextField productName;
  /**
   * Choice Box for type of new item.
   */
  @FXML
  private ChoiceBox<String> itemType;
  /**
   * Text field for user input of manufacturer.
   */
  @FXML
  private TextField manufacturer;
  /**
   * Text field for user input of supported audio formats.
   */
  @FXML
  private TextField supportedAudioFormats;
  /**
   * Text field for user input of supported playlist formats.
   */
  @FXML
  private TextField supportedPlaylistFormats;
  /**
   * Text field for user input of screen resolution.
   */
  @FXML
  private TextField screenResolution;
  /**
   * Text field for user input of screen refresh rate.
   */
  @FXML
  private TextField screenRefreshRate;
  /**
   * Text field for user input of screen response time.
   */
  @FXML
  private TextField screenResponseTime;
  /**
   * ChoiceBox for user input of monitor type.
   */
  @FXML
  private ChoiceBox<String> monitorType;
  /**
   * Table to display products.
   */
  @FXML
  private TableView<Product> productTable;
  /**
   * Column in table for productName.
   */
  @FXML
  private TableColumn<?, ?> productNameCol;
  /**
   * Column in table for manufacturer.
   */
  @FXML
  private TableColumn<?, ?> manufacturerCol;
  /**
   * Column in table for item type.
   */
  @FXML
  private TableColumn<?, ?> itemTypeCol;
  /**
   * Main controller will be injected in to communicate with other controllers.
   */
  private Controller mainController;

  /**
   * Method to add new product when button is pressed.
   * Clears all textFields to reset and allow for new
   * info to be entered.
   */
  @FXML
  private void addProduct() {
    insertToDb();
    productName.clear();
    manufacturer.clear();
    supportedAudioFormats.clear();
    supportedPlaylistFormats.clear();
    screenResolution.clear();
    screenRefreshRate.clear();
    screenResponseTime.clear();
  }

  /**
   * Inject main controller for communication with other controllers.
   */
  public void injectMainController(Controller mainController) {
    this.mainController = mainController;
  }

  /**
   * Connect to database and insert and display values.
   */
  public void insertToDb() {

    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(Controller.JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(Controller.DB_URL, Controller.USER, Controller.PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      // Get important data from fields
      String prodName = productName.getText();
      String man = manufacturer.getText();
      String type = itemType.getValue();

      // If any of the fields are not filled, display error message and exit function.
      if (prodName.isEmpty() || man.isEmpty() || type == null) {
        System.out.println("Insufficient info to add product");
        return;
      }

      // Flag to see if any optional fields are empty
      boolean errorInput = false;

      switch (type) {
        // For audio and audiomobile, check optional audio fields
        case "AUDIO":
        case "AUDIOMOBILE":
          // Will be empty strings if not filled, won't cause error
          String supportedAudio = supportedAudioFormats.getText();
          String supportedPlaylist = supportedPlaylistFormats.getText();
          // Add new Product to listview
          if (type.equals("AUDIO")) {
            AudioPlayer tempAudio = new AudioPlayer(prodName, man, ItemType.AUDIO, supportedAudio,
                supportedPlaylist);
            mainController.addToListView(tempAudio);
          } else {
            AudioPlayer tempAudioMobile = new AudioPlayer(prodName, man, ItemType.AUDIOMOBILE,
                supportedAudio, supportedPlaylist);
            mainController.addToListView(tempAudioMobile);
          }
          break;

        // Check to see if visual fields are filled out.
        case "VIDEO":
        case "VISUALMOBILE":
          String screenRes = screenResolution.getText();
          int screenRef = 0;
          int screenResT = 0;
          // Fields should be integers
          try {
            screenRef = parseInt(screenRefreshRate.getText());
          } catch (Exception e) {
            System.out.println("Please enter a valid integer for screen refresh rate");
            errorInput = true;
          }
          try {
            screenResT = parseInt(screenResponseTime.getText());
          } catch (Exception e) {
            System.out.println("Please enter a valid integer for screen response time");
            errorInput = true;
          }
          // Create new screen object
          Screen screen = new Screen(screenRes, screenRef, screenResT);
          String monStringType = monitorType.getValue();
          MonitorType monType = MonitorType.LED;
          // If a monitor type is selected, set the temp value.
          if (monStringType != null) {
            switch (monStringType) {
              case "LCD":
                monType = MonitorType.LCD;
                break;
              case "LED":
                monType = MonitorType.LED;
                break;
              default:
                break;
            }
          } else {
            errorInput = true;
          }

          // If optional fields were all filled out, create object with detailed constructor
          if (!errorInput) {
            if (type.equals("VIDEO")) {
              MoviePlayer tempVideo = new MoviePlayer(prodName, man, ItemType.VISUAL, screen,
                  monType);
              mainController.addToListView(tempVideo);
            } else {
              MoviePlayer tempVisualMobile = new MoviePlayer(prodName, man, ItemType.VISUALMOBILE,
                  screen, monType);
              mainController.addToListView(tempVisualMobile);
            }
          } else { // Otherwise just create with basic info.
            if (type.equals("VIDEO")) {
              MoviePlayer tempVideo = new MoviePlayer(prodName, man, ItemType.VISUAL);
              mainController.addToListView(tempVideo);
            } else {
              MoviePlayer tempVisualMobile = new MoviePlayer(prodName, man, ItemType.VISUALMOBILE);
              mainController.addToListView(tempVisualMobile);
            }
          }
          break;
        default:
          System.out.println("Invalid type");
      }

      // Insert value into table
      String sql = "INSERT INTO Product(type, manufacturer, name) VALUES (?,?,?)";

      PreparedStatement preparedStatement = conn.prepareStatement(sql);
      preparedStatement.setString(1, type);
      preparedStatement.setString(2, man);
      preparedStatement.setString(3, prodName);

      preparedStatement.executeUpdate();
      retrieveFromDb();

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }
  }

  /**
   * Retrieve all values from Database to set product list.
   */
  public void retrieveFromDb() {
    // Observable List for products
    ObservableList<Product> productLineList = FXCollections.observableArrayList();

    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(Controller.JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(Controller.DB_URL, Controller.USER, Controller.PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      // Retrieve all entries in table
      String sql2 = "SELECT name, manufacturer, type, ID FROM Product";

      ResultSet rs = stmt.executeQuery(sql2);

      while (rs.next()) {
        // Call function from other controller, add all contents to list.
        ProduceController.addToList(rs, productLineList);
      }
      // Set items in table
      productTable.setItems(productLineList);

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }
  }

  /**
   * Set up the product line table with labels.
   */
  // Suppress warnings because this type will always work.
  @SuppressWarnings("unchecked")
  public void setUpProductLineTable() {
    productNameCol.setCellValueFactory(new PropertyValueFactory("Name"));
    manufacturerCol.setCellValueFactory(new PropertyValueFactory("Manufacturer"));
    itemTypeCol.setCellValueFactory(new PropertyValueFactory("Type"));

  }

  /**
   * Initialize by setting up table and setting first value in choice boxes.
   */
  public void initialize() {
    setUpProductLineTable();
    retrieveFromDb();
    itemType.getSelectionModel().selectFirst();
    monitorType.getSelectionModel().selectFirst();
  }
}
