/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genemodel;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author jacob
 */
public class HealthBar {
    private double xCenter;
    private double yCenter;
    private Color color;
    private double xLength;
    private double yLength;
    private double borderLength;
    private double healthQuotient; //# between 0 and 1;
    private boolean visible;
    private boolean moreThanMax; //Variable currently not used
    
    public HealthBar(){
        xCenter = 500;
        yCenter = -500;
        healthQuotient = .5;
        color = Color.GREEN;
        xLength = 20;
        yLength = 8;
        borderLength = 2;
        visible = true;
        moreThanMax = false;
    }
    
    public HealthBar(double startXCenter, double startYCenter, double startHealthQuotient, boolean startVisible){
        xCenter = startXCenter;
        yCenter = startYCenter;
        healthQuotient = startHealthQuotient;
        color = Color.GREEN;
        xLength = 20;
        yLength = 6;
        borderLength = 2;
        visible = startVisible;
        moreThanMax = healthQuotient > 1;
    }
    
    public Color getColor(){
         return color;
    }
    public void setColor(Color newColor){
        color = newColor;
    }
    public double getXCenter(){
        return xCenter;
    }
    public void setXCenter(double newXCenter){
        xCenter = newXCenter;
    }
    public double getHealthQuotient(){
        return healthQuotient;
    }
    public void setHealthQuotient(double newHealthQuotient){
        healthQuotient = newHealthQuotient;
    }
    public double getYCenter(){
        return yCenter;
    }
     public void setYCenter (double newYCenter){
        yCenter = newYCenter;
    }
    public double getXLength(){
        return(xLength);
    }
    public void setXLength(double newXLength){
        xLength = newXLength;
    }
    public double getYLength(){
        return(yLength);
    }
    public void setYLength(double newYLength){
        yLength = newYLength;
    }
    public double getBorderLength(){
        return(borderLength);
    }
    public void setBorderLength(double newBorderLength){
        borderLength = newBorderLength;
    }
    public boolean getVisible(){
        return visible;
    }
    public void setVisible(boolean newVisible){
        visible = newVisible;
    }
    public boolean getMoreThanMax(){
        return(moreThanMax);
    }
    public void setMoreThanMax(boolean newMoreThanMax){
        moreThanMax = newMoreThanMax;
    }
    public void draw(Graphics g){
        if(visible){
            double realXLength = xLength - borderLength;
            if(healthQuotient > 1){
                healthQuotient = 1;
            }
            if(healthQuotient > .5){
                color = Color.GREEN;                
            }
            else if(healthQuotient > .25){
                color = Color.YELLOW;
            }
            else{
                color = Color.RED;
            }
            (new Rectangle(xCenter, yCenter, Color.BLACK, xLength, yLength)).draw(g);
            (new Rectangle(xCenter - ((1 - healthQuotient) * .5 * realXLength), yCenter, color, healthQuotient * realXLength, yLength - borderLength)).draw(g);
        }
    }
}
