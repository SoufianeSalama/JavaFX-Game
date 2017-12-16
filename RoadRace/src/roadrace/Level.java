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
    
    private final static int levelBreedte = 22;
    private final static int levelLengte = 19;
    
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
        
        this.spelerX = 8;
        this.spelerY = 16;
        
        // breedte van paneel is 600
        // hoogte van paneel is 500
        voorwerpen = new ArrayList<>();
        
        speler = new Voorwerp(VoorwerpType.SPELER, this.spelerX, this.spelerY, true);
        voorwerpen.add(speler);

        voorwerpen.add(new Voorwerp(VoorwerpType.BRANDSTOF, 4, 16, true));
        voorwerpen.add(new Voorwerp(VoorwerpType.TEGENLIGGER, 10, 5, true));
        
        for (int a=0; a<=levelLengte; a++){
            voorwerpen.add(new Voorwerp(VoorwerpType.MUUR, 2, a, true));
            voorwerpen.add(new Voorwerp(VoorwerpType.MUUR, 21, a, true));

        }

        
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
        if ((doelX < 0) || (doelX>=levelBreedte)){
            return false;
        }
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
                    
                    return true;
                }
                else if (vw.getType()==VoorwerpType.MUUR){
                    // Beschadiging
                    System.out.println("Speler raakt muur");
                    this.beschadiging += vw.getBeschadigingAanSpeler();
                    return false;
                }
                
                else if (vw.getType()==VoorwerpType.TEGENLIGGER){
                    // Beschadiging
                    System.out.println("Speler raakt tegenligger");
                    this.beschadiging += vw.getBeschadigingAanSpeler();
                    // Verplaats tegenligger
                    vw.setTotBeschadigingVW(vw.getBeschadigingAanZichzelf());
                    vw.verplaatsBoven(1);
                    return false;
                }
            }
        }
        return true;
    }
    
    
    public Iterator<Voorwerp> getVoorwerpenLijst()
    {
        return this.voorwerpen.iterator();
    }
    
    
    // GETTERS

    public double getBrandstof() {
        return brandstof;
    }

    public double getBeschadiging() {
        return beschadiging;
    }
    
    
    
    
}
