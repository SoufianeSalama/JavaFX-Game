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
     *
     * @param m
     */
    public Vijand(Level m) {
        this.model = m;
    }
    
    
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
