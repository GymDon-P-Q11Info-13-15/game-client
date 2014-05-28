package de.gymdon.inf1315.game.render.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ListAdapter<T> implements GuiAdapter {
    private List<T> list;
    private Map<GuiScrollList, Map<Integer, Gui>> guiCache = new HashMap<GuiScrollList, Map<Integer, Gui>>();
    private Map<GuiScrollList, Map<Integer, Integer>> heightCache = new HashMap<GuiScrollList, Map<Integer, Integer>>();
    private Map<GuiScrollList, Map<Integer, Integer>> widthCache = new HashMap<GuiScrollList, Map<Integer, Integer>>();

    public ListAdapter(List<T> list) {
	this.list = list;
    }

    @Override
    public int getHeight(int index, GuiScrollList parent) {
	if(heightCache.containsKey(parent) && heightCache.get(parent).containsKey(index))
	    return heightCache.get(parent).get(index);
	int height = getHeight(list.get(index), parent);
	if(!heightCache.containsKey(parent))
	    heightCache.put(parent, new HashMap<Integer,Integer>());
	heightCache.get(parent).put(index, height);
	return height;
    }

    @Override
    public int getWidth(int index, GuiScrollList parent) {
	if(widthCache.containsKey(parent) && widthCache.get(parent).containsKey(index))
	    return widthCache.get(parent).get(index);
	int width = getHeight(list.get(index), parent);
	if(!widthCache.containsKey(parent))
	    widthCache.put(parent, new HashMap<Integer,Integer>());
	widthCache.get(parent).put(index, width);
	return width;
    }

    @Override
    public Gui get(int index, GuiScrollList parent) {
	if(guiCache.containsKey(parent) && guiCache.get(parent).containsKey(index))
	    return guiCache.get(parent).get(index);
	Gui gui = get(list.get(index), parent);
	if(!guiCache.containsKey(parent))
	    guiCache.put(parent, new HashMap<Integer,Gui>());
	guiCache.get(parent).put(index, gui);
	return gui;
    }

    public abstract int getHeight(T element, GuiScrollList parent);

    public abstract int getWidth(T element, GuiScrollList parent);

    public abstract Gui get(T element, GuiScrollList parent);
    
    @Override
    public int getLength(GuiScrollList parent) {
	return list.size();
    }
    
    public void clear() {
	guiCache.clear();
	heightCache.clear();
	widthCache.clear();
    }
}
