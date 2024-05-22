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
public class Roost {
    private double xCenter;
    private double yCenter;
    private double radius;
    
    public Roost(){
        xCenter = 500;
        yCenter = -500;
        radius = 100;
    }
    
    public Roost(double startXCenter, double startYCenter){
        xCenter = startXCenter;
        yCenter = startYCenter;
        radius = 100;
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
    public double getRadius(){
        return(radius);
    }
    public void setRadius(double newRadius){
        radius = newRadius;
    }
    
    public void draw(Graphics G){
        (new Circle(xCenter, yCenter, Color.DARK_GRAY, radius)).draw(G);
    }
}
