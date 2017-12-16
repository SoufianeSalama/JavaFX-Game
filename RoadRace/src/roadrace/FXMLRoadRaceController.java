package roadrace;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.KeyEvent;

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

    @FXML
    void initialize() {
        // Knoppen
        paneel.setOnKeyPressed(this::beweegSpeler);
        paneel.setFocusTraversable(true);
    }
    
    public void setModel(Level model){
        this.model = model;
        paneel.getChildren().clear();
        
        view = new LevelView(model);
        paneel.getChildren().add(view);
    }
    
    public void beweegSpeler(KeyEvent e){
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
    
    public void updateViews(){
        //paneel.getChildren().clear();
        view.updateVWViews();
        fuelbar.setProgress(model.getBrandstof());
        damagebar.setProgress(model.getBeschadiging());
    }
    
}
