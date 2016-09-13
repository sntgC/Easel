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
    boolean filled;
    int tileWidth;
    public double[][] baseNoiseLayer;
    
    public Sector(int sides, String id, int width){
        tiles=new Tile[sides][sides];
        this.id=id;
        filled = false;
        tileWidth = width;
        
        baseNoiseLayer = new double[sides + 1][sides + 1];
        for(int x = 0; x < sides; x++){
            for(int y = 0; y < sides; y++){
                baseNoiseLayer[x][y] = Math.random();
            }
        }
        
    }
    
    public void fill(double[][] adjacentEdges){     //a double[][] of 3 double[]s, in order of: sector to the right, below,and bottom right(bottom right one has a length of only 1)
        filled = true;
        
        int xIndex = -800 + tileWidth/2;
        int yIndex = 0;
        
        baseNoiseLayer[tiles.length] = adjacentEdges[0];
        for(int i = 0; i < adjacentEdges[1].length; i++){
            baseNoiseLayer[i][baseNoiseLayer.length - 1] = adjacentEdges[1][i];
        }
        baseNoiseLayer[tiles.length][tiles.length] = adjacentEdges[2][0];
        
        double[][] perlinNoise = generatePerlinNoise(baseNoiseLayer, 7);
        
        
        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles[x].length; y++){
                
                
                double waterLevel = 0.55;
                Color col;
                
                if(perlinNoise[x][y] < waterLevel){
                    col = new Color(0, 0, (int)(perlinNoise[x][y] * 250));
                }
                else/* if(baseNoiseLayer[x][y] >= waterLevel)*/{
                    if((x > 0 && perlinNoise[x - 1][y] < waterLevel) || (x < perlinNoise.length - 1 && perlinNoise[x + 1][y] < waterLevel) || (y > 0 && perlinNoise[x][y - 1] < waterLevel) || (y < perlinNoise[x].length - 1 && perlinNoise[x][y + 1] < waterLevel)){
                        col = new Color(194, 178, 128);
                    }
                    else{
                        col = new Color(CreateRedBlueValue(waterLevel, perlinNoise[x][y]), (int)((perlinNoise[x][y] - waterLevel) * (1 / (1 - waterLevel)) * 200 + 50), CreateRedBlueValue(waterLevel, perlinNoise[x][y]));
                    }
                }
                
                tiles[x][y] = new Tile(xIndex, yIndex, tileWidth, col);
                xIndex+= tileWidth / 2;
                yIndex+= tileWidth / 4;
            }
            xIndex -= tileWidth/2 * (tiles.length-1);
            yIndex -= tileWidth/4 * (tiles.length+1);
        }
        
        minimizeBaseNoiseLayer();
    }
    
    private void minimizeBaseNoiseLayer(){
        double[][] newBase = new double[baseNoiseLayer.length][baseNoiseLayer[0].length];
        
        newBase[0] = baseNoiseLayer[0];
        
        for(int i = 1; i < baseNoiseLayer[0].length; i++){
            newBase[i][0] = baseNoiseLayer[i][0];
        }
        
        baseNoiseLayer = newBase;
    }
    
    private int CreateRedBlueValue(double waterLevel, double inVal){
        
        //int useVal = (int)((inVal - waterLevel) * (inVal - waterLevel) * (1 / (1 - waterLevel * waterLevel)) * 750);
        //int greenVal = (int)((inVal - waterLevel) * (1 / (1 - waterLevel)) * 200);
        
        //return Math.min(useVal, greenVal);
        
        return 0;
    }
    
    private double interpolate(double x0, double x1, double alpha){
        return x0 * (1 - alpha) + alpha * x1;
    }
    
    private double[][] generateSmoothNoise(double[][] baseNoise, int octave) {

        int width = baseNoise.length - 1;
        int height = baseNoise[0].length - 1;

        double[][] smoothNoise = new double[width][height];

        int samplePeriod = (int) Math.pow(2.0, octave); // calculates 2 ^ k
        double sampleFrequency = 1.0 / samplePeriod;

        for (int i = 0; i < width; i++) {
            //calculate the horizontal sampling indices
            int sample_i0 = (i / samplePeriod) * samplePeriod;
            int sample_i1 = (sample_i0 + samplePeriod);
            double horizontal_blend = (i - sample_i0) * sampleFrequency;

            for (int j = 0; j < height; j++) {
                //calculate the vertical sampling indices
                int sample_j0 = (j / samplePeriod) * samplePeriod;
                int sample_j1 = (sample_j0 + samplePeriod);
                double vertical_blend = (j - sample_j0) * sampleFrequency;

                //blend the top two corners
                double top = interpolate(baseNoise[sample_i0][sample_j0],
                        baseNoise[sample_i1][sample_j0], horizontal_blend);

                //blend the bottom two corners
                double bottom = interpolate(baseNoise[sample_i0][sample_j1],
                        baseNoise[sample_i1][sample_j1], horizontal_blend);

                //final blend
                smoothNoise[i][j] = interpolate(top, bottom, vertical_blend);
            }
        }

        return smoothNoise;
    }
    
    private double[][] generatePerlinNoise(double[][] baseNoise, int octaveCount) {
        int width = baseNoise.length - 1;
        int height = baseNoise[0].length - 1;

        double persistance = 0.5;
        double[][] perlinNoise = new double[width][height];
        double amplitude = 1.0;
        double totalAmplitude = 0.0;

        //blend noise together
        for (int octave = octaveCount - 1; octave >= 0; octave--) {
            amplitude *= persistance;
            totalAmplitude += amplitude;

            double[][] smoothNoise = generateSmoothNoise(baseNoise, octave);
            
            
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    perlinNoise[i][j] += smoothNoise[i][j] * amplitude;
                }
            }
        }

        //normalisation
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                perlinNoise[i][j] /= totalAmplitude;
            }
        }

        return perlinNoise;
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