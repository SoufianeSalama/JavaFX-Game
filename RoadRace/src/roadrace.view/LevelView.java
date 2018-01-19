/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roadrace;


import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import roadrace.Level;
import roadrace.Level;
import roadrace.Voorwerp;
import roadrace.Voorwerp;
import roadrace.VoorwerpView;

/**
 * De levelView is de hoofdview van dit project, zorgt ervoor dat er voorwerpviews van voorwerpen gemaakt worden
 * Zorgt er ook voor dat er views toegevoegd of verwijderd worden
 * @author Soufiane
 */
public class LevelView extends Region{

    public final static int VERGROTING = 25;
    private Level model;
    private ArrayList<VoorwerpView> vwViews;
    private Iterator<Voorwerp> voorwerpenLijst;
    
    /**
     * In de constructor van de LevelView klasse wordt er een nieuwe VoorwerpView lijst aangemaakt, 
     * het achtergrond van de scene ingesteld en de standaard voorwerpen toegevoegd zoals de muur en de speler
     * @param model
     */
    public LevelView(Level model) {
        this.model = model;
        vwViews = new ArrayList<>();
        this.initBackground();
        this.addVoorwerpenViews();
    }
    
    /**
     *  De methode initBackground wordt uitgevoerd door de contructor van de LevelView klasse
     *  en wordt gebruikt om de achtergrond foto van de spel scene in te stellen
     */
    private void initBackground(){
        Image image = new Image(getClass().getResourceAsStream("/roadrace.img/RoadRace.png")); 
        ImageView iv = new ImageView(image);
        iv.setFitHeight(500);
        iv.setFitWidth(600);

        getChildren().add(iv);
    }
    
    /**
     * De methode addVoorwerpenViews wordt uitgevoerd door de contructor.
     * En gaat de lijst van voorwerpen met behulp van een iterator uit het model halen
     * en per voorwerp zijn view aanmaken en toevoegen aan de view.
     */
    private void addVoorwerpenViews(){
        Iterator<Voorwerp> voorwerpenLijst = model.getVoorwerpenLijst();
        while (voorwerpenLijst.hasNext()){
            Voorwerp vw = voorwerpenLijst.next();
            vwViews.add(new VoorwerpView(vw));
        }
        getChildren().addAll(vwViews);
        updateVWViews();
    }
    
    /**
     * De methode "updateVWViews()" wordt opgeroepen vanuit de controller, nadat de speler beweegt of de thread voorwerpen laat bewegen(vallen)
     * en gaat eerste controleren of er views van voorwerpen aangemaakt of verwijdert moeten worden
     * en gaat dan elke voorwerpview updaten
     */
    public void updateVWViews(){
        checkVoorwerpenEnViews();
        for (VoorwerpView vww : vwViews){
            vww.update();
        }
    }
    
    /**
     * Tijdens het spel worden er voorwerpen toegevoegd en verwijderd
     * Het is met behulp van de methode "checkVoorwerpenEnViews" dat we gaan controleren
     * ofdat er views van voorwerpen aangemaakt of verwijderd moeten worden.
     * 
     * Technisch: als er meer views dan voorwerpen zijn, dan weten we dat er nog een view bestaat van een voorwerp die tijdens het spel verwijderd is,
     * dus deze view moet ook verwijderd worden uit de viewslijst.
     * En ook omgekeerd, dus dat er nieuwe views aangemaakt moeten worden van nieuwe views.
     * 
     * Het uitzoeken van welk specifiek voorwerp de view aangemaakt of verwijderd moet worden gebeurkt respectievelijk in de methodes "maakView" en "verwijderView"
     */
    private void checkVoorwerpenEnViews(){
       voorwerpenLijst = model.getVoorwerpenLijst();
       if (model.getAantalVoorwerpen()>this.vwViews.size()){
           // Er bestaan voorwerpen waarvoor er nog geen views zijn
           // deze voorwerpen zoeken en aanmaken
           maakView();
       }
       else if(model.getAantalVoorwerpen()<this.vwViews.size()){
           // Er zijn bestaan nog views van verwijderde voorwerpen
           // deze voorwerpenviews zoeken en verwijderen
           verwijderView();
       }
    }
    
    /**
     * Tijdens het spel worden er nieuwe voorwerpen zoals voertuig en brandstof toegevoegd.
     * Van deze voorwerpen moet er nog een view aangemaakt worden.
     * Dus de methode "maakView" gaat per voorwerp kijken of er van dit voorwerp een view bestaat in de VoorwerpView lijst mbv de methode controleerViewLijst.
     * Als deze methode een false terug stuurt, dan bestaat er nog geen voorwerpview en wordt er ene aangemaakt.
     */
    private void maakView(){
        System.out.println("Voorwerpviews heeft :" + vwViews.size());
        while (voorwerpenLijst.hasNext()){
            Voorwerp vw = voorwerpenLijst.next();
            if (vw.getType() != VoorwerpType.MUUR) // Controle voor alle voorwerp behalve muur, deze worden niet toegevoegd/verwijderd
            {
                if (!this.controleerViewLijst(vw)){
                    // Voorwerp heeft geen view -> maak een nieuw view aan van dit voorwerp
                    VoorwerpView vww = new VoorwerpView(vw);
                    vwViews.add(vww);
                    getChildren().add(vww);
                }
            }
        }
    }
    
    /**
     * De methode controleerViewLijst gaat voor het meegekregen voorwerp controleren of er van deze een view bestaat.
     * Bij een true bestaat er een view van het meegesturude voorwerp, dus er wordt uiteindelijk GEEN nieuwe view aangemaakt
     * @param vw voorwerp
     * @return result true/false
     */
    private boolean controleerViewLijst(Voorwerp vw){
        boolean result = false;
        for (VoorwerpView vww: vwViews){
            if (vww.getVoorwerp().getType() != VoorwerpType.MUUR) // Controle voor alle voorwerp behalve muur, deze worden niet toegevoegd/verwijderd
            {
                if (vww.getVoorwerp()==vw){
                  result = true;
                }
            }
        }
        return result;
    }
    
    
    /**
     * Tijdens het spel worden er voorwerpen verwijderd uit de voorwerpenlijst, bijvoorbeeld nadat de speler een brandstof opneemt.
     * Het is dan zo dat de voorwerpview van dit voorwerp ook verwijderd moet worden.
     * Dus de methode "verwijderView" gaat per view kijken of er van deze view een voorwerp bestaat in de Voorwerpen lijst mbv de methode "controleerVoorwerplijst" .
     * Als deze methode een false terug stuurt, dan bestaat er het voorwerp niet meer, en wordt de vooorwerpview verwijderd.
     */
    private void verwijderView(){
        for (VoorwerpView vww: vwViews){
            if (!this.controleerVoorwerplijst(vww)){
                // Het voorwerp van deze view zit niet in de voorwerpenlijst -> dus verwijder deze view
                getChildren().remove(vww);
                vwViews.remove(vww);
                return;
            }
        }
    }
    /**
     * De methode controleerVoorwerplijst gaat voor het meegekregen voorwerpview controleren of het voorwerp van deze view nog bestaat.
     * Bij een true bestaat het voorwerp nog, dus deze wordt uiteindelijk NIET verwijderd
     * @param vww voorwerpview
     * @return result true/false
     */
    private boolean controleerVoorwerplijst(VoorwerpView vww){
        boolean result = false;
        while (voorwerpenLijst.hasNext()){
            Voorwerp vw = voorwerpenLijst.next();
            if (vww.getVoorwerp()==vw){
                result = true;
                return result;
            }
        }
        return result;
    }
}
