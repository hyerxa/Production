
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Controller for Production Log fxml page.
 * Controls Production Log output and adding new production
 * records to database.
 *
 * @author Haley Yerxa
 */
public class ProductionLogController {

  /**
   * Text area for production log output.
   */
  @FXML
  private TextArea txtProductionLog;

  /**
   * Adds new record to production log.
   *
   * @param text text to be added to current text.
   */
  public void setTaText(String text) {
    txtProductionLog.appendText(text);
  }

  /**
   * Connect to database and display production log.
   */
  public void initialize() {

    Connection conn;
    Statement stmt;

    /*
      String Builder to hold output that will be placed in text area
     */
    StringBuilder textAreaOutput = new StringBuilder();

    try {
      // STEP 1: Register JDBC driver
      Class.forName(Controller.JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(
          Controller.DB_URL, Controller.USER, Controller.PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      // Retrieve all entries in table
      String sql2 =
          "SELECT production_num, product_id, serial_num, date_produced FROM ProductionRecord";

      ResultSet rs = stmt.executeQuery(sql2);

      while (rs.next()) {
        //  Create new productionRecord object for entry in database
        ProductionRecord productionRecord = new ProductionRecord(rs.getInt(1), rs.getInt(2),
            rs.getString(3), rs.getDate(4));
        //  Add information to temporary StringBuilder
        textAreaOutput.append(productionRecord.toString()).append("\n");
      }

      // Set initial value of text area to all entries in database
      txtProductionLog.setText(textAreaOutput.toString());

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

}
