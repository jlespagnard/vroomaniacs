package fr.unice.miage.vroomaniacs.circuit.builder;

import fr.unice.miage.vroomaniacs.circuit.element.RouteDepart;
import fr.unice.miage.vroomaniacs.circuit.gui.EditeurCircuit;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public class BuilderRouteDepart extends Builder {
	@Override
	public void creerElement(EditeurCircuit p_editeur, String p_id) {
		this.m_element = new RouteDepart(p_editeur,p_id);
	}
}