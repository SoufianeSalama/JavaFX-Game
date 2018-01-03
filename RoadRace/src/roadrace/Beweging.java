/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roadrace;

import javafx.application.Platform;

/**
 *
 * @author Soufiane
 */
public class Beweging implements Runnable {
    
    private Level model;
    private FXMLRoadRaceController controller;

    /**
     *
     * @param model
     * @param controller
     */
    public Beweging(Level model, FXMLRoadRaceController controller) {
        this.model = model;
        this.controller = controller;
    }
    
     
    
    @Override
    public void run() {
        while (!model.speler.isDood()){
            try{
                Thread.sleep(model.getBewegingsInterval());
                
                Platform.runLater(()->{
                    controller.beweegVoorwerpen();
                    controller.updateViews();
                });
            }
            catch(InterruptedException e){
                System.out.println("Probleem met run functie " + e.getMessage());
            }
        }
    }
    
}
