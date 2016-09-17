/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import WorldGen.World;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 *
 * @author sntgc
 */
public abstract class Body implements Renderable, Tangible{
    //Might have to change this to a different class
    BufferedImage[] sprites;
    String globalCoords;
    int[] sectorCoords;
    int[] hitBox;
    World myMap;
    
    public Body(World w){
        myMap=w;
        globalCoords="";
        sectorCoords=new int[2];
        hitBox=new int[2];
    }
    
    @Override
    public void setHitbox(int width, int length) {
        hitBox=new int[] {length, width};
    }

    @Override
    public void setCoords(String globalCoord, int[] sectorCoords) {
        if(!globalCoords.equals("")){
            myMap.requestSector(globalCoords).removeBody(this);
        }
        this.globalCoords=globalCoord;
        myMap.requestSector(globalCoord).addBody(this);
        this.sectorCoords=sectorCoords.clone();
    }
    
    

    @Override
    public void render(int xShift, int yShift) {
        Render.Polygons.drawIsometricTile(sectorCoords[0]+xShift, sectorCoords[1]+yShift, 64, Color.blue);
    }
    
    public String toString(){
        return "BODY @"+globalCoords;
    }
}
