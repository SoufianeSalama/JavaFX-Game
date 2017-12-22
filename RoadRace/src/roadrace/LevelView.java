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
    
    private void test(){
       Iterator<Voorwerp> voorwerpenLijst = model.getVoorwerpenLijst();
        
        while (voorwerpenLijst.hasNext()){
            Voorwerp vw = voorwerpenLijst.next();
            
            
            if (vwViews.contains(new VoorwerpView(vw))){
                System.out.println("Voorwerp: " + vw.getType() + " staat al in de VoorwerpenViews lijst");
            }
//            else{
//                vwViews.add(new VoorwerpView(vw));
//            }
        }
        
    }
    
    
    
    
    
    
    
}
