import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import java.util.Date;

import java.sql.*;
import javafx.scene.layout.AnchorPane;

/**
 * Controller for Produce fxml page.
 * Sets up combobox and button reaction for produce tab.
 * Haley Yerxa
 */
public class ProduceController {

  /**
   * Button to record production.
   */
  @FXML Button recordProduction;

  /**
   * Combobox with quantity options.
   */
  @FXML private ComboBox<String> quantity;

  /**
   * ListView with Product options.
   */
  @FXML private ListView<Product> prodList;

  /**
   * ObservableList to hold all product options
   */
  ObservableList<Product> productLineList = FXCollections.observableArrayList();

  /**
   * Main controller will be injected in to communicate with other controllers
   */
  private Controller mainController;

  /**
   * Stores highest production number
   */
  private int highestProdNum = 0;

  /**
   * Inject main controller for communication with other controllers.
   */
  public void injectMainController(Controller mainController) {
    this.mainController = mainController;
  }

  /**
   * Adds new product to list view
   * @param product product to be added to listview
   */
  public void addLVItem(Product product) {
    prodList.getItems().add(product);
  }

  /**
   * Retrieve all products from database
   */
  public void retrieveFromDb() {
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/HR";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      // Retrieve all entries in table
      String sql2 = "SELECT name, manufacturer, type, ID FROM Product";

      ResultSet rs = stmt.executeQuery(sql2);

      while (rs.next()) {
        /**
         * Add specific product type to list
         */
        switch (rs.getString(3)) {
          case "AUDIO":
            productLineList.add(new AudioPlayer(rs.getString(1), rs.getString(2), rs.getInt(4)));
            break;

          case "VIDEO":
            productLineList.add(new MoviePlayer(rs.getString(1), rs.getString(2), rs.getInt(4)));
            break;
        }
      }

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    }
    catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }
  }

  /**
   * Populate Combobox and Listview
   */
  public void initialize() {

    // Add 1-10 as options for combobox
    ObservableList<String> options = FXCollections
        .observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    quantity.setItems(options);
    quantity.getSelectionModel().selectFirst(); // default is first value (1)
    quantity.setEditable(true); // Allow user to enter custom values

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/HR";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn;
    Statement stmt;

    /**
     * Create List to store serial numbers so they can be incremented
     */
    List<String> serialNumbers = new ArrayList<>();
    /**
     * Stores production numbers to set new records with correct production number
     */
    List<Integer> productionNumbers = new ArrayList<>();

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);
      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
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
      e.printStackTrace();
    }

    /**
     * Sets highest production number
     */
    for (int prodNum : productionNumbers) {
      if (highestProdNum < prodNum) {
        highestProdNum = prodNum;
      }
    }

    /**
     * Stores number of audio products in database
     */
    int countAudio = 0;
    /**
     * Stores number of video products in database
     */
    int countVideo = 0;

    /**
     * Go through serial numbers and increment each count
     */
    for (String serialNum : serialNumbers) {
      switch (serialNum.substring(3,5)) {
        case "AU":
          countAudio++;
          break;
        case "VI":
          countVideo++;
          break;
      }
    }
    /**
     * Set the starting counts to the correct value so new entries can autoincrement
     */
    Product.setAudioCount(countAudio);
    Product.setVisualCount(countVideo);

    //populate list, retrieve all entries
    retrieveFromDb();

    // Add all entries to the listview
    for (Product product : productLineList) {
      prodList.getItems().add(product);
    }
  }

  /**
   * Add record to database when record button is pressed.
   */
  @FXML
  private void setRecordProduction(ActionEvent event) {

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/HR";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn;
    Statement stmt;

    /**
     * Product object to hold selected item
     */
    Product currentProduct = prodList.getSelectionModel().getSelectedItem();

    /**
     * List to hold all current production records
     */
    List<ProductionRecord> prodRecords = new ArrayList<>();

    /**
     * Number of records the user wishes to submit.
     */
    int numInsert = Integer.parseInt(quantity.getValue());

    /**
     * Add new record(s) to list and increment for serial number
     */
    switch (currentProduct.getType().getCode()) {
      case "AU":
        for (int i = 0; i < numInsert; i++) {
          // pre-increment count
          Product.setAudioCount(Product.getAudioCount() + 1);
          highestProdNum++;
          // add record
          ProductionRecord tempAudioRecord = new ProductionRecord(currentProduct, Product.getAudioCount());
          tempAudioRecord.setProductionNumber(highestProdNum);
          prodRecords.add(tempAudioRecord);
        }
        break;

      case "VI":
        for (int i = 0; i < numInsert; i++) {
          Product.setVisualCount(Product.getVisualCount() + 1);
          highestProdNum++;
          ProductionRecord tempVideoRecord = new ProductionRecord(currentProduct, Product.getVisualCount());
          tempVideoRecord.setProductionNumber(highestProdNum);
          prodRecords.add(tempVideoRecord);
        }
        break;

      default:
        System.out.println("Invalid code");
    }

    /**
     * Insert new records into database
     */
    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      for (ProductionRecord prodRecord : prodRecords) {
        /**
         * Setup query fields
         */
        int prodId = prodRecord.getProductId();
        String serialNum = prodRecord.getSerialNumber();
        Date date = prodRecord.getDateProduced();

        // Insert value into table
        String sql = "INSERT INTO ProductionRecord(product_id, serial_num, date_produced) VALUES (?,?,?)";

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
     /****/

  }
}
