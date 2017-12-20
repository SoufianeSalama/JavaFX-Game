/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roadrace;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

/**
 *
 * @author Soufiane
 */
public class VoorwerpView extends Group{
    private Voorwerp vw;
    private AnchorPane paneel;
    private int VERGROTING;
    
    private Rectangle vwIndicator;
    private Circle vijandIndicator;

    public VoorwerpView(Voorwerp voorwerp) {
        this.vw = voorwerp;
        this.VERGROTING = LevelView.VERGROTING;
        
        paneel = new AnchorPane();
        
        switch (this.vw.getType()){
            case SPELER:
                ImageView ivSpeler = this.tekenSpeler();
                vwIndicator = this.tekenVoorwerpIndicator();
               
                paneel.getChildren().addAll(ivSpeler, vwIndicator);
                break;
                
            case MUUR:
                Rectangle muur = this.tekenMuur();
                paneel.getChildren().addAll(muur);
                break;
            case BRANDSTOF:
                ImageView ivBrandstof = this.tekenBrandstof();
                Rectangle test = this.tekenMuur();
                paneel.getChildren().add(test);
                break;
            case VOERTUIG:
                Rectangle voertuig  = tekenVoertuig();
                if (voorwerp.isVijand()){
                    vijandIndicator = this.tekenVijandIndicator();
                    paneel.getChildren().addAll(voertuig,vijandIndicator);
                }
                else{
                    paneel.getChildren().add(voertuig);
                }
                
                break;    
        }
        
        getChildren().add(paneel);
    }
    
    
    private ImageView tekenSpeler(){
        Image image = new Image(getClass().getResourceAsStream("/roadrace.img/police.png")); // https://www.vexels.com/png-svg/preview/145582/city-car-rear-view
        ImageView iv = new ImageView(image);
        //iv.setRotate(90);
        iv.setFitHeight(vw.getLengteVW()*this.VERGROTING);
        iv.setFitWidth(vw.getBreedteVW()*this.VERGROTING);
        
        return iv;
    }
    
    private Rectangle tekenVoorwerpIndicator(){
        Rectangle vwIndicator = new Rectangle(0, 0, vw.getBreedteVW() * this.VERGROTING, vw.getLengteVW() * this.VERGROTING);
        vwIndicator.setStrokeType(StrokeType.INSIDE);
        vwIndicator.setStrokeWidth(2);
        vwIndicator.setStroke(Color.LIGHTGREEN);
        vwIndicator.setFill(null);
        
        return vwIndicator;
    }
    
    private ImageView tekenBrandstof(){
        Image image = new Image(getClass().getResourceAsStream("/roadrace.img/fuel.png"));
        ImageView iv = new ImageView(image);
        iv.setFitHeight(vw.getLengteVW()*this.VERGROTING);
        iv.setFitWidth(vw.getBreedteVW()*this.VERGROTING);
        
        return iv;
    }
    
    private Rectangle tekenMuur(){
        Rectangle muurView = new Rectangle(0,0,vw.getBreedteVW() * this.VERGROTING ,vw.getLengteVW() * this.VERGROTING);
        muurView.setFill(Color.BLACK);   
        return muurView;
    }
    
    private Rectangle tekenVoertuig(){
        Rectangle tegenliggerView = new Rectangle(0,0,vw.getBreedteVW() * this.VERGROTING, vw.getLengteVW() * this.VERGROTING);
        tegenliggerView.setFill(Color.WHITE);   
        return tegenliggerView;
    }
    
    private Circle tekenVijandIndicator(){
        Circle ic = new Circle(vw.getBreedteVW()*this.VERGROTING/2, vw.getBreedteVW()*this.VERGROTING/2, vw.getBreedteVW()*this.VERGROTING);
        ic.setStrokeType(StrokeType.INSIDE);
        ic.setStrokeWidth(3);
        ic.setStroke(Color.LIGHTGREEN);
        ic.setFill(null);
        
        return ic;
    }
    
    
    public void update(){
        this.paneel.setTranslateX(this.vw.getVoorwerpX() * this.VERGROTING);
        this.paneel.setTranslateY(this.vw.getVoorwerpY() * this.VERGROTING);;
        
        if (this.vw.getType() == VoorwerpType.SPELER){
             if (vw.getTotBeschadigingVW() >=0.00 && vw.getTotBeschadigingVW() <=0.33){
                    this.vwIndicator.setStroke(Color.GREEN);
                }
                else if (vw.getTotBeschadigingVW() >=0.34 && vw.getTotBeschadigingVW() <=0.67){
                    this.vwIndicator.setStroke(Color.ORANGE);
                }
                else if (vw.getTotBeschadigingVW() >=0.68 && vw.getTotBeschadigingVW() <=1){
                    this.vwIndicator.setStroke(Color.RED);
                }
        }
        if (this.vw.getType() == VoorwerpType.VOERTUIG && this.vw.isVijand()){
            if (this.vw.isDood()){
                this.vijandIndicator.setStroke(Color.DARKRED);
            }
        }
    }
    
    public void deleteVW(){
        getChildren().remove(this.vw);
    }
    
    
}
