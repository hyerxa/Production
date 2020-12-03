import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

/**
 * Controller for Produce fxml page. Sets up combobox and button reaction for produce tab.
 *
 * @author Haley Yerxa
 */
public class ProduceController {

  /**
   * Button to record production.
   */
  @FXML
  Button recordProduction;

  /**
   * Combobox with quantity options.
   */
  @FXML
  private ComboBox<String> quantity;

  /**
   * ListView with Product options.
   */
  @FXML
  private ListView<Product> prodList;

  /**
   * ObservableList to hold all product options.
   */
  final ObservableList<Product> productList = FXCollections.observableArrayList();

  /**
   * Main controller will be injected in to communicate with other controllers.
   */
  private Controller mainController;

  /**
   * Stores highest production number.
   */
  private int highestProdNum = 0;

  /**
   * Inject main controller for communication with other controllers.
   */
  public void injectMainController(Controller mainController) {
    this.mainController = mainController;
  }

  /**
   * Adds new product to list view.
   *
   * @param product product to be added to listview.
   */
  public void addLvItem(Product product) {
    prodList.getItems().add(product);
  }

  /**
   * Given result set, adds new product to list.
   *
   * @param rs result set to be evaluated.
   * @param list Observable list to be added to.
   */
  public static void addToList(ResultSet rs, ObservableList<Product> list) {
    try {
      /*
        Add specific product type to list
      */
      switch (rs.getString(3)) {
        case "AUDIO":
          list.add(new AudioPlayer(rs.getString(1), rs.getString(2), ItemType.AUDIO, rs.getInt(4)));
          break;
        case "AUDIOMOBILE":
          list.add(new AudioPlayer(rs.getString(1), rs.getString(2), ItemType.AUDIOMOBILE,
              rs.getInt(4)));
          break;
        case "VIDEO":
          list.add(
              new MoviePlayer(rs.getString(1), rs.getString(2), rs.getInt(4), ItemType.VISUAL));
          break;
        case "VISUALMOBILE":
          list.add(new MoviePlayer(rs.getString(1), rs.getString(2), rs.getInt(4),
              ItemType.VISUALMOBILE));
          break;
        default:
          System.out.println("Invalid Type");
      }
    } catch (SQLException e) {
      System.out.println("Could not connect to database");
      e.printStackTrace();
    }
  }

  /**
   * Retrieve all products from database.
   */
  public void retrieveFromDb() {

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
        addToList(rs, productList);
      }

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Populate Combobox and Listview.
   */
  public void initialize() {

    // Add 1-10 as options for combobox
    ObservableList<String> options = FXCollections
        .observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    quantity.setItems(options);
    quantity.getSelectionModel().selectFirst(); // default is first value (1)
    quantity.setEditable(true); // Allow user to enter custom values

    Connection conn;
    Statement stmt;

    /*
      Create List to store serial numbers so they can be incremented
     */
    List<String> serialNumbers = new ArrayList<>();
    /*
      Stores production numbers to set new records with correct production number
     */
    List<Integer> productionNumbers = new ArrayList<>();

    try {
      // STEP 1: Register JDBC driver
      Class.forName(Controller.JDBC_DRIVER);
      //STEP 2: Open a connection
      conn = DriverManager.getConnection(Controller.DB_URL, Controller.USER, Controller.PASS);
      //STEP 3: Execute a query
      stmt = conn.createStatement();

      // first select serial numbers to determine most recent entry
      String sql = "SELECT serial_num, production_num FROM ProductionRecord";
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        serialNumbers.add(rs.getString(1));
        productionNumbers.add(rs.getInt(2));
      }

    } catch (ClassNotFoundException | SQLException e) {
      System.out.println("Could not connect to database");
      e.printStackTrace();
    }

    /*
      Sets highest production number
     */
    for (int prodNum : productionNumbers) {
      if (highestProdNum < prodNum) {
        highestProdNum = prodNum;
      }
    }

    /*
      Stores number of audio products in database
     */
    int countAudio = 0;
    /*
      Stores number of video products in database
     */
    int countVideo = 0;

    /*
      Go through serial numbers and increment each count
     */
    for (String serialNum : serialNumbers) {
      switch (serialNum.substring(3, 5)) {
        case "AU":
        case "AM":
          countAudio++;
          break;
        case "VI":
        case "VM":
          countVideo++;
          break;
        default:
          System.out.println("Invalid code");
      }
    }
    /*
      Set the starting counts to the correct value so new entries can autoincrement
     */
    Product.setAudioCount(countAudio);
    Product.setVisualCount(countVideo);

    //populate list, retrieve all entries
    retrieveFromDb();

    // Add all entries to the listview
    for (Product product : productList) {
      prodList.getItems().add(product);
    }
  }

  /**
   * Add record to database when record button is pressed.
   */
  @FXML
  private void setRecordProduction() {

    Connection conn;
    Statement stmt;

    /*
      Product object to hold selected item
     */
    Product currentProduct = prodList.getSelectionModel().getSelectedItem();
    if (currentProduct == null) {
      return;
    }

    /*
      List to hold all current production records
     */
    List<ProductionRecord> prodRecords = new ArrayList<>();

    /*
      Number of records the user wishes to submit.
     */
    int numInsert;
    try {
      numInsert = Integer.parseInt(quantity.getValue());
    } catch (Exception e) {
      System.out.println("Invalid numeric input");
      return;
    }


    /*
      Add new record(s) to list and increment for serial number
     */
    switch (currentProduct.getType().getCode()) {
      case "AU":
      case "AM":
        for (int i = 0; i < numInsert; i++) {
          // pre-increment count
          Product.setAudioCount(Product.getAudioCount() + 1);
          highestProdNum++;
          // add record
          ProductionRecord tempAudioRecord = new ProductionRecord(currentProduct,
              Product.getAudioCount());
          tempAudioRecord.setProductionNumber(highestProdNum);
          prodRecords.add(tempAudioRecord);
        }
        break;

      case "VI":
      case "VM":
        for (int i = 0; i < numInsert; i++) {
          Product.setVisualCount(Product.getVisualCount() + 1);
          highestProdNum++;
          ProductionRecord tempVideoRecord = new ProductionRecord(currentProduct,
              Product.getVisualCount());
          tempVideoRecord.setProductionNumber(highestProdNum);
          prodRecords.add(tempVideoRecord);
        }
        break;

      default:
        System.out.println("Invalid code");
    }

    /*
      Insert new records into database
     */
    try {
      // STEP 1: Register JDBC driver
      Class.forName(Controller.JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(Controller.DB_URL, Controller.USER, Controller.PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      for (ProductionRecord prodRecord : prodRecords) {
        /*
          Setup query fields
         */
        int prodId = prodRecord.getProductId();
        String serialNum = prodRecord.getSerialNumber();
        Date date = prodRecord.getDateProduced();

        // Insert value into table
        String sql =
            "INSERT INTO ProductionRecord(product_id, serial_num, date_produced) VALUES (?,?,?)";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, prodId);
        preparedStatement.setString(2, serialNum);
        preparedStatement.setObject(3, date);

        preparedStatement.executeUpdate();

        // Update product log
        mainController.setProductLog(prodRecord.toString() + "\n");
      }

      retrieveFromDb();

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

  }
}
