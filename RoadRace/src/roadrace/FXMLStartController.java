package roadrace;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FXMLStartController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnSpeel;

    @FXML
    private TextArea txtScore;
    
    private Stage mainStage;
    private Scene gameScene;
    
    // Te tonen resulaten
    private int behaaldeAfstand, behaaldeLevel;

    @FXML
    void initialize() {
        //txtScore.setText("Hou de straten veilig en versla alle vijanden!");
        
        btnSpeel.setOnAction(event -> {
            this.startSpel();
        });

    }
    
    private void startSpel(){
        Level model = new Level();
        FXMLLoader lader = new FXMLLoader(getClass().getResource("FXMLRoadRace.fxml"));
        try{
            Parent root = lader.load();
            
            // Fade in/out
            FadeTransition ft = new FadeTransition(Duration.millis(3000), root);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();
            
            FXMLRoadRaceController controller = lader.getController();
            controller.setModel(model);
            controller.setMainStage(this.mainStage);
            
            Beweging b = new Beweging(model, controller);
            Thread t = new Thread(b);
            t.setDaemon(true);
//            t.start();

            gameScene = new Scene(root);
            mainStage.setScene(gameScene);
            mainStage.show();

        }
        catch(Exception e){
            System.out.println("roadrace.FXMLStartController.startSpel()");     
        }
        
    }
    
    public void setStage(Stage s){
        this.mainStage = s;
    }
    
    public void setEinde(boolean isDood, int level, int afstand){
        this.behaaldeLevel = level;
        this.behaaldeAfstand = afstand;
        if (isDood){
            txtScore.setText(
                "VERLOREN" + "\n" + 
                "Behaalde Level: " + Integer.toString(behaaldeLevel) + "\n" +
                "Behaalde Afstand " + Integer.toString(behaaldeAfstand)
            );
        }
        else{
            txtScore.setText(
                "GEWONNEN" + "\n" + 
                "Behaalde Level: " + Integer.toString(behaaldeLevel) + "\n" +
                "Behaalde Afstand " + Integer.toString(behaaldeAfstand)
            );
        }
            
        
        
    }
}

