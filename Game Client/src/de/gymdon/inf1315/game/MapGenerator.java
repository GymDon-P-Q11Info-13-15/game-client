package de.gymdon.inf1315.game;

import java.util.Random;

public class MapGenerator {

    private int mapWidth = 48;
    private int mapHeight = 32;

    private int mines = 4;
    private int superiorMines = 1;
    private int lakes = 2;
    private int sandbanks = 3;
    private int avgLakeSize = 20;
    private int avgSandbankSize = 14;
    private int averageSideWater = 5;

    public static Tile[][] map;
    public Building[][] buildings;
    public final long seed;
    public Random random;

    public MapGenerator() {
	this(new Random().nextLong());
    }

    public MapGenerator(long seed) {
	this.seed = seed;
	this.random = new Random(seed);
    }

    public Tile[][] getMap() {
	if (map == null)
	    generateAll();
	return map;
    }

    public Building[][] getBuildings() {
	if (buildings == null)
	    generateAll();
	return buildings;
    }

    public int getMapWidth() {
	return mapWidth;
    }

    public int getMapHeight() {
	return mapHeight;
    }

    public void generateAll() {
	random.setSeed(seed);

	map = new Tile[mapWidth][mapHeight];
	for (int i = 0; i < mapWidth; i++) {
	    for (int k = 0; k < mapHeight; k++) {
		map[i][k] = Tile.grass;
	    }
	}
	// generateMapOutside();
	generateMapInside();
	generateBuildings();
    }

    public void generateMapOutside() {
	/**
	 * This method is not finished yet It is to create the outline of the
	 * map
	 */

	for (int i = 1; i <= averageSideWater; i++) {
	    for (int k = 1; k <= i; k++) {
		for (int l = i; l >= k; l--) {
		    map[i - k][i - l] = Tile.water;
		}
	    }
	}
    }

    /**
     * This method will basically generate the map for the game.
     */
    public void generateMapInside() {
	int[] xLakes = new int[lakes];
	int[] yLakes = new int[lakes];
	int tries = 0;
	for (int i = 0; i < lakes; i++) {
	    int xLake = random.nextInt(mapWidth - avgLakeSize) + avgLakeSize / 2;
	    int yLake = random.nextInt(mapHeight - avgLakeSize) + avgLakeSize / 2;
	    for (;;) {
		boolean near = false;
		for (int j = 0; j < i; j++) {
		    int a = xLake - xLakes[j];
		    int b = yLake - yLakes[j];
		    near |= a * a + b * b < avgLakeSize * avgLakeSize;
		}
		if (!near || tries++ > 4)
		    break;
		xLake = random.nextInt(mapWidth - avgLakeSize) + avgLakeSize / 2;
		yLake = random.nextInt(mapHeight - avgLakeSize) + avgLakeSize / 2;
	    }
	    xLakes[i] = xLake;
	    yLakes[i] = yLake;
	    int xDir, yDir;

	    for (int j = 0; j < avgLakeSize / 4; j++) {
		do {
		    xDir = random.nextInt(2) - 1;
		    yDir = random.nextInt(2) - 1;
		} while (xDir == 0 && yDir == 0);
		try {
		    for (int k = 0; k < random.nextGaussian() * avgLakeSize; k++) {
			map[xLake][yLake] = Tile.water;
			map[xLake][yLake + 1] = Tile.water;
			map[xLake][yLake - 1] = Tile.water;
			map[xLake + 1][yLake] = Tile.water;
			map[xLake - 1][yLake] = Tile.water;
			if (random.nextBoolean())
			    map[xLake + 1][yLake + 1] = Tile.water;
			if (random.nextBoolean())
			    map[xLake + 1][yLake - 1] = Tile.water;
			if (random.nextBoolean())
			    map[xLake - 1][yLake + 1] = Tile.water;
			if (random.nextBoolean())
			    map[xLake - 1][yLake - 1] = Tile.water;
			xLake += xDir;
			yLake += yDir;
		    }
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	    }
	}

	int[] xSands = new int[sandbanks];
	int[] ySands = new int[sandbanks];
	tries = 0;
	for (int i = 0; i < sandbanks; i++) {
	    int xSand = random.nextInt(mapWidth - avgSandbankSize) + avgSandbankSize / 2;
	    int ySand = random.nextInt(mapHeight - avgSandbankSize) + avgSandbankSize / 2;
	    for (;;) {
		boolean near = false;
		for (int j = 0; j < i; j++) {
		    int a = xSand - xSands[j];
		    int b = ySand - ySands[j];
		    near |= a * a + b * b < avgSandbankSize * avgSandbankSize;
		}
		for (int j = 0; j < i; j++) {
		    int a = xSand - xLakes[j];
		    int b = ySand - yLakes[j];
		    near |= a * a + b * b < avgSandbankSize * avgSandbankSize;
		}
		if (!near || tries++ > 4)
		    break;
		xSand = random.nextInt(mapWidth - avgSandbankSize) + avgSandbankSize / 2;
		ySand = random.nextInt(mapHeight - avgSandbankSize) + avgSandbankSize / 2;
	    }
	    xSands[i] = xSand;
	    ySands[i] = ySand;
	    int xDir, yDir;

	    for (int j = 0; j < avgSandbankSize / 4; j++) {
		do {
		    xDir = random.nextInt(2) - 1;
		    yDir = random.nextInt(2) - 1;
		} while (xDir == 0 && yDir == 0);
		try {
		    for (int k = 0; k < random.nextGaussian() * avgSandbankSize; k++) {
			map[xSand][ySand] = Tile.sand;
			map[xSand][ySand + 1] = Tile.sand;
			map[xSand][ySand - 1] = Tile.sand;
			map[xSand + 1][ySand] = Tile.sand;
			map[xSand - 1][ySand] = Tile.sand;
			if (random.nextBoolean())
			    map[xSand + 1][ySand + 1] = Tile.sand;
			if (random.nextBoolean())
			    map[xSand + 1][ySand - 1] = Tile.sand;
			if (random.nextBoolean())
			    map[xSand - 1][ySand + 1] = Tile.sand;
			if (random.nextBoolean())
			    map[xSand - 1][ySand - 1] = Tile.sand;
			xSand += xDir;
			ySand += yDir;
		    }
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	    }
	}
    }

    public void generateBuildings() {
	buildings = new Building[mapWidth][mapHeight];

	// Generate superiorMines
	for (int i = 0; i < superiorMines; i++) {
	    int xSMine = (int) ((mapWidth / 2) - 4 + random.nextInt(8));
	    int ySMine = (int) ((mapHeight / 2) - 3 + random.nextInt(6));

	    if (map[xSMine][ySMine] != Tile.grass || marginBuildings(xSMine, ySMine, 5)) {
		i--;
	    } else {
		Mine m = new Mine(xSMine, ySMine);
		m.superior = true;
		buildings[xSMine][ySMine] = m;
	    }
	}

	// Generate Mines
	for (int i = 0; i < mines; i++) {
	    int xMine = (int) (random.nextInt(mapWidth - 16) + 8);
	    int yMine = (int) (random.nextInt(mapHeight - 8) + 4);

	    if (map[xMine][yMine] != Tile.grass || marginBuildings(xMine, yMine, 5)) {
		i--;
	    } else {
		Mine m = new Mine(xMine, yMine);
		m.superior = false;
		buildings[xMine][yMine] = m;
	    }
	}

	// Generate Castles
	buildings[1][mapHeight / 2 - 1] = new Castle(null, 1, mapHeight / 2 - 1);
	buildings[mapWidth - 3][mapHeight / 2 - 1] = new Castle(null, mapWidth - 3, mapHeight / 2 - 1);
    }

    private boolean marginBuildings(int x, int y, int m) {
	for (int dx = x - m; dx < x + m; dx++) {
	    for (int dy = y - m; dy < y + m; dy++) {
		if (dx > 0 && dy > 0 && buildings[dx][dy] != null) {
		    return true;
		}
	    }
	}
	return false;
    }
}
