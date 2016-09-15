/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

/**
 *
 * @author sntgc
 */
public class StaticBody extends Body{
    public StaticBody(String global, int sectorX, int sectorY){
        super.setCoords(global, new int[] {sectorX,sectorY});
    }
    
}
