import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import static java.lang.Integer.parseInt;

/**
 * Controller for Product Line fxml page. Connects to database to insert new products and display
 * current products. Haley Yerxa
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

  @FXML
  private TextField supportedAudioFormats;

  @FXML
  private TextField supportedPlaylistFormats;

  @FXML
  private TextField screenResolution;

  @FXML
  private TextField screenRefreshRate;

  @FXML
  private TextField screenResponseTime;

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
   * Main controller will be injected in to communicate with other controllers
   */
  private Controller mainController;

  /**
   * Method to add new product when button is pressed.
   *
   * @param event the action of pressing the button.
   */
  @FXML
  private void addProduct(ActionEvent event) {
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

      String prodName = productName.getText();
      String man = manufacturer.getText();
      String type = itemType.getValue();

      if (prodName.isEmpty() || man.isEmpty() || type == null) {
        System.out.println("Insufficient info to add product");
        return;
      }

      boolean errorInput = false;

      switch (type) {
        case "AUDIO":
        case "AUDIOMOBILE":
          String supportedAudio = supportedAudioFormats.getText();
          String supportedPlaylist = supportedPlaylistFormats.getText();
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
        case "VIDEO":
        case "VISUALMOBILE":
          String screenRes = screenResolution.getText();
          int screenRef = 0;
          int screenResT = 0;
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

          Screen screen = new Screen(screenRes, screenRef, screenResT);
          String monStringType = monitorType.getValue();
          MonitorType monType = MonitorType.LED;
          if (monStringType != null) {
            switch (monStringType) {
              case "LCD":
                monType = MonitorType.LCD;
                break;
              case "LED":
                monType = MonitorType.LED;
                break;
            }
          }
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
          } else {
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

  public void retrieveFromDb() {
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
        ProduceController.addToList(rs, productLineList);
        productTable.setItems(productLineList);
      }

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }
  }

  @SuppressWarnings("unchecked")
  public void setUpProductLineTable() {
    productNameCol.setCellValueFactory(new PropertyValueFactory("Name"));
    manufacturerCol.setCellValueFactory(new PropertyValueFactory("Manufacturer"));
    itemTypeCol.setCellValueFactory(new PropertyValueFactory("Type"));

  }

  public void initialize() {
    setUpProductLineTable();
    retrieveFromDb();
    itemType.getSelectionModel().selectFirst();
    monitorType.getSelectionModel().selectFirst();
  }

}
