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

/**
 *
 * @author Soufiane
 */
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
    
    private Parent root;

    @FXML
    void initialize() {
        btnSpeel.setOnAction(event -> {
            this.startSpel();
        });

    }
    
    /**
     * De methode "startSpel()" wordt uitgevoerd nadat een gebruikers het spel start door op de button "START" te klikken
     * Deze gaat eerst een nieuw level model aanmaken en maakt de RoadRaceController aan
     * Ook wordt de beweging thread gestart, deze zordt ervoor dat de voorwerpen kunnen bewegen(als het ware vallen)
     * Hierna wordt er naar de spel scene getoond door de scene in de (main)Stage te plaaten
     * M.b.v. de klasse "FadeTransistion" krijgen een mooiere overgang tussen de scene's
     */
    private void startSpel(){
        Level model = new Level();
        
        try{
            FXMLLoader lader = new FXMLLoader(getClass().getResource("FXMLRoadRace.fxml"));
            this.root = lader.load();
            
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
            t.start();

            gameScene = new Scene(root);
            
            mainStage.setScene(gameScene);
            
            mainStage.show();

        }
        catch(Exception e){
            System.out.println("roadrace.FXMLStartController.startSpel()");    
            System.out.println(e.getLocalizedMessage());
        }
        
    }
    
    /**
     * De (setter) methode "setMainStage()" is nodig zodat deze controller de spel Scene in (main)Stage kan plaatsen
     * @param s bevat de (main)Stage
     */
    public void setMainStage(Stage s){
        this.mainStage = s;
    }
    
    /**
     * De methode "setEinde()" wordt uitgevoerd door RoadRaceController nadat de speler heeft gewonnen of verloren.
     * Deze gaat afhankelijk van de meegestuurde parameters de resultaten van de speler weergeven in het tekstvak
     * @param isDood is de speler dood (true/false)
     * @param level tot welk level is de speler geraak (1,2 of 3)
     * @param afstand tot hoeveel m is de speler geraakt(int)
     */
    public void setEinde(boolean isDood, int level, int afstand){
        this.behaaldeLevel = level;
        this.behaaldeAfstand = afstand;
        if (isDood){
            txtScore.setText(
                "VERLOREN" + "\n" + 
                "Behaald Level: " + Integer.toString(behaaldeLevel) + "\n" +
                "Behaalde Afstand: " + Integer.toString(behaaldeAfstand) +"m"
            );
        }
        else{
            txtScore.setText(
                "GEWONNEN" + "\n" + 
                "Behaald Level: " + Integer.toString(behaaldeLevel) + "\n" +
                "Behaalde Afstand: " + Integer.toString(behaaldeAfstand) + "m"
            );
        }
            
        
        
    }
}

