package fr.unice.miage.vroomaniacs.circuit.builder;

import fr.unice.miage.vroomaniacs.circuit.element.VirageSD;
import fr.unice.miage.vroomaniacs.circuit.gui.EditeurCircuit;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public class BuilderVirageSD extends Builder {
	@Override
	public void creerElement(EditeurCircuit p_editeur, String p_id) {
		this.m_element = new VirageSD(p_editeur,p_id);
	}
}