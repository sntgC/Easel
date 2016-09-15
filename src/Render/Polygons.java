/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Render;

import java.awt.Color;
import java.util.Arrays;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

/**
 *
 * @author sntgc
 */
public class Polygons {
    public static void drawTriangle(){
        glBegin(GL_TRIANGLES);
                glVertex2f(0.200f, 0.100f);
                glVertex2f(0.300f, 0.400f);
                glVertex2f(0.100f, (float)(Math.random()));
        glEnd();
    }
    
    public static void drawFPSBar(int fps){
        
        int fpsMax = 100;
        float changePoint = (float)fps / (float)fpsMax * 0.15f - 0.95f;
        
        glBegin(GL11.GL_POLYGON);
                GL11.glColor3f(255, 0, 0);
                glVertex2f(-0.95f, 0.95f);         //order: start top-left, go clockwise
                glVertex2f(changePoint, 0.95f);
                glVertex2f(changePoint, 0.9f);
                glVertex2f(-0.95f, 0.9f);
        glEnd();
        
        glBegin(GL11.GL_POLYGON);
                GL11.glColor3f(255, 255, 255);
                glVertex2f(changePoint, 0.95f);         //order: start top-left, go clockwise
                glVertex2f(-0.8f, 0.95f);
                glVertex2f(-0.8f, 0.9f);
                glVertex2f(changePoint, 0.9f);
        glEnd();
        
    }
    
    public static void drawIsometricTile(long x, long y, long width, Color c){
        float[] rgb=new float[] {c.getRed()/255.0f,c.getGreen()/255.0f, c.getBlue()/255.0f};
        float w=800.0f;
        float h=600.0f;
        float[] xCoords=new float[] {(x/w),(x+width/2)/w,(x/w),((x-width/2)/w)};
        float[] yCoords=new float[] {(y+width/4)/h,(y)/h,(y-width/4)/h,(y)/h};
        if(yCoords[0] > -1f && yCoords[2] < 1f && xCoords[1] < 1f && xCoords[1] > -1f)
        {
            glBegin(GL11.GL_POLYGON);
                GL11.glColor3f(rgb[0], rgb[1], rgb[2]);
                glVertex2f(xCoords[0], yCoords[0]);
                glVertex2f(xCoords[1], yCoords[1]);
                glVertex2f(xCoords[2], yCoords[2]);
                glVertex2f(xCoords[3], yCoords[3]);
            glEnd();
        }
    }
}
