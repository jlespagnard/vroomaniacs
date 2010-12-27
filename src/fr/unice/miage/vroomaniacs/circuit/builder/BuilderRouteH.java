package fr.unice.miage.vroomaniacs.circuit.builder;

import fr.unice.miage.vroomaniacs.circuit.element.RouteH;
import fr.unice.miage.vroomaniacs.circuit.gui.EditeurCircuit;

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
	public void creerElement(EditeurCircuit p_editeur, String p_id) {
		this.m_element = new RouteH(p_editeur,this,p_id);
	}
}