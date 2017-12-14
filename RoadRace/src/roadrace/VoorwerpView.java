/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roadrace;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Soufiane
 */
public class VoorwerpView extends Group{
    private Voorwerp vw;
    
    private Rectangle vwView;

    public VoorwerpView(Voorwerp voorwerp) {
        this.vw = voorwerp;
        
        //vwView = new Rectangle(0,0,LevelView.GROOTTEPANEEL* 1 , LevelView.GROOTTEPANEEL * vw.getLengteVW());
        vwView = new Rectangle(0,0,35 ,35);
        vwView.setFill(Color.RED);   
        
        getChildren().add(vwView);
        
        update();
    }
    
    public void update(){
        vwView.setTranslateX(vw.getVoorwerpX()*LevelView.GROOTTEPANEEL);
        
        vwView.setTranslateY(vw.getVoorwerpY()*LevelView.GROOTTEPANEEL);
        
    }
    
    
    
}
