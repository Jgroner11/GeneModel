package genemodel;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.JFrame;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
/**
 *
 * @author Jacob Groner
 */
    
public class GeneModel extends JFrame implements Runnable, KeyListener{
    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static final double screenWIDTH = tk.getScreenSize().getWidth();
    private static final double screenHEIGHT = tk.getScreenSize().getHeight();
    private static double frameWidth = 1000;
    private static double frameHeight = 1000;
    
    private static GeneModel window = new GeneModel();
    
    private Rectangle background = new Rectangle (frameWidth / 2, (frameHeight / 2) * -1, new Color(134, 186, 39), frameWidth, frameHeight);
    private ArrayList<FoodParticle> foodList = new ArrayList<FoodParticle>();
    private ArrayList<Roost> roostList = new ArrayList<Roost>();
    private ArrayList<Bird> birdList = new ArrayList<Bird>();
    private ArrayList<Bird> newBirds = new ArrayList<Bird>();
    private Timer foodTimer = new Timer(60);
    private Bird jonathan = new Sucker(260, -251);
    private Timer dayTimer = new Timer(8 * 60);
    private Timer nightTimer = new Timer(8 * 60);
    private boolean isDay = true;
    private int[] pruneCommand;
    
    private double seconds = 0;
    private int runAmount = 0;
    
    private Thread thread;
    private boolean running = false;
    private boolean onFirstUpdate = true;
    
    private double mouseX = 500.0;
    private double mouseY = -500.0; 
    private ArrayList<Integer> keysDown;
    
//Data collection variables
    private double aDead = 0;
    private double aInfected = 0;
    private double aIRate;
    private boolean foundGrudger;
           
//Test Variables

    public synchronized void start(){
        running = true;
        thread = new Thread(this, "DISPLAY");
        thread.start();
    }
    public synchronized void stop(){
        running = false;
        try {   
            thread.join();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }
  
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void run(){
        long lastTime = System.nanoTime();
        final double ns1 = 1000000000.0 / 60.0;
        final double ns2 = 1000000000.0 / 15.0;
        double delta1 = 0;
        double delta2 = 0;
        while (running){
            long now = System.nanoTime();
            delta1 += (now - lastTime) / ns1;
            delta2 += (now - lastTime) / ns2;
            lastTime = now;
            while (delta1 >= 1){
                update();
                delta1--;
            }
            while (delta2 >= 1){
                render();
                delta2--;
            }
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void update(){
        seconds = runAmount / 60.0;
        foundGrudger = false;
        if(onFirstUpdate){
            //place to intialize all roosts and birds for program
            roostList.add(new Roost(250, -250));
            roostList.add(new Roost(750, -750));
            
            birdList.add(new Sucker(240, -260));
            //birdList.add(new Cheater(240, -260));
            birdList.add(new Grudger(240, -260, null));
            
            for(Bird b : birdList){
                b.setID(birdList);
            }
            
            dayTimer.reset();
            nightTimer.reset();
            for (Bird b : birdList){
                b.findTargetRoost(roostList);
            }
            for(int i = 0; i < 50; i++){
                boolean foodPositionCheck = true;
                while(foodPositionCheck){
                    foodPositionCheck = false;
                    FoodParticle newFP = new FoodParticle(Math.random() * frameWidth, -1 * Math.random() * frameHeight, Math.random() < .5);
                    for (Roost r : roostList){
                        if(Calculation.distance(newFP.getXCenter(), newFP.getYCenter(), r.getXCenter(), r.getYCenter()) < newFP.foodSize + r.getRadius()){
                            foodPositionCheck = true;
                        }
                    }
                    if(!foodPositionCheck){
                        foodList.add(newFP);    
                    }             
                }
            }
        }
        try{
            if(!onFirstUpdate){
                mouseX = MouseInfo.getPointerInfo().getLocation().getX() - this.getLocationOnScreen().getX();
                mouseY = (MouseInfo.getPointerInfo().getLocation().getY() - this.getLocationOnScreen().getY()) * -1;
                }
        }catch(Exception e){}

        if(foodTimer.checkInterval()){
            foodTimer.reset();
            boolean foodPositionCheck = true;
            while(foodPositionCheck){
                foodPositionCheck = false;
                FoodParticle newFP = new FoodParticle(Math.random() * frameWidth, -1 * Math.random() * frameHeight, Math.random() < .5);
                for (Roost r : roostList){
                    if(Calculation.distance(newFP.getXCenter(), newFP.getYCenter(), r.getXCenter(), r.getYCenter()) < newFP.foodSize + r.getRadius()){
                        foodPositionCheck = true;
                    }
                }
                if(!foodPositionCheck){
                    foodList.add(newFP);    
                }             
            }
        }
        
        if(isDay){
            for(Bird b : birdList){
                if(!b.getIsDead()){
                    if(!b.getGivingBirth()){
                        if(b.getFlying()){
                            b.flyOut();
                        }
                        else {
                            b.forage(foodList, frameWidth, frameHeight, roostList);
                        }
                    }
                    else {
                        Bird chick = b.giveBirth();
                        if(!(chick == null)){
                            newBirds.add(chick);
                        }
                        if(!b.getGivingBirth()){
                            b.setFirstRun(true);
                            b.setFlying(true);
                        }
                    }
                    b.consumeEnergy(roostList);
                }
            }
            for(Bird c : newBirds){
                c.setID(birdList);
                birdList.add(c);
            }
            newBirds.clear();
            if(dayTimer.checkInterval()){
                dayTimer.reset();
                for(Bird b : birdList){
                    if(!b.getGivingBirth()){
                        b.findTargetRoost(roostList);
                        b.setFirstRun(true);
                        b.setFlying(true);
                    }
                }
                isDay = false;    
            }
        }
        else if(!isDay){
            for(Bird b : birdList){
                if(!b.getIsDead()){
                    if(!b.getGivingBirth()){    
                        if(b.getFlying()){
                            b.flyIn();
                        }
                        else {
                            ArrayList<Bird> otherBirds = new ArrayList<Bird>();
                            Roost tR = b.getTargetRoost();
                            for(Bird b2 : birdList){
                                if(b2.getID() != b.getID())
                                    if(b2.getTargetRoost() == tR){
                                        otherBirds.add(b2);
                                    }
                            }
                            pruneCommand = b.prune(otherBirds, b.getTargetRoost());
                            if(pruneCommand.length == 2){
                                if(pruneCommand[0] == 1){    
                                    for(Bird b2 : otherBirds){
                                        if(b2.getID() == pruneCommand[1]){
                                            b2.setPruning(true);
                                            b2.setTargetID(b.getID());
                                        }
                                    }
                                }
                                else if(pruneCommand[0] == 2){
                                    for(Bird b2 : otherBirds){
                                        if(b2.getID() == pruneCommand[1]){
                                            b2.setPruning(false);
                                            b2.setInfected(false);
                                            if(!(b2 instanceof Cheater)){
                                                b2.setEnergy(b2.getEnergy() - b2.getPruneCOST());
                                            }
                                            b2.setPruneBreak(true);
                                            b2.getPruneTimer().reset();
                                            b2.setTargetID(-1);
                                        }
                                    }
                                }
                                else if(pruneCommand[0] == 3){
                                    for(Bird b2 : otherBirds){
                                        if(b2.getID() == pruneCommand[1]){
                                            b2.setPruning(false);
                                            b2.setEnergy(b2.getEnergy() - b2.getPruneCOST());
                                            b2.setPruneBreak(true);
                                            b2.getPruneTimer().reset();
                                            b2.setTargetID(-1);
                                        }
                                    }
                                }
                                else if(pruneCommand[0] == 4){
                                    for(Bird b2 : otherBirds){
                                        if(b2.getID() == pruneCommand[1]){//if b2 is the grudger
                                            b2.setPruning(false);
                                            b2.setEnergy(b2.getEnergy() - b2.getPruneCOST());
                                            b2.setPruneBreak(true);
                                            b2.getPruneTimer().reset();
                                            b2.setTargetID(-1);
                                            ((Grudger)b2).addKnownCheater(b.getID());
                                        }
                                        else if(b2 instanceof Grudger){
                                            ((Grudger)b2).addKnownCheater(b.getID());
                                        }
                                    }
                                }
                                else if(pruneCommand[0] == 5){
                                    for(Bird b2 : otherBirds){
                                        if(b.getID() == pruneCommand[1]){// if b2 is the cheater
                                            b2.setPruning(false);
                                            b2.setInfected(false);
                                            b2.setPruneBreak(true);
                                            b2.getPruneTimer().reset();
                                            b2.setTargetID(-1);
                                        }
                                        else if(b2 instanceof Grudger){
                                            ((Grudger) b2).addKnownCheater(pruneCommand[1]);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else {
                        Bird chick = b.giveBirth();
                        if(!(chick == null)){
                            newBirds.add(chick);
                        }                       
                    }
                }
            }
            for(Bird c : newBirds){
                c.setID(birdList);
                birdList.add(c);
            }
            newBirds.clear();
            if(nightTimer.checkInterval()){
                nightTimer.reset();
                for(Bird b : birdList){
                    if(!b.getGivingBirth()){
                        b.setFirstRun(true);
                        b.setFlying(true);
                    }
                    b.setPruning(false);
                    b.getPruneTimer().reset();
                    b.setPruneBreak(false);
                    b.setTargetID(-1);
                }
                isDay = true;
            }
        }
        
        Iterator itr = birdList.iterator();
        while (itr.hasNext()){
            Bird b = (Bird)itr.next();
            if(b.decay()){
                if(b.getInfected()){
                    aInfected++;
                }
                aDead++;
                if(b instanceof Cheater){
                    for(Bird b2: birdList){
                        if(b2 instanceof Grudger){
                            ((Grudger) b2).removeKnownCheater(b.getID());
                        }
                    }
                }
                itr.remove();
            }
        }
        aIRate = aInfected / aDead;
        //System.out.println(aIRate * 100 + "%");  
        onFirstUpdate = false;
        runAmount++;
        for(Bird b : birdList){
            if(b.getID() == 1){
                /*
                if(b.getInfected()){
                    System.out.print(b.getInfectedCONST1() * b.getDeclineRATE() + b.getInfectedCONST2() * b.getInfectedDuration());
                }
                else{
                    System.out.print(b.getDeclineRATE());
                }
                System.out.print(": " + "1=" + b.getInfectedCONST1() + ", dR=" + b.getDeclineRATE() + ", 2=" + b.getInfectedCONST2()  + ", iD=" + b.getInfectedDuration() + "\n");
                */
                System.out.println(b.getEnergy());
            }
        }
    }///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void render(){
        repaint();
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void paint (Graphics g){
        if(isDay){
            background.setColor(new Color(92,214,92));
        }
        else {
            background.setColor(new Color(36,143,36));
        }
        background.draw(g);
        
        for (Roost r : roostList){
            r.draw(g);
        }
        try {
            for(Bird b : birdList){
                b.draw(g);
            }
        }
        catch(Exception e){
        }
        try {
            for (FoodParticle fp : foodList){
                    fp.draw(g);
            }
            
        }
        catch(Exception e){
        }
        
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));//Arial Bold Italic         
        g.drawString(Integer.toString((int)seconds), (int)((9/10.0) * frameWidth), (int)(-1 * ((-9/10.0) * frameHeight)));
        
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
       if(frameWidth > screenWIDTH)
           frameWidth = screenWIDTH;
       if (frameHeight > screenHEIGHT)
           frameHeight = screenHEIGHT;
       window.setSize((int)frameWidth, (int)frameHeight);
       window.setTitle("Gene Model");
       window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       window.setResizable(false);
       //window.setUndecorated(true);
       window.setVisible(true);
       window.setLocation(tk.getScreenSize().width/2-window.getSize().width/2, tk.getScreenSize().height/2-window.getSize().height/2);
    }
    public GeneModel(){
       this.addKeyListener(this);
       keysDown = new ArrayList<Integer>();
       start();
    }
    
    @Override
    public void keyPressed(KeyEvent e){
        if(!keysDown.contains(e.getKeyCode())){
            keysDown.add(new Integer(e.getKeyCode()));     
        }
    }
    @Override
    public void keyReleased(KeyEvent e){
        keysDown.remove(new Integer(e.getKeyCode())); 
    }
    @Override
    public void keyTyped(KeyEvent e){      
    }
}
    