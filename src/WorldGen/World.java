/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WorldGen;

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
        }else
            ret=reference.get(id);
        return ret;
    }
}
