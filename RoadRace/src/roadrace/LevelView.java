/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roadrace;


import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Soufiane
 */
public class LevelView extends Region{
    public final static int GROOTTEPANEEL = 5;
    private Level model;
    private ArrayList<VoorwerpView> vwViews;
    
    
    public LevelView(Level model) {
        this.model = model;
        
        this.initBackground();
        this.addVoorwerpenViews();
    }
    
    private void initBackground(){
        Rectangle achtergrond = new  Rectangle(0, 0, 600, 500);
        achtergrond.setFill(Color.GREENYELLOW);
        
        getChildren().add(achtergrond);
    }
    
    private void addVoorwerpenViews(){
        
        vwViews = new ArrayList<>();
        Iterator<Voorwerp> voorwerpenLijst = model.getVoorwerpenLijst();
        
        while (voorwerpenLijst.hasNext()){
            Voorwerp vw = voorwerpenLijst.next();
            vwViews.add(new VoorwerpView(vw));
        }
        
        getChildren().addAll(vwViews);
        
        updateVWViews();
        
        
    }
    
    public void updateVWViews(){
        for (VoorwerpView vww : vwViews){
            vww.update();
        }
    }
    
    
    
}
