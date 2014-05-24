package de.gymdon.inf1315.game;

import java.awt.Point;
import java.util.ArrayList;

public class MapGenerator {

    private int x = 48; // TODO: Rename
    private int y = 32; // TODO: Rename

    private int mines = 4;
    private int superiorMines = 1;
    private int seas = 2;
    private int sandbanks = 3;
    private int averageSizeOfSeas = 10;
    private int averageSizeOfSandbanks = 14;
    private int averageSideWater = 5;

    public static Tile[][] map;
    public Building[][] buildings;
    public Tile grass;
    public Tile sand;
    public Tile water;

    public MapGenerator() {

	grass = new Tile("grass");
	grass.groundFactor = 1;
	sand = new Tile("sand");
	sand.groundFactor = 3;
	water = new Tile("Wasser_versuch");
	water.groundFactor = 2;
	map = new Tile[x][y];
	buildings = new Building[x][y];

	for (int i = 0; i < x; i++) {

	    for (int k = 0; k < y; k++) {

		map[i][k] = grass;

	    }

	}

    }

    public Tile[][] getMap() {

	return map;

    }

    public Building[][] getBuildings() {

	return buildings;

    }

    public int getMapWidth() {
	return x;
    }

    public int getMapHeight() {
	return y;
    }

    public void generateAll() {

	generateMapOutside();
	generateMapInside();
	generateBuildings();

    }

    public void generateMapOutside() {

	ArrayList<Point> list = new ArrayList<Point>();

	for (int i = 1; i <= averageSideWater; i++) {

	    for (int k = 1; k <= i; k++) {

		for (int l = i; l >= k; l--) {

		    list.add(new Point(i - k, i - l));
		    map[i - k][i - l] = water;

		}

	    }

	}

    }

    public void generateMapInside() {

	/**
	 * This method will basically generate the map for the game. All
	 * generation parts create a field only one size big at the moment
	 */

	for (int i = 1; i <= seas; i++) {

	    int xWater = (int) (Math.random() * 32 + 8);
	    int yWater = (int) (Math.random() * 22 + 5);

	    map[xWater][yWater] = water;
	    map[xWater + 1][yWater] = water;
	    map[xWater + 1][yWater + 1] = water;
	    map[xWater + 1][yWater - 1] = water;
	    map[xWater + 2][yWater] = water;
	    map[xWater + 2][yWater + 1] = water;
	    map[xWater + 2][yWater + 2] = water;
	    map[xWater + 2][yWater - 1] = water;
	    map[xWater + 2][yWater - 2] = water;
	    map[xWater + 3][yWater] = water;
	    map[xWater + 3][yWater - 1] = water;
	    map[xWater + 3][yWater - 2] = water;
	    map[xWater + 3][yWater - 3] = water;
	    map[xWater + 3][yWater + 1] = water;
	    map[xWater + 4][yWater] = water;
	    map[xWater + 4][yWater - 1] = water;
	    map[xWater + 4][yWater - 2] = water;
	    map[xWater + 5][yWater - 1] = water;

	}

	for (int i = 1; i <= sandbanks; i++) {

	    int xSand = (int) (Math.random() * 32 + 8);
	    int ySand = (int) (Math.random() * 22 + 5);

	    map[xSand][ySand] = sand;
	    map[xSand + 1][ySand] = sand;
	    map[xSand + 1][ySand + 1] = sand;
	    map[xSand + 1][ySand - 1] = sand;
	    map[xSand + 2][ySand] = sand;
	    map[xSand + 2][ySand + 1] = sand;
	    map[xSand + 2][ySand + 2] = sand;
	    map[xSand + 2][ySand - 1] = sand;
	    map[xSand + 2][ySand - 2] = sand;
	    map[xSand + 3][ySand] = sand;
	    map[xSand + 3][ySand - 1] = sand;
	    map[xSand + 3][ySand - 2] = sand;
	    map[xSand + 3][ySand - 3] = sand;
	    map[xSand + 3][ySand + 1] = sand;
	    map[xSand + 4][ySand] = sand;
	    map[xSand + 4][ySand - 1] = sand;
	    map[xSand + 4][ySand - 2] = sand;
	    map[xSand + 5][ySand - 1] = sand;
	}

    }

    public void generateBuildings() {

    }

}
