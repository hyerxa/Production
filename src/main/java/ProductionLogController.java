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
public class ProductionLogController implements Initializable {

    @FXML
    private TextArea productionLog;

    /**
     * Connect to database and display production log.
     */
    public void getText() {
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
            String sql2 = "SELECT name, manufacturer, type, ID FROM Product";

            ResultSet rs = stmt.executeQuery(sql2);

            while (rs.next()) {
                ProductionRecord productionRecord;
                switch(rs.getString(3)) {
                    case "AUDIO":
                        AudioPlayer audioProduct = new AudioPlayer(rs.getString(1), rs.getString(2), rs.getInt(4));
                        Product.setAudioCount(Product.getAudioCount()+1);
                        productionRecord = new ProductionRecord(audioProduct, Product.getAudioCount());
                        break;
                    case "VIDEO":
                        MoviePlayer videoProduct = new MoviePlayer(rs.getString(1), rs.getString(2), rs.getInt(4));
                        Product.setVisualCount(Product.getVisualCount()+1);
                        productionRecord = new ProductionRecord(videoProduct, Product.getVisualCount());
                        break;
                    default:
                        productionRecord = new ProductionRecord(rs.getInt(4));
                }

                textAreaOutput.append(productionRecord.toString() + "\n");
            }

            productionLog.setText(textAreaOutput.toString());

            // STEP 4: Clean-up environment
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();

        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        getText();
    }

}
