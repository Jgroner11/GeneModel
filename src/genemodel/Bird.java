/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genemodel;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author jacob
 */
public abstract class Bird {
    private final double pruneCOST = 5.0;
    private final double maxENERGY = 100.0;
    private final double declineRATE = 2.0;
    private final double infectedCONST1 = 1.5;
    private final double infectedCONST2 = .2;

    private double xCenter;
    private double yCenter;
    private double radius;
    private double energy;
    private boolean infected;
    private double direction;
    private double xDelta;
    private double yDelta;
    private double speed;
    private Timer movementTimer;
    private Timer movementTimer2;
    private int moveStage;
    private FoodParticle targetFood;
    private double vision;
    private Timer energyTimer;
    private boolean healthBarVisible;
    private Roost targetRoost;
    private double flyOutDist;
    private boolean firstRun;
    private boolean flying;
    private boolean isDead;
    private Timer deathTimer;
    private double infectedDuration;
    private boolean givingBirth;
    private int ID;
    private boolean pruning;
    private int targetID;
    private Timer pruneTimer;
    private boolean pruneBreak;
    private boolean isChick;
    
    public Bird(){
        xCenter = 500;
        yCenter = -500;
        infected = false;
        energy = 50;
        radius = 10;
        speed = 2;        
        movementTimer = new Timer(60);
        moveStage = 0;
        vision = radius + 40;
        energyTimer = new Timer(60);
        healthBarVisible = true;
        firstRun = true;
        flying = true;
        isDead = false;
        deathTimer = new Timer(2 * 60);
        infectedDuration = 0;
        givingBirth = false;
        movementTimer2 = new Timer(30);
        pruning = false;
        targetID = -1;
        pruneTimer = new Timer(2*60);
        pruneBreak = false;
        isChick = true;
    }
    public Bird(double startXCenter, double startYCenter){
        xCenter = startXCenter;
        yCenter = startYCenter;
        infected = false;
        energy = 50;
        radius = 10;
        speed = 2;        
        movementTimer = new Timer(60);
        moveStage = 0;
        vision = radius + 40;
        energyTimer = new Timer(60);
        healthBarVisible = true;
        firstRun = true;
        flying = true;
        isDead = false;
        deathTimer = new Timer(2 * 60);
        infectedDuration = 0;
        givingBirth = false;
        movementTimer2 = new Timer(30);
        pruning = false;
        targetID = -1;
        pruneTimer = new Timer(2 * 60);
        pruneBreak = false;
        isChick = true;
    }
    
    public double getPruneCOST(){
        return pruneCOST;
    }
        
    public double getMaxENERGY(){
        return(maxENERGY);
    }
    public double getInfectedCONST1(){
        return(infectedCONST1);
    }
    public double getInfectedCONST2(){
        return(infectedCONST2);
    }
    public double getDeclineRATE(){
        return(declineRATE);
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
    public double getEnergy(){
        return(energy);
    }
    public void setEnergy(double newEnergy){
        energy = newEnergy;
    }
    public boolean getInfected(){
        return(infected);
    }
    public void setInfected(boolean newInfected){
        infected = newInfected;
    }
    public double getDirection(){
        return(direction);
    }
    public void setDirection(double newDirection){
        direction = newDirection;
    }
    public double getXDelta(){
        return xDelta;
    }
    public void setXDelta(double newXDelta){
        xDelta = newXDelta;
    }
    public double getYDelta(){
        return yDelta;
    }
    public void setYDelta(double newYDelta){
        yDelta = newYDelta;
    }
    public double getSpeed(){
        return(speed);
    }
    public void setSpeed(double newSpeed){
        speed = newSpeed;
    }
    public Timer getMovementTimer(){
        return(movementTimer);
    }
    public void setMovementTimer(Timer newMovementTimer){
        movementTimer = newMovementTimer;
    }
    public Timer getMovementTimer2(){
        return(movementTimer2);
    }
    public void setMovementTimer2(Timer newMovementTimer2){
        movementTimer2 = newMovementTimer2;
    }
    public int getMoveStage(){
        return(moveStage);
    }
    public void setMoveStage(int newMoveStage){
        moveStage = newMoveStage;
    }
    public FoodParticle getTargetFood(){
        return(targetFood);
    }
    public void setTargetFood(FoodParticle newTargetFood){
        targetFood = newTargetFood;
    }
    public double getVision(){
        return(vision);
    }
    public void setVision(double newVision){
        vision = newVision;
    }
    public boolean getHealthBarVisible(){
        return healthBarVisible;
    }
    public void setHealthBarVisible(boolean newHealthBarVisible){
        healthBarVisible = newHealthBarVisible;
    }
    public Roost getTargetRoost(){
        return targetRoost;
    }
    public void setTargetRoost(Roost newTargetRoost){
        targetRoost = newTargetRoost;
    }
    public double getFlyOutDist(){
        return(flyOutDist);
    }
    public void setFlyOutDist(double newFlyOutDist){
        flyOutDist = newFlyOutDist;
    }
    public boolean getFirstRun(){
        return(firstRun);
    }
    public void setFirstRun(boolean newFirstRun){
        firstRun = newFirstRun;
    }
    public boolean getFlying(){
        return(flying);
    }
    public void setFlying(boolean newFlying){
        flying = newFlying;
    }
    public boolean getIsDead(){
        return(isDead);
    }
    public void setIsDead(boolean newIsDead){
        isDead = newIsDead;
    }
    public Timer getDeathTimer(){
        return(deathTimer);
    }
    public void setDeathTimer(Timer newDeathTimer){
        deathTimer = newDeathTimer;
    }
    public double getInfectedDuration(){
        return infectedDuration;
    }
    public void setInfectedDuration(double newInfectedDuration){
        infectedDuration = newInfectedDuration;
    }
    public boolean getGivingBirth(){
        return givingBirth;
    }
    public void setGivingBirth(boolean newGivingBirth){
        givingBirth = newGivingBirth;
    }
    public int getID(){
        return ID;
    }
    public void setID(int newID){
        ID = newID;
    }
    
    public void setID(ArrayList<Bird> otherBirds){
        boolean complete = false;
        int testID = 1;
        while(!complete){
            boolean IDFound = false;
            for(Bird b : otherBirds){
                if(b.getID() == testID){
                    IDFound = true;
                }      
            }
            if(IDFound){
                testID++;
            }
            else {
                ID = testID;
                complete = true;
            }
        }
    }
    
    public boolean getPruning(){
        return pruning;
    }
    public void setPruning(boolean newPruning){
        pruning = newPruning;
    }
    public int getTargetID(){
        return targetID;
    }
    public void setTargetID(int newTargetID){
        targetID = newTargetID;
    }
    public Timer getPruneTimer(){
        return pruneTimer;
    }
    public void setPruneTimer(Timer newPruneTimer){
        pruneTimer = newPruneTimer;
    }
    public boolean getPruneBreak(){
        return pruneBreak;
    }
    public void setPruneBreak(boolean newPruneBreak){
        pruneBreak = newPruneBreak;
    }
    public boolean getIsChick(){
        return(isChick);
    }
    public void setIsChick(boolean newIsChick){
        isChick = newIsChick;
    }
    
    
    public ArrayList<FoodParticle> forage(ArrayList<FoodParticle> allFood, double frameWidth, double frameHeight, ArrayList<Roost> allRoosts){
        if(moveStage == 0){
            if(movementTimer.checkInterval()){
                direction = Math.random() * (2 * Math.PI); 
                movementTimer.reset();
            }
            if(xCenter - radius < 0){
                boolean realigned = false;
                while(!realigned){
                    direction = Math.random() * (2 * Math.PI);
                    xDelta = Math.cos(direction);
                    if(xCenter < xCenter + xDelta){
                        realigned = true;
                        movementTimer.reset();
                    }     
                }
            }
            else if(xCenter + radius > frameWidth){
                boolean realigned = false;
                while(!realigned){
                    direction = Math.random() * (2 * Math.PI);
                    xDelta = Math.cos(direction);
                    if(xCenter > xCenter + xDelta){
                        realigned = true;
                        movementTimer.reset();
                    }     
                }
            }
            else if(yCenter + radius > 0){
                boolean realigned = false;
                while(!realigned){
                    direction = Math.random() * (2 * Math.PI);
                    yDelta = Math.sin(direction);
                    if(yCenter > yCenter + yDelta){
                        realigned = true;
                        movementTimer.reset();
                    }     
                }
            }
            else if(yCenter - radius < -1 * frameHeight){
                boolean realigned = false;
                while(!realigned){
                    direction = Math.random() * (2 * Math.PI);
                    yDelta = Math.sin(direction);
                    if(yCenter < yCenter + yDelta){
                        realigned = true;
                        movementTimer.reset();
                    }     
                }    
            }
            else {
               for (Roost r : allRoosts){
                   if(Calculation.distance(xCenter, yCenter, r.getXCenter(), r.getYCenter()) < radius + r.getRadius()){
                        boolean realigned = false;
                        while(!realigned){
                            direction = Math.random() * (2 * Math.PI);
                            xDelta = Math.cos(direction);
                            yDelta = Math.sin(direction);
                            if(Calculation.distance(xCenter, yCenter, r.getXCenter(), r.getYCenter()) < Calculation.distance(xCenter + xDelta, yCenter + yDelta, r.getXCenter(), r.getYCenter())){
                                realigned = true;
                                movementTimer.reset();
                            }     
                        }       
                   }
               } 
            }
            for (FoodParticle f : allFood){
                double d = Calculation.distance(xCenter, yCenter, f.getXCenter(), f.getYCenter());
                if(d < vision){
                    targetFood = f;
                    moveStage = 1;
                }
            }
            
        }
        else if (moveStage == 1){
            if(Calculation.distance(xCenter, yCenter, targetFood.getXCenter(), targetFood.getYCenter()) < radius){
                Iterator itr = allFood.iterator();
                while (itr.hasNext()){
                    FoodParticle f = (FoodParticle)itr.next();
                    if(f == targetFood){
                        if(targetFood.getInfected()){
                            infected = true;
                        }
                        energy += 20;
                        itr.remove();
                    }
                }                
                moveStage = 0;
            }
            else if(Calculation.distance(xCenter, yCenter, targetFood.getXCenter(), targetFood.getYCenter()) < vision){
                direction = Calculation.findDirection(xCenter, yCenter, targetFood.getXCenter(), targetFood.getYCenter());
            }
            else {
                moveStage = 0;
            }
        }
        
        xDelta = Math.cos(direction);
        yDelta = Math.sin(direction);
        xCenter += speed * xDelta;
        yCenter += speed * yDelta;
        
        return(allFood);
    }
    
    public ArrayList<FoodParticle> forage(ArrayList<Bird> otherBirds, ArrayList<FoodParticle> allFood, ArrayList<Roost> allRoosts, double frameWidth, double frameHeight){ 
        return(allFood);
    }
    
    public void consumeEnergy(ArrayList<Roost> allRoosts){
        //also birth check
        if(energyTimer.checkInterval()){
            if(!infected){
                energy -= declineRATE;
                infectedDuration = 0;
            }
            else if(infected){
                energy -= infectedCONST1 * declineRATE + infectedCONST2 * infectedDuration;
                infectedDuration += 1;
            }
            if(!givingBirth){
                if(energy >= maxENERGY){
                    givingBirth = true;
                    findTargetRoost(allRoosts);
                    firstRun = true;
                    flying = true;
                }
            }
            if(energy <= 0){
                isDead = true;
            }
  
            energyTimer.reset();
            
        }
    }
    
    public boolean decay(){
        
        if(isDead){
            return(deathTimer.checkInterval());
        }
        return(false);
    }
    
    public void findTargetRoost(ArrayList<Roost> allRoosts){
        targetRoost = allRoosts.get(0);
        for (Roost r : allRoosts){
            if(Calculation.distance(r.getXCenter(), r.getYCenter(), xCenter, yCenter) < Calculation.distance(xCenter, yCenter, targetRoost.getXCenter(), targetRoost.getYCenter())){
                targetRoost = r;
            }
        }
            
    }
    
    public void flyOut(){
        if(firstRun){
            if(isChick){
                direction = Math.random() * 2 * Math.PI;
                flyOutDist = 2 * targetRoost.getRadius() + radius + (250 - targetRoost.getRadius()) * Math.random();
                isChick = false;
            }
            else{
                direction = Math.PI + Calculation.findDirection(xCenter, yCenter, targetRoost.getXCenter(), targetRoost.getYCenter());          
                flyOutDist = targetRoost.getRadius() + radius + (250 * Math.random());
            }
            xDelta = Math.cos(direction);
            yDelta = Math.sin(direction); 
        }
        
        firstRun = false;
        
        if(Calculation.distance(xCenter, yCenter, targetRoost.getXCenter(), targetRoost.getYCenter()) < flyOutDist){
            xCenter += (speed * 1.25) * xDelta;
            yCenter += (speed * 1.25) * yDelta;
        }
        else {
            flying = false;
        }
    }
    
    public void flyIn(){
        if(firstRun){
            direction = Calculation.findDirection(xCenter, yCenter, targetRoost.getXCenter(), targetRoost.getYCenter());
            xDelta = Math.cos(direction);
            yDelta = Math.sin(direction);
        }
        
        firstRun = false;
        if(Calculation.distance(xCenter, yCenter, targetRoost.getXCenter(), targetRoost.getYCenter()) > targetRoost.getRadius() - (givingBirth?6:2) * radius){
            xCenter += (speed * 1.25) * xDelta;
            yCenter += (speed * 1.25) * yDelta;
        }
        else {
            flying = false;
        }
    }
    
    public Bird giveBirth(){
        if(getFlying()){
            flyIn();
            return(null);
        }
        else {
            givingBirth = false;
            energy = energy / 2;
            Bird c = createChick();
            c.setEnergy(energy);
            if(infected){
                c.setInfected(true);
            }
            return(c);
        }
    }
    
    public abstract void draw(Graphics G);
    
    public abstract int[] prune(ArrayList<Bird> otherBirds, Roost roost);
    
    public abstract Bird createChick();
}
