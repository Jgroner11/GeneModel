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
 * @author Jacob Groner
 */
public class Circle extends Figure {
    private double radius;
    private double diameter;
    
    public Circle(){
        super();
        radius = 0;
        diameter = radius * 2;
    }
    public Circle (double startXCenter, double startYCenter, Color startColor, double startRadius){
        super(startXCenter, startYCenter, startColor);
        radius = startRadius;
        diameter = radius * 2;  
    }
    public double getRadius (){
        return radius;
    }
    public void setRadius(double newRadius){
        radius = newRadius;
        diameter = radius * 2;
    }
    public void draw(Graphics g){
        g.setColor(getColor());
        g.fillOval((int)(getXCenter() - radius), ((int)((getYCenter() * -1) - radius)), (int)diameter, (int)diameter);
    }
    public boolean isTouching(Circle c1){
        if(Calculation.distance(c1.getXCenter(), c1.getYCenter(), getXCenter(), getYCenter()) < c1.getRadius() + getRadius())
            return(true);
        else
            return(false);
    }
}
