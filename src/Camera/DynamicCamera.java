/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Camera;

import WorldGen.Sector;
import WorldGen.World;
import java.util.Arrays;

/**
 *
 * @author sntgc
 */
public class DynamicCamera {
    private int[] sectorCoords;
    private long[] globalCoords;
    private Sector[] sectors;
    private World world;
    private int[] xyMax;
    private int[] sectorData;
    public DynamicCamera(World map){
        sectorCoords=new int[] {0,0};
        globalCoords=new long[] {0,0};
        sectors=new Sector[9];
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
        int index=0;
        for(int x=-1;x<=1;x++){
            for(int y=-1;y<=1;y++){
                //if(Math.abs(x)+Math.abs(y)<=1){
                    long xCoord=globalCoords[0]+x;
                    long yCoord=globalCoords[1]+y;
                    sectors[index++]=world.requestSector(xCoord+","+yCoord);
                //}
            }
        }
        
        //System.out.println(Arrays.toString(sectors));
    }
    
    public void render(){
        sectors[0].render(sectorCoords[0]-sectorData[2],sectorCoords[1]);
        sectors[1].render(sectorCoords[0]-sectorData[2]/2,sectorCoords[1]+sectorData[2]/4);
        sectors[2].render(sectorCoords[0],sectorCoords[1]+sectorData[2]/2);
        sectors[3].render(sectorCoords[0]-sectorData[2]/2,sectorCoords[1]-sectorData[2]/4);
        sectors[4].render(sectorCoords[0],sectorCoords[1]);
        sectors[5].render(sectorCoords[0]+sectorData[2]/2,sectorCoords[1]+sectorData[2]/4);
        sectors[6].render(sectorCoords[0],sectorCoords[1]-sectorData[2]/2);
        sectors[7].render(sectorCoords[0]+sectorData[2]/2,sectorCoords[1]-sectorData[2]/4);
        sectors[8].render(sectorCoords[0]+sectorData[2],sectorCoords[1]);
    }
}
