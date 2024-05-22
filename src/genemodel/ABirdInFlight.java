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
public class ABirdInFlight {
    private static double[][] rBasic;
    
    public static double[][] getEndpointMatrix(int numLines){
        if(numLines % 2 == 1){
            numLines -= 1;
        }
        rBasic = new double[numLines][4];
        int j = 0;
        for(int i = 0; i < numLines / 2; i++){            
            rBasic[j][0] = 1.5 * Math.pow(Math.sin(2.0 * Math.PI * ((double)i + 1.0) / numLines + Math.PI / 3 ), 7.0);
            rBasic[j][1] = .25 * Math.pow(Math.cos(6.0 * Math.PI * ((double)i + 1.0) / numLines), 2.0);
            rBasic[j][2] = .2 * Math.sin(6.0 * Math.PI * ((double)i + 1.0) / numLines + Math.PI / 5.0);
            rBasic[j][3] = (-2.0 / 3.0) * Math.pow(Math.sin(2.0 * Math.PI * ((double)i + 1.0) / numLines - Math.PI / 3.0), 2);
            
            rBasic[j + 1][0] = 1.5 * Math.pow(Math.sin(2.0 * Math.PI * (((double)i + 1.0) + numLines / 2.0) / numLines + Math.PI / 3 ), 7.0);
            rBasic[j + 1][1] = .25 * Math.pow(Math.cos(6.0 * Math.PI * (((double)i + 1.0) + numLines / 2.0) / numLines), 2.0);
            rBasic[j + 1][2] = .2 * Math.sin(6.0 * Math.PI * (((double)i + 1.0) + numLines / 2.0) / numLines + Math.PI / 5.0);
            rBasic[j + 1][3] = (-2.0 / 3.0) * Math.pow(Math.sin(2.0 * Math.PI * (((double)i + 1.0) + numLines / 2.0) / numLines - Math.PI / 3.0), 2);
            j += 2;
            
        }
        return(rBasic);
    }
    public static double[][] multiplyByRotationMatrix(double[][] r, double theta){
        theta -= Math.PI / 2;
        double[][] rNew = new double[r.length][r[0].length];
        for(int i = 0; i < rNew.length; i++){
            rNew[i][0] = r[i][0] * Math.cos(theta) - r[i][1] * Math.sin(theta);
            rNew[i][1] = r[i][0] * Math.sin(theta) + r[i][1] * Math.cos(theta);
            rNew[i][2] = r[i][2] * Math.cos(theta) - r[i][3] * Math.sin(theta);
            rNew[i][3] = r[i][2] * Math.sin(theta) + r[i][3] * Math.cos(theta);
        }
        return(rNew);
    }
    public static double[][] multiplyByRotationMatrixErrored(double[][] r, double theta){
        for(int i = 0; i < r.length; i++){
            r[i][0] = r[i][0] * Math.cos(theta) - r[i][1] * Math.sin(theta);
            r[i][1] = r[i][0] * Math.sin(theta) + r[i][1] * Math.cos(theta);
            r[i][2] = r[i][2] * Math.cos(theta) - r[i][3] * Math.sin(theta);
            r[i][3] = r[i][2] * Math.sin(theta) + r[i][3] * Math.cos(theta);
        }
        return(r);
    }
    public static double[][] multiplyByCosAndSin(double[][] r, double theta){
        for(int i = 0; i < r.length; i++){
            r[i][0] = r[i][0] * Math.cos(theta);
            r[i][1] = r[i][1] * Math.sin(theta);
            r[i][2] = r[i][2] * Math.cos(theta);
            r[i][3] = r[i][3] * Math.sin(theta);
        }
        return(r);
    }
    
    
    public static double[][] scaleAndChangePosition(double[][] r, double scale, double xShift, double yShift){
        for(int i = 0; i < r.length; i++){
            r[i][0] = r[i][0] * scale + xShift;
            r[i][1] = r[i][1] * scale + yShift;
            r[i][2] = r[i][2] * scale + xShift;
            r[i][3] = r[i][3] * scale + yShift;        
        }
        return(r);
    }
}
