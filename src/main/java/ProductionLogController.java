import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * Controller for Production Log fxml page.
 * Will control production log file once functionality is added.
 * Haley Yerxa
 */
public class ProductionLogController {

    @FXML
    private TextArea txtProductionLog;

    /**
     * Connect to database and display production log.
     */
    public void setText(String text) {
        txtProductionLog.appendText(text);
    }

    public String getText() {
        return txtProductionLog.getText();
    }

    public void start() {
        System.out.println("in start");
        populateTextArea();
    }

    public void populateTextArea() {
        System.out.println("populate text area");
        final String JDBC_DRIVER = "org.h2.Driver";
        final String DB_URL = "jdbc:h2:./res/HR";

        //  Database credentials
        final String USER = "";
        final String PASS = "";
        Connection conn;
        Statement stmt;
        StringBuilder textAreaOutput = new StringBuilder();

        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 3: Execute a query
            stmt = conn.createStatement();

            // Retrieve all entries in table
            String sql2 = "SELECT production_num, product_id, serial_num, date_produced FROM ProductionRecord";

            ResultSet rs = stmt.executeQuery(sql2);

            while (rs.next()) {
                ProductionRecord productionRecord = new ProductionRecord(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDate(4));
                textAreaOutput.append(productionRecord.toString() + "\n");
            }

            txtProductionLog.setText(textAreaOutput.toString());

            // STEP 4: Clean-up environment
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
