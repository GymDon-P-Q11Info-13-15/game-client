package de.gymdon.inf1315.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import de.gymdon.inf1315.game.client.Client;

public class GameMechanics implements ActionListener {
    Random r = new Random();
    Tile[][] map;
    Building[][] buildings;
    Unit[][] units;
    boolean[][] tempRange;
    boolean won;
    int round;
    int phase;

    /**
     * Map, Buildings etc wird alle im MapGenerator generiert
     * Da braucht ihr hier in den GameMechanics nichts machen
     * Ich denk mal ein Objekt von GameMechanics wird im Client erzeugt,
     * dann wird da die Karte etc gleich übergeben. (?)
     */
    
    public GameMechanics() { // neue Welt mit Breite x und Höhe y
	//this.map = Client.instance.map;
	//buildings = Client.instance.buildings;
	//units = Client.instance.units;
	won = false;
	round = 0;
	phase = 0;
    }
    
    
    /**
     * Set a Map as internal Tile Array
     * 
     * @param t 
     * 		Tile Array as new Map
     */
    public void setMap(Tile[][] t) {
	map = t;
    }

    public void run() {

	while (!won) { // Ablauf EINER Spielrunde (was ein Spieler machen darf)
		       // (Bauen -> Bewegen -> Kaempfen)
	    round++;
	}

    }
    
    public void nextPhase(){
	phase++;
	phase=phase%6;
	
	
    }
    
    public void clicked(int x, int y){
	if(x>=0 && y>=0){
	    if(units[x][y]!=null){
		//units[x][y].clicked();
	    }
	    
	}
    }

    /**
     * Build a new building if possible
     * 
     * @param b
     *            Building (attention to building type)
     * @param x
     *            x-coordinate of the field to build on
     * @param y
     *            y-coordinate of the field to build on
     * 
     */
    public void buildBuilding(Building b, int x, int y) {
	if (x >= 0 && y >= 0) {
	    // check player's gold!
	    if (buildings[x][y] == null) {
		buildings[x][y] = b;
	    }
	} else {
	    throw new IllegalArgumentException("Field position must be positive");
	}
    }

    /**
     * Moves a unit to a field if possible
     * 
     * @param u
     *            Unit
     * @param x
     *            x-coordinate of the field to move to
     * @param y
     *            y-coordinate of the field to move to
     * @return true if move was possible, false otherwise
     */
    public boolean move(Unit u, int x, int y) {
	getAccessableFields(u);
	if (tempRange[x][y] == true) {
	    u.x = x;
	    u.y = y;
	    return true;
	} else
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

    public void getAccessableFields(Unit a) {
	tempRange = new boolean[Client.instance.map.length][Client.instance.map[0].length];
	step(a.getSpeed(), a.x, a.y);

    }
    
    public boolean isAccessable(Unit u, int x, int y){
	getAccessableFields(u);
	if(tempRange[x][y]==true){
	    return true;
	}
	else{
	    return false;
	}
	
    }

    private void step(int actualSpeed, int x, int y) {

	if (map[x][y].isWalkable() && buildings[x][y]==null) { 			//can only walk if no building or walkable
	    int newSpeed = actualSpeed - map[x][y].getGroundFactor();

	    if (newSpeed >= 1) {

		tempRange[x][y] = true;
		step(newSpeed, x - 1, y);
		step(newSpeed, x + 1, y);
		step(newSpeed, x, y + 1);
		step(newSpeed, x, y - 1);
	    } else if (newSpeed > 0) {
		tempRange[x][y] = true;
		step(1, x - 1, y);
		step(1, x + 1, y);
		step(1, x, y + 1);
		step(1, x, y - 1);
	    } else { // Movement-points used -> field not accessible

	    }

	} else { // Field not walkable

	}

    }

    /*
     * public void buildUnit(Player p,Unit u,int number,Building b){ if(p.gold <
     * u.cost*number) {
     * 
     * }
     * 
     * 
     * }
     */
    /*public int getassist(Unit u){
    Unit x;
    
    if (x!=null){
    return  (int)(Math.round(x.attack * x.combined)) ;
    }
    else{
    return 0;	
    }
    }
    */
    public int strikechance(Unit striker, Unit stroke) {
	// Berechnet eine zahl die der Rng überschreiten muss um zu treffen
	int attchance = 80 - (striker.attack + striker.hp / 4 - stroke.defense / 2 - stroke.hp / 4);
	System.out.println(attchance);
	if (attchance < 0) {
	    return 0;
	} else if (attchance > 75) {
	    return 75;
	} else {
	    return attchance;
	}
    }

    public void combat(Unit attacker, Unit defender, int round) {

	if (round < 100) {
	    if (attacker.range > defender.range)
	    // Prüfen ob der Verteidiger sich wehren kann
	    {
		defender.setHP(defender.hp - r.nextInt(attacker.attack) * attacker.hp / 100);
		// ranged Schadensberechnung wip
		
		return;
	    } else {
		if (r.nextInt(81) >= strikechance(attacker, defender)) {
		    defender.setHP(defender.hp - 1);
		}
		// Rng Wert muss ausgerechneten Wert überschreiten um für 1 zu
		// striken
		if (r.nextInt(81) >= strikechance(defender, attacker)) {
		    attacker.setHP(attacker.hp - 1);
		}
		System.out.println("round " + round + " defhp " + defender.hp + " atkhp " + attacker.hp);
		// Nur zu Testzwecken wird später noch entfernt
	    }
	    if (defender.hp > 0 && attacker.hp > 0) {
		combat(attacker, defender, round + 1);
	    }
	}
    }
    public void pillage(Unit u,Building b)
    {
     if(r.nextInt(101)>=b.defense){
	b.hp=b.hp-u.attack*125*(int)((75+r.nextInt(51))/100); 
     }
	
    }
    @Override
    public void actionPerformed(ActionEvent e) {
	String s = e.paramString();

    }
}
