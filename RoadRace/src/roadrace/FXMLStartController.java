package roadrace;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class FXMLStartController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnSpeel;

    @FXML
    private TextArea txtScore;

    @FXML
    void initialize() {
        btnSpeel.setOnAction(event -> {
            this.startSpel();
        });

    }
    
    private void startSpel(){
        Level model = new Level();
        FXMLLoader lader = new FXMLLoader(getClass().getResource("FXMLRoadRace.fxml"));
        Stage stage = new Stage();
        try{
            Parent root = lader.load();
            FXMLRoadRaceController controller = lader.getController();
            controller.setModel(model);
        
            Beweging b = new Beweging(model, controller);
            Thread t = new Thread(b);
            t.setDaemon(true);
            t.start();

            Scene s = new Scene(root);
            stage.setScene(s);
            stage.show();
            


        }
        catch(Exception e){
            System.out.println("roadrace.FXMLStartController.startSpel()");     
        }
        
        
    }
}

