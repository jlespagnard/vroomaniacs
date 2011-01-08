package fr.unice.miage.vroomaniacs.circuit;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import fr.unice.miage.vroomaniacs_plugins.builders.element.Element;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IElement;

@SuppressWarnings("serial")
public class CircuitPanel extends JPanel {
	public CircuitPanel() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JPanel mainPanel = new JPanel();
		this.add(mainPanel);
		
		List<IElement> elements = new LinkedList<IElement>();
		for(IElement elem : Circuit.getInstance()) {
			elem.setBorderElement(null);
			elements.add(elem);
			mainPanel.add((Element)elem);
		}
		
		if(!elements.isEmpty()) {
			int x = Integer.parseInt(elements.get(elements.size()-1).getId().split("_")[0]);
			int y = Integer.parseInt(elements.get(elements.size()-1).getId().split("_")[1]);
			mainPanel.setLayout(new GridLayout(x+1,y+1));
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(Circuit.getInstance().estValide()) {
			if(this.getComponent(0) != null) {
				Graphics2D panGridGraphic = (Graphics2D)this.getComponent(0).getGraphics();
				panGridGraphic.setColor(Color.MAGENTA);
				panGridGraphic.setStroke(new BasicStroke(2));
				Point pointPrecedent = null;
//				for(Point point : Circuit.getInstance().getChemin()) {
//					if(pointPrecedent != null) {
//						panGridGraphic.drawLine(pointPrecedent.x, pointPrecedent.y, point.x, point.y);
//					}
//					pointPrecedent = point;
//				}
			}
			this.validate();
		}
	}
}