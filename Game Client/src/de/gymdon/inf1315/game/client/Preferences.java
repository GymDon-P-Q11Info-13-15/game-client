package de.gymdon.inf1315.game.client;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Preferences {
    public static final int CURRENT_VERSION = 3;
    public int version = CURRENT_VERSION;
    public String language = "en";
    public VideoSettings video = new VideoSettings();
    public GameSettings game = new GameSettings();
    
    public Preferences() {
	String lang = Locale.getDefault().getLanguage();
	if(Preferences.class.getResourceAsStream("/lang/" + lang + ".json") != null)
	    this.language = lang;
    }
    
    public class VideoSettings {
	public boolean vsync = true;
	public boolean fullscreen = false;
    }
    
    public class GameSettings {
	public int arrow = 0;
    }

    public void write(Writer writer) throws IOException {
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	gson.toJson(this, writer);
	writer.flush();
    }

    public void read(Reader reader) {
	Preferences np = Preferences.readNew(reader);
	this.language = np.language;
    }

    public static Preferences readNew(Reader reader) {
	Gson gson = new Gson();
	return gson.fromJson(reader, Preferences.class);
    }
}
