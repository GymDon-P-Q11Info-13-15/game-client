package de.gymdon.inf1315.game;

public class GameMechanics {

    Tile[][] map;
    Building[][] buildings;
    Unit[][] units;
    boolean won;
    int round;

    public GameMechanics(int x, int y) {
	map = new Tile[x][y];
	buildings = new Building[x][y];
	units = new Unit[x][y];
	won = false;
	round = 0;
    }

    public void run() {

	while (!won) { // Ablauf EINER Spielrunde (was ein Spieler machen darf)
		       // Bauen -> Bewegen -> Kaempfen

	    round++;
	}

    }

    public void move(Unit u, int x, int y) { // Abfrage, ob Bewegung moeglich
					     // ist -> Aenderung der Position
	int xold = u.x; // Bisherige Koordinaten der Unit
	int yold = u.y;
	int spd = u.getSpeed(); // Speed der Unit
	int effspd = (int) Math.abs((x - xold) + (y - yold - 1)); // Effektiv
								  // benoetigte
								  // Speed, um
								  // zum neuen
								  // Feld zu
								  // gelangen
								  // (Feldmalus
								  // einberechnet)

	if (effspd < 1) {
	    effspd = 1;
	    
	}

	if (effspd <= spd) {

	}
	
    }
	
    
    public int[][] getAccesableFields(Unit a){
	
	
	
    }
	
    public boolean[][] step(int actualSpeed, int x, int y){
	
	
	if(map[x][y].isWalkable==true){
	    boolean [][] position = new boolean[map.length][map.length];
	int newSpeed = actualSpeed - map[x][y].groundFactor;
	
	
	if(newSpeed>1){
	    position[x][y]=true;
	    return position;
	}
	
	else if(newSpeed>0&&newSpeed<1){
	    newSpeed=1;
	}
	
	else if(newSpeed<0){
	    return null;
	}
	
	
	step(newSpeed, x-1,y);
	step(newSpeed, x+1,y);
	step(newSpeed, x,y+1);
	step(newSpeed, x,y-1);
	}
	
    }
	
    
    
    }

