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
 * @author CGroner
 */
public class Rectangle extends Figure{
    private double xLength;
    private double yLength;
    
    public Rectangle(){
        super();
        xLength = 0.0;
        yLength = 0.0;
    }
    public Rectangle(double startXCenter, double startYCenter, Color startColor, double startXLength, double startYLength){
        super(startXCenter, startYCenter, startColor);
        xLength = startXLength;
        yLength = startYLength;
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
    public void draw(Graphics g){
        g.setColor(getColor());
        g.fillRect((int)(getXCenter() - (xLength / 2)), (int)((getYCenter() * -1) - (yLength / 2)), (int)xLength, (int)yLength);
    }
    public boolean isTouching(Circle c1){
       if(((c1.getXCenter() > ((getXCenter() - (getXLength() / 2)) - c1.getRadius())) && (c1.getXCenter() < ((getXCenter() + (getXLength() / 2)) + c1.getRadius()))) && ((c1.getYCenter() > ((getYCenter() - (getYLength() / 2)) - c1.getRadius())) && (c1.getYCenter() < ((getYCenter() + (getYLength() / 2)) + c1.getRadius()))))
           return true;
       else
           return false;
    }
}