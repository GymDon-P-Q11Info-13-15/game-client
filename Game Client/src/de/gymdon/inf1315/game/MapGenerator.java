package de.gymdon.inf1315.game;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MapGenerator {

    private int x = 48; // TODO: Rename
    private int y = 32; // TODO: Rename

    private int mines = 4;
    private int superiorMines = 1;
    private int seas = 2;
    private int sandbanks = 3;
    private int averageSizeOfSeas = 10;
    private int averageSizeOfSandbanks = 14;

    public static Tile[][] map;
    public Building[][] buildings;
    Tile grass;
    Tile sand;
    Tile water;

    public MapGenerator() {

	Tile grass = new Tile();
	grass.groundFactor = 1;
	Tile sand = new Tile();
	sand.groundFactor = 2;
	Tile water = new Tile();
	water.groundFactor = 3;
	map = new Tile[x][y];
	buildings = new Building[x][y];

	for (int i = 0; i < x; i++) {

	    for (int k = 0; k < y; k++) {

		map[i][k] = grass;

	    }

	}

    }
    
    public void generate() {
	
	for(int i = 1; i <= seas; i++) {
	    
	    int xWater = (int)(Math.random()*32+8);
	    int yWater = (int)(Math.random()*22+5);
	    
	    map[xWater][yWater] = water;
	    
	}
	
    }
    

    public static void main(String[] args) {
	
	MapGenerator mapgen = new MapGenerator();
	
	BufferedImage bi = new BufferedImage(1536, 1024, BufferedImage.TYPE_INT_ARGB);

	for (int i = 0; i < mapgen.x; i++) {

	    for (int k = 0; k < mapgen.y; k++) {

		if(map[i][k].groundFactor == 1) {
		
		    for (int g = 32*i+1; g < 32*(i+1); g++) {
		    
			for (int f = 32*k+1; f < 32*(k+1); f++) {
			    
			    bi.setRGB(g, f,  ((0 << 24) | (0 << 16) | (255 << 8) | 0));
			    
			}
		    
		    }
		    
		}
		if(map[i][k].groundFactor == 2) System.out.print("S");
		if(map[i][k].groundFactor == 3) System.out.print("W");

	    }

	    System.out.println("");
	    
	    File f = new File("/home/simon/MapTileFile.png");
	    try {
		ImageIO.write(bi, "PNG", f);
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    
	}

    }

}
