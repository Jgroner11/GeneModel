/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genemodel;

/**
 *
 * @author CGroner
 */
public class Calculation {
    public static double distance(double x1, double y1, double x2, double y2){
        return(Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2))));
    }
    public static double findDirection(double x1, double y1, double x2, double y2){
        double direction;
        double d = Calculation.distance(x1, y1, x2, y2);
        double xDelta = (x2 - x1) / d;
        double yDelta = (y2 - y1) / d;
        direction = Math.asin(yDelta);
        if(xDelta < 0){
            direction = Math.PI - direction;
        }
        return(direction);
    }
}
