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
//        for (int a=0; a<levelLengte; a++){
//            voorwerpen.add(new Voorwerp(VoorwerpType.MUUR, 2, a, true, false));
//            voorwerpen.add(new Voorwerp(VoorwerpType.MUUR, 20, a, true, false));
//        }
        
        speler = new Voorwerp(VoorwerpType.SPELER, this.spelerX, this.spelerY, true, false);
        voorwerpen.add(speler);
        voorwerpen.add(new Voorwerp(VoorwerpType.BRANDSTOF, 4, 0, true, false));
//        voorwerpen.add(new Voorwerp(VoorwerpType.VOERTUIG, 12, 11, true, false));
        
        voorwerpen.add(new Voorwerp(VoorwerpType.VOERTUIG, 10, 10, true, false));
//        voorwerpen.add(new Voorwerp(VoorwerpType.VOERTUIG, 10, 15, true, false));

    }
    
    public void beweegSpelerLinks(){
        int dx = 1;
        int doelX = speler.getVoorwerpX() - dx ;
        int doelY = speler.getVoorwerpY();
        
        if (controleerVerplaatsingSpeler(doelX, doelY)){
            speler.verplaatsLinks(dx);
            this.spelerX = speler.getVoorwerpX();
            this.spelerY = speler.getVoorwerpY();
        }
    }
    
    public void beweegSpelerRechts(){
        int dx = 1;
        int doelX = speler.getVoorwerpX() + dx ;
        int doelY = speler.getVoorwerpY();
        
        if (controleerVerplaatsingSpeler(doelX, doelY)){
            speler.verplaatsRechts(dx);
            this.spelerX = speler.getVoorwerpX();
            this.spelerY = speler.getVoorwerpY();
        }
    }
    
    public void beweegSpelerBoven(){
        int dy = 1;
        int doelX = speler.getVoorwerpX();
        int doelY = speler.getVoorwerpY() - dy;
        
        if (controleerVerplaatsingSpeler(doelX, doelY)){
            speler.verplaatsBoven(dy);
            this.spelerX = speler.getVoorwerpX();
            this.spelerY = speler.getVoorwerpY();
        }
    }
    
    public void beweegSpelerOnder(){
        int dy = 1;
        int doelX = speler.getVoorwerpX();
        int doelY = speler.getVoorwerpY() + dy;
        
        if (controleerVerplaatsingSpeler(doelX, doelY)){
            speler.verplaatsOnder(dy);
            this.spelerX = speler.getVoorwerpX();
            this.spelerY = speler.getVoorwerpY();
        }
        
    }
    
    private boolean controleerVerplaatsingSpeler(int doelX, int doelY){
        
        // Grens: boven- en onderzijde
        if ((doelY < 2) || (doelY>levelLengte-2)){
            return false;
        }
        // Controle of er al een voorwerp op het doelcoordinaat van de speler staat
        for (Voorwerp vw: voorwerpen){
            
            if( vw.getType() != VoorwerpType.SPELER){
                
                if (vw.isOp(speler, doelX,doelY)){
                
                    if (vw.getType()==VoorwerpType.BRANDSTOF){
                        System.out.println("Speler raakt brandstof");
                        this.brandstof = 1.00;
                        //voorwerpen.remove(vw);
                        return true;
                    }
                    else if (vw.getType()==VoorwerpType.MUUR){
                        System.out.println("Speler raakt muur");
                        this.speler.setTotBeschadigingVW(vw.getBeschadigingAanAnderen());
                        this.beschadiging = this.speler.getTotBeschadigingVW();
                        return false;
                    }
                    else if (vw.getType()==VoorwerpType.VOERTUIG){
                        System.out.println("Speler raakt voertuig");
                        this.speler.setTotBeschadigingVW(vw.getBeschadigingAanAnderen());
                        this.beschadiging = this.speler.getTotBeschadigingVW();
                        System.out.println("Totale beschadiging tegenligger: "  + vw.getTotBeschadigingVW());
                        vw.setTotBeschadigingVW(this.speler.getBeschadigingAanAnderen());

                        if (vw.isVijand() && vw.isDood()){
                            // Speler raakt vijand en is dood
                            // Level verhogen
                            System.out.println("Vijand verslagen -> Verhoog level");
                            this.verhoogLevel();
                        }

                        // Berekenen hoe een voertuig wordt geduwd na een botsing
                        // eerste parameters is het voorwerp dat gaat botsen 
                        // tweede parameters id het voorwerp dat gebotst wordt
                        this.botsting(speler,vw);

                        return false;
                    }
                }
            }   
        }
        return true;
    }
    
    private boolean contrVerplGebotstVoertuig(Voorwerp reactieVW,int doelX, int doelY){
        
        // Controle of er al een voorwerp op het doelcoordinaat van het gebotste voertuig staat
        for (Voorwerp vw: voorwerpen){
            
//            if( vw.getType() != VoorwerpType.SPELER){
            if( vw != reactieVW){
    
                if (vw.isOp(reactieVW, doelX,doelY)){
                    
                    if (vw.getType()==VoorwerpType.MUUR){
                        System.out.println("Gebotste voertuig raakt muur");
                        reactieVW.setTotBeschadigingVW(vw.getBeschadigingAanAnderen());
                        return false;
                    }
                    
                    if (vw.getType()==VoorwerpType.VOERTUIG){
                        System.out.println("Gebotste voertuig raakt ander voertuig");
                        // het botsende voertuig loopt beschading op
                        reactieVW.setTotBeschadigingVW(vw.getBeschadigingAanAnderen());
                        
                        // en het gebotste voertuig loopt beschadeging op
                        vw.setTotBeschadigingVW(reactieVW.getBeschadigingAanAnderen());
                        
                        if (vw.isVijand() && vw.isDood()){
                            // Speler raakt vijand en is dood
                            // Level verhogen
                            System.out.println("Vijand verslagen -> Verhoog level");
                            this.verhoogLevel();
                        }

                        // Berekenen hoe een voertuig wordt geduwd na een botsing
                        // eerste parameters is het voorwerp dat gaat botsen 
                        // tweede parameters id het voorwerp dat gebotst wordt
                        this.botsting(reactieVW,vw);
                        return false;
                        
                    }
                    
                    
                }
            }   
        }
        return true;
    }
   
    /**
     * Deze methode bepaalt de richting vd verplaatsing van een voertuig nadat de speler of een ander voertuig hier tegenaan botst
     * eerste parameters is het voorwerp dat gaat botsen (ACTIE)
     * tweede parameters is het voorwerp dat gebotst wordt  (REACTIE)
     */
    private void botsting(Voorwerp actieVW, Voorwerp reactieVW){
        int dx = 1;
        int dy = 1;
        
        ////////////////////////////////////////////////////////
        //  Actie voertuig staat op dezelfde hoogte als het reactie voertuig
        ////////////////////////////////////////////////////////
        if (actieVW.getVoorwerpY() == reactieVW.getVoorwerpY() ){

            if (actieVW.getVoorwerpX() < reactieVW.getVoorwerpX()){
                //Speler staat links van het voertuig -> verplaatst het voertuig naar rechts
                
                if (contrVerplGebotstVoertuig(reactieVW, reactieVW.getVoorwerpX()+dx, reactieVW.getVoorwerpY())){
                    reactieVW.verplaatsRechts(dx);
                }
                
            }
            if (actieVW.getVoorwerpX() > reactieVW.getVoorwerpX()){
                //Speler staat rechts van het voertuig -> verplaatst het voertuig naar links
                
                if (contrVerplGebotstVoertuig(reactieVW, reactieVW.getVoorwerpX()-dx, reactieVW.getVoorwerpY())){
                    reactieVW.verplaatsLinks(dx);
                }
            }
        }
        
        ////////////////////////////////////////////////////////
        //  Actie (botsende) voertuig en reactie (gebotste) voertuig staan op verschillende hoogtes
        ////////////////////////////////////////////////////////
        else if (actieVW.getVoorwerpY() > (reactieVW.getVoorwerpY() + (reactieVW.getLengteVW()-1)) ){
            // Speler staat helemaal onder het voertuig -> verplaats het voertuig naar boven
            if (contrVerplGebotstVoertuig(reactieVW, reactieVW.getVoorwerpX(), reactieVW.getVoorwerpY()-dy)){
                reactieVW.verplaatsBoven(dy);
            }
            
        }
        else if (actieVW.getVoorwerpY() > (reactieVW.getVoorwerpY()) ){
            // Speler staat schuin onder het voertuig
            if (actieVW.getVoorwerpX() < reactieVW.getVoorwerpX()){
                //Speler staat links van het voertuig -> verplaatst het voertuig naar rechts en naar boven
                if (contrVerplGebotstVoertuig(reactieVW, reactieVW.getVoorwerpX()+dx, reactieVW.getVoorwerpY()-dy)){
                    reactieVW.verplaatsRechts(dx);
                    reactieVW.verplaatsBoven(dy);
                }
                
            }
            if (actieVW.getVoorwerpX() > reactieVW.getVoorwerpX()){
                //Speler staat rechts van het voertuig -> verplaatst het voertuig naar links en naar boven
                if (contrVerplGebotstVoertuig(reactieVW, reactieVW.getVoorwerpX()-dx, reactieVW.getVoorwerpY()-dy)){
                    reactieVW.verplaatsLinks(dx);
                    reactieVW.verplaatsBoven(dy);
                }
               
            }
        }
        else if ( (actieVW.getVoorwerpY()+ actieVW.getLengteVW() -1) < reactieVW.getVoorwerpY()){
            // Speler staat helemaal boven het voertuig -> verplaats het voertuig naar onder
            if (contrVerplGebotstVoertuig(reactieVW, reactieVW.getVoorwerpX(), reactieVW.getVoorwerpY()+dy)){
                reactieVW.verplaatsOnder(dy);
            }
        }
        else if (actieVW.getVoorwerpY() < (reactieVW.getVoorwerpY()) ){
            // Speler staat schuin boven het voertuig
             if (actieVW.getVoorwerpX() < reactieVW.getVoorwerpX()){
                //Speler staat links van het voertuig -> verplaatst het voertuig naar rechts en naar onder
                if (contrVerplGebotstVoertuig(reactieVW, reactieVW.getVoorwerpX()+dx, reactieVW.getVoorwerpY()+dy)){
                    reactieVW.verplaatsRechts(dx);
                    reactieVW.verplaatsOnder(dy);
                }

                
            }
            if (actieVW.getVoorwerpX() > reactieVW.getVoorwerpX()){
                //Speler staat rechts van het voertuig -> verplaatst het voertuig naar links en naar onder
                if (contrVerplGebotstVoertuig(reactieVW, reactieVW.getVoorwerpX()-dx, reactieVW.getVoorwerpY()+dy)){
                    reactieVW.verplaatsLinks(dx);
                    reactieVW.verplaatsOnder(dy);
                }
                
            }
        }
        
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
        int dy = 1;
        int doelX = 0;
        int doelY = 0;
        
        for (Voorwerp vw: voorwerpen){
            doelX = vw.getVoorwerpX();
            doelY = vw.getVoorwerpY() + dy;
            
            if( vw.getType() != VoorwerpType.SPELER){
                
                if (!speler.isOp(vw, doelX,doelY)){
                    // Speler bevindt zich NIET onder een vallend voorwerp
                    if (vw.getType()==VoorwerpType.BRANDSTOF || vw.getType()==VoorwerpType.VOERTUIG){
                         vw.verplaatsOnder(dy);
                    }
                }
                
                else{
                    // Speler bevindt zich onder een vallend voorwerp
                    if (vw.getType()==VoorwerpType.BRANDSTOF){
                        // Brandstof opgepakt
                        this.brandstof=1;
                        //this.voorwerpen.remove(vw);
                        
                    }
                    else if (vw.getType()==VoorwerpType.VOERTUIG){
                        System.out.println("Voertuig raakt speler");
                        this.speler.setTotBeschadigingVW(vw.getBeschadigingAanAnderen());
                        this.beschadiging = this.speler.getTotBeschadigingVW();

                        vw.setTotBeschadigingVW(this.speler.getBeschadigingAanAnderen());

                        if (vw.isVijand() && vw.isDood()){
                            // Speler raakt vijand en is dood
                            // Level verhogen
                            System.out.println("Vijand verslagen -> Verhoog level");
                            this.verhoogLevel();
                        }

                        // Berekenen hoe een voertuig wordt geduwd na een botsing
                        // eerste parameters is het voorwerp dat gaat botsen 
                        // tweede parameters id het voorwerp dat gebotst wordt
                        this.botsting(vw,speler);

                    }
                    
                }
                
                
            }
                

//            if (vw.getType()==VoorwerpType.VOERTUIG && !vw.isVijand()){
//               vw.verplaatsOnder(dy);
//            }
                    
                   
             
        }
        
        // Afstand verhogen
        this.totAfstand += this.afstandTick;
        // Brandstof verlagen
        if (this.brandstof>=0){
             this.brandstof -=0.01;
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
