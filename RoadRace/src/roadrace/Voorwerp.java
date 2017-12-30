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
    
    
    /**
     * Eerste constructor van de voorwerp klasse, gebruikt om de voorwerpen muur en brandstof aan te maken
     * 
     * @param type VoorwerpType
     * @param x x-coordinaat van het voorwerp
     * @param y y-coordinaat van het voorwerp
     */
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
    
    /**
     * Tweede contructor van de voorwerp klasse, gebruikt om de voorwerpen speler en voertuig aan te maken
     * 
     * @param type  VoorwerpType
     * @param x     x-coordinaat van het voorwerp
     * @param y     y-coordinaat van het voorwerp
     * @param vt    VoertuigType
     * @param vijand true of false
     */
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
    
    /**
     * De methode "setVWParameters" stelt de nodige parameters van een voorwerp in naar gelang het voorwerpType,
     * zoals beschadiging waardes, is het duwbaar en/of neembaar, de breedte en lengte en of het voowerp mag bewegen
     */
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
                        this.beschadigingAanAnderen = 0.1;
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

    /**
     * Geeft de x-coordinaat van de het voorwerp terug
     * @return voorwerpX de x-coordinaat van het voorwerp (int)
     */
    public int getVoorwerpX() {
        return voorwerpX;
    }

    /**
     * Geeft de y-coordinaat van de het voorwerp terug
     * @return voorwerpY de y-coordinaat van het voorwerp (int)
     */
    public int getVoorwerpY() {
        return voorwerpY;
    }

    /**
     * Geeft het type van de voorwerp terug (muur, voertuig, brandstof, speler)
     * @return type VoorwerpType
     */
    public VoorwerpType getType() {
        return type;
    }
    
    /**
     * Geeft het type voertuig terug (Auto, motor, truck)
     * @return voertuigType VoertuigType
     */
    public VoertuigType getVoertuigType() {
        return voertuigType;
    }
    
    /**
     * Geeft de beschadigingswaarde aan andere voorwerpen (voertuigen) van dit voorwerp terug
     * @return beschadigingAanAndere waarde van 0.00-1.00
     */
    public double getBeschadigingAanAnderen() {
        return beschadigingAanAnderen;
    }
    
    /**
     * Geeft de huidige beschadidingswaarde van dit voorwerp terug
     * @return totBeschadigingVW waarde van 0.00-1.00
     */
    public double getTotBeschadigingVW() {
        return totBeschadigingVW;
    }
    
    /**
     * Is dit voorwerp dood(true) / levend(false) (enkel van toepassing als het de vijand voertuig is)
     * @return dood true of false
     */
    public boolean isDood() {
        return dood;
    }
    
    /**
     * Is dit voorwerp duwbaar?
     * @return duwbaar true of false
     */
    public boolean isDuwbaar() {
        return duwbaar;
    }
    
    /**
     * Is dit voorwerp (voertuig) een vijand?
     * @return vijand true of false
     */
    public boolean isVijand() {
        return vijand;
    }
    
    /**
     * Is dit voorwerp neembaar? enkel van toepassing voor brandstof
     * @return neembaar true of false
     */
    public boolean isNeembaar() {
        return neembaar;
    }
    
    /**
     * Wat is de lengte (verticaal) van dit voorwerp?
     * @return lengteVW pixels
     */
    public int getLengteVW(){
       return this.lengteVW; 
    }
    
    /**
     * Wat is de breedte (horizontaal) van dit voorwerp?
     * @return breedteVW pixels
     */
    public int getBreedteVW(){
        return this.breedteVW;
    }
    
    // Setters
    
    /**
     * Deze methode stelt de toestand van een voorwerp in levend/dood
     * @param dood true of false
     */
    public void setDood(boolean dood) {
        this.dood = dood;
    }
    
    /**
     * Deze methode verhoogt het beschadigingsniveau van dit voorwerp
     * als het beschadigingsniveau hoger of gelijk is aan 1.00 dan is het voorwerp dood
     * @param totBeschadigingVW waarde van 0.00-1.00
     */
    public void setTotBeschadigingVW(double totBeschadigingVW) {
        
        this.totBeschadigingVW += totBeschadigingVW;
        if (this.totBeschadigingVW>=1.00){
            this.dood = true;
        }
    }
    
    /**
     * Deze methode zorgt ervoor dat nadat een speler een vijand heeft uitgeschakeld dat het geen vijand is
     * @param vijand true of false
     */
    public void setVijand(boolean vijand) {
        this.vijand = vijand;
    }
    
    
    /**
     * Deze methode verplaatst het voowerp naar links aan de hand van de meegestuude parameter dx
     * @param dx horizontale verplaatsing
     */
    public void verplaatsLinks(int dx){
        this.voorwerpX -=dx;
    }
    
    /**
     * Deze methode verplaatst het voorwerp naar rechts aan de hand van de meegestuurde parameter dx
     * @param dx horizontale verplaatsing
     */
    public void verplaatsRechts(int dx){
        this.voorwerpX +=dx;
    }
    
    /**
     * Deze methode verplaatst het voowerp naar onder aan de hand van de meegestuude parameter dy
     * @param dy verticale verplaatsing
     */
    public void verplaatsOnder(int dy){
        this.voorwerpY +=dy;
    }
    
    /**
     * Deze methode verplaatst het voowerp naar boven aan de hand van de meegestuude parameter dy
     * @param dy verticale verplaatsing
     */
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
