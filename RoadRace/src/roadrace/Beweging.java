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
     * De beweging klasse zorgt voor een thread die de voorwerp gaat laten bewegen
     * @param model
     * @param controller
     */
    public Beweging(Level model, FXMLRoadRaceController controller) {
        this.model = model;
        this.controller = controller;
    }
    
     
    /**
     * De functie run is een thread die bij elke level wordt versneld en die de voorwerpen gaat laten bewegen zolang de speler in leven is.
     * Met behulp van de functie "beweegVoorwerpen()" en "updateViews()" van de RoadRaceController kunnen we bij elk thread interval 
     * een nieuwe voorwerpen aanmaken en op de view laten bewegen
     */
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
