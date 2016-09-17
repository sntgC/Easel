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
    private float[] fSectorCoords;
    public DynamicBody(String global, int sectorX, int sectorY, World w){
        super(w);
        super.setCoords(global, new int[] {sectorX,sectorY});
    }
    public void move(String isoDirection){
        if(isoDirection.equals("UP")){
            fSectorCoords[1]*=-velocity/2;
            sectorCoords[1]=(int)fSectorCoords[1];
        }else if(isoDirection.equals("DOWN")){
            fSectorCoords[1]*=velocity/2;
            sectorCoords[1]=(int)fSectorCoords[1];
        }else if(isoDirection.equals("LEFT")){
            fSectorCoords[0]*=velocity;
            sectorCoords[0]=(int)fSectorCoords[1];
        }else if(isoDirection.equals("RIGHT")){
            fSectorCoords[0]*=-velocity;
            sectorCoords[0]=(int)fSectorCoords[1];
        }
    }
    
    public void setVelocity(float v){
        velocity=v;
    }
    public void setAcceleration(float a){
        acceleration=a;
    }
}
