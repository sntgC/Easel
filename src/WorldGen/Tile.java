/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WorldGen;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;

/**
 *
 * @author sntgc
 */
public class Tile {
    private Color c;
    public int[] drawData;
    Point[] sectorCoordinates;
    public Tile(int x,int y,int isoWidth,Color c){
        drawData=new int[] {x,y,isoWidth};
        this.c=c;
    }
    
    public void render(int xShift, int yShift){
        Render.Polygons.drawIsometricTile(drawData[0]+xShift, drawData[1]+yShift, drawData[2], c);
    }
}
