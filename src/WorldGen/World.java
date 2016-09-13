/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WorldGen;

import java.awt.Point;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author sntgc
 */
public class World {
    //Colection of sectors
    private Map<String, Sector> reference;
    private int[] sectorData;
    public World(int tileWidth, int sectorWidth){
        reference=new TreeMap<String, Sector>();
        sectorData=new int[] {tileWidth, sectorWidth, tileWidth*sectorWidth};
        reference.put("0,0", new Sector(sectorWidth, "0,0", tileWidth));
    }
    
    public int[] getSectorData(){
        return sectorData;
    }
    
    public Sector requestSector(String id){
        
        Sector ret;
        if(reference.get(id)==null){
            ret=new Sector(sectorData[1],id,sectorData[0]/*Change this to custom tile width*/); 
            reference.put(id,ret);
        }
        
        if(reference.get(id).filled){
            return reference.get(id);
        }
        else{
            long xVal = Long.parseLong(id.substring(0, id.indexOf(",")));
            long yVal = Long.parseLong(id.substring(id.indexOf(",") + 1));
            
            String rightID = (xVal + 1) + "," + yVal;
            String belowID = xVal + "," + (yVal + 1);
            String bottomRightID = (xVal + 1) + "," + (yVal + 1);
            
            if(reference.get(rightID) == null){
                reference.put(rightID, new Sector(sectorData[1], rightID, sectorData[0]/*Change this to custom tile width*/)); 
            }
            if(reference.get(belowID) == null){
                reference.put(belowID, new Sector(sectorData[1], belowID, sectorData[0]/*Change this to custom tile width*/)); 
            }
            if(reference.get(bottomRightID) == null){
                reference.put(bottomRightID, new Sector(sectorData[1], bottomRightID, sectorData[0]/*Change this to custom tile width*/)); 
            }
            
            double[][] edges = new double[3][sectorData[1]];
            edges[0] = reference.get(rightID).baseNoiseLayer[0];
            for(int i = 0; i < sectorData[1]; i++){
                edges[1][i] = reference.get(belowID).baseNoiseLayer[i][0];
            }
            edges[2][0] = reference.get(bottomRightID).baseNoiseLayer[0][0];
            
            reference.get(id).fill(edges);
        }
        
        return reference.get(id);
    }
    
    //public Sector
}
