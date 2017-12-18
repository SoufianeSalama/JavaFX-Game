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
    
    // BESCHADIGING 
    private double totBeschadigingVW;
    private double beschadigingAanZichzelf;
    private double beschadigingAanSpeler;
    
    // DUWBAAR/NEEMBAAR
    private boolean duwbaar, neembaar;
    
    // VIJAND
    private boolean vijand;
    
    // BEWEGING (VALT)
    private boolean beweegt = false;
    
    

    public Voorwerp(VoorwerpType type, int x, int y, boolean richting, boolean vijand) {
        this.type = type;
        this.voorwerpX = x;
        this.voorwerpY = y;
        this.richting = richting;
        this.vijand = vijand;
        
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
                this.lengteVW = 3;
                this.breedteVW = 2;
                break;
            
            case VOERTUIG:
                this.beschadigingAanSpeler = 0.2;
                this.beschadigingAanZichzelf = 0.3;
                this.duwbaar = true;
                this.neembaar = false;
                this.lengteVW = 2;
                this.breedteVW = 2;
                this.beweegt = true;
                break;
                
            case BRANDSTOF:
                this.beschadigingAanSpeler = 0.0;
                this.beschadigingAanZichzelf = 0.0;
                this.duwbaar = false;
                this.neembaar = true;
                this.lengteVW = 2;
                this.breedteVW = 2;
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

    public boolean isVijand() {
        return vijand;
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
        
        this.totBeschadigingVW += totBeschadigingVW;
        if (this.totBeschadigingVW>=1.00){
            this.dood = true;
        }
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
//        System.out.println("DoelX: "+ doelX);
//        System.out.println("DoelY: "+ doelY);
//        
//        System.out.println(this.type+" Voorwerp X: " + this.voorwerpX + " breedte = " + this.breedteVW );
//        System.out.println(this.type+" Voorwerp Y: " + this.voorwerpY + " lengte = " + this.lengteVW);
//        if (this.voorwerpX == doelX ){
//            if (this.voorwerpY == doelY){
//                return true;
//            }
//        }
//        return false;
        
         if (this.voorwerpX == doelX || this.voorwerpX+(this.breedteVW/2) ==doelX || this.voorwerpX-(this.breedteVW/2) ==doelX ){
             
             if (this.voorwerpY == doelY || this.voorwerpY-(this.lengteVW/2) ==doelY || this.voorwerpY+(this.lengteVW/2) ==doelY){
                return true;
            }
         }
        
       return false;
    }
    
    
    
    
}
