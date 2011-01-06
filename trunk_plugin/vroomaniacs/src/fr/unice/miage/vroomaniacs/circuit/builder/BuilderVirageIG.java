package fr.unice.miage.vroomaniacs.circuit.builder;

import javax.swing.JFrame;

import fr.unice.miage.vroomaniacs.circuit.element.VirageIG;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public class BuilderVirageIG extends Builder {
	
	/**
	 * Constructeur.
	 */
	public BuilderVirageIG() {
		super("./images/virage-inferieur-gauche.png");
	}
	
	@Override
	public void creerElement(JFrame p_editeur, String p_id) {
		this.m_element = new VirageIG(p_editeur,this,p_id);
	}
}