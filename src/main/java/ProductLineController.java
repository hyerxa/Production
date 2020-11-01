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

/**
 * Controller for Product Line fxml page.
 * Connects to database to insert new products and display current products.
 * Haley Yerxa
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
   * Method to add new product when button is pressed.
   *
   * @param event the action of pressing the button.
   */
  @FXML
  private void addProduct(ActionEvent event) {
    insertToDb();
  }

  ObservableList<Product> productLine = FXCollections.observableArrayList();

  /**
   * Connect to database and insert and display values.
   */
  public void insertToDb() {
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

      String prodName = productName.getText();
      String man = manufacturer.getText();
      String type = itemType.getValue();

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
        productTable.setItems(productLine);
      }

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    }
    catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }
  }

  public void setUpProductLineTable() {
    productNameCol.setCellValueFactory(new PropertyValueFactory("Name"));
    manufacturerCol.setCellValueFactory(new PropertyValueFactory("Manufacturer"));
    itemTypeCol.setCellValueFactory(new PropertyValueFactory("Type"));

  }

  public void initialize() {
    setUpProductLineTable();
    retrieveFromDb();
  }

}
