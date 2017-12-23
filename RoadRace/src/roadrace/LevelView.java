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

/**
 *
 * @author Soufiane
 */
public class LevelView extends Region{
    public final static int VERGROTING = 25;
    private Level model;
    private ArrayList<VoorwerpView> vwViews;
    
    
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
    
    public void updateVWViews(){
        test();
        for (VoorwerpView vww : vwViews){
            vww.update();
        }
    }
    
    /**
     * 1e reden: Tijdens het spel worden er voorwerpen verwijdererd uit de voorwerpenlijst, bijvoorbeeld nadat de speler een brandstof opneemt.
     * Het is dan zo dat een het voorwerpview van dit voorwerp ook verwijderd moet worden.
     * 
     * 2de reden: Tijdens het spel worden er ook d.m.v. een thread nieuwe voorwerpen toegevoegd, dus hiervan moeten er nog views aangemaakt worden.
     * 
     */
    private void test(){
        Iterator<Voorwerp> voorwerpenLijst = model.getVoorwerpenLijst();
        boolean resultaat = false;
        while (voorwerpenLijst.hasNext()){
            
            Voorwerp vw = voorwerpenLijst.next();
            if (this.controleer(vw)){
                // Voorwerp heeft een view
            }
            else
            {
                // Voorwerp heeft geen view
                vwViews.add(new VoorwerpView(vw));
            }
            
                     
        }
        
       
    }
    
    private boolean controleer(Voorwerp vw){
        for (VoorwerpView vww: vwViews){
                if (vww.getVoorwerp()==vw){
                   return true;
                   
                }
                                
        }
        return false;
    }
    
    
    
    
    
    
    
}
