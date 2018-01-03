package roadrace;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Soufiane
 */
public class FXMLRoadRaceController {
    
    private Level model;
    private LevelView view;
    
    @FXML
    private AnchorPane paneel;

    @FXML
    private ProgressBar fuelbar;

    @FXML
    private Label afstand;

    @FXML
    private ProgressBar damagebar;

    @FXML
    private Label snelheid;

    @FXML
    private Label level;
    
    private Stage mainStage;
    private Scene resultScene;

    @FXML
    void initialize() {
        // Knoppen
        paneel.setOnKeyPressed(this::beweegSpeler);
        paneel.setFocusTraversable(true);
    }
    
    /**
     *
     * @param model
     */
    public void setModel(Level model){
        this.model = model;
        paneel.getChildren().clear();
        
        view = new LevelView(model);
        paneel.getChildren().add(view);
        
       
    }
    
    /**
     *
     * @param e
     */
    public void beweegSpeler(KeyEvent e){
        if (!model.speler.isDood() && !model.isSpelGewonnen()){
            switch (e.getCode()){
                case LEFT:
                    model.beweegSpelerLinks();
                    //updateViews();
                    break;
                case RIGHT:
                    model.beweegSpelerRechts();
                    //updateViews();
                    break;
                case UP:
                    model.beweegSpelerBoven();
                    //updateViews();
                    break;
                case DOWN:
                    model.beweegSpelerOnder();
                    //updateViews();
                    break;
            }
            this.updateViews();
        }
        else
        {
            System.out.println("Speler is dood of heeft gewonnen"); 
            startResultaat();
        }
        
    }
    
    /**
     *
     */
    public void updateViews(){
        //paneel.getChildren().clear();
        view.updateVWViews();
        
        fuelbar.setProgress(model.getBrandstof());
        damagebar.setProgress(model.getBeschadiging());
        level.setText(Integer.toString(model.getLevel()));
        snelheid.setText(Integer.toString(model.getSnelheid()));
        afstand.setText(Integer.toString(model.getTotAfstand()));
        
    }

    /**
     *
     */
    public void beweegVoorwerpen(){
        model.beweegVoorwerpen();
        view.updateVWViews();
    }
    
    /**
     *
     * @param s
     */
    public void setMainStage(Stage s){
        this.mainStage = s;
    }
   
    /**
     * De methode "startResultaat" uitgevoerd als de speler het spel wint of verliest
     * en navigeert terug naar de startpagina, waar de resultaten getoond worden -> aan de "StartController" wordt de data meegestuurd
     * M.b.v. de klasse "FadeTransistion" krijgen een mooiere overgang tussen de scene's
     */
    private void startResultaat(){
        FXMLLoader lader = new FXMLLoader(getClass().getResource("FXMLStartScherm.fxml"));
        
        try{
            Parent root = lader.load();
            
            // Fade in/out
            FadeTransition ft = new FadeTransition(Duration.millis(3000), root);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();
            
            FXMLStartController controller = lader.getController();
            controller.setEinde(model.isSpelGewonnen(), model.isDood(),model.getLevel(), model.getTotAfstand());
            
            resultScene = new Scene(root);
            mainStage.setScene(resultScene);
            mainStage.show();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("roadrace.FXMLRoadRaceController.startResultaat()");
        }
    }
    
    
    
}
