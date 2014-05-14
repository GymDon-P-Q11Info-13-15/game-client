package de.gymdon.inf1315.game;

import java.util.Random;

public class GameMechanics {
    Random r = new Random();
    Tile[][] map;
    Building[][] buildings;
    Unit[][] units;
    boolean[][] tempRange;
    boolean won;
    int round;

    public GameMechanics(int x, int y) { // neue Welt mit Breite x und Höhe y
	map = new Tile[x][y];
	buildings = new Building[x][y];
	units = new Unit[x][y];
	won = false;
	round = 0;
    }

    public void setTile(Tile t, int x, int y) { // zum Map-Bauen
	map[x][y] = t;
    }

    public void setMap(Tile[][] t) {
	map = t;
    }

    public void run() {

	while (!won) { // Ablauf EINER Spielrunde (was ein Spieler machen darf) (Bauen -> Bewegen -> Kaempfen)
	    round++;
	}

    }

    /**
     * Moves a unit to a field if possible
     * @param u Unit
     * @param x x-coordinate of the field to move to
     * @param y y-coordinate of the field to move to
     * @return true if move was possible, false otherwise
     */
    public boolean move(Unit u, int x, int y) {
	getAccessibleFields(u);
	if (tempRange[x][y] == true) {
	    u.x = x;
	    u.y = y;
	    return true;
	}else
	    return false;

	/*
	 * int xold = u.x; // Bisherige Koordinaten der Unit int yold = u.y; int
	 * spd = u.getSpeed(); // Speed der Unit int effspd = (int) Math.abs((x
	 * - xold) + (y - yold - 1)); // Effektiv // benoetigte // Speed, um //
	 * zum neuen // Feld zu // gelangen // (Feldmalus // einberechnet)
	 * 
	 * if (effspd < 1) { effspd = 1;
	 * 
	 * }
	 * 
	 * if (effspd <= spd) {
	 * 
	 * }
	 */

    }

    public void getAccessibleFields(Unit a) {
	tempRange = new boolean[map.length][map.length];
	step(a.getSpeed(), a.x, a.y);

    }

    public void step(int actualSpeed, int x, int y) {

	if (map[x][y].isWalkable) {
	    int[] position = new int[2];
	    int newSpeed = actualSpeed - map[x][y].groundFactor;

	    if (newSpeed >= 1) {

		tempRange[x][y] = true;
		step(newSpeed, x - 1, y);
		step(newSpeed, x + 1, y);
		step(newSpeed, x, y + 1);
		step(newSpeed, x, y - 1);
	    } else if (newSpeed > 0) {
		newSpeed = 1;
		tempRange[x][y] = true;
		step(newSpeed, x - 1, y);
		step(newSpeed, x + 1, y);
		step(newSpeed, x, y + 1);
		step(newSpeed, x, y - 1);
	    } else { // Movement-points used -> field not accessible

	    }

	} else { // Field not walkable

	}

    }

    public void combat(Unit attacker, Unit defender, int round) {
	if (round < 3) {
	    if (attacker.range > defender.range) // Prüfen ob der Verteidiger
						 // sich wehren kann
	    {
		if (attacker.attack + attacker.hp >= defender.defense
			* defender.hp * 0.9)
		    defender.setHP(defender.hp
			    - (int) (attacker.attack * attacker.hp / 10
				    * (80 + r.nextInt(41) / 100) - defender.defense
				    * defender.hp / 10 * 0.9));// Schadensberechnung(w.i.p)
	    } else {
		if (attacker.attack + attacker.hp >= defender.defense
			* defender.hp * 0.9)
		    defender.setHP(defender.hp
			    - (int) (attacker.attack * attacker.hp / 10
				    * (80 + r.nextInt(41) / 100) - defender.defense
				    * defender.hp / 10 * 0.9));
		if (defender.attack + defender.hp >= attacker.defense
			* attacker.hp * 0.9)
		    attacker.setHP(attacker.hp
			    - (int) (defender.attack * defender.hp / 10
				    * (80 + r.nextInt(41) / 100) - attacker.defense
				    * attacker.hp / 10 * 0.9));

		if (defender.hp > 0 && attacker.hp > 0) {
		    combat(attacker, defender, round + 1);
		}
	    }
	}
    }
}
