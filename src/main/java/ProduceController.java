import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import java.util.Date;

import java.sql.*;

/**
 * Controller for Produce fxml page.
 * Sets up combobox and button reaction for produce tab.
 * Haley Yerxa
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

  @FXML
  private ListView<Product> prodList;

  ObservableList<Product> productLine = FXCollections.observableArrayList();

  /**
   * Connect to database and insert and display values.
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
        switch (rs.getString(3)) {
          case "AUDIO":
            productLine.add(new AudioPlayer(rs.getString(1), rs.getString(2), rs.getInt(4)));
            break;

          case "VIDEO":
            productLine.add(new MoviePlayer(rs.getString(1), rs.getString(2), rs.getInt(4)));
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

  public void initialize() {
    // Add 1-10 as options for combobox
    ObservableList<String> options = FXCollections
        .observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    quantity.setItems(options);
    quantity.getSelectionModel().selectFirst(); // default is first value (1)
    quantity.setEditable(true); // Allow user to enter custom values

    retrieveFromDb();
    for (Product product : productLine) {
      prodList.getItems().add(product);
    }
  }

  /**
   * Print statement when button is pressed.
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
    Product currentProduct = prodList.getSelectionModel().getSelectedItem();
    ProductionRecord prodRecord;
    switch (currentProduct.getType().getCode()) {
      case "AU":
        prodRecord = new ProductionRecord(currentProduct, Product.getAudioCount());
        break;

      case "VI":
        prodRecord = new ProductionRecord(currentProduct, Product.getVisualCount());
        break;

      default:
        prodRecord = new ProductionRecord(0);
    }

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

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
      retrieveFromDb();

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }
  }
}
