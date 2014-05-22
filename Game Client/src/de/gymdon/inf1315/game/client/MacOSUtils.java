package de.gymdon.inf1315.game.client;

import java.awt.Window;
import java.lang.reflect.Method;

//import com.apple.eawt.*;
//import com.apple.eawt.AppEvent.FullScreenEvent;

public class MacOSUtils {

    private Window window;

    public MacOSUtils(Window window) {
	try {
	    Class<?> fullScreenUtils = Class.forName("com.apple.eawt.FullScreenUtilities");
	    Method setWindowCanFullScreen = fullScreenUtils.getMethod("setWindowCanFullScreen", Window.class, Boolean.TYPE);
	    setWindowCanFullScreen.invoke(null, window, true);
	    /*	Can this be done with reflection?
	     	FullScreenUtilities.addFullScreenListenerTo(window,
		    new FullScreenListener() {

			@Override
			public void windowExitingFullScreen(FullScreenEvent arg0) {
			}

			@Override
			public void windowExitedFullScreen(FullScreenEvent arg0) {
			    Client.instance.preferences.video.fullscreen = false;
			}

			@Override
			public void windowEnteringFullScreen(
				FullScreenEvent arg0) {
			}

			@Override
			public void windowEnteredFullScreen(FullScreenEvent arg0) {
			    Client.instance.preferences.video.fullscreen = true;
			}
		    });
		    
	     */
	} catch (Exception e) {
	    e.printStackTrace();
	}
	this.window = window;
    }

    public void setFullscreen(boolean fullscreen) {
	if (Client.instance.preferences.video.fullscreen == fullscreen) {
	    try {
		Class<?> application = Class.forName("com.apple.eawt.Application");
		Method getApplication = application.getMethod("getApplication");
		Object app = getApplication.invoke(null);
		Method requestToggleFullScreen = application.getMethod("requestToggleFullScreen", Window.class);
		requestToggleFullScreen.invoke(app, window);
	    } catch(Exception e) {
		e.printStackTrace();
	    }
	}
    }

    public static boolean iMacOS() {
	return System.getProperty("os.name").equals("Mac OS X");
    }
}
