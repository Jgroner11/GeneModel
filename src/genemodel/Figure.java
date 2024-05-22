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
public abstract class Figure {
     private double xCenter;
     private double yCenter;
     private Color color;
     public Figure (){
         xCenter = 0.0;
         yCenter = 0.0;
         color = Color.BLACK;
     }
     public Figure (double startXCenter, double startYCenter, Color startColor){
         xCenter = startXCenter;
         yCenter = startYCenter;
         color = startColor;
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
     public double getYCenter(){
         return yCenter;
     }
     public void setYCenter (double newYCenter){
         yCenter = newYCenter;
     }
     public abstract void draw(Graphics g);
     public abstract boolean isTouching(Circle c1);
}
