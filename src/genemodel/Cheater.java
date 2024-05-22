/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genemodel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author jacob
 */
public class Cheater extends Bird{
    public Cheater(){
        super();
    }
    public Cheater(double startXCenter, double startYCenter){
        super(startXCenter, startYCenter);
    }
    public void draw(Graphics G){
        //(new Circle(super.getXCenter(), super.getYCenter(), Color.GRAY/*!super.getIsDead()?(super.getGivingBirth()?Color.BLUE:Color.WHITE):Color.RED*/, super.getRadius())).draw(G);

        double[][] r = ABirdInFlight.getEndpointMatrix(30);       
        r = ABirdInFlight.multiplyByRotationMatrix(r, super.getDirection());       
        r = ABirdInFlight.scaleAndChangePosition(r, super.getRadius(), super.getXCenter(), super.getYCenter());
        
        int lb = (int)(.14 * r.length);
        int ub = (int)(.24 * r.length);

        G.setColor(super.getIsDead()?Color.RED:super.getGivingBirth()?Color.BLUE:Color.WHITE);
        for(int i = 0; i < lb; i++){         
            G.drawLine((int)r[i][0], -1 * (int)r[i][1], (int)r[i][2], -1 * (int)r[i][3]);   
        }
        for(int i = ub + 1; i < r.length; i++){
            G.drawLine((int)r[i][0], -1 * (int)r[i][1], (int)r[i][2], -1 * (int)r[i][3]);
        }
        G.setColor(Color.MAGENTA);
        for(int i = lb; i <= ub; i++){
            G.drawLine((int)r[i][0], -1 * (int)r[i][1], (int)r[i][2], -1 * (int)r[i][3]);
        }

        if(super.getInfected()){
            (new Circle(super.getXCenter(), super.getYCenter(), Color.RED, 5)).draw(G);
        }
        (new HealthBar(super.getXCenter(), super.getYCenter() + super.getRadius() + 6, super.getEnergy() / super.getMaxENERGY(), super.getHealthBarVisible())).draw(G);
        G.setColor(Color.BLACK);
        G.setFont(new Font("TimesRoman", Font.BOLD, 15));//Arial Bold Italic         
        G.drawString(Integer.toString(super.getID()), (int)super.getXCenter() - 5, -1 * (int)super.getYCenter() + 5);
    }
    
    public int[] prune(ArrayList<Bird> otherBirds, Roost roost){
        if(super.getPruning() == false){
            //Code to move bird around roost
            if(super.getMovementTimer2().checkInterval()){
                    super.setDirection(Math.random() * (2 * Math.PI)); 
                    super.getMovementTimer2().reset();
            }
            if(Calculation.distance(super.getXCenter(), super.getYCenter(), super.getTargetRoost().getXCenter(), super.getTargetRoost().getYCenter()) >= super.getTargetRoost().getRadius() - super.getRadius()){
                boolean realigned = false;
                while(!realigned){
                    super.setDirection(Math.random() * (2 * Math.PI));
                    super.setXDelta(Math.cos(super.getDirection()));
                    super.setYDelta(Math.sin(super.getDirection()));
                    if(Calculation.distance(super.getXCenter() + super.getXDelta(), super.getYCenter() + super.getYDelta(), super.getTargetRoost().getXCenter(), super.getTargetRoost().getYCenter()) < Calculation.distance(super.getXCenter(), super.getYCenter(), super.getTargetRoost().getXCenter(), super.getTargetRoost().getYCenter())){
                        realigned = true;
                        super.getMovementTimer().reset();
                    }     
                }
            }
            super.setXDelta(Math.cos(super.getDirection()));
            super.setYDelta(Math.sin(super.getDirection()));
            super.setXCenter(super.getXCenter() + (super.getSpeed() * .75) * super.getXDelta());
            super.setYCenter(super.getYCenter() + (super.getSpeed() * .75) * super.getYDelta());
            
            //Code to check if another viable bird is ready to be pruned
            if(super.getPruneBreak()){
                if(super.getPruneTimer().checkInterval()){
                    super.setPruneBreak(false);
                    super.getPruneTimer().reset();
                }
            }
        }
        else {
            Bird targetBird = null;
            for(Bird b : otherBirds){
                if(b.getID() == super.getTargetID()){
                    targetBird = b;
                }
            }
            if(targetBird == null){
                super.setPruning(false);
                int[] a = {-1};
                return(a);
            }
            if(!(Calculation.distance(super.getXCenter(), super.getYCenter(), targetBird.getXCenter(), targetBird.getYCenter()) > (3/2) * super.getRadius())){
                if(super.getPruneTimer().checkInterval()){
                    super.getPruneTimer().reset();
                    super.setPruneBreak(true);
                    super.setPruning(false);
                    super.setInfected(false);
                    int[] a = {3, super.getTargetID()};
                    if(targetBird instanceof Grudger){
                        a[0] = 4;
                    }
                    super.setTargetID(-1);
                    return(a);
                }
            }
            
        }
        int[] a = {-1};
        return(a);
    }
    public Bird createChick(){
        Bird c = new Cheater(super.getXCenter() + super.getXDelta() * 22, super.getYCenter() + super.getYDelta() * 22);
        c.setTargetRoost(super.getTargetRoost());
        return(c);
    }
}
