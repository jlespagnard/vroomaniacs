package fr.unice.miage.vroomaniacs.circuit.builder;

import javax.swing.JFrame;

import fr.unice.miage.vroomaniacs.circuit.element.RouteDepart;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public class BuilderRouteDepart extends Builder {
	
	/**
	 * Constructeur.
	 */
	public BuilderRouteDepart() {
		super("./images/route-depart.png");
	}
	
	@Override
	public void creerElement(JFrame p_editeur, String p_id) {
		this.m_element = new RouteDepart(p_editeur,this,p_id);
	}
}