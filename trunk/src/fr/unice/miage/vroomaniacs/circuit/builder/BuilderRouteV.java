package fr.unice.miage.vroomaniacs.circuit.builder;

import fr.unice.miage.vroomaniacs.circuit.element.RouteV;
import fr.unice.miage.vroomaniacs.circuit.gui.EditeurCircuit;

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
	public void creerElement(EditeurCircuit p_editeur, String p_id) {
		this.m_element = new RouteV(p_editeur,this,p_id);
	}
}