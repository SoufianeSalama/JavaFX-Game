/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roadrace;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Soufiane
 */
public class RoadRace extends Application {
    
    @Override
    public void start(Stage mainStage) throws Exception {

        FXMLLoader lader = new FXMLLoader(getClass().getResource("FXMLStartScherm.fxml"));
        Parent root = lader.load();
                
        FXMLStartController controller = lader.getController();
        Scene resultScene = new Scene(root);
        controller.setMainStage(mainStage);
        
        mainStage.setScene(resultScene);
        mainStage.setTitle("Road Race");
        mainStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
