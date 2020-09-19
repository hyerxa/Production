import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;

public class ProductLineController {

    @FXML
    Button addProduct;

    @FXML
    private TextField productName;

    @FXML
    private ChoiceBox<String> itemType;

    @FXML
    private TextField manufacturer;

    @FXML
    private TableView<?> productTable;

    @FXML
    private TableColumn<?, ?> productNameCol;

    @FXML
    private TableColumn<?, ?> manufacturerCol;

    @FXML
    private TableColumn<?, ?> itemTypeCol;

    @FXML
    private void addProduct(ActionEvent event) {
        connectToDb();
    }

    public void initialize() {
    }

    public void connectToDb() {
        final String JDBC_DRIVER = "org.h2.Driver";
        final String DB_URL = "jdbc:h2:./res/HR";

        //  Database credentials
        final String USER = "";
        final String PASS = "";
        Connection conn = null;
        Statement stmt = null;

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

            String sql = "INSERT INTO Product(type, manufacturer, name) VALUES (?,?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, type);
            preparedStatement.setString(2, man);
            preparedStatement.setString(3, prodName);

            preparedStatement.executeUpdate();

            String sql2 = "SELECT name, manufacturer, type FROM Product";

            ResultSet rs = stmt.executeQuery(sql2);

            while (rs.next()) {
                System.out.println("Name: " + rs.getString(1) + ", Manufacturer: " + rs.getString(2) + ", Type: " + rs.getString(3));
            }

            // STEP 4: Clean-up environment
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
