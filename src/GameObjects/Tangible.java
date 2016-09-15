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
public interface Tangible {
    public void setHitbox(int width, int length);
    public void setCoords(String globalCoord, int[] sectorCoords);
}
