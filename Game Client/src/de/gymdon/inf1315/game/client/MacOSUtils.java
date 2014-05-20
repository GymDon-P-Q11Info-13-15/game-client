package de.gymdon.inf1315.game.client;

import java.awt.Window;

import com.apple.eawt.*;
import com.apple.eawt.AppEvent.FullScreenEvent;

public class MacOSUtils {
    
    private Window window;

    public MacOSUtils(Window window) {
	FullScreenUtilities.setWindowCanFullScreen(window, true);
	FullScreenUtilities.addFullScreenListenerTo(window, new FullScreenListener() {
	    
	    @Override
	    public void windowExitingFullScreen(FullScreenEvent arg0) {
	    }
	    
	    @Override
	    public void windowExitedFullScreen(FullScreenEvent arg0) {
		Client.instance.preferences.video.fullscreen = false;
	    }
	    
	    @Override
	    public void windowEnteringFullScreen(FullScreenEvent arg0) {
	    }
	    
	    @Override
	    public void windowEnteredFullScreen(FullScreenEvent arg0) {
		Client.instance.preferences.video.fullscreen = true;
	    }
	});
	this.window = window;
    }
    
    public void setFullscreen(boolean fullscreen) {
	if(Client.instance.preferences.video.fullscreen == fullscreen)
	    Application.getApplication().requestToggleFullScreen(window);
    }
    
    public static boolean iMacOS() {
	return System.getProperty("os.name").equals("Mac OS X");
    }
}
