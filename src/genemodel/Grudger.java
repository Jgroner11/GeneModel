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
import java.util.Iterator;

/**
 *
 * @author jacob
 */
public class Grudger extends Bird{
    private ArrayList<Integer> knownCheaters;

    public Grudger(){
        super();
        knownCheaters = new ArrayList<Integer>();
    }
    
    public Grudger(double startXCenter, double startYCenter, ArrayList<Integer> startKnownCheaters){
        super(startXCenter, startYCenter);
        knownCheaters = new ArrayList<Integer>();
    }
    
    public ArrayList<Integer> getKnownCheaters(){
        return(knownCheaters);
    }
    public void setKnownCheaters(ArrayList<Integer> newKnownCheaters){
        knownCheaters = newKnownCheaters;
    }
    public void addKnownCheater(int knownCheater){
        if(!knownCheaters.contains(new Integer(knownCheater))){
            knownCheaters.add(new Integer(knownCheater));
        }
    }
    public void removeKnownCheater(int knownCheater){
        Iterator itr = knownCheaters.iterator();
        while(itr.hasNext()){
            Integer i = (Integer)itr.next();
            if(i.intValue() == knownCheater){
                itr.remove();
            }
        }
    }
    public boolean knowsCheater(int id){
        for(Integer i : knownCheaters){
            if(i.intValue() == id){
                return(true);
            }
        }
        return(false);
    }
    
    public void draw(Graphics G){
        //(new Circle(super.getXCenter(), super.getYCenter(), Color.GRAY/*!super.getIsDead()?(super.getGivingBirth()?Color.BLUE:Color.WHITE):Color.RED*/, super.getRadius())).draw(G);

        double[][] r = ABirdInFlight.getEndpointMatrix(30);       
        r = ABirdInFlight.multiplyByRotationMatrix(r, super.getDirection());       
        r = ABirdInFlight.scaleAndChangePosition(r, super.getRadius(), super.getXCenter(), super.getYCenter());
        
        int lb = (int)(.14 * r.length);
        int ub = (int)(.24 * r.length);

        G.setColor(super.getIsDead()?Color.RED:super.getGivingBirth()?Color.BLUE:super.getPruning()?Color.ORANGE:Color.WHITE);
        for(int i = 0; i < lb; i++){         
            G.drawLine((int)r[i][0], -1 * (int)r[i][1], (int)r[i][2], -1 * (int)r[i][3]);   
        }
        for(int i = ub + 1; i < r.length; i++){
            G.drawLine((int)r[i][0], -1 * (int)r[i][1], (int)r[i][2], -1 * (int)r[i][3]);
        }
        G.setColor(new Color(0, 229, 255));
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
            if(!super.getPruneBreak() && super.getEnergy() > super.getPruneCOST()){
                for(Bird b : otherBirds){
                    if(!b.getPruning() && !b.getPruneBreak() && b.getEnergy() > b.getPruneCOST()){
                        if(Calculation.distance(super.getXCenter(), super.getYCenter(), b.getXCenter(), b.getYCenter()) < 2 * super.getRadius() + 10){
                            if(!knowsCheater(b.getID())){
                                super.setTargetID(b.getID());
                                super.setPruning(true);
                                int[] a = {1, super.getTargetID()};
                                return(a);
                            }
                        }
                    }
                }
            }
            else {
                if(super.getPruneTimer().checkInterval()){
                    super.setPruneBreak(false);
                    super.getPruneTimer().reset();
                }
            }
        }
        //code for if bird is actively pruning
        else {
            //Code to prevent errors, makes sure the other bird exists
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
            //move bird if the birds aren't close enough
            if(Calculation.distance(super.getXCenter(), super.getYCenter(), targetBird.getXCenter(), targetBird.getYCenter()) > (3/2) * super.getRadius()){
                super.setDirection(Calculation.findDirection(super.getXCenter(), super.getYCenter(), targetBird.getXCenter(), targetBird.getYCenter()));
                super.setXDelta(Math.cos(super.getDirection()));
                super.setYDelta(Math.sin(super.getDirection()));
                super.setXCenter(super.getXCenter() + (super.getSpeed() * .75) * super.getXDelta());
                super.setYCenter(super.getYCenter() + (super.getSpeed() * .75) * super.getYDelta());
            }
            //If close enough, run timer and check if prune is completed, then reset all prune-related areas of this bird and output result
            else {
                if(super.getPruneTimer().checkInterval()){
                    super.getPruneTimer().reset();
                    super.setPruneBreak(true);
                    super.setPruning(false);
                    super.setEnergy(super.getEnergy() - super.getPruneCOST());
                    
                    int[] a = {2, super.getTargetID()};
                    if(targetBird instanceof Cheater){
                        a[0] = 5;
                        addKnownCheater(targetBird.getID());
                                              
                    }
                    else {
                        super.setInfected(false);  
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
        Bird c = new Grudger(super.getXCenter() + super.getXDelta() * 22, super.getYCenter() + super.getYDelta() * 22, knownCheaters);
        c.setTargetRoost(super.getTargetRoost());
        return(c);
    }
}
