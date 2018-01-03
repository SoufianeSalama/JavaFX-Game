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
 *
 * @author Soufiane
 */
public class LevelView extends Region{

    /**
     *
     */
    public final static int VERGROTING = 25;
    private Level model;
    private ArrayList<VoorwerpView> vwViews;
    private Iterator<Voorwerp> voorwerpenLijst;
    
    /**
     *
     * @param model
     */
    public LevelView(Level model) {
        this.model = model;
        vwViews = new ArrayList<>();
        this.initBackground();
        this.addVoorwerpenViews();
        
    }
    
    private void initBackground(){
        Rectangle achtergrond = new  Rectangle(0, 0, 600, 500);
        achtergrond.setFill(Color.GREY);
        
        Image image = new Image(getClass().getResourceAsStream("/roadrace.img/RoadRace.png")); 
        ImageView iv = new ImageView(image);
        iv.setFitHeight(500);
        iv.setFitWidth(600);

        getChildren().add(iv);
    }
    
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
     *
     */
    public void updateVWViews(){
        checkVoorwerpenEnViews();
        for (VoorwerpView vww : vwViews){
            vww.update();
        }
    }
    
    private void checkVoorwerpenEnViews(){
       voorwerpenLijst = model.getVoorwerpenLijst();
       
       if (model.getAantalVoorwerpen()>this.vwViews.size()){
           // Er bestaan voorwerpen waarvoor er nog geen views zijn
           // deze voorwerpen zoeken en aanmaken
           maakView();
       }
       else if(model.getAantalVoorwerpen()<this.vwViews.size()){
           // Er zijn bestaan nog views van voorwerpen, nadat deze voorwerpen verwijderd zijn
           // deze voorwerpenviews zoeken en verwijdenen
           verwijderView();
       }
       
    }
    
    /**
     * Tijdens het spel worden er d.m.v. een thread nieuwe voorwerpen toegevoegd, dus hiervan moeten er nog views aangemaakt worden.
     * 
     */
    private void maakView(){
        System.out.println("Voorwerpviews heeft :" + vwViews.size());
                
        
        boolean resultaat = false;
        while (voorwerpenLijst.hasNext()){
            
            Voorwerp vw = voorwerpenLijst.next();
            if (!this.controleerViewLijst(vw)){
                // Voorwerp heeft geen view -> maak een nieuw view aan van dit voorwerp
                VoorwerpView vww = new VoorwerpView(vw);
                vwViews.add(vww);
                getChildren().add(vww);
            }
            
        }
    }
    private boolean controleerViewLijst(Voorwerp vw){
        boolean result = false;
        for (VoorwerpView vww: vwViews){
                if (vww.getVoorwerp()==vw){
                  result = true;
                   
                }
                                
        }
        return result;
    }
    
    
    
    //////////////////////
    /**
     * Tijdens het spel worden er voorwerpen verwijdererd uit de voorwerpenlijst, bijvoorbeeld nadat de speler een brandstof opneemt.
     * Het is dan zo dat een het voorwerpview van dit voorwerp ook verwijderd moet worden.
     * 
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
    private boolean controleerVoorwerplijst(VoorwerpView vww){
        boolean result = false;
        while (voorwerpenLijst.hasNext()){
            Voorwerp vw = voorwerpenLijst.next();
            if (vww.getVoorwerp()==vw){
                return true;
            }
        }
        return false;

        
    }
    
    
    
    
    
    
    
    
}
