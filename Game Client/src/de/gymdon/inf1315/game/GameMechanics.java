package de.gymdon.inf1315.game;



public class GameMechanics {

    Texture[][] field;
    Building[][] buildings;
    Unit[][] units;
    boolean won;
    int round;
    
    public GameMechanics(int x, int y){
	field= new Texture[x][y];
	buildings= new Building[x][y];
	units= new Unit[x][y];
	won= false;
	round=0;
    }
    
    public void run(){
	
	while(won==false){		//Ablauf EINER Spielrunde (was ein Spieler machen darf)
	    				//Bauen -> Bewegen -> Kämpfen
	    
	    
	    
	 round++;   
	}
	
    }
    
    public void move(Unit u, int x, int y){        //Abfrage, ob Bewegung möglich ist -> Änderung der Position
	int xold= u.x;		//Bisherige Koordinaten der Unit
	int yold= u.y;
	int spd= u.getSpeed();    //Speed der Unit
	int effspd =(int) Math.abs((x-xold)+(y-yold-1)) ; //Effektiv benötigte Speed, um zum neuen Feld zu gelangen (Feldmalus einberechnet)
	
	if(effspd<1){effspd=1;}
	
	if(effspd<=spd){
	    
	}
	
	
    }
    
    
}
