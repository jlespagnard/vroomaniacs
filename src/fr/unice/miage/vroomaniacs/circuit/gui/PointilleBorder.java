package fr.unice.miage.vroomaniacs.circuit.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class PointilleBorder extends LineBorder {
	
	public PointilleBorder(Color color, int thickness) {
		super(color, thickness);
	}
	
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y,
			int width, int height) {
		Graphics2D g2 = (Graphics2D)g;
		float[] dashA = {10,10};
		BasicStroke s = new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 5f, dashA, 0f);
		g2.setStroke(s);
		
		Color oldColor = g.getColor();
        g.setColor(lineColor);
        for(int i=0;i<thickness;i++)  {
        	g.drawLine(x+i, y+i, x+width-i-i-1, y+i);
	        g.drawLine(x+i, y+i, x+i, y+height-i-i-1);
	        g.drawLine(x+width-i-i-1, y+i, x+width-i-i-1, y+height-i-i-1);
	        g.drawLine(x+i, y+height-i-i-1, x+width-i-i-1, y+height-i-i-1);
        }
        g.setColor(oldColor);
	}
}
