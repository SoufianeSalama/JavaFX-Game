/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roadrace;

import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Soufiane
 */
public class VoorwerpView extends Group{
    private Voorwerp vw;
    
    private AnchorPane vwView;

    public VoorwerpView(Voorwerp voorwerp) {
        this.vw = voorwerp;
        vwView  = new AnchorPane();
        
        switch (this.vw.getType()){
            case SPELER:
                Rectangle spelerView = this.tekenSpeler();
                vwView.getChildren().add(spelerView);
                break;
                
            case BRANDSTOF:
                //Circle brandstofView = this.tekenBrandstof();
                Rectangle brandstofView = this.tekenBrandstof();
                vwView.getChildren().add(brandstofView);
                break;
            
            case TEGENLIGGER:
                Rectangle tegenliggerView = this.tekenTegenligger();
                vwView.getChildren().add(tegenliggerView);
                break;
                
            case MUUR:
                Rectangle muurView = this.tekenMuur();
                vwView.getChildren().add(muurView);
                break;
        }
        
        getChildren().add(vwView);
        
        update();
    }
    
    public void update(){
        vwView.setTranslateX(vw.getVoorwerpX()*LevelView.VERGROTING);
        
        vwView.setTranslateY(vw.getVoorwerpY()*LevelView.VERGROTING);
        
    }
    
    private Rectangle tekenSpeler(){
        Rectangle spelerView = new Rectangle(0,0,(LevelView.VERGROTING * vw.getLengteVW()) , (LevelView.VERGROTING * vw.getLengteVW()));
        //vwView = new Rectangle(0,0,35 ,35);
        spelerView.setFill(Color.RED);   
        return spelerView;
    }
    
    private Rectangle tekenBrandstof(){
        /*Circle brandstofView = new Circle(LevelView.VERGROTING * vw.getLengteVW()/2, Color.YELLOW);
        return brandstofView;*/
        
        Rectangle spelerView = new Rectangle(0,0,(LevelView.VERGROTING * vw.getLengteVW()) , (LevelView.VERGROTING * vw.getLengteVW()));
        //vwView = new Rectangle(0,0,35 ,35);
        spelerView.setFill(Color.YELLOW);   
        return spelerView;
        
    }
    
    private Rectangle tekenTegenligger(){
        Rectangle spelerView = new Rectangle(0,0,(LevelView.VERGROTING * vw.getLengteVW()) , (LevelView.VERGROTING * vw.getLengteVW()));
        //vwView = new Rectangle(0,0,35 ,35);
        spelerView.setFill(Color.BLUE);   
        return spelerView;
    }
    
    private Rectangle tekenMuur(){
        Rectangle muurView = new Rectangle(0, 0, LevelView.VERGROTING * vw.getBreedteVW(), LevelView.VERGROTING * vw.getLengteVW());
        muurView.setFill(Color.DARKGRAY);
        return muurView;
    }
    
    public void removeVoorwerpView(Voorwerp vw){
        vwView.getChildren().remove(vw);
    }
    
    
}
