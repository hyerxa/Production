import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    Tab productLine;
    @FXML
    Tab produce;
    @FXML
    Tab productionLog;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        FXMLLoader loader = new FXMLLoader();
        try
        {
            AnchorPane anch1 = loader.load(getClass().getResource("ProductLine.fxml"));
            productLine.setContent(anch1);
        }
        catch(IOException iex)
        {
            System.out.println("unable to load production line");
        }

        loader = new FXMLLoader();
        try{
            AnchorPane anch2 = loader.load(getClass().getResource("Produce.fxml"));
            produce.setContent(anch2);
        }
        catch(IOException iex)
        {
            System.out.println("unable to load produce");
        }

        loader = new FXMLLoader();
        try{
            AnchorPane anch3 = loader.load(getClass().getResource("ProductionLog.fxml"));
            productionLog.setContent(anch3);
        }
        catch(IOException iex)
        {
            System.out.println("unable to load production log");
        }

    }

}
