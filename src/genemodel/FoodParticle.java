/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genemodel;

import java.awt.Graphics;
import java.awt.Color;
/**
 *
 * @author jacob
 */
public class FoodParticle {
    public final Color healthyColor = Color.YELLOW;
    public final Color infectedColor = Color.GREEN;
    public final double foodSize = 3; //radius
    private double xCenter;
    private double yCenter;
    private boolean infected;
    private Color color;
    
    public FoodParticle(){
        xCenter = 500;
        yCenter = -500;
        infected = false;
        color = healthyColor;
    }
    
    public FoodParticle(double startXCenter, double startYCenter, boolean startInfected){
        xCenter = startXCenter;
        yCenter = startYCenter;
        infected = startInfected;
        color = infected?infectedColor:healthyColor;
    }
    
    public double getXCenter(){
        return(xCenter);
    }
    public void setXCenter(double newXCenter){
        xCenter = newXCenter;
    }
    public double getYCenter(){
        return(yCenter);
    }
    public void setYCenter(double newYCenter){
        yCenter = newYCenter;
    }
    public boolean getInfected(){
        return(infected);
    }
    public void setInfected(boolean newInfected){
        infected = newInfected;
        color = infected?infectedColor:healthyColor;
    }
    public Color getColor(){
        return(color);
    }
    public void setColor(Color newColor){
        color = newColor;
    }
    public void draw(Graphics g) {
        (new Circle(xCenter, yCenter, color, foodSize)).draw(g);
    }
}
