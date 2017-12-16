/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roadrace;

/**
 *
 * @author Soufiane
 */
public class Voorwerp {
    
    private VoorwerpType type;
    private int voorwerpX, voorwerpY;
    private int lengteVW, breedteVW;
    private boolean richting; 
    private boolean dood;
    
    // BESCHADEGING 
    private double totBeschadigingVW;
    private double beschadigingAanZichzelf;
    private double beschadigingAanSpeler;
    
    // DUWBAAR/NEEMBAAR
    private boolean duwbaar, neembaar;
    
    

    public Voorwerp(VoorwerpType type, int x, int y, boolean richting) {
        this.type = type;
        this.voorwerpX = x;
        this.voorwerpY = y;
        this.richting = richting;
        
        this.dood = false;
        this.totBeschadigingVW = 0.00;
        
        this.setVWParameters();
    }
    
    private void setVWParameters(){
        switch (this.type){
            case SPELER:
                this.beschadigingAanSpeler = 0.0;
                this.beschadigingAanZichzelf = 0.0;
                this.duwbaar = false;
                this.neembaar = false;
                this.lengteVW = 1;
                this.breedteVW = 1;
                break;
            
            case TEGENLIGGER:
                this.beschadigingAanSpeler = 0.2;
                this.beschadigingAanZichzelf = 0.3;
                this.duwbaar = true;
                this.neembaar = false;
                this.lengteVW = 1;
                this.breedteVW = 1;
                break;
                
            case BRANDSTOF:
                this.beschadigingAanSpeler = 0.0;
                this.beschadigingAanZichzelf = 0.0;
                this.duwbaar = false;
                this.neembaar = true;
                this.lengteVW = 1;
                this.breedteVW = 1;
                break;
                
            case MUUR:
                this.beschadigingAanSpeler = 0.1;
                this.beschadigingAanZichzelf = 0.0;
                this.duwbaar = false;
                this.neembaar = false;
                this.lengteVW = 1;
                this.breedteVW = 1;
                break;   
        }
    }

    public int getVoorwerpX() {
        return voorwerpX;
    }

    public int getVoorwerpY() {
        return voorwerpY;
    }

    public VoorwerpType getType() {
        return type;
    }

    public double getBeschadigingAanSpeler() {
        return beschadigingAanSpeler;
    }

    public double getBeschadigingAanZichzelf() {
        return beschadigingAanZichzelf;
    }

    public double getTotBeschadigingVW() {
        return totBeschadigingVW;
    }

    public boolean isDood() {
        return dood;
    }

    public boolean isDuwbaar() {
        return duwbaar;
    }

    public boolean isNeembaar() {
        return neembaar;
    }
    
    public int getLengteVW(){
       return this.lengteVW; 
    }
    
    public int getBreedteVW(){
        return this.breedteVW;
    }
    // Setters
    
    public void setDood(boolean dood) {
        this.dood = dood;
    }

    public void setTotBeschadigingVW(double totBeschadigingVW) {
        this.totBeschadigingVW = totBeschadigingVW;
    }
    
    
    public void verplaatsLinks(int dx){
        this.voorwerpX -=dx;
    }
    
    public void verplaatsRechts(int dx){
        this.voorwerpX +=dx;
    }
    
    public void verplaatsOnder(int dy){
        this.voorwerpY +=dy;
    }
    
    public void verplaatsBoven(int dy){
        this.voorwerpY -=dy;
    }
    
    
    
    
    public boolean isOp(int doelX, int doelY){
        System.out.println(this.type+" Voorwerp X: " + this.voorwerpX);
        System.out.println(this.type+" Voorwerp Y: " + this.voorwerpY );
        if ((this.voorwerpX == doelX && this.voorwerpY==doelY)){
                return true;
        }
        return false;
        
    }
    
    
    
    
}
