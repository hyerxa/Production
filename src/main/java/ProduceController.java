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

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/HR";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn;
    Statement stmt;
    List<String> serialNumbers = new ArrayList<>();

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);
      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      //STEP 3: Execute a query
      stmt = conn.createStatement();

      String sql = "SELECT serial_num FROM ProductionRecord";
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        serialNumbers.add(rs.getString(1));
      }

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

    int countAudio = 0;
    int countVideo = 0;
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
    Product.setAudioCount(countAudio);
    Product.setVisualCount(countVideo);

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
    FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductionLog.fxml"));
    try {
      loader.load();
    } catch (IOException iex) {
      System.out.println("unable to load production log");
    }

    ProductionLogController productionLogController = loader.getController();

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/HR";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn;
    Statement stmt;

    Product currentProduct = prodList.getSelectionModel().getSelectedItem();
    List<ProductionRecord> prodRecords = new ArrayList<>();

    int numInsert = Integer.parseInt(quantity.getValue());
    switch (currentProduct.getType().getCode()) {
      case "AU":
        for (int i = 0; i < numInsert; i++) {
          Product.setAudioCount(Product.getAudioCount() + 1);
          prodRecords.add(new ProductionRecord(currentProduct, Product.getAudioCount()));
        }
        break;

      case "VI":
        for (int i = 0; i < numInsert; i++) {
          Product.setVisualCount(Product.getVisualCount() + 1);
          prodRecords.add(new ProductionRecord(currentProduct, Product.getVisualCount()));
        }
        break;

      default:
        for (int i = 0; i < numInsert; i++) {
          prodRecords.add(new ProductionRecord(0));
        }
    }

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      for (ProductionRecord prodRecord : prodRecords) {
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
      }

      retrieveFromDb();
      productionLogController.getText();

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }
  }
}
