/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roadrace;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Soufiane
 */
public class Level {
    
    private ArrayList<Voorwerp> voorwerpen;
    
    private int spelerX, spelerY;
    private int level, snelheid, totAfstand;
    
    private double brandstof, beschadiging;
    
    private Voorwerp speler;
    
            
    public Level() {
        init();
    }
    
    public void init(){
        this.level = 1;
        this.totAfstand = 0;
        this.snelheid = 50;
        
        this.spelerX = 50;
        this.spelerY = 80;
        
        // breedte van paneel is 600
        // hoogte van paneel is 500
        voorwerpen = new ArrayList<>();
        
        //voorwerpen.add(new Voorwerp(VoorwerpType.SPELER, 4, 0, true));
        
        speler = new Voorwerp(VoorwerpType.SPELER, this.spelerX, this.spelerY, true);;
        
        voorwerpen.add(speler);
    }
    
    public void beweegSpelerLinks(){
        
        int doelX = speler.getVoorwerpX() - 1 ;
        int doelY = speler.getVoorwerpY();
        
        /*if (isVrij(doelX, doelY)){
            speler.verplaatsLinks();
        }*/
        speler.verplaatsLinks();
    }
    
    public void beweegSpelerRechts(){
        int doelX = speler.getVoorwerpX() + 1 ;
        int doelY = speler.getVoorwerpY();
        
        /*if (isVrij(doelX, doelY)){
            speler.verplaatsLinks();
        }*/
        
        speler.verplaatsRechts();
    }
    
    private void isVrij(int doelX, int doelY){
        
    }
    
    
    public Iterator<Voorwerp> getVoorwerpenLijst()
    {
        return this.voorwerpen.iterator();
    }
}
