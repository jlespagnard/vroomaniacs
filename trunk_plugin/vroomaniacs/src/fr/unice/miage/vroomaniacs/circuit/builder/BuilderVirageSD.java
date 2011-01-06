package fr.unice.miage.vroomaniacs.circuit.builder;

import javax.swing.JFrame;

import fr.unice.miage.vroomaniacs.circuit.element.VirageSD;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public class BuilderVirageSD extends Builder {
	
	/**
	 * Constructeur.
	 */
	public BuilderVirageSD() {
		super("./images/virage-superieur-droit.png");
	}
	
	@Override
	public void creerElement(JFrame p_editeur, String p_id) {
		this.m_element = new VirageSD(p_editeur,this,p_id);
	}
}