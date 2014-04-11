package de.gymdon.inf1315.game;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import de.gymdon.inf1315.game.render.GameCanvas;
import de.gymdon.inf1315.game.render.gui.GuiMainMenu;

public class Game implements Runnable, WindowListener {
    public static final boolean DEBUG = true;
    public static final String TITLE = "Game";
    public static final String VERSION = "Alpha 0.0.1";
    public static Game instance;
    private boolean running = false;
    private JFrame frame;
    private GameCanvas canvas;
    private int ticksRunning = 0;
    private int tps = 0;
    private int fps = 0;

    public Game() {
	Game.instance = this;
	frame = new JFrame("Game");
	frame.setSize(1280, 720);
	frame.setMinimumSize(new Dimension(800, 600));
	frame.setPreferredSize(frame.getSize());
	frame.setLocationRelativeTo(null);
	canvas = new GameCanvas();
	frame.add(canvas);
	frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	frame.setVisible(true);
	frame.addWindowListener(this);
    }
    
    @Override
    public void run() {
	running = true;
	long lastTime = System.nanoTime();
	double unprocessed = 0;
	double nsPerTick = 1000000000.0 / 60;
	int frames = 0;
	int ticks = 0;
	long lastTimer1 = System.currentTimeMillis();

	init();

	while (running) {
	    long now = System.nanoTime();
	    unprocessed += (now - lastTime) / nsPerTick;
	    lastTime = now;
	    boolean shouldRender = true;
	    while (unprocessed >= 1) {
		ticks++;
		ticksRunning++;
		tick();
		unprocessed -= 1;
		shouldRender = true;
	    }

	    try {
		Thread.sleep(2);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }

	    if (shouldRender) {
		frames++;
		render();
	    }

	    if (System.currentTimeMillis() - lastTimer1 > 1000) {
		lastTimer1 += 1000;
		if(DEBUG)
		    frame.setTitle(TITLE + " - " + ticks + "TPS " + frames + "FPS");
		this.tps = ticks;
		this.fps = frames;
		frames = 0;
		ticks = 0;
	    }
	}
	cleanUp();
    }
    
    private void init() {
	canvas.currentScreen = new GuiMainMenu();
	System.out.println("Started \"" + TITLE + " " + VERSION + "\"");
    }
    
    private void tick() {
	canvas.currentScreen.tick();
    }
    
    private void render() {
	canvas.repaint();
    }
    
    public void stop() {
	running = false;
    }
    
    private void cleanUp() {
	System.out.println("Stopping");
	System.exit(0);
    }

    public static void main(String[] args) {
	new Thread(new Game()).start();
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
	stop();
    }

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
    
    public int getTicksRunning() {
	return ticksRunning;
    }
    
    public int getTPS() {
	return tps;
    }
    
    public int getFPS() {
	return fps;
    }
}
