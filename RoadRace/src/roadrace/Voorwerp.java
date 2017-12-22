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
    //private double beschadigingAanZichzelf;
    private double beschadigingAanAnderen;
    
    // DUWBAAR/NEEMBAAR
    private boolean duwbaar, neembaar;
    
    // VIJAND
    private boolean vijand;
    
    // BEWEGING (VALT)
    private boolean beweegt = false;
    
    // VERSCHILLENDE VOERTUIGEN
    private VoertuigType voertuigType;
    
    

    public Voorwerp(VoorwerpType type, int x, int y) {
        this.type = type;
        this.voorwerpX = x;
        this.voorwerpY = y;
        this.vijand = vijand;
        
        this.dood = false;
        this.totBeschadigingVW = 0.00;
        
        this.voertuigType = null;
        
        this.setVWParameters();
    }
    
    public Voorwerp(VoorwerpType type, int x, int y, VoertuigType vt, boolean vijand) {
        this.type = type;
        this.voorwerpX = x;
        this.voorwerpY = y;
        this.vijand = vijand;
        
        this.dood = false;
        this.totBeschadigingVW = 0.00;
        
        this.voertuigType = vt;
        this.setVWParameters();
    }
    
    private void setVWParameters(){
        switch (this.type){
            case SPELER:
                this.beschadigingAanAnderen = 0.2;
                //this.beschadigingAanZichzelf = 0.0;
                this.duwbaar = false;
                this.neembaar = false;
                this.lengteVW = 4;
                this.breedteVW = 3;
                break;
            
            case VOERTUIG:
                
                switch (this.voertuigType){
                    case AUTO:
                        this.beschadigingAanAnderen = 0.1;
                        this.lengteVW = 4;
                        this.breedteVW = 3;
                        break;
                    
                    case TRUCK:
                        this.beschadigingAanAnderen = 0.3;
                        this.lengteVW = 10;
                        this.breedteVW = 4;
                        break;
                        
                    case MOTOR:
                        this.beschadigingAanAnderen = 0.05;
                        this.lengteVW = 4;
                        this.breedteVW = 2;
                        break;                        
                }
                this.neembaar = false;
                this.duwbaar = true;
                this.beweegt = true;
                break;
                
            case BRANDSTOF:
                this.beschadigingAanAnderen = 0.0;
                //this.beschadigingAanZichzelf = 0.0;
                this.duwbaar = false;
                this.neembaar = true;
                this.lengteVW = 2;
                this.breedteVW = 2;
                break;
                
            case MUUR:
                this.beschadigingAanAnderen = 0.15;
                //this.beschadigingAanZichzelf = 0.0;
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

    public VoertuigType getVoertuigType() {
        return voertuigType;
    }
    
    public double getBeschadigingAanAnderen() {
        return beschadigingAanAnderen;
    }

//    public double getBeschadigingAanZichzelf() {
//        return beschadigingAanZichzelf;
//    }

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

    public void setVijand(boolean vijand) {
        this.vijand = vijand;
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
    
    
    
    /**
     * 
     * De methode "isOp()" controlleert of een voorwerp zich bevindt op de doelcoordinaten van de speler of van een gebotst voertuig.
     * Deze methode wordt voor alle voorwerpen uitgevoerd
     * 
     * @param doelX
     * @param doelY
     * @return 
     */
    public boolean isOp(Voorwerp actie, int doelX, int doelY){

        //if (this.voorwerpX == doelX || (doelX + actie.breedteVW-1 == this.voorwerpX) || (this.voorwerpX + this.breedteVW-1 == doelX ) ){
        if ( ((doelX>=this.voorwerpX) && (doelX<= this.voorwerpX+this.breedteVW-1)) || ( (doelX <= this.voorwerpX) && (doelX + actie.breedteVW - 1>=this.voorwerpX)) ){

            if ( ((doelY>=this.voorwerpY) && (doelY<= this.voorwerpY+this.lengteVW-1)) || ( (doelY <= this.voorwerpY) && (doelY + actie.lengteVW - 1>=this.voorwerpY)) ){
               return true;
            }
        }
        return false;
    }
    
    
    
    
}
