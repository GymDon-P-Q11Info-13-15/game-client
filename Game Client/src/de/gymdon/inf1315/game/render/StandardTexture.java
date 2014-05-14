package de.gymdon.inf1315.game.render;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class StandardTexture implements Texture {

    private static Map<String,Texture> map = new HashMap<String, Texture>();
    private BufferedImage image;
    
    public StandardTexture(BufferedImage image) {
	this.image = image;
    }
    
    public StandardTexture(String name) {
	try {
	    this.image = ImageIO.read(StandardTexture.class.getResourceAsStream("/textures/" + name + ".png"));
	    map.put(name, this);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    @Override
    public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
	return false;
    }

    @Override
    public int getX() {
	return 0;
    }

    @Override
    public int getY() {
	return 0;
    }

    @Override
    public int getWidth() {
	return image.getWidth();
    }

    @Override
    public int getHeight() {
	return image.getHeight();
    }

    @Override
    public BufferedImage getImage() {
	return image;
    }

    public static Texture get(String name) {
	if(map.containsKey(name))
	    return map.get(name);
	return new StandardTexture(name);
    }
}
