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
import roadrace.LevelView;
import roadrace.Voorwerp;
import roadrace.Voorwerp;
import roadrace.VoorwerpType;
import roadrace.VoorwerpType;

/**
 * De VoorwerpView klasse wordt gebruikt om voorwerpviews te maken van een voorwerp
 * @author Soufiane
 */
public class VoorwerpView extends Group{
    private Voorwerp vw;
    private AnchorPane paneel;
    private int VERGROTING;
    
    private Rectangle vwIndicator;

    /**
     * De VoorwerView klasse gaat voor elke voorwerp een view aanmaken
     * Afhankelijk van voorwerpType worden er view aangemaakt
     * @param voorwerp Het voorwerp waarvoor een view aangemaakt moet worden
     */
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
    
    /**
     * De methode "tekenSpeler()" maakt een view voor de speler
     * Deze gaat een image inladen en vergroten afhankelijk van de vergroting van de levelView
     * @return imageView de image van de speler
     */
    private ImageView tekenSpeler(){
        Image image = new Image(getClass().getResourceAsStream("/roadrace.img/police.png")); // https://www.vexels.com/png-svg/preview/145582/city-car-rear-view
        ImageView iv = new ImageView(image);
        iv.setFitHeight(vw.getLengteVW()*this.VERGROTING);
        iv.setFitWidth(vw.getBreedteVW()*this.VERGROTING);
        
        return iv;
    }
    
    /**
     * De methode tekenVoorwerpIndicator wordt enkel gebruikt om een rechthoek rond de vijand te tekenen waarmee de speler de beschadiging van hem kan bepalen. (Rood, Oranje en Groen)
     * @return vwIndicator een rechthoekige border
     */
    private Rectangle tekenVoorwerpIndicator(){
        Rectangle vwIndicator = new Rectangle(0, 0, vw.getBreedteVW() * this.VERGROTING, vw.getLengteVW() * this.VERGROTING);
        vwIndicator.setStrokeType(StrokeType.INSIDE);
        vwIndicator.setStrokeWidth(2);
        vwIndicator.setStroke(Color.LIGHTGREEN);
        vwIndicator.setFill(null);
        
        return vwIndicator;
    }
    
    /**
     * De methode "tekenBrandstof()" maakt een view voor het voorwerp brandstof
     * Deze gaat een image inladen en vergroten afhankelijk van de vergroting van de levelView
     * @return imageView de image van de brandstof
     */
    private ImageView tekenBrandstof(){
        Image image = new Image(getClass().getResourceAsStream("/roadrace.img/fuel.png"));
        ImageView iv = new ImageView(image);
        iv.setFitHeight(vw.getLengteVW()*this.VERGROTING);
        iv.setFitWidth(vw.getBreedteVW()*this.VERGROTING);
        
        return iv;
    }
    
    /**
     * De methode "tekenMuur()" maakt een view voor het voorwerp muur
     * Deze gaat een zwarte rechthoek aanmaken
     * @return muurView een rechthoek als view voor voorwerp muur
     */
    private Rectangle tekenMuur(){
        Rectangle muurView = new Rectangle(0,0,vw.getBreedteVW() * this.VERGROTING ,vw.getLengteVW() * this.VERGROTING);
        muurView.setFill(Color.BLACK);   
        return muurView;
    }
    
    /**
     * De methode "tekenVoertuigAuto()" maakt een view voor het voorwerp voertuig
     * Deze gaat een image inladen en vergroten afhankelijk van de vergroting van de levelView
     * @return imageView de image van het voertuig
     */
    private ImageView tekenVoertuigAuto(){
        Image image = new Image(getClass().getResourceAsStream("/roadrace.img/voertuig.png")); // http://www.freeiconspng.com/img/34874
        ImageView iv = new ImageView(image);
        iv.setFitHeight(vw.getLengteVW()*this.VERGROTING);
        iv.setFitWidth(vw.getBreedteVW()*this.VERGROTING);
        return iv;
    }
    
    /**
     * De methode "tekenVoertuigMotor()" maakt een view voor het voorwerp motor
     * Deze gaat een image inladen en vergroten afhankelijk van de vergroting van de levelView
     * @return imageView de image van de motor
     */
    private ImageView tekenVoertuigMotor(){
        Image image = new Image(getClass().getResourceAsStream("/roadrace.img/motor.png")); // https://openclipart.org/detail/203366/racing-bike-top-down-for-games
        ImageView iv = new ImageView(image);
        iv.setFitHeight(vw.getLengteVW()*this.VERGROTING);
        iv.setFitWidth(vw.getBreedteVW()*this.VERGROTING);
        return iv;
    }
    
    /**
     * De methode "tekenVoertuigTruck()" maakt een view voor het voorwerp truck
     * Deze gaat een image inladen en vergroten afhankelijk van de vergroting van de levelView
     * @return imageView de image van de truck
     */
    private ImageView tekenVoertuigTruck(){
        Image image = new Image(getClass().getResourceAsStream("/roadrace.img/truck.png")); // http://www.freeiconspng.com/img/34874
        ImageView iv = new ImageView(image);
        iv.setFitHeight(vw.getLengteVW()*this.VERGROTING);
        iv.setFitWidth(vw.getBreedteVW()*this.VERGROTING);
        return iv;
    }
    
    /**
     * Deze methode "update()" wordt uitgevoerd door methode updateViews() van klasse LevelView
     * Deze gaat de voorwerpen verplaatsten op de view 
     * Ook voor vijand voertuigen wordt de beschadingingsIndicator geupdatet
     */
    public void update(){
        this.paneel.setTranslateX(this.vw.getVoorwerpX() * this.VERGROTING);
        this.paneel.setTranslateY(this.vw.getVoorwerpY() * this.VERGROTING);
        
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
    }
    
    /**
     * Deze getter geeft de voorwerp van deze voorwerpView terug
     * @return vw VoorwerpView
     */
    public Voorwerp getVoorwerp(){
        return this.vw;
    }
    
    
}
