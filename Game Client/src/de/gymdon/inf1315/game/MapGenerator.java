package de.gymdon.inf1315.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

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

	grass = new Tile();
	grass.groundFactor = 1;
	sand = new Tile();
	sand.groundFactor = 3;
	water = new Tile();
	water.groundFactor = 2;
	map = new Tile[x][y];
	buildings = new Building[x][y];

	for (int i = 0; i < x; i++) {

	    for (int k = 0; k < y; k++) {

		map[i][k] = grass;

	    }

	}

    }

    public void generate() {

	/**
	 * This methode will basically generate the map for the game. All
	 * generation parts create a field only one size big at the moment
	 * Currently every random water/sand field will make the field to have
	 * 'null' as a tile...
	 */

	for (int i = 1; i <= seas; i++) {

	    int xWater = (int) (Math.random() * 32 + 8);
	    int yWater = (int) (Math.random() * 22 + 5);

	    map[xWater][yWater] = water;
	    map[xWater+1][yWater] = water;
	    map[xWater-1][yWater] = water;
	    map[xWater][yWater+1] = water;
	    map[xWater][yWater-1] = water;
	    map[xWater+1][yWater+1] = water;
	    map[xWater+2][yWater+1] = water;
	    map[xWater+1][yWater+2] = water;
	    map[xWater][yWater] = water;
	    map[xWater][yWater] = water;
	    map[xWater][yWater] = water;
	    map[xWater][yWater] = water;
	    map[xWater][yWater] = water;
	    

	}

	for (int i = 1; i <= sandbanks; i++) {

	    int xSand = (int) (Math.random() * 32 + 8);
	    int ySand = (int) (Math.random() * 22 + 5);

	    map[xSand][ySand] = sand;
	    map[xSand+1][ySand] = sand;
	    map[xSand-1][ySand] = sand;
	    map[xSand][ySand+1] = sand;
	    map[xSand][ySand-1] = sand;

	}

    }

    public static void main(String[] args) {

	/**
	 * This should generate a picture with the generated map, currently only
	 * shows green fields with a black margin
	 */

	MapGenerator mapgen = new MapGenerator();
	
	mapgen.generate();

	BufferedImage bi = new BufferedImage(1536, 1024,
		BufferedImage.TYPE_INT_RGB);

	int rd = 0;
	int gd = 255;
	int bd = 0;
	int color = (rd << 16) | (gd << 8) | bd;
	
	int rf = 0;
	int gf = 0;
	int bf = 255;
	int blue = (rf << 16) | (gf << 8) | bf;
	
	int rh = 255;
	int gh = 255;
	int bh = 0;
	int yellow = (rh << 16) | (gh << 8) | bh;

	for (int i = 0; i < mapgen.x; i++) {

	    for (int k = 0; k < mapgen.y; k++) {

		if (mapgen.map[i][k].groundFactor == 1) {

		    for (int g = 32 * i + 1; g < 32 * (i + 1); g++) {

			for (int f = 32 * k + 1; f < 32 * (k + 1); f++) {

			    bi.setRGB(g, f, color);

			}

		    }

		}
	    }
	}
		
		for (int c = 0; c < mapgen.x; c++) {

		    for (int n = 0; n < mapgen.y; n++) {

			if (mapgen.map[c][n].groundFactor == 2) {

			    for (int g = 32 * c + 1; g < 32 * (c + 1); g++) {

				for (int f = 32 * n + 1; f < 32 * (n + 1); f++) {

				    bi.setRGB(g, f, blue);

				}

			    }

			}
		    }
		}
		
		for (int c = 0; c < mapgen.x; c++) {

		    for (int n = 0; n < mapgen.y; n++) {

			if (mapgen.map[c][n].groundFactor == 3) {

			    for (int g = 32 * c + 1; g < 32 * (c + 1); g++) {

				for (int f = 32 * n + 1; f < 32 * (n + 1); f++) {

				    bi.setRGB(g, f, yellow);

				}

			    }

			}
		    }
		}
		

	    

	    System.out.println("");

	    File f = new File("/home/students/11q/klimassi/Home_auf_Server/MapFileTile.png");
	    try {
		ImageIO.write(bi, "PNG", f);
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	}

    }

