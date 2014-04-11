package de.gymdon.inf1315.game;

import java.awt.*;
import javax.swing.*;

public class GameCanvas extends JPanel
{
	private static final long serialVersionUID = 1L;

	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		g2d.clearRect(0, 0, getWidth(), getHeight());
	}
}
