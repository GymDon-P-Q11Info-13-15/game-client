package de.gymdon.inf1315.game;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Utils {
    
    public static List<String> getResourceListing(ClassLoader cl) throws URISyntaxException, IOException {
	URL dirURL = cl.getResource("");
	if (dirURL != null && dirURL.getProtocol().equals("file")) {
	    File f = new File(dirURL.toURI());
	    return recurse(f, f.getAbsolutePath().replace('\\', '/'));
	}

	if (dirURL.getProtocol().equals("jar")) {
	    String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!"));
	    JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
	    Enumeration<JarEntry> entries = jar.entries();
	    Set<String> result = new HashSet<String>();
	    while (entries.hasMoreElements())
		result.add("/" + entries.nextElement().getName().replace('\\', '/'));
	    jar.close();
	    return new ArrayList<String>(result);
	}

	throw new UnsupportedOperationException("Cannot list files for URL " + dirURL);
    }
    
    public static List<String> getResourceListing(ClassLoader cl, String prefix) throws URISyntaxException, IOException {
	List<String> l = getResourceListing(cl);
	for(Iterator<String> it = l.iterator(); it.hasNext();) {
	    String s = it.next();
	    if(!s.startsWith(prefix))
		it.remove();
	}
	return l;
    }
    
    private static List<String> recurse(File dir, List<String> append, String relative) {
	if(append == null)
	    append = new ArrayList<String>();
	for(File f : dir.listFiles()) {
	    if(f.isDirectory())
		recurse(f, append, relative);
	    else
		append.add(f.getAbsolutePath().substring(relative.length()));
	}
	return append;
    }
    
    public static List<String> recurse(File directory, String relative) {
	return recurse(directory, null, relative);
    }
    
    public static List<String> recurse(File directory) {
	return recurse(directory, null, "/");
    }
}
