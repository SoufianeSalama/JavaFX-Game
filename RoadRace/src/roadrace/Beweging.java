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
    private boolean spelerDood;

    public Beweging(Level model, FXMLRoadRaceController controller) {
        this.model = model;
        this.controller = controller;
        this.spelerDood = model.speler.isDood();
    }
    
     
    
    @Override
    public void run() {
        while (!spelerDood){
            try{
                Thread.sleep(1000);
                    model.beweegVoorwerpen();
                Platform.runLater(()->{
                    controller.updateViews();
                });
            }
            catch(InterruptedException e){
                System.out.println("Probleem met run functie " + e.getMessage());
            }
            this.spelerDood = model.speler.isDood();
            
        }
    }
    
}
