/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WorldGen;

import java.awt.Color;

/**
 *
 * @author sntgc
 */
public class Sector {
    //Collection of tiles
    private Tile[][] tiles;
    private String id;
    private String[] neighbors;
    public Sector(int sides, String id, int width){
        tiles=new Tile[sides][sides];
        this.id=id;
        fillMap(width);
    }
    
    private void fillMap(int tileWidth){
        int rIndex=-800+tileWidth/2;
        int cIndex=0;
        Color aColor=new Color((int)(Math.random()*255)+0,0,(int)(Math.random()*255)+0);
        Color bColor=bugTestRender()? Color.BLACK:Color.WHITE;//new Color(0,0,aColor.getBlue()/2);
        for(int r=0;r<tiles.length;r++){
            for(int c=0;c<tiles[r].length;c++){
                tiles[r][c]=new Tile(rIndex,cIndex,tileWidth,(r+c)%2==0? aColor:bColor);//new Color(0,0,(r+c)%2==0? 255:155));//(r+c)%2==0? aColor:bColor);
                rIndex+=tileWidth/2;
                cIndex+=tileWidth/4;
            }
            rIndex-=tileWidth/2*(tiles.length-1);
            cIndex-=tileWidth/4*(tiles.length+1);
        }
    }
    
    private boolean bugTestRender(){
        String[] nums=id.split(",");
        return (Integer.parseInt(nums[0])+Integer.parseInt(nums[1]))%2==0;
    }
    
    public static int[] lineCross(int ba,int bb){
        int[] ret= new int[2];
        //returns an array in the for of [x,y]
        ret[0]=(ba-bb);
        ret[1]=ret[0]/2+bb;
        return ret;
    }
    
    public void render(int xShift, int yShift){
        for(Tile[] t:tiles){
            for(Tile mt:t){
                mt.render(xShift,yShift);
            }
        }
    }
    
    @Override
    public String toString(){
        return " "+id;
    }
}
