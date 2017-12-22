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

    public VoorwerpView(Voorwerp voorwerp) {
        this.vw = voorwerp;
        this.VERGROTING = LevelView.VERGROTING;
        
        paneel = new AnchorPane();
        
        switch (this.vw.getType()){
            case SPELER:
                ImageView ivSpeler = this.tekenSpeler();
                //vwIndicator = this.tekenVoorwerpIndicator();
                paneel.getChildren().addAll(ivSpeler);
                break;
                
            case MUUR:
                Rectangle muur = this.tekenMuur();
                paneel.getChildren().addAll(muur);
                break;
            case BRANDSTOF:
                ImageView ivBrandstof = this.tekenBrandstof();
                paneel.getChildren().add(ivBrandstof);
                break;
            case VOERTUIG:
                ImageView voertuig = null;
                switch (this.vw.getVoertuigType()){
                    case AUTO:
                        voertuig = tekenVoertuigAuto();
                        break;
                    case TRUCK:
                        voertuig = tekenVoertuigTruck();
                        break;
                    case MOTOR:
                        voertuig = tekenVoertuigMotor();
                        break;
                }
                
                if (voorwerp.isVijand()){
                    vwIndicator = this.tekenVoorwerpIndicator();
                    paneel.getChildren().addAll(voertuig,vwIndicator);
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
    
    private ImageView tekenVoertuigAuto(){
        Rectangle tegenliggerView = new Rectangle(0,0,vw.getBreedteVW() * this.VERGROTING, vw.getLengteVW() * this.VERGROTING);
        tegenliggerView.setFill(Color.WHITE);   
        
        Image image = new Image(getClass().getResourceAsStream("/roadrace.img/voertuig.png")); // http://www.freeiconspng.com/img/34874
        ImageView iv = new ImageView(image);
        //iv.setRotate(90);
        iv.setFitHeight(vw.getLengteVW()*this.VERGROTING);
        iv.setFitWidth(vw.getBreedteVW()*this.VERGROTING);
        
        return iv;
    }
    
    private ImageView tekenVoertuigMotor(){
        Rectangle tegenliggerView = new Rectangle(0,0,vw.getBreedteVW() * this.VERGROTING, vw.getLengteVW() * this.VERGROTING);
        tegenliggerView.setFill(Color.WHITE);   
        
        Image image = new Image(getClass().getResourceAsStream("/roadrace.img/motor.png")); // https://openclipart.org/detail/203366/racing-bike-top-down-for-games
        ImageView iv = new ImageView(image);
        //iv.setRotate(90);
        iv.setFitHeight(vw.getLengteVW()*this.VERGROTING);
        iv.setFitWidth(vw.getBreedteVW()*this.VERGROTING);
        
        return iv;
    }
    
    private ImageView tekenVoertuigTruck(){
        Rectangle tegenliggerView = new Rectangle(0,0,vw.getBreedteVW() * this.VERGROTING, vw.getLengteVW() * this.VERGROTING);
        tegenliggerView.setFill(Color.WHITE);   
        
        Image image = new Image(getClass().getResourceAsStream("/roadrace.img/truck.png")); // http://www.freeiconspng.com/img/34874
        ImageView iv = new ImageView(image);
        //iv.setRotate(90);
        iv.setFitHeight(vw.getLengteVW()*this.VERGROTING);
        iv.setFitWidth(vw.getBreedteVW()*this.VERGROTING);
        
        return iv;
    }
    
  
    public void update(){
        this.paneel.setTranslateX(this.vw.getVoorwerpX() * this.VERGROTING);
        this.paneel.setTranslateY(this.vw.getVoorwerpY() * this.VERGROTING);;
        
        if (this.vw.getType() == VoorwerpType.VOERTUIG && this.vw.isVijand()){
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
        
        if (this.vw.getType() == VoorwerpType.BRANDSTOF && this.vw.isDood()){
            deleteVW();
        }
    }
    
    private void deleteVW(){
        getChildren().remove(this.vw);
    }
    
    
}
