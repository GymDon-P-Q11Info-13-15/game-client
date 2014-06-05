package de.gymdon.inf1315.game.client;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.gymdon.inf1315.game.Utils;

public class Preferences {
    public static final int CURRENT_VERSION = 4;
    public int version = CURRENT_VERSION;
    public String language = "en_US";
    public VideoSettings video = new VideoSettings();
    public GameSettings game = new GameSettings();
    
    public Preferences() {
	String lang = Locale.getDefault().toLanguageTag();
	if(Preferences.class.getResourceAsStream("/lang/" + lang + ".json") != null)
	    this.language = lang;
    }
    
    public class VideoSettings {
	public boolean vsync = true;
	public boolean fullscreen = false;
    }
    
    public class GameSettings {
	public int arrow = 0;
	public boolean invertZoom = false;
    }

    public void write(Writer writer) throws IOException {
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	gson.toJson(this, writer);
	writer.flush();
    }

    public void read(Reader reader) {
	Preferences np = Preferences.readNew(reader);
	this.language = np.language;
	this.video = np.video;
	this.game = np.game;
    }
    
    private Preferences init() {
	try {
	    List<String> langs = Utils.getResourceListing(Preferences.class.getClassLoader(), "/lang/");
	    langs.remove("/langs/languages.json");
	    if(!langs.contains("/lang/" + language + ".json")) {
		String last = this.language;
		for(String lang : langs)
		    if(lang.startsWith("/lang/" + language + "_")) {
			language = lang.substring(6);
			break;
		    }
		if(!langs.contains("/lang/" + language + ".json"))
		    language = new Preferences().language;
		System.out.println(last + " -> " + language);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return this;
    }

    public static Preferences readNew(Reader reader) {
	Gson gson = new Gson();
	return gson.fromJson(reader, Preferences.class).init();
    }
}
