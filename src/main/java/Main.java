import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class for production project.
 * Starts Javafx application.
 * Haley Yerxa
 */
public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Start Javafx Application.
   *
   * @param primaryStage is stage for application window.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

    Scene scene = new Scene(root);

    primaryStage.setTitle("Haley Yerxa Production");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
