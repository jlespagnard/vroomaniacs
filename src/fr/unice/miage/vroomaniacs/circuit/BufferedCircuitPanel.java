package fr.unice.miage.vroomaniacs.circuit;

import java.awt.Graphics;

import javax.swing.JPanel;

import fr.unice.miage.vroomaniacs_plugins.objetsAnimes.Deplacable;
import fr.unice.miage.vroomaniacs_plugins.objetsAnimes.Dessinable;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IVroomaniacs;

@SuppressWarnings("serial")
public class BufferedCircuitPanel extends JPanel {
	private IVroomaniacs m_parent;
	
	public BufferedCircuitPanel(IVroomaniacs p_parent) {
		super();
		this.m_parent = p_parent;
	}
	
	@Override
	public void update(Graphics g) {
		this.paint(g);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// Il faut dessiner les voitures sur le circuit avec g2
		// car le circuit est dans le panel m_circuitPanel
		for(Dessinable o : this.m_parent.getListeObjetDessinable()) {
			o.dessineToi(g);
			if(o instanceof Deplacable) {
				((Deplacable)o).deplaceToi();
			}
		}
		this.validate();
	}
}
