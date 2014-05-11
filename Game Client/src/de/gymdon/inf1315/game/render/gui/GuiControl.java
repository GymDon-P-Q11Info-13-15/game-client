package de.gymdon.inf1315.game.render.gui;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

public abstract class GuiControl extends Gui implements MouseInputListener{
    public abstract int getId();
    
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    
    public abstract void addActionListener(ActionListener l);
    public abstract void removeActionListener(ActionListener l);
}
