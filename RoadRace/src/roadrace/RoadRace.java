/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roadrace;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Soufiane
 */
public class RoadRace extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        /*Parent root = FXMLLoader.load(getClass().getResource("FXMLRoadRace.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();*/
        
        Level model = new Level();
        FXMLLoader lader = new FXMLLoader(getClass().getResource("FXMLRoadRace.fxml"));
        Parent root = lader.load();
        
        FXMLRoadRaceController controller = lader.getController();
        controller.setModel(model);
        
        Scene s = new Scene(root);
        
        stage.setScene(s);
        stage.show();
            
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
