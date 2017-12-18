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
import javafx.scene.shape.StrokeType;

/**
 *
 * @author Soufiane
 */
public class VoorwerpViewOLD extends Group{
    private Voorwerp vw;
    private Rectangle voorwerpVW;
    private Circle vijandIndicator;
    private int GROOTTE;

    public VoorwerpViewOLD(Voorwerp voorwerp) {
        this.vw = voorwerp;
        this.GROOTTE = LevelView.VERGROTING;
        
        voorwerpVW = new Rectangle();
        
        
        switch (this.vw.getType()){
            case SPELER:
                voorwerpVW = tekenSpeler();
                getChildren().add(voorwerpVW);
                break;
            case VOERTUIG:
                voorwerpVW = tekenVoertuig();
                if (voorwerp.isVijand()){
                    vijandIndicator = tekenIndicator();
                    vijandIndicator.translateXProperty().bind(voorwerpVW.translateXProperty());
                    vijandIndicator.translateYProperty().bind(voorwerpVW.translateYProperty());
                    getChildren().addAll(voorwerpVW,vijandIndicator);
                }
                else{
                    getChildren().add(voorwerpVW);
                }
                
                break;
            case MUUR:
                voorwerpVW = tekenMuur();
                getChildren().add(voorwerpVW);
                break;    
            case BRANDSTOF:
                voorwerpVW = tekenBrandstof();
                getChildren().add(voorwerpVW);
                break;   
        }
        
        
        //getChildren().add(voorwerpVW);
        update();
    }
    
    public void update(){
        this.voorwerpVW.setTranslateX(this.vw.getVoorwerpX() * this.GROOTTE);
        this.voorwerpVW.setTranslateY(this.vw.getVoorwerpY() * this.GROOTTE);
        
        if (this.vw.getType() == VoorwerpType.SPELER || this.vw.getType() == VoorwerpType.VOERTUIG){
             if (vw.getTotBeschadigingVW() >=0.00 && vw.getTotBeschadigingVW() <=0.33){
                    this.voorwerpVW.setFill(Color.GREEN);
                }
                else if (vw.getTotBeschadigingVW() >=0.34 && vw.getTotBeschadigingVW() <=0.67){
                    this.voorwerpVW.setFill(Color.ORANGE);
                }
                else if (vw.getTotBeschadigingVW() >=0.68 && vw.getTotBeschadigingVW() <=1){
                    this.voorwerpVW.setFill(Color.RED);
                }
        }
        if (this.vw.getType() == VoorwerpType.VOERTUIG && this.vw.isVijand()){
            if (this.vw.isDood()){
                this.vijandIndicator.setStroke(Color.DARKRED);
            }
        }
        if (this.vw.getType()==VoorwerpType.BRANDSTOF && this.vw.isDood()){
            getChildren().remove(vw);
        }
        
    }
    
    private Rectangle tekenSpeler(){
        Rectangle spelerView = new Rectangle(0,0, vw.getBreedteVW()*GROOTTE ,vw.getLengteVW()*GROOTTE);
        return spelerView;
    }
    
    private Rectangle tekenBrandstof(){
        Rectangle brandstofView = new Rectangle(0, 0, vw.getBreedteVW()*GROOTTE, vw.getLengteVW()*GROOTTE);
        return brandstofView;        
    }
    
    private Rectangle tekenVoertuig(){
        Rectangle tegenliggerView = new Rectangle(0,0,GROOTTE * vw.getBreedteVW() , GROOTTE * vw.getLengteVW());
        tegenliggerView.setFill(Color.WHITE);   
        return tegenliggerView;
    }
    
    private Rectangle tekenMuur(){
        Rectangle muurView = new Rectangle(0,0,GROOTTE * vw.getBreedteVW() , GROOTTE * vw.getLengteVW());
        muurView.setFill(Color.DARKGREY);   
        return muurView;
    }
    
    private Circle tekenIndicator(){
        Circle ic = new Circle(vw.getBreedteVW()*GROOTTE/2, vw.getBreedteVW()*GROOTTE/2, vw.getBreedteVW()*GROOTTE);
        ic.setStrokeType(StrokeType.INSIDE);
        ic.setStrokeWidth(3);
        ic.setStroke(Color.LIGHTGREEN);
        ic.setFill(null);
        return ic;
    }
    
   
    
    
}
