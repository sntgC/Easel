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
    public World(int tileWidth){
        reference=new TreeMap<String, Sector>();
        reference.put("0,0", new Sector(10, "0,0", tileWidth));
    }
    
    public Sector requestSector(String id){
        Sector ret;
        if(reference.get(id)==null){
            ret=new Sector(10,id,160/*Change this to custom tile width*/); 
            reference.put(id,ret);
        }else
            ret=reference.get(id);
        return ret;
    }
}
