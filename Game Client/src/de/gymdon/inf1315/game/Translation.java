package de.gymdon.inf1315.game;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.*;

import com.google.gson.Gson;

public class Translation {

    private Map<String, Object> data = new HashMap<String,Object>();
    private Map<String, String> translations = new HashMap<String,String>();
    public Font font = Font.decode(Font.SANS_SERIF);
    
    public Translation(String lang) {
	init();
	load(lang);
    }
    
    public Translation(Reader reader) {
	init();
	load(reader);
    }
    
    private void init() {
	data.put("number.format", "arabic");
	data.put("font", "Helvetica,sans-serif");
    }
    
    public void load(String lang) {
	try {
	    load(new InputStreamReader(Translation.class.getResourceAsStream("/lang/" + lang + ".json"), Charset.forName("UTF-8")));
	}catch(Exception e) {
	    System.err.println("Couldn't load language \"" + lang + "\"");
	}
    }
    
    public void load(Reader reader) {
	Translation t = new Gson().fromJson(reader, Translation.class);
	translations.putAll(t.translations);
	if(t.data != null)
	    data.putAll(t.data);
	String[] available = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	for(String family : ((String)data.get("font")).split(","))
	    if(Arrays.binarySearch(available, family) >= 0) {
		this.font = Font.decode(family);
		break;
	    }
    }
    
    public void reload(String lang) {
	translations.clear();
	data.clear();
	init();
	load(lang);
    }
    
    public void reload(Reader reader) {
	translations.clear();
	data.clear();
	init();
	load(reader);
    }
    
    public String translate(String code, Object... args) {
	if(translations.containsKey(code))
	    return String.format(translations.get(code), args);
	if(code == null || code.equals(""))
	    return "";
	if(translations.containsKey("translation.missing"))
	    System.err.println(translate("translation.missing", code));
	else
	    System.err.println("translation.missing[" + code + "]");
	return code + (args.length > 0 ? Arrays.toString(args) : "");
    }
}
