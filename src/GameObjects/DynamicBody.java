/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import WorldGen.World;

/**
 *
 * @author sntgc
 */
public class DynamicBody extends Body{
    private float velocity;
    private float acceleration;
    //Used for float math, not sure if im keeping this array though
    private float[] fSectorCoords;
    private static final int[] xyMax=new int[] {512,256};
    private long[] globalCoords;
    public DynamicBody(String global, int sectorX, int sectorY, World w){
        super(w);
        fSectorCoords=new float[2];
        velocity=8;
        globalCoords=new long[2];
        globalCoords[0]=Integer.parseInt(global.substring(0,global.indexOf(",")));
        globalCoords[1]=Integer.parseInt(global.substring(global.indexOf(",")+1));
        super.setCoords(global, new int[] {sectorX,sectorY});
    }
    public void move(String isoDirection){
        if(isoDirection.equals("DOWN")){
            moveMath(0,(int)(velocity/2));
        }else if(isoDirection.equals("UP")){
            moveMath(0,(int)(-velocity/2));
        }else if(isoDirection.equals("RIGHT")){
            moveMath((int)(-velocity/2),0);
        }else if(isoDirection.equals("LEFT")){
            moveMath((int)(velocity/2),0);
        }
    }
    
    private void moveMath(int xShift, int yShift){
        int newX=sectorCoords[0]-xShift;
        int newY=sectorCoords[1]-yShift;
        int absSlope=Math.abs(newX)/2;
        if(newY>-absSlope+xyMax[1]){
            sectorCoords[1]=-(xyMax[1]-newY);
            if(newX<0){
                sectorCoords[0]=xyMax[0]+newX;
                globalCoords[0]--;
            }else{
                sectorCoords[0]=-(xyMax[0]-newX);
                globalCoords[1]++;
            }
            String global=globalCoords[0]+","+globalCoords[1];
            super.setCoords(global, sectorCoords);
        }else if(newY<absSlope-xyMax[1]){
            sectorCoords[1]=xyMax[1]+newY;
            if(newX<0){
                sectorCoords[0]=xyMax[0]+newX;
                globalCoords[1]--;
            }else{
                sectorCoords[0]=-(xyMax[0]-newX);
                globalCoords[0]++;
            }
            String global=globalCoords[0]+","+globalCoords[1];
            super.setCoords(global, sectorCoords);
        }else{
            sectorCoords[0]=newX;
            sectorCoords[1]=newY;
        }
    }
    
    public void setVelocity(float v){
        velocity=v;
    }
    public void setAcceleration(float a){
        acceleration=a;
    }
}
