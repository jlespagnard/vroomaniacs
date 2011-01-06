package fr.unice.miage.vroomaniacs.circuit.builder;

import javax.swing.JFrame;

import fr.unice.miage.vroomaniacs.circuit.element.RouteV;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public class BuilderRouteV extends Builder {
	
	/**
	 * Constructeur.
	 */
	public BuilderRouteV() {
		super("./images/route-verticale.png");
	}
	
	@Override
	public void creerElement(JFrame p_editeur, String p_id) {
		this.m_element = new RouteV(p_editeur,this,p_id);
	}
}