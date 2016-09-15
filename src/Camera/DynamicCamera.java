 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Camera;

import WorldGen.Sector;
import WorldGen.World;
import java.awt.Color;
import java.util.Arrays;

/**
 *
 * @author sntgc
 */
public class DynamicCamera {
    private int[] sectorCoords;         // cameras coords within the current sector
    private long[] globalCoords;        
    private Sector[][] sectors;
    private World world;
    private int[] xyMax;
    private int[] sectorData;
    
    private int heldSectorsWidth = 7;
    
    public DynamicCamera(World map){
        sectorCoords=new int[] {0,0};
        globalCoords=new long[] {0,0};
        sectors=new Sector[heldSectorsWidth][heldSectorsWidth];
        world=map;
        sectorData=map.getSectorData();
        xyMax=new int[] {sectorData[2]/2,sectorData[2]/4};
        getNeighbors();
    }
    
    public void moveCamera(int xShift, int yShift){
        int newX=sectorCoords[0]+xShift;
        int newY=sectorCoords[1]+yShift;
        int absSlope=Math.abs(newX)/2;
        if(newY>-absSlope+xyMax[1]){
            sectorCoords[1]=-(xyMax[1]-newY);
            if(newX<0){
                sectorCoords[0]=xyMax[0]+newX;
                globalCoords[0]++;
            }else{
                sectorCoords[0]=-(xyMax[0]-newX);
                globalCoords[1]--;
            }
            getNeighbors();
        }else if(newY<absSlope-xyMax[1]){
            sectorCoords[1]=xyMax[1]+newY;
            if(newX<0){
                sectorCoords[0]=xyMax[0]+newX;
                globalCoords[1]++;
            }else{
                sectorCoords[0]=-(xyMax[0]-newX);
                globalCoords[0]--;
            }
            getNeighbors();
        }else{
            sectorCoords[0]=newX;
            sectorCoords[1]=newY;
        }
    }
    
    private void getNeighbors(){
        //int index=0;
        for(int x= 0; x < heldSectorsWidth; x++){
            for(int y= 0; y < heldSectorsWidth; y++){
                
                long xCoord=globalCoords[0] + (x - (heldSectorsWidth / 2));
                long yCoord=globalCoords[1] + (y - (heldSectorsWidth / 2));
                sectors[x][y] = world.requestSector(xCoord+","+yCoord);
            }
        }
        
    }
    
    public void render(int fps){
        
        for(int x= 0; x < heldSectorsWidth; x++){
            int tempX = x - (heldSectorsWidth / 2);
            for(int y= 0; y < heldSectorsWidth; y++){
                int tempY = y - (heldSectorsWidth / 2);
                if(tempX != 0 || tempY != 0)  //dont draw center tile (for testing)
                sectors[x][y].render(sectorCoords[0] + (tempX + tempY) * (sectorData[2] / 2), sectorCoords[1] + (tempY - tempX) * (sectorData[2] / 4));
            }
        }
        
        //Color c = new Color(250, 0, 0);
        //Render.Polygons.drawIsometricTile(0, 0, 50, c);
        
        Render.Polygons.drawFPSBar(fps);
    }
}
