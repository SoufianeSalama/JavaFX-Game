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
    
    private final static int levelBreedte = 23;
    private final static int levelLengte = 19;
    
    private ArrayList<Voorwerp> voorwerpen;
    
    private int spelerX, spelerY;
    private int level, snelheid, totAfstand;
    
    private double brandstof, beschadiging;
    
    public Voorwerp speler;
    
    private int snelheidBeweging, afstandTick;
    
            
    public Level() {
        init();
    }
    
    public void init(){
        this.level = 1;
        this.setLevelParam();
        
        this.spelerX = 8;
        this.spelerY = 16;
        
        
        voorwerpen = new ArrayList<>();
        
        // Muur opbouwen
        for (int a=0; a<=levelLengte; a++){
            voorwerpen.add(new Voorwerp(VoorwerpType.MUUR, 2, a, true, false));
            voorwerpen.add(new Voorwerp(VoorwerpType.MUUR, 20, a, true, false));
        }
        
        speler = new Voorwerp(VoorwerpType.SPELER, this.spelerX, this.spelerY, true, false);
        voorwerpen.add(speler);
        voorwerpen.add(new Voorwerp(VoorwerpType.BRANDSTOF, 4, 16, true, false));
        voorwerpen.add(new Voorwerp(VoorwerpType.VOERTUIG, 4, 8, true, false));
        
        voorwerpen.add(new Voorwerp(VoorwerpType.VOERTUIG, 10, 10, true, true));
        
        

        
    }
    
    public void beweegSpelerLinks(){
        int dx = 1;
        int doelX = speler.getVoorwerpX() - dx ;
        int doelY = speler.getVoorwerpY();
        
        if (controleerVerplaatsing(doelX, doelY)){
            speler.verplaatsLinks(dx);
            this.spelerX = speler.getVoorwerpX();
            this.spelerY = speler.getVoorwerpY();
        }
    }
    
    public void beweegSpelerRechts(){
        int dx = 1;
        int doelX = speler.getVoorwerpX() + dx ;
        int doelY = speler.getVoorwerpY();
        
        if (controleerVerplaatsing(doelX, doelY)){
            speler.verplaatsRechts(dx);
            this.spelerX = speler.getVoorwerpX();
            this.spelerY = speler.getVoorwerpY();
        }
    }
    
    public void beweegSpelerBoven(){
        int dy = 1;
        int doelX = speler.getVoorwerpX();
        int doelY = speler.getVoorwerpY() - dy;
        
        if (controleerVerplaatsing(doelX, doelY)){
            speler.verplaatsBoven(dy);
            this.spelerX = speler.getVoorwerpX();
            this.spelerY = speler.getVoorwerpY();
        }
    }
    
    public void beweegSpelerOnder(){
        int dy = 1;
        int doelX = speler.getVoorwerpX();
        int doelY = speler.getVoorwerpY() + dy;
        
        if (controleerVerplaatsing(doelX, doelY)){
            speler.verplaatsOnder(dy);
            this.spelerX = speler.getVoorwerpX();
            this.spelerY = speler.getVoorwerpY();
        }
        
    }
    
    private boolean controleerVerplaatsing(int doelX, int doelY){
        
        // Grens: linker- en rechterzijde
        /*if ((doelX < 0) || (doelX>levelBreedte)){
            return false;
        }*/
        // Grens: boven- en onderzijde
        if ((doelY < 2) || (doelY>levelLengte-2)){
            return false;
        }
        // Controle of er al een voorwerp op het doelcoordinaat staat
        for (Voorwerp vw: voorwerpen){
            if (vw.isOp(doelX,doelY)){
                
                
                if (vw.getType()==VoorwerpType.BRANDSTOF){
                    System.out.println("Speler raakt brandstof");
                    this.brandstof = 1.00;
                    voorwerpen.remove(vw);
                    // Verwijder voorwerp brandstof
                    //vw.setDood(true);
                    
                    return true;
                }
                else if (vw.getType()==VoorwerpType.MUUR){
                    // Beschadiging
                    System.out.println("Speler raakt muur");
                    this.speler.setTotBeschadigingVW(vw.getBeschadigingAanSpeler());
                    this.beschadiging = this.speler.getTotBeschadigingVW();
                    
                    return false;
                }
                
                else if (vw.getType()==VoorwerpType.VOERTUIG){
                     // Beschadiging
                    System.out.println("Speler raakt voertuig");
                    this.speler.setTotBeschadigingVW(vw.getBeschadigingAanSpeler());
                   
                    this.beschadiging = this.speler.getTotBeschadigingVW();
                    // Verplaats tegenligger
                    System.out.println("Totale beschadiging tegenligger: "  + vw.getTotBeschadigingVW());
                    vw.setTotBeschadigingVW(vw.getBeschadigingAanZichzelf());
                    
                    
                    if (vw.isVijand() && vw.isDood()){
                        // Speler raakt vijand en is dood
                        // Level verhogen
                        
                    }
                    
                    // Berekenen hoe een voertuig wordt geduwd na een botsing
                    vw.verplaatsBoven(1);
                    
                    return false;
                }
            }
        }
        return true;
    }
    
    private void verhoogLevel(){
        if (this.level<5){
            this.level++;
            this.setLevelParam();
        }
        else{
            // Speler heeft gewonnen
        }
    }
    
    private void setLevelParam(){
        switch (this.level){
            case (1):
                this.snelheid = 50;
                this.snelheidBeweging = 800;
                this.brandstof = 1;
                break;
            case (2):
                this.snelheid = 75;
                this.snelheidBeweging = 600;
                break;
              
            case (3):
                this.snelheid = 100;
                this.snelheidBeweging = 400;
                break;
            case (4):
                this.snelheid = 125;
                this.snelheidBeweging = 300;
                break;
            case (5):
                this.snelheid = 150;
                this.snelheidBeweging = 200;
                break;
            
        }
        this.afstandTick = this.snelheidBeweging/this.snelheid;
    }
    
   
    
    
     // Beweging thread (Voorwerpen vallen)
    public void beweegVoorwerpen(){
        System.out.println("Beweeg Voorwerpen");
        int doelX, doelY;
        
        for (Voorwerp vw: voorwerpen){
            doelX = vw.getVoorwerpX();
            doelY = vw.getVoorwerpY()+1;
            
            
             // enkel gewone voertuigen en brandstof mogen bewegen (vallen) worden
            if (vw.getType() == VoorwerpType.VOERTUIG){
                
                if (vw.isOp(doelX,doelY)){
                
                // Beschadiging aan speler/Vijand -> er valt een voertuig op de speler...
                    if (vw.getType()==VoorwerpType.SPELER){
                        // Beschadiging
                        System.out.println("Speler raakt voertuig");
                        this.speler.setTotBeschadigingVW(vw.getBeschadigingAanSpeler());

                        this.beschadiging = this.speler.getTotBeschadigingVW();
                        // Verplaats tegenligger
                        System.out.println("Totale beschadiging tegenligger: "  + vw.getTotBeschadigingVW());
                        vw.setTotBeschadigingVW(vw.getBeschadigingAanZichzelf());

                        vw.verplaatsBoven(1);
                    }
                }
                else{
                     vw.verplaatsOnder(1);
                }
               
            }
            
        }
        
        // Afstand verhogen
        this.totAfstand += this.afstandTick;
        // Brandstof verlagen
        if (this.brandstof>=0){
             this.brandstof -=0.02;
        }
       
    }
    
    
    // GETTERS

    public double getBrandstof() {
        return brandstof;
    }

    public double getBeschadiging() {
        return beschadiging;
    }

    public int getSnelheid() {
        return snelheid;
    }

    public int getLevel() {
        return level;
    }

    public int getTotAfstand() {
        return totAfstand;
    }
    
    public Iterator<Voorwerp> getVoorwerpenLijst()
    {
        return this.voorwerpen.iterator();
    }
    
   
    
    
    
    
}
