package fr.unice.miage.vroomaniacs.circuit.builder;

import javax.swing.JFrame;

import fr.unice.miage.vroomaniacs.circuit.element.Herbe;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public class BuilderHerbe extends Builder {
	
	/**
	 * Constructeur.
	 */
	public BuilderHerbe() {
		super("./images/font.png");
	}
	
	@Override
	public void creerElement(JFrame p_editeur, String p_id) {
		this.m_element = new Herbe(p_editeur,this,p_id);
	}
}