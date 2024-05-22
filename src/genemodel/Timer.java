/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genemodel;

/**
 *
 * @author jacob
 */
public class Timer {
    private int interval;
    private int frameCounter;
    public Timer(){
        frameCounter = 0;
        interval = 60;
    }
    public Timer(int startInterval){
        interval = startInterval;
        frameCounter = 0;
    }
    public int getInterval(){
        return interval;
    }
    public void setInterval(int newInterval){
        interval = newInterval;
    }
    public int getFrameCounter(){
        return frameCounter;
    }
    public void setFrameCounter(int newFrameCounter){
        frameCounter = newFrameCounter;
    }
    public boolean checkInterval(){
        frameCounter++;
        if(frameCounter >= interval){
            return true;
        }
        return false;
    }
    public void reset(){
        frameCounter = 0;
    }
}
