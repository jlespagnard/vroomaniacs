package fr.unice.miage.vroomaniacs.circuit.builder;

import fr.unice.miage.vroomaniacs.circuit.element.VirageSG;
import fr.unice.miage.vroomaniacs.circuit.gui.EditeurCircuit;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public class BuilderVirageSG extends Builder {
	
	/**
	 * Constructeur.
	 */
	public BuilderVirageSG() {
		super("./images/virage-superieur-gauche.png");
	}
	
	@Override
	public void creerElement(EditeurCircuit p_editeur, String p_id) {
		this.m_element = new VirageSG(p_editeur,this,p_id);
	}
}