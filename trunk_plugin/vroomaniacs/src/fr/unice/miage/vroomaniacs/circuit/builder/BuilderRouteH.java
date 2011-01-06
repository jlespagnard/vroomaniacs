package fr.unice.miage.vroomaniacs.circuit.builder;

import javax.swing.JFrame;

import fr.unice.miage.vroomaniacs.circuit.element.RouteH;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public class BuilderRouteH extends Builder {
	
	/**
	 * Constructeur.
	 */
	public BuilderRouteH() {
		super("./images/route-horizontale.png");
	}
	
	@Override
	public void creerElement(JFrame p_editeur, String p_id) {
		this.m_element = new RouteH(p_editeur,this,p_id);
	}
}