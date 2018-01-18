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
        paneel.setOnKeyPressed(this::beweegSpeler);
        paneel.setFocusTraversable(true);
    }
    
    /**
     * De (setter) methode "setModel()" is nodig zodat deze controller over de Level model beschikt 
     * en om een nieuwe View object aan te maken
     * @param model het level model
     */
    public void setModel(Level model){
        this.model = model;
        paneel.getChildren().clear();
        
        view = new LevelView(model);
        paneel.getChildren().add(view);
    }
    
    /**
     * De functie "beweegSpeler()" wordt uitgevoerd nadat een gebruiker op de toets klikt
     * Afhankelijk van welke toets die meegestuurd wordt met de parameter "e" kunnen we de speler gaan bewegen
     * Dit wordt enkel gedaan zoals de speler niet dood is en niet heeft gewonnen
     * @param e
     */
    public void beweegSpeler(KeyEvent e){
        if (!model.speler.isDood() && !model.isSpelGewonnen()){
            switch (e.getCode()){
                case LEFT:
                    model.beweegSpelerLinks();
                    break;
                case RIGHT:
                    model.beweegSpelerRechts();
                    break;
                case UP:
                    model.beweegSpelerBoven();
                    break;
                case DOWN:
                    model.beweegSpelerOnder();
                    break;
            }
            this.updateViews();
        }
        else
        {
            System.out.println("Speler is dood of heeft gewonnen"); 
            this.model.stopMedia();
            startResultaat();
        }
        
    }
    
    /**
     *  De functie "updateViews()" gaat nadat een gebruiker de speler heeft laten bewegen de view updaten 
     *  en ook de gegevens van de speler zoals brandstof, snelheid, beschadiging, ...
     */
    public void updateViews(){
        view.updateVWViews();
        fuelbar.setProgress(model.getBrandstof());
        damagebar.setProgress(model.getBeschadiging());
        level.setText(Integer.toString(model.getLevel()));
        snelheid.setText(Integer.toString(model.getSnelheid()));
        afstand.setText(Integer.toString(model.getTotAfstand()));
    }

    /**
     *  De functie "beweegVoorwerpen()" wordt uitgevoerd door de thread "Beweging" die ervoor zorgt dat de voorwerpen bewegen
     *  Hij gaat eerst in het "Level" model de voorwerpen laten vallen en mbv een random nieuwe voorwerpen toevoegen
     *  Vervolgens gaat hij in de view de voorwerpen updaten
     */
    public void beweegVoorwerpen(){
        model.beweegVoorwerpen();
        view.updateVWViews();
    }
    
    /**
     * De (setter) methode "setMainStage()" is nodig zodat deze controller de spel Scene in (main)Stage kan plaatsen
     * @param s bevat de (main)Stage
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
            controller.setMainStage(mainStage);
            controller.setEinde(model.isDood(),model.getLevel(), model.getTotAfstand());
            
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
