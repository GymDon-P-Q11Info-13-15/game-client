package de.gymdon.inf1315.game.client;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;

import de.gymdon.inf1315.game.Translation;
import de.gymdon.inf1315.game.render.GameCanvas;
import de.gymdon.inf1315.game.render.gui.GuiMainMenu;
import de.gymdon.inf1315.game.render.gui.GuiScreen;
import de.gymdon.inf1315.game.client.Preferences;

public class Client implements Runnable, WindowListener {
    public static final boolean DEBUG = true;
    public static final String TITLE = "Game Title";
    public static final String VERSION = "Alpha 0.0.1";
    public static Client instance;
    private boolean running = false;
    private JFrame frame;
    private GameCanvas canvas;
    private int ticksRunning = 0;
    private int tps = 0;
    private int fps = 0;
    public GuiScreen currentScreen;
    public Translation translation;
    public Preferences preferences;
    public Random random = new Random();

    public Client() {
	Client.instance = this;
	frame = new JFrame(TITLE);
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

	new Thread(){
	    public void run() {
		init();
	    }
	}.start();

	while (running) {
	    long now = System.nanoTime();
	    unprocessed += (now - lastTime) / nsPerTick;
	    lastTime = now;
	    boolean shouldRender = preferences != null && !preferences.video.vsync;
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
		if (DEBUG)
		    frame.setTitle(TITLE + " - " + ticks + "TPS " + frames
			    + "FPS");
		this.tps = ticks;
		this.fps = frames;
		frames = 0;
		ticks = 0;
	    }
	}
	cleanUp();
    }

    private void init() {
	translation = new Translation("en");
	readPreferences();
	if(!preferences.language.equals("en"))
	    translation.load(preferences.language);
	setGuiScreen(new GuiMainMenu());
	System.out.println("Started \"" + TITLE + " " + VERSION + "\"");
    }
    
    public void reload() {
	translation = new Translation("en");
	readPreferences();
	if(!preferences.language.equals("en"))
	    translation.load(preferences.language);
    }
    
    private void readPreferences() {
	File f = new File("preferences.json");
	try {
	    if (f.exists())
		preferences = Preferences.readNew(new FileReader(f));
	    else {
		preferences = new Preferences();
		f.createNewFile();
		preferences.write(new FileWriter(f));
		System.out.println(translation.translate("file.created", "preferences.json"));
	    }
	    if(preferences.version != Preferences.CURRENT_VERSION) {
		preferences.version = Preferences.CURRENT_VERSION;
		preferences = new Preferences();
		f.createNewFile();
		preferences.write(new FileWriter(f));
		System.out.println(translation.translate("updated.version","preferences.json", preferences.version));
	    }
	} catch (IOException e) {
	    throw new RuntimeException("Preferences couldn't be loaded/saved", e);
	}
    }

    private void tick() {
	if(currentScreen != null)
	    currentScreen.tick();
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
	new Thread(new Client()).start();
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
	stop();
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    public void setGuiScreen(GuiScreen newScreen) {
	if(currentScreen != null) {
	    canvas.removeMouseListener(currentScreen);
	    canvas.removeMouseMotionListener(currentScreen);
	}
	currentScreen = newScreen;
	if(currentScreen != null) {
	    canvas.addMouseListener(currentScreen);
	    canvas.addMouseMotionListener(currentScreen);
	}
    }

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
