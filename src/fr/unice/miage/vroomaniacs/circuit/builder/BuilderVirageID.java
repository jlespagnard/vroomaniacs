package fr.unice.miage.vroomaniacs.circuit.builder;

import javax.swing.JFrame;

import fr.unice.miage.vroomaniacs.circuit.element.VirageID;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public class BuilderVirageID extends Builder {
	
	/**
	 * Constructeur.
	 */
	public BuilderVirageID() {
		super("./images/virage-inferieur-droit.png");
	}
	
	@Override
	public void creerElement(JFrame p_editeur, String p_id) {
		this.m_element = new VirageID(p_editeur,this,p_id);
	}
}