/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import java.awt.Color;
import java.awt.image.BufferedImage;

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
    @Override
    public void setHitbox(int width, int length) {
        hitBox=new int[] {length, width};
    }

    @Override
    public void setCoords(String globalCoord, int[] sectorCoords) {
        this.globalCoords=globalCoord;
        this.sectorCoords=sectorCoords.clone();
    }
    
    

    @Override
    public void render(int xShift, int yShift) {
        Render.Polygons.drawIsometricTile(sectorCoords[0]+xShift, sectorCoords[1]+yShift, 64, Color.blue);
    }
}
