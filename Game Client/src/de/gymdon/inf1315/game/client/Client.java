package de.gymdon.inf1315.game.client;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.gymdon.inf1315.game.*;
import de.gymdon.inf1315.game.packet.*;
import de.gymdon.inf1315.game.render.*;
import de.gymdon.inf1315.game.render.gui.*;

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
    public MacOSUtils macOsUtils;
    public List<Remote> remotes = new ArrayList<Remote>();
    public Map<Remote, Thread> remoteThreads = new HashMap<Remote, Thread>();
    public MapRenderer mapren;
    public MapGenerator mapgen;
    public Tile[][] map;
    public Building[][] buildings;
    public Unit[][] units;
    public GameMechanics gm;
    

    public Client() {
	//gm = new GameMechanics(); //You throw a NullPointerException
	mapgen = new MapGenerator();
	mapren = new MapRenderer();
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

	new Thread() {
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
		if (DEBUG) {
		    if(translation != null) {
			frame.setTitle(translation.translate("game.title") + " - " + ticks + "TPS " + frames + "FPS");
			System.out.println(translation.translate("game.title") + " - " + ticks + "TPS " + frames + "FPS");
		    }
		}
		this.tps = ticks;
		this.fps = frames;
		frames = 0;
		ticks = 0;
	    }
	}
	cleanUp();
    }

    private void init() {
	translation = new Translation("en_US");
	readPreferences();
	translation.load(preferences.language);
	setGuiScreen(new GuiMainMenu());
	if (MacOSUtils.iMacOS())
	    macOsUtils = new MacOSUtils(frame);
	setFullscreen(preferences.video.fullscreen);
	System.out.println("Started \"" + translation.translate("game.title") + " " + VERSION + "\"");
    }

    public void setFullscreen(final boolean fullscreen) {
	/*
	 * if(macOsUtils != null) { macOsUtils.setFullscreen(fullscreen);
	 * return; }
	 */
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		GraphicsDevice screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		if (fullscreen) {
		    screen.setFullScreenWindow(frame);
		    DisplayMode[] modes = screen.getDisplayModes();
		    DisplayMode current = screen.getDisplayMode();
		    for (DisplayMode mode : modes) {
			if (mode.getWidth() * mode.getHeight() > current.getWidth() * current.getHeight() && mode.getRefreshRate() > current.getRefreshRate())
			    current = mode;
		    }
		    screen.setDisplayMode(current);
		    System.out.println("DisplayMode: " + current.getWidth() + "x" + current.getHeight() + "x" + current.getBitDepth() + " @" + current.getRefreshRate() + "Hz");
		    frame.setSize(current.getWidth(), current.getHeight());
		} else {
		    screen.setFullScreenWindow(null);
		}
	    }
	});
    }

    public void reload() {
	translation = new Translation("en_US");
	readPreferences();
	translation.load(preferences.language);
	frame.setTitle(Client.instance.translation.translate("game.title"));
	setFullscreen(preferences.video.fullscreen);
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
	    if (preferences.version != Preferences.CURRENT_VERSION) {
		preferences.version = Preferences.CURRENT_VERSION;
		preferences = new Preferences();
		f.createNewFile();
		preferences.write(new FileWriter(f));
		System.out.println(translation.translate("updated.version", "preferences.json", preferences.version));
	    }
	} catch (IOException e) {
	    throw new RuntimeException("Preferences couldn't be loaded/saved", e);
	}
    }

    private void tick() {
	for (Iterator<Remote> it = remotes.iterator(); it.hasNext();) {
	    final Remote r = it.next();
	    if (r instanceof Server && !remoteThreads.containsKey(r)) {
		Thread t = new Thread() {
		    public void run() {
			while (true) {
			    ((Server) r).processPackets();

			    try {
				Thread.sleep(10);
			    } catch (InterruptedException e) {
				e.printStackTrace();
			    }
			}
		    }
		};
		remoteThreads.put(r, t);
		t.start();
	    }
	    if (r.left() || r.getSocket().isClosed()) {
		try {
		    remoteThreads.get(r).join(10);
		} catch (InterruptedException e) {
		}
		remoteThreads.remove(r);
	    }
	}
	for (Iterator<Remote> it = remotes.iterator(); it.hasNext();) {
	    if (it.next().left())
		it.remove();
	}
	if (currentScreen != null)
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
	if (currentScreen != null) {
	    canvas.removeMouseListener(currentScreen);
	    canvas.removeMouseMotionListener(currentScreen);
	    frame.removeKeyListener(currentScreen);
	} else {
	    canvas.removeMouseListener(canvas.mapRenderer);
	    canvas.removeMouseMotionListener(canvas.mapRenderer);
	    canvas.removeMouseWheelListener(canvas.mapRenderer);
	    frame.removeKeyListener(canvas.mapRenderer);
	    canvas.mapRenderer = null;
	}
	currentScreen = newScreen;
	if (currentScreen != null) {
	    canvas.addMouseListener(currentScreen);
	    canvas.addMouseMotionListener(currentScreen);
	    frame.addKeyListener(currentScreen);
	}
    }

    public void activateMap(boolean newMap) {
	if(newMap)
	{
	    mapgen = new MapGenerator();
	    mapgen.generateAll();
	    map = mapgen.getMap();
	    buildings = mapgen.getBuildings();
	}
	setGuiScreen(null);
	canvas.mapRenderer = mapren;
	canvas.addMouseListener(canvas.mapRenderer);
	canvas.addMouseMotionListener(canvas.mapRenderer);
	canvas.addMouseWheelListener(canvas.mapRenderer);
	frame.addKeyListener(canvas.mapRenderer);
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
