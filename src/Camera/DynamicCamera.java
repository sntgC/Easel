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
        if(sectorCoords[0]+xShift<xyMax[0]&&sectorCoords[0]+xShift>(-xyMax[0]))
            sectorCoords[0]+=xShift;
        else if(sectorCoords[0]+xShift>=xyMax[0]){
            sectorCoords[0]=-xyMax[0]+1;
            globalCoords[0]--;
            globalCoords[1]--;
            getNeighbors();
        }else{
            sectorCoords[0]=xyMax[0]-1;
            globalCoords[0]++;
            globalCoords[1]++;
            getNeighbors();
        }
        if(sectorCoords[1]+yShift<xyMax[1]&&sectorCoords[1]+yShift>(-xyMax[1]))
            sectorCoords[1]+=yShift;
        else if(sectorCoords[1]+yShift>=xyMax[1]){
            sectorCoords[1]=-xyMax[1]+1;
            globalCoords[0]++;
            globalCoords[1]--;
            getNeighbors();
        }else{
            sectorCoords[1]=xyMax[1]-1;
            globalCoords[0]--;
            globalCoords[1]++;
            getNeighbors();
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
                //if(tempX != 0 || tempY != 0)  //dont draw center tile (for testing)
                sectors[x][y].render(sectorCoords[0] + (tempX + tempY) * (sectorData[2] / 2), sectorCoords[1] + (tempY - tempX) * (sectorData[2] / 4));
            }
        }
        
        //Color c = new Color(250, 0, 0);
        //Render.Polygons.drawIsometricTile(0, 0, 50, c);
        
        Render.Polygons.drawFPSBar(fps);
    }
}
