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
public class Vijand implements Runnable {
    
    private Level model;

    /**
     * De klassen zorgt ervoor dat nadat de speler naar een level gaat, er na 15 seconden een nieuwe vijand tevoorschijn komt.
     * @param m het level model
     */
    public Vijand(Level m) {
        this.model = m;
    }
    
    /**
     * De functie run is een thread die 15 seconden na een levelverhoging wordt gestart 
     * Met behulp van de functie "nieuwVijand()" van de Level model kunnen we bij elk thread interval een nieuwe vijand toevoegen
     */
    @Override
    public void run() {
        try{
            Thread.sleep(15000);
            Platform.runLater(()-> {
                this.model.nieuwVijand();
            });
        }
        catch(InterruptedException e){
            
        }
    }
    
}
