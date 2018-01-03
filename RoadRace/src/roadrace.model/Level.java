/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roadrace;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Soufiane
 */
public class Level {
    
    private final static int levelBreedte = 24;
    private final static int levelLengte = 20;
    
    private ArrayList<Voorwerp> voorwerpen;
    
    private int spelerX, spelerY;
    private int level, snelheid, totAfstand, bewegingsInterval, afstandTick;
    
    private double brandstof, beschadiging;
    private boolean spelGewonnen;

    public Voorwerp speler;
    private VoertuigType vijandVoertuigtype;
    
    private Vijand vijand;
    private Thread thread;
    
    private Random random;
    
    private Media media;
    private MediaPlayer mediaplayer;
        
    /**
     *
     */
    public Level() {
        initParameters();
    }
    
    /**
     * De methode "initParameters" wordt als eerste gestart
     * Deze methode zorgt ervoor dat alle begin parameters juist ingesteld worden
     * Parameters zoals 
     * Ook worden de coordinaten van de speler ingesteld en de muur wordt opgebouwd
     * 
     */
    public void initParameters(){
        this.level = 1;
        this.spelGewonnen = false;
        this.random = new Random();
        this.voorwerpen = new ArrayList<>();
        
        // Vijand thread
        this.vijand = new Vijand(this);
        
        this.setLevelParam();
        
        this.spelerX = 9;
        this.spelerY = 15;
        speler = new Voorwerp(VoorwerpType.SPELER, this.spelerX, this.spelerY);
        voorwerpen.add(speler);
        
        // Muur opbouwen
        for (int a=0; a<levelLengte; a++){
            voorwerpen.add(new Voorwerp(VoorwerpType.MUUR, 2, a));
            voorwerpen.add(new Voorwerp(VoorwerpType.MUUR, 21, a));
        }

    }
    
    /**
     * De functie "beweegSpelerLinks" wordt opgeroepen vanuit de controller nadat de speler op de "Linkse Pijl" toets klikt
     * Deze methode gaat eerst controleren of er geen andere voorwerpen op de doelcoordinaten van de speler liggen m.b.v. de methode "controleerVerplaatsingSpeler", zoniet wordt de speler verplaatst met een xwaarde van -1
     */
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
    /**
     * De functie "beweegSpelerRechts" wordt opgeroepen vanuit de controller nadat de speler op de "Rechtse Pijl" toets klikt
     * Deze methode gaat eerst controleren of er geen andere voorwerpen op de doelcoordinaten van de speler liggen m.b.v. de methode "controleerVerplaatsingSpeler", zoniet wordt de speler verplaatst met een x-waarde van +1
     */
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
    
    /**
     * De functie "beweegSpelerBoven" wordt opgeroepen vanuit de controller nadat de speler op de "Boven Pijl" toets klikt
     * Deze methode gaat eerst controleren of er geen andere voorwerpen op de doelcoordinaten van de speler liggen m.b.v. de methode "controleerVerplaatsingSpeler", zoniet wordt de speler verplaatst met een y-waarde van -1
     */
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
    
    /**
     * De functie "beweegSpelerOnder" wordt opgeroepen vanuit de controller nadat de speler op de "Onder Pijl" toets klikt
     * Deze methode gaat eerst controleren of er geen andere voorwerpen op de doelcoordinaten van de speler liggen m.b.v. de methode "controleerVerplaatsingSpeler", zoniet wordt de speler verplaatst met een y-waarde van +1
     */
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
    
    /**
     * De methode "controleerVerplaatsingSpeler" controleert de beweging die een speler wilt uitvoeren
     * Eerst wordt er gecontroleerd of de speler niet boven of onder het veld wilt
     * 
     * Maar eerst wordt gecontrolleerd de speler niet op de doelcoordinaat staat met de methode "isOp" van de "Voorwerp" klasse die een true of false terug geeft.
     * Als de speler niet op het doelcoordinaat staat, dan wordt er gekeken of het bewegend voorwerp onderaan staat (want als het voorwerp onder het veld valt moet het verdwijnen
     * Zoniet mag het voorwerp met y-waarde +1 bewegen.
     * 
     * Als de speler wel op de doelcoordinaat van het bewegend voorwerp staat, dan wordt er gekeken naar wat voor type voorwerp het is
     * Als het een brandstof voorwerp is, dan verhoogt de brandstofwaarde van de speler naar 1.00, verwijdert dit voorwerp uit lijst en wordt er een geluid afgespeeld mbv "startBrandstofGeluid"
     * 
     * Als het een voertuig is, dan wordt het beschadigingsniveau van zowel het voertuig als de speler verhoogt
     * Dan wordt er nog gecontroleerd of het voertuig een vijand is en door de beschadiging dood is, want dan is het level uitgespeeld
     * Tenslotte wordt de methode "botsing" uitgevoerd, deze gaat bepalen hoe het voertuig wordt verplaatst na de botsing
     * 
     * @param doelX het doel x-coordinaat van de speler
     * @param doelY het doel y-coordinaat van de speler
     * @return 
     */
    private boolean controleerVerplaatsingSpeler(int doelX, int doelY){
        
        // Controlere grens: boven- en onderzijde
        if ((doelY < 2) || (doelY+this.speler.getLengteVW()-1>levelLengte-2)){
            return false;
        }
        
        // Controle of er al een voorwerp op het doelcoordinaat van de speler staat
        for (Voorwerp vw: voorwerpen){
            
            if( vw.getType() != VoorwerpType.SPELER){
                
                if (vw.isOp(speler, doelX,doelY)){
                
                    if (vw.getType()==VoorwerpType.BRANDSTOF){
                        System.out.println("Speler raakt brandstof");
                        this.brandstof = 1.00;
                        
                        startBrandstofGeluid();
                        
                        vw.setDood(true);
                        voorwerpen.remove(vw);
                        return true;
                    }
                    else if (vw.getType()==VoorwerpType.MUUR){
                        System.out.println("Speler raakt muur");
                        this.speler.setTotBeschadigingVW(vw.getBeschadigingAanAnderen());
                        this.beschadiging = this.speler.getTotBeschadigingVW();
                        
                        startBotsGeluid();
                        
                        return false;
                    }
                    else if (vw.getType()==VoorwerpType.VOERTUIG){
                        System.out.println("Speler raakt voertuig");
                        this.speler.setTotBeschadigingVW(vw.getBeschadigingAanAnderen());
                        this.beschadiging = this.speler.getTotBeschadigingVW();
                        System.out.println("Totale beschadiging tegenligger: "  + vw.getTotBeschadigingVW());
                        vw.setTotBeschadigingVW(this.speler.getBeschadigingAanAnderen());
                        
                        startBotsGeluid();
                        
                        if (vw.isVijand() && vw.isDood()){
                            // Speler raakt vijand en is dood
                            vw.setVijand(false);
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
                            vw.setVijand(false);
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
        if (this.level<3){
            this.level++;
            this.setLevelParam();
        }
        else{
            // Speler heeft gewonnen
            this.spelGewonnen=true;
        }
    }
    
    /**
     * De methode "setLevelParam" wordt uitgevoerd door de methodes "initParameters()" en "verhoogLevel"
     * Deze methode gaat de juiste parameters voor de levels instellen
     */
    private void setLevelParam(){
        switch (this.level){
            case (1):
                this.snelheid = 50;
                this.brandstof = 1;
                this.bewegingsInterval = 1000;
                // Vijand => Motor
                vijandVoertuigtype = VoertuigType.MOTOR;
                this.thread = new Thread(this.vijand);
                this.thread.start();
                
                break;
            case (2):
                this.snelheid = 75;
                this.bewegingsInterval = 800;
                // Vijand => Auto
                vijandVoertuigtype = VoertuigType.AUTO;
                //this.thread.start(); // WERKT NIET NA DE EERSTE AANROEP (hierboven) 
                this.thread = new Thread(this.vijand);
                this.thread.start();
                break;
              
            case (3):
                this.snelheid = 100;
                this.bewegingsInterval = 600;
                // Vijand => Truck
                vijandVoertuigtype = VoertuigType.TRUCK;
                //this.thread.start();// WERKT NIET NA DE EERSTE AANROEP (hierboven) 
                this.thread = new Thread(this.vijand);
                this.thread.start();
                break;
        }
        this.afstandTick = this.bewegingsInterval/this.snelheid;
    }
    
    /**
     * De methode "beweegVoorwerpen" wordt uitgevoerd na elke threadtick van de klasse "Beweging", met behulp hiervan kunnen de voorwerpen bewegen (vallen)
     * Voor alle voorwerpen buiten de speler en muur gaan we proberen te laten bewegen met een y-waarde van +1
     * 
     * Maar eerst wordt gecontrolleerd de speler niet op de doelcoordinaat staat met de methode "isOp" van de "Voorwerp" klasse die een true of false terug geeft.
     * Als de speler niet op het doelcoordinaat staat, dan wordt er gekeken of het bewegend voorwerp onderaan staat (want als het voorwerp onder het veld valt moet het verdwijnen
     * Zoniet mag het voorwerp met y-waarde +1 bewegen.
     * 
     * Als de speler wel op de doelcoordinaat van het bewegend voorwerp staat, dan wordt er gekeken naar wat voor type voorwerp het is
     * Als het een brandstof voorwerp is, dan verhoogt de brandstofwaarde van de speler naar 1.00, verwijdert dit voorwerp uit lijst en wordt er een geluid afgespeeld mbv "startBrandstofGeluid"
     * 
     * Als het een voertuig is, dan wordt het beschadigingsniveau van zowel het voertuig als de speler verhoogt
     * Dan wordt er nog gecontroleerd of het voertuig een vijand is en door de beschadiging dood is, want dan is het level uitgespeeld
     * Tenslotte wordt de methode "botsing" uitgevoerd, deze gaat bepalen hoe het voertuig wordt verplaatst na de botsing
     */
    public void beweegVoorwerpen(){
        System.out.println("Beweeg Voorwerpen");
        int dy = 1;
        int doelX = 0;
        int doelY = 0;
        
        //for (Voorwerp vw: voorwerpen){
        Iterator<Voorwerp> voorwerpenLijst = voorwerpen.iterator();
        while(voorwerpenLijst.hasNext())   {
            
            Voorwerp vw = voorwerpenLijst.next();
            doelX = vw.getVoorwerpX();
            doelY = vw.getVoorwerpY() + dy;
            
            if( vw.getType() != VoorwerpType.SPELER && vw.getType() != VoorwerpType.MUUR){
                
                if (!speler.isOp(vw, doelX,doelY)){
                    // Speler bevindt zich NIET onder een vallend voorwerp
                    if (vw.getType()==VoorwerpType.BRANDSTOF || vw.getType()==VoorwerpType.VOERTUIG){
                         if (vw.getVoorwerpY()>=this.levelLengte-1){
                             //this.voorwerpen.remove(vw);
                             voorwerpenLijst.remove();
                         }
                         else{
                             if (vw.getType()==VoorwerpType.VOERTUIG && vw.isVijand()){
                                // De vijand beweegt na een specifieke hoogte niet meer => hij rijdt als het ware even snel als de speler
                                if (vw.getVoorwerpY()<7){
                                    vw.verplaatsOnder(dy);
                                }
                             }
                             else{
                                 // Alle andere gewone voertuig bewegen wel gewoon nog => ze worden ingehaald door de speler
                                 vw.verplaatsOnder(dy);
                             }
                         }
                    }
                }
                else{
                    // Speler bevindt zich WEL onder een vallend voorwerp
                    if (vw.getType()==VoorwerpType.BRANDSTOF){
                        // Brandstof opgepakt
                        this.brandstof=1;
                        //this.voorwerpen.remove(vw);
                         voorwerpenLijst.remove();
                        // Geluid
                        startBrandstofGeluid();
                        
                    }
                    else if (vw.getType()==VoorwerpType.VOERTUIG){
                        System.out.println("Voertuig raakt speler");
                        this.speler.setTotBeschadigingVW(vw.getBeschadigingAanAnderen());
                        this.beschadiging = this.speler.getTotBeschadigingVW();
                        vw.setTotBeschadigingVW(this.speler.getBeschadigingAanAnderen());
                        
                        startBotsGeluid();
                        
                        if (vw.isVijand() && vw.isDood()){
                            // Speler raakt vijand en is dood
                            // Level verhogen
                            System.out.println("Vijand verslagen -> Verhoog level");
                            this.verhoogLevel();
                            vw.setVijand(false);
                        }
                        // Berekenen hoe een voertuig wordt geduwd na een botsing
                        // eerste parameters is het voorwerp dat gaat botsen 
                        // tweede parameters id het voorwerp dat gebotst wordt
                        this.botsting(vw,speler);
                    }
                }
            }
        }
        // Afstand verhogen
        this.totAfstand += this.afstandTick;
        // Brandstof verlagen
        if (this.brandstof>0){
             this.brandstof -=0.01;
        }
        else{
            this.speler.setDood(true);
        }
        nieuweVoorwerpen();
    }
    
    
    /**
     * De methode "nieuweVoorwerpen" wordt uitgevoerd door de methode "beweegVoorwerpen" en zorgt ervoor dat er nieuwe voorwerpen (brandstof of voertuigen) tevoorschijn komen
     * De methode beweegVoorwerpen wordt door een thread aangeroepen
     * 
     * Deze methode gaat m.b.v. random-generatie kiezen welke voorwerp en op welke x-coordinaat deze moet komen
     */
    private void nieuweVoorwerpen(){
        // nieuw voorwerp tonen
        int randomVoorwerp = random.nextInt(15);
        int randomVoorwerpX = random.nextInt(12)+4;// Horizonale waarde ligt tussen 4 en 16
        int VoorwerpY = -9; // grote waarde is vooral door de truck voorwerpen
        Voorwerp nieuwVW;
                
        switch (randomVoorwerp){
            case 1:
                nieuwVW = new Voorwerp(VoorwerpType.BRANDSTOF, randomVoorwerpX,VoorwerpY);
                voorwerpen.add(nieuwVW);
                
                this.controleNieuweVoorwerpen(nieuwVW);
                break;

            case 4:
                nieuwVW = new Voorwerp(VoorwerpType.VOERTUIG, randomVoorwerpX, VoorwerpY, VoertuigType.AUTO, false);
                voorwerpen.add(nieuwVW);
                
                this.controleNieuweVoorwerpen(nieuwVW);
                break;
                
            case 9:
                nieuwVW = new Voorwerp(VoorwerpType.VOERTUIG, randomVoorwerpX, VoorwerpY, VoertuigType.MOTOR, false);
                voorwerpen.add(nieuwVW);
                
                this.controleNieuweVoorwerpen(nieuwVW);
                break;

        }
        
    }
    private void controleNieuweVoorwerpen(Voorwerp nieuwVW){
        Iterator<Voorwerp> voorwerpenLijst = voorwerpen.iterator();
        while(voorwerpenLijst.hasNext())   {
            Voorwerp vw = voorwerpenLijst.next();
            if (vw != nieuwVW){
                // Enkel kijken naar voorwerpen die al bestaan => dus alles buiten het nieuw voorwerp
                if (vw.isOp(nieuwVW, nieuwVW.getVoorwerpX(),nieuwVW.getVoorwerpY())){
                    // nieuw voorwerp bevindt zich op een bestaand voorwerp -> verplaaten
                    if (nieuwVW.getVoorwerpX()>=2 && nieuwVW.getVoorwerpX()<=15){
                        nieuwVW.verplaatsRechts(3);
                    }
                    else{
                        nieuwVW.verplaatsLinks(3);
                    }
                }
            }
        }
    }

    /**
     *
     */
    public void nieuwVijand(){
        System.out.println("nieuwe vijand");
        int randomVoorwerpX = random.nextInt(12)+4; // Horizonale waarde ligt tussen 4 en 16
        Voorwerp nieuwVW =new Voorwerp(VoorwerpType.VOERTUIG, randomVoorwerpX, -5, this.vijandVoertuigtype, true);
        voorwerpen.add(nieuwVW);
        controleNieuweVoorwerpen(nieuwVW);
    }
    
    
    // AUDIO
    /**
     * De methode "startBotsGeluid" wordt uigevoerd nadat de speler tegen een voertuig of de muur botst
     * M.b.v. de klassen Media en MediaPlayer kan er een geluid afgespeeld worden
     */
    private void startBotsGeluid(){
        String geluid = "src/roadrace/sound/botsing.mp3";
        media = new Media(new File(geluid).toURI().toString());
        mediaplayer = new MediaPlayer(media);
        mediaplayer.play();
    }
    
    /**
     * De methode "startBrandstofGeluid" wordt uigevoerd nadat de speler een brandstof voorwerp raakt
     * M.b.v. de klassen Media en MediaPlayer kan er een geluid afgespeeld worden
     */
    private void startBrandstofGeluid(){
        String geluid = "src/roadrace/sound/brandstof.mp3";
        media = new Media(new File(geluid).toURI().toString());
        mediaplayer = new MediaPlayer(media);
        mediaplayer.play();
    }
    
    
    
    
    // GETTERS
    
    /**
     * Geeft de brandstof waarde van de speler terug
     * @return brandstof het huidige brandstof niveau van de speler
     */
    public double getBrandstof() {
        return brandstof;
    }
    
    /**
     * Geeft de beschadigings waarde van de speler terug
     * @return beschadiging het huidige beschadigings niveau van de speler
     */
    public double getBeschadiging() {
        return beschadiging;
    }
    
    /**
     * Geeft de brandstof waarde van de speler terug
     * @return brandstof het huidige brandstof niveau van de speler
     */
    public int getSnelheid() {
        return snelheid;
    }
    
    /**
     * Geeft de toestand van de speler terug
     * @return speler.isDood() de toestand vd speler
     */
    public boolean isDood(){
        return this.speler.isDood();
    }
    
    /**
     * Geeft het huidge level van het spel terug
     * @return level het huidge level van het spel
     */
    public int getLevel() {
        return level;
    }
    
    /**
     * Geeft de totale afgelegde afstand door de speler terug
     * @return totAfstand de totale afstand
     */
    public int getTotAfstand() {
        return totAfstand;
    }
    
    /**
     *
     * @return
     */
    public Iterator<Voorwerp> getVoorwerpenLijst()
    {
        return this.voorwerpen.iterator();
    }  
    
    /**
     *
     * @return
     */
    public int getAantalVoorwerpen(){
        return this.voorwerpen.size();
    }

    /**
     *
     * @return
     */
    public boolean isSpelGewonnen() {
        return this.spelGewonnen;
    }
    
    public int getBewegingsInterval(){
        return this.bewegingsInterval;
    }
    
}
